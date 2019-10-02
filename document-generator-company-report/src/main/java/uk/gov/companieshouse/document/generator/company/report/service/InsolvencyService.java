package uk.gov.companieshouse.document.generator.company.report.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriTemplate;
import uk.gov.companieshouse.api.ApiClient;
import uk.gov.companieshouse.api.error.ApiErrorResponseException;
import uk.gov.companieshouse.api.handler.exception.URIValidationException;
import uk.gov.companieshouse.api.model.insolvency.InsolvencyApi;
import uk.gov.companieshouse.document.generator.company.report.exception.ServiceException;

@Service
public class InsolvencyService {

    private CompanyReportApiClientService companyReportApiClientService;

    @Autowired
    public InsolvencyService(CompanyReportApiClientService companyReportApiClientService) {
        this.companyReportApiClientService = companyReportApiClientService;
    }

    private static final UriTemplate GET_INSOLVENCY_URI =
        new UriTemplate("/company/{companyNumber}/insolvency");

    public InsolvencyApi getInsolvency(String companyNumber) throws ServiceException {

        InsolvencyApi insolvencyApi;

        ApiClient apiClient = companyReportApiClientService.getApiClient();

        String uri = GET_INSOLVENCY_URI.expand(companyNumber).toString();

        try {
            insolvencyApi =  apiClient.insolvency().get(uri).execute().getData();
        } catch (ApiErrorResponseException e) {

            throw new ServiceException("Error retrieving insolvency", e);
        } catch (URIValidationException e) {

            throw new ServiceException("Invalid URI for insolvency resource", e);
        }
        return insolvencyApi;
    }
}
