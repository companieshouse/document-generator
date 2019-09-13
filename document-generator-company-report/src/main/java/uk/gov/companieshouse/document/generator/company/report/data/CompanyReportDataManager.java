package uk.gov.companieshouse.document.generator.company.report.data;

import org.springframework.stereotype.Component;
import uk.gov.companieshouse.api.model.company.CompanyProfileApi;
import uk.gov.companieshouse.api.model.filinghistory.FilingApi;
import uk.gov.companieshouse.api.model.filinghistory.FilingHistoryApi;
import uk.gov.companieshouse.api.model.insolvency.CaseApi;
import uk.gov.companieshouse.api.model.insolvency.DatesApi;
import uk.gov.companieshouse.api.model.insolvency.InsolvencyApi;
import uk.gov.companieshouse.api.model.insolvency.PractitionerApi;
import uk.gov.companieshouse.api.model.registers.CompanyRegistersApi;
import uk.gov.companieshouse.api.model.registers.RegisterApi;
import uk.gov.companieshouse.api.model.registers.RegisterItemsApi;
import uk.gov.companieshouse.api.model.statements.StatementApi;
import uk.gov.companieshouse.api.model.statements.StatementsApi;
import uk.gov.companieshouse.document.generator.company.report.exception.ApiDataException;
import uk.gov.companieshouse.document.generator.company.report.exception.ServiceException;
import uk.gov.companieshouse.document.generator.company.report.handler.RequestParameters;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.CompanyReportApiData;
import uk.gov.companieshouse.document.generator.company.report.service.ChargesService;
import uk.gov.companieshouse.document.generator.company.report.service.CompanyService;
import uk.gov.companieshouse.document.generator.company.report.service.InsolvencyService;
import uk.gov.companieshouse.document.generator.company.report.service.OfficerService;
import uk.gov.companieshouse.document.generator.company.report.service.PscsService;
import uk.gov.companieshouse.document.generator.company.report.service.RecentFilingHistoryService;
import uk.gov.companieshouse.document.generator.company.report.service.RegistersService;
import uk.gov.companieshouse.document.generator.company.report.service.StatementsService;
import uk.gov.companieshouse.document.generator.company.report.service.UkEstablishmentService;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static uk.gov.companieshouse.document.generator.company.report.CompanyReportDocumentInfoServiceImpl.MODULE_NAME_SPACE;

@Component
public class CompanyReportDataManager {

    private CompanyService companyService;

    private PscsService pscsService;

    private OfficerService officerService;

    private UkEstablishmentService ukEstablishmentService;

    private RecentFilingHistoryService recentFilingHistoryService;

    private StatementsService statementsService;

    private InsolvencyService insolvencyService;

    private RegistersService registersService;

    private ChargesService chargesService;

    public CompanyReportDataManager (CompanyService companyService,
                                     PscsService pscsService,
                                     OfficerService officerService,
                                     UkEstablishmentService ukEstablishmentService,
                                     RecentFilingHistoryService recentFilingHistoryService,
                                     StatementsService statementsService,
                                     InsolvencyService insolvencyService,
                                     RegistersService registersService,
                                     ChargesService chargesService) {

        this.companyService = companyService;
        this.pscsService = pscsService;
        this.officerService = officerService;
        this.ukEstablishmentService = ukEstablishmentService;
        this.recentFilingHistoryService = recentFilingHistoryService;
        this.statementsService = statementsService;
        this.insolvencyService = insolvencyService;
        this.registersService = registersService;
        this.chargesService = chargesService;
    }

    private static final Logger LOG = LoggerFactory.getLogger(MODULE_NAME_SPACE);
    private static final String FILING_HISTORY_KEY = "filing_history";
    private static final String PSCS_KEY = "persons_with_significant_control";
    private static final String OFFICERS_KEY = "officers";
    private static final String UK_ESTABLISHMENTS = "uk_establishments";
    private static final String STATEMENTS_KEY = "persons_with_significant_control_statements";
    private static final String INSOLVENCY_KEY = "insolvency";
    private static final String REGISTERS_KEY = "registers";
    private static final String CHARGES_KEY = "charges";


    public CompanyReportApiData getCompanyReportData(RequestParameters requestParameters)
        throws ApiDataException {

        CompanyReportApiData companyReportApiData = new CompanyReportApiData();

        setCompanyReportData(companyReportApiData, getCompanyProfile(requestParameters), requestParameters);

        return companyReportApiData;
    }

    private void setCompanyReportData(CompanyReportApiData companyReportApiData, CompanyProfileApi companyProfileApi,
       RequestParameters requestParameters) {

        companyReportApiData.setCompanyProfileApi(companyProfileApi);

        setOfficersData(companyReportApiData, companyProfileApi, requestParameters);
        setUkEstablishmentsData(companyReportApiData, companyProfileApi, requestParameters);
        setFilingHistoryData(companyReportApiData, companyProfileApi, requestParameters);
        setStatementsData(companyReportApiData, companyProfileApi, requestParameters);
        setPscsData(companyReportApiData, companyProfileApi, requestParameters);
        setInsolvency(companyReportApiData, companyProfileApi, requestParameters);
        setCompanyRegisters(companyReportApiData, companyProfileApi, requestParameters);
        setCharges(companyReportApiData, companyProfileApi, requestParameters);
    }

    private CompanyProfileApi getCompanyProfile(RequestParameters requestParameters) throws ApiDataException {

        try {
            LOG.infoContext(requestParameters.getRequestId(),"Attempting to retrieve company profile data for company: "
                + requestParameters.getCompanyNumber(), getDebugMap(requestParameters.getResourceUri()));
            return companyService.getCompanyProfile(requestParameters.getCompanyNumber());
        } catch (ServiceException se) {
            throw new ApiDataException("error occurred obtaining the company profile data for company: "
                + requestParameters.getCompanyNumber(), se);
        }
    }

    private void setOfficersData(CompanyReportApiData companyReportApiData,
        CompanyProfileApi companyProfileApi, RequestParameters requestParameters) {

        if(companyProfileApi.getLinks().containsKey(OFFICERS_KEY)) {

            try {
                LOG.infoContext(requestParameters.getRequestId(),"Attempting to retrieve company officers data for company: "
                    + requestParameters.getCompanyNumber(), getDebugMap(requestParameters.getResourceUri()));
                companyReportApiData.setOfficersApi(officerService.getOfficers(requestParameters.getCompanyNumber()));
            } catch (ServiceException se) {
                LOG.infoContext(requestParameters.getRequestId(), "Failed to get company officer data for company: "
                    + requestParameters.getCompanyNumber(), getDebugMap(requestParameters.getResourceUri()));
            }
        }
    }

    private void setUkEstablishmentsData(CompanyReportApiData companyReportApiData,
        CompanyProfileApi companyProfileApi, RequestParameters requestParameters) {

        if(companyProfileApi.getLinks().containsKey(UK_ESTABLISHMENTS)) {

            try {
                LOG.infoContext(requestParameters.getRequestId(), "Attempting to retrieve uk establishment data for company: "
                    + requestParameters.getCompanyNumber(), getDebugMap(requestParameters.getResourceUri()));
                companyReportApiData.setUkEstablishmentsApi(ukEstablishmentService
                    .getUkEstablishments(requestParameters.getCompanyNumber()));
            } catch (ServiceException se) {
                LOG.infoContext(requestParameters.getRequestId(), "Failed to get uk establishment data for company: "
                    + requestParameters.getCompanyNumber(), getDebugMap(requestParameters.getResourceUri()));
            }
        }
    }

    private void setFilingHistoryData(CompanyReportApiData companyReportApiData,
        CompanyProfileApi companyProfileApi, RequestParameters requestParameters) {

        if(companyProfileApi.getLinks().containsKey(FILING_HISTORY_KEY)) {

            try {
                LOG.infoContext(requestParameters.getRequestId(), "Attempting to retrieve company filing history data for company: "
                    + requestParameters.getCompanyNumber(), getDebugMap(requestParameters.getResourceUri()));
                companyReportApiData.setFilingHistoryApi(sortFilingHistory(
                    recentFilingHistoryService.getFilingHistory(requestParameters.getCompanyNumber())));
            } catch (ServiceException se) {
                LOG.infoContext(requestParameters.getRequestId(), "Failed to get company filing history data for company: "
                    + requestParameters.getCompanyNumber(), getDebugMap(requestParameters.getResourceUri()));
            }
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

    private void setStatementsData(CompanyReportApiData companyReportApiData,
        CompanyProfileApi companyProfileApi, RequestParameters requestParameters) {

        if(companyProfileApi.getLinks().containsKey(STATEMENTS_KEY)) {

            try {
                LOG.infoContext(requestParameters.getRequestId(), "Attempting to retrieve company psc statements data for company: "
                    + requestParameters.getCompanyNumber(), getDebugMap(requestParameters.getResourceUri()));
                companyReportApiData.setStatementsApi(sortStatements(statementsService
                    .getStatements(requestParameters.getCompanyNumber())));
            } catch (ServiceException se) {
                LOG.infoContext(requestParameters.getRequestId(), "Failed to get company psc statements data for company: "
                    + requestParameters.getCompanyNumber(), getDebugMap(requestParameters.getResourceUri()));
            }
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

    private void setPscsData(CompanyReportApiData companyReportApiData,
        CompanyProfileApi companyProfileApi, RequestParameters requestParameters) {

        if(companyProfileApi.getLinks().containsKey(PSCS_KEY)) {

            try {
                LOG.infoContext(requestParameters.getRequestId(), "Attempting to retrieve company PSCS data for company: "
                        + requestParameters.getCompanyNumber(), getDebugMap(requestParameters.getResourceUri()));
                companyReportApiData.setPscsApi(pscsService.getPscs(requestParameters.getCompanyNumber()));
            } catch (ServiceException se) {
                LOG.infoContext(requestParameters.getRequestId(), "Failed to get company PSCS data for company: "
                    + requestParameters.getCompanyNumber(), getDebugMap(requestParameters.getResourceUri()));

            }
        }
    }

    private void setInsolvency(CompanyReportApiData companyReportApiData, CompanyProfileApi companyProfileApi,
        RequestParameters requestParameters) {

        if(companyProfileApi.getLinks().containsKey(INSOLVENCY_KEY)) {

            try {
                LOG.infoContext(requestParameters.getRequestId(),
                    "Attempting to retrieve company insolvency data for company: " + requestParameters.getCompanyNumber(),
                    getDebugMap(requestParameters.getResourceUri()));
                companyReportApiData.setInsolvencyApi(sortInsolvency(insolvencyService.getInsolvency(requestParameters.getCompanyNumber())));
            } catch (ServiceException se) {
                LOG.infoContext(requestParameters.getRequestId(), "Failed to get company insolvency data for company: "
                    + requestParameters.getCompanyNumber(), getDebugMap(requestParameters.getResourceUri()));
            }
        }
    }

    private InsolvencyApi sortInsolvency (InsolvencyApi insolvencyApi){

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

    private void setCompanyRegisters(CompanyReportApiData companyReportApiData, CompanyProfileApi companyProfileApi,
        RequestParameters requestParameters) {

        if(companyProfileApi.getLinks().containsKey(REGISTERS_KEY)) {
            try {
                LOG.infoContext(requestParameters.getRequestId(),
                    "Attempting to retrieve company registers data for company: " + requestParameters.getCompanyNumber(),
                    getDebugMap(requestParameters.getResourceUri()));
                companyReportApiData.setCompanyRegistersApi(sortEachRegistersDates(registersService.getCompanyRegisters(requestParameters.getCompanyNumber())));
            } catch (ServiceException se) {
                LOG.infoContext(requestParameters.getRequestId(), "Failed to get company registers data for company: "
                    + requestParameters.getCompanyNumber(), getDebugMap(requestParameters.getResourceUri()));
            }
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

    private void setCharges(CompanyReportApiData companyReportApiData, CompanyProfileApi companyProfileApi,
        RequestParameters requestParameters){

        if(companyProfileApi.getLinks().containsKey(CHARGES_KEY)) {

            try {
                LOG.infoContext(requestParameters.getRequestId(),
                    "Attempting to retrieve company charges data for company: " + requestParameters.getCompanyNumber(),
                    getDebugMap(requestParameters.getResourceUri()));
                companyReportApiData.setChargesApi(chargesService.getCharges(requestParameters.getCompanyNumber()));
            } catch (ServiceException se) {
                LOG.infoContext(requestParameters.getRequestId(), "Failed to get company charges data for company: "
                    + requestParameters.getCompanyNumber(), getDebugMap(requestParameters.getResourceUri()));
            }
        }
    }

    private Map<String, Object> getDebugMap(String resourceUri) {
        Map<String, Object> logMap = new HashMap<>();
        logMap.put("resource", resourceUri);

        return logMap;
    }

}
