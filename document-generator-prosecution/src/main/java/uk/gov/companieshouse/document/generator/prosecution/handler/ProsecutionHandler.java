package uk.gov.companieshouse.document.generator.prosecution.handler;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import uk.gov.companieshouse.api.model.prosecution.prosecutioncase.ProsecutionCaseStatusApi;
import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfoResponse;
import uk.gov.companieshouse.document.generator.prosecution.ProsecutionDocumentInfoService;
import uk.gov.companieshouse.document.generator.prosecution.ProsecutionType;
import uk.gov.companieshouse.document.generator.prosecution.exception.DocumentInfoCreationException;
import uk.gov.companieshouse.document.generator.prosecution.exception.HandlerException;
import uk.gov.companieshouse.document.generator.prosecution.exception.ProsecutionServiceException;
import uk.gov.companieshouse.document.generator.prosecution.mapping.model.ProsecutionDocument;
import uk.gov.companieshouse.document.generator.prosecution.mapping.model.defendant.Defendant;
import uk.gov.companieshouse.document.generator.prosecution.mapping.model.offence.Offence;
import uk.gov.companieshouse.document.generator.prosecution.mapping.model.prosecutioncase.ProsecutionCase;
import uk.gov.companieshouse.document.generator.prosecution.service.ProsecutionService;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;

@Component
public class ProsecutionHandler {

    private static final Logger LOG =
            LoggerFactory.getLogger(ProsecutionDocumentInfoService.MODULE_NAME_SPACE);

    @Autowired
    private ProsecutionService prosecutionService;

    /**
     * Method to get the base prosecution information for dealing with prosecution documents
     * Throws a HandlerException if any exceptions occurred in the service
     * 
     * @param resourceUri the URI for the defendant
     * @return the ProsecutionDocument containing defendant, prosecution case and offence(s)
     *         information
     * @throws HandlerException
     */
    public ProsecutionDocument getProsecutionDocument(String resourceUri)
            throws HandlerException {
        try {
            Defendant defendant = prosecutionService.getDefendant(resourceUri);
            ProsecutionCase prosecutionCase = prosecutionService.getProsecutionCase(defendant.getLinks().get("prosecution-case"));
            List<Offence> offences = prosecutionService.getOffences(defendant.getLinks().get("offences"));
            ProsecutionDocument document = new ProsecutionDocument();
            document.setDefendant(defendant);
            document.setOffences(offences);
            document.setProsecutionCase(prosecutionCase);
            return document;
        } catch (ProsecutionServiceException pse) {
            throw new HandlerException("An error occurred when retrieving data from the service: " + pse);
        }
    }

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
        String documentId = String.format("%s-<number>", type.getResource());
        return String.format("/%s/%s/%s", type.getAssetId(), type.getResource(), documentId);
    }

    /**
     * Method to make a DocumentInfoResponse for an Ultimatum or SJPn document
     * 
     * @param document The document containing the base information on defendant, prosecution case
     *        and offences
     * @param requestId The request id
     * @return the DocumentInfoResponse for an Ultimatum/SJPn
     * @throws HandlerException 
     */
    public DocumentInfoResponse getDocumentResponse(ProsecutionDocument document,
            String requestId, ProsecutionCaseStatusApi status) throws HandlerException {
        switch(status) {
            case ACCEPTED:
                return createProsecutionDocumentResponse(document, requestId, ProsecutionType.ULTIMATUM);
            case ULTIMATUM_ISSUED:
                return createProsecutionDocumentResponse(document, requestId, ProsecutionType.SJPN);
            default:
                throw new HandlerException("Invalid status for Prosecution Case to generate document: " + status);
        }
    }

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
