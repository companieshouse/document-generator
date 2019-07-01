package uk.gov.companieshouse.document.generator.company.report.mapping.model;

import uk.gov.companieshouse.api.model.company.CompanyProfileApi;
import uk.gov.companieshouse.api.model.officers.OfficersApi;

public class CompanyReportApiData {

    private CompanyProfileApi companyProfileApi;

    private OfficersApi officerApi;

    public CompanyProfileApi getCompanyProfileApi() {
        return companyProfileApi;
    }

    public void setCompanyProfileApi(CompanyProfileApi companyProfileApi) {
        this.companyProfileApi = companyProfileApi;
    }

    public OfficersApi getOfficerApi() {
        return officerApi;
    }

    public void setOfficerApi(OfficersApi officerApi) {
        this.officerApi = officerApi;
    }
}
