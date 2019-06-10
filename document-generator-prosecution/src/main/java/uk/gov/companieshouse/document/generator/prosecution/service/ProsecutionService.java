package uk.gov.companieshouse.document.generator.prosecution.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriTemplate;

import uk.gov.companieshouse.api.InternalApiClient;
import uk.gov.companieshouse.api.error.ApiErrorResponseException;
import uk.gov.companieshouse.api.handler.exception.URIValidationException;
import uk.gov.companieshouse.api.model.ApiResponse;
import uk.gov.companieshouse.api.model.prosecution.defendant.DefendantApi;
import uk.gov.companieshouse.api.model.prosecution.offence.OffenceApi;
import uk.gov.companieshouse.api.model.prosecution.prosecutioncase.ProsecutionCaseApi;
import uk.gov.companieshouse.document.generator.prosecution.mapping.mappers.ApiToDefendantMapper;
import uk.gov.companieshouse.document.generator.prosecution.mapping.mappers.ApiToOffenceMapper;
import uk.gov.companieshouse.document.generator.prosecution.mapping.mappers.ApiToProsecutionCaseMapper;
import uk.gov.companieshouse.document.generator.prosecution.mapping.model.defendant.Defendant;
import uk.gov.companieshouse.document.generator.prosecution.mapping.model.offence.Offence;
import uk.gov.companieshouse.document.generator.prosecution.mapping.model.prosecutioncase.ProsecutionCase;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;

import static uk.gov.companieshouse.document.generator.prosecution.ProsecutionDocumentInfoService.MODULE_NAME_SPACE;

@Service
public class ProsecutionService {

	private static final Logger LOG = LoggerFactory.getLogger(MODULE_NAME_SPACE);
	
	@Autowired
	private ApiClientService apiClientService;
	
	@Autowired
	private ApiToDefendantMapper defendantMapper;
	
	@Autowired
	private ApiToOffenceMapper offenceMapper;
	
	@Autowired
	private ApiToProsecutionCaseMapper caseMapper;
	
	public Defendant getDefendant(String uri) {
	    InternalApiClient internalApiClient = getInternalApiClient();
		DefendantApi defendantApi = new DefendantApi();
		try {
		    LOG.info("Call to DEFENDANT API : " + uri);
		    LOG.info("Getting defendant information");
			ApiResponse<DefendantApi> response = internalApiClient.privateDefendant().get(uri).execute();
			defendantApi = response.getData();
			LOG.info("Defendant information : " + defendantApi.toString());
		} catch (ApiErrorResponseException e) {
            LOG.error("ApiErrorResponseException" + e);
		} catch (URIValidationException e) {
            LOG.error("UriValidationException" + e);
		}
		return defendantMapper.apiToDefendant(defendantApi);
	}
	
	public List<Offence> getOffences(String uri){
	    InternalApiClient internalApiClient = getInternalApiClient();
	    OffenceApi offenceApi = new OffenceApi();
	    try {
            ApiResponse<List> apiResponse = internalApiClient.privateOffence().list(uri).execute();
            List<OffenceApi> responseList = apiResponse.getData();
        } catch (ApiErrorResponseException e) {
            LOG.error("ApiErrorResponseException " + e);
        } catch (URIValidationException e) {
            LOG.error("URIValidationException " + e);
        }
	    return new ArrayList<>();
	}
	
	public ProsecutionCase getProsecutionCase(String uri) {
	    InternalApiClient internalApiClient = getInternalApiClient();
        ProsecutionCaseApi prosecutionCaseApi = new ProsecutionCaseApi();
		try {
            ApiResponse<ProsecutionCaseApi> apiResponse = internalApiClient.privateProsecutionCase().get(uri).execute();
            prosecutionCaseApi = apiResponse.getData();
        } catch (ApiErrorResponseException e ) { 
            LOG.error("ApiErrorResponseException " + e);
	    } catch (URIValidationException e) {
	        LOG.error("UriValidationException" + e);
        }
		return caseMapper.apiToProsecutionCase(prosecutionCaseApi);
	}
	
	private InternalApiClient getInternalApiClient() {
	    InternalApiClient internalApiClient = apiClientService.getApiClient();
        boolean result = internalApiClient != null;
        LOG.info("InternalApiClient: " + result);
        return internalApiClient;
	}
}
		