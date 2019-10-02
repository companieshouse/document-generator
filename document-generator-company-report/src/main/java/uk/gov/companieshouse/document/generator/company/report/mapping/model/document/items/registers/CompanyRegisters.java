package uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.registers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.registers.items.Register;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompanyRegisters {

    @JsonProperty("directors")
    private Register directorsRegister;

    @JsonProperty("llp_members")
    private Register llpMembersRegister;

    @JsonProperty("llp_usual_residential_address")
    private Register llpUsualResidentialAddressRegister;

    @JsonProperty("members")
    private Register membersRegister;

    @JsonProperty("persons_with_significant_control")
    private Register pscRegister;

    @JsonProperty("secretaries")
    private Register secretariesRegister;

    @JsonProperty("usual_residential_address")
    private Register usualResidentialAddressRegister;

    public Register getDirectorsRegister() {
        return directorsRegister;
    }

    public void setDirectorsRegister(Register directorsRegister) {
        this.directorsRegister = directorsRegister;
    }

    public Register getLlpMembersRegister() {
        return llpMembersRegister;
    }

    public void setLlpMembersRegister(Register llpMembersRegister) {
        this.llpMembersRegister = llpMembersRegister;
    }

    public Register getLlpUsualResidentialAddressRegister() {
        return llpUsualResidentialAddressRegister;
    }

    public void setLlpUsualResidentialAddressRegister(Register llpUsualResidentialAddressRegister) {
        this.llpUsualResidentialAddressRegister = llpUsualResidentialAddressRegister;
    }

    public Register getMembersRegister() {
        return membersRegister;
    }

    public void setMembersRegister(Register membersRegister) {
        this.membersRegister = membersRegister;
    }

    public Register getPscRegister() {
        return pscRegister;
    }

    public void setPscRegister(Register pscRegister) {
        this.pscRegister = pscRegister;
    }

    public Register getSecretariesRegister() {
        return secretariesRegister;
    }

    public void setSecretariesRegister(Register secretariesRegister) {
        this.secretariesRegister = secretariesRegister;
    }

    public Register getUsualResidentialAddressRegister() {
        return usualResidentialAddressRegister;
    }

    public void setUsualResidentialAddressRegister(Register usualResidentialAddressRegister) {
        this.usualResidentialAddressRegister = usualResidentialAddressRegister;
    }
}
