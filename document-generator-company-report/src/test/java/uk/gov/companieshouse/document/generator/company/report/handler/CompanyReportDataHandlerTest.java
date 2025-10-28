package uk.gov.companieshouse.document.generator.company.report.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

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
import uk.gov.companieshouse.api.model.charges.ChargeApi;
import uk.gov.companieshouse.api.model.charges.ChargesApi;
import uk.gov.companieshouse.api.model.company.CompanyProfileApi;
import uk.gov.companieshouse.api.model.filinghistory.FilingApi;
import uk.gov.companieshouse.api.model.filinghistory.FilingHistoryApi;
import uk.gov.companieshouse.api.model.insolvency.CaseApi;
import uk.gov.companieshouse.api.model.insolvency.CaseTypeApi;
import uk.gov.companieshouse.api.model.insolvency.InsolvencyApi;
import uk.gov.companieshouse.api.model.officers.OfficersApi;
import uk.gov.companieshouse.api.model.psc.PscsApi;
import uk.gov.companieshouse.api.model.registers.CompanyRegistersApi;
import uk.gov.companieshouse.api.model.registers.RegisterApi;
import uk.gov.companieshouse.api.model.registers.RegisterItemsApi;
import uk.gov.companieshouse.api.model.registers.RegistersApi;
import uk.gov.companieshouse.api.model.statements.StatementApi;
import uk.gov.companieshouse.api.model.statements.StatementsApi;
import uk.gov.companieshouse.api.model.ukestablishments.UkEstablishmentsApi;
import uk.gov.companieshouse.api.model.ukestablishments.UkEstablishmentsItemsApi;
import uk.gov.companieshouse.document.generator.company.report.mapping.mappers.CompanyReportMapper;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.CompanyReportApiData;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.CompanyReport;
import uk.gov.companieshouse.document.generator.company.report.service.ChargesService;
import uk.gov.companieshouse.document.generator.company.report.service.CompanyService;
import uk.gov.companieshouse.document.generator.company.report.service.FilingHistoryService;
import uk.gov.companieshouse.document.generator.company.report.service.InsolvencyService;
import uk.gov.companieshouse.document.generator.company.report.service.OfficerService;
import uk.gov.companieshouse.document.generator.company.report.service.PscsService;
import uk.gov.companieshouse.document.generator.company.report.service.RegistersService;
import uk.gov.companieshouse.document.generator.company.report.service.StatementsService;
import uk.gov.companieshouse.document.generator.company.report.service.UkEstablishmentService;
import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfoResponse;

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

    @Mock
    private UkEstablishmentService mockUkEstablishmentService;

    @Mock
    private FilingHistoryService mockFilingHistoryService;

    @Mock
    private RegistersService mockRegistersService;

    @Mock
    private InsolvencyService mockInsolvencyService;

    @Mock
    private ChargesService mockChargesService;


    @InjectMocks
    private CompanyReportDataHandler companyReportDataHandler;

    private static final String RESOURCE_URI = "/company-number/000064000";
    private static final String REQUEST_ID = "request-id";
    private static final String COMPANY_NAME = "company name";
    private static final String COMPANY_NUMBER = "FC000005";
    private static final String COMPANY_STATUS = "company status";
    private static final String LOCALITY = "locality";
    private static final String FILING_DESCRIPTION = "filing description";
    private static final String FORM_TYPE = "form type";

    @Test
    @DisplayName("Test get company report successful")
    void testGetDocumentInfoSuccessful() throws Exception {
        CompanyProfileApi companyProfileApi = createCompanyProfile();
        PscsApi pscsApi = createPscsApi();
        OfficersApi officersApi = createOfficers();
        UkEstablishmentsApi ukEstablishmentsApi = createUkEstablishment();
        FilingHistoryApi filingHistoryApi = createFilingHistory();
        StatementsApi statementsApi = createStatementsApi();
        CompanyRegistersApi companyRegistersApi = createCompanyRegisters();
        InsolvencyApi insolvencyApi = createInsolvencyApi();
        ChargesApi chargesApi = createChargesApi();

        CompanyReportApiData companyReportApiData = new CompanyReportApiData();
        companyReportApiData.setCompanyProfileApi(companyProfileApi);

        when(mockCompanyService.getCompanyProfile(any(String.class))).thenReturn(companyProfileApi);
        when(mockPscService.getPscs(any(String.class))).thenReturn(pscsApi);
        when(mockOfficerService.getOfficers(any(String.class))).thenReturn(officersApi);
        when(mockUkEstablishmentService.getUkEstablishments(any(String.class))).thenReturn(ukEstablishmentsApi);
        when(mockFilingHistoryService.getFilingHistory(any(String.class))).thenReturn(filingHistoryApi);
        when(mockCompanyReportMapper.mapCompanyReport(any(CompanyReportApiData.class), anyString(), anyString())).thenReturn(new CompanyReport());
        when(mockStatementsService.getStatements(any(String.class))).thenReturn(statementsApi);
        when(mockRegistersService.getCompanyRegisters(any(String.class))).thenReturn(companyRegistersApi);
        when(mockInsolvencyService.getInsolvency(anyString())).thenReturn(insolvencyApi);
        when(mockChargesService.getCharges(any(String.class))).thenReturn(chargesApi);

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
        links.put("uk_establishments", "/uk-establishments");
        links.put("filing_history", "/filing-history");
        links.put("persons_with_significant_control_statements", "/persons_with_significant_control_statements");
        links.put("registers", "/registers");
        links.put("insolvency", "/insolvency");
        links.put("charges", "/charges");

        companyProfileApi.setLinks(links);

        return companyProfileApi;
    }

    private OfficersApi createOfficers() {
        OfficersApi officersApi = new OfficersApi();
        officersApi.setActiveCount(1L);

        return officersApi;
    }

    private UkEstablishmentsApi createUkEstablishment() {

        UkEstablishmentsApi ukEstablishmentsApi = new UkEstablishmentsApi();
        List<UkEstablishmentsItemsApi> ukEstablishmentsItemsApiList = new ArrayList<>();

        UkEstablishmentsItemsApi ukEstablishmentsItemsApi = new UkEstablishmentsItemsApi();
        ukEstablishmentsItemsApi.setCompanyName(COMPANY_NAME);
        ukEstablishmentsItemsApi.setCompanyNumber(COMPANY_NUMBER);
        ukEstablishmentsItemsApi.setLocality(LOCALITY);
        ukEstablishmentsItemsApi.setCompanyStatus(COMPANY_STATUS);

        ukEstablishmentsItemsApiList.add(ukEstablishmentsItemsApi);
        ukEstablishmentsApi.setItems(ukEstablishmentsItemsApiList);

        return ukEstablishmentsApi;
    }

    private FilingHistoryApi createFilingHistory() {

        FilingHistoryApi filingHistoryApi = new FilingHistoryApi();
        List<FilingApi> filingApiList = new ArrayList<>();

        FilingApi filingApi = new FilingApi();
        filingApi.setDate(LocalDate.of(1999, 01, 01));
        filingApi.setDescription(FILING_DESCRIPTION);
        filingApi.setType(FORM_TYPE);

        filingApiList.add(filingApi);

        filingHistoryApi.setItems(filingApiList);

        return filingHistoryApi;
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

    private CompanyRegistersApi createCompanyRegisters() {

        CompanyRegistersApi companyRegistersApi = new CompanyRegistersApi();
        RegistersApi registersApi = new RegistersApi();
        RegisterApi registerApi = new RegisterApi();
        List<RegisterItemsApi> registerItemsApisList = new ArrayList<>();
        RegisterItemsApi registerItem = new RegisterItemsApi();

        registerItem.setMovedOn(LocalDate.of(2018, 12, 13));
        registerItem.setRegisterMovedTo("register moved to");

        companyRegistersApi.setRegisters(registersApi);
        registersApi.setDirectorsRegister(registerApi);
        registersApi.setUsualResidentialAddressRegister(registerApi);
        registersApi.setPscRegister(registerApi);
        registersApi.setSecretariesRegister(registerApi);
        registersApi.setMembersRegister(registerApi);
        registersApi.setLlpMembersRegister(registerApi);
        registersApi.setLlpUsualResidentialAddressRegister(registerApi);
        registerApi.setItems(registerItemsApisList);
        registerItemsApisList.add(registerItem);

        return companyRegistersApi;
    }

    private InsolvencyApi createInsolvencyApi() {

        InsolvencyApi insolvencyApi = new InsolvencyApi();

        List<CaseApi> caseApiList = new ArrayList<>();
        CaseApi caseApi = new CaseApi();
        caseApi.setPractitioners(new ArrayList<>());
        caseApi.setDates(new ArrayList<>());
        caseApi.setNumber(1L);
        caseApi.setType(CaseTypeApi.ADMINISTRATION_ORDER);

        caseApiList.add(caseApi);
        insolvencyApi.setCases(caseApiList);

        return insolvencyApi;
    }

    private ChargesApi createChargesApi() {

        ChargesApi chargesApi = new ChargesApi();

        chargesApi.setTotalCount(1L);
        chargesApi.setSatisfiedCount(1L);
        chargesApi.setPartSatisfiedCount(1L);

        List<ChargeApi> items = new ArrayList<>();
        ChargeApi chargeApi = new ChargeApi();
        chargeApi.setStatus("status");
        chargesApi.setItems(items);

        return chargesApi;
    }
}
