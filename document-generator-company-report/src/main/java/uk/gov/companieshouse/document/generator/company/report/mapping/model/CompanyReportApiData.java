package uk.gov.companieshouse.document.generator.company.report.mapping.model;

import uk.gov.companieshouse.api.model.company.CompanyProfileApi;
import uk.gov.companieshouse.api.model.filinghistory.FilingApi;
import uk.gov.companieshouse.api.model.officers.OfficersApi;

public class CompanyReportApiData {

    private CompanyProfileApi companyProfileApi;

    private OfficersApi officersApi;

    private FilingApi filingApi;

    public CompanyProfileApi getCompanyProfileApi() {
        return companyProfileApi;
    }

    public void setCompanyProfileApi(CompanyProfileApi companyProfileApi) {
        this.companyProfileApi = companyProfileApi;
    }

    public OfficersApi getOfficersApi() {
        return officersApi;
    }

    public void setOfficersApi(OfficersApi officerApi) {
        this.officersApi = officerApi;
    }

    public FilingApi getFilingApi() {
        return filingApi;
    }

    public void setFilingApi(FilingApi filingApi) {
        this.filingApi = filingApi;
    }
}
