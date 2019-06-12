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
import uk.gov.companieshouse.document.generator.prosecution.exception.HandlerException;
import uk.gov.companieshouse.document.generator.prosecution.exception.ProsecutionServiceException;
import uk.gov.companieshouse.document.generator.prosecution.mapping.model.ProsecutionDocument;
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

    private static final String RESOURCE_URI = "/internal/company/1234/prosecution-cases/4321/defendants/1122";
    private static final String PROSECUTION_LINK = "/internal/company/00006400/prosecution-cases/483d4e25b760193c8c7a7e437aab76b3";
    private static final String OFFENCES = PROSECUTION_LINK + "/defendants/463c380b792007a5a743bf807223ab6b7cbc1ca2/offences";

    @Mock
    private ProsecutionService prosecutionService;

    @InjectMocks
    private ProsecutionHandler prosecutionHandler;

    @Test
    @DisplayName("Tests successful get of prosecution document")
    void testGetProsecutionDocument() throws ProsecutionServiceException, HandlerException {
        ProsecutionCase prosecutionCase = new ProsecutionCase();
        Defendant defendant = new Defendant();
        List<Offence> offences = new ArrayList<>();

        Map<String, String> linkMap = new HashMap<>();
        linkMap.put("prosecution-case", PROSECUTION_LINK);
        linkMap.put("offences", OFFENCES);

        defendant.setLinks(linkMap);

        when(prosecutionService.getDefendant(RESOURCE_URI)).thenReturn(defendant);
        when(prosecutionService.getProsecutionCase(defendant.getLinks().get("prosecution-case"))).thenReturn(prosecutionCase);
        when(prosecutionService.getOffences(defendant.getLinks().get("offences"))).thenReturn(offences);

        ProsecutionDocument document = prosecutionHandler.getProsecutionDocument(RESOURCE_URI);
        assertNotNull(document);
        assertEquals(defendant, document.getDefendant());
        assertEquals(prosecutionCase, document.getProsecutionCase());
        assertEquals(offences, document.getOffences());
    }

    @Test
    @DisplayName("Tests unsuccessful get of prosecution document")
    void testGetProsecutionDocumentUnsuccessfulDefendant() throws ProsecutionServiceException, HandlerException {
        when(prosecutionService.getDefendant(RESOURCE_URI)).thenThrow(ProsecutionServiceException.class);

        assertThrows(HandlerException.class, () -> prosecutionHandler.getProsecutionDocument(RESOURCE_URI));
    }
}
