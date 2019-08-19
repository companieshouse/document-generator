package uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.mortgagechargedetails.items;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

import java.time.LocalDate;
import java.util.List;

public class Charge {

    @JsonProperty("description")
    private String description;

    @JsonProperty("charge_code")
    private String chargeCode;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonProperty("created_date")
    private LocalDate createdDate;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonProperty("delivered")
    private LocalDate delivered;

    @JsonProperty("status")
    private String status;

    @JsonProperty("transaction")
    private String transaction;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonProperty("acquisition_date")
    private LocalDate acquisitionDate;

    @JsonProperty("assets_ceased")
    private String assetsCeased;

    @JsonProperty("persons_entitled")
    private List<PersonEntitled> personsEntitled;

    @JsonProperty("more_than_four_persons_entitled")
    private Boolean moreThanFourPersonsEntitled;

    @JsonProperty("secured_details_description")
    private String securedDetailsDescription;

    @JsonProperty("secured_details_type")
    private String securedDetailsType;

    @JsonProperty("particulars_type")
    private String type;

    @JsonProperty("particulars_description")
    private String particularsDescription;

    @JsonProperty("chargor_acting_as_bare_trustee")
    private Boolean chargorActingAsBareTrustee;

    @JsonProperty("contains_fixed_charge")
    private Boolean containsFixedCharge;

    @JsonProperty("contains_floating_charge")
    private Boolean containsFloatingCharge;

    @JsonProperty("contains_negative_pledge")
    private Boolean containsNegativePledge;

    @JsonProperty("floating_charge_covers_all")
    private Boolean floatingChargeCoversAll;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getChargeCode() {
        return chargeCode;
    }

    public void setChargeCode(String chargeCode) {
        this.chargeCode = chargeCode;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getDelivered() {
        return delivered;
    }

    public void setDelivered(LocalDate delivered) {
        this.delivered = delivered;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

    public LocalDate getAcquisitionDate() {
        return acquisitionDate;
    }

    public void setAcquisitionDate(LocalDate acquisitionDate) {
        this.acquisitionDate = acquisitionDate;
    }

    public String getAssetsCeased() {
        return assetsCeased;
    }

    public void setAssetsCeased(String assetsCeased) {
        this.assetsCeased = assetsCeased;
    }

    public List<PersonEntitled> getPersonsEntitled() {
        return personsEntitled;
    }

    public void setPersonsEntitled(List<PersonEntitled> personsEntitled) {
        this.personsEntitled = personsEntitled;
    }

    public Boolean getMoreThanFourPersonsEntitled() {
        return moreThanFourPersonsEntitled;
    }

    public void setMoreThanFourPersonsEntitled(Boolean moreThanFourPersonsEntitled) {
        this.moreThanFourPersonsEntitled = moreThanFourPersonsEntitled;
    }

    public String getSecuredDetailsDescription() {
        return securedDetailsDescription;
    }

    public void setSecuredDetailsDescription(String securedDetailsDescription) {
        this.securedDetailsDescription = securedDetailsDescription;
    }

    public String getSecuredDetailsType() {
        return securedDetailsType;
    }

    public void setSecuredDetailsType(String securedDetailsType) {
        this.securedDetailsType = securedDetailsType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParticularsDescription() {
        return particularsDescription;
    }

    public void setParticularsDescription(String particularsDescription) {
        this.particularsDescription = particularsDescription;
    }

    public Boolean getChargorActingAsBareTrustee() {
        return chargorActingAsBareTrustee;
    }

    public void setChargorActingAsBareTrustee(Boolean chargorActingAsBareTrustee) {
        this.chargorActingAsBareTrustee = chargorActingAsBareTrustee;
    }

    public Boolean getContainsFixedCharge() {
        return containsFixedCharge;
    }

    public void setContainsFixedCharge(Boolean containsFixedCharge) {
        this.containsFixedCharge = containsFixedCharge;
    }

    public Boolean getContainsFloatingCharge() {
        return containsFloatingCharge;
    }

    public void setContainsFloatingCharge(Boolean containsFloatingCharge) {
        this.containsFloatingCharge = containsFloatingCharge;
    }

    public Boolean getContainsNegativePledge() {
        return containsNegativePledge;
    }

    public void setContainsNegativePledge(Boolean containsNegativePledge) {
        this.containsNegativePledge = containsNegativePledge;
    }

    public Boolean getFloatingChargeCoversAll() {
        return floatingChargeCoversAll;
    }

    public void setFloatingChargeCoversAll(Boolean floatingChargeCoversAll) {
        this.floatingChargeCoversAll = floatingChargeCoversAll;
    }
}
