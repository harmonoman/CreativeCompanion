package com.nashss.se.creativecompanion.exceptions;


public class WordPoolNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1388755943846983272L;

    /**
     * Exception with no message or cause.
     */
    public WordPoolNotFoundException() {
        super();
    }

    /**
     * Exception with a message, but no cause.
     * @param message A descriptive message for this exception.
     */
    public WordPoolNotFoundException(String message) {
        super(message);
    }

    /**
     * Exception with no message, but with a cause.
     * @param cause The original throwable resulting in this exception.
     */
    public WordPoolNotFoundException(Throwable cause) {
        super(cause);
    }

    /**
     * Exception with message and cause.
     * @param message A descriptive message for this exception.
     * @param cause The original throwable resulting in this exception.
     */
    public WordPoolNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
