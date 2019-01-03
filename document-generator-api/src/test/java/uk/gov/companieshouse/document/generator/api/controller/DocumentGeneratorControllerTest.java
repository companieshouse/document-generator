package uk.gov.companieshouse.document.generator.api.controller;


import org.junit.jupiter.api.BeforeEach;
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
import uk.gov.companieshouse.document.generator.api.models.DocumentRequest;
import uk.gov.companieshouse.document.generator.api.models.DocumentResponse;
import uk.gov.companieshouse.document.generator.api.models.Links;
import uk.gov.companieshouse.document.generator.api.service.DocumentGeneratorService;
import uk.gov.companieshouse.document.generator.api.service.response.ResponseObject;
import uk.gov.companieshouse.document.generator.api.service.response.ResponseStatus;
import uk.gov.companieshouse.document.generator.api.mapper.ApiResponseMapper;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DocumentGeneratorControllerTest {

    @Mock
    private BindingResult mockBindingResult;

    @Mock
    private DocumentGeneratorService mockDocumentGeneratorService;

    @Mock
    private HttpServletRequest mockHttpServletRequest;

    @Mock
    private ApiResponseMapper mockApiResponseMapper;

    @InjectMocks
    private DocumentGeneratorController documentGeneratorController;

    private static final String DESCRIPTION = "description";

    private static final String DESCRIPTION_IDENTIFIER = "descriptionIdentifier";

    private static final String SIZE = "size";

    private static final String LOCATION = "location";

    private static final String REQUEST_ID = "requestId";

    @BeforeEach
    public void setUp() {
        when(mockHttpServletRequest.getHeader(any(String.class))).thenReturn(REQUEST_ID);
    }


    @Test
    @DisplayName("Tests bad request")
    public void testsBadRequestErrorReturned() {

        DocumentRequest request = new DocumentRequest();

        when(mockBindingResult.hasErrors()).thenReturn(true);

        ResponseEntity responseEntity = documentGeneratorController.generateDocument(request, mockBindingResult, mockHttpServletRequest);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("Test successful Creation")
    public void testsSuccessfullCreation() {

        DocumentRequest request = setDocumentgeneratorRequest();

        DocumentResponse response = new DocumentResponse();
        response.setDescription(DESCRIPTION);
        response.setDescriptionIdentifier(DESCRIPTION_IDENTIFIER);
        response.setSize(SIZE);

        Links links = new Links();
        links.setLocation(LOCATION);
        response.setLinks(links);

        response.setDescriptionValues(setDescriptionValue());

        ResponseObject responseObject = new ResponseObject(ResponseStatus.CREATED, response);

        when(mockBindingResult.hasErrors()).thenReturn(false);
        when(mockDocumentGeneratorService.generate(any(DocumentRequest.class), any(String.class))).thenReturn(responseObject);
        when(mockApiResponseMapper.map(any(ResponseObject.class))).thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(responseObject.getData()));

        ResponseEntity responseEntity = documentGeneratorController.generateDocument(request, mockBindingResult, mockHttpServletRequest);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("Tests fails to render")
    public void testsFailedToRenderDocument() {

        DocumentRequest request = setDocumentgeneratorRequest();

        DocumentResponse response = new DocumentResponse();
        response.setDescription(DESCRIPTION);
        response.setDescriptionIdentifier(DESCRIPTION_IDENTIFIER);
        response.setDescriptionValues(setDescriptionValue());

        ResponseObject responseObject = new ResponseObject(ResponseStatus.FAILED_TO_RENDER, response);

        when(mockBindingResult.hasErrors()).thenReturn(false);
        when(mockDocumentGeneratorService.generate(any(DocumentRequest.class), any(String.class))).thenReturn(responseObject);
        when(mockApiResponseMapper.map(any(ResponseObject.class))).thenReturn(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObject.getData()));

        ResponseEntity responseEntity = documentGeneratorController.generateDocument(request, mockBindingResult, mockHttpServletRequest);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("Tests fails to obtain data and render")
    public void testsFailedToObtainDataAndRender() {

        DocumentRequest request = setDocumentgeneratorRequest();

        ResponseObject responseObject = new ResponseObject(ResponseStatus.NO_DATA_RETRIEVED);

        when(mockBindingResult.hasErrors()).thenReturn(false);
        when(mockDocumentGeneratorService.generate(any(DocumentRequest.class), any(String.class))).thenReturn(responseObject);
        when(mockApiResponseMapper.map(any(ResponseObject.class))).thenReturn(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());

        ResponseEntity responseEntity = documentGeneratorController.generateDocument(request, mockBindingResult, mockHttpServletRequest);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("Tests fails to obtain documentType")
    public void testsFailedToObtainDocumentType() {

        DocumentRequest request = setDocumentgeneratorRequest();

        ResponseObject responseObject = new ResponseObject(ResponseStatus.NO_TYPE_FOUND);

        when(mockBindingResult.hasErrors()).thenReturn(false);
        when(mockDocumentGeneratorService.generate(any(DocumentRequest.class), any(String.class))).thenReturn(responseObject);
        when(mockApiResponseMapper.map(any(ResponseObject.class))).thenReturn(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());

        ResponseEntity responseEntity = documentGeneratorController.generateDocument(request, mockBindingResult, mockHttpServletRequest);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
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
        request.setResourceUri("resourceUri");

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
