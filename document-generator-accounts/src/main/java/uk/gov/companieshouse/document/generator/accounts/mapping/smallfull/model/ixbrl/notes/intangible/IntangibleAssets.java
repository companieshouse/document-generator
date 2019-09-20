package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.notes.intangible;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class IntangibleAssets {

    @JsonProperty("cost")
    private IntangibleAssetsCost cost;

    @JsonProperty("amortisation")
    private IntangibleAssetsAmortisation amortisation;

    @JsonProperty("net_book_value")
    private IntangibleAssetsNetBookValue netBookValue;

    @JsonProperty("additional_information")
    private String additionalInformation;

    public IntangibleAssetsCost getCost() {
        return cost;
    }

    public void setCost(IntangibleAssetsCost cost) {
        this.cost = cost;
    }

    public IntangibleAssetsAmortisation getAmortisation() {
        return amortisation;
    }

    public void setAmortisation(IntangibleAssetsAmortisation amortisation) {
        this.amortisation = amortisation;
    }

    public IntangibleAssetsNetBookValue getNetBookValue() {
        return netBookValue;
    }

    public void setNetBookValue(IntangibleAssetsNetBookValue netBookValue) {
        this.netBookValue = netBookValue;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }
}
