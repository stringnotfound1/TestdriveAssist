package com.esa.beuth.testdriveassist.client;

import java.io.IOException;
import java.net.SocketException;

/**
 * @author Schellner - Berner & Mattner Systemtechnik GmbH
 */
public class ClientExceptionHandler {

    private final Client client;

    public ClientExceptionHandler(final Client client) {
        this.client = client;
    }

    public void handleConnectException(final IOException e) {
        e.printStackTrace();
    }

    public void handleInitializeSocketException(final SocketException e) {
        e.printStackTrace();
    }

    public void handleCloseException(final IOException e) {
        e.printStackTrace();
    }

    public void handleWriteException(final IOException e) {
        e.printStackTrace();
    }

    public void handleSocketReadException(final IOException e) {
        if (e instanceof SocketException && e.getLocalizedMessage().equals("Socket closed"))
            return;
    }
}
