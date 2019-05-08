package uk.gov.companieshouse.document.generator.company.report.model;

import uk.gov.companieshouse.api.model.company.CompanyProfileApi;
import uk.gov.companieshouse.api.model.filinghistory.FilingHistoryApi;

public class CompanyReport {

    private CompanyProfileApi companyProfileApi;

    private FilingHistoryApi filingHistoryApi;

    public CompanyProfileApi getCompanyProfileApi() {
        return companyProfileApi;
    }

    public void setCompanyProfileApi(
        CompanyProfileApi companyProfileApi) {
        this.companyProfileApi = companyProfileApi;
    }

    public FilingHistoryApi getFilingHistoryApi() {
        return filingHistoryApi;
    }

    public void setFilingHistoryApi(FilingHistoryApi filingHistoryApi) {
        this.filingHistoryApi = filingHistoryApi;
    }
}
