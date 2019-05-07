package uk.gov.companieshouse.document.generator.company.report.model;

import uk.gov.companieshouse.api.model.company.CompanyProfileApi;

public class CompanyReport {

    private CompanyProfileApi companyProfileApi;

    public CompanyProfileApi getCompanyProfileApi() {
        return companyProfileApi;
    }

    public void setCompanyProfileApi(
        CompanyProfileApi companyProfileApi) {
        this.companyProfileApi = companyProfileApi;
    }
}
