package uk.gov.companieshouse.document.generator.api.exception;

/**
 * RenderServiceException is a wrapper exception that hides
 * lower level exceptions from the caller and prevents them
 * from being propagated up the call stack.
 */
public class RenderServiceException extends Exception {

    /**
     * Constructs a new RenderServiceException with a custom message
     *
     * @param message a custom message
     */
    public RenderServiceException(String message) {
        super(message);
    }

    /**
     * Constructs a new RenderServiceException with a custom message and specified cause
     *
     * @param message a custom message
     * @param cause the cause
     */
    public RenderServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
