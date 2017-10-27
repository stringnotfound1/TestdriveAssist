package com.esa.beuth.testdriveassist.client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * @author Schellner - Berner & Mattner Systemtechnik GmbH
 */
public class Client {
    private int connectionTimeout = 3000;
    private int inputBufferSize = 256;
    private boolean showMessages = false;
    private Socket socket;
    private BiConsumer<Integer, byte[]> onInput;
    private Consumer<Socket> onConnectionEstablished;
    private Runnable onClose;
    private ClientInputReadingThread readingThread;
    private ClientExceptionHandler clientExceptionHandler = new ClientExceptionHandler(this);
    // this lock is for convenience for subclasses so they can send multiple
    // "write" statements without interruption
    private ReentrantLock writeLock = new ReentrantLock();
    private String ip;
    private int port;

    public void start(final InetAddress address, int port) throws Exception {
        start(address.getHostName(), port);
    }

    public void start(String ip, int port) throws Exception {
        if (showMessages)
            System.out.println("Connecting...");

        connect(ip, port);
        initializeSocket();

        readingThread = new ClientInputReadingThread(this);
        readingThread.execute();

        this.ip = ip;
        this.port = port;
    }

    private void connect(final String ip, final int port) throws Exception {
        close();
        socket = new Socket();
        socket.connect(new InetSocketAddress(InetAddress.getByName(ip), port), connectionTimeout);
        if (showMessages)
            System.out.println("Connected successfully");
    }

    private void initializeSocket() {
        try {
            socket.setTcpNoDelay(true);
        } catch (SocketException e) {
            clientExceptionHandler.handleInitializeSocketException(e);
        }
    }

    public void close() {
        if (onClose != null)
            onClose.run();
        if (socket == null)
            return;
        try {
            socket.close();
            socket = null;
        } catch (IOException e) {
            clientExceptionHandler.handleCloseException(e);
        }
        if (showMessages)
            System.out.println("Client closed");
    }

    public void write(final byte... bytes) {
        try {
            socket.getOutputStream().write(bytes);
        } catch (IOException e) {
            clientExceptionHandler.handleWriteException(e);
        }
    }

    public boolean isConnected() {
        return socket != null && socket.isConnected();
    }

    public BiConsumer<Integer, byte[]> getOnInput() {
        return onInput;
    }

    public ClientExceptionHandler getClientExceptionHandler() {
        return clientExceptionHandler;
    }

    public int getInputBufferSize() {
        return inputBufferSize;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setOnInput(BiConsumer<Integer, byte[]> onInput) {
        this.onInput = onInput;
    }
}
