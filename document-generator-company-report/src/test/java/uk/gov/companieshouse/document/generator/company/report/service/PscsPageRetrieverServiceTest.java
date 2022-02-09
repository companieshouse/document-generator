package uk.gov.companieshouse.document.generator.company.report.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.ApiClient;
import uk.gov.companieshouse.api.error.ApiErrorResponseException;
import uk.gov.companieshouse.api.handler.exception.URIValidationException;
import uk.gov.companieshouse.api.handler.psc.PscsResourceHandler;
import uk.gov.companieshouse.api.handler.psc.request.PscsList;
import uk.gov.companieshouse.api.model.ApiResponse;
import uk.gov.companieshouse.api.model.psc.PscsApi;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PscsPageRetrieverServiceTest {

    private static final String PSCS_URI = "/company/00000000/persons-with-significant-control";
    private static final int ITEMS_PER_PAGE = 100;

    @InjectMocks
    private PscsPageRetrieverService pageRetrieverService;

    @Mock
    private ApiClient apiClient;

    @Mock
    private PscsResourceHandler pscsResourceHandler;

    @Mock
    private PscsList pscsList;

    @Mock
    private ApiResponse<PscsApi> responseWithData;

    @Test
    @DisplayName("retrieve() gets PscsApi instance successfully")
    void retrieveSuccessfully() throws ApiErrorResponseException, URIValidationException {

        // Given
        when(apiClient.pscs()).thenReturn(pscsResourceHandler);
        when(pscsResourceHandler.list(PSCS_URI)).thenReturn(pscsList);

        when(pscsList.execute()).thenReturn(responseWithData);
        when(responseWithData.getData()).thenReturn(createPscsApi());

        // When
        pageRetrieverService.retrieve(PSCS_URI, apiClient, ITEMS_PER_PAGE);
    }

    private PscsApi createPscsApi() {
        final PscsApi api = new PscsApi();
        api.setActiveCount(2L);
        return api;
    }
}