package uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.mortgagechargedetails.items;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Charge {

    @JsonProperty("charge_description")
    private String chargeDescription;

    @JsonProperty("type")
    private String type;

    @JsonProperty("classification_description")
    private String classificationDescription;

    @JsonProperty("created")
    private String created;

    @JsonProperty("delivered")
    private String delivered;

    @JsonProperty("status")
    private String status;

    @JsonProperty("satisfied_on")
    private String satisfiedOn;

    @JsonProperty("assets_ceased_released")
    private String assetsCeasedReleased;

    @JsonProperty("more_than_four_persons_entitled")
    private boolean moreThanFourPersonsEntitled;

    @JsonProperty("persons_entitled")
    private List<PersonsEntitled> personsEntitled;

    @JsonProperty("secured_details")
    private SecuredDetails securedDetails;

    @JsonProperty("particulars")
    private Particulars particulars;

    public String getChargeDescription() {
        return chargeDescription;
    }

    public void setChargeDescription(String chargeDescription) {
        this.chargeDescription = chargeDescription;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getDelivered() {
        return delivered;
    }

    public void setDelivered(String delivered) {
        this.delivered = delivered;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSatisfiedOn() {
        return satisfiedOn;
    }

    public void setSatisfiedOn(String satisfiedOn) {
        this.satisfiedOn = satisfiedOn;
    }

    public String getAssetsCeasedReleased() {
        return assetsCeasedReleased;
    }

    public void setAssetsCeasedReleased(String assetsCeasedReleased) {
        this.assetsCeasedReleased = assetsCeasedReleased;
    }

    public boolean isMoreThanFourPersonsEntitled() {
        return moreThanFourPersonsEntitled;
    }

    public void setMoreThanFourPersonsEntitled(boolean moreThanFourPersonsEntitled) {
        this.moreThanFourPersonsEntitled = moreThanFourPersonsEntitled;
    }

    public List<PersonsEntitled> getPersonsEntitled() {
        return personsEntitled;
    }

    public void setPersonsEntitled(
        List<PersonsEntitled> personsEntitled) {
        this.personsEntitled = personsEntitled;
    }

    public SecuredDetails getSecuredDetails() {
        return securedDetails;
    }

    public void setSecuredDetails(
        SecuredDetails securedDetails) {
        this.securedDetails = securedDetails;
    }

    public Particulars getParticulars() {
        return particulars;
    }

    public void setParticulars(
        Particulars particulars) {
        this.particulars = particulars;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getClassificationDescription() {
        return classificationDescription;
    }

    public void setClassificationDescription(String classificationDescription) {
        this.classificationDescription = classificationDescription;
    }
}
