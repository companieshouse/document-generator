package uk.gov.companieshouse.document.generator.prosecution.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriTemplate;

import uk.gov.companieshouse.api.InternalApiClient;
import uk.gov.companieshouse.api.model.prosecution.defendant.DefendantApi;

@Service
public class ProsecutionService {
	
	@Autowired
	private ApiClientService apiClientService;
	
	private static final UriTemplate GET_DEFENDANT_URI = new UriTemplate("/internal/company/{companyNumber}/prosecution-cases/{prosecutionCaseId}/defendants/{defendantId}");

	public DefendantApi getDefendant(String companyNumber, String prosecutionCaseId, String defendantId) {
		DefendantApi defendantApi;
		InternalApiClient internalApiClient = apiClientService.getApiClient();
		
		String uri = GET_DEFENDANT_URI.expand(companyNumber, prosecutionCaseId, defendantId).toString();
		
		
	}
}
