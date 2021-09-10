package uk.gov.companieshouse.document.generator.company.report.service.oracle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;
import uk.gov.companieshouse.api.model.company.CompanyProfileApi;
import uk.gov.companieshouse.environment.EnvironmentReader;

@Service
public class CompanyServiceOracle {

    private static String COMPANY_PROFILE_URL = "/company/{companyNumber}";
    private static final String ORACLE_QUERY_API_URL_ENV_VARIABLE = "ORACLE_QUERY_API_URL";
    private static final UriTemplate COMPANY_PROFILE_URI = new UriTemplate(COMPANY_PROFILE_URL);

    @Value("${ORACLE_QUERY_API_URL}")
    private String oracleQueryApiUrl;

    private RestTemplate restTemplate;
    private EnvironmentReader environmentReader;

    @Autowired
    public CompanyServiceOracle(final RestTemplate restTemplate,
                                final EnvironmentReader environmentReader) {
        this.restTemplate = restTemplate;
        this.environmentReader = environmentReader;
    }

    public CompanyProfileApi getCompanyProfile(String companyNumber) {
        String baseUrl = environmentReader.getMandatoryString((ORACLE_QUERY_API_URL_ENV_VARIABLE));
        String url = COMPANY_PROFILE_URI.expand(companyNumber).toString();
        return restTemplate.getForObject(baseUrl + url, CompanyProfileApi.class);
    }

}