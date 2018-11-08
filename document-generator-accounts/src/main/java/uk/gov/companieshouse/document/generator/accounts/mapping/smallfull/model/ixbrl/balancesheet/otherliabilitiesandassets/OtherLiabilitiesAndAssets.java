package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.otherliabilitiesandassets;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.otherliabilitiesandassets.items.AccrualsAndDeferredIncome;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.otherliabilitiesandassets.items.CreditorsAfterMoreThanOneYear;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.otherliabilitiesandassets.items.CreditorsDueWithinOneYear;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.otherliabilitiesandassets.items.CurrentNetCurrentAssets;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.otherliabilitiesandassets.items.CurrentTotalAssetsLessCurrentLiabilities;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.otherliabilitiesandassets.items.PrepaymentsAndAccruedIncome;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.otherliabilitiesandassets.items.PreviousTotalAssetsLessCurrentLiabilities;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.otherliabilitiesandassets.items.ProvisionsForLiabilities;

import java.util.Objects;

public class OtherLiabilitiesAndAssets {

    @JsonProperty("prepayments_and_accrued_income")
    private PrepaymentsAndAccruedIncome prepaymentsAndAccruedIncome;

    @JsonProperty("creditors_due_within_one_year")
    private CreditorsDueWithinOneYear creditorsDueWithinOneYear;

    @JsonProperty("current_net_current_assets")
    private CurrentNetCurrentAssets currentNetCurrentAssets;

    @JsonProperty("current_total_assets_less_current_liabilities")
    private CurrentTotalAssetsLessCurrentLiabilities currentTotalAssetsLessCurrentLiabilities;

    @JsonProperty("previous_total_assets_less_current_liabilities")
    private PreviousTotalAssetsLessCurrentLiabilities previousTotalAssetsLessCurrentLiabilities;

    @JsonProperty("creditors_after_more_than_one_year")
    private CreditorsAfterMoreThanOneYear creditorsAfterMoreThanOneYear;

    @JsonProperty("provisions_for_liabilities")
    private ProvisionsForLiabilities provisionsForLiabilities;

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

    public CreditorsDueWithinOneYear getCreditorsDueWithinOneYear() {
        return creditorsDueWithinOneYear;
    }

    public void setCreditorsDueWithinOneYear(CreditorsDueWithinOneYear creditorsDueWithinOneYear) {
        this.creditorsDueWithinOneYear = creditorsDueWithinOneYear;
    }

    public CurrentNetCurrentAssets getCurrentNetCurrentAssets() {
        return currentNetCurrentAssets;
    }

    public void setCurrentNetCurrentAssets(CurrentNetCurrentAssets currentNetCurrentAssets) {
        this.currentNetCurrentAssets = currentNetCurrentAssets;
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

    public CreditorsAfterMoreThanOneYear getCreditorsAfterMoreThanOneYear() {
        return creditorsAfterMoreThanOneYear;
    }

    public void setCreditorsAfterMoreThanOneYear(CreditorsAfterMoreThanOneYear creditorsAfterMoreThanOneYear) {
        this.creditorsAfterMoreThanOneYear = creditorsAfterMoreThanOneYear;
    }

    public ProvisionsForLiabilities getProvisionsForLiabilities() {
        return provisionsForLiabilities;
    }

    public void setProvisionsForLiabilities(ProvisionsForLiabilities provisionsForLiabilities) {
        this.provisionsForLiabilities = provisionsForLiabilities;
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
                Objects.equals(getCreditorsDueWithinOneYear(), that.getCreditorsDueWithinOneYear()) &&
                Objects.equals(getCurrentNetCurrentAssets(), that.getCurrentNetCurrentAssets()) &&
                Objects.equals(getCurrentTotalAssetsLessCurrentLiabilities(), that.getCurrentTotalAssetsLessCurrentLiabilities()) &&
                Objects.equals(getPreviousTotalAssetsLessCurrentLiabilities(), that.getPreviousTotalAssetsLessCurrentLiabilities()) &&
                Objects.equals(getCreditorsAfterMoreThanOneYear(), that.getCreditorsAfterMoreThanOneYear()) &&
                Objects.equals(getProvisionsForLiabilities(), that.getProvisionsForLiabilities()) &&
                Objects.equals(getAccrualsAndDeferredIncome(), that.getAccrualsAndDeferredIncome());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getPrepaymentsAndAccruedIncome(), getCreditorsDueWithinOneYear(),
                getCurrentNetCurrentAssets(), getCurrentTotalAssetsLessCurrentLiabilities(),
                getPreviousTotalAssetsLessCurrentLiabilities(), getCreditorsAfterMoreThanOneYear(),
                getProvisionsForLiabilities(), getAccrualsAndDeferredIncome(), getCurrentTotalNetAssets(),
                getPreviousTotalNetAssets());
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
