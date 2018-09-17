package uk.gov.companieshouse.document.generator.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.companieshouse.document.generator.core.Exception.DocumentGeneratorServiceException;
import uk.gov.companieshouse.document.generator.core.document.render.RenderDocumentRequestHandler;
import uk.gov.companieshouse.document.generator.core.document.render.models.RenderDocumentRequest;
import uk.gov.companieshouse.document.generator.core.document.render.models.RenderDocumentResponse;
import uk.gov.companieshouse.document.generator.core.models.DocumentRequest;
import uk.gov.companieshouse.document.generator.core.models.DocumentResponse;
import uk.gov.companieshouse.document.generator.core.service.DocumentGeneratorService;
import uk.gov.companieshouse.document.generator.core.service.response.ResponseObject;
import uk.gov.companieshouse.document.generator.core.service.response.ResponseStatus;
import uk.gov.companieshouse.document.generator.interfaces.DocumentInfoService;
import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfoRequest;
import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfoResponse;
import uk.gov.companieshouse.environment.EnvironmentReader;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static uk.gov.companieshouse.document.generator.core.DocumentGeneratorApplication.APPLICATION_NAME_SPACE;

@Service
public class DocumentGeneratorServiceImpl implements DocumentGeneratorService {

    private DocumentInfoService documentInfoService;

    private EnvironmentReader environmentReader;

    private RenderDocumentRequestHandler requestHandler;

    private static final String DOCUMENT_RENDER_SERVICE_HOST_ENV_VAR = "DOCUMENT_RENDER_SERVICE_HOST";

    private static final String CONTEXT_PATH = "/document-render/store?is_public=true";

    private static final Logger LOG = LoggerFactory.getLogger(APPLICATION_NAME_SPACE);

    @Autowired
    public DocumentGeneratorServiceImpl(DocumentInfoService documentInfoService, EnvironmentReader environmentReader,
                                        RenderDocumentRequestHandler requestHandler) {
        this.documentInfoService = documentInfoService;
        this.environmentReader = environmentReader;
        this.requestHandler = requestHandler;
    }

    @Override
    public ResponseObject generate(DocumentRequest documentRequest, String requestId) {

        DocumentResponse response;

        final Map < String, Object > debugMap = new HashMap < > ();
        debugMap.put("resource_uri", documentRequest.getResourceUrl());
        debugMap.put("resource_id", documentRequest.getResourceId());

        //TODO addition of get doc gen type from URL to be added in SFA 580

        //TODO currently no impl present, being completed in SFA 567
        DocumentInfoRequest documentInfoRequest = new DocumentInfoRequest();
        DocumentInfoResponse documentInfoResponse = documentInfoService.getDocumentInfo(documentInfoRequest);

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
                return new ResponseObject(ResponseStatus.DOCUMENT_NOT_RENDERED, response);
            }

            response = setDocumentResponse(renderResponse, documentInfoResponse);

        } else {
            DocumentGeneratorServiceException documentGeneratorServiceException =
                    new DocumentGeneratorServiceException("No data was returned from documentInfoService" +
                            documentInfoRequest.getResourceUri());
            LOG.errorContext(requestId, documentGeneratorServiceException, debugMap);

            return new ResponseObject(ResponseStatus.NO_DATA_RETRIEVED, null);
        }

        return new ResponseObject(ResponseStatus.CREATED, response);
    }

    /**
     * Send data to Render Service and generate document
     *
     * @param documentRequest
     * @param documentInfoResponse
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
        requestData.setLocation(documentInfoResponse.getLocation());

        return requestHandler.sendDataToDocumentRenderService(url, requestData);
    }

    /**
     * Set documentResponse for Api
     *
     * @param renderResponse
     * @param documentInfoResponse
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
