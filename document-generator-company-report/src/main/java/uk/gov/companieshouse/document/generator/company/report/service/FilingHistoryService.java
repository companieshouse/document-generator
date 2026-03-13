package uk.gov.companieshouse.document.generator.company.report.service;

import static uk.gov.companieshouse.document.generator.company.report.CompanyReportDocumentInfoServiceImpl.MODULE_NAME_SPACE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriTemplate;
import uk.gov.companieshouse.api.ApiClient;
import uk.gov.companieshouse.api.error.ApiErrorResponseException;
import uk.gov.companieshouse.api.handler.exception.URIValidationException;
import uk.gov.companieshouse.api.handler.filinghistory.request.FilingHistoryList;
import uk.gov.companieshouse.api.model.filinghistory.FilingHistoryApi;
import uk.gov.companieshouse.document.generator.company.report.exception.ServiceException;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;

@Service
public class FilingHistoryService {

    private CompanyReportApiClientService companyReportApiClientService;

    private static final String ITEMS_PER_PAGE_KEY = "items_per_page";
    private static final String ITEMS_PER_PAGE_VALUE = "100";
    private static final String START_INDEX_KEY = "start_index";
    private static final Logger LOG = LoggerFactory.getLogger(MODULE_NAME_SPACE);

    @Autowired
    public FilingHistoryService(CompanyReportApiClientService companyReportApiClientService) {
        this.companyReportApiClientService = companyReportApiClientService;
    }

    private static final UriTemplate GET_FILING_HISTORY_URI =
        new UriTemplate("/company/{company_number}/filing-history");

    public FilingHistoryApi getFilingHistory(String companyNumber) throws ServiceException {
        LOG.debug("getFilingHistory - getting filing history...");
        FilingHistoryApi filingHistoryApi = null;

        ApiClient apiClient = companyReportApiClientService.getApiClient();

        int startIndex = 0;
        int itemsPerPage = 100;

        LOG.debug("getFilingHistory(): calling retrieveFilingHistory("+companyNumber+")");

        filingHistoryApi = retrieveFilingHistory(companyNumber, apiClient, startIndex, itemsPerPage);

        while (filingHistoryApi.getItems().size() < filingHistoryApi.getTotalCount()) {
            startIndex += itemsPerPage;
            FilingHistoryApi moreResults = retrieveFilingHistory(companyNumber, apiClient, startIndex, itemsPerPage);
            filingHistoryApi.getItems().addAll(moreResults.getItems());
        }

        LOG.debug("getFilingHistory(): returning to caller");

        return filingHistoryApi;
    }

    private FilingHistoryApi retrieveFilingHistory(String companyNumber, ApiClient apiClient, Integer startIndex, Integer itemsPerPage)
        throws ServiceException {

        LOG.debug("retrieveFilingHistory(): )retrieving filing history");

        String uri = GET_FILING_HISTORY_URI.expand(companyNumber).toString();

        LOG.debug("retrieveFilingHistory(): )uri -> "+uri);

        FilingHistoryApi filingHistoryApi;

        try {
            FilingHistoryList filingHistoryList = apiClient.filingHistory().list(uri);
            filingHistoryList.addQueryParams(ITEMS_PER_PAGE_KEY, ITEMS_PER_PAGE_VALUE);
            filingHistoryList.addQueryParams(START_INDEX_KEY, startIndex.toString());

            filingHistoryApi = filingHistoryList.execute().getData();
        } catch (ApiErrorResponseException e) {
            LOG.debug("retrieveFilingHistory(): ApiErrorResponseException - error -> "+e.getMessage());
            throw new ServiceException("Error retrieving filing history items", e);
        } catch (URIValidationException e) {
            LOG.debug("retrieveFilingHistory(): URIValidationException - error -> "+e.getMessage());
            throw new ServiceException("Invalid URI for filing history resource", e);
        }
        LOG.debug("retrieveFilingHistory(): returning to caller");
        return filingHistoryApi;
    }
}
