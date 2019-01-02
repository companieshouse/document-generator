package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.accountingpolicies;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import java.util.Objects;

@JsonInclude(Include.NON_NULL)
public class AccountingPolicies {

    @JsonProperty("basis_of_measurement_and_preparation")
    private String basisOfMeasurementAndPreparation;

    @JsonProperty("turnover_policy")
    private String turnoverPolicy;

    @JsonProperty("tangible_fixed_assets_depreciation_policy")
    private String tangibleFixedAssetsDepreciationPolicy;

    @JsonProperty("intangible_fixed_assets_amortisation_policy")
    private String intangibleFixedAssetsAmortisationPolicy;

    @JsonProperty("valuation_information_and_policy")
    private String valuationInformationPolicy;

    @JsonProperty("other_accounting_policy")
    private String otherAccountingPolicy;

    public String getBasisOfMeasurementAndPreparation() {
        return basisOfMeasurementAndPreparation;
    }

    public void setBasisOfMeasurementAndPreparation(String basisOfMeasurementAndPreparation) {
        this.basisOfMeasurementAndPreparation = basisOfMeasurementAndPreparation;
    }

    public String getTurnoverPolicy() {
        return turnoverPolicy;
    }

    public void setTurnoverPolicy(String turnoverPolicy) {
        this.turnoverPolicy = turnoverPolicy;
    }

    public String getTangibleFixedAssetsDepreciationPolicy() {
        return tangibleFixedAssetsDepreciationPolicy;
    }

    public void setTangibleFixedAssetsDepreciationPolicy(
            String tangibleFixedAssetsDepreciationPolicy) {
        this.tangibleFixedAssetsDepreciationPolicy = tangibleFixedAssetsDepreciationPolicy;
    }

    public String getIntangibleFixedAssetsAmortisationPolicy() {
        return intangibleFixedAssetsAmortisationPolicy;
    }

    public void setIntangibleFixedAssetsAmortisationPolicy(
            String intangibleFixedAssetsAmortisationPolicy) {
        this.intangibleFixedAssetsAmortisationPolicy = intangibleFixedAssetsAmortisationPolicy;
    }

    public String getValuationInformationPolicy() {
        return valuationInformationPolicy;
    }

    public void setValuationInformationPolicy(String valuationInformationPolicy) {
        this.valuationInformationPolicy = valuationInformationPolicy;
    }

    public String getOtherAccountingPolicy() {
        return otherAccountingPolicy;
    }

    public void setOtherAccountingPolicy(String otherAccountingPolicy) {
        this.otherAccountingPolicy = otherAccountingPolicy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountingPolicies)) return false;
        AccountingPolicies accountingPolicies = (AccountingPolicies) o;
        return Objects.equals(getBasisOfMeasurementAndPreparation(), accountingPolicies.getBasisOfMeasurementAndPreparation()) &&
                Objects.equals(getTurnoverPolicy(), accountingPolicies.getTurnoverPolicy()) &&
                Objects.equals(getTangibleFixedAssetsDepreciationPolicy(), accountingPolicies.getTangibleFixedAssetsDepreciationPolicy()) &&
                Objects.equals(getIntangibleFixedAssetsAmortisationPolicy(), accountingPolicies.getIntangibleFixedAssetsAmortisationPolicy()) &&
                Objects.equals(getValuationInformationPolicy(), accountingPolicies.getValuationInformationPolicy()) &&
                Objects.equals(getOtherAccountingPolicy(), accountingPolicies.getOtherAccountingPolicy());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getBasisOfMeasurementAndPreparation(), getTurnoverPolicy(), getTangibleFixedAssetsDepreciationPolicy(),
                            getIntangibleFixedAssetsAmortisationPolicy(), getValuationInformationPolicy(), getOtherAccountingPolicy());
    }

    @Override
    public String toString() {return new Gson().toJson(this);
    }
}
