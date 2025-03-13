package uk.gov.companieshouse.document.generator.api.service.impl;

import static uk.gov.companieshouse.document.generator.api.DocumentGeneratorApplication.APPLICATION_NAME_SPACE;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.companieshouse.document.generator.api.document.DocumentType;
import uk.gov.companieshouse.document.generator.api.document.render.RenderDocumentRequestHandler;
import uk.gov.companieshouse.document.generator.api.document.render.models.RenderDocumentRequest;
import uk.gov.companieshouse.document.generator.api.document.render.models.RenderDocumentResponse;
import uk.gov.companieshouse.document.generator.api.exception.RenderServiceException;
import uk.gov.companieshouse.document.generator.api.exception.ServiceException;
import uk.gov.companieshouse.document.generator.api.factory.DocumentInfoServiceFactory;
import uk.gov.companieshouse.document.generator.api.models.DocumentRequest;
import uk.gov.companieshouse.document.generator.api.models.DocumentResponse;
import uk.gov.companieshouse.document.generator.api.models.Links;
import uk.gov.companieshouse.document.generator.api.service.DocumentGeneratorService;
import uk.gov.companieshouse.document.generator.api.service.DocumentTypeService;
import uk.gov.companieshouse.document.generator.api.service.response.ResponseObject;
import uk.gov.companieshouse.document.generator.api.service.response.ResponseStatus;
import uk.gov.companieshouse.document.generator.common.descriptions.RetrieveApiEnumerationDescription;
import uk.gov.companieshouse.document.generator.interfaces.exception.DocumentInfoException;
import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfoRequest;
import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfoResponse;
import uk.gov.companieshouse.environment.EnvironmentReader;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;

@Service
public class DocumentGeneratorServiceImpl implements DocumentGeneratorService {

    private EnvironmentReader environmentReader;

    private RenderDocumentRequestHandler requestHandler;

    private DocumentTypeService documentTypeService;

    private DocumentInfoServiceFactory documentInfoServiceFactory;

    private RetrieveApiEnumerationDescription retrieveApiEnumerationDescription;

    private static final String DOCUMENT_RENDER_SERVICE_HOST_ENV_VAR = "DOCUMENT_RENDER_SERVICE_HOST";

    private static final String DOCUMENT_BUCKET_NAME_ENV_VAR = "DOCUMENT_BUCKET_NAME";

    private static final String S3 = "s3://";

    private static final String CONTEXT_PATH = "/document-render/store";

    private static final String PUBLIC_LOCATION_PARAM = "?is_public=true";

    private static final Logger LOG = LoggerFactory.getLogger(APPLICATION_NAME_SPACE);

    private static final String FILING_DESCRIPTIONS = "filing_descriptions";

    private static final String DESCRIPTION_IDENTIFIERS_KEY = "description_identifiers";

    private static final String TEXT_HTML = "text/html";

    private static final String RESOURCE_URI = "resource_uri";

    private static final String REQUEST_ID = "request_id";

    @Autowired
    public DocumentGeneratorServiceImpl(DocumentInfoServiceFactory documentInfoServiceFactory,
                                        EnvironmentReader environmentReader,
                                        RenderDocumentRequestHandler requestHandler,
                                        DocumentTypeService documentTypeService,
                                        RetrieveApiEnumerationDescription retrieveApiEnumerationDescription) {

        this.documentInfoServiceFactory = documentInfoServiceFactory;
        this.environmentReader = environmentReader;
        this.requestHandler = requestHandler;
        this.documentTypeService = documentTypeService;
        this.retrieveApiEnumerationDescription = retrieveApiEnumerationDescription;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseObject generate(DocumentRequest documentRequest, String requestId) {

        DocumentResponse response;

        Map<String, String> requestParameters = new HashMap<>();
        requestParameters.put(RESOURCE_URI, documentRequest.getResourceUri());
        requestParameters.put(REQUEST_ID, requestId);

        createAndLogInfoMessage("Generation of document for resource: "
                        + requestParameters.get(RESOURCE_URI) + " has started", requestParameters);
        DocumentType documentType;
        try {
            documentType = documentTypeService.getDocumentType(requestParameters);
        } catch (ServiceException se){
            createAndLogErrorMessage("Failed to get document type from resource:  "
                    + requestParameters.get(RESOURCE_URI), se, requestParameters);
            return new ResponseObject(ResponseStatus.NO_TYPE_FOUND, null);
        }

        DocumentInfoRequest documentInfoRequest = setDocumentInfoRequest(documentRequest, requestId);

        DocumentInfoResponse documentInfoResponse;
        try {
            documentInfoResponse = documentInfoServiceFactory
                        .get(documentType.toString())
                        .getDocumentInfo(documentInfoRequest);
        } catch (DocumentInfoException die) {
             createAndLogErrorMessage("Error occurred whilst obtaining the data to generate document " +
                     "for resource: " + requestParameters.get(RESOURCE_URI), die, requestParameters);
            return new ResponseObject(ResponseStatus.FAILED_TO_RETRIEVE_DATA, null);
        }

        if (documentInfoResponse != null) {
            RenderDocumentResponse renderResponse = null;

            try {
                renderResponse = renderSubmittedDocumentData(documentRequest, documentInfoResponse,
                        requestParameters);
                if (renderResponse.getStatus() >= HttpStatus.SC_BAD_REQUEST) {
                    createAndLogErrorMessage("An error occurred in the render service, returning a status of: " +
                                    renderResponse.getStatus() + " for resource: " + requestParameters.get(RESOURCE_URI),
                            null, requestParameters);
                    response = setDocumentResponse(renderResponse, documentInfoResponse, requestParameters);
                    return new ResponseObject(ResponseStatus.FAILED_TO_RENDER, response);
                }
            } catch (IOException | RenderServiceException | JSONException se) {
                createAndLogErrorMessage("Error occurred when trying to render the document for resource: " +
                        requestParameters.get(RESOURCE_URI), se, requestParameters);
                response = setDocumentResponse(renderResponse, documentInfoResponse, requestParameters);
                return new ResponseObject(ResponseStatus.FAILED_TO_RENDER, response);
            }

            response = setDocumentResponse(renderResponse, documentInfoResponse, requestParameters);
        } else {
            createAndLogErrorMessage("No data was returned from documentInfoService for resource: " +
                    requestParameters.get(RESOURCE_URI),null, requestParameters);
            return new ResponseObject(ResponseStatus.NO_DATA_RETRIEVED, null);
        }
        createAndLogInfoMessage("Document generated for resource: " + requestParameters.get(RESOURCE_URI),
                requestParameters);
        return new ResponseObject(ResponseStatus.CREATED, response);
    }

    /**
     * Set parameters required in the document info request
     *
     * @param documentRequest An object containing the request details from the Api call
     * @param requestId The id of the request
     * @return A populated documentInfoRequest object
     */
    private DocumentInfoRequest setDocumentInfoRequest(DocumentRequest documentRequest, String requestId) {

        DocumentInfoRequest documentInfoRequest = new DocumentInfoRequest();
        BeanUtils.copyProperties(documentRequest, documentInfoRequest);
        documentInfoRequest.setRequestId(requestId);

        return  documentInfoRequest;
    }

    /**
     * Send data to Render Service and generate document
     *
     * @param documentRequest object that contains the request details from the Api call
     * @param documentInfoResponse object that contain the Response from documentInfoService
     * @param requestParameters Map containing requestId and resourceUri as a key/value pair
     * @return A populated RenderDocumentResponse model or Null
     * @throws IOException
     */
    private RenderDocumentResponse renderSubmittedDocumentData(DocumentRequest documentRequest,
                                                               DocumentInfoResponse documentInfoResponse,
                                                               Map<String, String> requestParameters)
            throws IOException, RenderServiceException, JSONException {

        String host = environmentReader.getMandatoryString(DOCUMENT_RENDER_SERVICE_HOST_ENV_VAR);
        String url = host + getContextPath(documentRequest.isPublicLocationRequired());

        RenderDocumentRequest requestData = new RenderDocumentRequest();
        requestData.setAssetId(documentInfoResponse.getAssetId());
        requestData.setData(documentInfoResponse.getData());
        requestData.setTemplateName(documentInfoResponse.getTemplateName());
        requestData.setLocation(buildLocation(documentInfoResponse.getPath()));

        setContentAndDocumentType(documentRequest.getMimeType(), documentRequest.getDocumentType(),
                requestData, requestParameters);

        return requestHandler.sendDataToDocumentRenderService(url, requestData, requestParameters);
    }

    /**
     * Builds the correct context path to obtain a public or private location.
     *
     * @param isPublicLocationRequired - flag to specify if a public location is required.
     * @return
     */
    private String getContextPath(boolean isPublicLocationRequired) {

        String contextPath = CONTEXT_PATH;

        if (isPublicLocationRequired) {
            contextPath = contextPath + PUBLIC_LOCATION_PARAM;
        }

        return contextPath;
    }

     /**
     * Sets the content and document type required in render document request.
     *
     * @param mimeType The content type of the document, also the document type if no document type set
     * @param documentType The document type
     * @param requestData The object containing the populated request data for the render service
     * @param requestParameters Map containing requestId and resourceUri as a key/value pair
     * @throws IOException
     */
    private void setContentAndDocumentType(String mimeType, String documentType, RenderDocumentRequest requestData,
                                           Map<String, String> requestParameters) throws IOException {

        if (mimeType.equals(TEXT_HTML)) {
            requestData.setContentType(mimeType);
            if (documentType != null) {
                requestData.setDocumentType(documentType);
            } else {
                requestData.setDocumentType(mimeType);
            }
        } else {
            createAndLogInfoMessage("error occurred while setting content and document type, as mime type: "
                    + mimeType + " is not valid", requestParameters);
            throw new IOException("The mime type: " + mimeType + " is not valid");
        }
    }

    /**
     * build the location the document is to be stored at
     *
     * @param path the path to be added
     * @return String the full location path constructed with StringBuilder
     */
    private String buildLocation(String path) {

        String bucketName = environmentReader.getMandatoryString(DOCUMENT_BUCKET_NAME_ENV_VAR);

        return new StringBuilder(S3).append(bucketName).append(path).toString();
    }

    /**
     * Set documentResponse for Api
     *
     * @param renderResponse object that contains the RenderDocumentResponse details
     * @param documentInfoResponse object that contains the Response from documentInfoService
     * @param requestParameters Map containing requestId and resourceUri as a key/value pair
     * @return a Document Response
     * @return
     */
    private DocumentResponse setDocumentResponse(RenderDocumentResponse renderResponse,
                                                 DocumentInfoResponse documentInfoResponse,
                                                 Map<String, String> requestParameters) {

        DocumentResponse response = new DocumentResponse();

        if (renderResponse != null) {
            Links links = new Links();
            links.setLocation(renderResponse.getLocation());
            response.setLinks(links);
            response.setSize(renderResponse.getDocumentSize());
        }

        response.setDescriptionValues(documentInfoResponse.getDescriptionValues());
        response.setDescription(getDescription(documentInfoResponse, requestParameters));
        response.setDescriptionIdentifier(documentInfoResponse.getDescriptionIdentifier());

        return response;
    }

    /**
     * Get the document description from an api enumeration file
     *
     * @param documentInfoResponse object that contains the Response from documentInfoService
     * @param requestParameters Map containing requestId and resourceUri as a key/value pair
     * @return String containing the description
     */
    private String getDescription(DocumentInfoResponse documentInfoResponse, Map<String, String> requestParameters) {

        return retrieveApiEnumerationDescription.getApiEnumerationDescription(
            FILING_DESCRIPTIONS, DESCRIPTION_IDENTIFIERS_KEY,
            documentInfoResponse.getDescriptionIdentifier(), requestParameters);
    }


    /**
     * Create and log the error message
     *
     * @param message The message to be logged
     * @param <T> generic exception parameter to be stored in message
     * @param requestParameters Map containing requestId and resourceUri as a key/value pair
     */
    private <T extends Exception> void createAndLogErrorMessage(String message, T exception,
                                                                Map<String, String> requestParameters) {

        ServiceException serviceException;

        if (exception != null) {
            serviceException =
                    new ServiceException(message, exception);
        } else {
            serviceException =
                    new ServiceException(message);
        }
        
        LOG.errorContext(requestParameters.get(REQUEST_ID), serviceException,
                setDebugMap(requestParameters));
    }


    /**
     * Create and log Info Message
     *
     * @param message message to be logged
     * @param requestParameters Map containing requestId and resourceUri as a key/value pair
     */
    private void createAndLogInfoMessage(String message, Map<String, String> requestParameters) {
        LOG.infoContext(requestParameters.get(REQUEST_ID), message, setDebugMap(requestParameters));
    }

    private Map<String, Object> setDebugMap(Map<String, String> requestParameters) {

        Map <String, Object> debugMap = new HashMap <>();
        debugMap.put(RESOURCE_URI, requestParameters.get(RESOURCE_URI));

        return debugMap;
    }
}
