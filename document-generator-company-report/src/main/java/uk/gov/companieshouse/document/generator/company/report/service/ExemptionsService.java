package uk.gov.companieshouse.document.generator.company.report.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriTemplate;
import uk.gov.companieshouse.api.ApiClient;
import uk.gov.companieshouse.api.error.ApiErrorResponseException;
import uk.gov.companieshouse.api.handler.exception.URIValidationException;
import uk.gov.companieshouse.api.handler.exemptions.request.ExemptionsList;
import uk.gov.companieshouse.api.model.exemptions.CompanyExemptionsApi;
import uk.gov.companieshouse.document.generator.company.report.exception.ServiceException;

@Service
public class ExemptionsService {

    @Autowired CompanyReportApiClientService companyReportApiClientService;

    private static final UriTemplate GET_EXEMPTIONS_URI =
        new UriTemplate("/company/{companyNumber}/exemptions");

    public CompanyExemptionsApi getCompanyExemptions(String companyNumber) throws ServiceException {

        CompanyExemptionsApi companyExemptionsApi;

        ApiClient apiClient = companyReportApiClientService.getApiClient();

        String uri = GET_EXEMPTIONS_URI.expand(companyNumber).toString();

        try {

            ExemptionsList exemptionsList = apiClient.exemptions().list(uri);

            companyExemptionsApi = exemptionsList.execute().getData();
        } catch (ApiErrorResponseException e) {

            throw new ServiceException("Error retrieving company exemptions", e);
        } catch (URIValidationException e) {

            throw new ServiceException("Invalid URI for company exemptions resource", e);
        }
        return companyExemptionsApi;
    }
}
