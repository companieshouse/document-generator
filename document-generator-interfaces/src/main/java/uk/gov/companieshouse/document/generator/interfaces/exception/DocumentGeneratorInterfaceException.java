package uk.gov.companieshouse.document.generator.interfaces.exception;

/**
 * DocumentGeneratorInterfaceException is a wrapper exception that hides
 * lower level exceptions from the caller and prevents them
 * from being propagated up the call stack.
 */
public class DocumentGeneratorInterfaceException extends Exception{

    /**
     * Constructs a new DocumentGeneratorInterfaceException with a custom message
     *
     * @param message a custom message
     */
    public DocumentGeneratorInterfaceException(String message) {
        super(message);
    }

    /**
     * Constructs a new DocumentGeneratorInterfaceException with a custom message and specified cause
     *
     * @param message a custom message
     * @param cause the cause
     */
    public DocumentGeneratorInterfaceException(String message, Throwable cause) {
        super(message, cause);
    }
}
