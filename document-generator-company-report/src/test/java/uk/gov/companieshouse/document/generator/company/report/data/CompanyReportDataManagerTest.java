package uk.gov.companieshouse.document.generator.company.report.data;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
import uk.gov.companieshouse.document.generator.company.report.exception.ApiDataException;
import uk.gov.companieshouse.document.generator.company.report.exception.ServiceException;
import uk.gov.companieshouse.document.generator.company.report.handler.RequestParameters;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.CompanyReportApiData;
import uk.gov.companieshouse.document.generator.company.report.service.ChargesService;
import uk.gov.companieshouse.document.generator.company.report.service.CompanyService;
import uk.gov.companieshouse.document.generator.company.report.service.InsolvencyService;
import uk.gov.companieshouse.document.generator.company.report.service.OfficerService;
import uk.gov.companieshouse.document.generator.company.report.service.PscsService;
import uk.gov.companieshouse.document.generator.company.report.service.RecentFilingHistoryService;
import uk.gov.companieshouse.document.generator.company.report.service.RegistersService;
import uk.gov.companieshouse.document.generator.company.report.service.StatementsService;
import uk.gov.companieshouse.document.generator.company.report.service.UkEstablishmentService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CompanyReportDataManagerTest {

    @Mock
    private CompanyService mockCompanyService;

    @Mock
    private PscsService mockPscsService;

    @Mock
    private OfficerService mockOfficerService;

    @Mock
    private UkEstablishmentService mockUkEstablishmentService;

    @Mock
    private RecentFilingHistoryService mockRecentFilingHistoryService;

    @Mock
    private StatementsService mockStatementsService;

    @Mock
    private InsolvencyService mockInsolvencyService;

    @Mock
    private RegistersService mockRegistersService;

    @Mock
    private ChargesService mockChargesService;

    @InjectMocks
    private CompanyReportDataManager companyReportDataManager;

    private static final String COMPANY_NUMBER = "CN001122";
    private static final String FILING_DESCRIPTION = "filing description";
    private static final String FORM_TYPE = "form type";

    @Test
    @DisplayName("Test get company report data is successful")
    void testCompanyReportDataSuccessful() throws ServiceException, ApiDataException {

        when(mockCompanyService.getCompanyProfile(anyString())).thenReturn(createCompanyProfileApi());
        when(mockPscsService.getPscs(anyString())).thenReturn(new PscsApi());
        when(mockOfficerService.getOfficers(anyString())).thenReturn(new OfficersApi());
        when(mockUkEstablishmentService.getUkEstablishments(anyString())).thenReturn(new UkEstablishmentsApi());
        when(mockRecentFilingHistoryService.getFilingHistory(anyString())).thenReturn(createFilingHistoryApi());
        when(mockStatementsService.getStatements(anyString())).thenReturn(createStatementsApi());
        when(mockInsolvencyService.getInsolvency(anyString())).thenReturn(createInsolvencyApi());
        when(mockRegistersService.getCompanyRegisters(anyString())).thenReturn(createCompanyRegistersApi());
        when(mockChargesService.getCharges(anyString())).thenReturn(new ChargesApi());

        CompanyReportApiData companyReportApiData =
            companyReportDataManager.getCompanyReportData(createRequestParameters());

        assertNotNull(companyReportApiData);
        assertNotNull(companyReportApiData.getCompanyProfileApi());
        assertNotNull(companyReportApiData.getPscsApi());
        assertNotNull(companyReportApiData.getOfficersApi());
        assertNotNull(companyReportApiData.getUkEstablishmentsApi());
        assertNotNull(companyReportApiData.getFilingHistoryApi());
        assertNotNull(companyReportApiData.getStatementsApi());
        assertNotNull(companyReportApiData.getInsolvencyApi());
        assertNotNull(companyReportApiData.getCompanyRegistersApi());
        assertNotNull(companyReportApiData.getChargesApi());
    }

    @Test
    @DisplayName("Test service exception thrown from all but company profile")
    void testServiceExceptionThrownNotCompanyProfile() throws ServiceException, ApiDataException {

        when(mockCompanyService.getCompanyProfile(anyString())).thenReturn(createCompanyProfileApi());
        when(mockPscsService.getPscs(anyString())).thenThrow(ServiceException.class);
        when(mockOfficerService.getOfficers(anyString())).thenThrow(ServiceException.class);
        when(mockUkEstablishmentService.getUkEstablishments(anyString())).thenThrow(ServiceException.class);
        when(mockRecentFilingHistoryService.getFilingHistory(anyString())).thenThrow(ServiceException.class);
        when(mockStatementsService.getStatements(anyString())).thenThrow(ServiceException.class);
        when(mockInsolvencyService.getInsolvency(anyString())).thenThrow(ServiceException.class);
        when(mockRegistersService.getCompanyRegisters(anyString())).thenThrow(ServiceException.class);
        when(mockChargesService.getCharges(anyString())).thenThrow(ServiceException.class);

        CompanyReportApiData companyReportApiData =
            companyReportDataManager.getCompanyReportData(createRequestParameters());

        assertNotNull(companyReportApiData);
        assertNotNull(companyReportApiData.getCompanyProfileApi());
        assertNull(companyReportApiData.getPscsApi());
        assertNull(companyReportApiData.getOfficersApi());
        assertNull(companyReportApiData.getUkEstablishmentsApi());
        assertNull(companyReportApiData.getFilingHistoryApi());
        assertNull(companyReportApiData.getStatementsApi());
        assertNull(companyReportApiData.getInsolvencyApi());
        assertNull(companyReportApiData.getCompanyRegistersApi());
        assertNull(companyReportApiData.getChargesApi());
    }

    @Test
    @DisplayName("Test service exception thrown from company profile")
    void testServiceExceptionThrownFromCompanyProfile() throws ServiceException {

        when(mockCompanyService.getCompanyProfile(anyString())).thenThrow(ServiceException.class);

        assertThrows(ApiDataException.class,
            () -> companyReportDataManager.getCompanyReportData(createRequestParameters()));
    }

    private CompanyProfileApi createCompanyProfileApi() {

        CompanyProfileApi companyProfileApi = new CompanyProfileApi();
        companyProfileApi.setCompanyNumber(COMPANY_NUMBER);
        companyProfileApi.setCompanyName("Company Name test");

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

    private FilingHistoryApi createFilingHistoryApi() {

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

    private CompanyRegistersApi createCompanyRegistersApi() {

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


    private RequestParameters createRequestParameters() {

        RequestParameters requestParameters = new RequestParameters();
        requestParameters.setResourceUri("/resource/test");
        requestParameters.setRequestId("request_id");
        requestParameters.setCompanyNumber(COMPANY_NUMBER);

        return requestParameters;
    }
}
