package uk.gov.companieshouse.document.generator.company.report.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriTemplate;
import uk.gov.companieshouse.api.ApiClient;
import uk.gov.companieshouse.api.error.ApiErrorResponseException;
import uk.gov.companieshouse.api.handler.exception.URIValidationException;
import uk.gov.companieshouse.api.handler.psc.request.PscsList;
import uk.gov.companieshouse.api.model.psc.PscsApi;
import uk.gov.companieshouse.document.generator.company.report.exception.ServiceException;

@Service
public class PscsService {

    private CompanyReportApiClientService companyReportApiClientService;

    @Autowired
    public PscsService(CompanyReportApiClientService companyReportApiClientService) {
        this.companyReportApiClientService = companyReportApiClientService;
    }

    private static final UriTemplate GET_PSCS_URI =
            new UriTemplate("/company/{companyNumber}/persons-with-significant-control");

    public PscsApi getPscs(String companyNumber) throws ServiceException {


        ApiClient apiClient = companyReportApiClientService.getApiClient();

        String uri = GET_PSCS_URI.expand(companyNumber).toString();

        try {

            PscsList pscsList = apiClient.pscs().list(uri);
            pscsList.addQueryParams("items_per_page", "100");

            return pscsList.execute().getData();
        } catch (ApiErrorResponseException e) {
            throw new ServiceException("Error retrieving pscs", e);
        } catch (URIValidationException e) {

            throw new ServiceException("Invalid URI for pscs resource", e);
        }
    }
}
