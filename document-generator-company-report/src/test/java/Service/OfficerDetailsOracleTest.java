package Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import uk.gov.companieshouse.api.model.officers.OfficersApi;
import uk.gov.companieshouse.document.generator.company.report.service.oracle.OfficerDetailsServiceOracle;
import uk.gov.companieshouse.environment.EnvironmentReader;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OfficerDetailsOracleTest {

    @Mock
    private RestTemplate restTemplateMock;

    @Mock
    private EnvironmentReader mockEnvironmentReader;

    @InjectMocks
    private OfficerDetailsServiceOracle officerDetailsServiceOracle;

    @Captor
    ArgumentCaptor<String> captor;

    private static final String COMPANY_NUMBER = "00000000";
    private static final String ORACLE_QUERY_API_URL_ENV_VARIABLE = "ORACLE_QUERY_API_URL";

    @Test
    @DisplayName("Test officers api response is not null")
    void testOfficerDetailsServiceOracle() {
        OfficersApi officersApi = new OfficersApi();

        doReturn("oracle/query/api/url").when(mockEnvironmentReader).getMandatoryString(anyString());

        when(restTemplateMock.getForObject(captor.capture(), ArgumentMatchers.eq(OfficersApi.class))).thenReturn(officersApi);

        OfficersApi response = officerDetailsServiceOracle.getOfficerDetails(COMPANY_NUMBER);

        verify(restTemplateMock).getForObject(System.getenv(ORACLE_QUERY_API_URL_ENV_VARIABLE)  + captor.capture(), ArgumentMatchers.eq(OfficersApi.class));
        assertEquals("oracle/query/api/url/company/00000000/officers", captor.getValue());
        assertNotNull(response);

    }

}
