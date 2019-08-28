package uk.gov.companieshouse.document.generator.company.report.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.companieshouse.api.model.company.CompanyProfileApi;
import uk.gov.companieshouse.api.model.filinghistory.FilingApi;
import uk.gov.companieshouse.api.model.filinghistory.FilingHistoryApi;
import uk.gov.companieshouse.api.model.insolvency.CaseApi;
import uk.gov.companieshouse.api.model.insolvency.DatesApi;
import uk.gov.companieshouse.api.model.insolvency.InsolvencyApi;
import uk.gov.companieshouse.api.model.insolvency.PractitionerApi;
import uk.gov.companieshouse.api.model.officers.OfficersApi;
import uk.gov.companieshouse.api.model.psc.PscsApi;
import uk.gov.companieshouse.api.model.statements.StatementApi;
import uk.gov.companieshouse.api.model.statements.StatementsApi;
import uk.gov.companieshouse.api.model.ukestablishments.UkEstablishmentsApi;
import uk.gov.companieshouse.document.generator.company.report.exception.HandlerException;
import uk.gov.companieshouse.document.generator.company.report.exception.ServiceException;
import uk.gov.companieshouse.document.generator.company.report.mapping.mappers.CompanyReportMapper;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.CompanyReportApiData;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.CompanyReport;
import uk.gov.companieshouse.document.generator.company.report.service.CompanyService;
import uk.gov.companieshouse.document.generator.company.report.service.InsolvencyService;
import uk.gov.companieshouse.document.generator.company.report.service.OfficerService;
import uk.gov.companieshouse.document.generator.company.report.service.PscsService;
import uk.gov.companieshouse.document.generator.company.report.service.RecentFilingHistoryService;
import uk.gov.companieshouse.document.generator.company.report.service.StatementsService;
import uk.gov.companieshouse.document.generator.company.report.service.UkEstablishmentService;
import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfoResponse;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static uk.gov.companieshouse.document.generator.company.report.CompanyReportDocumentInfoServiceImpl.MODULE_NAME_SPACE;

@Component
public class CompanyReportDataHandler {

    private CompanyService companyService;

    private PscsService pscsService;

    private OfficerService officerService;

    private UkEstablishmentService ukEstablishmentService;

    private RecentFilingHistoryService recentFilingHistoryService;

    private CompanyReportMapper companyReportMapper;

    private StatementsService statementsService;

    private InsolvencyService insolvencyService;

    @Autowired
    public CompanyReportDataHandler(CompanyService companyService,
                                    PscsService pscsService,
                                    OfficerService officerService,
                                    UkEstablishmentService ukEstablishmentService,
                                    RecentFilingHistoryService recentFilingHistoryService,
                                    CompanyReportMapper companyReportMapper,
                                    StatementsService statementsService,
                                    InsolvencyService insolvencyService) {

        this.companyService = companyService;
        this.pscsService = pscsService;
        this.officerService = officerService;
        this.ukEstablishmentService = ukEstablishmentService;
        this.recentFilingHistoryService = recentFilingHistoryService;
        this.companyReportMapper = companyReportMapper;
        this.statementsService = statementsService;
        this.insolvencyService = insolvencyService;
    }

    private static final Logger LOG = LoggerFactory.getLogger(MODULE_NAME_SPACE);
    private static final String PSCS_KEY = "persons_with_significant_control";
    private static final String OFFICERS_KEY = "officers";
    private static final String UK_ESTABLISHMENTS = "uk_establishments";
    private static final String STATEMENTS_KEY = "persons_with_significant_control_statements";
    private static final String FILING_HISTORY_KEY = "filing_history";
    private static final String INSOLVENCY_KEY = "insolvency";

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

        documentInfoResponse.setData(getCompanyReportData(companyNumber, requestId, timeStamp));
        documentInfoResponse.setAssetId("company-report");
        documentInfoResponse.setPath(createPathString());
        documentInfoResponse.setTemplateName("company-report.html");

        return documentInfoResponse;
    }

    private String getCompanyReportData(String companyNumber,  String requestId,
        ZonedDateTime timeStamp) throws HandlerException {

        CompanyReportApiData companyReportApiData = new CompanyReportApiData();

        CompanyProfileApi companyProfileApi = getCompanyProfile(companyNumber, requestId);
        companyReportApiData.setCompanyProfileApi(companyProfileApi);

        if(companyProfileApi.getLinks().containsKey(FILING_HISTORY_KEY)) {
            try {
                FilingHistoryApi filingHistoryApi = getFilingHistory(companyNumber, requestId);
                companyReportApiData.setFilingHistoryApi(filingHistoryApi);

            } catch (HandlerException he) {
                LOG.infoContext(requestId, "Failed to get filing history data for company: "
                    + companyNumber, getDebugMap(companyNumber));
            }
        }

        if (companyProfileApi.getLinks().containsKey(PSCS_KEY)) {
            try {
                companyReportApiData.setPscsApi(getPscs(companyNumber, requestId));
            } catch (HandlerException he) {
                LOG.infoContext(requestId,"Failed to get PSCs data for company: "
                    + companyNumber, getDebugMap(companyNumber));
            }
        }

         if (companyProfileApi.getLinks().containsKey(OFFICERS_KEY)) {
             try {
                 OfficersApi officersApi = getOfficers(companyNumber, requestId);
                 companyReportApiData.setOfficersApi(officersApi);
             } catch (HandlerException he) {
                 LOG.infoContext(requestId,"Failed to get officers data for company: "
                     + companyNumber, getDebugMap(companyNumber));
             }
         }

         if(companyProfileApi.getLinks().containsKey(UK_ESTABLISHMENTS)) {
             try {
                 UkEstablishmentsApi ukEstablishmentsApi = getUkEstablishments(companyNumber, requestId);
                 companyReportApiData.setUkEstablishmentsApi(ukEstablishmentsApi);
             } catch (HandlerException he) {
                 LOG.infoContext(requestId,"Failed to get uk establishments: ", getDebugMap(companyNumber));
             }
         }

        if (companyProfileApi.getLinks().containsKey(STATEMENTS_KEY)) {
            try {
                StatementsApi statementsApi = getStatements(companyNumber, requestId);
                companyReportApiData.setStatementsApi(statementsApi);
            } catch (HandlerException he) {
                LOG.infoContext(requestId,"Failed to get psc statements data for company: "
                    + companyNumber, getDebugMap(companyNumber));
            }
        }

        if (companyProfileApi.getLinks().containsKey(INSOLVENCY_KEY)) {
            try {
                InsolvencyApi insolvencyApi = getInsolvency(companyNumber, requestId);
                companyReportApiData.setInsolvencyApi(insolvencyApi);
            } catch (HandlerException he) {
                LOG.infoContext(requestId, "Failed to get insolvency data for company: "
                    + companyNumber, getDebugMap(companyNumber));
            }
        }

        return toJson(companyReportMapper
            .mapCompanyReport(companyReportApiData, requestId, companyNumber),
            companyNumber,
            requestId,
            timeStamp);
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

    private UkEstablishmentsApi getUkEstablishments(String companyNumber, String requestId) throws HandlerException {
        try {
            LOG.infoContext(requestId, "Attempting to retrieve uk establishment", getDebugMap(companyNumber));
            return ukEstablishmentService.getUkEstablishments(companyNumber);
        } catch (ServiceException se) {
            throw new HandlerException("error occurred obtaining uk establishments", se);
        }
    }

    private FilingHistoryApi getFilingHistory(String companyNumber, String requestId) throws HandlerException {
        try {
            LOG.infoContext(requestId, "Attempting to retrieve company filing history", getDebugMap(companyNumber));
            return sortFilingHistory(recentFilingHistoryService.getFilingHistory(companyNumber));
        } catch (ServiceException se) {
            throw new HandlerException("error occurred obtaining the company filing history", se);
        }
    }

    private FilingHistoryApi sortFilingHistory(FilingHistoryApi filingHistory) {

        FilingHistoryApi filingHistoryApi = filingHistory;

        List<FilingApi> filings = filingHistory.getItems().stream()
            .sorted(Comparator.comparing(FilingApi::getDate, Comparator.nullsLast(Comparator.reverseOrder())))
            .collect(Collectors.toList());

        filingHistoryApi.setItems(filings);

        return filingHistoryApi;
    }

    private StatementsApi getStatements(String companyNumber, String requestId) throws HandlerException {
        try {
            LOG.infoContext(requestId, "Attempting to retrieve company psc statements", getDebugMap(companyNumber));
            return sortStatements(statementsService.getStatements(companyNumber));
        } catch (ServiceException se) {
            throw new HandlerException("error occurred obtaining the company psc statements", se);
        }
    }

    private StatementsApi sortStatements(StatementsApi statementsApi) {

        StatementsApi sortedStatementsApi = statementsApi;

        List<StatementApi> statements = statementsApi.getItems().stream()
            .sorted(Comparator.comparing(StatementApi::getCeasedOn, Comparator.nullsFirst(Comparator.reverseOrder()))
                .thenComparing(StatementApi::getNotifiedOn))
            .collect(Collectors.toList());

        sortedStatementsApi.setItems(statements);

        return  sortedStatementsApi;
    }

    private PscsApi getPscs(String companyNumber, String requestId) throws HandlerException {

        try {
            LOG.infoContext(requestId,"Attempting to retrieve company PSCSs", getDebugMap(companyNumber));
            return pscsService.getPscs(companyNumber);
        } catch (ServiceException se) {
            throw new HandlerException("error occurred obtaining the company PSCSs", se);

        }
    }

    private InsolvencyApi getInsolvency(String companyNumber, String requestId) throws HandlerException {
        try {
            LOG.infoContext(requestId, "Attempting to retrieve company insolvency", getDebugMap(companyNumber));
            return sortInsolvency(insolvencyService.getInsolvency(companyNumber));
        } catch (ServiceException se) {
            throw new HandlerException("error occurred obtaining the company insolvency", se);
        }
    }

    private InsolvencyApi sortInsolvency(InsolvencyApi insolvencyApi) {

        InsolvencyApi sortedInsolvencyApi = insolvencyApi;

        List<CaseApi> sortedCaseApi = insolvencyApi.getCases().stream()
            .sorted(Comparator.comparing(CaseApi::getNumber, Comparator.nullsLast(Comparator.reverseOrder())))
            .map(cases -> {
                List<DatesApi> dates = cases.getDates().stream()
                    .sorted(Comparator.comparing(DatesApi::getDate, Comparator.nullsLast(Comparator.reverseOrder())))
                    .collect(Collectors.toList());
                cases.setDates(dates);
                List<PractitionerApi> practitioners = cases.getPractitioners().stream()
                    .sorted(Comparator.comparing(PractitionerApi::getCeasedToActOn, Comparator.nullsLast(Comparator.reverseOrder())))
                    .collect(Collectors.toList());
                cases.setPractitioners(practitioners);
                return cases;
            })
            .collect(Collectors.toList());

        sortedInsolvencyApi.setCases(sortedCaseApi);

        return sortedInsolvencyApi;
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
