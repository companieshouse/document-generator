package uk.gov.companieshouse.document.generator.accounts.exception;

/**
 * ManagerException is a wrapper exception that hides
 * lower level exceptions from the caller and prevents them
 * from being propagated up the call stack.
 */
public class ManagerException extends Exception {

    /**
     * Constructs a new ManagerException with a custom message.
     *
     * @param message a custom message
     */
    public ManagerException(String message) {
        super(message);
    }
    
    /**
     * Constructs a new ManagerException with a custom message and the specified
     * cause.
     *
     * @param message a custom message
     * @param cause the cause
     */
    public ManagerException(String message, Throwable cause) {
        super(message, cause);
    }
}
