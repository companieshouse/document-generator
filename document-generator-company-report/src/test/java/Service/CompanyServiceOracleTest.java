package Service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;
import uk.gov.companieshouse.api.model.company.CompanyProfileApi;
import uk.gov.companieshouse.api.model.filinghistory.FilingHistoryApi;
import uk.gov.companieshouse.document.generator.company.report.service.oracle.CompanyServiceOracle;
import uk.gov.companieshouse.document.generator.company.report.service.oracle.FilingHistoryServiceOracle;
import uk.gov.companieshouse.environment.EnvironmentReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CompanyServiceOracleTest {

    @Mock
    private RestTemplate restTemplateMock;

    @Mock
    private EnvironmentReader mockEnvironmentReader;

    @InjectMocks
    private CompanyServiceOracle companyServiceOracle;

    @Captor
    ArgumentCaptor<String> captor;

    private static final String COMPANY_NUMBER = "00000000";
    private static final String ORACLE_QUERY_API_URL_ENV_VARIABLE = "ORACLE_QUERY_API_URL";


    @Test
    @DisplayName("Test company service oracle response is not null")
    void testCompanyServiceOracle() {
        CompanyProfileApi companyProfileApi = new CompanyProfileApi();

        doReturn("oracle/query/api/url").when(mockEnvironmentReader).getMandatoryString(anyString());

        when(restTemplateMock.getForObject(captor.capture(), ArgumentMatchers.eq(CompanyProfileApi.class))).thenReturn(companyProfileApi);

        CompanyProfileApi response = companyServiceOracle.getCompanyProfile(COMPANY_NUMBER);

        verify(restTemplateMock).getForObject(System.getenv("ORACLE_QUERY_API_URL") + captor.capture(), ArgumentMatchers.eq(CompanyProfileApi.class));
        assertEquals("oracle/query/api/url/company/00000000", captor.getValue());
        assertNotNull(response);

    }
}
