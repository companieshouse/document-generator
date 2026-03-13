package uk.gov.companieshouse.document.generator.company.report.handler;

import static uk.gov.companieshouse.document.generator.company.report.CompanyReportDocumentInfoServiceImpl.MODULE_NAME_SPACE;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.companieshouse.api.model.charges.ChargesApi;
import uk.gov.companieshouse.api.model.company.CompanyProfileApi;
import uk.gov.companieshouse.api.model.exemptions.CompanyExemptionsApi;
import uk.gov.companieshouse.api.model.filinghistory.FilingApi;
import uk.gov.companieshouse.api.model.filinghistory.FilingHistoryApi;
import uk.gov.companieshouse.api.model.insolvency.CaseApi;
import uk.gov.companieshouse.api.model.insolvency.DatesApi;
import uk.gov.companieshouse.api.model.insolvency.InsolvencyApi;
import uk.gov.companieshouse.api.model.insolvency.PractitionerApi;
import uk.gov.companieshouse.api.model.officers.OfficersApi;
import uk.gov.companieshouse.api.model.psc.PscsApi;
import uk.gov.companieshouse.api.model.registers.CompanyRegistersApi;
import uk.gov.companieshouse.api.model.registers.RegisterApi;
import uk.gov.companieshouse.api.model.registers.RegisterItemsApi;
import uk.gov.companieshouse.api.model.statements.StatementApi;
import uk.gov.companieshouse.api.model.statements.StatementsApi;
import uk.gov.companieshouse.api.model.ukestablishments.UkEstablishmentsApi;
import uk.gov.companieshouse.document.generator.company.report.exception.HandlerException;
import uk.gov.companieshouse.document.generator.company.report.exception.ServiceException;
import uk.gov.companieshouse.document.generator.company.report.mapping.mappers.CompanyReportMapper;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.CompanyReportApiData;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.CompanyReport;
import uk.gov.companieshouse.document.generator.company.report.service.ChargesService;
import uk.gov.companieshouse.document.generator.company.report.service.CompanyService;
import uk.gov.companieshouse.document.generator.company.report.service.ExemptionsService;
import uk.gov.companieshouse.document.generator.company.report.service.FilingHistoryService;
import uk.gov.companieshouse.document.generator.company.report.service.InsolvencyService;
import uk.gov.companieshouse.document.generator.company.report.service.OfficerService;
import uk.gov.companieshouse.document.generator.company.report.service.PscsService;
import uk.gov.companieshouse.document.generator.company.report.service.RegistersService;
import uk.gov.companieshouse.document.generator.company.report.service.StatementsService;
import uk.gov.companieshouse.document.generator.company.report.service.UkEstablishmentService;
import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfoResponse;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;

@Component
public class CompanyReportDataHandler {

    private CompanyService companyService;

    private PscsService pscsService;

    private OfficerService officerService;

    private UkEstablishmentService ukEstablishmentService;

    private FilingHistoryService filingHistoryService;

    private CompanyReportMapper companyReportMapper;

    private StatementsService statementsService;

    private ChargesService chargesService;

    private RegistersService registersService;

    private InsolvencyService insolvencyService;

    private ExemptionsService exemptionsService;

    @Autowired
    public CompanyReportDataHandler(CompanyService companyService,
                                    PscsService pscsService,
                                    OfficerService officerService,
                                    UkEstablishmentService ukEstablishmentService,
                                    FilingHistoryService filingHistoryService,
                                    CompanyReportMapper companyReportMapper,
                                    StatementsService statementsService,
                                    InsolvencyService insolvencyService,
                                    RegistersService registersService,
                                    ChargesService chargesService,
                                    ExemptionsService exemptionsService) {

        this.companyService = companyService;
        this.pscsService = pscsService;
        this.officerService = officerService;
        this.ukEstablishmentService = ukEstablishmentService;
        this.filingHistoryService = filingHistoryService;
        this.companyReportMapper = companyReportMapper;
        this.statementsService = statementsService;
        this.insolvencyService = insolvencyService;
        this.chargesService = chargesService;
        this.registersService = registersService;
        this.chargesService = chargesService;
        this.exemptionsService =exemptionsService;
    }

    private static final Logger LOG = LoggerFactory.getLogger(MODULE_NAME_SPACE);
    private static final String FILING_HISTORY_KEY = "filing_history";
    private static final String PSCS_KEY = "persons_with_significant_control";
    private static final String OFFICERS_KEY = "officers";
    private static final String UK_ESTABLISHMENTS = "uk_establishments";
    private static final String STATEMENTS_KEY = "persons_with_significant_control_statements";
    private static final String CHARGES_KEY = "charges";
    private static final String REGISTERS_KEY = "registers";
    private static final String INSOLVENCY_KEY = "insolvency";
    private static final String EXEMPTION_KEY = "exemptions";
    private static final String COMPANY_REPORT = "company-report";

    public DocumentInfoResponse getCompanyReport(String resourceUri, String requestId)
        throws HandlerException {

        String companyNumber = getCompanyNumberFromUri(resourceUri);

        ZonedDateTime timeStamp = ZonedDateTime.now();

        LOG.infoContext(requestId, "getCompanyReport(): Getting data for report for company number: ", getDebugMap(companyNumber));
        DocumentInfoResponse docInfoResponse = createDocumentInfoResponse(companyNumber, requestId, timeStamp);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try {
            LOG.infoContext(requestId, "getCompanyReport(): Returning DocumentInfoResponse to caller: docInfoResponse -> "+mapper.writeValueAsString(docInfoResponse), getDebugMap(companyNumber));
        } catch(JsonProcessingException ex){
            LOG.debug("getCompanyReport: getCompanyReport(): docInfoResponse error -> "+ex.getMessage());
        }
        LOG.infoContext(requestId, "getCompanyReport(): Received data for report for company number and returning to caller", getDebugMap(companyNumber));
        return docInfoResponse;
    }

    private DocumentInfoResponse createDocumentInfoResponse(String companyNumber,
        String requestId, ZonedDateTime timeStamp) throws HandlerException {

        LOG.infoContext(requestId, "createDocumentInfoResponse(): Calling getCompanyReportData to retrieve data", getDebugMap(companyNumber));

        DocumentInfoResponse documentInfoResponse = new DocumentInfoResponse();
        
        documentInfoResponse.setData(getCompanyReportData(companyNumber, requestId, timeStamp));
        documentInfoResponse.setAssetId(COMPANY_REPORT);
        documentInfoResponse.setPath(createPathString());
        documentInfoResponse.setTemplateName("company-report.html");
        documentInfoResponse.setDescriptionIdentifier(COMPANY_REPORT);

        LOG.infoContext(requestId, "createDocumentInfoResponse(): Returning getCompanyReportData to caller", getDebugMap(companyNumber));

        return documentInfoResponse;
    }

    private String getCompanyReportData(String companyNumber, String requestId,
        ZonedDateTime timeStamp) throws HandlerException {

        LOG.infoContext(requestId, "getCompanyReportData(): Calling CompanyReportApiData to retrieve data", getDebugMap(companyNumber));

        CompanyReportApiData companyReportApiData = new CompanyReportApiData();

        CompanyProfileApi companyProfileApi = getCompanyProfile(companyNumber, requestId);

        LOG.infoContext(requestId, "getCompanyReportData(): Recieved companyProfileApi data and setting CompanyReportData", getDebugMap(companyNumber));

        setCompanyReportData(companyNumber, requestId, companyReportApiData, companyProfileApi);

        LOG.infoContext(requestId, "getCompanyReportData(): Returning to caller", getDebugMap(companyNumber));

        return toJson(companyReportMapper
                .mapCompanyReport(companyReportApiData, requestId, companyNumber),
            companyNumber,
            requestId,
            timeStamp);
    }

    private void setCompanyReportData(String companyNumber, String requestId,
        CompanyReportApiData companyReportApiData, CompanyProfileApi companyProfileApi) {

        companyReportApiData.setCompanyProfileApi(companyProfileApi);

        LOG.infoContext(requestId, "setCompanyReportData(): Calling setFilingHistoryData - start...", getDebugMap(companyNumber));
        setFilingHistoryData(companyNumber, requestId, companyReportApiData, companyProfileApi);
        LOG.infoContext(requestId, "setCompanyReportData(): Calling setFilingHistoryData - end...", getDebugMap(companyNumber));

        LOG.infoContext(requestId, "setCompanyReportData(): Calling setPscsData - start...", getDebugMap(companyNumber));
        setPscsData(companyNumber, requestId, companyReportApiData, companyProfileApi);
        LOG.infoContext(requestId, "setCompanyReportData(): Calling setPscsData - end...", getDebugMap(companyNumber));

        LOG.infoContext(requestId, "setCompanyReportData(): Calling setOfficersData - start...", getDebugMap(companyNumber));
        setOfficersData(companyNumber, requestId, companyReportApiData, companyProfileApi);
        LOG.infoContext(requestId, "setCompanyReportData(): Calling setOfficersData - end...", getDebugMap(companyNumber));

        LOG.infoContext(requestId, "setCompanyReportData(): Calling setUkEstablishmentsData - start...", getDebugMap(companyNumber));
        setUkEstablishmentsData(companyNumber, requestId, companyReportApiData, companyProfileApi);
        LOG.infoContext(requestId, "setCompanyReportData(): Calling setUkEstablishmentsData - end...", getDebugMap(companyNumber));

        LOG.infoContext(requestId, "setCompanyReportData(): Calling setStatementsData - start...", getDebugMap(companyNumber));
        setStatementsData(companyNumber, requestId, companyReportApiData, companyProfileApi);
        LOG.infoContext(requestId, "setCompanyReportData(): Calling setStatementsData - end...", getDebugMap(companyNumber));

        LOG.infoContext(requestId, "setCompanyReportData(): Calling setChargesData - start...", getDebugMap(companyNumber));
        setChargesData(companyNumber, requestId, companyReportApiData, companyProfileApi);
        LOG.infoContext(requestId, "setCompanyReportData(): Calling setChargesData - end...", getDebugMap(companyNumber));

        LOG.infoContext(requestId, "setCompanyReportData(): Calling setInsolvency - start...", getDebugMap(companyNumber));
        setInsolvency(companyNumber, requestId, companyReportApiData, companyProfileApi);
        LOG.infoContext(requestId, "setCompanyReportData(): Calling setInsolvency - end...", getDebugMap(companyNumber));

        LOG.infoContext(requestId, "setCompanyReportData(): Calling setRegisters - start...", getDebugMap(companyNumber));
        setRegisters(companyNumber, requestId, companyReportApiData, companyProfileApi);
        LOG.infoContext(requestId, "setCompanyReportData(): Calling setRegisters - end...", getDebugMap(companyNumber));

        LOG.infoContext(requestId, "setCompanyReportData(): Calling setExemptions - start...", getDebugMap(companyNumber));
        setExemptions(companyNumber, requestId, companyReportApiData, companyProfileApi);
        LOG.infoContext(requestId, "setCompanyReportData(): Calling setExemptions - end...", getDebugMap(companyNumber));

        LOG.infoContext(requestId, "setCompanyReportData(): returning to caller", getDebugMap(companyNumber));
    }

    private void setFilingHistoryData(String companyNumber, String requestId,
        CompanyReportApiData companyReportApiData, CompanyProfileApi companyProfileApi) {

        if (companyProfileApi.getLinks().containsKey(FILING_HISTORY_KEY)) {

            try {
                LOG.debug("setFilingHistoryData: getting filing history...");
                FilingHistoryApi filingHistoryApi = getFilingHistory(companyNumber, requestId);
                LOG.debug("setFilingHistoryData: building filing history object...");
                companyReportApiData.setFilingHistoryApi(filingHistoryApi);
                LOG.debug("setFilingHistoryData: done...");
            } catch (HandlerException he) {
                LOG.infoContext(requestId, "Failed to get filing history data for company: "
                    + companyNumber, getDebugMap(companyNumber));
            }
        }
    }

    private void setPscsData(String companyNumber, String requestId,
        CompanyReportApiData companyReportApiData, CompanyProfileApi companyProfileApi) {
        LOG.infoContext(requestId, "setPscsData(): start...", getDebugMap(companyNumber));
        if (companyProfileApi.getLinks().containsKey(PSCS_KEY)) {
            try {
                LOG.infoContext(requestId, "setPscsData(): PSCS_KEY found - calling getPscs() ", getDebugMap(companyNumber));
                PscsApi pscsApi = getPscs(companyNumber, requestId);
                LOG.infoContext(requestId, "setPscsData(): PSCS_KEY found - back from getPscs() - setting PSC data ", getDebugMap(companyNumber));
                companyReportApiData.setPscsApi(pscsApi);

            } catch (HandlerException he) {
                LOG.infoContext(requestId, "Failed to get PSCs data for company: "
                    + companyNumber, getDebugMap(companyNumber));
            }
        }
        LOG.infoContext(requestId, "setPscsData(): end...", getDebugMap(companyNumber));
    }

    private void setOfficersData(String companyNumber, String requestId,
        CompanyReportApiData companyReportApiData, CompanyProfileApi companyProfileApi) {
        LOG.infoContext(requestId, "setOfficersData(): start...", getDebugMap(companyNumber));
        if (companyProfileApi.getLinks().containsKey(OFFICERS_KEY)) {
            try {
                OfficersApi officersApi = getOfficers(companyNumber, requestId);
                companyReportApiData.setOfficersApi(officersApi);
            } catch (HandlerException he) {
                LOG.infoContext(requestId, "Failed to get company officers: ", getDebugMap(companyNumber));
            }
        }
        LOG.infoContext(requestId, "setOfficersData(): start...", getDebugMap(companyNumber));
    }

    private void setUkEstablishmentsData(String companyNumber, String requestId,
        CompanyReportApiData companyReportApiData, CompanyProfileApi companyProfileApi) {
        LOG.infoContext(requestId, "setUkEstablishmentsData(): start...", getDebugMap(companyNumber));
        if (companyProfileApi.getLinks().containsKey(UK_ESTABLISHMENTS)) {
            try {
                UkEstablishmentsApi ukEstablishmentsApi = getUkEstablishments(companyNumber, requestId);
                companyReportApiData.setUkEstablishmentsApi(ukEstablishmentsApi);
            } catch (HandlerException he) {
                LOG.infoContext(requestId, "Failed to get uk establishments: ", getDebugMap(companyNumber));
            }
        }
        LOG.infoContext(requestId, "setUkEstablishmentsData(): start...", getDebugMap(companyNumber));
    }

    private void setStatementsData(String companyNumber, String requestId,
        CompanyReportApiData companyReportApiData, CompanyProfileApi companyProfileApi) {
        LOG.infoContext(requestId, "setStatementsData(): start...", getDebugMap(companyNumber));
        if (companyProfileApi.getLinks().containsKey(STATEMENTS_KEY)) {
            try {
                StatementsApi statementsApi = getStatements(companyNumber, requestId);
                companyReportApiData.setStatementsApi(statementsApi);
            } catch (HandlerException he) {
                LOG.infoContext(requestId, "Failed to get psc statements data for company: "
                    + companyNumber, getDebugMap(companyNumber));
            }
        }
        LOG.infoContext(requestId, "setStatementsData(): start...", getDebugMap(companyNumber));
    }

    private void setChargesData(String companyNumber, String requestId,
        CompanyReportApiData companyReportApiData, CompanyProfileApi companyProfileApi) {
        LOG.infoContext(requestId, "setChargesData(): start...", getDebugMap(companyNumber));
        if (companyProfileApi.getLinks().containsKey(CHARGES_KEY)) {
            try {
                ChargesApi chargesApi = getCharges(companyNumber, requestId);
                companyReportApiData.setChargesApi(chargesApi);
            } catch (HandlerException he) {
                LOG.infoContext(requestId, "Failed to get charges: ", getDebugMap(companyNumber));
            }
        }
        LOG.infoContext(requestId, "setChargesData(): start...", getDebugMap(companyNumber));
    }

    private void setInsolvency(String companyNumber, String requestId,
        CompanyReportApiData companyReportApiData, CompanyProfileApi companyProfileApi) {
        LOG.infoContext(requestId, "setInsolvency(): start...", getDebugMap(companyNumber));
        if (companyProfileApi.getLinks().containsKey(INSOLVENCY_KEY)) {
            try {
                InsolvencyApi insolvencyApi = getInsolvency(companyNumber, requestId);
                companyReportApiData.setInsolvencyApi(insolvencyApi);
            } catch (HandlerException he) {
                LOG.infoContext(requestId, "Failed to get insolvency data for company: "
                    + companyNumber, getDebugMap(companyNumber));
            }
        }
        LOG.infoContext(requestId, "setInsolvency(): start...", getDebugMap(companyNumber));
    }

    private void setRegisters(String companyNumber, String requestId,
        CompanyReportApiData companyReportApiData, CompanyProfileApi companyProfileApi) {
        LOG.infoContext(requestId, "setRegisters(): start...", getDebugMap(companyNumber));
        if (companyProfileApi.getLinks().containsKey(REGISTERS_KEY)) {
            try {
                CompanyRegistersApi companyRegistersApi = getCompanyRegisters(companyNumber, requestId);
                companyReportApiData.setCompanyRegistersApi(companyRegistersApi);
            } catch (HandlerException he) {
                LOG.infoContext(requestId, "Failed to get company registers: ", getDebugMap(companyNumber));
            }
        }
        LOG.infoContext(requestId, "setRegisters(): start...", getDebugMap(companyNumber));
    }

    private void setExemptions(String companyNumber, String requestId,
        CompanyReportApiData companyReportApiData, CompanyProfileApi companyProfileApi) {
        LOG.infoContext(requestId, "setExemptions(): start...", getDebugMap(companyNumber));
        if (companyProfileApi.getLinks().containsKey(EXEMPTION_KEY)) {
            try {
                CompanyExemptionsApi companyExemptionsApi = getCompanyExemptions(companyNumber, requestId);
                companyReportApiData.setCompanyExemptionsApi(companyExemptionsApi);
            } catch (HandlerException he) {
                LOG.infoContext(requestId, "Failed to get company exemptions: ", getDebugMap(companyNumber));
            }
        }
        LOG.infoContext(requestId, "setExemptions(): start...", getDebugMap(companyNumber));
    }

    private String toJson(CompanyReport companyReport, String companyNumber,
        String requestId, ZonedDateTime timeStamp) throws HandlerException {

        String reportToJson;
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        companyReport.setTimeStampCreated(timeStamp.format(DateTimeFormatter.ofPattern("d MMMM uuuu HH:mm:ss")));

        try {
            LOG.infoContext(requestId, "Attempting to convert company report to JSON", getDebugMap(companyNumber));
            reportToJson = mapper.writeValueAsString(companyReport);
        } catch (JsonProcessingException e) {
            throw new HandlerException(
                new StringBuilder("Could not serialise Document data for the generation of the company report for company: ")
                    .append(companyReport.getRegistrationInformation().getCompanyName())
                    .append("-").append(companyReport.getRegistrationInformation().getCompanyNumber()).toString());
        }

        return reportToJson;
    }

    private CompanyProfileApi getCompanyProfile(String companyNumber,
        String requestId) throws HandlerException {

        try {
            LOG.infoContext(requestId, "Attempting to retrieve company profile", getDebugMap(companyNumber));
            return companyService.getCompanyProfile(companyNumber);
        } catch (ServiceException se) {
            throw new HandlerException("error occurred obtaining the company profile", se);
        }
    }

    private OfficersApi getOfficers(String companyNumber,
        String requestId) throws HandlerException {
        try {
            LOG.infoContext(requestId, "Attempting to retrieve company officers", getDebugMap(companyNumber));
            return officerService.getOfficers(companyNumber);
        } catch (ServiceException se) {
            throw new HandlerException("error occurred obtaining the company officers", se);
        }
    }

    private UkEstablishmentsApi getUkEstablishments(String companyNumber,
        String requestId) throws HandlerException {
        try {
            LOG.infoContext(requestId, "Attempting to retrieve uk establishment", getDebugMap(companyNumber));
            return ukEstablishmentService.getUkEstablishments(companyNumber);
        } catch (ServiceException se) {
            throw new HandlerException("error occurred obtaining uk establishments", se);
        }
    }

    private FilingHistoryApi getFilingHistory(String companyNumber,
        String requestId) throws HandlerException {
        try {
            LOG.infoContext(requestId, "Attempting to retrieve company filing history", getDebugMap(companyNumber));
            return sortFilingHistory(filingHistoryService.getFilingHistory(companyNumber));
        } catch (ServiceException se) {
            throw new HandlerException("error occurred obtaining the company filing history", se);
        }
    }

    private FilingHistoryApi sortFilingHistory(FilingHistoryApi filingHistory) {

        FilingHistoryApi filingHistoryApi = filingHistory;
        LOG.debug("sortFilingHistory(): sorting filing history...");

        List<FilingApi> filings = filingHistory.getItems().stream()
            .sorted(Comparator.comparing(FilingApi::getDate, Comparator.nullsLast(Comparator.reverseOrder())))
            .collect(Collectors.toList());

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        try {
            LOG.debug("sortFilingHistory(): filings -> "+mapper.writeValueAsString(filings));
        } catch(JsonProcessingException ex){
            LOG.debug("sortFilingHistory(): filings error -> "+ex.getMessage());
        }

        filingHistoryApi.setItems(filings);
        try {
            LOG.debug("sortFilingHistory(): filingHistoryApi -> "+mapper.writeValueAsString(filingHistoryApi.getItems()));
        } catch(JsonProcessingException ex){
            LOG.debug("sortFilingHistory(): filingHistoryApi error -> "+ex.getMessage());
        }

        return filingHistoryApi;
    }

    private StatementsApi getStatements(String companyNumber,
        String requestId) throws HandlerException {
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

        return sortedStatementsApi;
    }

    private PscsApi getPscs(String companyNumber, String requestId) throws HandlerException {
        try {
            LOG.infoContext(requestId, "Attempting to retrieve company PSCSs", getDebugMap(companyNumber));
            return pscsService.getPscs(companyNumber);
        } catch (ServiceException se) {
            throw new HandlerException("error occurred obtaining the company PSCSs", se);
        }
    }


    private ChargesApi getCharges(String companyNumber, String requestId) throws HandlerException {

        try {
            LOG.infoContext(requestId, "Attempting to retrieve company charges", getDebugMap(companyNumber));
            return chargesService.getCharges(companyNumber);
        } catch (ServiceException se) {
            throw new HandlerException("error occurred obtaining the company charges", se);
        }
    }

    private CompanyRegistersApi getCompanyRegisters(String companyNumber,
        String requestId) throws HandlerException {
        try {
            LOG.infoContext(requestId, "Attempting to retrieve company registers", getDebugMap(companyNumber));
            return sortEachRegistersDates(registersService.getCompanyRegisters(companyNumber));
        } catch (ServiceException se) {
            throw new HandlerException("error occurred obtaining the company registers", se);
        }
    }

    private CompanyRegistersApi sortEachRegistersDates(CompanyRegistersApi companyRegistersApi) {

        CompanyRegistersApi sortedCompanyRegistersApi = companyRegistersApi;

        if (companyRegistersApi.getRegisters() != null) {

            if (companyRegistersApi.getRegisters().getDirectorsRegister() != null) {
                RegisterApi sortRegister = sortRegister(companyRegistersApi.getRegisters().getDirectorsRegister());
                sortedCompanyRegistersApi.getRegisters().setDirectorsRegister(sortRegister);
            }

            if (companyRegistersApi.getRegisters().getLlpMembersRegister() != null) {
                RegisterApi sortRegister = sortRegister(companyRegistersApi.getRegisters().getLlpMembersRegister());
                sortedCompanyRegistersApi.getRegisters().setLlpMembersRegister(sortRegister);
            }

            if (companyRegistersApi.getRegisters().getLlpUsualResidentialAddressRegister() != null) {
                RegisterApi sortRegister = sortRegister(companyRegistersApi.getRegisters().getLlpUsualResidentialAddressRegister());
                sortedCompanyRegistersApi.getRegisters().setLlpUsualResidentialAddressRegister(sortRegister);
            }

            if (companyRegistersApi.getRegisters().getMembersRegister() != null) {
                RegisterApi sortRegister = sortRegister(companyRegistersApi.getRegisters().getMembersRegister());
                sortedCompanyRegistersApi.getRegisters().setMembersRegister(sortRegister);
            }

            if (companyRegistersApi.getRegisters().getPscRegister() != null) {
                RegisterApi sortRegister = sortRegister(companyRegistersApi.getRegisters().getPscRegister());
                sortedCompanyRegistersApi.getRegisters().setPscRegister(sortRegister);
            }

            if (companyRegistersApi.getRegisters().getSecretariesRegister() != null) {
                RegisterApi sortRegister = sortRegister(companyRegistersApi.getRegisters().getSecretariesRegister());
                sortedCompanyRegistersApi.getRegisters().setSecretariesRegister(sortRegister);
            }

            if (companyRegistersApi.getRegisters().getUsualResidentialAddressRegister() != null) {
                RegisterApi sortRegister = sortRegister(companyRegistersApi.getRegisters().getUsualResidentialAddressRegister());
                sortedCompanyRegistersApi.getRegisters().setUsualResidentialAddressRegister(sortRegister);
            }
        }

        return sortedCompanyRegistersApi;
    }

    private RegisterApi sortRegister(RegisterApi registerApi) {

        RegisterApi sortedRegisterApi = registerApi;

        List<RegisterItemsApi> items = registerApi.getItems().stream()
            .sorted(Comparator.comparing(RegisterItemsApi::getMovedOn, Comparator.nullsLast(Comparator.reverseOrder())))
            .collect(Collectors.toList());

        sortedRegisterApi.setItems(items);

        return sortedRegisterApi;
    }

    private InsolvencyApi getInsolvency(String companyNumber,
        String requestId) throws HandlerException {
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
                    .sorted(Comparator.comparing(DatesApi::getDate, Comparator.nullsLast(Comparator.naturalOrder())))
                    .collect(Collectors.toList());
                cases.setDates(dates);
                List<PractitionerApi> practitioners = cases.getPractitioners().stream()
                    .sorted(Comparator.comparing(PractitionerApi::getCeasedToActOn, Comparator.nullsLast(Comparator.reverseOrder()))
                        .thenComparing(PractitionerApi::getAppointedOn, Comparator.nullsLast(Comparator.reverseOrder())))
                    .collect(Collectors.toList());
                cases.setPractitioners(practitioners);
                return cases;
            })
            .collect(Collectors.toList());

        sortedInsolvencyApi.setCases(sortedCaseApi);

        return sortedInsolvencyApi;
    }

    private CompanyExemptionsApi getCompanyExemptions(String companyNumber, String requestId) throws HandlerException {
        try {
            LOG.infoContext(requestId, "Attempting to retrieve company exemptions", getDebugMap(companyNumber));
            return exemptionsService.getCompanyExemptions(companyNumber);
        } catch (ServiceException se) {
            throw new HandlerException("error occurred obtaining the company exemptions", se);
        }
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

    private Map<String, Object> getDebugMap(String companyNumber) {
        Map<String, Object> logMap = new HashMap<>();
        logMap.put("COMPANY_NUMBER", companyNumber);

        return logMap;
    }
}

