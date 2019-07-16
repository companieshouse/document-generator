package uk.gov.companieshouse.document.generator.prosecution.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import uk.gov.companieshouse.api.model.prosecution.defendant.DefendantApi;
import uk.gov.companieshouse.api.model.prosecution.offence.OffenceApi;
import uk.gov.companieshouse.api.model.prosecution.prosecutioncase.ProsecutionCaseApi;
import uk.gov.companieshouse.api.model.prosecution.prosecutioncase.ProsecutionCaseStatusApi;
import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfoResponse;
import uk.gov.companieshouse.document.generator.prosecution.ProsecutionDocumentInfoService;
import uk.gov.companieshouse.document.generator.prosecution.ProsecutionType;
import uk.gov.companieshouse.document.generator.prosecution.exception.DocumentInfoCreationException;
import uk.gov.companieshouse.document.generator.prosecution.exception.HandlerException;
import uk.gov.companieshouse.document.generator.prosecution.exception.ProsecutionServiceException;
import uk.gov.companieshouse.document.generator.prosecution.mapping.mappers.ApiToDefendantMapper;
import uk.gov.companieshouse.document.generator.prosecution.mapping.mappers.ApiToOffenceMapper;
import uk.gov.companieshouse.document.generator.prosecution.mapping.mappers.ApiToProsecutionCaseMapper;
import uk.gov.companieshouse.document.generator.prosecution.mapping.model.ProsecutionDocument;
import uk.gov.companieshouse.document.generator.prosecution.service.ProsecutionService;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;

@Component
public class ProsecutionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ProsecutionDocumentInfoService.MODULE_NAME_SPACE);

    @Autowired
    private ProsecutionService prosecutionService;

    @Autowired
    private ApiToProsecutionCaseMapper prosecutionCaseMapper;

    @Autowired
    private ApiToDefendantMapper defendantMapper;

    @Autowired
    private ApiToOffenceMapper offenceMapper;

    /**
     * Method to convert an annotated document to a JSON string
     * 
     * @param document the annotated document
     * @param requestId the id of the request being made
     * @return the mapped JSON string
     * @throws DocumentInfoCreationException
     */
    private String convertToJson(Object document, String requestId)
            throws DocumentInfoCreationException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(document);
        } catch (JsonProcessingException e) {
            throw new DocumentInfoCreationException(
                    "Could not serialise document info for request id " + requestId);
        }
    }

    /**
     * Method to create the path where the completed document is stored
     * 
     * @param type of the prosecution document to store e.g. Ultimatum, SJPn, witness statements
     * @return the path to store the finished document
     */
    private String createLocationLink(ProsecutionType type) {
        return String.format("/%s/%s", type.getAssetId(), type.getResource());
    }

    /**
     * Retrieves prosecution data from service including case status,
     * transforms prosecution data to appropriate models for the document
     * and chooses the right document to generate based on case status
     *
     * @param requestId
     * @param resourceUri URI of defendant
     * @return
     * @throws HandlerException
     */
    public DocumentInfoResponse getDocumentResponse(String requestId, String resourceUri) throws HandlerException {
        ProsecutionCaseStatusApi status;
        ProsecutionDocument document;

        try{
            DefendantApi defendant = prosecutionService.getDefendant(resourceUri);
            ProsecutionCaseApi prosecutionCase = prosecutionService.getProsecutionCase(defendant.getLinks().get("prosecution-case"));
            status = prosecutionCase.getStatus();
            OffenceApi[] offences = prosecutionService.getOffences(defendant.getLinks().get("offences"));
            document = new ProsecutionDocument();
            document.setDefendant(defendantMapper.apiToDefendant(defendant));
            document.setOffences(offenceMapper.apiToOffences(offences));
            document.setProsecutionCase(prosecutionCaseMapper.apiToProsecutionCase(prosecutionCase));
        } catch (ProsecutionServiceException pse) {
            throw new HandlerException("An error occurred when retrieving data from the service: " + pse);
        }
        switch(status) {
            case ACCEPTED:
                return createProsecutionDocumentResponse(document, requestId, ProsecutionType.ULTIMATUM);
            case ULTIMATUM_ISSUED:
                return createProsecutionDocumentResponse(document, requestId, ProsecutionType.SJPN);
            default:
                throw new HandlerException("Invalid status for Prosecution Case to generate document: " + status);
        }
    }

    /**
     * Builds a DocumentInfoResponse for an Ultimatum or SJPn document
     *
     * @param data Document consisting of prosecution case, defendant and offence information
     * @param requestId
     * @param type Type of ProsecutionDocument - either Ultimatum or SJPn
     * @return DocumentInfoResponse for an Ultimatum or SJPn
     * @throws HandlerException
     */
    private DocumentInfoResponse createProsecutionDocumentResponse(ProsecutionDocument data, String requestId, ProsecutionType type) throws HandlerException {
        DocumentInfoResponse response = new DocumentInfoResponse();
        response.setAssetId(type.getAssetId());
        response.setPath(createLocationLink(type));
        response.setTemplateName(type.getTemplate());
        response.setDescriptionIdentifier(type.getResource());

        try {
            response.setData(convertToJson(data, requestId));
        } catch (DocumentInfoCreationException e) {
            throw new HandlerException("Error creating prosecution document info response for request: " + type, e);
        }

        return response;
    }

}
