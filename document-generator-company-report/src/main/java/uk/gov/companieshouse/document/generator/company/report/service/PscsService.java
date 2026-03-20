package uk.gov.companieshouse.document.generator.company.report.service;

import static uk.gov.companieshouse.document.generator.company.report.CompanyReportDocumentInfoServiceImpl.MODULE_NAME_SPACE;

import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriTemplate;
import uk.gov.companieshouse.api.ApiClient;
import uk.gov.companieshouse.api.error.ApiErrorResponseException;
import uk.gov.companieshouse.api.handler.exception.URIValidationException;
import uk.gov.companieshouse.api.model.psc.PscsApi;
import uk.gov.companieshouse.document.generator.company.report.exception.ServiceException;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;

@Service
public class PscsService {

    private static final Logger LOG = LoggerFactory.getLogger(MODULE_NAME_SPACE);

    private static final AtomicInteger COUNTER = new AtomicInteger(1);


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
        LOG.info("Retrieving PSC for company number (Attempt=%d): %s".formatted(COUNTER.getAndIncrement(), companyNumber));
        ApiClient apiClient = companyReportApiClientService.getApiClient();

        String uri = GET_PSCS_URI.expand(companyNumber).toString();

        try {
            PscsApi response = pageRetrieverService.retrieve(uri, apiClient, ITEMS_PER_PAGE_VALUE);
            return response;

        } catch (ApiErrorResponseException e) {
            LOG.error("*** ERROR: ApiErrorResponseException was raised: %s".formatted(e), e);
            throw new ServiceException("Error retrieving pscs", e);

        } catch (URIValidationException e) {
            LOG.error("*** ERROR: URIValidationException was raised: %s".formatted(e), e);
            throw new ServiceException("Invalid URI for pscs resource", e);

        } catch (Exception e) {
            LOG.error("*** ERROR: Exception was raised: %s".formatted(e), e);
            throw new ServiceException("General exception for pscs resource", e);
        }
    }
}
