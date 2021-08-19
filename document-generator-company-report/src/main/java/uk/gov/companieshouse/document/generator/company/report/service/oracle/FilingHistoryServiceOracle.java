package uk.gov.companieshouse.document.generator.company.report.service.oracle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import uk.gov.companieshouse.api.model.filinghistory.FilingHistoryApi;

@Service
public class FilingHistoryServiceOracle {
    
    private static String FILING_HISTORY_URL = "/company/{companyNumber}/filing-history";
    private static final UriTemplate FILING_HISTORY_URI = new UriTemplate(FILING_HISTORY_URL);
    
    @Value("${ORACLE_QUERY_API_URL}")
    private String oracleQueryApiUrl;
    
    private RestTemplate restTemplate;
    
    @Autowired
    public FilingHistoryServiceOracle(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    public FilingHistoryApi getFilingHistory(String companyNumber) {
        String url = FILING_HISTORY_URI.expand(companyNumber).toString();
        FilingHistoryApi response = restTemplate.getForObject(url, FilingHistoryApi.class);
        
        return response;
    }
}
