package uk.gov.companieshouse.document.generator.api.Exception;

/**
 * The class {@code DocumentGeneratorServiceException} is a form of {@code Exception}
 * that should be used at the handler layer to abstract lower level
 * level exceptions from being propagated up the call stack.
 */
public class DocumentGeneratorServiceException extends Exception {

    /**
     * Constructs a new {@code DocumentGeneratorServiceException} with a custom message
     *
     * @param message a custom message
     */
    public DocumentGeneratorServiceException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code DocumentGeneratorServiceException} with a custom message and specified cause
     *
     * @param message a custom message
     * @param cause the cause
     */
    public DocumentGeneratorServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
