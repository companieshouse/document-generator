package uk.gov.companieshouse.document.generator.core.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.document.generator.core.document.render.RenderDocumentRequestHandler;
import uk.gov.companieshouse.document.generator.core.document.render.models.RenderDocumentRequest;
import uk.gov.companieshouse.document.generator.core.document.render.models.RenderDocumentResponse;
import uk.gov.companieshouse.document.generator.core.service.impl.DocumentGeneratorServiceImpl;
import uk.gov.companieshouse.document.generator.core.service.models.DocumentRequest;
import uk.gov.companieshouse.document.generator.core.service.models.DocumentResponse;
import uk.gov.companieshouse.document.generator.interfaces.DocumentInfoService;
import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfoRequest;
import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfoResponse;
import uk.gov.companieshouse.environment.EnvironmentReader;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DocumentGeneratorServiceTest {

    @Mock
    private DocumentInfoService mockDocumentInfoService;

    @Mock
    private RenderDocumentRequestHandler mockRequestHandler;

    @Mock
    private EnvironmentReader mockEnvironmentReader;

    private DocumentGeneratorService documentGeneratorService;

    @BeforeEach
    public void setUp() {
        documentGeneratorService = new DocumentGeneratorServiceImpl(mockDocumentInfoService,
                mockEnvironmentReader, mockRequestHandler);
    }

    @Test
    @DisplayName("Test a successful generate completed")
    public void testsSuccessfulGenerateCompleted() throws IOException {

        when(mockDocumentInfoService.getDocumentInfo(any(DocumentInfoRequest.class))).thenReturn(setSuccessfulDocumentInfo());

        when(mockRequestHandler.sendDataToDocumentRenderService(any(String.class), any(RenderDocumentRequest.class))).thenReturn(setSuccessfulRenderResponse());

        DocumentResponse response = documentGeneratorService.generate(setValidRequest());

        assertNotNull(response);
        assertNotNull(response.getDescription());
        assertNotNull(response.getDescriptionIdentifier());
        assertNotNull(response.getDescriptionValues());
        assertNotNull(response.getLocation());
        assertNotNull(response.getSize());
    }

    @Test
    @DisplayName("Tests when null returned from documentInfoService")
    public void testsWhenErrorThrownFromDocumentInfoService() {

        when(mockDocumentInfoService.getDocumentInfo(any(DocumentInfoRequest.class))).thenReturn(null);

        DocumentResponse response = documentGeneratorService.generate(setValidRequest());

        assertNull(response);
    }

    @Test
    @DisplayName("Tests when an error thrown from requestHandler")
    public void testsWhenErrorThrownFromRequestHandler() throws IOException {

        when(mockDocumentInfoService.getDocumentInfo(any(DocumentInfoRequest.class))).thenReturn(setSuccessfulDocumentInfo());

        when(mockRequestHandler.sendDataToDocumentRenderService(any(String.class), any(RenderDocumentRequest.class))).thenThrow(IOException.class);

        DocumentResponse response = documentGeneratorService.generate(setValidRequest());

        assertNotNull(response);
        assertNotNull(response.getDescription());
        assertNotNull(response.getDescriptionIdentifier());
        assertNotNull(response.getDescriptionValues());
        assertNull(response.getLocation());
        assertNull(response.getSize());
    }

    /**
     * Set data for valid DocumentRequest
     *
     * @return
     */
    private DocumentRequest setValidRequest() {

        DocumentRequest request = new DocumentRequest();
        request.setContentType("contentType");
        request.setDocumentType("documentType");
        request.setId("id");
        request.setResource("resource");
        request.setResourceId("resourceId");
        request.setUserId("userId");

        return request;
    }

    /**
     * Set Data for successful RenderResponse
     *
     * @return
     */
    private RenderDocumentResponse setSuccessfulRenderResponse() {

        RenderDocumentResponse renderDocumentResponse = new RenderDocumentResponse();
        renderDocumentResponse.setDocumentSize("size");
        renderDocumentResponse.setLocation("location.file");

        return renderDocumentResponse;
    }

    /**
     * Set data from successful DocumentInfoService call
     *
     * @return
     */
    private DocumentInfoResponse setSuccessfulDocumentInfo() {

        DocumentInfoResponse documentInfoResponse = new DocumentInfoResponse();
        documentInfoResponse.setLocation("location");
        documentInfoResponse.setTemplateName("templateName");
        documentInfoResponse.setData("data");
        documentInfoResponse.setAssetId("assetId");
        documentInfoResponse.setDescription("description");
        documentInfoResponse.setDescriptionIdentifier("descriptionIdentifier");

        Map<String, String> descriptionValues = new HashMap<>();
        descriptionValues.put("date", "01/01/1980");

        documentInfoResponse.setDescriptionValues(descriptionValues);

        return documentInfoResponse;
    }
}
