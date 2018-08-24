package uk.gov.companieshouse.document.generator.core.document.render;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.document.generator.core.document.models.DocumentGenerationCompleted;
import uk.gov.companieshouse.document.generator.core.document.render.impl.RenderDocumentRequestHandlerImpl;
import uk.gov.companieshouse.document.generator.core.document.render.models.RenderDocumentRequest;
import uk.gov.companieshouse.document.generator.core.document.render.models.RenderDocumentResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RenderDocumentRequestHandlerTest {

    private static final String PDF_LOCATION = "pdf-location";

    private static final String RESPONSE_TEXT = "{\"document_size\":12345}";

    @Mock
    private HttpURLConnection mockHttpURLConnection;

    @Mock
    private OutputStream mockOutputSteam;

    @InjectMocks
    private RenderDocumentRequestHandlerImpl renderDocumentRequestHandler;

    @Mock
    private ConvertJsonHandler convertJsonHandler;

    @Mock
    private HttpConnectionHandler mockHttpConnectionHandler;

    private RenderDocumentRequest renderDocumentRequest;

    @BeforeEach
    public void setUp() throws IOException{

        renderDocumentRequest = new RenderDocumentRequest();
        renderDocumentRequest.setAssetId("asset1");
        renderDocumentRequest.setContentType("text/html");
        renderDocumentRequest.setData("test-data");
        renderDocumentRequest.setDocumentType("application/pdf");
        renderDocumentRequest.setTemplateName("template1");

        when(mockHttpConnectionHandler.openConnection(any(String.class))).thenReturn(mockHttpURLConnection);

    }

    @Test
    @DisplayName("Send the data to the render service successfully")
    public void testSendDataToRenderServiceSuccess() throws IOException {

        when(convertJsonHandler.convert(any(String.class))).thenReturn(documentGeneratorCompletedData());

        setMockHttpConnectionForSuccess(201);

        RenderDocumentResponse response = renderDocumentRequestHandler.sendDataToDocumentRenderService("http://www.test.com", renderDocumentRequest);
        DocumentGenerationCompleted generatedDocument = response.getGeneratedDocument();

        assertEquals(PDF_LOCATION, generatedDocument.getLocation());
        assertEquals(201, response.getStatus());

        verifyHttpConnectionMock(true);
    }

    @Test
    @DisplayName("Send the data to the render service and obtain a response error")
    public void testSendDataToRenderServiceServerResponseError() throws IOException {

        setMockHttpConnectionForError(500);
        RenderDocumentResponse response = renderDocumentRequestHandler.sendDataToDocumentRenderService("http://www.test.com", renderDocumentRequest);
        DocumentGenerationCompleted generatedDocument = response.getGeneratedDocument();

        assertNull(generatedDocument);
        assertEquals(500, response.getStatus());

        verifyHttpConnectionMock(false);
    }

    /**
     * Set document size data when mocking renderedDocumentJsonHandler
     *
     * @return generatedDocument
     */
    private DocumentGenerationCompleted documentGeneratorCompletedData() {
        DocumentGenerationCompleted generatedDocument = new DocumentGenerationCompleted();

        generatedDocument.setDocumentSize("long data");

        return generatedDocument;
    }

    /**
     * Mock the http connection for test with failed status code
     *
     * @param statusCode
     * @throws IOException
     */
    private void setMockHttpConnectionForError(int statusCode) throws IOException {
        when(mockHttpURLConnection.getOutputStream()).thenReturn(mockOutputSteam);
        when(mockHttpURLConnection.getResponseCode()).thenReturn(statusCode);
    }

    /**
     * Mock the http connection for test with successful status code
     *
     * @param statusCode
     */
    private void setMockHttpConnectionForSuccess(int statusCode) throws IOException {
        when(mockHttpURLConnection.getOutputStream()).thenReturn(mockOutputSteam);
        when(mockHttpURLConnection.getResponseCode()).thenReturn(statusCode);

        when(mockHttpURLConnection.getHeaderField("Location")).thenReturn(PDF_LOCATION);

        InputStream testResponseInputStream = new ByteArrayInputStream(RESPONSE_TEXT.getBytes());
        when(mockHttpURLConnection.getInputStream()).thenReturn(testResponseInputStream);
    }

    /**
     * Verify if the connection is a success or not
     *
     * @param isSuccess
     */
    private void verifyHttpConnectionMock(boolean isSuccess) throws IOException {

        verify(mockHttpURLConnection).setRequestMethod("POST");
        verify(mockHttpURLConnection).setRequestProperty("templateName", renderDocumentRequest.getTemplateName());
        verify(mockHttpURLConnection).setRequestProperty("assetId", renderDocumentRequest.getAssetId());
        verify(mockHttpURLConnection).setRequestProperty("Content-Type", renderDocumentRequest.getContentType());
        verify(mockHttpURLConnection).setRequestProperty("Accept", renderDocumentRequest.getDocumentType());
        verify(mockHttpURLConnection).setRequestProperty("Location", renderDocumentRequest.getLocation());
        verify(mockHttpURLConnection).setDoOutput(true);
        verify(mockHttpURLConnection).getOutputStream();

        if (isSuccess) {
            verify(mockHttpURLConnection).getInputStream();
        }
    }
}
