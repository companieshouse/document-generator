package uk.gov.companieshouse.document.generator.api.document.render;

import uk.gov.companieshouse.document.generator.api.document.render.models.RenderDocumentRequest;
import uk.gov.companieshouse.document.generator.api.document.render.models.RenderDocumentResponse;
import uk.gov.companieshouse.document.generator.api.exception.RenderServiceException;

import java.io.IOException;
import java.util.Map;

public interface RenderDocumentRequestHandler {

    /**
     * Call the document render service and convert the data into a document
     *
     * @param url a string formatted url
     * @param request the RenderDocumentRequest
     * @param requestParameters Map containing requestId, resourceId and resourceUri as a key/value pair
     * @return RenderDocumentResponse
     * @throws IOException
     */
    RenderDocumentResponse sendDataToDocumentRenderService(String url, RenderDocumentRequest request,
                                                           Map<String, String> requestParameters) throws IOException, RenderServiceException;
}
