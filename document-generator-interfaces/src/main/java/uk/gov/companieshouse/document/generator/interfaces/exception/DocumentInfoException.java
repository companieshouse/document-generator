package uk.gov.companieshouse.document.generator.interfaces.exception;

/**
 * DocumentInfoException is a wrapper exception that hides
 * lower level exceptions from the caller and prevents them
 * from being propagated up the call stack.
 */
public class DocumentInfoException extends Exception{

    /**
     * Constructs a new DocumentInfoException with a custom message
     *
     * @param message a custom message
     */
    public DocumentInfoException(String message) {
        super(message);
    }

    /**
     * Constructs a new DocumentInfoException with a custom message and specified cause
     *
     * @param message a custom message
     * @param cause the cause
     */
    public DocumentInfoException(String message, Throwable cause) {
        super(message, cause);
    }
}
