package uk.gov.companieshouse.document.generator.company.report.service.oracle;

import static uk.gov.companieshouse.document.generator.company.report.CompanyReportDocumentInfoServiceImpl.MODULE_NAME_SPACE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriTemplate;
import uk.gov.companieshouse.api.error.ApiErrorResponseException;
import uk.gov.companieshouse.api.handler.exception.URIValidationException;
import uk.gov.companieshouse.api.model.officers.OfficersApi;
import uk.gov.companieshouse.document.generator.company.report.service.OracleQueryApiClientService;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;
import uk.gov.companieshouse.logging.util.DataMap;

@Service
public class OfficerDetailsServiceOracle {

    private static final String OFFICER_DETAILS_URL = "/company/{company_number}/officers";
    private static final UriTemplate OFFICER_DETAILS_URI = new UriTemplate(OFFICER_DETAILS_URL);

    private static final Logger LOG = LoggerFactory.getLogger(MODULE_NAME_SPACE);

    private final OracleQueryApiClientService oracleQueryApiClientService;

    @Autowired
    public OfficerDetailsServiceOracle(OracleQueryApiClientService oracleQueryApiClientService) {
        this.oracleQueryApiClientService = oracleQueryApiClientService;
    }

    public OfficersApi getOfficerDetails(String companyNumber) {

        DataMap requestDataMap = new DataMap.Builder().
                companyNumber(companyNumber).
                build();
        LOG.info("Retrieving Officer Details", requestDataMap.getLogMap());
        LOG.debug("Base URL used [" + oracleQueryApiClientService.getInternalApiClient().getBasePath() + "]");

        String url = OFFICER_DETAILS_URI.expand(companyNumber).toString();
        try {
            return oracleQueryApiClientService
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
