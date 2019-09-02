package uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.foreigncompanydetails.items;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class AccountingRequirement {

    @JsonProperty("foreign_account_type")
    private String foreignAccountType;

    @JsonProperty("terms_of_account_publication")
    private String termsOfAccountPublication;

    public String getForeignAccountType() {
        return foreignAccountType;
    }

    public void setForeignAccountType(String foreignAccountType) {
        this.foreignAccountType = foreignAccountType;
    }

    public String getTermsOfAccountPublication() {
        return termsOfAccountPublication;
    }

    public void setTermsOfAccountPublication(String termsOfAccountPublication) {
        this.termsOfAccountPublication = termsOfAccountPublication;
    }
}
