package uk.gov.companieshouse.document.generator.company.report.exception;

/**
 * MapperException is a wrapper exception that hides
 * lower level exceptions from the caller and prevents them
 * from being propagated up the call stack.
 */
public class MapperException extends Exception {

    /**
     * Constructs a new MapperException with a custom message.
     *
     * @param message a custom message
     */
    public MapperException(String message) {
        super(message);
    }

    /**
     * Constructs a new MapperException with a custom message and the specified
     * cause.
     *
     * @param message a custom message
     * @param cause the cause
     */
    public MapperException(String message, Throwable cause) {
        super(message, cause);
    }
}