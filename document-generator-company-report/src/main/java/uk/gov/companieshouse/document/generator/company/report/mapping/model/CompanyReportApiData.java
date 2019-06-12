package uk.gov.companieshouse.document.generator.company.report.mapping.model;

import uk.gov.companieshouse.api.model.company.CompanyProfileApi;

public class CompanyReportApiData {

    private CompanyProfileApi companyProfileApi;

    public CompanyProfileApi getCompanyProfileApi() {
        return companyProfileApi;
    }

    public void setCompanyProfileApi(CompanyProfileApi companyProfileApi) {
        this.companyProfileApi = companyProfileApi;
    }
}
