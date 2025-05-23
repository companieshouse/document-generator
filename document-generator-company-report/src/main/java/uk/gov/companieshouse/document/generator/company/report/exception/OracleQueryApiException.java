package uk.gov.companieshouse.document.generator.company.report.exception;

/**
 * OracleQueryApiException is a custom exception that is thrown when there is an
 * error while calling the `oracle-query-api` service.
 *
 * This is a runtime exception since the previous code that used Spring RestClient also threw a runtime exception
 */
public class OracleQueryApiException extends RuntimeException {

    public OracleQueryApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
