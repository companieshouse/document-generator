package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.notes.tangible;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TangibleAssets {

    @JsonProperty("cost")
    private TangibleAssetsCost cost;

    @JsonProperty("depreciation")
    private TangibleAssetsDepreciation depreciation;

    @JsonProperty("net_book_value")
    private TangibleAssetsNetBookValue netBookValue;

    @JsonProperty("additional_information")
    private String additionalInformation;

    public TangibleAssetsCost getCost() {
        return cost;
    }

    public void setCost(TangibleAssetsCost cost) {
        this.cost = cost;
    }

    public TangibleAssetsDepreciation getDepreciation() {
        return depreciation;
    }

    public void setDepreciation(TangibleAssetsDepreciation depreciation) {
        this.depreciation = depreciation;
    }

    public TangibleAssetsNetBookValue getNetBookValue() {
        return netBookValue;
    }

    public void setNetBookValue(TangibleAssetsNetBookValue netBookValue) {
        this.netBookValue = netBookValue;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }
}
