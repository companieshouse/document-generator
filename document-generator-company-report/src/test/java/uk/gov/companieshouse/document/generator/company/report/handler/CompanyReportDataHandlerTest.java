package uk.gov.companieshouse.document.generator.company.report.handler;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.model.charges.ChargesApi;
import uk.gov.companieshouse.api.model.company.CompanyProfileApi;
import uk.gov.companieshouse.api.model.filinghistory.FilingHistoryApi;
import uk.gov.companieshouse.api.model.insolvency.InsolvencyApi;
import uk.gov.companieshouse.api.model.officers.OfficersApi;
import uk.gov.companieshouse.api.model.psc.PscsApi;
import uk.gov.companieshouse.api.model.registers.CompanyRegistersApi;
import uk.gov.companieshouse.api.model.statements.StatementsApi;
import uk.gov.companieshouse.api.model.ukestablishments.UkEstablishmentsApi;
import uk.gov.companieshouse.document.generator.company.report.data.CompanyReportDataManager;
import uk.gov.companieshouse.document.generator.company.report.exception.ApiDataException;
import uk.gov.companieshouse.document.generator.company.report.exception.HandlerException;
import uk.gov.companieshouse.document.generator.company.report.mapping.mappers.CompanyReportMapper;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.CompanyReportApiData;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.CompanyReport;
import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfoResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CompanyReportDataHandlerTest {

    @Mock
    private CompanyReport mockCompanyReport;

    @Mock
    private CompanyReportMapper mockCompanyReportMapper;

    @Mock
    private CompanyReportDataManager mockCompanyReportDataManager;

    @InjectMocks
    private CompanyReportDataHandler companyReportDataHandler;

    private static final String RESOURCE_URI = "/company-number/000064000";
    private static final String REQUEST_ID = "request-id";

    @Test
    @DisplayName("Test get company report successful")
    void testGetDocumentInfoSuccessful() throws ApiDataException, HandlerException {

        when(mockCompanyReportMapper.mapCompanyReport(any(CompanyReportApiData.class), any(RequestParameters.class))).thenReturn(new CompanyReport());
        when(mockCompanyReportDataManager.getCompanyReportData(any(RequestParameters.class))).thenReturn(createCompanyReportApiData());

        DocumentInfoResponse documentInfoResponse = companyReportDataHandler.getCompanyReport(RESOURCE_URI, REQUEST_ID);

        assertNotNull(documentInfoResponse);
    }

    @Test
    @DisplayName("Test Exception thrown when company report data manager throws ApiDataException")
    void testWhenApiDataExceptionThrown() throws ApiDataException {

        when(mockCompanyReportDataManager.getCompanyReportData(any(RequestParameters.class))).thenThrow(ApiDataException.class);

        assertThrows(HandlerException.class,
            () -> companyReportDataHandler.getCompanyReport(RESOURCE_URI, RESOURCE_URI));
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

    private CompanyReportApiData createCompanyReportApiData() {

        CompanyReportApiData companyReportApiData = new CompanyReportApiData();
        companyReportApiData.setCompanyProfileApi(createCompanyProfile());
        companyReportApiData.setPscsApi(new PscsApi());
        companyReportApiData.setOfficersApi(new OfficersApi());
        companyReportApiData.setUkEstablishmentsApi(new UkEstablishmentsApi());
        companyReportApiData.setFilingHistoryApi(new FilingHistoryApi());
        companyReportApiData.setStatementsApi(new StatementsApi());
        companyReportApiData.setCompanyRegistersApi(new CompanyRegistersApi());
        companyReportApiData.setInsolvencyApi(new InsolvencyApi());
        companyReportApiData.setChargesApi(new ChargesApi());

        return companyReportApiData;
    }

    private CompanyProfileApi createCompanyProfile() {

        CompanyProfileApi companyProfileApi = new CompanyProfileApi();
        companyProfileApi.setCompanyNumber("00006400");
        companyProfileApi.setCompanyName("GIRLS SCHOOL TRUST");

        return companyProfileApi;
    }
}
