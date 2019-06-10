package uk.gov.companieshouse.document.generator.company.report.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.companieshouse.api.model.company.CompanyProfileApi;
import uk.gov.companieshouse.document.generator.company.report.exception.HandlerException;
import uk.gov.companieshouse.document.generator.company.report.exception.MapperException;
import uk.gov.companieshouse.document.generator.company.report.exception.ServiceException;
import uk.gov.companieshouse.document.generator.company.report.mapping.mappers.CompanyReportMapper;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.CompanyReportApiData;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.CompanyReport;
import uk.gov.companieshouse.document.generator.company.report.service.CompanyService;
import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfoResponse;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static uk.gov.companieshouse.document.generator.company.report.CompanyReportDocumentInfoServiceImpl.MODULE_NAME_SPACE;

@Component
public class CompanyReportDataHandler {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CompanyReportMapper companyReportMapper;

    private static final Logger LOG = LoggerFactory.getLogger(MODULE_NAME_SPACE);

    private static final String DATE_TIME_FORMAT = "dd-MMMM-yyyy";

    public DocumentInfoResponse getCompanyReport(String resourceUri, String requestId)
        throws HandlerException {

        String companyNumber = getCompanyNumberFromUri(resourceUri);

        try {
            LOG.infoContext(requestId, "Getting data for report for company number: " + companyNumber, getDebugMap(companyNumber));
            return createDocumentInfoResponse(companyNumber, requestId);
        } catch (MapperException e) {
            LOG.errorContext(requestId,"Failed to get data for report for company number " + companyNumber, e, getDebugMap(companyNumber));
            throw new HandlerException(e.getMessage(), e.getCause());
        }
    }

    private DocumentInfoResponse createDocumentInfoResponse(String companyNumber, String requestId)
        throws HandlerException, MapperException {

        DocumentInfoResponse documentInfoResponse = new DocumentInfoResponse();

        documentInfoResponse.setData(getCompanyReportData(companyNumber, requestId));
        documentInfoResponse.setAssetId("company-report");
        documentInfoResponse.setPath(createPathString());
        documentInfoResponse.setTemplateName("company-report.html");

        return documentInfoResponse;
    }

    private String getCompanyReportData(String companyNumber,  String requestId) throws HandlerException, MapperException {

        CompanyReportApiData companyReportApiData = new CompanyReportApiData();

        CompanyProfileApi companyProfileApi = getCompanyProfile(companyNumber, requestId);

        companyReportApiData.setCompanyProfileApi(companyProfileApi);

        return toJson(companyReportMapper
            .mapCompanyReport(companyReportApiData),
            companyNumber,
            requestId);
    }


    private String toJson(CompanyReport companyReport, String companyNumber,
                          String requestId) throws HandlerException {

        String reportToJson;
        ObjectMapper mapper = new ObjectMapper();
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)));
        mapper.registerModule(javaTimeModule);
        mapper.setDateFormat(new SimpleDateFormat(DATE_TIME_FORMAT));

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

    private CompanyProfileApi getCompanyProfile(String companyNumber, String requestId) throws HandlerException {

        try {
            LOG.infoContext(requestId,"Attempting to retrieve company profile", getDebugMap(companyNumber));
            return companyService.getCompanyProfile(companyNumber);
        } catch (ServiceException se) {
            throw new HandlerException("error occurred obtaining the company profile", se);
        }
    }

    private String createPathString() {
        return String.format("/%s/%s", "company-report", getUniqueFileName());
    }

    private String getUniqueFileName() {
        UUID uuid = UUID.randomUUID();
        return "companyReport" + uuid.toString() + ".html";
    }

    private String getCompanyNumberFromUri(String resourceUri) {
        return resourceUri.replaceAll("[^\\d.]", "");
    }

    private Map<String, Object> getDebugMap(String companyNumber) {
        Map<String, Object> logMap = new HashMap<>();
        logMap.put("COMPANY_NUMBER", companyNumber);

        return logMap;
    }
}
