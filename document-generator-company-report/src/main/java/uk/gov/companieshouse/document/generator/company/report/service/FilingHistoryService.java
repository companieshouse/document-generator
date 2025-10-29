package uk.gov.companieshouse.document.generator.company.report.service;


import java.nio.charset.StandardCharsets;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriTemplate;
import uk.gov.companieshouse.api.model.filinghistory.FilingHistoryApi;
import uk.gov.companieshouse.document.generator.company.report.exception.ServiceException;
import uk.gov.companieshouse.environment.EnvironmentReader;

@Service
public class FilingHistoryService {

    private CompanyReportApiClientService companyReportApiClientService;

    private static final String ITEMS_PER_PAGE_KEY = "items_per_page";
    private static final String ITEMS_PER_PAGE_VALUE = "100";
    private static final String START_INDEX_KEY = "start_index";

    private final RestClient restClient;
    private final EnvironmentReader environmentReader;

    @Autowired
    public FilingHistoryService(CompanyReportApiClientService companyReportApiClientService, RestClient restClient, EnvironmentReader environmentReader) {
        this.companyReportApiClientService = companyReportApiClientService;
        this.restClient = restClient;
        this.environmentReader = environmentReader;
    }

    private static final UriTemplate GET_FILING_HISTORY_URI =
        new UriTemplate("/company/{company_number}/filing-history");

    public FilingHistoryApi getFilingHistory(String companyNumber) throws ServiceException {

        FilingHistoryApi filingHistoryApi = null;

//        ApiClient apiClient = companyReportApiClientService.getApiClient();

        Integer startIndex = 0;
        Integer itemsPerPage = 100;

        filingHistoryApi = retrieveFilingHistory(companyNumber, restClient, startIndex, itemsPerPage);

        while (filingHistoryApi.getItems().size() < filingHistoryApi.getTotalCount()) {
            startIndex += itemsPerPage;
            FilingHistoryApi moreResults = retrieveFilingHistory(companyNumber, restClient, startIndex, itemsPerPage);
            filingHistoryApi.getItems().addAll(moreResults.getItems());
        }

        return filingHistoryApi;
    }

    private FilingHistoryApi retrieveFilingHistory(String companyNumber, RestClient restClient, Integer startIndex, Integer itemsPerPage)
        throws ServiceException {

//        FilingHistoryApi filingHistoryApi;

//        try {
            //            FilingHistoryList filingHistoryList = apiClient.filingHistory().list(uri);

            //            filingHistoryList.addQueryParams(ITEMS_PER_PAGE_KEY, ITEMS_PER_PAGE_VALUE);
            //            filingHistoryList.addQueryParams(START_INDEX_KEY, startIndex.toString());
            //
            //            filingHistoryApi = filingHistoryList.execute().getData();

        String CHS_INTERNAL_API_KEY = "CHS_INTERNAL_API_KEY";
        final String apiKey = environmentReader.getMandatoryString(CHS_INTERNAL_API_KEY);

        byte[] base64ApiKey = Base64.encodeBase64(apiKey.concat(":").getBytes(StandardCharsets.UTF_8));
        String base64ApiKeyString = new String(base64ApiKey, StandardCharsets.UTF_8);

        String uri = GET_FILING_HISTORY_URI.expand(companyNumber).toString();

        return restClient.get().uri(UriComponentsBuilder
                        .fromUriString(uri)
                        .queryParam(ITEMS_PER_PAGE_KEY, itemsPerPage)
                        .queryParam(START_INDEX_KEY, startIndex.toString())
                        .build()
                        .toUri())
                .headers(httpHeaders -> httpHeaders.setBasicAuth(base64ApiKeyString))
                .retrieve()
                .body(FilingHistoryApi.class);

            //        } catch (ApiErrorResponseException e) {
            //
            //            throw new ServiceException("Error retrieving filing history items", e);
            //        } catch (URIValidationException e) {
            //
            //            throw new ServiceException("Invalid URI for filing history resource", e);
            //        }
//        return filingHistoryApi;
    }
}
