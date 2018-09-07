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
import uk.gov.companieshouse.document.generator.core.models.DocumentGeneratorRequest;
import uk.gov.companieshouse.document.generator.core.models.DocumentGeneratorResponse;
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

    @Test
    @DisplayName("Tests if Bad Request error return when binding error found")
    public void errorReturnAsIncompleteRequest() {

        DocumentGeneratorRequest request = new DocumentGeneratorRequest();

        when(mockBindingResult.hasErrors()).thenReturn(true);

        ResponseEntity responseEntity = documentGeneratorController.generateDocument(request, mockBindingResult);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("Test if Created returned if response not null and location present")
    public void createdReturned() {

        DocumentGeneratorRequest request = setDocumentgeneratorRequest();

        DocumentGeneratorResponse response = new DocumentGeneratorResponse();
        response.setDescription("description");
        response.setDescriptionIdentifier("desctiptionIdentifier");
        response.setSize("size");
        response.setLocation("location");

        response.setDescriptionValues(setDescriptionValue());

        when(mockBindingResult.hasErrors()).thenReturn(false);
        when(mockDocumentGeneratorService.generate(request)).thenReturn(response);

        ResponseEntity responseEntity = documentGeneratorController.generateDocument(request, mockBindingResult);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(response);
        assertNotNull(response.getLocation());
    }

    @Test
    @DisplayName("Tests if Internal Server Error return as document generator response is null")
    public void errorReturnedAsNullResponse() {

        DocumentGeneratorRequest request = setDocumentgeneratorRequest();

        when(mockBindingResult.hasErrors()).thenReturn(false);
        when(mockDocumentGeneratorService.generate(request)).thenReturn(null);

        ResponseEntity responseEntity = documentGeneratorController.generateDocument(request, mockBindingResult);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("Tests if Internal Server error return when location missing from response")
    public void errorReturnAsLocationMissingFromResponse() {

        DocumentGeneratorRequest request = setDocumentgeneratorRequest();

        DocumentGeneratorResponse response = new DocumentGeneratorResponse();
        response.setDescription("description");
        response.setDescriptionIdentifier("desctiptionIdentifier");
        response.setSize("size");

        response.setDescriptionValues(setDescriptionValue());

        when(mockBindingResult.hasErrors()).thenReturn(false);
        when(mockDocumentGeneratorService.generate(request)).thenReturn(response);

        ResponseEntity responseEntity = documentGeneratorController.generateDocument(request, mockBindingResult);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertNotNull(response);
        assertNull(response.getLocation());
    }

    @Test
    @DisplayName("Tests if Internal Server error returned when exception thrown")
    public void errorReturnedAsExceptionThrown() {

        DocumentGeneratorRequest request = setDocumentgeneratorRequest();

        when(mockDocumentGeneratorService.generate(request)).thenThrow(RuntimeException.class);

        ResponseEntity responseEntity = documentGeneratorController.generateDocument(request, mockBindingResult);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    /**
     * Set the data for the document generator request
     *
     * @return
     */
    private DocumentGeneratorRequest setDocumentgeneratorRequest() {

        DocumentGeneratorRequest request = new DocumentGeneratorRequest();
        request.setContentType("content_type");
        request.setDocumentType("document_type");
        request.setId("Id");
        request.setResource("resource");
        request.setResourceId("resources/1/resource/1");
        request.setUserId("user_id");

        return request;
    }

    /**
     * Set the description value
     *
     * @return
     */
    private Map<String,String> setDescriptionValue() {

        Map<String, String> descriptionValues = new HashMap<>();
        descriptionValues.put("date", "01/01/1980");

        return descriptionValues;
    }

}
