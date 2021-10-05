package uk.gov.companieshouse.document.generator.company.report.handler;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.model.company.CompanyProfileApi;
import uk.gov.companieshouse.api.model.filinghistory.FilingApi;
import uk.gov.companieshouse.api.model.filinghistory.FilingHistoryApi;
import uk.gov.companieshouse.api.model.officers.CompanyOfficerApi;
import uk.gov.companieshouse.api.model.officers.OfficersApi;
import uk.gov.companieshouse.document.generator.company.report.mapping.mappers.CompanyReportMapper;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.CompanyReportApiData;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.CompanyReport;
import uk.gov.companieshouse.document.generator.company.report.service.oracle.CompanyServiceOracle;
import uk.gov.companieshouse.document.generator.company.report.service.oracle.FilingHistoryServiceOracle;
import uk.gov.companieshouse.document.generator.company.report.service.oracle.OfficerDetailsServiceOracle;
import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfoResponse;

@ExtendWith(MockitoExtension.class)
public class DissolvedCompanyReportDataHandlerTest {

    @Mock
    private FilingHistoryServiceOracle mockFilingHistoryServiceOracle;

    @Mock
    private OfficerDetailsServiceOracle mockOfficerDetailsServiceOracle;

    @Mock
    private CompanyServiceOracle mockCompanyServiceOracle;

    @Mock
    private CompanyReportMapper mockCompanyReportMapper;

    @InjectMocks
    private DissolvedCompanyReportDataHandler dissolvedCompanyReportDataHandler;

    private static final String REQUEST_ID = "request-id";
    private static final String COMPANY_NAME = "GIRLS SCHOOL TRUST";
    private static final String COMPANY_NUMBER = "00006400";
    private static final String FILING_DESCRIPTION = "filing description";
    private static final String FORM_TYPE = "form type";

    private CompanyProfileApi createCompanyProfile() {

        CompanyProfileApi companyProfileApi = new CompanyProfileApi();
        companyProfileApi.setCompanyNumber(COMPANY_NUMBER);
        companyProfileApi.setCompanyName(COMPANY_NAME);

        return companyProfileApi;
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

    private OfficersApi createOfficers() {

        OfficersApi officersApi = new OfficersApi();
        List<CompanyOfficerApi> officerList = new ArrayList<>();

        CompanyOfficerApi companyOfficerApi = new CompanyOfficerApi();
        companyOfficerApi.setAppointedOn(LocalDate.of(2010, 01, 01));
        companyOfficerApi.setName("test name");
        companyOfficerApi.setOccupation("test occupation");

        officerList.add(companyOfficerApi);

        officersApi.setItems(officerList);

        return officersApi;
    }

    @Test
    @DisplayName("Test get dissolved company report successful")
    void testGetDocumentInfoSuccessful() throws Exception {
        CompanyProfileApi companyProfileApi = createCompanyProfile();
        FilingHistoryApi filingHistoryApi = createFilingHistory();
        OfficersApi officersApi = createOfficers();

        when(mockCompanyServiceOracle.getCompanyProfile(any(String.class))).thenReturn(companyProfileApi);
        when(mockFilingHistoryServiceOracle.getFilingHistory(any(String.class))).thenReturn(filingHistoryApi);
        when(mockOfficerDetailsServiceOracle.getOfficerDetails(any(String.class))).thenReturn(officersApi);
        when(mockCompanyReportMapper.mapCompanyReport(any(CompanyReportApiData.class), anyString(), anyString())).thenReturn(new CompanyReport());

        DocumentInfoResponse documentInfoResponse = dissolvedCompanyReportDataHandler.getCompanyReport(COMPANY_NUMBER,
                REQUEST_ID);

        assertNotNull(documentInfoResponse);

    }

}
