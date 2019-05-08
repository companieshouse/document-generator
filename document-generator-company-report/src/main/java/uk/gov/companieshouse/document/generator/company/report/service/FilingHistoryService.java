package uk.gov.companieshouse.document.generator.company.report.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.UriTemplate;
import uk.gov.companieshouse.api.ApiClient;
import uk.gov.companieshouse.api.error.ApiErrorResponseException;
import uk.gov.companieshouse.api.handler.exception.URIValidationException;
import uk.gov.companieshouse.api.model.filinghistory.FilingHistoryApi;
import uk.gov.companieshouse.document.generator.company.report.exception.ServiceException;

public class FilingHistoryService {

    @Autowired
    private ApiClientService apiClientService;

    private static final UriTemplate GET_FILING_HISTORY_URI =
        new UriTemplate("/company/{companyNumber}/filing-history");

    public FilingHistoryApi getFilingHistory(String companyNumber) throws ServiceException {

        FilingHistoryApi filingHistoryApi;

        ApiClient apiClient = apiClientService.getApiClient();

        String uri = GET_FILING_HISTORY_URI.expand(companyNumber).toString();

        try {
            filingHistoryApi = apiClient.filingHistory().list(uri).execute();
        }  catch (
            ApiErrorResponseException e) {

            throw new ServiceException("Error retrieving filing history", e);
        } catch (
            URIValidationException e) {

            throw new ServiceException("Invalid URI for filing history", e);
        }

        return filingHistoryApi;
    }
}
