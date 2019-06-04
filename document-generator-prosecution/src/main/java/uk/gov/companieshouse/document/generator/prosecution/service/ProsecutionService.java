package uk.gov.companieshouse.document.generator.prosecution.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriTemplate;

import uk.gov.companieshouse.api.InternalApiClient;
import uk.gov.companieshouse.api.error.ApiErrorResponseException;
import uk.gov.companieshouse.api.handler.exception.URIValidationException;
import uk.gov.companieshouse.api.model.prosecution.defendant.DefendantApi;
import uk.gov.companieshouse.api.model.prosecution.offence.OffenceApi;
import uk.gov.companieshouse.api.model.prosecution.prosecutioncase.ProsecutionCaseApi;
import uk.gov.companieshouse.document.generator.prosecution.mapping.mappers.ApiToDefendantMapper;
import uk.gov.companieshouse.document.generator.prosecution.mapping.mappers.ApiToOffenceMapper;
import uk.gov.companieshouse.document.generator.prosecution.mapping.mappers.ApiToProsecutionCaseMapper;
import uk.gov.companieshouse.document.generator.prosecution.mapping.model.defendant.Defendant;

@Service
public class ProsecutionService {
	
	@Autowired
	private ApiClientService apiClientService;
	
	@Autowired
	private ApiToDefendantMapper defendantMapper;
	
	@Autowired
	private ApiToOffenceMapper offenceMapper;
	
	@Autowired
	private ApiToProsecutionCaseMapper caseMapper;
	
	private static final UriTemplate GET_DEFENDANT_URI = new UriTemplate("/internal/company/{companyNumber}/prosecution-cases/{prosecutionCaseId}/defendants/{defendantId}");

	private DefendantApi defendantApi;
	
	public Defendant getDefendant(String companyNumber, String prosecutionCaseId, String defendantId) {
		InternalApiClient internalApiClient = apiClientService.getApiClient();

		GET_DEFENDANT_URI.expand(companyNumber, prosecutionCaseId, defendantId);
		try {
			defendantApi = internalApiClient.privateDefendant().get(GET_DEFENDANT_URI.toString()).execute();
		} catch (ApiErrorResponseException e) {
			
			e.printStackTrace();
		} catch (URIValidationException e) {
			
			e.printStackTrace();
		}
		return defendantMapper.apiToDefendant(defendantApi);
	}
	
	public List<OffenceApi> getOffences(){
		List<OffenceApi> offences = new ArrayList<OffenceApi>();
		return offences;
	}
	
	public ProsecutionCaseApi getProsecutionCase() {
		ProsecutionCaseApi prosecutionCase = new ProsecutionCaseApi();
		return prosecutionCase;
	}
}
