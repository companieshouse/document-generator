package uk.gov.companieshouse.document.generator.api.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.companieshouse.document.generator.api.document.DocumentType;
import uk.gov.companieshouse.document.generator.api.document.description.RetrieveApiEnumerationDescription;
import uk.gov.companieshouse.document.generator.api.document.render.RenderDocumentRequestHandler;
import uk.gov.companieshouse.document.generator.api.document.render.models.RenderDocumentRequest;
import uk.gov.companieshouse.document.generator.api.document.render.models.RenderDocumentResponse;
import uk.gov.companieshouse.document.generator.api.exception.DocumentGeneratorServiceException;
import uk.gov.companieshouse.document.generator.api.factory.DocumentInfoServiceFactory;
import uk.gov.companieshouse.document.generator.api.models.DocumentRequest;
import uk.gov.companieshouse.document.generator.api.models.DocumentResponse;
import uk.gov.companieshouse.document.generator.api.service.DocumentGeneratorService;
import uk.gov.companieshouse.document.generator.api.service.DocumentTypeService;
import uk.gov.companieshouse.document.generator.api.service.response.ResponseObject;
import uk.gov.companieshouse.document.generator.api.service.response.ResponseStatus;
import uk.gov.companieshouse.document.generator.interfaces.exception.DocumentInfoException;
import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfoRequest;
import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfoResponse;
import uk.gov.companieshouse.environment.EnvironmentReader;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static uk.gov.companieshouse.document.generator.api.DocumentGeneratorApplication.APPLICATION_NAME_SPACE;

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

    private static final String CONTEXT_PATH = "/document-render/store?is_public=true";

    private static final Logger LOG = LoggerFactory.getLogger(APPLICATION_NAME_SPACE);

    private static final String FILING_DESCRIPTIONS_FILE_NAME = "api-enumerations/filing_descriptions.yml";

    private static final String DESCRIPTION_IDENTIFIERS_KEY = "description_identifiers";

    private static final String TEXT_HTML = "text/html";

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
        String resourceId = documentRequest.getResourceId();
        String resourceUri = documentRequest.getResourceUri();

        DocumentType documentType;
        try {
            documentType = documentTypeService.getDocumentType(documentRequest.getResourceUri());
        } catch (DocumentGeneratorServiceException dgse){
            createAndLogErrorMessage("Failed to get document type from resource:  "
                    + resourceUri, resourceId, resourceUri, dgse, requestId);
            return new ResponseObject(ResponseStatus.NO_TYPE_FOUND, null);
        }

        DocumentInfoRequest documentInfoRequest = new DocumentInfoRequest();
        BeanUtils.copyProperties(documentRequest, documentInfoRequest);

        DocumentInfoResponse documentInfoResponse;
        try {
            documentInfoResponse = documentInfoServiceFactory
                        .get(documentType.toString())
                        .getDocumentInfo(documentInfoRequest);
        } catch (DocumentInfoException dgie) {
             createAndLogErrorMessage("Error occurred whilst obtaining the data to generate document " +
                     "for resource: " + resourceUri, resourceId, resourceUri, dgie, requestId);
            return new ResponseObject(ResponseStatus.FAILED_TO_RETRIEVE_DATA, null);
        }

        if (documentInfoResponse != null) {
            RenderDocumentResponse renderResponse = null;
            try {
                renderResponse = renderSubmittedDocumentData(documentRequest, documentInfoResponse);
            } catch (IOException ioe) {
                createAndLogErrorMessage("Error occurred whilst rendering the document for resource: " +
                        resourceUri, resourceId, resourceUri, ioe, requestId);
                response = setDocumentResponse(renderResponse, documentInfoResponse, requestId, resourceId, resourceUri);
                return new ResponseObject(ResponseStatus.FAILED_TO_RENDER, response);
            }

            response = setDocumentResponse(renderResponse, documentInfoResponse, requestId, resourceId, resourceUri);
        } else {
            createAndLogErrorMessage("No data was returned from documentInfoService for resource: " +
                    resourceUri, resourceId, resourceUri, null, requestId);
            return new ResponseObject(ResponseStatus.NO_DATA_RETRIEVED, null);
        }

        return new ResponseObject(ResponseStatus.CREATED, response);
    }

    /**
     * Send data to Render Service and generate document
     *
     * @param documentRequest object that contains the request details from the Api call
     * @param documentInfoResponse object that contain the Response from documentInfoService
     * @return A populated RenderDocumentResponse model or Null
     */
    private RenderDocumentResponse renderSubmittedDocumentData(DocumentRequest documentRequest,
                                                               DocumentInfoResponse documentInfoResponse)
            throws IOException {

        String host = environmentReader.getMandatoryString(DOCUMENT_RENDER_SERVICE_HOST_ENV_VAR);
        String url = new StringBuilder(host).append(CONTEXT_PATH).toString();

        RenderDocumentRequest requestData = new RenderDocumentRequest();
        requestData.setAssetId(documentInfoResponse.getAssetId());
        requestData.setData(documentInfoResponse.getData());
        requestData.setTemplateName(documentInfoResponse.getTemplateName());
        requestData.setLocation(buildLocation(documentInfoResponse.getPath()));

        setContentAndDocumentType(documentRequest.getMimeType(), documentRequest.getDocumentType(), requestData);

        return requestHandler.sendDataToDocumentRenderService(url, requestData);
    }

    /**
     * Sets the content and document type required in render document request.
     *
     * @param mimeType The content type of the document, also the document type if no document type set
     * @param documentType The document type
     * @param requestData The object containing the populated request data for the render service
     * @throws IOException
     */
    private void setContentAndDocumentType(String mimeType, String documentType, RenderDocumentRequest requestData)
            throws IOException {

        if (mimeType.equals(TEXT_HTML)) {
            requestData.setContentType(mimeType);
            if (documentType != null) {
                requestData.setDocumentType(documentType);
            } else {
                requestData.setDocumentType(mimeType);
            }
        } else {
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
     * @return a Document Response
     */
    private DocumentResponse setDocumentResponse(RenderDocumentResponse renderResponse,
                                                 DocumentInfoResponse documentInfoResponse, String requestId,
                                                 String resourceId, String resourceUri) {

        DocumentResponse response = new DocumentResponse();

        if (renderResponse != null) {
            response.setLinks(renderResponse.getLocation());
            response.setSize(renderResponse.getDocumentSize());
        }

        response.setDescriptionValues(documentInfoResponse.getDescriptionValues());
        response.setDescription(getDescription(documentInfoResponse, requestId, resourceId, resourceUri));
        response.setDescriptionIdentifier(documentInfoResponse.getDescriptionIdentifier());

        return response;
    }

    /**
     * Get the document description
     *
     * @param documentInfoResponse object that contains the Response from documentInfoService
     * @return String containing the description
     * @throws IOException
     */
    private String getDescription(DocumentInfoResponse documentInfoResponse, String requestId,
                                  String resourceId, String resourceUri) {

        String description = null;
        try {
            description = retrieveApiEnumerationDescription.getApiEnumerationDescription(
                    FILING_DESCRIPTIONS_FILE_NAME, DESCRIPTION_IDENTIFIERS_KEY,
                    documentInfoResponse.getTemplateName(), documentInfoResponse.getDescriptionValues());
        } catch (IOException ioe) {
            createAndLogErrorMessage("Error retrieving description from api-enumeration from: "
                    + FILING_DESCRIPTIONS_FILE_NAME, resourceId, resourceUri, ioe, requestId);
        }

        return description;
    }

    /**
     * Create and log the error message
     *
     * @param message The message to be logged
     * @param <T> generic exception parameter to be stored in message
     * @param requestId The request Id
     */
    private <T extends Exception> void createAndLogErrorMessage(String message, String resourceId, String resourceUri,
                                                                T exception, String requestId) {

        Map <String, Object> debugMap = new HashMap <>();
        debugMap.put("resource_uri", resourceUri);
        debugMap.put("resource_id",resourceId);

        DocumentGeneratorServiceException documentGeneratorServiceException;

        if (exception != null) {
            documentGeneratorServiceException =
                    new DocumentGeneratorServiceException(message, exception);
        } else {
            documentGeneratorServiceException =
                    new DocumentGeneratorServiceException(message);
        }
        
        LOG.errorContext(requestId, documentGeneratorServiceException, debugMap);
    }
}
