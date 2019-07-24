package uk.gov.companieshouse.document.generator.company.report.mapping.model;

import uk.gov.companieshouse.api.model.company.CompanyProfileApi;
import uk.gov.companieshouse.api.model.officers.OfficersApi;
import uk.gov.companieshouse.api.model.statements.StatementsApi;

public class CompanyReportApiData {

    private CompanyProfileApi companyProfileApi;

    private OfficersApi officersApi;

    private StatementsApi statementsApi;

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

    public StatementsApi getStatementsApi() {
        return statementsApi;
    }

    public void setStatementsApi(StatementsApi statementsApi) {
        this.statementsApi = statementsApi;
    }
}
