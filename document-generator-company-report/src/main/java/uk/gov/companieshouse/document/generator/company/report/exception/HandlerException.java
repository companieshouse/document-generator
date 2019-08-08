package uk.gov.companieshouse.document.generator.company.report.exception;

/**
 * HandlerException is a wrapper exception that hides
 * lower level exceptions from the caller and prevents them
 * from being propagated up the call stack.
 */
public class HandlerException extends Exception{

    /**
     * Constructs a new HandlerException with a custom message.
     *
     * @param message a custom message
     */
    public HandlerException(String message) {
        super(message);
    }

    /**
     * Constructs a new HandlerException with a custom message and the specified
     * cause.
     *
     * @param message a custom message
     * @param cause the cause
     */
    public HandlerException(String message, Throwable cause) {
        super(message, cause);
    }
}