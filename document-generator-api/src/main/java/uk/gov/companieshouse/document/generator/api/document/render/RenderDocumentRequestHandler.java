package uk.gov.companieshouse.document.generator.api.document.render;

import uk.gov.companieshouse.document.generator.api.document.render.models.RenderDocumentResponse;
import uk.gov.companieshouse.document.generator.api.document.render.models.RenderDocumentRequest;

import java.io.IOException;

public interface RenderDocumentRequestHandler {

    /**
     * Call the document render service and convert the data into a document
     *
     * @param url
     * @param request
     * @return
     * @throws IOException
     */
    RenderDocumentResponse sendDataToDocumentRenderService(String url, RenderDocumentRequest request) throws IOException;
}
