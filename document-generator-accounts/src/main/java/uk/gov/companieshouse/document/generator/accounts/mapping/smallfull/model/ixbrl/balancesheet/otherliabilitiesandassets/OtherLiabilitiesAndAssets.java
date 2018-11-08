package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.otherliabilitiesandassets;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.otherliabilitiesandassets.items.AccrualsAndDeferredIncome;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.otherliabilitiesandassets.items.CreditorsAmountsFallingDueAfterMoreThanOneYear;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.otherliabilitiesandassets.items.CreditorsAmountsFallingDueWithinOneYear;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.otherliabilitiesandassets.items.CurrentNetCurrentAssets;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.otherliabilitiesandassets.items.CurrentTotalAssetsLessCurrentLiabilities;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.otherliabilitiesandassets.items.PrepaymentsAndAccruedIncome;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.otherliabilitiesandassets.items.PreviousNetCurrentAssets;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.otherliabilitiesandassets.items.PreviousTotalAssetsLessCurrentLiabilities;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.otherliabilitiesandassets.items.ProvisionForLiabilities;

import java.util.Objects;

public class OtherLiabilitiesAndAssets {

    @JsonProperty("prepayments_and_accrued_income")
    private PrepaymentsAndAccruedIncome prepaymentsAndAccruedIncome;

    @JsonProperty("creditors_amounts_falling_due_within_one_year")
    private CreditorsAmountsFallingDueWithinOneYear creditorsAmountsFallingDueWithinOneYear;

    @JsonProperty("current_net_current_assets")
    private CurrentNetCurrentAssets currentNetCurrentAssets;

    @JsonProperty("previous_net_current_assets")
    private PreviousNetCurrentAssets previousNetCurrentAssets;

    @JsonProperty("current_total_assets_less_current_liabilities")
    private CurrentTotalAssetsLessCurrentLiabilities currentTotalAssetsLessCurrentLiabilities;

    @JsonProperty("previous_total_assets_less_current_liabilities")
    private PreviousTotalAssetsLessCurrentLiabilities previousTotalAssetsLessCurrentLiabilities;

    @JsonProperty("creditors_amounts_falling_due_after_more_than_one_year")
    private CreditorsAmountsFallingDueAfterMoreThanOneYear creditorsAmountsFallingDueAfterMoreThanOneYear;

    @JsonProperty("provision_for_liabilities")
    private ProvisionForLiabilities provisionForLiabilities;

    @JsonProperty("accruals_and_deferred_income")
    private AccrualsAndDeferredIncome accrualsAndDeferredIncome;

    @JsonProperty("current_total_net_assets")
    private long currentTotalNetAssets;

    @JsonProperty("previous_total_net_assets")
    private long previousTotalNetAssets;

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

    public CurrentNetCurrentAssets getCurrentNetCurrentAssets() {
        return currentNetCurrentAssets;
    }

    public void setCurrentNetCurrentAssets(CurrentNetCurrentAssets currentNetCurrentAssets) {
        this.currentNetCurrentAssets = currentNetCurrentAssets;
    }

    public PreviousNetCurrentAssets getPreviousNetCurrentAssets() {
        return previousNetCurrentAssets;
    }

    public void setPreviousNetCurrentAssets(PreviousNetCurrentAssets previousNetCurrentAssets) {
        this.previousNetCurrentAssets = previousNetCurrentAssets;
    }

    public CurrentTotalAssetsLessCurrentLiabilities getCurrentTotalAssetsLessCurrentLiabilities() {
        return currentTotalAssetsLessCurrentLiabilities;
    }

    public void setCurrentTotalAssetsLessCurrentLiabilities(CurrentTotalAssetsLessCurrentLiabilities currentTotalAssetsLessCurrentLiabilities) {
        this.currentTotalAssetsLessCurrentLiabilities = currentTotalAssetsLessCurrentLiabilities;
    }

    public PreviousTotalAssetsLessCurrentLiabilities getPreviousTotalAssetsLessCurrentLiabilities() {
        return previousTotalAssetsLessCurrentLiabilities;
    }

    public void setPreviousTotalAssetsLessCurrentLiabilities(PreviousTotalAssetsLessCurrentLiabilities previousTotalAssetsLessCurrentLiabilities) {
        this.previousTotalAssetsLessCurrentLiabilities = previousTotalAssetsLessCurrentLiabilities;
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

    public long getCurrentTotalNetAssets() {
        return currentTotalNetAssets;
    }

    public void setCurrentTotalNetAssets(long currentTotalNetAssets) {
        this.currentTotalNetAssets = currentTotalNetAssets;
    }

    public long getPreviousTotalNetAssets() {
        return previousTotalNetAssets;
    }

    public void setPreviousTotalNetAssets(long previousTotalNetAssets) {
        this.previousTotalNetAssets = previousTotalNetAssets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OtherLiabilitiesAndAssets)) return false;
        OtherLiabilitiesAndAssets that = (OtherLiabilitiesAndAssets) o;
        return getCurrentTotalNetAssets() == that.getCurrentTotalNetAssets() &&
                getPreviousTotalNetAssets() == that.getPreviousTotalNetAssets() &&
                Objects.equals(getPrepaymentsAndAccruedIncome(), that.getPrepaymentsAndAccruedIncome()) &&
                Objects.equals(getCreditorsAmountsFallingDueWithinOneYear(), that.getCreditorsAmountsFallingDueWithinOneYear()) &&
                Objects.equals(getCurrentNetCurrentAssets(), that.getCurrentNetCurrentAssets()) &&
                Objects.equals(getPreviousNetCurrentAssets(), that.getPreviousNetCurrentAssets()) &&
                Objects.equals(getCurrentTotalAssetsLessCurrentLiabilities(), that.getCurrentTotalAssetsLessCurrentLiabilities()) &&
                Objects.equals(getPreviousTotalAssetsLessCurrentLiabilities(), that.getPreviousTotalAssetsLessCurrentLiabilities()) &&
                Objects.equals(getCreditorsAmountsFallingDueAfterMoreThanOneYear(), that.getCreditorsAmountsFallingDueAfterMoreThanOneYear()) &&
                Objects.equals(getProvisionForLiabilities(), that.getProvisionForLiabilities()) &&
                Objects.equals(getAccrualsAndDeferredIncome(), that.getAccrualsAndDeferredIncome());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getPrepaymentsAndAccruedIncome(), getCreditorsAmountsFallingDueWithinOneYear(),
                getCurrentNetCurrentAssets(), getPreviousNetCurrentAssets(), getCurrentTotalAssetsLessCurrentLiabilities(),
                getPreviousTotalAssetsLessCurrentLiabilities(), getCreditorsAmountsFallingDueAfterMoreThanOneYear(),
                getProvisionForLiabilities(), getAccrualsAndDeferredIncome(), getCurrentTotalNetAssets(), getPreviousTotalNetAssets());
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
