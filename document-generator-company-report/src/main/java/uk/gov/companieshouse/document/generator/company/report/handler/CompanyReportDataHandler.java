package uk.gov.companieshouse.document.generator.company.report.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import uk.gov.companieshouse.document.generator.company.report.data.CompanyReportDataManager;
import uk.gov.companieshouse.document.generator.company.report.exception.ApiDataException;
import uk.gov.companieshouse.document.generator.company.report.exception.HandlerException;
import uk.gov.companieshouse.document.generator.company.report.mapping.mappers.CompanyReportMapper;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.CompanyReportApiData;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.CompanyReport;
import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfoResponse;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static uk.gov.companieshouse.document.generator.company.report.CompanyReportDocumentInfoServiceImpl.MODULE_NAME_SPACE;

@Component
public class CompanyReportDataHandler {

    private CompanyReportMapper companyReportMapper;

    private CompanyReportDataManager companyReportDataManager;

    public CompanyReportDataHandler(CompanyReportMapper companyReportMapper,
        CompanyReportDataManager companyReportDataManager) {

        this.companyReportMapper = companyReportMapper;
        this.companyReportDataManager = companyReportDataManager;
    }

    private static final Logger LOG = LoggerFactory.getLogger(MODULE_NAME_SPACE);

    private static final String COMPANY_REPORT = "company-report";

    public DocumentInfoResponse getCompanyReport(String resourceUri, String requestId)
        throws HandlerException {

        RequestParameters requestParameters = setRequestParameters(resourceUri, requestId);

        LOG.infoContext(requestId, "Getting data for report for company number: "
            + requestParameters.getCompanyNumber(), getDebugMap(requestParameters.getResourceUri()));
        return createDocumentInfoResponse(requestParameters, ZonedDateTime.now());
    }

    private DocumentInfoResponse createDocumentInfoResponse(RequestParameters requestParameters,
        ZonedDateTime timeStamp) throws HandlerException {

        DocumentInfoResponse documentInfoResponse = new DocumentInfoResponse();

        documentInfoResponse.setData(toJson(companyReportMapper
                .mapCompanyReport(getCompanyReportData(requestParameters), requestParameters),
            requestParameters, timeStamp));

        documentInfoResponse.setAssetId(COMPANY_REPORT);
        documentInfoResponse.setPath(createPathString());
        documentInfoResponse.setTemplateName("company-report.html");
        documentInfoResponse.setDescriptionIdentifier(COMPANY_REPORT);

        return documentInfoResponse;
    }

    private CompanyReportApiData getCompanyReportData(
        RequestParameters requestParameters) throws HandlerException {

        try {
            return companyReportDataManager.getCompanyReportData(requestParameters);
        } catch (ApiDataException ae) {
            throw new HandlerException("An error occurred whilst obtaining the company report data for company: "
                + requestParameters.getCompanyNumber(), ae);
        }
    }


    private String toJson(CompanyReport companyReport, RequestParameters requestParameters,
        ZonedDateTime timeStamp) throws HandlerException {

        String reportToJson;
        ObjectMapper mapper = new ObjectMapper();

        companyReport.setTimeStampCreated(timeStamp.format(DateTimeFormatter.ofPattern("d MMMM uuuu HH:mm:ss")));

        try {
            LOG.infoContext(requestParameters.getRequestId(),
                "Attempting to convert company report to JSON for company: " + requestParameters.getCompanyNumber(),
                getDebugMap(requestParameters.getResourceUri()));
            reportToJson = mapper.writeValueAsString(companyReport);
        } catch (JsonProcessingException e) {
            throw new HandlerException(
                new StringBuilder("Could not serialise Document data for the generation of the company report for company: ")
                    .append(companyReport.getRegistrationInformation().getCompanyName())
                    .append("-").append(companyReport.getRegistrationInformation().getCompanyNumber()).toString());
        }

        return reportToJson;
    }

    private RequestParameters setRequestParameters(String resourceUri, String requestId) {

        RequestParameters requestParameters = new RequestParameters();
        requestParameters.setCompanyNumber(getCompanyNumberFromUri(resourceUri));
        requestParameters.setRequestId(requestId);
        requestParameters.setResourceUri(resourceUri);

        return requestParameters;
    }

    private String createPathString() {
        return String.format("/%s/%s", COMPANY_REPORT, getUniqueFileName());
    }

    private String getUniqueFileName() {
        UUID uuid = UUID.randomUUID();
        return "companyReport" + uuid.toString() + ".html";
    }

    protected String getCompanyNumberFromUri(String resourceUri) {
        return resourceUri.replaceAll("^/company-number/", "");
    }

    private Map<String, Object> getDebugMap(String resourceUri) {
        Map<String, Object> logMap = new HashMap<>();
        logMap.put("resource", resourceUri);

        return logMap;
    }
}

