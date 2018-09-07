package uk.gov.companieshouse.document.generator.core.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.companieshouse.document.generator.core.models.DocumentRequest;
import uk.gov.companieshouse.document.generator.core.models.DocumentResponse;
import uk.gov.companieshouse.document.generator.core.service.DocumentGeneratorService;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;

import javax.validation.Valid;

import static uk.gov.companieshouse.document.generator.core.DocumentGeneratorApplication.APPLICATION_NAME_SPACE;

@RestController
@RequestMapping(value = "/document-generate", produces = MediaType.APPLICATION_JSON_VALUE)
public class DocumentGeneratorController {

    private static final Logger LOG = LoggerFactory.getLogger(APPLICATION_NAME_SPACE);

    private DocumentGeneratorService documentGeneratorService;

    @PostMapping
    @ResponseBody
    public ResponseEntity generateDocument(@Valid @RequestBody DocumentRequest documentRequest,
                                           BindingResult result) {

        DocumentResponse response;

        if (result.hasErrors()) {
            LOG.error("error in request body");
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        try {
            response = documentGeneratorService.generate(documentRequest);
        } catch (Exception e) {
            LOG.error(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(response == null) {
            LOG.error("no data has been returned");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(response.getLocation() != null) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            LOG.error("Failed to generate the document");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

