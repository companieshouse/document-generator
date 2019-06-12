package uk.gov.companieshouse.document.generator.prosecution;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.companieshouse.document.generator.interfaces.DocumentInfoService;
import uk.gov.companieshouse.document.generator.interfaces.exception.DocumentInfoException;
import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfoRequest;
import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfoResponse;
import uk.gov.companieshouse.document.generator.prosecution.exception.HandlerException;
import uk.gov.companieshouse.document.generator.prosecution.handler.ProsecutionHandler;
import uk.gov.companieshouse.document.generator.prosecution.mapping.model.ProsecutionDocument;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;

/**
 * The Prosecution module of Document Generator. When a request with a matching URI is received by
 * Document Generator, it is forwarded to this module (see matching, below). The URI is further
 * processed by DocumentGenerator to see which kind of document is to be generated. Different
 * documents require different template variables and so have different methods to fill in those
 * template values and other metadata in a {@link DocumentInfoResponse} that is returned to Document
 * Generator. Document Generator will then use this metadata to determine where to send the template
 * values and other metadata.
 * </p>
 * 
 *
 * <h3>Matching</h3> The enum uk.gov.companieshouse.document.generator.api.document.DocumentType,
 * within the document generator api project, is used to determine whether or not to steer a request
 * to this service, based on whether the URI matches the regex in
 * uk.gov.companieshouse.document.generator.api.document.DocumentType.PROSECUTION. </br>
 * Matching URIs must take the form:
 * <ul>
 * <li>Ultimatum: <code>/prosecution/ultimatum{urlOfProsecutionCase}</code></br>
 * e.g.
 * <code>/prosecution/ultimatum/company/1/prosecution-cases/dd908afef2b8529aab3c680239f5d06717113634</code>
 * </li>
 * <li>SJP: <code>/prosecution/sjp{urlOfProsecutionCase}</code></br>
 * e.g.
 * <code>/prosecution/sjp/company/1/prosecution-cases/dd908afef2b8529aab3c680239f5d06717113634</code>
 * </li>
 * </ul>
 * This service will be matched on the base of this URI, will use the base to further determine
 * exactly which document type to produce, and then will use the rest of the URI to get the data
 * that it needs.
 */
@Service
public class ProsecutionDocumentInfoService implements DocumentInfoService {
    public static final String MODULE_NAME_SPACE = "document-generator-prosecution";

    private static final Logger LOG = LoggerFactory.getLogger(MODULE_NAME_SPACE);

    @Autowired
    private ProsecutionHandler handler;

    /**
     * Entry point from Document Generator, asking for a DocumentInfoResponse that contains info to
     * build a document, such info will include the location of any template, any variables that are
     * needed by the template, etc - see DocumentInfoResponse for more info.
     */
    @Override
    public DocumentInfoResponse getDocumentInfo(DocumentInfoRequest documentInfoRequest)
            throws DocumentInfoException {
        String resourceUri = documentInfoRequest.getResourceUri();
        String requestId = documentInfoRequest.getRequestId();
        final Map<String, Object> debugMap = new HashMap<>();
        debugMap.put("resource_uri", resourceUri);
        LOG.infoContext(requestId, "Started getting document info for prosecution", debugMap);

        String docGenUri = documentInfoRequest.getResourceUri();
        ProsecutionDocument document;
        try {
            document = handler.getProsecutionDocument(resourceUri);
            return handler.getDocumentResponse(document, requestId, document.getProsecutionCase().getStatus());
        } catch (HandlerException e) {
            Map<String, Object> logData = new HashMap<>();
            logData.put("resource_uri", resourceUri);
            LOG.errorContext(requestId, "Failed to generate prosecution document", e, logData);
            throw new DocumentInfoException("Unmatchable resourceUri inside prosecution" + docGenUri);
        }
    }
}
