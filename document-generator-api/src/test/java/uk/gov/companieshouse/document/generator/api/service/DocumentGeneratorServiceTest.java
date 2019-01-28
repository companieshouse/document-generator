package uk.gov.companieshouse.document.generator.api.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.document.generator.api.document.description.RetrieveApiEnumerationDescription;
import uk.gov.companieshouse.document.generator.api.document.render.RenderDocumentRequestHandler;
import uk.gov.companieshouse.document.generator.api.document.render.models.RenderDocumentRequest;
import uk.gov.companieshouse.document.generator.api.document.render.models.RenderDocumentResponse;
import uk.gov.companieshouse.document.generator.api.exception.RenderServiceException;
import uk.gov.companieshouse.document.generator.api.exception.ServiceException;
import uk.gov.companieshouse.document.generator.api.factory.DocumentInfoServiceFactory;
import uk.gov.companieshouse.document.generator.api.models.DocumentRequest;
import uk.gov.companieshouse.document.generator.api.service.impl.DocumentGeneratorServiceImpl;
import uk.gov.companieshouse.document.generator.api.service.response.ResponseObject;
import uk.gov.companieshouse.document.generator.api.service.response.ResponseStatus;
import uk.gov.companieshouse.document.generator.api.document.DocumentType;
import uk.gov.companieshouse.document.generator.interfaces.DocumentInfoService;
import uk.gov.companieshouse.document.generator.interfaces.exception.DocumentInfoException;
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
    private DocumentInfoServiceFactory mockDocumentInfoServiceFactory;

    @Mock
    private RenderDocumentRequestHandler mockRequestHandler;

    @Mock
    private DocumentTypeService mockDocumentTypeService;

    @Mock
    private EnvironmentReader mockEnvironmentReader;

    @Mock
    private RetrieveApiEnumerationDescription mockRetrieveApiEnumerationDescription;

    private DocumentGeneratorService documentGeneratorService;

    private String REQUEST_ID = "requestId";

    private String DESCRIPTION = "description";

    private String DESCRIPTION_IDENTIFIER = "descriptionIdentifier";

    private String SIZE = "size";

    private String PATH = "/assetId/UniqueFileName";

    private String BUCKET_LOCATION = "bucket_location";

    private String S3 = "s3://";

    private String DATE = "date";

    private String DATE_VALUE = "01/01/1980";

    @BeforeEach
    public void setUp() {
        documentGeneratorService = new DocumentGeneratorServiceImpl(mockDocumentInfoServiceFactory, mockEnvironmentReader,
                mockRequestHandler, mockDocumentTypeService, mockRetrieveApiEnumerationDescription);
    }

    @Test
    @DisplayName("Test a successful generate completed")
    public void testsSuccessfulGenerateCompleted() throws IOException, ServiceException, DocumentInfoException,
            RenderServiceException {

        when(mockDocumentTypeService.getDocumentType(any(Map.class))).thenReturn(DocumentType.ACCOUNTS);
        when(mockDocumentInfoServiceFactory.get(any(String.class))).thenReturn(mockDocumentInfoService);
        when(mockDocumentInfoService.getDocumentInfo(any(DocumentInfoRequest.class))).thenReturn(setSuccessfulDocumentInfo());
        when(mockRequestHandler.sendDataToDocumentRenderService(any(String.class), any(RenderDocumentRequest.class),
                any(Map.class))).thenReturn(setSuccessfulRenderResponse());
        when(mockEnvironmentReader.getMandatoryString(any(String.class))).thenReturn(BUCKET_LOCATION);
        when(mockRetrieveApiEnumerationDescription.getApiEnumerationDescription(any(String.class), any(String.class),
                any(String.class), any(Map.class), any(Map.class))).thenReturn(DESCRIPTION);

        ResponseObject response = documentGeneratorService.generate(setValidRequest(), REQUEST_ID);

        assertNotNull(response);
        assertNotNull(response.getData());
        assertEquals(setDescriptionValue(), response.getData().getDescriptionValues());
        assertEquals(DESCRIPTION, response.getData().getDescription());
        assertEquals(DESCRIPTION_IDENTIFIER, response.getData().getDescriptionIdentifier());
        assertEquals(SIZE, response.getData().getSize());
        assertEquals(buildLocation(), response.getData().getLinks().getLocation());

        assertEquals(ResponseStatus.CREATED, response.getStatus());
    }

    @Test
    @DisplayName("Tests when null returned from documentInfoService")
    public void testsWhenNullReturnedFromDocumentInfoService() throws ServiceException, DocumentInfoException {

        when(mockDocumentTypeService.getDocumentType(any(Map.class))).thenReturn(DocumentType.ACCOUNTS);
        when(mockDocumentInfoServiceFactory.get(any(String.class))).thenReturn(mockDocumentInfoService);
        when(mockDocumentInfoService.getDocumentInfo(any(DocumentInfoRequest.class))).thenReturn(null);

        ResponseObject response = documentGeneratorService.generate(setValidRequest(), REQUEST_ID);

        assertNull(response.getData());
        assertEquals(ResponseStatus.NO_DATA_RETRIEVED, response.getStatus());
    }

    @Test
    @DisplayName("Tests when error returned from documentInfoService")
    public void testsWhenErrorThrownFromDocumentInfoService() throws ServiceException, DocumentInfoException {

        when(mockDocumentTypeService.getDocumentType(any(Map.class))).thenReturn(DocumentType.ACCOUNTS);
        when(mockDocumentInfoServiceFactory.get(any(String.class))).thenReturn(mockDocumentInfoService);
        when(mockDocumentInfoService.getDocumentInfo(any(DocumentInfoRequest.class))).thenThrow(DocumentInfoException.class);

        ResponseObject response = documentGeneratorService.generate(setValidRequest(), REQUEST_ID);

        assertNull(response.getData());
        assertEquals(ResponseStatus.FAILED_TO_RETRIEVE_DATA, response.getStatus());
    }

    @Test
    @DisplayName("Tests when error thrown from documentTypeService")
    public void testsWhenErrorThrownFromDocumentTypeService() throws ServiceException {

        when(mockDocumentTypeService.getDocumentType(any(Map.class))).thenThrow(ServiceException.class);

        ResponseObject response = documentGeneratorService.generate(setValidRequest(), REQUEST_ID);

        assertNull(response.getData());
        assertEquals(ResponseStatus.NO_TYPE_FOUND, response.getStatus());
    }

    @Test
    @DisplayName("Tests when an error thrown from requestHandler")
    public void testsWhenErrorThrownFromRequestHandler() throws IOException, ServiceException,
            DocumentInfoException, RenderServiceException {

        when(mockDocumentTypeService.getDocumentType(any(Map.class))).thenReturn(DocumentType.ACCOUNTS);
        when(mockDocumentInfoServiceFactory.get(any(String.class))).thenReturn(mockDocumentInfoService);
        when(mockDocumentInfoService.getDocumentInfo(any(DocumentInfoRequest.class))).thenReturn(setSuccessfulDocumentInfo());
        when(mockRequestHandler.sendDataToDocumentRenderService(any(String.class), any(RenderDocumentRequest.class),
                any(Map.class))).thenThrow(IOException.class);
        when(mockEnvironmentReader.getMandatoryString(any(String.class))).thenReturn(BUCKET_LOCATION);
        when(mockRetrieveApiEnumerationDescription.getApiEnumerationDescription(any(String.class), any(String.class),
                any(String.class), any(Map.class), any(Map.class))).thenReturn(DESCRIPTION);

        ResponseObject response = documentGeneratorService.generate(setValidRequest(), REQUEST_ID);

        assertNotNull(response);
        assertNotNull(response.getData());
        assertNull(response.getData().getLinks());
        assertNull(response.getData().getSize());
        assertEquals(setDescriptionValue(), response.getData().getDescriptionValues());
        assertEquals(DESCRIPTION, response.getData().getDescription());
        assertEquals(DESCRIPTION_IDENTIFIER, response.getData().getDescriptionIdentifier());

        assertEquals(ResponseStatus.FAILED_TO_RENDER, response.getStatus());
    }

    /**
     * Set data for valid DocumentRequest
     *
     * @return Document Request
     */
    private DocumentRequest setValidRequest() {

        DocumentRequest request = new DocumentRequest();
        request.setResourceUri("/transactions/091174-913515-326060/accounts/xU-6Vebn7F8AgLwa2QHBUL2yRpk=");
        request.setMimeType("text/html");
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
        renderDocumentResponse.setLocation(buildLocation());

        return renderDocumentResponse;
    }

    /**
     * Build the string path for parameter location
     *
     * @return String the Location as a string value
     */
    private String buildLocation() {

        return new StringBuilder(S3).append(BUCKET_LOCATION).append(PATH).toString();
    }

    /**
     * Set data from successful DocumentInfoService call
     *
     * @return DocumentInfoResponse
     */
    private DocumentInfoResponse setSuccessfulDocumentInfo() {

        DocumentInfoResponse documentInfoResponse = new DocumentInfoResponse();
        documentInfoResponse.setPath(PATH);
        documentInfoResponse.setTemplateName("templateName");
        documentInfoResponse.setData("data");
        documentInfoResponse.setAssetId("assetId");
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
