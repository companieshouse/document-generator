package uk.gov.companieshouse.document.generator.company.report.handler;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.model.company.CompanyProfileApi;
import uk.gov.companieshouse.api.model.filinghistory.FilingApi;
import uk.gov.companieshouse.api.model.filinghistory.FilingHistoryApi;
import uk.gov.companieshouse.document.generator.company.report.mapping.mappers.CompanyReportMapper;
import uk.gov.companieshouse.document.generator.company.report.mapping.mappers.recentfilinghistory.ApiToRecentFilingHistoryMapper;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.CompanyReportApiData;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.CompanyReport;
import uk.gov.companieshouse.document.generator.company.report.service.CompanyService;
import uk.gov.companieshouse.document.generator.company.report.service.RecentFilingHistoryService;
import uk.gov.companieshouse.document.generator.company.report.service.oracle.CompanyServiceOracle;
import uk.gov.companieshouse.document.generator.company.report.service.oracle.FilingHistoryServiceOracle;
import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfoResponse;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DissolvedCompanyReportDataHandlerTest {

    @InjectMocks
    private DissolvedCompanyReportDataHandler dissolvedCompanyReportDataHandler;

    @Mock
    private CompanyReport mockCompanyReport;

    @Mock
    private FilingHistoryServiceOracle mockFilingHistoryServiceOracle;

    @Mock
    private CompanyServiceOracle mockCompanyServiceOracle;

    @Mock
    private CompanyReportMapper companyReportMapper;

    @Mock
    private ApiToRecentFilingHistoryMapper apiToRecentFilingHistoryMapper;

    private static final String RESOURCE_URI = "/dissolved-company-number/000064000";
    private static final String REQUEST_ID = "request-id";
    private static final String COMPANY_NAME = "GIRLS SCHOOL TRUST";
    private static final String COMPANY_NUMBER = "00006400";
    private static final String COMPANY_STATUS = "company status";
    private static final String LOCALITY = "locality";
    private static final String FILING_DESCRIPTION = "filing description";
    private static final String FORM_TYPE = "form type";


    @Test
    @DisplayName("Test get company report successful")
    void testGetDocumentInfoSuccessful() throws Exception {
        CompanyProfileApi companyProfileApi = createCompanyProfile();
        FilingHistoryApi filingHistoryApi = createFilingHistory();

        CompanyReportApiData companyReportApiData = new CompanyReportApiData();
        companyReportApiData.setCompanyProfileApi(companyProfileApi);

        when(mockCompanyServiceOracle.getCompanyProfile(any(String.class))).thenReturn(companyProfileApi);
        when(mockFilingHistoryServiceOracle.getFilingHistory(any(String.class))).thenReturn(filingHistoryApi);

        DocumentInfoResponse documentInfoResponse = dissolvedCompanyReportDataHandler.getCompanyReport(COMPANY_NUMBER, REQUEST_ID);

        assertNotNull(documentInfoResponse);

    }

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

}
