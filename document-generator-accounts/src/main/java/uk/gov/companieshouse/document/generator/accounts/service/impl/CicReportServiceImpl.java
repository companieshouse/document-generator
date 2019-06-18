package uk.gov.companieshouse.document.generator.accounts.service.impl;

import static uk.gov.companieshouse.document.generator.accounts.AccountsDocumentInfoServiceImpl.MODULE_NAME_SPACE;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.companieshouse.api.error.ApiErrorResponseException;
import uk.gov.companieshouse.api.handler.exception.URIValidationException;
import uk.gov.companieshouse.document.generator.accounts.data.cic.CicReportManager;
import uk.gov.companieshouse.document.generator.accounts.exception.ServiceException;
import uk.gov.companieshouse.document.generator.accounts.mapping.cic.model.CicReport;
import uk.gov.companieshouse.document.generator.accounts.service.CicReportService;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;

@Service
public class CicReportServiceImpl implements CicReportService {

    private static final Logger LOG = LoggerFactory.getLogger(MODULE_NAME_SPACE);

    @Autowired
    private CicReportManager cicReportManager;

    @Override
    public CicReport getCicReport(String cicReportLink, String requestId) throws ServiceException {
        try {
            LOG.infoContext(requestId, "Getting cic report data: " + cicReportLink, getDebugMap(cicReportLink));
            return cicReportManager.getCicReport(cicReportLink);
        } catch (URIValidationException | ApiErrorResponseException e) {
            LOG.errorContext(requestId,"Failed to retrieve cic report data: ", e, getDebugMap(cicReportLink));
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    private Map<String, Object> getDebugMap(String resource) {

        Map<String, Object> debugMap = new HashMap<>();
        debugMap.put("resource", resource);

        return debugMap;
    }
}
