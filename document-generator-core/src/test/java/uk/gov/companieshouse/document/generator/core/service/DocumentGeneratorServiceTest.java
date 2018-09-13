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
import uk.gov.companieshouse.document.generator.core.models.DocumentRequest;
import uk.gov.companieshouse.document.generator.core.service.impl.DocumentGeneratorServiceImpl;
import uk.gov.companieshouse.document.generator.core.service.response.ResponseObject;
import uk.gov.companieshouse.document.generator.core.service.response.ResponseStatus;
import uk.gov.companieshouse.document.generator.interfaces.DocumentInfoService;
import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfoRequest;
import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfoResponse;
import uk.gov.companieshouse.environment.EnvironmentReader;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    private String REQUEST_ID = "requestId";

    private String DESCRIPTION = "description";

    private String DESCRIPTION_IDENTIFIER = "descriptionIdentifier";

    private String SIZE = "size";

    private String LOCATION = "location.file";

    private String DATE = "date";

    private String DATE_VALUE = "01/01/1980";

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

        ResponseObject response = documentGeneratorService.generate(setValidRequest(), REQUEST_ID);

        assertNotNull(response);
        assertNotNull(response.getData());
        assertEquals(response.getData().getDescriptionValues(), setDescriptionValue());
        assertEquals(response.getData().getDescription(), DESCRIPTION);
        assertEquals(response.getData().getDescriptionIdentifier(), DESCRIPTION_IDENTIFIER);
        assertEquals(response.getData().getSize(), SIZE);
        assertEquals(response.getData().getLinks(), LOCATION);

        assertEquals(response.getStatus(), ResponseStatus.CREATED);
    }

    @Test
    @DisplayName("Tests when null returned from documentInfoService")
    public void testsWhenErrorThrownFromDocumentInfoService() {

        when(mockDocumentInfoService.getDocumentInfo(any(DocumentInfoRequest.class))).thenReturn(null);

        ResponseObject response = documentGeneratorService.generate(setValidRequest(), REQUEST_ID);

        assertNull(response.getData());
        assertEquals(response.getStatus(), ResponseStatus.NO_DATA_RETRIEVED);
    }

    @Test
    @DisplayName("Tests when an error thrown from requestHandler")
    public void testsWhenErrorThrownFromRequestHandler() throws IOException {

        when(mockDocumentInfoService.getDocumentInfo(any(DocumentInfoRequest.class))).thenReturn(setSuccessfulDocumentInfo());
        when(mockRequestHandler.sendDataToDocumentRenderService(any(String.class), any(RenderDocumentRequest.class))).thenThrow(IOException.class);

        ResponseObject response = documentGeneratorService.generate(setValidRequest(), REQUEST_ID);

        assertNotNull(response);
        assertNotNull(response.getData());
        assertNull(response.getData().getLinks());
        assertNull(response.getData().getSize());
        assertEquals(response.getData().getDescriptionValues(), setDescriptionValue());
        assertEquals(response.getData().getDescription(), DESCRIPTION);
        assertEquals(response.getData().getDescriptionIdentifier(), DESCRIPTION_IDENTIFIER);

        assertEquals(response.getStatus(), ResponseStatus.DOCUMENT_NOT_RENDERED);
    }

    /**
     * Set data for valid DocumentRequest
     *
     * @return Document Request
     */
    private DocumentRequest setValidRequest() {

        DocumentRequest request = new DocumentRequest();
        request.setResourceUrl("resourceUri");
        request.setResourceId("resourceId");
        request.setMimeType("mimeType");
        request.setDocumentType("documentType");

        return request;
    }

    /**
     * Set Data for successful RenderResponse
     *
     * @return RenderDocumentResponse
     */
    private RenderDocumentResponse setSuccessfulRenderResponse() {

        RenderDocumentResponse renderDocumentResponse = new RenderDocumentResponse();
        renderDocumentResponse.setDocumentSize(SIZE);
        renderDocumentResponse.setLocation(LOCATION);

        return renderDocumentResponse;
    }

    /**
     * Set data from successful DocumentInfoService call
     *
     * @return DocumentInfoResponse
     */
    private DocumentInfoResponse setSuccessfulDocumentInfo() {

        DocumentInfoResponse documentInfoResponse = new DocumentInfoResponse();
        documentInfoResponse.setLocation(LOCATION);
        documentInfoResponse.setTemplateName("templateName");
        documentInfoResponse.setData("data");
        documentInfoResponse.setAssetId("assetId");
        documentInfoResponse.setDescription(DESCRIPTION);
        documentInfoResponse.setDescriptionIdentifier(DESCRIPTION_IDENTIFIER);
        documentInfoResponse.setDescriptionValues(setDescriptionValue());

        return documentInfoResponse;
    }

    /**
     * Set the description values for tests
     *
     * @return Map String pair
     */
    private Map<String,String> setDescriptionValue() {

        Map<String, String> descriptionValues = new HashMap<>();
        descriptionValues.put(DATE, DATE_VALUE);

        return descriptionValues;
    }
}
