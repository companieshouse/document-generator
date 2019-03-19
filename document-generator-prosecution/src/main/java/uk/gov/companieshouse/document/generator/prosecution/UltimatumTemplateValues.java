package uk.gov.companieshouse.document.generator.prosecution;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Holds the variable field values to be filled in the template for an Ultimatum
 * letter.
 */
public class UltimatumTemplateValues {
    @JsonProperty("DefendantName")
    private String defendantName;

    @JsonProperty("template_registry_addr")
    private String templateRegistryAddr;

    @JsonProperty("CompanyName")
    private String companyName;

    @JsonProperty("CompanyNumber")
    private String companyNumber;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyNumber() {
        return companyNumber;
    }

    public void setCompanyNumber(String companyNumber) {
        this.companyNumber = companyNumber;
    }

    /**
     * Gets the address of the registry that stores the template that these
     * values (held in an instance of this) pertain to.
     * @return
     */
    public String getTemplateRegistryAddress() {
        return templateRegistryAddr;
    }

    /**
     * Sets the address of the registry that stores the template that these
     * values (held in an instance of this) pertain to.
     * @param templateRegistryAddr
     */
    public void setTemplateRegistryAddress(String templateRegistryAddr) {
        this.templateRegistryAddr = templateRegistryAddr;
    }

    public String getDefendantName() {
        return defendantName;
    }

    public void setDefendantName(String defendantName) {
        this.defendantName = defendantName;
    }
}
