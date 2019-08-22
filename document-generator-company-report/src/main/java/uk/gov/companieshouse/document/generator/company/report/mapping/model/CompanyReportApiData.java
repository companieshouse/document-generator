package uk.gov.companieshouse.document.generator.company.report.mapping.model;

import uk.gov.companieshouse.api.model.company.CompanyProfileApi;
import uk.gov.companieshouse.api.model.filinghistory.FilingHistoryApi;
import uk.gov.companieshouse.api.model.officers.OfficersApi;
import uk.gov.companieshouse.api.model.psc.PscsApi;
import uk.gov.companieshouse.api.model.registers.CompanyRegistersApi;
import uk.gov.companieshouse.api.model.statements.StatementsApi;
import uk.gov.companieshouse.api.model.ukestablishments.UkEstablishmentsApi;

public class CompanyReportApiData {

    private CompanyProfileApi companyProfileApi;

    private PscsApi pscsApi;

    private OfficersApi officersApi;

    private UkEstablishmentsApi ukEstablishmentsApi;

    private FilingHistoryApi filingHistoryApi;

    private StatementsApi statementsApi;

    private CompanyRegistersApi companyRegistersApi;

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

    public UkEstablishmentsApi getUkEstablishmentsApi() {
        return ukEstablishmentsApi;
    }

    public void setUkEstablishmentsApi(UkEstablishmentsApi ukEstablishmentsApi) {
        this.ukEstablishmentsApi = ukEstablishmentsApi;
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

    public CompanyRegistersApi getCompanyRegistersApi() {
        return companyRegistersApi;
    }

    public void setCompanyRegistersApi(CompanyRegistersApi companyRegistersApi) {
        this.companyRegistersApi = companyRegistersApi;
    }
}
