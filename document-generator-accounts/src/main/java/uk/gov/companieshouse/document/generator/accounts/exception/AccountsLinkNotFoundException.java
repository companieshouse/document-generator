package uk.gov.companieshouse.document.generator.accounts.exception;

/**
 * AccountsLinkNotFoundException is a wrapper exception that hides
 * lower level exceptions from the caller and prevents them
 * from being propagated up the call stack.
 */
public class AccountsLinkNotFoundException extends Exception {

    /**
     * Constructs a new AccountsLinkNotFoundException with a custom message.
     *
     * @param message a custom message
     */
    public AccountsLinkNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new AccountsLinkNotFoundException with a custom message and the specified
     * cause.
     *
     * @param message a custom message
     * @param cause the cause
     */
    public AccountsLinkNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
