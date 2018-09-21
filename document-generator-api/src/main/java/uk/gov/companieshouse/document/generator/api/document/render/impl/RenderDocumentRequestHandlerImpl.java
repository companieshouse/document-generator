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

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

@Service
public class RenderDocumentRequestHandlerImpl implements RenderDocumentRequestHandler {

    @Autowired
    private HttpConnectionHandler httpConnectionHandler;

    @Autowired
    private ConvertJsonHandler convertJsonHandler;

    /**
     * {@inheritDoc}
     */
    @Override
    public RenderDocumentResponse sendDataToDocumentRenderService(String url, RenderDocumentRequest request) throws IOException {

        RenderDocumentResponse response = new RenderDocumentResponse();

        HttpURLConnection connection = httpConnectionHandler.openConnection(url);

        try {
            prepareConnection(connection, request);
            sendRequest(connection, request);

            if (connection.getResponseCode() == HttpURLConnection.HTTP_CREATED) {
                response = handleResponse(connection);
            }

            response.setStatus(connection.getResponseCode());

        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

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
}
