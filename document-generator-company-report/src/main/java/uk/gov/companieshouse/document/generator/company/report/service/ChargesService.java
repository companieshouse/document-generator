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

    private static final UriTemplate GET_CHARGES_URI =
        new UriTemplate("/company/{companyNumber}/charges");

    public ChargesApi getCharges(String companyNumber) throws ServiceException {

        ChargesApi chargesApi;

        ApiClient apiClient = companyReportApiClientService.getApiClient();

        String uri = GET_CHARGES_URI.expand(companyNumber).toString();

        try {
            ChargesGet chargesGet = apiClient.charges().get(uri);
            chargesGet.addQueryParams("items_per_page", "100");

            chargesApi = chargesGet.execute().getData();
        } catch (ApiErrorResponseException e) {

            throw new ServiceException("Error retrieving company charges", e);
        } catch (URIValidationException e) {

            throw new ServiceException("Invalid URI for company charges", e);
        }

        return chargesApi;
    }
}
