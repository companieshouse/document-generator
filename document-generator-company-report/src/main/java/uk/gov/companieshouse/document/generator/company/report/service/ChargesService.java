package uk.gov.companieshouse.document.generator.company.report.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriTemplate;
import uk.gov.companieshouse.api.ApiClient;
import uk.gov.companieshouse.api.error.ApiErrorResponseException;
import uk.gov.companieshouse.api.handler.charges.request.ChargesGet;
import uk.gov.companieshouse.api.handler.exception.URIValidationException;
import uk.gov.companieshouse.api.model.charges.ChargesApi;
import uk.gov.companieshouse.document.generator.company.report.exception.ServiceException;

@Service
public class ChargesService {

    @Autowired
    CompanyReportApiClientService companyReportApiClientService;

    private static final String ITEMS_PER_PAGE_KEY = "items_per_page";
    private static final String ITEMS_PER_PAGE = "100";
    private static final String START_INDEX_KEY = "start_index";
    private static final UriTemplate GET_CHARGES_URI =
        new UriTemplate("/company/{companyNumber}/charges");

    public ChargesApi getCharges(String companyNumber) throws ServiceException {

        ChargesApi chargesApi = null;

        ApiClient apiClient = companyReportApiClientService.getApiClient();

        int startIndex = 0;
        int itemsPerPage = Integer.parseInt(ITEMS_PER_PAGE);

        chargesApi = retrieveChargesApi(companyNumber, apiClient, startIndex, itemsPerPage);

        while (chargesApi.getItems().size() < chargesApi.getTotalCount()) {
            startIndex += itemsPerPage;
            ChargesApi moreResults = retrieveChargesApi(companyNumber, apiClient, startIndex, itemsPerPage);
            chargesApi.getItems().addAll(moreResults.getItems());
        }

        return chargesApi;
    }

    private ChargesApi retrieveChargesApi(String companyNumber, ApiClient apiClient, Integer startIndex, Integer itemsPerPage) throws ServiceException {

        String uri = GET_CHARGES_URI.expand(companyNumber).toString();

        ChargesApi chargesApi;

        try {
            ChargesGet chargesGet = apiClient.charges().get(uri);
            chargesGet.addQueryParams(ITEMS_PER_PAGE_KEY, ITEMS_PER_PAGE);
            chargesGet.addQueryParams(START_INDEX_KEY, startIndex.toString());

            chargesApi = chargesGet.execute().getData();
        } catch (ApiErrorResponseException e) {

            throw new ServiceException("Error retrieving company charges", e);
        } catch (URIValidationException e) {

            throw new ServiceException("Invalid URI for company charges", e);
        }

        return chargesApi;
    }
}
