package uk.gov.companieshouse.document.generator.company.report.service.oracle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import uk.gov.companieshouse.api.error.ApiErrorResponseException;
import uk.gov.companieshouse.api.handler.exception.URIValidationException;
import uk.gov.companieshouse.api.model.officers.OfficersApi;
import uk.gov.companieshouse.document.generator.company.report.service.ApiClientService;
import uk.gov.companieshouse.environment.EnvironmentReader;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;
import uk.gov.companieshouse.logging.util.DataMap;

import static uk.gov.companieshouse.document.generator.company.report.CompanyReportDocumentInfoServiceImpl.MODULE_NAME_SPACE;

@Service
public class OfficerDetailsServiceOracle {

    private static final String OFFICER_DETAILS_URL = "/company/{company_number}/officers";
    private static final UriTemplate OFFICER_DETAILS_URI = new UriTemplate(OFFICER_DETAILS_URL);

    private static final Logger LOG = LoggerFactory.getLogger(MODULE_NAME_SPACE);

    private final ApiClientService apiClientService;

    @Autowired
    public OfficerDetailsServiceOracle(ApiClientService apiClientService) {
        this.apiClientService = apiClientService;
    }

    public OfficersApi getOfficerDetails(String companyNumber) {

        DataMap requestDataMap = new DataMap.Builder().
                companyNumber(companyNumber).
                build();
        LOG.info("Retrieving Officer Details", requestDataMap.getLogMap());

        String url = OFFICER_DETAILS_URI.expand(companyNumber).toString();

        try {            
            return apiClientService
                    .getInternalApiClient()
                    .officers()
                    .list(url)
                    .execute()
                    .getData();
        } catch (URIValidationException | ApiErrorResponseException e) {
            var message = String.format("Error Retrieving Officer data for %s", companyNumber);
            LOG.error(message, e, requestDataMap.getLogMap());
            // copying how this worked when using RestTemplate
            return null;
        }

    }

}
