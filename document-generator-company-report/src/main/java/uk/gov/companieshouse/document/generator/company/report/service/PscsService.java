package uk.gov.companieshouse.document.generator.company.report.service;

import org.springframework.stereotype.Service;
import org.springframework.web.util.UriTemplate;
import uk.gov.companieshouse.api.ApiClient;
import uk.gov.companieshouse.api.error.ApiErrorResponseException;
import uk.gov.companieshouse.api.handler.exception.URIValidationException;
import uk.gov.companieshouse.api.model.psc.PscsApi;
import uk.gov.companieshouse.document.generator.company.report.exception.ServiceException;

@Service
public class PscsService {

    private static final UriTemplate GET_PSCS_URI =
            new UriTemplate("/company/{companyNumber}/persons-with-significant-control");
    private static final int ITEMS_PER_PAGE_VALUE = 100;

    private final CompanyReportApiClientService companyReportApiClientService;
    private final PscsPageRetrieverService pageRetrieverService;

    public PscsService(CompanyReportApiClientService companyReportApiClientService,
                       PscsPageRetrieverService pageRetrieverService) {
        this.companyReportApiClientService = companyReportApiClientService;
        this.pageRetrieverService = pageRetrieverService;
    }

    public PscsApi getPscs(String companyNumber) throws ServiceException {

        ApiClient apiClient = companyReportApiClientService.getApiClient();

        String uri = GET_PSCS_URI.expand(companyNumber).toString();

        try {
            return pageRetrieverService.retrieve(uri, apiClient,  ITEMS_PER_PAGE_VALUE);
        } catch (ApiErrorResponseException e) {
            throw new ServiceException("Error retrieving pscs", e);
        } catch (URIValidationException e) {

            throw new ServiceException("Invalid URI for pscs resource", e);
        }
    }
}
