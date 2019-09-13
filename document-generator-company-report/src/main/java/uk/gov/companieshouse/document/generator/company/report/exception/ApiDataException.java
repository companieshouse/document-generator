package uk.gov.companieshouse.document.generator.company.report.exception;

/**
 * ApiDataException is a wrapper exception that hides
 * lower level exceptions from the caller and prevents them
 * from being propagated up the call stack.
 */
public class ApiDataException extends Exception {

    /**
     * Constructs a new ApiDataException with a custom message.
     *
     * @param message a custom message
     */
    public ApiDataException(String message) {
        super(message);
    }

    /**
     * Constructs a new ApiDataException with a custom message and the specified
     * cause.
     *
     * @param message a custom message
     * @param cause   the cause
     */
    public ApiDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
