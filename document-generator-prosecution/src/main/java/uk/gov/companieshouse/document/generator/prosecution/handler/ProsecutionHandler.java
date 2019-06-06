package uk.gov.companieshouse.document.generator.prosecution.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfoResponse;
import uk.gov.companieshouse.document.generator.prosecution.DocumentInfoCreationException;
import uk.gov.companieshouse.document.generator.prosecution.ProsecutionDocumentInfoService;
import uk.gov.companieshouse.document.generator.prosecution.exception.HandlerException;
import uk.gov.companieshouse.document.generator.prosecution.mapping.model.UltimatumDocument;
import uk.gov.companieshouse.document.generator.prosecution.mapping.model.defendant.Defendant;
import uk.gov.companieshouse.document.generator.prosecution.service.ProsecutionService;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;

@Component
public class ProsecutionHandler {
	
	private static final Logger LOG = LoggerFactory.getLogger(ProsecutionDocumentInfoService.MODULE_NAME_SPACE);
	
	@Autowired
	ProsecutionService prosecutionService;

//	private String companyNumber;
//	private String prosecutionCaseId;
//	private String defendantId;
	
	public DocumentInfoResponse getUltimatumResponse(String resourceUri, String requestId) throws HandlerException {
		DocumentInfoResponse response = new DocumentInfoResponse();
//		setUriValues(resourceUri);
		Defendant defendant = prosecutionService.getDefendant(resourceUri);

		UltimatumDocument document = new UltimatumDocument();
		document.setDefendant(defendant);
		
		response.setAssetId("accounts");
		response.setPath(createPathString("ultimatum"));
		response.setTemplateName("ultimatum.html");
		//response.setDescriptionIdentifier("ultimatum");
		try {
			response.setData(convertToJson(document, "Ultimatum"));
		} catch (DocumentInfoCreationException e) {
			//LOG.error("Error creating document info response for ultimatum for defendant " + defendantId);
		}
		return response;
	}
	
	private String convertToJson(Object document, String type) throws DocumentInfoCreationException {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(document);
		} catch (JsonProcessingException e) {
			throw new DocumentInfoCreationException("Could not serialise document info for " + type);
		}
	}
	
	/**
	 * method to get the company number, prosecution case id and defendant id from the url
	 * @param resourceUri
	 */
//	private void setUriValues(String resourceUri) {
//		String[] elements = resourceUri.split("/");
//		companyNumber = elements[3];
//		prosecutionCaseId = elements[5];
//		defendantId = elements[7];
//	}
//
	private String createPathString(String docType) {
		StringBuilder path = new StringBuilder("/prosecution/" + docType);
		// TODO append the defendant UID to the file name
		path.append(".html");
		return path.toString();
	}

}
