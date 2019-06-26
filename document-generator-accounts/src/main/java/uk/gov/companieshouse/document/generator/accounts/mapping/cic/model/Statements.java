package uk.gov.companieshouse.document.generator.accounts.mapping.cic.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Statements {

    @JsonProperty("company_activities_and_impact")
    private String companyActivitiesAndImpact;

    @JsonProperty("consultation_with_stakeholders")
    private String consultationWithStakeholders;

    @JsonProperty("directors_remuneration")
    private String directorsRemuneration;

    @JsonProperty("transfer_of_assets")
    private String transferOfAssets;

    public String getCompanyActivitiesAndImpact() {
        return companyActivitiesAndImpact;
    }

    public void setCompanyActivitiesAndImpact(String companyActivitiesAndImpact) {
        this.companyActivitiesAndImpact = companyActivitiesAndImpact;
    }

    public String getConsultationWithStakeholders() {
        return consultationWithStakeholders;
    }

    public void setConsultationWithStakeholders(String consultationWithStakeholders) {
        this.consultationWithStakeholders = consultationWithStakeholders;
    }

    public String getDirectorsRemuneration() {
        return directorsRemuneration;
    }

    public void setDirectorsRemuneration(String directorsRemuneration) {
        this.directorsRemuneration = directorsRemuneration;
    }

    public String getTransferOfAssets() {
        return transferOfAssets;
    }

    public void setTransferOfAssets(String transferOfAssets) {
        this.transferOfAssets = transferOfAssets;
    }
}
