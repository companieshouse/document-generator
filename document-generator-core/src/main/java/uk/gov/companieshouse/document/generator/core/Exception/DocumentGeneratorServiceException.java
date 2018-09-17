package uk.gov.companieshouse.document.generator.core.Exception;

public class DocumentGeneratorServiceException extends Exception {

    public DocumentGeneratorServiceException(String message) {
        super(message);
    }

    public DocumentGeneratorServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
