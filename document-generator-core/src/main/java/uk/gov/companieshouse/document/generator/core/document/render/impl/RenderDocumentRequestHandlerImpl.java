package uk.gov.companieshouse.document.generator.core.document.render.impl;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import uk.gov.companieshouse.document.generator.core.document.models.DocumentGenerationCompleted;
import uk.gov.companieshouse.document.generator.core.document.render.httpConnectionHandler;
import uk.gov.companieshouse.document.generator.core.document.render.RenderDocumentRequestHandler;
import uk.gov.companieshouse.document.generator.core.document.render.RenderedDocumentJsonHandler;
import uk.gov.companieshouse.document.generator.core.document.render.models.RenderDocumentRequest;
import uk.gov.companieshouse.document.generator.core.document.render.models.RenderDocumentResponse;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

@Service
public class RenderDocumentRequestHandlerImpl implements RenderDocumentRequestHandler {

    @Autowired
    private RenderedDocumentJsonHandler renderedDocumentJsonHandler;

    @Autowired
    private httpConnectionHandler httpConnectionHandler;

    /**
     * Call the document render service and convert the data into a document
     * @param url
     * @param request
     * @return
     * @throws IOException
     */
    @Override
    public RenderDocumentResponse sendDataToDocumentRenderService(String url, RenderDocumentRequest request) throws IOException {

        DocumentGenerationCompleted generatedDocument = null;
        RenderDocumentResponse response = new RenderDocumentResponse();

        HttpURLConnection connection = httpConnectionHandler.openConnection(url);

        try {
            prepareConnection(connection, request);
            sendRequest(connection, request);
            response.setStatus(connection.getResponseCode());

            if (connection.getResponseCode() == HttpURLConnection.HTTP_CREATED) {
                generatedDocument = handleResponse(connection);
            }

        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        response.setGeneratedDocument(generatedDocument);

        return response;
    }

    /**
     * handle Response from http connection
     *
     * @param connection
     * @return
     * @throws IOException
     */
    private DocumentGenerationCompleted handleResponse(HttpURLConnection connection) throws IOException {

        DocumentGenerationCompleted generatedDocument = null;
        String generatedDocumentJson;

        try (InputStream response = connection.getInputStream()) {
            generatedDocumentJson = new String(IOUtils.toByteArray(response));
        }

        generatedDocument = renderedDocumentJsonHandler.convert(generatedDocumentJson);

        generatedDocument.setLocation(connection.getHeaderField("Location"));


        return generatedDocument;
    }

    /**
     * send request to service
     *
     * @param connection
     * @param request
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
     * @param connection
     * @param request
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
     * @param connection
     * @param request
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
