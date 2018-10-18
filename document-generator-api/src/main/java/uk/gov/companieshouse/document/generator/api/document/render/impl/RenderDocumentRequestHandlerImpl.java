package uk.gov.companieshouse.document.generator.api.document.render.impl;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import uk.gov.companieshouse.document.generator.api.document.render.ConvertJsonHandler;
import uk.gov.companieshouse.document.generator.api.document.render.HttpConnectionHandler;
import uk.gov.companieshouse.document.generator.api.document.render.RenderDocumentRequestHandler;
import uk.gov.companieshouse.document.generator.api.document.render.models.RenderDocumentRequest;
import uk.gov.companieshouse.document.generator.api.document.render.models.RenderDocumentResponse;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static uk.gov.companieshouse.document.generator.api.DocumentGeneratorApplication.APPLICATION_NAME_SPACE;

@Service
public class RenderDocumentRequestHandlerImpl implements RenderDocumentRequestHandler {

    @Autowired
    private HttpConnectionHandler httpConnectionHandler;

    @Autowired
    private ConvertJsonHandler convertJsonHandler;

    private static final Logger LOG = LoggerFactory.getLogger(APPLICATION_NAME_SPACE);

    private static final String RESOURCE_URI = "resource_uri";

    private static final String RESOURCE_ID = "resource_id";

    private static final String REQUEST_ID = "request_id";

    /**
     * {@inheritDoc}
     */
    @Override
    public RenderDocumentResponse sendDataToDocumentRenderService(String url, RenderDocumentRequest request,
                                                                  Map<String, String> requestParameters) throws IOException {

        RenderDocumentResponse response = new RenderDocumentResponse();

        HttpURLConnection connection = httpConnectionHandler.openConnection(url);

        String requestId = requestParameters.get(REQUEST_ID);

        try {
            LOG.infoContext(requestId,"Preparing the connection for render service", setDebugMap(requestParameters));
            prepareConnection(connection, request);
            LOG.infoContext(requestId, "Sending the request to the render service", setDebugMap(requestParameters));
            sendRequest(connection, request);

            if (connection.getResponseCode() == HttpURLConnection.HTTP_CREATED) {
                LOG.infoContext(requestId, "handling the response from the render service", setDebugMap(requestParameters));
                response = handleResponse(connection);
            }

            response.setStatus(connection.getResponseCode());

        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        LOG.infoContext(requestId,"returning response from the render service", setDebugMap(requestParameters));
        return response;
    }

    /**
     * handle Response from http connection
     *
     * @param connection the HttpUrlConnection
     * @return RenderDocumentResponse
     * @throws IOException
     */
    private RenderDocumentResponse handleResponse(HttpURLConnection connection) throws IOException {

        String generatedDocumentJson;
        RenderDocumentResponse renderDocumentResponse = new RenderDocumentResponse();

        try (InputStream response = connection.getInputStream()) {
            generatedDocumentJson = new String(IOUtils.toByteArray(response));
        }

        renderDocumentResponse.setDocumentSize(convertJsonHandler.convert(generatedDocumentJson));
        renderDocumentResponse.setLocation(connection.getHeaderField("Location"));

        return renderDocumentResponse;
    }

    /**
     * Send request to service
     *
     * @param connection the HttpUrlConnection
     * @param request the RenderDocumentRequest
     * @throws IOException
     */
    private void sendRequest(HttpURLConnection connection, RenderDocumentRequest request) throws IOException {

        try (DataOutputStream out = new DataOutputStream(connection.getOutputStream())) {
            out.write(request.getData().getBytes(StandardCharsets.UTF_8));
            out.flush();
        }
    }

    /**
     * Prepare the HTTP connection
     *
     * @param connection the HttpUrlConnection
     * @param request the RenderDocumentRequest
     * @throws IOException
     */
    private void prepareConnection(HttpURLConnection connection, RenderDocumentRequest request) throws IOException {

        connection.setDoOutput(true);
        connection.setRequestMethod("POST");

        setConnectionRequestProperties(connection, request);
    }

    /**
     * Set the connection request properties
     *
     * @param connection the HttpUrlConnection
     * @param request the RenderDocumentRequest
     */
    private void setConnectionRequestProperties(HttpURLConnection connection, RenderDocumentRequest request) {

        String apiKey = System.getenv("CHS_API_KEY");
        if (!StringUtils.isEmpty(apiKey)) {
            connection.setRequestProperty("Authorization", apiKey);
        }

        connection.setRequestProperty("templateName", request.getTemplateName());
        connection.setRequestProperty("assetId", request.getAssetId());
        connection.setRequestProperty("Content-Type", request.getContentType());
        connection.setRequestProperty("Accept", request.getDocumentType());
        connection.setRequestProperty("Location", request.getLocation());
    }

    private Map<String, Object> setDebugMap(Map<String, String> requestParameters) {

        Map<String, Object> debugMap = new HashMap<>();
        debugMap.put(RESOURCE_URI, requestParameters.get(RESOURCE_URI));
        debugMap.put(RESOURCE_ID, requestParameters.get(RESOURCE_ID));

        return debugMap;
    }
}
