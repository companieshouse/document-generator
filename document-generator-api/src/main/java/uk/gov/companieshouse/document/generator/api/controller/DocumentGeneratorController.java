package uk.gov.companieshouse.document.generator.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.companieshouse.document.generator.api.models.DocumentRequest;
import uk.gov.companieshouse.document.generator.api.service.DocumentGeneratorService;
import uk.gov.companieshouse.document.generator.api.service.response.ResponseObject;
import uk.gov.companieshouse.document.generator.api.mapper.ApiResponseMapper;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

import static uk.gov.companieshouse.document.generator.api.DocumentGeneratorApplication.APPLICATION_NAME_SPACE;

@RestController
@RequestMapping(value = "/private/documents/generate", produces = MediaType.APPLICATION_JSON_VALUE)
public class DocumentGeneratorController {

    private static final Logger LOG = LoggerFactory.getLogger(APPLICATION_NAME_SPACE);

    @Autowired
    private DocumentGeneratorService documentGeneratorService;

    @Autowired
    private ApiResponseMapper apiResponseMapper;

    @PostMapping
    @ResponseBody
    public ResponseEntity generateDocument(@Valid @RequestBody DocumentRequest documentRequest,
                                           BindingResult result, HttpServletRequest request) {

        String requestId = request.getHeader("X-Request-Id");

        if (result.hasErrors()) {
            final Map<String, Object> debugMap = new HashMap<>();
            debugMap.put("resource_uri", documentRequest.getResourceUri());
            debugMap.put("resource_id", documentRequest.getResourceId());
            LOG.debugRequest(request, "error in request body", debugMap);
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        ResponseObject responseObject = documentGeneratorService.generate(documentRequest, requestId);

        return apiResponseMapper.map(responseObject);
    }
}

