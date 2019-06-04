package uk.gov.companieshouse.document.generator.company.report.mapping.model.documentmodels.registrationinformation.items;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class SicCodes {

    @JsonProperty("sic_codes")
    private String sicCodes;

    @JsonProperty("sic_codes_description")
    private String sicCodesDescription;

    public String getSicCodes() {
        return sicCodes;
    }

    public void setSicCodes(String sicCodes) {
        this.sicCodes = sicCodes;
    }

    public String getSicCodesDescription() {
        return sicCodesDescription;
    }

    public void setSicCodesDescription(String sicCodesDescription) {
        this.sicCodesDescription = sicCodesDescription;
    }
}
