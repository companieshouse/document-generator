package uk.gov.companieshouse.document.generator.prosecution.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.gov.companieshouse.api.InternalApiClient;
import uk.gov.companieshouse.api.error.ApiErrorResponseException;
import uk.gov.companieshouse.api.handler.exception.URIValidationException;
import uk.gov.companieshouse.api.model.ApiResponse;
import uk.gov.companieshouse.api.model.prosecution.defendant.DefendantApi;
import uk.gov.companieshouse.api.model.prosecution.offence.OffenceApi;
import uk.gov.companieshouse.api.model.prosecution.prosecutioncase.ProsecutionCaseApi;
import uk.gov.companieshouse.document.generator.prosecution.exception.ProsecutionServiceException;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;

import static uk.gov.companieshouse.document.generator.prosecution.ProsecutionDocumentInfoService.MODULE_NAME_SPACE;

@Service
public class ProsecutionService {

    private static final Logger LOG = LoggerFactory.getLogger(MODULE_NAME_SPACE);

    @Autowired
    private ApiClientService apiClientService;

    public DefendantApi getDefendant(String uri) throws ProsecutionServiceException {
        InternalApiClient internalApiClient = apiClientService.getInternalApiClient();
        DefendantApi defendantApi;
        try {
            LOG.info("Getting defendant information from: " + uri);
            ApiResponse<DefendantApi> response = internalApiClient.privateDefendant().get(uri).execute();
            defendantApi = response.getData();
            LOG.info("Successfully retrieved defendant information");
        } catch (ApiErrorResponseException e) {
            throw new ProsecutionServiceException("An error occurred while retrieving the defendant from the SDK: " + e);
        } catch (URIValidationException e) {
            throw new ProsecutionServiceException("Invalid URI to retrieve the defendant: " + e);
        }
        return defendantApi;
    }

    public OffenceApi[] getOffences(String uri) throws ProsecutionServiceException {
        InternalApiClient internalApiClient = apiClientService.getInternalApiClient();
        OffenceApi[] offenceApis;
        try {
            LOG.info("Getting offences information from: " + uri);
            ApiResponse<OffenceApi[]> apiResponse = internalApiClient.privateOffence().list(uri).execute();
            offenceApis = apiResponse.getData();
            LOG.info("Successfully retrieved offences information");
        } catch (ApiErrorResponseException e) {
            throw new ProsecutionServiceException("An error occurred while retrieving offences from the SDK: " + e);
        } catch (URIValidationException e) {
            throw new ProsecutionServiceException("Invalid URI to retrieve offences: " + e);
        }
        return offenceApis;
    }

    public ProsecutionCaseApi getProsecutionCase(String uri) throws ProsecutionServiceException {
        InternalApiClient internalApiClient = apiClientService.getInternalApiClient();
        ProsecutionCaseApi prosecutionCaseApi;
        try {
            LOG.info("Getting prosecution case information from: " + uri);
            ApiResponse<ProsecutionCaseApi> apiResponse = internalApiClient.privateProsecutionCase().get(uri).execute();
            prosecutionCaseApi = apiResponse.getData();
            LOG.info("Successfully retrieved prosecution case information");
        } catch (ApiErrorResponseException e) {
            throw new ProsecutionServiceException("An error occurred while retrieving the prosecution case from the SDK: " + e);
        } catch (URIValidationException e) {
            throw new ProsecutionServiceException("Invalid URI to retrieve the prosecution case: " + e);
        }
        return prosecutionCaseApi;
    }
}
