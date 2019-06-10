package uk.gov.companieshouse.document.generator.company.report.handler;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.model.company.CompanyProfileApi;
import uk.gov.companieshouse.document.generator.accounts.service.CompanyService;
import uk.gov.companieshouse.document.generator.company.report.mapping.mappers.CompanyReportMapper;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.CompanyReportApiData;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.CompanyReport;
import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfoResponse;

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

    @InjectMocks
    private CompanyReportDataHandler companyReportDataHandler;

    private static final String RESOURCE_URI = "/company-number/000064000";
    private static final String REQUEST_ID = "request-id";

    @Test
    @DisplayName("Test get company report successful")
    void testGetDocumentInfoSuccessful() throws Exception {

        CompanyProfileApi companyProfileApi = createCompanyProfile();
        CompanyReportApiData companyReportApiData = new CompanyReportApiData();

        companyReportApiData.setCompanyProfileApi(companyProfileApi);

        when(mockCompanyService.getCompanyProfile(any(String.class))).thenReturn(companyProfileApi);

        DocumentInfoResponse documentInfoResponse = companyReportDataHandler.getCompanyReport(RESOURCE_URI, REQUEST_ID);

        assertNotNull(documentInfoResponse);

        //TODO - This test needs to be revisited when we have access to the mappers/companyreport
    }

    private CompanyProfileApi createCompanyProfile() {

        CompanyProfileApi companyProfileApi = new CompanyProfileApi();
        companyProfileApi.setCompanyNumber("00006400");
        companyProfileApi.setCompanyName("GIRLS SCHOOL TRUST");

        return companyProfileApi;
    }
}
