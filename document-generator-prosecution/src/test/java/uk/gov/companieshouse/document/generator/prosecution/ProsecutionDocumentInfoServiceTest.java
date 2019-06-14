package uk.gov.companieshouse.document.generator.prosecution;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.document.generator.interfaces.exception.DocumentInfoException;
import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfoRequest;
import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfoResponse;
import uk.gov.companieshouse.document.generator.prosecution.exception.HandlerException;
import uk.gov.companieshouse.document.generator.prosecution.exception.ProsecutionServiceException;
import uk.gov.companieshouse.document.generator.prosecution.handler.ProsecutionHandler;
import uk.gov.companieshouse.document.generator.prosecution.mapping.model.ProsecutionDocument;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProsecutionDocumentInfoServiceTest {

    @InjectMocks
    private ProsecutionDocumentInfoService prosecutionDocumentInfoService;

    @Mock
    private ProsecutionHandler mockHandler;

    @Mock
    private ProsecutionDocument prosecutionDocument;

    private static final String RESOURCE_URI = "uri";
    private static final String REQUEST_ID = "1";
    private static final String ASSET_ID = "assetId";
    private static final String DESCRIPTION_IDENTIFIER = "descriptionIdentifier";
    private static final String DATA = "data";
    private static final String TEMPLATE_NAME = "templateName";
    private static final String PATH = "path";

    @Test
    @DisplayName("Tests successful retrieval of a prosecution document")
    void testSuccessfulGetDocumentInfo() throws HandlerException, DocumentInfoException, ProsecutionServiceException {
        when(mockHandler.getDocumentResponse(REQUEST_ID, RESOURCE_URI)).thenReturn(createDocumentInfoResponse());

        DocumentInfoResponse response = prosecutionDocumentInfoService.getDocumentInfo(getDocumentInfoRequest());
        assertNotNull(response);
        assertEquals(response, createDocumentInfoResponse());
    }

    @Test
    @DisplayName("Tests unsuccessful retrieval of a prosecution document")
    void testUnsuccessfulGetDocumentInfo() throws DocumentInfoException, HandlerException {
        when(mockHandler.getDocumentResponse(REQUEST_ID, RESOURCE_URI)).thenThrow(HandlerException.class);

        assertThrows(DocumentInfoException.class, () -> prosecutionDocumentInfoService.getDocumentInfo(getDocumentInfoRequest()));
    }
    private DocumentInfoRequest getDocumentInfoRequest() {
        DocumentInfoRequest request = new DocumentInfoRequest();
        request.setResourceUri(RESOURCE_URI);
        request.setRequestId(REQUEST_ID);
        return request;
    }

    private DocumentInfoResponse createDocumentInfoResponse() {
        DocumentInfoResponse response = new DocumentInfoResponse();
        response.setDescriptionIdentifier(DESCRIPTION_IDENTIFIER);
        response.setAssetId(ASSET_ID);
        response.setData(DATA);
        response.setTemplateName(TEMPLATE_NAME);
        response.setPath(PATH);
        return response;
    }

}
