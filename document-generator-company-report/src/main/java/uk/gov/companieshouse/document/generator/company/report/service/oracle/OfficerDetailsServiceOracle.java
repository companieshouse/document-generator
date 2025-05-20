package uk.gov.companieshouse.document.generator.company.report.service.oracle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import uk.gov.companieshouse.api.model.officers.OfficersApi;
import uk.gov.companieshouse.environment.EnvironmentReader;

@Service
public class OfficerDetailsServiceOracle {

    private static final String OFFICER_DETAILS_URL = "/company/{company_number}/officers";
    private static final String ORACLE_QUERY_API_URL_ENV_VARIABLE = "ORACLE_QUERY_API_URL";
    private static final UriTemplate OFFICER_DETAILS_URI = new UriTemplate(OFFICER_DETAILS_URL);

    private RestTemplate restTemplate;
    private EnvironmentReader environmentReader;

    @Autowired

    public OfficerDetailsServiceOracle(final RestTemplate restTemplate,
                                       final EnvironmentReader environmentReader) {
        this.restTemplate = restTemplate;
        this.environmentReader = environmentReader;
    }
    public OfficersApi getOfficerDetails(String companyNumber) {
        String baseUrl = environmentReader.getMandatoryString(ORACLE_QUERY_API_URL_ENV_VARIABLE);
        String url = OFFICER_DETAILS_URI.expand(companyNumber).toString();

        try {            
            return restTemplate.getForObject(baseUrl + url, OfficersApi.class);
        } catch (RestClientException rce) {
            return null;
        }

    }

}
