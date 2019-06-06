package uk.gov.companieshouse.document.generator.company.report;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.document.generator.company.report.exception.HandlerException;
import uk.gov.companieshouse.document.generator.company.report.handler.CompanyReportDataHandler;
import uk.gov.companieshouse.document.generator.interfaces.exception.DocumentInfoException;
import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfoRequest;
import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfoResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CompanyReportDocumentInfoServiceImplTest {

    @Mock
    private DocumentInfoRequest mockDocumentInfoRequest;

    @Mock
    private CompanyReportDataHandler mockCompanyReportDataHandler;

    @Mock
    private DocumentInfoResponse mockDocumentInfoResponse;

    @InjectMocks
    CompanyReportDocumentInfoServiceImpl service;

    private static final String RESOURCE_URI = "/company-number/0064000";
    private static final String REQUEST_ID = "request-id";

    @Test
    @DisplayName("Test get document info successful")
    void testGetDocumentInfoSuccessful() throws Exception {

        when(mockDocumentInfoRequest.getResourceUri()).thenReturn(RESOURCE_URI);
        when(mockDocumentInfoRequest.getRequestId()).thenReturn(REQUEST_ID);

        when(mockCompanyReportDataHandler.getCompanyReport(RESOURCE_URI, REQUEST_ID)).thenReturn(createDocumentInfoResponse());


        assertNotNull(service.getDocumentInfo(mockDocumentInfoRequest));
        assertEquals(createDocumentInfoResponse(), service.getDocumentInfo(mockDocumentInfoRequest));
    }

    @Test
    @DisplayName("Test get document information throws handler exception")
    void testGetDocumentInfoThrowsHandlerException() throws Exception {

        when(mockDocumentInfoRequest.getResourceUri()).thenReturn(RESOURCE_URI);
        when(mockDocumentInfoRequest.getRequestId()).thenReturn(REQUEST_ID);

        when(mockCompanyReportDataHandler.getCompanyReport(RESOURCE_URI, REQUEST_ID))
            .thenThrow(new HandlerException("error"));

        assertThrows(DocumentInfoException.class, () ->
            service.getDocumentInfo(mockDocumentInfoRequest));
    }

    private DocumentInfoResponse createDocumentInfoResponse() {
        DocumentInfoResponse documentInfoResponse = new DocumentInfoResponse();
        documentInfoResponse.setData("testData");

        return documentInfoResponse;
    }
}
