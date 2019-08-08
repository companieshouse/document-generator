package uk.gov.companieshouse.document.generator.company.report.mapping.model;

import uk.gov.companieshouse.api.model.company.CompanyProfileApi;
import uk.gov.companieshouse.api.model.filinghistory.FilingHistoryApi;
import uk.gov.companieshouse.api.model.insolvency.InsolvencyApi;
import uk.gov.companieshouse.api.model.psc.PscsApi;
import uk.gov.companieshouse.api.model.officers.OfficersApi;
import uk.gov.companieshouse.api.model.statements.StatementsApi;

public class CompanyReportApiData {

    private CompanyProfileApi companyProfileApi;

    private PscsApi pscsApi;

    private OfficersApi officersApi;

    private FilingHistoryApi filingHistoryApi;

    private StatementsApi statementsApi;

    private InsolvencyApi insolvencyApi;

    public CompanyProfileApi getCompanyProfileApi() {
        return companyProfileApi;
    }

    public void setCompanyProfileApi(CompanyProfileApi companyProfileApi) {
        this.companyProfileApi = companyProfileApi;
    }

    public PscsApi getPscsApi() {
        return pscsApi;
    }

    public void setPscsApi(PscsApi pscsApi) {
        this.pscsApi = pscsApi;
    }

    public OfficersApi getOfficersApi() {
        return officersApi;
    }

    public void setOfficersApi(OfficersApi officersApi) {
        this.officersApi = officersApi;
    }

    public FilingHistoryApi getFilingHistoryApi() {
        return filingHistoryApi;
    }

    public void setFilingHistoryApi(FilingHistoryApi filingHistoryApi) {
        this.filingHistoryApi = filingHistoryApi;
    }

    public StatementsApi getStatementsApi() {
        return statementsApi;
    }

    public void setStatementsApi(StatementsApi statementsApi) {
        this.statementsApi = statementsApi;
    }

    public InsolvencyApi getInsolvencyApi() {
        return insolvencyApi;
    }

    public void setInsolvencyApi(InsolvencyApi insolvencyApi) {
        this.insolvencyApi = insolvencyApi;
    }
}
