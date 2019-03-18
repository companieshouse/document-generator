package uk.gov.companieshouse.document.generator.prosecution;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;
import uk.gov.companieshouse.document.generator.interfaces.DocumentInfoService;
import uk.gov.companieshouse.document.generator.interfaces.exception.DocumentInfoException;
import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfoRequest;
import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfoResponse;
import uk.gov.companieshouse.document.generator.prosecution.UltimatumDocumentInfoBuilderProvider.UltimatumDocumentInfoBuilder;
import uk.gov.companieshouse.document.generator.prosecution.tmpclient.ProsecutionCase;
import uk.gov.companieshouse.document.generator.prosecution.tmpclient.ProsecutionClient;
import uk.gov.companieshouse.document.generator.prosecution.tmpclient.SdkException;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;

/**
 * The Prosecution module of Document Generator. When a request with a matching URL is received by
 * Document Generator, it is forwarded to this module (see matching, below). The URL is further
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
 * to this service, based on whether the URL matches the regex in
 * uk.gov.companieshouse.document.generator.api.document.DocumentType.PROSECUTION. </br>
 * Matching URLs must take the form:
 * <ul>
 * <li>Ultimatum: <code>/prosecution/ultimatum{urlOfProsecutionCase}</code></br>
 * e.g.
 * <code>/prosecution/ultimatum/company/00066516/prosecution-cases/dd908afef2b8529aab3c680239f5d06717113634</code>
 * </li>
 * <li>SJP: <code>/prosecution/sjp{urlOfProsecutionCase}</code></br>
 * e.g.
 * <code>/prosecution/sjp/company/00066516/prosecution-cases/dd908afef2b8529aab3c680239f5d06717113634</code>
 * </li>
 * </ul>
 * This service will be matched on the base of this URL, will use the base to further determine
 * exactly which document type to produce, and then will use the rest of the URL to get the data
 * that it needs.
 */
@Service
public class ProsecutionDocumentInfoService implements DocumentInfoService {
    public static final String MODULE_NAME_SPACE = "document-generator-prosecution";
    private static final String LOGGING_RESOURCE_KEY = "resource";

    private static final Logger LOG = LoggerFactory.getLogger(MODULE_NAME_SPACE);

    private static final String MATCH_START_OF_SJP_URI = "/prosecution/sjp/";
    private static final String MATCH_START_OF_ULTIMATUM_URI = "/prosecution/ultimatum/";

    private static final int ULTIMATUM_INDEX_TO_TRIM_FROM =
                    MATCH_START_OF_ULTIMATUM_URI.length() - 1;
    private static final int SJP_INDEX_TO_TRIM_FROM = MATCH_START_OF_SJP_URI.length() - 1;
    private final UltimatumDocumentInfoBuilderProvider docInfoBuilderProvider;

    private final ProsecutionClient prosecutionClient;

    /**
     * Constructor, which will be autowired.
     * 
     * @param docInfoBuilderProvider
     * @param prosecutionClient
     */
    public ProsecutionDocumentInfoService(
                    UltimatumDocumentInfoBuilderProvider docInfoBuilderProvider,
                    ProsecutionClient prosecutionClient) {
        this.docInfoBuilderProvider = docInfoBuilderProvider;
        this.prosecutionClient = prosecutionClient;
    }

    /**
     * Entry point from Document Generator, in which it asks delegates a request for a
     * DocumentInfoRespoonse to this class.
     * 
     */
    @Override
    public DocumentInfoResponse getDocumentInfo(DocumentInfoRequest documentInfoRequest)
                    throws DocumentInfoException {
        LOG.info("Started getting document");
        String requestId = documentInfoRequest.getRequestId();
        String docGenUri = documentInfoRequest.getResourceUri();
        if (isProsecutionUltimatumRequest(docGenUri)) {
            return getUltimatumInfo(docGenUri, requestId);
        } else if (isProsecutionSjpRequest(docGenUri)) {
            return getSjpInfo(docGenUri, requestId);
        } else {
            Map<String, Object> logMap = new HashMap<>();
            logMap.put(LOGGING_RESOURCE_KEY, docGenUri);
            // TODO: Jo: I am not sure that this is the right way of logging this in doc getn
            LOG.error("Unmatchable resourceUri inside prosecution: " + docGenUri);
            throw new DocumentInfoException(
                            "Unmatchable resourceUri inside prosecution: " + docGenUri);
        }
    }

    private boolean isProsecutionUltimatumRequest(String urlToMatch) {
        return urlToMatch.startsWith(MATCH_START_OF_ULTIMATUM_URI);
    }

    private boolean isProsecutionSjpRequest(String urlToMatch) {
        return urlToMatch.startsWith(MATCH_START_OF_SJP_URI);
    }

    private String getProsecutionCaseUriFromUltimatumUri(String prosecutionCaseUri) {
        return prosecutionCaseUri.substring(ULTIMATUM_INDEX_TO_TRIM_FROM);
    }

    private String getProsecutionCaseUriFromSjpUri(String docGenUri) {
        return docGenUri.substring(SJP_INDEX_TO_TRIM_FROM);
    }

    private ProsecutionCase getProsecutionCase(String docGenUri, String prosecutionCaseUri,
                    String requestId) throws DocumentInfoException {
        try {
            return prosecutionClient.getProsecutionCase(prosecutionCaseUri, requestId);
        } catch (SdkException ex) {
            Map<String, Object> logMap = new HashMap<>();
            logMap.put(LOGGING_RESOURCE_KEY, docGenUri);
            LOG.errorContext(requestId,
                            "Could not get prosecution case info to build template for URI: "
                                            + prosecutionCaseUri,
                            ex, logMap);
            throw new DocumentInfoException("Could not build template: " + docGenUri, ex);
        }
    }

    /**
     * Returns the necessary metadata to contact the document renderer and ask it to render an
     * ultimatum.
     * 
     * @param docGenUri The URI asking Document Generator to create an Ultimatum.
     * @param requestId The request ID, used in logging.
     * @return the necessary metadata to contact the document renderer and ask it to render a
     *         document.
     * @throws DocumentInfoException If the DocumentInfoResponse could not be created.
     */
    private DocumentInfoResponse getUltimatumInfo(String docGenUri, String requestId)
                    throws DocumentInfoException {
        String prosecutionCaseUri = getProsecutionCaseUriFromUltimatumUri(docGenUri);
        ProsecutionCase prosecutionCase =
                        getProsecutionCase(docGenUri, prosecutionCaseUri, requestId);
        UltimatumDocumentInfoBuilder builder = docInfoBuilderProvider.builder();
        builder.prosecutionCase(prosecutionCase);
        builder.renderedDocFileName(createFileName("Ultimatum-"));
        try {
            DocumentInfoResponse ultimatumInfo = builder.build();
            LOG.info("Ultimatum Info created: " + ultimatumInfo);
            return ultimatumInfo;
        } catch (DocumentInfoCreationException ex) {
            Map<String, Object> logMap = new HashMap<>();
            logMap.put(LOGGING_RESOURCE_KEY, prosecutionCaseUri);
            LOG.errorContext(requestId,
                            "Error building template variables from: " + prosecutionCaseUri, ex,
                            logMap);
            throw new DocumentInfoException("Could not build template: " + prosecutionCaseUri, ex);
        }
    }

    private String createFileName(String start) {
        StringBuilder sb = new StringBuilder(start);
        sb.append(LocalDateTime.now());
        sb.append(".pdf");
        return sb.toString();
    }

    private DocumentInfoResponse getSjpInfo(String docGenUri, String requestId)
                    throws DocumentInfoException {
        throw new UnsupportedOperationException("TODO: SJP-");
    }
}
