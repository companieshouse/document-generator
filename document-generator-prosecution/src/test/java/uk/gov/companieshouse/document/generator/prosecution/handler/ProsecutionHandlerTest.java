package uk.gov.companieshouse.document.generator.prosecution.handler;

import com.sun.org.apache.regexp.internal.RE;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.model.prosecution.prosecutioncase.ProsecutionCaseStatusApi;
import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfoResponse;
import uk.gov.companieshouse.document.generator.prosecution.ProsecutionType;
import uk.gov.companieshouse.document.generator.prosecution.exception.HandlerException;
import uk.gov.companieshouse.document.generator.prosecution.exception.ProsecutionServiceException;
import uk.gov.companieshouse.document.generator.prosecution.mapping.model.ProsecutionDocument;
import uk.gov.companieshouse.document.generator.prosecution.mapping.model.defendant.Defendant;
import uk.gov.companieshouse.document.generator.prosecution.mapping.model.offence.Offence;
import uk.gov.companieshouse.document.generator.prosecution.mapping.model.prosecutioncase.ProsecutionCase;
import uk.gov.companieshouse.document.generator.prosecution.service.ProsecutionService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProsecutionHandlerTest {

    private static final String RESOURCE_URI = "/internal/company/1234/prosecution-cases/4321/defendants/1122";
    private static final String REQUEST_ID = "requestId";

    @Mock
    Defendant defendant;

    @Mock
    ProsecutionCase prosecutionCase;

    @Mock
    List<Offence> offences;

    @Mock
    private ProsecutionService prosecutionService;

    @InjectMocks
    private ProsecutionHandler prosecutionHandler;

    @Test
    @DisplayName("Tests successful get of prosecution document")
    void testGetProsecutionDocument() throws ProsecutionServiceException, HandlerException {
        when(prosecutionService.getDefendant(RESOURCE_URI)).thenReturn(defendant);
        when(prosecutionService.getProsecutionCase("/" + defendant.getLinks().get("prosecution-case"))).thenReturn(prosecutionCase);
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
