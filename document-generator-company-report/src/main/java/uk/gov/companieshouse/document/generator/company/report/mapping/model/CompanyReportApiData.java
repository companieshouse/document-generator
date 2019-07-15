package uk.gov.companieshouse.document.generator.company.report.mapping.model;

import uk.gov.companieshouse.api.model.company.CompanyProfileApi;
import uk.gov.companieshouse.api.model.psc.PscsApi;

public class CompanyReportApiData {

    private CompanyProfileApi companyProfileApi;

    private PscsApi pscsApi;

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
}
