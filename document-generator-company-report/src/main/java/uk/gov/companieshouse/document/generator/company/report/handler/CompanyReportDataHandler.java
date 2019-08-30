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


    public DocumentInfoResponse getCompanyReport(String resourceUri, String requestId)
        throws HandlerException {

        String companyNumber = getCompanyNumberFromUri(resourceUri);

        ZonedDateTime timeStamp = ZonedDateTime.now();

        LOG.infoContext(requestId, "Getting data for report for company number: " + companyNumber, getDebugMap(companyNumber));
        return createDocumentInfoResponse(companyNumber, requestId, timeStamp);
    }

    private DocumentInfoResponse createDocumentInfoResponse(String companyNumber,
        String requestId, ZonedDateTime timeStamp) throws HandlerException {

        DocumentInfoResponse documentInfoResponse = new DocumentInfoResponse();

        CompanyReportApiData companyReportApiData = getCompanyReportData(companyNumber, requestId);

        documentInfoResponse.setData(toJson(companyReportMapper
            .mapCompanyReport(companyReportApiData,requestId, companyNumber),
            companyNumber, requestId, timeStamp));

        documentInfoResponse.setAssetId("company-report");
        documentInfoResponse.setPath(createPathString());
        documentInfoResponse.setTemplateName("company-report.html");

        return documentInfoResponse;
    }

    private CompanyReportApiData getCompanyReportData(String companyNumber, String requestId) throws HandlerException {

        try {
            return companyReportDataManager.getCompanyReportData(companyNumber, requestId);
        } catch (ApiDataException ae) {
            throw new HandlerException("An error occurred whilst obtaining the company report data for company: " + companyNumber, ae);
        }
    }

    private String toJson(CompanyReport companyReport, String companyNumber,
        String requestId, ZonedDateTime timeStamp) throws HandlerException {

        String reportToJson;
        ObjectMapper mapper = new ObjectMapper();

        companyReport.setTimeStampCreated(timeStamp.format(DateTimeFormatter.ofPattern("d MMMM uuuu HH:mm:ss")));

        try {
            LOG.infoContext(requestId,"Attempting to convert company report to JSON",  getDebugMap(companyNumber));
            reportToJson = mapper.writeValueAsString(companyReport);
        } catch (JsonProcessingException e) {
            throw new HandlerException(
                new StringBuilder("Could not serialise Document data for the generation of the company report for company: ")
                    .append(companyReport.getRegistrationInformation().getCompanyName())
                    .append("-").append(companyReport.getRegistrationInformation().getCompanyNumber()).toString());
        }

        return reportToJson;
    }

    private String createPathString() {
        return String.format("/%s/%s", "company-report", getUniqueFileName());
    }

    private String getUniqueFileName() {
        UUID uuid = UUID.randomUUID();
        return "companyReport" + uuid.toString() + ".html";
    }

    protected String getCompanyNumberFromUri(String resourceUri) {
        return resourceUri.replaceAll("^/company-number/", "");
    }

    private Map<String, Object> getDebugMap(String companyNumber) {
        Map<String, Object> logMap = new HashMap<>();
        logMap.put("COMPANY_NUMBER", companyNumber);

        return logMap;
    }
}
