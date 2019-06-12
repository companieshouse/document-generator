package uk.gov.companieshouse.document.generator.prosecution.handler;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import uk.gov.companieshouse.api.model.prosecution.defendant.DefendantApi;
import uk.gov.companieshouse.api.model.prosecution.offence.OffenceApi;
import uk.gov.companieshouse.api.model.prosecution.prosecutioncase.ProsecutionCaseApi;
import uk.gov.companieshouse.api.model.prosecution.prosecutioncase.ProsecutionCaseStatusApi;
import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfoResponse;
import uk.gov.companieshouse.document.generator.prosecution.ProsecutionType;
import uk.gov.companieshouse.document.generator.prosecution.exception.HandlerException;
import uk.gov.companieshouse.document.generator.prosecution.exception.ProsecutionServiceException;
import uk.gov.companieshouse.document.generator.prosecution.mapping.mappers.ApiToDefendantMapper;
import uk.gov.companieshouse.document.generator.prosecution.mapping.mappers.ApiToOffenceMapper;
import uk.gov.companieshouse.document.generator.prosecution.mapping.mappers.ApiToProsecutionCaseMapper;
import uk.gov.companieshouse.document.generator.prosecution.mapping.model.defendant.Defendant;
import uk.gov.companieshouse.document.generator.prosecution.mapping.model.offence.Offence;
import uk.gov.companieshouse.document.generator.prosecution.mapping.model.prosecutioncase.ProsecutionCase;
import uk.gov.companieshouse.document.generator.prosecution.service.ProsecutionService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ProsecutionHandlerTest {
    private static final String REQUEST_ID = "1";

    private static final String RESOURCE_URI = "/internal/company/1234/prosecution-cases/4321/defendants/1122";
    private static final String PROSECUTION_LINK = "/internal/company/1234/prosecution-cases/4321";
    private static final String OFFENCES = RESOURCE_URI + "/offences";

    private ProsecutionCaseApi prosecutionCaseApi = new ProsecutionCaseApi();
    private DefendantApi defendantApi = new DefendantApi();
    private OffenceApi[] offenceApis = new OffenceApi[2];

    private ProsecutionCase prosecutionCase = new ProsecutionCase();
    private Defendant defendant = new Defendant();
    private List<Offence> offences = new ArrayList<>();

    private ProsecutionType type;

    @Mock
    private ProsecutionService prosecutionService;

    @Mock
    private ApiToProsecutionCaseMapper prosecutionCaseMapper;

    @Mock
    private ApiToDefendantMapper defendantMapper;

    @Mock
    private ApiToOffenceMapper offenceMapper;

    @InjectMocks
    private ProsecutionHandler prosecutionHandler;

    @Test
    @DisplayName("Tests successful get of document info response for generating an ultimatum")
    void testGetUltimatumDocumentResponse() throws HandlerException, ProsecutionServiceException {
        type = ProsecutionType.ULTIMATUM;
        prosecutionCaseApi.setStatus(ProsecutionCaseStatusApi.ACCEPTED);

        DefendantApi defendantApiWithLinks = addLinksToDefendant(defendantApi);

        when(prosecutionService.getDefendant(RESOURCE_URI)).thenReturn(defendantApiWithLinks);
        when(prosecutionService.getProsecutionCase(defendantApiWithLinks.getLinks().get("prosecution-case"))).thenReturn(prosecutionCaseApi);
        when(prosecutionService.getOffences(defendantApiWithLinks.getLinks().get("offences"))).thenReturn(offenceApis);

        when(prosecutionCaseMapper.apiToProsecutionCase(prosecutionCaseApi)).thenReturn(prosecutionCase);
        when(defendantMapper.apiToDefendant(defendantApi)).thenReturn(defendant);
        when(offenceMapper.apiToOffences(offenceApis)).thenReturn(offences);

        DocumentInfoResponse response = prosecutionHandler.getDocumentResponse(REQUEST_ID, RESOURCE_URI);

        assertNotNull(response);
        assertEquals(response.getTemplateName(), type.getTemplate());
        assertEquals(response.getAssetId(), type.getAssetId());
        assertEquals(response.getDescriptionIdentifier(), type.getResource());
    }

    @Test
    @DisplayName("Tests successful get of document info response for generating an SJPn")
    void testGetSJPDocumentResponse() throws HandlerException, ProsecutionServiceException {
        type = ProsecutionType.SJPN;
        prosecutionCaseApi.setStatus(ProsecutionCaseStatusApi.ULTIMATUM_ISSUED);

        DefendantApi defendantApiWithLinks = addLinksToDefendant(defendantApi);

        when(prosecutionService.getDefendant(RESOURCE_URI)).thenReturn(defendantApiWithLinks);
        when(prosecutionService.getProsecutionCase(defendantApiWithLinks.getLinks().get("prosecution-case"))).thenReturn(prosecutionCaseApi);
        when(prosecutionService.getOffences(defendantApiWithLinks.getLinks().get("offences"))).thenReturn(offenceApis);

        when(prosecutionCaseMapper.apiToProsecutionCase(prosecutionCaseApi)).thenReturn(prosecutionCase);
        when(defendantMapper.apiToDefendant(defendantApi)).thenReturn(defendant);
        when(offenceMapper.apiToOffences(offenceApis)).thenReturn(offences);

        DocumentInfoResponse response = prosecutionHandler.getDocumentResponse(REQUEST_ID, RESOURCE_URI);

        assertNotNull(response);
        assertEquals(response.getTemplateName(), type.getTemplate());
        assertEquals(response.getAssetId(), type.getAssetId());
        assertEquals(response.getDescriptionIdentifier(), type.getResource());
    }

    @Test
    @DisplayName("Tests unsuccessful get of prosecution document when trying to retrieve a defendant")
    void testGetProsecutionDocumentUnsuccessfulDefendant() throws ProsecutionServiceException {
        when(prosecutionService.getDefendant(RESOURCE_URI)).thenThrow(ProsecutionServiceException.class);

        assertThrows(HandlerException.class, () -> prosecutionHandler.getDocumentResponse(REQUEST_ID, RESOURCE_URI));
    }

    @Test
    @DisplayName("Tests unsuccessful get of prosecution document when trying to retrieve a prosecution case")
    void testGetProsecutionDocumentUnsuccessfulProsecutionCase() throws ProsecutionServiceException {
        DefendantApi defendantApiWithLinks = addLinksToDefendant(defendantApi);

        when(prosecutionService.getDefendant(RESOURCE_URI)).thenReturn(defendantApiWithLinks);
        when(prosecutionService.getProsecutionCase(defendantApiWithLinks.getLinks().get("prosecution-case"))).thenThrow(ProsecutionServiceException.class);

        assertThrows(HandlerException.class, () -> prosecutionHandler.getDocumentResponse(REQUEST_ID, RESOURCE_URI));
    }

    @Test
    @DisplayName("Tests unsuccessful get of prosecution document when trying to retrieve a list of offences")
    void testGetProsecutionDocumentUnsuccessfulOffences() throws ProsecutionServiceException {
        DefendantApi defendantApiWithLinks = addLinksToDefendant(defendantApi);

        when(prosecutionService.getDefendant(RESOURCE_URI)).thenReturn(defendantApiWithLinks);
        when(prosecutionService.getProsecutionCase(defendantApiWithLinks.getLinks().get("prosecution-case"))).thenThrow(ProsecutionServiceException.class);
        when(prosecutionService.getOffences(defendantApiWithLinks.getLinks().get("offence"))).thenThrow(ProsecutionServiceException.class);

        assertThrows(HandlerException.class, () -> prosecutionHandler.getDocumentResponse(REQUEST_ID, RESOURCE_URI));
    }

    @Test
    @DisplayName("Tests unsuccessful get of prosecution document when prosecution case has wrong status")
    void testGetProsecutionDocumentUnsuccessfulWrongStatus() throws ProsecutionServiceException {
        prosecutionCaseApi.setStatus(ProsecutionCaseStatusApi.REJECTED);

        DefendantApi defendantApiWithLinks = addLinksToDefendant(defendantApi);

        when(prosecutionService.getDefendant(RESOURCE_URI)).thenReturn(defendantApiWithLinks);
        when(prosecutionService.getProsecutionCase(defendantApiWithLinks.getLinks().get("prosecution-case"))).thenReturn(prosecutionCaseApi);
        when(prosecutionService.getOffences(defendantApiWithLinks.getLinks().get("offences"))).thenReturn(offenceApis);

        when(prosecutionCaseMapper.apiToProsecutionCase(prosecutionCaseApi)).thenReturn(prosecutionCase);
        when(defendantMapper.apiToDefendant(defendantApi)).thenReturn(defendant);
        when(offenceMapper.apiToOffences(offenceApis)).thenReturn(offences);

        assertThrows(HandlerException.class, () -> prosecutionHandler.getDocumentResponse(REQUEST_ID, RESOURCE_URI));
    }

    private DefendantApi addLinksToDefendant(DefendantApi defendantApi) {
        Map<String, String> linkMap = new HashMap<>();
        linkMap.put("prosecution-case", PROSECUTION_LINK);
        linkMap.put("offences", OFFENCES);

        defendantApi.setLinks(linkMap);
        return defendantApi;
    }
}
