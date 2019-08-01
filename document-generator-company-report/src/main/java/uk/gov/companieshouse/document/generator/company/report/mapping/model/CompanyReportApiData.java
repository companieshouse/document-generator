package uk.gov.companieshouse.document.generator.company.report.mapping.model;

import uk.gov.companieshouse.api.model.company.CompanyProfileApi;
import uk.gov.companieshouse.api.model.psc.PscsApi;
import uk.gov.companieshouse.api.model.officers.OfficersApi;
import uk.gov.companieshouse.api.model.ukestablishments.UkEstablishmentsApi;

public class CompanyReportApiData {

    private CompanyProfileApi companyProfileApi;

    private PscsApi pscsApi;

    private OfficersApi officersApi;

    private UkEstablishmentsApi ukEstablishmentsApi;

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
}
