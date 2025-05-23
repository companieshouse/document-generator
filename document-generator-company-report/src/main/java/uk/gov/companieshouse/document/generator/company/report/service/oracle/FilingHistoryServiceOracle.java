package uk.gov.companieshouse.document.generator.company.report.service.oracle;

import static uk.gov.companieshouse.document.generator.company.report.CompanyReportDocumentInfoServiceImpl.MODULE_NAME_SPACE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriTemplate;
import uk.gov.companieshouse.api.error.ApiErrorResponseException;
import uk.gov.companieshouse.api.handler.exception.URIValidationException;
import uk.gov.companieshouse.api.model.filinghistory.FilingHistoryApi;
import uk.gov.companieshouse.document.generator.company.report.exception.OracleQueryApiException;
import uk.gov.companieshouse.document.generator.company.report.service.OracleQueryApiClientService;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;
import uk.gov.companieshouse.logging.util.DataMap;

@Service
public class FilingHistoryServiceOracle {

    private static final String FILING_HISTORY_URL = "/company/{companyNumber}/filing-history";
    private static final UriTemplate FILING_HISTORY_URI = new UriTemplate(FILING_HISTORY_URL);

    private static final Logger LOG = LoggerFactory.getLogger(MODULE_NAME_SPACE);

    private final OracleQueryApiClientService oracleQueryApiClientService;

    @Autowired
    public FilingHistoryServiceOracle(OracleQueryApiClientService oracleQueryApiClientService) {
        this.oracleQueryApiClientService = oracleQueryApiClientService;
    }

    public FilingHistoryApi getFilingHistory(String companyNumber) throws OracleQueryApiException{

        String url = FILING_HISTORY_URI.expand(companyNumber).toString();

        DataMap requestDataMap = new DataMap.Builder().
                companyNumber(companyNumber).
                uri(url).
                build();
        LOG.info("Retrieving Filing History", requestDataMap.getLogMap());

        LOG.debug("Base URL used [" + oracleQueryApiClientService.getInternalApiClient().getBasePath() + "]");

        try {
            return oracleQueryApiClientService
                    .getInternalApiClient()
                    .filingHistory()
                    .list(url)
                    .execute()
                    .getData();
        } catch (URIValidationException | ApiErrorResponseException e) {
            var message = String.format("Error Retrieving Filing history data for %s at %s", companyNumber, url);
            LOG.error(message, e, requestDataMap.getLogMap());
            throw new OracleQueryApiException(message, e);
        }
    }
}
