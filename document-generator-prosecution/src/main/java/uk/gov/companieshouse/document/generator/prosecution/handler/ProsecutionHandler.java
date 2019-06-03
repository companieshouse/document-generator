package uk.gov.companieshouse.document.generator.prosecution.handler;

import org.springframework.stereotype.Component;

import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfoResponse;
import uk.gov.companieshouse.document.generator.prosecution.exception.HandlerException;

@Component
public class ProsecutionHandler {
	
	
	
	public DocumentInfoResponse getUltimatumResponse(String resourceUri, String requestId) throws HandlerException {
		DocumentInfoResponse response = new DocumentInfoResponse();
		// get defendant from the service
		return response;
	}

}
