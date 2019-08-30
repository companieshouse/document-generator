package uk.gov.companieshouse.document.generator.company.report.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriTemplate;
import uk.gov.companieshouse.api.ApiClient;
import uk.gov.companieshouse.api.error.ApiErrorResponseException;
import uk.gov.companieshouse.api.handler.exception.URIValidationException;
import uk.gov.companieshouse.api.handler.officers.request.OfficersList;
import uk.gov.companieshouse.api.model.officers.OfficersApi;
import uk.gov.companieshouse.document.generator.company.report.exception.ServiceException;

@Service
public class OfficerService {

    private CompanyReportApiClientService companyReportApiClientService;

    @Autowired
    public OfficerService(CompanyReportApiClientService companyReportApiClientService) {
        this.companyReportApiClientService = companyReportApiClientService;
    }

    private static final UriTemplate GET_OFFICERS_URI =
        new UriTemplate("/company/{companyNumber}/officers");

    public OfficersApi getOfficers(String companyNumber) throws ServiceException {

        OfficersApi officersApi;

        ApiClient apiClient = companyReportApiClientService.getApiClient();

        String uri = GET_OFFICERS_URI.expand(companyNumber).toString();

        try {
            OfficersList officersList = apiClient.officers().list(uri);
            officersList.addQueryParams("items_per_page", "100");

            officersApi = officersList.execute().getData();
        } catch (ApiErrorResponseException e) {

            throw new ServiceException("Error retrieving officers", e);
        } catch (URIValidationException e) {

            throw new ServiceException("Invalid URI for officers resource", e);
        }
        return officersApi;
    }
}
