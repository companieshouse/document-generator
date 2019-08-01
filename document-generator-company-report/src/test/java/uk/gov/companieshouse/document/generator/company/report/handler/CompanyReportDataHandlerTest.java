package uk.gov.companieshouse.document.generator.company.report.handler;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.model.company.CompanyProfileApi;
import uk.gov.companieshouse.api.model.psc.PscsApi;
import uk.gov.companieshouse.api.model.officers.OfficersApi;
import uk.gov.companieshouse.api.model.statements.StatementApi;
import uk.gov.companieshouse.api.model.statements.StatementsApi;
import uk.gov.companieshouse.document.generator.company.report.mapping.mappers.CompanyReportMapper;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.CompanyReportApiData;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.CompanyReport;
import uk.gov.companieshouse.document.generator.company.report.service.CompanyService;
import uk.gov.companieshouse.document.generator.company.report.service.PscsService;
import uk.gov.companieshouse.document.generator.company.report.service.OfficerService;
import uk.gov.companieshouse.document.generator.company.report.service.StatementsService;
import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfoResponse;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CompanyReportDataHandlerTest {

    @Mock
    private CompanyService mockCompanyService;

    @Mock
    private CompanyReportMapper mockCompanyReportMapper;

    @Mock
    private CompanyReport mockCompanyReport;

    @Mock
    private PscsService mockPscService;

    @Mock
    private StatementsService mockStatementsService;

    @Mock
    private OfficerService mockOfficerService;


    @InjectMocks
    private CompanyReportDataHandler companyReportDataHandler;

    private static final String RESOURCE_URI = "/company-number/000064000";
    private static final String REQUEST_ID = "request-id";

    @Test
    @DisplayName("Test get company report successful")
    void testGetDocumentInfoSuccessful() throws Exception {

        CompanyProfileApi companyProfileApi = createCompanyProfile();
        PscsApi pscsApi = createPscsApi();
        OfficersApi officersApi = createOfficers();
        StatementsApi statementsApi = createStatementsApi();

        CompanyReportApiData companyReportApiData = new CompanyReportApiData();
        companyReportApiData.setCompanyProfileApi(companyProfileApi);

        when(mockCompanyService.getCompanyProfile(any(String.class))).thenReturn(companyProfileApi);
        when(mockPscService.getPscs(any(String.class))).thenReturn(pscsApi);
        when(mockOfficerService.getOfficers(any(String.class))).thenReturn(officersApi);
        when(mockStatementsService.getStatements(any(String.class))).thenReturn(statementsApi);
        when(mockCompanyReportMapper.mapCompanyReport(any(CompanyReportApiData.class))).thenReturn(new CompanyReport());

        DocumentInfoResponse documentInfoResponse = companyReportDataHandler.getCompanyReport(RESOURCE_URI, REQUEST_ID);

        assertNotNull(documentInfoResponse);

        //TODO - This test needs to be revisited when we have access to the mappers/companyreport
    }

    @Test
    @DisplayName("Test regex gets company number containing letters from url")
    void testRegexGetsCompanyNumberContainingCharacters() {

        String result = companyReportDataHandler.getCompanyNumberFromUri("/company-number/CV2234554");

        assertEquals("CV2234554",result);
    }

    @Test
    @DisplayName("Test regex gets company number containing numeric only from url")
    void testRegexGetsCompanyNumberContainingNumericOnly() {

        String result = companyReportDataHandler.getCompanyNumberFromUri("/company-number/112234554");

        assertEquals("112234554",result);
    }

    private PscsApi createPscsApi() {
        PscsApi pscsApi = new PscsApi();
        pscsApi.setActiveCount(1L);

        return pscsApi;
    }

    private CompanyProfileApi createCompanyProfile() {

        CompanyProfileApi companyProfileApi = new CompanyProfileApi();
        companyProfileApi.setCompanyNumber("00006400");
        companyProfileApi.setCompanyName("GIRLS SCHOOL TRUST");

        Map<String, String> links = new HashMap<>();

        links.put("persons_with_significant_control", "/persons-with-significant-control");
        links.put("officers", "/officers");
        links.put("persons_with_significant_control_statements", "/persons_with_significant_control_statements");

        companyProfileApi.setLinks(links);

        return companyProfileApi;
    }

    private OfficersApi createOfficers() {
        OfficersApi officersApi = new OfficersApi();
        officersApi.setActiveCount(1L);

        return officersApi;
    }

    private StatementsApi createStatementsApi() {
        StatementsApi statementsApi = new StatementsApi();
        statementsApi.setActiveCount(3L);

        List<StatementApi> statementApiList = new ArrayList<>();
        StatementApi statementApi = new StatementApi();
        statementApi.setStatement("test data");
        statementApi.setCeasedOn(LocalDate.of(2018, 12, 13));
        statementApiList.add(statementApi);
        statementsApi.setItems(statementApiList);

        return statementsApi;
    }
}
