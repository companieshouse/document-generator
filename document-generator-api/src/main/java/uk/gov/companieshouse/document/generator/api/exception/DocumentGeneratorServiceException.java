package uk.gov.companieshouse.document.generator.api.exception;

/**
 * DocumentGeneratorServiceException is a wrapper exception that hides
 * lower level exceptions from the caller and prevents them
 * from being propagated up the call stack.
 */
public class DocumentGeneratorServiceException extends Exception {

    /**
     * Constructs a new DocumentGeneratorServiceException with a custom message
     *
     * @param message a custom message
     */
    public DocumentGeneratorServiceException(String message) {
        super(message);
    }

    /**
     * Constructs a new DocumentGeneratorServiceException with a custom message and specified cause
     *
     * @param message a custom message
     * @param cause the cause
     */
    public DocumentGeneratorServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
