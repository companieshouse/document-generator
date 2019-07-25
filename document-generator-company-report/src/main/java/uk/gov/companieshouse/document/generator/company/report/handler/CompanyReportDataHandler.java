package uk.gov.companieshouse.document.generator.company.report.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.companieshouse.api.model.company.CompanyProfileApi;
import uk.gov.companieshouse.api.model.officers.OfficersApi;
import uk.gov.companieshouse.api.model.psc.PscsApi;
import uk.gov.companieshouse.api.model.statements.StatementsApi;
import uk.gov.companieshouse.document.generator.company.report.exception.HandlerException;
import uk.gov.companieshouse.document.generator.company.report.exception.ServiceException;
import uk.gov.companieshouse.document.generator.company.report.mapping.mappers.CompanyReportMapper;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.CompanyReportApiData;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.CompanyReport;
import uk.gov.companieshouse.document.generator.company.report.service.CompanyService;
import uk.gov.companieshouse.document.generator.company.report.service.OfficerService;
import uk.gov.companieshouse.document.generator.company.report.service.PscsService;
import uk.gov.companieshouse.document.generator.company.report.service.StatementsService;
import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfoResponse;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static uk.gov.companieshouse.document.generator.company.report.CompanyReportDocumentInfoServiceImpl.MODULE_NAME_SPACE;

@Component
public class CompanyReportDataHandler {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private PscsService pscsService;

    @Autowired
    private OfficerService officerService;

    @Autowired
    private CompanyReportMapper companyReportMapper;

    @Autowired
    private StatementsService statementsService;

    private static final Logger LOG = LoggerFactory.getLogger(MODULE_NAME_SPACE);
    private static final String PSCS_KEY = "persons_with_significant_control";
    private static final String OFFICERS_KEY = "officers";

    private static final String STATEMENTS_KEY = "persons_with_significant_control_statements";

    public DocumentInfoResponse getCompanyReport(String resourceUri, String requestId)
        throws HandlerException {

        String companyNumber = getCompanyNumberFromUri(resourceUri);

        LOG.infoContext(requestId, "Getting data for report for company number: " + companyNumber, getDebugMap(companyNumber));
        return createDocumentInfoResponse(companyNumber, requestId);
    }

    private DocumentInfoResponse createDocumentInfoResponse(String companyNumber,
        String requestId) throws HandlerException {

        DocumentInfoResponse documentInfoResponse = new DocumentInfoResponse();

        documentInfoResponse.setData(getCompanyReportData(companyNumber, requestId));
        documentInfoResponse.setAssetId("company-report");
        documentInfoResponse.setPath(createPathString());
        documentInfoResponse.setTemplateName("company-report.html");

        return documentInfoResponse;
    }

    private String getCompanyReportData(String companyNumber,  String requestId) throws HandlerException {

        CompanyReportApiData companyReportApiData = new CompanyReportApiData();

        CompanyProfileApi companyProfileApi = getCompanyProfile(companyNumber, requestId);
        companyReportApiData.setCompanyProfileApi(companyProfileApi);

        if (companyProfileApi.getLinks().containsKey(PSCS_KEY)) {
            try {
                companyReportApiData.setPscsApi(getPscs(companyNumber, requestId));
            } catch (HandlerException he) {
                LOG.infoContext(requestId,"Failed to get PSCs: ", getDebugMap(companyNumber));
            }
        }

         if (companyProfileApi.getLinks().containsKey(OFFICERS_KEY)) {
             try {
                 OfficersApi officersApi = getOfficers(companyNumber, requestId);
                 companyReportApiData.setOfficersApi(officersApi);
             } catch (HandlerException he) {
                 LOG.infoContext(requestId,"Failed to get company officers: ", getDebugMap(companyNumber));
             }
         }

        if (companyProfileApi.getLinks().containsKey(STATEMENTS_KEY)) {
            try {
                StatementsApi statementsApi = getStatements(companyNumber, requestId);
                companyReportApiData.setStatementsApi(statementsApi);
            } catch (HandlerException he) {
                LOG.infoContext(requestId,"Failed to get psc statements: ", getDebugMap(companyNumber));
            }
        }

        return toJson(companyReportMapper
            .mapCompanyReport(companyReportApiData),
            companyNumber,
            requestId);
    }

    private String toJson(CompanyReport companyReport, String companyNumber,
                          String requestId) throws HandlerException {

        String reportToJson;
        ObjectMapper mapper = new ObjectMapper();

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

    private OfficersApi getOfficers(String companyNumber, String requestId) throws HandlerException {
        try {
            LOG.infoContext(requestId,"Attempting to retrieve company officers", getDebugMap(companyNumber));
            return officerService.getOfficers(companyNumber);
        } catch (ServiceException se) {
            throw new HandlerException("error occurred obtaining the company officers", se);
        }
    }

    private StatementsApi getStatements(String companyNumber, String requestId) throws HandlerException {
        try {
            LOG.infoContext(requestId, "Attempting to retrieve company psc statements", getDebugMap(companyNumber));
            return statementsService.getStatements(companyNumber);
        } catch (ServiceException se) {
            throw new HandlerException("error occurred obtaining the company psc statements", se);
        }
    }

    private PscsApi getPscs(String companyNumber, String requestId) throws HandlerException {

        try {
            LOG.infoContext(requestId,"Attempting to retrieve company PSCSs", getDebugMap(companyNumber));
            return pscsService.getPscs(companyNumber);
        } catch (ServiceException se) {
            throw new HandlerException("error occurred obtaining the company PSCSs", se);

        }
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
