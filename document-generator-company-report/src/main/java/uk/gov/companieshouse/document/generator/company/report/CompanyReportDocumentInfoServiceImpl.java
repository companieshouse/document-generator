package uk.gov.companieshouse.document.generator.company.report;

import org.springframework.beans.factory.annotation.Autowired;
import uk.gov.companieshouse.document.generator.company.report.exception.HandlerException;
import uk.gov.companieshouse.document.generator.company.report.handler.CompanyReportDataHandler;
import uk.gov.companieshouse.document.generator.interfaces.DocumentInfoService;
import uk.gov.companieshouse.document.generator.interfaces.exception.DocumentInfoException;
import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfoRequest;
import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfoResponse;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class CompanyReportDocumentInfoServiceImpl implements DocumentInfoService {

    public static final String MODULE_NAME_SPACE = "document-generator-company-report";

    private static final Logger LOG = LoggerFactory.getLogger(MODULE_NAME_SPACE);

    @Autowired
    private CompanyReportDataHandler companyReportDataHandler;

    @Override
    public DocumentInfoResponse getDocumentInfo(DocumentInfoRequest documentInfoRequest) throws DocumentInfoException {

        String resourceUri = documentInfoRequest.getResourceUri();
        String requestId = documentInfoRequest.getRequestId();

        final Map<String, Object> debugMap = new HashMap<>();
        debugMap.put("resource_uri", resourceUri);

        LOG.infoContext(requestId, "Started getting document information for company profile", debugMap);

        try {
            return companyReportDataHandler.getCompanyReport(resourceUri, requestId);
        } catch (HandlerException he) {
            LOG.errorContext(requestId, "An error occurred when retrieving the data for the company report", he, debugMap);
            throw new DocumentInfoException("Failed to get company report data for resourceUri: "
                + resourceUri);
        }
    }
}
