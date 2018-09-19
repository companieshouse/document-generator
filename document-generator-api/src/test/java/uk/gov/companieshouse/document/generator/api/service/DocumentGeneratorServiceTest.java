package uk.gov.companieshouse.document.generator.api.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.document.generator.api.Exception.DocumentGeneratorServiceException;
import uk.gov.companieshouse.document.generator.api.document.render.RenderDocumentRequestHandler;
import uk.gov.companieshouse.document.generator.api.document.render.models.RenderDocumentRequest;
import uk.gov.companieshouse.document.generator.api.document.render.models.RenderDocumentResponse;
import uk.gov.companieshouse.document.generator.api.models.DocumentRequest;
import uk.gov.companieshouse.document.generator.api.service.impl.DocumentGeneratorServiceImpl;
import uk.gov.companieshouse.document.generator.api.service.response.ResponseObject;
import uk.gov.companieshouse.document.generator.api.service.response.ResponseStatus;
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
    private DocumentTypeService mockDocumentTypeService;

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
                mockEnvironmentReader, mockRequestHandler, mockDocumentTypeService);
    }

    @Test
    @DisplayName("Test a successful generate completed")
    public void testsSuccessfulGenerateCompleted() throws IOException, DocumentGeneratorServiceException {

        when(mockDocumentTypeService.getDocumentType(any(String.class))).thenReturn("ACCOUNTS");
        when(mockDocumentInfoService.getDocumentInfo(any(DocumentInfoRequest.class))).thenReturn(setSuccessfulDocumentInfo());
        when(mockRequestHandler.sendDataToDocumentRenderService(any(String.class), any(RenderDocumentRequest.class))).thenReturn(setSuccessfulRenderResponse());

        ResponseObject response = documentGeneratorService.generate(setValidRequest(), REQUEST_ID);

        assertNotNull(response);
        assertNotNull(response.getData());
        assertEquals(setDescriptionValue(), response.getData().getDescriptionValues());
        assertEquals(DESCRIPTION, response.getData().getDescription());
        assertEquals(DESCRIPTION_IDENTIFIER, response.getData().getDescriptionIdentifier());
        assertEquals(SIZE, response.getData().getSize());
        assertEquals(LOCATION, response.getData().getLinks());

        assertEquals(ResponseStatus.CREATED, response.getStatus());
    }

    @Test
    @DisplayName("Tests when null returned from documentInfoService")
    public void testsWhenErrorThrownFromDocumentInfoService() throws DocumentGeneratorServiceException {

        when(mockDocumentTypeService.getDocumentType(any(String.class))).thenReturn("ACCOUNTS");
        when(mockDocumentInfoService.getDocumentInfo(any(DocumentInfoRequest.class))).thenReturn(null);

        ResponseObject response = documentGeneratorService.generate(setValidRequest(), REQUEST_ID);

        assertNull(response.getData());
        assertEquals(ResponseStatus.NO_DATA_RETRIEVED, response.getStatus());
    }

    @Test
    @DisplayName("Tests when error thrown from documentTypeService")
    public void testsWhenErrorThrownFromDocumentTypeService() throws DocumentGeneratorServiceException {

        when(mockDocumentTypeService.getDocumentType(any(String.class))).thenThrow(DocumentGeneratorServiceException.class);

        ResponseObject response = documentGeneratorService.generate(setValidRequest(), REQUEST_ID);

        assertNull(response.getData());
        assertEquals(ResponseStatus.NO_DOCUMENT_TYPE_FOUND, response.getStatus());
    }

    @Test
    @DisplayName("Tests when an error thrown from requestHandler")
    public void testsWhenErrorThrownFromRequestHandler() throws IOException, DocumentGeneratorServiceException {

        when(mockDocumentTypeService.getDocumentType(any(String.class))).thenReturn("ACCOUNTS");
        when(mockDocumentInfoService.getDocumentInfo(any(DocumentInfoRequest.class))).thenReturn(setSuccessfulDocumentInfo());
        when(mockRequestHandler.sendDataToDocumentRenderService(any(String.class), any(RenderDocumentRequest.class))).thenThrow(IOException.class);

        ResponseObject response = documentGeneratorService.generate(setValidRequest(), REQUEST_ID);

        assertNotNull(response);
        assertNotNull(response.getData());
        assertNull(response.getData().getLinks());
        assertNull(response.getData().getSize());
        assertEquals(setDescriptionValue(), response.getData().getDescriptionValues());
        assertEquals(DESCRIPTION, response.getData().getDescription());
        assertEquals(DESCRIPTION_IDENTIFIER, response.getData().getDescriptionIdentifier());

        assertEquals(ResponseStatus.DOCUMENT_NOT_RENDERED, response.getStatus());
    }

    /**
     * Set data for valid DocumentRequest
     *
     * @return Document Request
     */
    private DocumentRequest setValidRequest() {

        DocumentRequest request = new DocumentRequest();
        request.setResourceUri("resourceUri");
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
