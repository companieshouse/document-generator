
package uk.gov.companieshouse.document.generator.company.report.exception;

/**
 * ServiceException is a wrapper exception that hides
 * lower level exceptions from the caller and prevents them
 * from being propagated up the call stack.
 */
public class ServiceException extends Exception {

    /**
     * Constructs a new ServiceException with a custom message.
     *
     * @param message a custom message
     */
    public ServiceException(String message) {
        super(message);
    }

    /**
     * Constructs a new ServiceException with a custom message and the specified
     * cause.
     *
     * @param message a custom message
     * @param cause the cause
     */
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}