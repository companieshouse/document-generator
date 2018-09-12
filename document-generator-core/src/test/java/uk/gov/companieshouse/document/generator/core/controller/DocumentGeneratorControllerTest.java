package uk.gov.companieshouse.document.generator.core.controller;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import uk.gov.companieshouse.document.generator.core.models.DocumentRequest;
import uk.gov.companieshouse.document.generator.core.models.DocumentResponse;
import uk.gov.companieshouse.document.generator.core.service.DocumentGeneratorService;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DocumentGeneratorControllerTest {

    @Mock
    private BindingResult mockBindingResult;

    @Mock
    private DocumentGeneratorService mockDocumentGeneratorService;

    @InjectMocks
    private DocumentGeneratorController documentGeneratorController;

    private static final String DESCRIPTION = "description";

    private static final String DESCRIPTION_IDENTIFIER = "descriptionIdentifier";

    private static final String SIZE = "size";

    private static final String LOCATION = "location";

    @Test
    @DisplayName("Tests if Bad Request error return when binding error found")
    public void errorReturnAsIncompleteRequest() {

        DocumentRequest request = new DocumentRequest();

        when(mockBindingResult.hasErrors()).thenReturn(true);

        ResponseEntity responseEntity = documentGeneratorController.generateDocument(request, mockBindingResult);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("Test if Created returned if response not null and location present")
    public void createdReturned() {

        DocumentRequest request = setDocumentgeneratorRequest();

        DocumentResponse response = new DocumentResponse();
        response.setDescription(DESCRIPTION);
        response.setDescriptionIdentifier(DESCRIPTION_IDENTIFIER);
        response.setSize(SIZE);
        response.setLinks(LOCATION);

        response.setDescriptionValues(setDescriptionValue());

        when(mockBindingResult.hasErrors()).thenReturn(false);
        when(mockDocumentGeneratorService.generate(request)).thenReturn(response);

        ResponseEntity responseEntity = documentGeneratorController.generateDocument(request, mockBindingResult);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(response);
        assertNotNull(response.getLinks());
    }

    @Test
    @DisplayName("Tests if Internal Server Error return as document generator response is null")
    public void errorReturnedAsNullResponse() {

        DocumentRequest request = setDocumentgeneratorRequest();

        when(mockBindingResult.hasErrors()).thenReturn(false);
        when(mockDocumentGeneratorService.generate(request)).thenReturn(null);

        ResponseEntity responseEntity = documentGeneratorController.generateDocument(request, mockBindingResult);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("Tests if Internal Server error return when location missing from response")
    public void errorReturnAsLocationMissingFromResponse() {

        DocumentRequest request = setDocumentgeneratorRequest();

        DocumentResponse response = new DocumentResponse();
        response.setDescription(DESCRIPTION);
        response.setDescriptionIdentifier(DESCRIPTION_IDENTIFIER);
        response.setSize(SIZE);

        response.setDescriptionValues(setDescriptionValue());

        when(mockBindingResult.hasErrors()).thenReturn(false);
        when(mockDocumentGeneratorService.generate(request)).thenReturn(response);

        ResponseEntity responseEntity = documentGeneratorController.generateDocument(request, mockBindingResult);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertNotNull(response);
        assertNull(response.getLinks());
    }

    @Test
    @DisplayName("Tests if Internal Server error returned when exception thrown")
    public void errorReturnedAsExceptionThrown() {

        DocumentRequest request = setDocumentgeneratorRequest();

        when(mockDocumentGeneratorService.generate(request)).thenThrow(RuntimeException.class);

        ResponseEntity responseEntity = documentGeneratorController.generateDocument(request, mockBindingResult);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    /**
     * Set the data for the document generator request
     *
     * @return DocumentRequest
     */
    private DocumentRequest setDocumentgeneratorRequest() {

        DocumentRequest request = new DocumentRequest();
        request.setDocumentType("documentType");
        request.setMimeType("mimeType");
        request.setResourceId("resourceId");
        request.setResourceUrl("resourceUrl");

        return request;
    }

    /**
     * Set the description value
     *
     * @return Map String/Pair for descriptionValues
     */
    private Map<String,String> setDescriptionValue() {

        Map<String, String> descriptionValues = new HashMap<>();
        descriptionValues.put("date", "01/01/1980");

        return descriptionValues;
    }

}
