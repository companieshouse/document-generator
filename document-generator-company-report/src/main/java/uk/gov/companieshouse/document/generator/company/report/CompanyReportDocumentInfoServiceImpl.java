package uk.gov.companieshouse.document.generator.company.report;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.companieshouse.document.generator.company.report.exception.HandlerException;
import uk.gov.companieshouse.document.generator.company.report.handler.CompanyReportDataHandler;
import uk.gov.companieshouse.document.generator.company.report.handler.DissolvedCompanyReportDataHandler;
import uk.gov.companieshouse.document.generator.interfaces.DocumentInfoService;
import uk.gov.companieshouse.document.generator.interfaces.exception.DocumentInfoException;
import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfoRequest;
import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfoResponse;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;

@Service
public class CompanyReportDocumentInfoServiceImpl implements DocumentInfoService {

    public static final String MODULE_NAME_SPACE = "document-generator-company-report";

    private static final Logger LOG = LoggerFactory.getLogger(MODULE_NAME_SPACE);

    @Autowired
    private CompanyReportDataHandler companyReportDataHandler;
    
    @Autowired
    private DissolvedCompanyReportDataHandler dissolvedCompanyReportDataHandler;

    @Override
    public DocumentInfoResponse getDocumentInfo(DocumentInfoRequest documentInfoRequest) throws DocumentInfoException {

        String resourceUri = documentInfoRequest.getResourceUri();
        String requestId = documentInfoRequest.getRequestId();
        String companyNumber = getCompanyNumber(resourceUri);

        final Map<String, Object> debugMap = new HashMap<>();
        debugMap.put("resource_uri", resourceUri);
        debugMap.put("company_number", companyNumber);
        LOG.infoContext(requestId, "Started getting document information for company profile", debugMap);

        try {
            if(resourceUri.startsWith("/dissolved-company-number")) {
                return dissolvedCompanyReportDataHandler.getCompanyReport(companyNumber, requestId);
            } else {                
                return companyReportDataHandler.getCompanyReport(resourceUri, requestId);
            }
        } catch (HandlerException he) {
            LOG.errorContext(requestId, "An error occurred when retrieving the data for the company report", he, debugMap);
            throw new DocumentInfoException("Failed to get company report data for resourceUri: "
                + resourceUri);
        }
    }
    
    /**
     * get the company number from the request uri without including any parameters
     * @param resourceUri the uri string of the request
     * @return the 8 character company number
     */
    private String getCompanyNumber(String resourceUri) {
        Pattern pattern = Pattern.compile("(?<=company-number/).{8}");
        Matcher matcher = pattern.matcher(resourceUri);
        if (matcher.find()) {
            return (matcher.group());
        }
        return null;
    }
}
