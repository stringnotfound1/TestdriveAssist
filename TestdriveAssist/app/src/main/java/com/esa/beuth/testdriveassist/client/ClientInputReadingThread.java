package com.esa.beuth.testdriveassist.client;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.Arrays;

/**
 * @author Schellner - Berner & Mattner Systemtechnik GmbH
 */
public class ClientInputReadingThread extends AsyncTask<Void, Object, Void> {
    private final Client client;
    private volatile boolean end = false;
    private byte[] inputBuffer;

    public ClientInputReadingThread(final Client client) {
        this.client = client;
    }

    @Override
    public Void doInBackground(Void... params) {
        System.out.println("started InputReadingThread");
        while (!end && client.isConnected() && !client.getSocket().isClosed())
            handleInput();
        return null;
    }

    @Override
    protected void onProgressUpdate(Object... values) {
        int length = (int) values[0];
        byte[] bytes = (byte[]) values[1];
        System.out.println("Length: " + length);
        if (client.getOnInput() != null && length > 0)
            client.getOnInput().accept(length, bytes);
    }

    private void handleInput() {
        System.out.println("handling input");
        inputBuffer = new byte[client.getInputBufferSize()];
        int length = tryRead();
        if (length == -1) {
            end = true;
            return;
        }
        byte[] bytes = Arrays.copyOf(inputBuffer, length);
        publishProgress(length, bytes);
    }

    private int tryRead() {
        try {
            return client.getSocket().getInputStream().read(inputBuffer);
        } catch (IOException e) {
            client.getClientExceptionHandler().handleSocketReadException(e);
        }
        return -1;
    }
}
