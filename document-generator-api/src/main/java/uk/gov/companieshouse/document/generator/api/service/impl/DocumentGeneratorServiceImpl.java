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
import uk.gov.companieshouse.document.generator.api.utility.DocumentType;
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

        final Map < String, Object > debugMap = new HashMap < > ();
        debugMap.put("resource_uri", documentRequest.getResourceUri());
        debugMap.put("resource_id", documentRequest.getResourceId());

        DocumentType documentType;
        try {
            documentType = documentTypeService.getDocumentType(documentRequest.getResourceUri());
        } catch (DocumentGeneratorServiceException dgse){
            LOG.errorContext(requestId, dgse, debugMap);
            return new ResponseObject(ResponseStatus.NO_TYPE_FOUND, null);
        }

        DocumentInfoRequest documentInfoRequest = new DocumentInfoRequest();
        BeanUtils.copyProperties(documentRequest, documentInfoRequest);

        //TODO refine exception handling in doc-gen-accounts SFA-723
        DocumentInfoResponse documentInfoResponse = documentInfoServiceFactory
                    .get(documentType.toString())
                    .getDocumentInfo(documentInfoRequest);


        if (documentInfoResponse != null) {
            RenderDocumentResponse renderResponse = null;
            try {
                renderResponse = renderSubmittedDocumentData(documentRequest, documentInfoResponse);
            } catch (IOException ioe) {
                DocumentGeneratorServiceException documentGeneratorServiceException =
                        new DocumentGeneratorServiceException("failed to render the document for " +
                                documentInfoRequest.getResourceUri(), ioe);
                LOG.errorContext(requestId, documentGeneratorServiceException, debugMap);

                response = setDocumentResponse(renderResponse, documentInfoResponse);
                return new ResponseObject(ResponseStatus.NOT_RENDERED, response);
            }

            response = setDocumentResponse(renderResponse, documentInfoResponse);

        } else {
            DocumentGeneratorServiceException documentGeneratorServiceException =
                    new DocumentGeneratorServiceException("No data was returned from documentInfoService " +
                            documentInfoRequest.getResourceUri());
            LOG.errorContext(requestId, documentGeneratorServiceException, debugMap);

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
        String url = host + CONTEXT_PATH;

        RenderDocumentRequest requestData = new RenderDocumentRequest();
        requestData.setAssetId(documentInfoResponse.getAssetId());
        requestData.setContentType(documentInfoResponse.getContentType());
        requestData.setData(documentInfoResponse.getData());
        requestData.setDocumentType(documentRequest.getMimeType());
        requestData.setTemplateName(documentInfoResponse.getTemplateName());
        requestData.setLocation(setLocation(documentInfoResponse.getPath()));

        return requestHandler.sendDataToDocumentRenderService(url, requestData);
    }

    /**
     * Set the location the document the document is to be stored at
     *
     * @param path the path to be added
     * @return location the full location path
     */
    private String setLocation(String path) {

        String bucketName = environmentReader.getMandatoryString(DOCUMENT_BUCKET_NAME_ENV_VAR);
        String location = S3 + bucketName + path;

        return location;
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
}
