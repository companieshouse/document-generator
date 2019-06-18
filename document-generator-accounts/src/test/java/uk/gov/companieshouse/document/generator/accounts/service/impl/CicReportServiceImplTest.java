package uk.gov.companieshouse.document.generator.accounts.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.error.ApiErrorResponseException;
import uk.gov.companieshouse.api.handler.exception.URIValidationException;
import uk.gov.companieshouse.document.generator.accounts.data.cic.CicReportManager;
import uk.gov.companieshouse.document.generator.accounts.exception.ServiceException;
import uk.gov.companieshouse.document.generator.accounts.mapping.cic.model.CicReport;
import uk.gov.companieshouse.document.generator.accounts.service.CicReportService;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class CicReportServiceImplTest {

    @Mock
    private CicReportManager cicReportManager;

    @InjectMocks
    private CicReportService cicReportService = new CicReportServiceImpl();

    @Mock
    private CicReport cicReport;

    private static final String REQUEST_ID = "requestId";

    private static final String RESOURCE = "resource";

    @Test
    @DisplayName("Get cic report - success")
    void getCicReportSuccess()
            throws ApiErrorResponseException, URIValidationException, ServiceException {

        when(cicReportManager.getCicReport(RESOURCE)).thenReturn(cicReport);

        CicReport returnedCicReport = cicReportService.getCicReport(RESOURCE, REQUEST_ID);

        assertEquals(cicReport, returnedCicReport);
    }

    @Test
    @DisplayName("Get cic report - throws ApiErrorResponseException")
    void getCicReportThrowsApiErrorResponseException()
            throws ApiErrorResponseException, URIValidationException {

        when(cicReportManager.getCicReport(RESOURCE)).thenThrow(ApiErrorResponseException.class);

        assertThrows(ServiceException.class, () -> cicReportService.getCicReport(RESOURCE, REQUEST_ID));
    }

    @Test
    @DisplayName("Get cic report - throws URIValidationException")
    void getCicReportThrowsURIValidationException()
            throws ApiErrorResponseException, URIValidationException {

        when(cicReportManager.getCicReport(RESOURCE)).thenThrow(URIValidationException.class);

        assertThrows(ServiceException.class, () -> cicReportService.getCicReport(RESOURCE, REQUEST_ID));
    }
}
