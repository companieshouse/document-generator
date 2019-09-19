package uk.gov.companieshouse.document.generator.company.report.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriTemplate;
import uk.gov.companieshouse.api.ApiClient;
import uk.gov.companieshouse.api.error.ApiErrorResponseException;
import uk.gov.companieshouse.api.handler.charges.request.ChargesGet;
import uk.gov.companieshouse.api.handler.exception.URIValidationException;
import uk.gov.companieshouse.api.handler.filinghistory.request.FilingHistoryList;
import uk.gov.companieshouse.api.handler.officers.request.OfficersList;
import uk.gov.companieshouse.api.handler.psc.request.PscsList;
import uk.gov.companieshouse.api.handler.registers.request.RegistersList;
import uk.gov.companieshouse.api.handler.statements.request.StatementsList;
import uk.gov.companieshouse.api.handler.ukestablishments.request.UkEstablishmentsList;
import uk.gov.companieshouse.api.model.charges.ChargesApi;
import uk.gov.companieshouse.api.model.company.CompanyProfileApi;
import uk.gov.companieshouse.api.model.filinghistory.FilingHistoryApi;
import uk.gov.companieshouse.api.model.insolvency.InsolvencyApi;
import uk.gov.companieshouse.api.model.officers.OfficersApi;
import uk.gov.companieshouse.api.model.psc.PscsApi;
import uk.gov.companieshouse.api.model.registers.CompanyRegistersApi;
import uk.gov.companieshouse.api.model.statements.StatementsApi;
import uk.gov.companieshouse.api.model.ukestablishments.UkEstablishmentsApi;
import uk.gov.companieshouse.document.generator.company.report.exception.ServiceException;

@Service
public class CompanyReportService {

    private CompanyReportApiClientService companyReportApiClientService;

    private static final UriTemplate GET_COMPANY_URI =
        new UriTemplate("/company/{companyNumber}");

    private static final UriTemplate GET_OFFICERS_URI =
        new UriTemplate("/company/{companyNumber}/officers");

    private static final UriTemplate GET_RECENT_FILING_HISTORY_URI =
        new UriTemplate("/company/{company_number}/filing-history");

    private static final UriTemplate GET_PSCS_URI =
        new UriTemplate("/company/{companyNumber}/persons-with-significant-control");

    private static final UriTemplate GET_STATEMENTS_URI =
        new UriTemplate("/company/{companyNumber}/persons-with-significant-control-statements");

    private static final UriTemplate GET_CHARGES_URI =
        new UriTemplate("/company/{companyNumber}/charges");

    private static final UriTemplate GET_INSOLVENCY_URI =
        new UriTemplate("/company/{companyNumber}/insolvency");

    private static final UriTemplate GET_REGISTERS_URI =
        new UriTemplate("/company/{companyNumber}/registers");

    private static final UriTemplate GET_UK_ESTABLISHMENTS_URI =
        new UriTemplate("/company/{companyNumber}/uk-establishments");

    private static final String ITEMS_PER_PAGE = "items_per_page";
    private static final String ITEMS_PER_PAGE_NUMBER = "100";

    @Autowired
    public CompanyReportService(CompanyReportApiClientService companyReportApiClientService) {
        this.companyReportApiClientService = companyReportApiClientService;
    }

    public CompanyProfileApi getCompanyProfile(String companyNumber) throws ServiceException {

        CompanyProfileApi companyProfileApi;

        ApiClient apiClient = companyReportApiClientService.getApiClient();

        String uri = GET_COMPANY_URI.expand(companyNumber).toString();

        try {
            companyProfileApi = apiClient.company().get(uri).execute().getData();
        } catch (ApiErrorResponseException e) {

            throw new ServiceException("Error retrieving company profile", e);
        } catch (URIValidationException e) {

            throw new ServiceException("Invalid URI for company resource", e);
        }

        return companyProfileApi;
    }

    public OfficersApi getOfficers(String companyNumber) throws ServiceException {

        OfficersApi officersApi;

        ApiClient apiClient = companyReportApiClientService.getApiClient();

        String uri = GET_OFFICERS_URI.expand(companyNumber).toString();

        try {
            OfficersList officersList = apiClient.officers().list(uri);
            officersList.addQueryParams(ITEMS_PER_PAGE, ITEMS_PER_PAGE_NUMBER);

            officersApi = officersList.execute().getData();
        } catch (ApiErrorResponseException e) {

            throw new ServiceException("Error retrieving officers", e);
        } catch (URIValidationException e) {

            throw new ServiceException("Invalid URI for officers resource", e);
        }
        return officersApi;
    }

    public FilingHistoryApi getFilingHistory(String companyNumber) throws ServiceException {

        FilingHistoryApi filingHistoryApi;

        ApiClient apiClient = companyReportApiClientService.getApiClient();

        String uri = GET_RECENT_FILING_HISTORY_URI.expand(companyNumber).toString();

        try {
            FilingHistoryList filingHistoryList = apiClient.filingHistory().list(uri);
            filingHistoryList.addQueryParams(ITEMS_PER_PAGE, ITEMS_PER_PAGE_NUMBER);

            filingHistoryApi = filingHistoryList.execute().getData();
        } catch (ApiErrorResponseException e) {

            throw new ServiceException("Error retrieving recent filing history", e);
        } catch (URIValidationException e) {

            throw new ServiceException("Invalid URI for recent filing history resource", e);
        }
        return filingHistoryApi;
    }

    public PscsApi getPscs(String companyNumber) throws ServiceException {


        ApiClient apiClient = companyReportApiClientService.getApiClient();

        String uri = GET_PSCS_URI.expand(companyNumber).toString();

        try {
            PscsList pscsList = apiClient.pscs().list(uri);
            pscsList.addQueryParams(ITEMS_PER_PAGE, ITEMS_PER_PAGE_NUMBER);

            return pscsList.execute().getData();
        } catch (ApiErrorResponseException e) {
            throw new ServiceException("Error retrieving pscs", e);
        } catch (URIValidationException e) {

            throw new ServiceException("Invalid URI for pscs resource", e);
        }
    }

    public StatementsApi getStatements(String companyNumber) throws ServiceException {

        StatementsApi statementsApi;

        ApiClient apiClient = companyReportApiClientService.getApiClient();

        String uri = GET_STATEMENTS_URI.expand(companyNumber).toString();

        try {
            StatementsList statementsList = apiClient.statements().list(uri);
            statementsList.addQueryParams(ITEMS_PER_PAGE, ITEMS_PER_PAGE_NUMBER);

            statementsApi = statementsList.execute().getData();
        } catch (ApiErrorResponseException e) {

            throw new ServiceException("Error retrieving psc statements", e);
        } catch (URIValidationException e) {

            throw new ServiceException("Invalid URI for psc statements resource", e);
        }
        return statementsApi;
    }

    public ChargesApi getCharges(String companyNumber) throws ServiceException {

        ChargesApi chargesApi;

        ApiClient apiClient = companyReportApiClientService.getApiClient();

        String uri = GET_CHARGES_URI.expand(companyNumber).toString();

        try {
            ChargesGet chargesGet = apiClient.charges().get(uri);
            chargesGet.addQueryParams(ITEMS_PER_PAGE, ITEMS_PER_PAGE_NUMBER);

            chargesApi = chargesGet.execute().getData();
        } catch (ApiErrorResponseException e) {

            throw new ServiceException("Error retrieving company charges", e);
        } catch (URIValidationException e) {

            throw new ServiceException("Invalid URI for company charges", e);
        }

        return chargesApi;
    }

    public InsolvencyApi getInsolvency(String companyNumber) throws ServiceException {

        InsolvencyApi insolvencyApi;

        ApiClient apiClient = companyReportApiClientService.getApiClient();

        String uri = GET_INSOLVENCY_URI.expand(companyNumber).toString();

        try {
            insolvencyApi = apiClient.insolvency().get(uri).execute().getData();
        } catch (ApiErrorResponseException e) {

            throw new ServiceException("Error retrieving insolvency", e);
        } catch (URIValidationException e) {

            throw new ServiceException("Invalid URI for insolvency resource", e);
        }
        return insolvencyApi;
    }

    public CompanyRegistersApi getCompanyRegisters(String companyNumber) throws ServiceException {

        CompanyRegistersApi companyRegistersApi;

        ApiClient apiClient = companyReportApiClientService.getApiClient();

        String uri = GET_REGISTERS_URI.expand(companyNumber).toString();

        try {
            RegistersList registersList = apiClient.registers().list(uri);

            companyRegistersApi = registersList.execute().getData();
        } catch (ApiErrorResponseException e) {

            throw new ServiceException("Error retrieving company registers", e);
        } catch (URIValidationException e) {

            throw new ServiceException("Invalid URI for company registers resource", e);
        }
        return companyRegistersApi;
    }

    public UkEstablishmentsApi getUkEstablishments(String companyNumber) throws ServiceException {

        UkEstablishmentsApi ukEstablishmentsApi;

        ApiClient apiClient = companyReportApiClientService.getApiClient();

        String uri = GET_UK_ESTABLISHMENTS_URI.expand(companyNumber).toString();

        try {
            UkEstablishmentsList ukEstablishmentsList = apiClient.ukEstablishments().list(uri);
            ukEstablishmentsApi = ukEstablishmentsList.execute().getData();

        } catch (ApiErrorResponseException e) {

            throw new ServiceException("Error retrieving uk establishment", e);
        } catch (URIValidationException e) {

            throw new ServiceException("Invalid URI for uk establishment resource", e);
        }
        return ukEstablishmentsApi;
    }
}
