package uk.gov.companieshouse.document.generator.company.report.data;

import org.springframework.stereotype.Component;
import uk.gov.companieshouse.api.error.ApiErrorResponseException;
import uk.gov.companieshouse.api.handler.exception.URIValidationException;
import uk.gov.companieshouse.api.model.company.CompanyProfileApi;
import uk.gov.companieshouse.api.model.filinghistory.FilingApi;
import uk.gov.companieshouse.api.model.filinghistory.FilingHistoryApi;
import uk.gov.companieshouse.api.model.statements.StatementApi;
import uk.gov.companieshouse.api.model.statements.StatementsApi;
import uk.gov.companieshouse.document.generator.company.report.exception.ApiDataException;
import uk.gov.companieshouse.document.generator.company.report.exception.HandlerException;
import uk.gov.companieshouse.document.generator.company.report.exception.ServiceException;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.CompanyReportApiData;
import uk.gov.companieshouse.document.generator.company.report.service.CompanyService;
import uk.gov.companieshouse.document.generator.company.report.service.InsolvencyService;
import uk.gov.companieshouse.document.generator.company.report.service.OfficerService;
import uk.gov.companieshouse.document.generator.company.report.service.PscsService;
import uk.gov.companieshouse.document.generator.company.report.service.RecentFilingHistoryService;
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

    public CompanyReportDataManager (CompanyService companyService,
                                     PscsService pscsService,
                                     OfficerService officerService,
                                     UkEstablishmentService ukEstablishmentService,
                                     RecentFilingHistoryService recentFilingHistoryService,
                                     StatementsService statementsService,
                                     InsolvencyService insolvencyService) {

        this.companyService = companyService;
        this.pscsService = pscsService;
        this.officerService = officerService;
        this.ukEstablishmentService = ukEstablishmentService;
        this.recentFilingHistoryService = recentFilingHistoryService;
        this.statementsService = statementsService;
        this.insolvencyService = insolvencyService;
    }

    private static final Logger LOG = LoggerFactory.getLogger(MODULE_NAME_SPACE);
    private static final String FILING_HISTORY_KEY = "filing_history";
    private static final String PSCS_KEY = "persons_with_significant_control";
    private static final String OFFICERS_KEY = "officers";
    private static final String UK_ESTABLISHMENTS = "uk_establishments";
    private static final String STATEMENTS_KEY = "persons_with_significant_control_statements";
    private static final String INSOLVENCY_KEY = "insolvency";


    public CompanyReportApiData getCompanyReportData(String companyNumber,  String requestId)
        throws ApiDataException {

        CompanyReportApiData companyReportApiData = new CompanyReportApiData();

        CompanyProfileApi companyProfileApi = getCompanyProfile(companyNumber, requestId);

        setCompanyReportData(companyNumber, requestId, companyReportApiData, companyProfileApi);

        return companyReportApiData;
    }

    private void setCompanyReportData(String companyNumber, String requestId,
        CompanyReportApiData companyReportApiData, CompanyProfileApi companyProfileApi)
        throws ApiDataException {

        companyReportApiData.setCompanyProfileApi(companyProfileApi);

        setOfficersData(companyNumber, requestId, companyReportApiData, companyProfileApi);
        setUkEstablishmentsData(companyNumber, requestId, companyReportApiData, companyProfileApi);
        setFilingHistoryData(companyNumber, requestId, companyReportApiData, companyProfileApi);
        setStatementsData(companyNumber, requestId, companyReportApiData, companyProfileApi);
        setPscsData(companyNumber, requestId, companyReportApiData, companyProfileApi);

    }

    private CompanyProfileApi getCompanyProfile(String companyNumber, String requestId) throws ApiDataException {

        try {
            LOG.infoContext(requestId,"Attempting to retrieve company profile data for company: " + companyNumber, getDebugMap(companyNumber));
            return companyService.getCompanyProfile(companyNumber);
        } catch (ServiceException se) {
            throw new ApiDataException("error occurred obtaining the company profile data for company: " + companyNumber, se);
        }
    }

    private void setOfficersData(String companyNumber, String requestId,
                                        CompanyReportApiData companyReportApiData,
                                        CompanyProfileApi companyProfileApi) throws ApiDataException {

        if(companyProfileApi.getLinks().containsKey(OFFICERS_KEY)) {

            try {
                LOG.infoContext(requestId,"Attempting to retrieve company officers data for company: " + companyNumber, getDebugMap(companyNumber));
                companyReportApiData.setOfficersApi(officerService.getOfficers(companyNumber));
            } catch (ServiceException se) {
                throw new ApiDataException("error occurred obtaining the company officers data for company: " + companyNumber, se);
            }
        }
    }

    private void setUkEstablishmentsData(String companyNumber, String requestId,
                                         CompanyReportApiData companyReportApiData,
                                         CompanyProfileApi companyProfileApi) throws ApiDataException {

        if(companyProfileApi.getLinks().containsKey(UK_ESTABLISHMENTS)) {

            try {
                LOG.infoContext(requestId, "Attempting to retrieve uk establishment data for company: " + companyNumber, getDebugMap(companyNumber));
                companyReportApiData.setUkEstablishmentsApi(ukEstablishmentService
                    .getUkEstablishments(companyNumber));
            } catch (ServiceException se) {
                throw new ApiDataException("error occurred obtaining uk establishments data for company: " + companyNumber, se);
            }
        }
    }

    private void setFilingHistoryData(String companyNumber, String requestId,
                                      CompanyReportApiData companyReportApiData,
                                      CompanyProfileApi companyProfileApi) throws ApiDataException {

        if(companyProfileApi.getLinks().containsKey(FILING_HISTORY_KEY)) {

            try {
                LOG.infoContext(requestId, "Attempting to retrieve company filing history data for company: " + companyNumber, getDebugMap(companyNumber));
                companyReportApiData.setFilingHistoryApi(sortFilingHistory(
                    recentFilingHistoryService.getFilingHistory(companyNumber)));
            } catch (ServiceException se) {
                throw new ApiDataException("error occurred obtaining the company filing history data for company: " + companyNumber, se);
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

    private void setStatementsData(String companyNumber, String requestId,
                                   CompanyReportApiData companyReportApiData,
                                   CompanyProfileApi companyProfileApi) throws ApiDataException {

        if(companyProfileApi.getLinks().containsKey(STATEMENTS_KEY)) {

            try {
                LOG.infoContext(requestId, "Attempting to retrieve company psc statements data for company: " + companyNumber, getDebugMap(companyNumber));
                companyReportApiData.setStatementsApi(sortStatements(statementsService.getStatements(companyNumber)));
            } catch (ServiceException se) {
                throw new ApiDataException("error occurred obtaining the company psc statements data for company: " + companyNumber, se);
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

    private void setPscsData(String companyNumber, String requestId,
                                CompanyReportApiData companyReportApiData,
                                CompanyProfileApi companyProfileApi) throws ApiDataException {

        if(companyProfileApi.getLinks().containsKey(PSCS_KEY)) {

            try {
                LOG.infoContext(requestId,"Attempting to retrieve company PSCS data for company: " + companyNumber, getDebugMap(companyNumber));
                companyReportApiData.setPscsApi(pscsService.getPscs(companyNumber));
            } catch (ServiceException se) {
                throw new ApiDataException("error occurred obtaining the company PSCS data for company: " + companyNumber, se);

            }
        }
    }

    private Map<String, Object> getDebugMap(String companyNumber) {
        Map<String, Object> logMap = new HashMap<>();
        logMap.put("COMPANY_NUMBER", companyNumber);

        return logMap;
    }

}
