package uk.gov.companieshouse.document.generator.prosecution.tmpclient;

public class SdkException extends Exception {
    private static final long serialVersionUID = 1L;

    public SdkException(String message) {
        super(message);
    }

    public SdkException(String message, Throwable cause) {
        super(message, cause);
    }
}
