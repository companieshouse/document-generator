package uk.gov.companieshouse.document.generator.api.document.render;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.document.generator.api.document.render.impl.RenderDocumentRequestHandlerImpl;
import uk.gov.companieshouse.document.generator.api.document.render.models.RenderDocumentRequest;
import uk.gov.companieshouse.document.generator.api.document.render.models.RenderDocumentResponse;
import uk.gov.companieshouse.document.generator.api.exception.RenderServiceException;

import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RenderDocumentRequestHandlerTest {

    private static final String PDF_LOCATION = "pdf-location";

    private static final String RESPONSE_TEXT = "{\"document_size\":12345}";

    private static final String RESOURCE_URI = "/transactions/091174-913515-326060/accounts/xU-6Vebn7F8AgLwa2QHBUL2yRpk=";

    private static final String REQUEST_ID = "requestId";

    private static final String TEST_URL = "testUrl";


    @Mock
    private HttpURLConnection mockHttpURLConnection;

    @Mock
    private OutputStream mockOutputSteam;

    @Mock
    private DataOutputStream mockDataOutputStream;

    @InjectMocks
    private RenderDocumentRequestHandlerImpl renderDocumentRequestHandler;

    @Mock
    private ConvertJsonHandler convertJsonHandler;

    @Mock
    private HttpConnectionHandler mockHttpConnectionHandler;

    private RenderDocumentRequest renderDocumentRequest;

    private static Map<String, String> requestParameters;

    @BeforeEach
    public void setUp() {

        renderDocumentRequest = new RenderDocumentRequest();
        renderDocumentRequest.setAssetId("asset1");
        renderDocumentRequest.setContentType("text/html");
        renderDocumentRequest.setData("test-data");
        renderDocumentRequest.setDocumentType("application/pdf");
        renderDocumentRequest.setTemplateName("template1");

        requestParameters = new HashMap<>();
        requestParameters.put("resource_uri", RESOURCE_URI);
        requestParameters.put("request_id", REQUEST_ID);
    }

    @Test
    @DisplayName("Send the data to the render service successfully")
    public void testSendDataToRenderServiceSuccess()
            throws IOException, RenderServiceException, JSONException {

        setValidOpenConnection();
        when(convertJsonHandler.convert(any(String.class))).thenReturn("long data");

        setMockHttpConnectionForSuccess(HttpStatus.SC_CREATED);
        RenderDocumentResponse response = renderDocumentRequestHandler.sendDataToDocumentRenderService(
                TEST_URL, renderDocumentRequest, requestParameters);

        assertEquals(PDF_LOCATION, response.getLocation());

        verifyHttpConnectionMock(true);
        assertEquals(HttpStatus.SC_CREATED, response.getStatus());
    }

    @Test
    @DisplayName("Send the data to the render service and obtain a response error")
    public void testSendDataToRenderServiceServerResponseError()
            throws IOException, RenderServiceException, JSONException {

        setValidOpenConnection();

        setMockHttpConnectionForError(HttpStatus.SC_INTERNAL_SERVER_ERROR);
        RenderDocumentResponse response = renderDocumentRequestHandler.sendDataToDocumentRenderService(
                TEST_URL, renderDocumentRequest, requestParameters);

        assertNull(response.getDocumentSize());
        assertNull(response.getLocation());

        verifyHttpConnectionMock(false);
        assertEquals(HttpStatus.SC_INTERNAL_SERVER_ERROR, response.getStatus());
    }

    @Test
    @DisplayName("Error Thrown when opening connection fails")
    public void testErrorThrownWhenOpeningConnectionFails() throws IOException {

        when(mockHttpConnectionHandler.openConnection(any(String.class))).thenThrow(IOException.class);

        assertThrows(RenderServiceException.class, () -> renderDocumentRequestHandler.sendDataToDocumentRenderService(
                TEST_URL, renderDocumentRequest, requestParameters));
    }

    @Test
    @DisplayName("Error Thrown when preparing connection fails")
    public void testErrorThrownWhenPreparingConnectionFails() throws IOException {

        setValidOpenConnection();
        doThrow(ProtocolException.class).when(mockHttpURLConnection).setRequestMethod(any(String.class));

        assertThrows(RenderServiceException.class, () -> renderDocumentRequestHandler.sendDataToDocumentRenderService(
                TEST_URL, renderDocumentRequest, requestParameters));
    }

    @Test
    @DisplayName("Error Thrown when send request fails")
    public void testErrorThrownWhenSendRequestFails() throws IOException {

        setValidOpenConnection();
        when(mockHttpURLConnection.getOutputStream()).thenThrow(IOException.class);

        assertThrows(RenderServiceException.class, () -> renderDocumentRequestHandler.sendDataToDocumentRenderService(
                TEST_URL, renderDocumentRequest, requestParameters));
    }

    @Test
    @DisplayName("Error Thrown when handle request fails")
    public void testErrorThrownWhenHandleRequestFails() throws IOException {

        setValidOpenConnection();
        when(mockHttpURLConnection.getOutputStream()).thenReturn(mockOutputSteam);
        when(mockHttpURLConnection.getResponseCode()).thenReturn(HttpStatus.SC_CREATED);
        when(mockHttpURLConnection.getInputStream()).thenThrow(IOException.class);

        assertThrows(RenderServiceException.class, () -> renderDocumentRequestHandler.sendDataToDocumentRenderService(
                TEST_URL, renderDocumentRequest, requestParameters));
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
     * Set a valid open connection
     *
     * @throws IOException
     */
    private void setValidOpenConnection() throws IOException {

        when(mockHttpConnectionHandler.openConnection(any(String.class))).thenReturn(mockHttpURLConnection);
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
