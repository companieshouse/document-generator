package uk.gov.companieshouse.document.generator.company.report.mapping.model.document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.util.List;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.keyfilingdates.KeyFilingDates;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.previousnames.PreviousNames;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.pscs.Pscs;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.registrationinformation.RegistrationInformation;

@JsonInclude(Include.NON_NULL)
@JsonTypeName("company_report")
@JsonTypeInfo(include = As.WRAPPER_OBJECT, use = Id.NAME)
public class CompanyReport {

    @JsonProperty("company_registration_information")
    private RegistrationInformation registrationInformation;

    @JsonProperty("previous_names")
    private List<PreviousNames> previousNames;

    @JsonProperty("key_filing_dates")
    private KeyFilingDates keyFilingDates;

    @JsonProperty("pscs")
    private Pscs pscs;

    public List<PreviousNames> getPreviousNames() {
        return previousNames;
    }

    public void setPreviousNames(List<PreviousNames> previousNames) {
        this.previousNames = previousNames;
    }

    public RegistrationInformation getRegistrationInformation() {
        return registrationInformation;
    }

    public void setRegistrationInformation(RegistrationInformation registrationInformation) {
        this.registrationInformation = registrationInformation;
    }

    public KeyFilingDates getKeyFilingDates() {
        return keyFilingDates;
    }

    public void setKeyFilingDates(KeyFilingDates keyFilingDates) {
        this.keyFilingDates = keyFilingDates;
    }

    public Pscs getPscs() {
        return pscs;
    }

    public void setPscs(Pscs pscs) {
        this.pscs = pscs;
    }
}
