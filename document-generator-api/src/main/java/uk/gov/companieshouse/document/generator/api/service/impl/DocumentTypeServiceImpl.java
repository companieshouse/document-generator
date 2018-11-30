package uk.gov.companieshouse.document.generator.api.service.impl;

import org.springframework.stereotype.Service;
import uk.gov.companieshouse.document.generator.api.document.DocumentType;
import uk.gov.companieshouse.document.generator.api.exception.ServiceException;
import uk.gov.companieshouse.document.generator.api.service.DocumentTypeService;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static uk.gov.companieshouse.document.generator.api.DocumentGeneratorApplication.APPLICATION_NAME_SPACE;

@Service
public class DocumentTypeServiceImpl implements DocumentTypeService {

    private static final Logger LOG = LoggerFactory.getLogger(APPLICATION_NAME_SPACE);

    private static final String RESOURCE_URI = "resource_uri";

    private static final String RESOURCE_ID = "resource_id";

    private static final String REQUEST_ID = "request_id";

    /**
     * {@inheritDoc}
     */
    @Override
    public DocumentType getDocumentType(Map<String, String> requestParameters) throws ServiceException {

        LOG.infoContext(requestParameters.get(REQUEST_ID),"Getting the document type from resource: "
                + requestParameters.get(RESOURCE_URI), setDebugMap(requestParameters));
        return Arrays.stream(DocumentType.values())
                .filter(docTypeEntry -> requestParameters.get(RESOURCE_URI).matches(docTypeEntry.getPattern()))
                .findFirst().orElseThrow(() -> new ServiceException(
                        "Could not locate the document type from the Uri"));
    }

    private Map<String,Object> setDebugMap(Map<String,String> requestParameters) {

        Map<String, Object> debugMap = new HashMap<>();
        debugMap.put(RESOURCE_URI, requestParameters.get(RESOURCE_URI));
        debugMap.put(RESOURCE_ID, requestParameters.get(RESOURCE_ID));

        return  debugMap;
    }
}
