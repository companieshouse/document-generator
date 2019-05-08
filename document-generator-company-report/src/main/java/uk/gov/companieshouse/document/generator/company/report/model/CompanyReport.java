package uk.gov.companieshouse.document.generator.company.report.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import uk.gov.companieshouse.api.model.company.CompanyProfileApi;

@JsonTypeName("company_report")
@JsonTypeInfo(include = As.WRAPPER_OBJECT, use = Id.NAME)
public class CompanyReport {

    @JsonProperty("company_profile")
    private CompanyProfileApi companyProfileApi;

    public CompanyProfileApi getCompanyProfileApi() {
        return companyProfileApi;
    }

    public void setCompanyProfileApi(
        CompanyProfileApi companyProfileApi) {
        this.companyProfileApi = companyProfileApi;
    }
}
