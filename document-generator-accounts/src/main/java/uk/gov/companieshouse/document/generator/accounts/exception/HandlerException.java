package uk.gov.companieshouse.document.generator.accounts.exception;

/**
 * The class {@code HandlerException} is a form of {@code Exception}
 * that should be used at the handler layer to abstract lower level
 * level exceptions from being propagated up the call stack.
 */
public class HandlerException extends Exception {

    /**
     * Constructs a new {@code HandlerException} with a custom message.
     *
     * @param message a custom message
     */
    public HandlerException(String message) {
        super(message);
    }


    /**
     * Constructs a new {@code HandlerException} with a custom message and the specified
     * cause.
     *
     * @param message a custom message
     * @param cause the cause
     */
    public HandlerException(String message, Throwable cause) {
        super(message, cause);
    }
}