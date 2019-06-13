package uk.gov.companieshouse.document.generator.prosecution.exception;

import uk.gov.companieshouse.document.generator.prosecution.service.ProsecutionService;

/**
 * Thrown whenever an error occurs in the {@link ProsecutionService}
 */
public class ProsecutionServiceException extends Exception{
    public ProsecutionServiceException(String message) {
        super(message);
    }
}
