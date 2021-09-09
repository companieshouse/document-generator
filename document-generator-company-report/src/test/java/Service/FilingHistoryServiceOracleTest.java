package Service;


import jdk.nashorn.internal.objects.annotations.Setter;
import org.junit.Rule;
import org.junit.contrib.java.lang.system.EnvironmentVariables;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;
import uk.gov.companieshouse.api.model.filinghistory.FilingHistoryApi;
import uk.gov.companieshouse.document.generator.company.report.service.oracle.FilingHistoryServiceOracle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FilingHistoryServiceOracleTest {

    @Mock
    private RestTemplate restTemplateMock;

    @InjectMocks
    private FilingHistoryServiceOracle filingHistoryServiceOracle;

    @Captor
    ArgumentCaptor<String> captor;

    @Rule
    public final EnvironmentVariables environmentVariables
            = new EnvironmentVariables();

    @Test
    @DisplayName("Test filing history api response is not null")
    void testFilingHistoryServiceOracle(){
        environmentVariables.set("ORACLE_QUERY_API_URL", "oracle/query/api/url");

        String companyNumber = "00006400";

        FilingHistoryApi filingHistoryApi = new FilingHistoryApi();
        when(restTemplateMock.getForObject(captor.capture(), ArgumentMatchers.eq(FilingHistoryApi.class))).thenReturn(filingHistoryApi);
        FilingHistoryApi response = filingHistoryServiceOracle.getFilingHistory(companyNumber);

        verify(restTemplateMock).getForObject(System.getenv("ORACLE_QUERY_API_URL") + captor.capture(), ArgumentMatchers.eq(FilingHistoryApi.class));
        assertEquals("oracle/query/api/url/company/00006400/filing-history", captor.getValue());
        assertNotNull(response);

    }


}
