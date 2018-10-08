package uk.gov.companieshouse.document.generator.api.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
import uk.gov.companieshouse.document.generator.api.document.DocumentType;
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

    private static final String DOCUMENT_RENDER_SERVICE_HOST_ENV_VAR = "DOCUMENT_RENDER_SERVICE_HOST";

    private static final String DOCUMENT_BUCKET_NAME_ENV_VAR = "DOCUMENT_BUCKET_NAME";

    private static final String S3 = "s3://";

    private static final String CONTEXT_PATH = "/document-render/store?is_public=true";

    private static final Logger LOG = LoggerFactory.getLogger(APPLICATION_NAME_SPACE);

    @Autowired
    public DocumentGeneratorServiceImpl(DocumentInfoServiceFactory documentInfoServiceFactory,
                                        EnvironmentReader environmentReader,
                                        RenderDocumentRequestHandler requestHandler,
                                        DocumentTypeService documentTypeService) {

        this.documentInfoServiceFactory = documentInfoServiceFactory;
        this.environmentReader = environmentReader;
        this.requestHandler = requestHandler;
        this.documentTypeService = documentTypeService;
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
                    + documentRequest.getResourceUri(), dgse, resourceId, resourceUri, requestId);
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
                     "for resource: " + documentInfoRequest.getResourceUri(), dgie, resourceId, resourceUri, requestId);
            return new ResponseObject(ResponseStatus.FAILED_TO_RETRIEVE_DATA, null);
        }

        if (documentInfoResponse != null) {
            RenderDocumentResponse renderResponse = null;
            try {
                renderResponse = renderSubmittedDocumentData(documentRequest, documentInfoResponse);
            } catch (IOException ioe) {
                createAndLogErrorMessage("Error occurred whilst rendering the document for resource: " +
                        documentInfoRequest.getResourceUri(), ioe, resourceId, resourceUri, requestId);
                response = setDocumentResponse(renderResponse, documentInfoResponse);
                return new ResponseObject(ResponseStatus.FAILED_TO_RENDER, response);
            }

            response = setDocumentResponse(renderResponse, documentInfoResponse);
        } else {
            createAndLogErrorMessage("No data was returned from documentInfoService for resource: " +
                    documentInfoRequest.getResourceUri(), null, resourceId, resourceUri, requestId);
            return new ResponseObject(ResponseStatus.NO_DATA_RETRIEVED, null);
        }

        return new ResponseObject(ResponseStatus.CREATED, response);
    }

    /**
     * Send data to Render Service and generate document
     *
     * @param documentRequest object that contains the request details
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
        requestData.setContentType(documentInfoResponse.getContentType());
        requestData.setData(documentInfoResponse.getData());
        requestData.setDocumentType(documentRequest.getMimeType());
        requestData.setTemplateName(documentInfoResponse.getTemplateName());
        requestData.setLocation(buildLocation(documentInfoResponse.getPath()));

        return requestHandler.sendDataToDocumentRenderService(url, requestData);
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
     * @param documentInfoResponse object that contain the Response from documentInfoService
     * @return a Document Response
     */
    private DocumentResponse setDocumentResponse(RenderDocumentResponse renderResponse,
                                                 DocumentInfoResponse documentInfoResponse) {

        DocumentResponse response = new DocumentResponse();

        if (renderResponse != null) {
            response.setLinks(renderResponse.getLocation());
            response.setSize(renderResponse.getDocumentSize());
        }

        response.setDescriptionValues(documentInfoResponse.getDescriptionValues());
        response.setDescription(documentInfoResponse.getDescription());
        response.setDescriptionIdentifier(documentInfoResponse.getDescriptionIdentifier());

        return response;
    }

    /**
     * Create and log the error message
     *
     * @param message The message to be logged
     * @param <T> generic exception parameter to be stored in message
     * @param resourceId The resource Id
     * @param resourceUri The resource Url
     * @param requestId The request Id
     */
    private <T extends Exception> void createAndLogErrorMessage(String message, T exception,
                                                                String resourceId, String resourceUri, String requestId) {

        Map < String, Object > debugMap = new HashMap < > ();
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
