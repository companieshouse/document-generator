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
import uk.gov.companieshouse.document.generator.core.models.DocumentGeneratorRequest;
import uk.gov.companieshouse.document.generator.core.models.DocumentGeneratorResponse;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;

import javax.validation.Valid;

import static uk.gov.companieshouse.document.generator.core.DocumentGeneratorApplication.APPLICATION_NAME_SPACE;

@RestController
@RequestMapping(value = "/document-generate", produces = MediaType.APPLICATION_JSON_VALUE)
public class DocumentGeneratorController {

    private static final Logger LOG = LoggerFactory.getLogger(APPLICATION_NAME_SPACE);

    @PostMapping
    @ResponseBody
    public ResponseEntity generateDocument(@Valid @RequestBody DocumentGeneratorRequest documentGeneratorRequest,
                                           BindingResult result) {

        DocumentGeneratorResponse response = new DocumentGeneratorResponse();

        if (result.hasErrors()) {
            LOG.error("error in request body");
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        try {
            response = null;
        } catch (Exception e) {
            LOG.error(e);
        }

        if(response == null) {
            LOG.error("no data has been returned");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // TODO response returns NULL so will only hit the below code once service to get response is implemented
        if(response.getLocation() != null) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            LOG.error("Failed to generate the document");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

