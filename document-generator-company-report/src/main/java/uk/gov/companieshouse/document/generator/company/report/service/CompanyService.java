package uk.gov.companieshouse.document.generator.company.report.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.UriTemplate;
import uk.gov.companieshouse.api.ApiClient;
import uk.gov.companieshouse.api.error.ApiErrorResponseException;
import uk.gov.companieshouse.api.handler.exception.URIValidationException;
import uk.gov.companieshouse.api.model.company.CompanyProfileApi;
import uk.gov.companieshouse.document.generator.company.report.exception.ServiceException;

public class CompanyService {

    @Autowired
    private ApiClientService apiClientService;

    private static final UriTemplate GET_COMPANY_URI =
        new UriTemplate("/company/{companyNumber}");

    public CompanyProfileApi getCompanyProfile(String companyNumber) throws ServiceException {

        CompanyProfileApi companyProfileApi;

        ApiClient apiClient = apiClientService.getApiClient();

        String uri = GET_COMPANY_URI.expand(companyNumber).toString();

        try {
            companyProfileApi = apiClient.company().get(uri).execute();
        } catch (
            ApiErrorResponseException e) {

            throw new ServiceException("Error retrieving company profile", e);
        } catch (
            URIValidationException e) {

            throw new ServiceException("Invalid URI for company resource", e);
        }

        return companyProfileApi;
    }
}
