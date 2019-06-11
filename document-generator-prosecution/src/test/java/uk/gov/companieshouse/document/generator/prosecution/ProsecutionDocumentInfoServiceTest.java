package uk.gov.companieshouse.document.generator.prosecution;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.model.prosecution.prosecutioncase.ProsecutionCaseStatusApi;
import uk.gov.companieshouse.document.generator.interfaces.exception.DocumentInfoException;
import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfoRequest;
import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfoResponse;
import uk.gov.companieshouse.document.generator.prosecution.exception.HandlerException;
import uk.gov.companieshouse.document.generator.prosecution.handler.ProsecutionHandler;
import uk.gov.companieshouse.document.generator.prosecution.mapping.model.ProsecutionDocument;
import uk.gov.companieshouse.document.generator.prosecution.mapping.model.prosecutioncase.ProsecutionCase;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProsecutionDocumentInfoServiceTest {

    @InjectMocks
    ProsecutionDocumentInfoService prosecutionDocumentInfoService;

    @Mock
    ProsecutionHandler mockHandler;

    private String resourceUri = "uri";
    private String requestId = "1";

    @Test
    @DisplayName("Test using a prosecution case that has an ACCEPTED status should call "
            + "the handler for ultimatum")
    public void testGetDocumentInfoWithAcceptedCase() throws Exception{
        ProsecutionCase prosecutionCase = new ProsecutionCase();
        prosecutionCase.setStatus(ProsecutionCaseStatusApi.ACCEPTED);
        DocumentInfoRequest request = getDocumentInfoRequest(resourceUri, requestId);
        ProsecutionDocument document = new ProsecutionDocument();
        document.setProsecutionCase(prosecutionCase);
        when(mockHandler.getProsecutionDocument(resourceUri, requestId)).thenReturn(document);
        DocumentInfoResponse response = new DocumentInfoResponse();
        when(mockHandler.getUltimatumResponse(document, requestId)).thenReturn(response);
        prosecutionDocumentInfoService.getDocumentInfo(request);
        verify(mockHandler, times(1)).getProsecutionDocument(resourceUri, requestId);
        verify(mockHandler, times(1)).getUltimatumResponse(document, requestId);
    }

    @Test
    @DisplayName("Test using a prosecution case that has an ULTIMATUM_ISSUED status "
            + "should call the handler for SJPn")
    public void testGetDocumentInfoWithUltimatumIssuedCase() throws Exception {
        ProsecutionCase prosecutionCase = new ProsecutionCase();
        prosecutionCase.setStatus(ProsecutionCaseStatusApi.ULTIMATUM_ISSUED);
        DocumentInfoRequest request = getDocumentInfoRequest(resourceUri, requestId);
        ProsecutionDocument document = new ProsecutionDocument();
        document.setProsecutionCase(prosecutionCase);
        when(mockHandler.getProsecutionDocument(resourceUri, requestId)).thenReturn(document);
        DocumentInfoResponse response = new DocumentInfoResponse();
        when(mockHandler.getSJPnResponse(document, requestId)).thenReturn(response);
        prosecutionDocumentInfoService.getDocumentInfo(request);
        verify(mockHandler, times(1)).getProsecutionDocument(resourceUri, requestId);
        verify(mockHandler, times(1)).getSJPnResponse(document, requestId);
    }

    @Test
    @DisplayName("Test using a prosecution case that has a REFERRED status should throw "
            + "DocumentInfoException")
    public void testGetDocumentInfoWithOtherStatusCase() throws Exception{
        ProsecutionCase prosecutionCase = new ProsecutionCase();
        prosecutionCase.setStatus(ProsecutionCaseStatusApi.REFERRED);
        DocumentInfoRequest request = getDocumentInfoRequest(resourceUri, requestId);
        ProsecutionDocument document = new ProsecutionDocument();
        document.setProsecutionCase(prosecutionCase);
        when(mockHandler.getProsecutionDocument(resourceUri, requestId)).thenReturn(document);
        assertThrows(DocumentInfoException.class, () -> prosecutionDocumentInfoService.getDocumentInfo(request));
        verify(mockHandler, times(1)).getProsecutionDocument(resourceUri, requestId);
    }
    
    @Test
    @DisplayName("Test where getProsecutionDocument call on the ProsecutionHandler throws a "
            + "HandlerException")
    public void testGetDocumentInfoWithHandlerException() throws Exception {
        DocumentInfoRequest request = getDocumentInfoRequest(resourceUri, requestId);
        when(mockHandler.getProsecutionDocument(resourceUri, requestId)).thenThrow(new HandlerException("Handler exception"));
        assertThrows(DocumentInfoException.class, () -> prosecutionDocumentInfoService.getDocumentInfo(request));
    }

    private DocumentInfoRequest getDocumentInfoRequest(String resourceUri, String requestId) {
        DocumentInfoRequest request = new DocumentInfoRequest();
        request.setResourceUri(resourceUri);
        request.setRequestId(requestId);
        return request;
    }

}
