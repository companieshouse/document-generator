package uk.gov.companieshouse.document.generator.company.report.service.oracle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;
import uk.gov.companieshouse.api.model.filinghistory.FilingHistoryApi;
import uk.gov.companieshouse.environment.EnvironmentReader;

@Service
public class FilingHistoryServiceOracle {

    private static final String FILING_HISTORY_URL = "/company/{companyNumber}/filing-history";
    private static final String ORACLE_QUERY_API_URL_ENV_VARIABLE = "ORACLE_QUERY_API_URL";
    private static final UriTemplate FILING_HISTORY_URI = new UriTemplate(FILING_HISTORY_URL);
    
    private RestTemplate restTemplate;
    private EnvironmentReader environmentReader;

    @Autowired
    public FilingHistoryServiceOracle(final RestTemplate restTemplate,
                                      final EnvironmentReader environmentReader) {
        this.restTemplate = restTemplate;
        this.environmentReader = environmentReader;
    }
    
    public FilingHistoryApi getFilingHistory(String companyNumber) {
        String baseUrl = environmentReader.getMandatoryString(ORACLE_QUERY_API_URL_ENV_VARIABLE);
        String url = FILING_HISTORY_URI.expand(companyNumber).toString();

        return restTemplate.getForObject(baseUrl + url, FilingHistoryApi.class);
    }
}
