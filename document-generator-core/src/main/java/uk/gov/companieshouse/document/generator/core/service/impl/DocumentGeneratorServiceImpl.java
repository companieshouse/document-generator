package uk.gov.companieshouse.document.generator.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import uk.gov.companieshouse.document.generator.core.document.render.RenderDocumentRequestHandler;
import uk.gov.companieshouse.document.generator.core.document.render.models.RenderDocumentRequest;
import uk.gov.companieshouse.document.generator.core.document.render.models.RenderDocumentResponse;
import uk.gov.companieshouse.document.generator.core.service.DocumentGeneratorService;
import uk.gov.companieshouse.document.generator.core.service.models.DocumentRequest;
import uk.gov.companieshouse.document.generator.core.service.models.DocumentResponse;
import uk.gov.companieshouse.document.generator.interfaces.DocumentInfoService;
import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfo;
import uk.gov.companieshouse.environment.EnvironmentReader;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;

import static uk.gov.companieshouse.document.generator.core.DocumentGeneratorApplication.APPLICATION_NAME_SPACE;

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
    public DocumentResponse generate(DocumentRequest documentRequest) {

        DocumentInfo documentInfo = null;
        RenderDocumentResponse renderResponse;
        DocumentResponse response = null;

        //TODO addition of get doc gen type from URL to be added in SFA 580

        try {
            //TODO currently no impl present, being completed in SFA 567
            documentInfo = documentInfoService.getDocumentInfo();
        } catch (Exception e){
            LOG.error(e);
        }

        if (documentInfo != null) {
            renderResponse = renderSubmittedDocumentData(documentRequest, documentInfo);
            response = setDocumentResponse(renderResponse, documentInfo);
        }

        if (response != null) {
            return response;
        }

        return null;
    }

    /**
     * Send data to Render Service and generate document
     *
     * @param documentRequest
     * @param documentInfo
     * @return
     */
    private RenderDocumentResponse renderSubmittedDocumentData(DocumentRequest documentRequest,
                                                               DocumentInfo documentInfo) {

        String host = environmentReader.getMandatoryString(DOCUMENT_RENDER_SERVICE_HOST_ENV_VAR);
        String url = host + CONTEXT_PATH;

        RenderDocumentRequest requestData = new RenderDocumentRequest();
        requestData.setAssetId(documentInfo.getAssetId());
        requestData.setContentType(documentRequest.getContentType());
        requestData.setData(documentInfo.getData());
        requestData.setDocumentType(documentRequest.getDocumentType());
        requestData.setTemplateName(documentInfo.getTemplateName());
        requestData.setLocation(documentInfo.getLocation());

        try {
            return requestHandler.sendDataToDocumentRenderService(url, requestData);
        } catch (Exception e) {
            LOG.error(e);
        }

        return null;
    }

    /**
     * Set documentResponse for Api
     *
     * @param renderResponse
     * @param documentInfo
     * @return
     */
    private DocumentResponse setDocumentResponse(RenderDocumentResponse renderResponse, DocumentInfo documentInfo) {

        DocumentResponse response;

        response = new DocumentResponse();
        response.setLocation(renderResponse != null ? renderResponse.getLocation() : null);
        response.setSize(renderResponse != null ? renderResponse.getDocumentSize() : null);
        response.setDescriptionValues(documentInfo.getDescriptionValues());
        response.setDescription(documentInfo.getDescription());
        response.setDescriptionIdentifier(documentInfo.getDescriptionIdentifier());

        return response;
    }
}
