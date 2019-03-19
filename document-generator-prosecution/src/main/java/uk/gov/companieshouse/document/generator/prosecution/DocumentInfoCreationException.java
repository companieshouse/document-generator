package uk.gov.companieshouse.document.generator.prosecution;

import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfoResponse;

/**
 * Thrown if there is an error creating a {@link DocumentInfoResponse}.
 */
public class DocumentInfoCreationException extends Exception {
    private static final long serialVersionUID = 1L;

    public DocumentInfoCreationException(String message) {
        super(message);
    }

    public DocumentInfoCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public DocumentInfoCreationException(String message, Throwable cause, boolean enableSuppression,
                    boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
