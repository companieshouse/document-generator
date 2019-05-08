package uk.gov.companieshouse.document.generator.company.report;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.companieshouse.api.model.company.CompanyProfileApi;
import uk.gov.companieshouse.document.generator.company.report.exception.ServiceException;
import uk.gov.companieshouse.document.generator.company.report.model.CompanyReport;
import uk.gov.companieshouse.document.generator.company.report.service.CompanyService;
import uk.gov.companieshouse.document.generator.interfaces.DocumentInfoService;
import uk.gov.companieshouse.document.generator.interfaces.exception.DocumentInfoException;
import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfoRequest;
import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfoResponse;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class CompanyReportDocumentInfoServiceImpl implements DocumentInfoService {

    public static final String MODULE_NAME_SPACE = "document-generator-company-report";

    private static final Logger LOG = LoggerFactory.getLogger(MODULE_NAME_SPACE);

    @Autowired
    private CompanyService companyService;

    @Override
    public DocumentInfoResponse getDocumentInfo(DocumentInfoRequest documentInfoRequest) throws DocumentInfoException {

        String resourceUri = documentInfoRequest.getResourceUri();
        String requestId = documentInfoRequest.getRequestId();

        final Map< String, Object > debugMap = new HashMap< >();
        debugMap.put("resource_uri", resourceUri);

        LOG.infoContext(requestId,"Started getting document info for company profile", debugMap);

        CompanyProfileApi companyProfileApi;
        try {
           companyProfileApi = companyService.getCompanyProfile(getCompanyNumberFromUri(resourceUri));
        } catch (ServiceException se) {
            throw new DocumentInfoException("error occurred obtaining the company profile", se);
        }

        return createDocumentInfoResponse(companyProfileApi);
    }

    private DocumentInfoResponse createDocumentInfoResponse(CompanyProfileApi companyProfileApi) throws DocumentInfoException {

        DocumentInfoResponse documentInfoResponse = new DocumentInfoResponse();
        documentInfoResponse.setData(createData(companyProfileApi));
        documentInfoResponse.setAssetId("accounts");
        documentInfoResponse.setPath(createPathString());
        documentInfoResponse.setTemplateName("company-report.html");

        return documentInfoResponse;
    }

    private String createData(CompanyProfileApi companyProfileApi) throws DocumentInfoException {

        CompanyReport companyReport = new CompanyReport();
        companyReport.setCompanyProfileApi(companyProfileApi);

        return toJson(companyReport);
    }

    private String toJson(CompanyReport companyReport) throws DocumentInfoException {

        String reportToJson;
        ObjectMapper mapper = new ObjectMapper();

        try {
            reportToJson = mapper.writeValueAsString(companyReport);
        } catch (JsonProcessingException e) {
            throw new DocumentInfoException(
                "Could not serialise Document Info for the company report for resource: "
                    + companyReport.getCompanyProfileApi().getCompanyNumber());
        }

        return reportToJson;
    }

    private String createPathString() {
        return String.format("/%s/%s", "accounts", getUniqueFileName());
    }

    public String getUniqueFileName() {
        UUID uuid = UUID.randomUUID();
        return "companyReport" + uuid.toString() + ".html";
    }

    private String getCompanyNumberFromUri(String resourceUri) {
        return  resourceUri.replaceAll("[^\\d.]", "");
    }
}
