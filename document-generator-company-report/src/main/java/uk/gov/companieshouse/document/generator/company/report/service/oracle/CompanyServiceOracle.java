package uk.gov.companieshouse.document.generator.company.report.service.oracle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import uk.gov.companieshouse.api.model.company.CompanyProfileApi;

@Service
public class CompanyServiceOracle {

    private static String COMPANY_PROFILE_URL = "/company/{companyNumber}";
    private static final UriTemplate COMPANY_PROFILE_URI = new UriTemplate(COMPANY_PROFILE_URL);
    
    @Value("${ORACLE_QUERY_API_URL}")
    private String oracleQueryApiUrl;
    
    private RestTemplate restTemplate;
    
    @Autowired
    public CompanyServiceOracle(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    public CompanyProfileApi getCompanyProfile(String companyNumber) {
        String url = COMPANY_PROFILE_URI.expand(companyNumber).toString();
        return restTemplate.getForObject(oracleQueryApiUrl + url, CompanyProfileApi.class);
    }
    
}
