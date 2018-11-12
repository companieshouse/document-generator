package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.otherliabilitiesandassets;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.otherliabilitiesandassets.items.AccrualsAndDeferredIncome;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.otherliabilitiesandassets.items.CreditorsAmountsFallingDueAfterMoreThanOneYear;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.otherliabilitiesandassets.items.CreditorsAmountsFallingDueWithinOneYear;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.otherliabilitiesandassets.items.TotalAssetsLessCurrentLiabilities;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.otherliabilitiesandassets.items.NetCurrentAssets;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.otherliabilitiesandassets.items.PrepaymentsAndAccruedIncome;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.otherliabilitiesandassets.items.ProvisionForLiabilities;

import java.util.Objects;

public class OtherLiabilitiesOrAssets {

    @JsonProperty("prepayments_and_accrued_income")
    private PrepaymentsAndAccruedIncome prepaymentsAndAccruedIncome;

    @JsonProperty("creditors_amounts_falling_due_within_one_year")
    private CreditorsAmountsFallingDueWithinOneYear creditorsAmountsFallingDueWithinOneYear;

    @JsonProperty("net_current_assets")
    private NetCurrentAssets netCurrentAssets;

    @JsonProperty("total_assets_less_current_liabilities")
    private TotalAssetsLessCurrentLiabilities totalAssetsLessCurrentLiabilities;

    @JsonProperty("creditors_amounts_falling_due_after_more_than_one_year")
    private CreditorsAmountsFallingDueAfterMoreThanOneYear creditorsAmountsFallingDueAfterMoreThanOneYear;

    @JsonProperty("provision_for_liabilities")
    private ProvisionForLiabilities provisionForLiabilities;

    @JsonProperty("accruals_and_deferred_income")
    private AccrualsAndDeferredIncome accrualsAndDeferredIncome;

    @JsonProperty("current_total_net_assets")
    private Long currentTotalNetAssets;

    @JsonProperty("previous_total_net_assets")
    private Long previousTotalNetAssets;

    public PrepaymentsAndAccruedIncome getPrepaymentsAndAccruedIncome() {
        return prepaymentsAndAccruedIncome;
    }

    public void setPrepaymentsAndAccruedIncome(PrepaymentsAndAccruedIncome prepaymentsAndAccruedIncome) {
        this.prepaymentsAndAccruedIncome = prepaymentsAndAccruedIncome;
    }

    public CreditorsAmountsFallingDueWithinOneYear getCreditorsAmountsFallingDueWithinOneYear() {
        return creditorsAmountsFallingDueWithinOneYear;
    }

    public void setCreditorsAmountsFallingDueWithinOneYear(CreditorsAmountsFallingDueWithinOneYear creditorsAmountsFallingDueWithinOneYear) {
        this.creditorsAmountsFallingDueWithinOneYear = creditorsAmountsFallingDueWithinOneYear;
    }

    public NetCurrentAssets getNetCurrentAssets() {
        return netCurrentAssets;
    }

    public void setNetCurrentAssets(NetCurrentAssets netCurrentAssets) {
        this.netCurrentAssets = netCurrentAssets;
    }

    public TotalAssetsLessCurrentLiabilities getTotalAssetsLessCurrentLiabilities() {
        return totalAssetsLessCurrentLiabilities;
    }

    public void setTotalAssetsLessCurrentLiabilities(TotalAssetsLessCurrentLiabilities totalAssetsLessCurrentLiabilities) {
        this.totalAssetsLessCurrentLiabilities = totalAssetsLessCurrentLiabilities;
    }


    public CreditorsAmountsFallingDueAfterMoreThanOneYear getCreditorsAmountsFallingDueAfterMoreThanOneYear() {
        return creditorsAmountsFallingDueAfterMoreThanOneYear;
    }

    public void setCreditorsAmountsFallingDueAfterMoreThanOneYear(CreditorsAmountsFallingDueAfterMoreThanOneYear creditorsAmountsFallingDueAfterMoreThanOneYear) {
        this.creditorsAmountsFallingDueAfterMoreThanOneYear = creditorsAmountsFallingDueAfterMoreThanOneYear;
    }

    public ProvisionForLiabilities getProvisionForLiabilities() {
        return provisionForLiabilities;
    }

    public void setProvisionForLiabilities(ProvisionForLiabilities provisionForLiabilities) {
        this.provisionForLiabilities = provisionForLiabilities;
    }

    public AccrualsAndDeferredIncome getAccrualsAndDeferredIncome() {
        return accrualsAndDeferredIncome;
    }

    public void setAccrualsAndDeferredIncome(AccrualsAndDeferredIncome accrualsAndDeferredIncome) {
        this.accrualsAndDeferredIncome = accrualsAndDeferredIncome;
    }

    public Long getCurrentTotalNetAssets() {
        return currentTotalNetAssets;
    }

    public void setCurrentTotalNetAssets(Long currentTotalNetAssets) {
        this.currentTotalNetAssets = currentTotalNetAssets;
    }

    public Long getPreviousTotalNetAssets() {
        return previousTotalNetAssets;
    }

    public void setPreviousTotalNetAssets(Long previousTotalNetAssets) {
        this.previousTotalNetAssets = previousTotalNetAssets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OtherLiabilitiesOrAssets)) return false;
        OtherLiabilitiesOrAssets that = (OtherLiabilitiesOrAssets) o;
        return getCurrentTotalNetAssets() == that.getCurrentTotalNetAssets() &&
                getPreviousTotalNetAssets() == that.getPreviousTotalNetAssets() &&
                Objects.equals(getPrepaymentsAndAccruedIncome(), that.getPrepaymentsAndAccruedIncome()) &&
                Objects.equals(getCreditorsAmountsFallingDueWithinOneYear(), that.getCreditorsAmountsFallingDueWithinOneYear()) &&
                Objects.equals(getNetCurrentAssets(), that.getNetCurrentAssets()) &&
                Objects.equals(getTotalAssetsLessCurrentLiabilities(), that.getTotalAssetsLessCurrentLiabilities()) &&
                Objects.equals(getCreditorsAmountsFallingDueAfterMoreThanOneYear(), that.getCreditorsAmountsFallingDueAfterMoreThanOneYear()) &&
                Objects.equals(getProvisionForLiabilities(), that.getProvisionForLiabilities()) &&
                Objects.equals(getAccrualsAndDeferredIncome(), that.getAccrualsAndDeferredIncome());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getPrepaymentsAndAccruedIncome(), getCreditorsAmountsFallingDueWithinOneYear(),
                getNetCurrentAssets(), getTotalAssetsLessCurrentLiabilities(), getCreditorsAmountsFallingDueAfterMoreThanOneYear(),
                getProvisionForLiabilities(), getAccrualsAndDeferredIncome(), getCurrentTotalNetAssets(), getPreviousTotalNetAssets());
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
