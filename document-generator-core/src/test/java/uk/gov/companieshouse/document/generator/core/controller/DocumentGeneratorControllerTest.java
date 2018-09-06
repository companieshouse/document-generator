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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DocumentGeneratorControllerTest {

    @Mock
    private BindingResult mockBindingResult;

    @InjectMocks
    private DocumentGeneratorController documentGeneratorController;

    @Test
    @DisplayName("Tests if Internal Server Error return as document generator response is null")
    public void errorReturnAsNullResponse() {

        DocumentGeneratorRequest request = new DocumentGeneratorRequest();
        request.setContentType("content_type");
        request.setDocumentType("document_type");
        request.setId("Id");
        request.setResource("resource");
        request.setResourceId("resources/1/resource/1");
        request.setUserId("user_id");

        ResponseEntity responseEntity = documentGeneratorController.generateDocument(request, mockBindingResult);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("Tests if Bad Request error return when binding error found")
    public void errorReturnAsIncompleteRequest() {

        DocumentGeneratorRequest request = new DocumentGeneratorRequest();

        when(mockBindingResult.hasErrors()).thenReturn(true);

        ResponseEntity responseEntity = documentGeneratorController.generateDocument(request, mockBindingResult);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

}
