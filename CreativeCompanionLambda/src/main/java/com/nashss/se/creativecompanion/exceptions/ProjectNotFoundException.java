package com.nashss.se.creativecompanion.exceptions;


/**
 * Exception to throw when a given project ID is not found in the database.
 */
public class ProjectNotFoundException extends RuntimeException {


    private static final long serialVersionUID = 8515726741813520837L;

    /**
     * Exception with no message or cause.
     */
    public ProjectNotFoundException() {
        super();
    }

    /**
     * Exception with a message, but no cause.
     * @param message A descriptive message for this exception.
     */
    public ProjectNotFoundException(String message) {
        super(message);
    }

    /**
     * Exception with no message, but with a cause.
     * @param cause The original throwable resulting in this exception.
     */
    public ProjectNotFoundException(Throwable cause) {
        super(cause);
    }

    /**
     * Exception with message and cause.
     * @param message A descriptive message for this exception.
     * @param cause The original throwable resulting in this exception.
     */
    public ProjectNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
