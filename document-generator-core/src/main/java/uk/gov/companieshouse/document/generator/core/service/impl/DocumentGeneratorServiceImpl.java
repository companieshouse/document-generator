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
        DocumentResponse response = null;
        RenderDocumentResponse renderResponse;

        //TODO addition of get doc gen type from URL to be added in SFA 580

        //TODO currently no impl present, being completed in SFA 567
        documentInfo = documentInfoService.getDocumentInfo();

        if (documentInfo != null) {
            renderResponse = renderSubmittedDocumentData(documentRequest, documentInfo);
            response = setDocumentResponse(renderResponse, documentInfo);
        } else {
            //TODO currently no impl present so errors not confirmed, being completed in SFA 567
           Exception e  = new Exception("No data returned from documentInfoService");
           LOG.error(e);
        }

        return response;
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

        DocumentResponse response = new DocumentResponse();

        if (renderResponse != null) {
            response.setLocation(renderResponse.getLocation());
            response.setSize(renderResponse.getDocumentSize());
        }

        response.setDescriptionValues(documentInfo.getDescriptionValues());
        response.setDescription(documentInfo.getDescription());
        response.setDescriptionIdentifier(documentInfo.getDescriptionIdentifier());

        return response;
    }
}
