package uk.gov.companieshouse.document.generator.company;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.document.generator.company.report.CompanyReportDocumentInfoServiceImpl;
import uk.gov.companieshouse.document.generator.interfaces.exception.DocumentInfoException;
import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfoRequest;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CompanyReportDocumentInfoServiceTest {

    @InjectMocks
    CompanyReportDocumentInfoServiceImpl service;

    @Disabled
    @Test
    @DisplayName("Test service")
    void testService() throws DocumentInfoException {
        service.getDocumentInfo(createDocumenInfoRequest());
    }

    private DocumentInfoRequest createDocumenInfoRequest() {

        DocumentInfoRequest documentInfoRequest = new DocumentInfoRequest();
        documentInfoRequest.setResourceUri("/company-number/0064000");
        return documentInfoRequest;
    }
}
