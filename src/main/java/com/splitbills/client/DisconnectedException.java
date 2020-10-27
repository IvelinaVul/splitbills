package com.splitbills.client;

import java.io.IOException;

public class DisconnectedException extends IOException {

    public DisconnectedException() {
        super();
    }

    public DisconnectedException(Throwable cause) {
        super(cause);

    }

    public DisconnectedException(String message, Throwable cause) {
        super(message, cause);
    }

}
