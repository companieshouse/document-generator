package uk.gov.companieshouse.document.generator.accounts.mapper.model.ixbrl.smallfull.balancesheet.otherliabilitiesandassets;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import uk.gov.companieshouse.document.generator.accounts.mapper.model.ixbrl.smallfull.balancesheet.otherliabilitiesandassets.items.AccrualsAndDeferredIncome;
import uk.gov.companieshouse.document.generator.accounts.mapper.model.ixbrl.smallfull.balancesheet.otherliabilitiesandassets.items.CreditorsAfterMoreThanOneYear;
import uk.gov.companieshouse.document.generator.accounts.mapper.model.ixbrl.smallfull.balancesheet.otherliabilitiesandassets.items.CreditorsDueWithinOneYear;
import uk.gov.companieshouse.document.generator.accounts.mapper.model.ixbrl.smallfull.balancesheet.otherliabilitiesandassets.items.NetCurrentAssetsLiabilities;
import uk.gov.companieshouse.document.generator.accounts.mapper.model.ixbrl.smallfull.balancesheet.otherliabilitiesandassets.items.PrepaymentsAndAccruedIncome;
import uk.gov.companieshouse.document.generator.accounts.mapper.model.ixbrl.smallfull.balancesheet.otherliabilitiesandassets.items.ProvisionsForLiabilities;
import uk.gov.companieshouse.document.generator.accounts.mapper.model.ixbrl.smallfull.balancesheet.otherliabilitiesandassets.items.TotalAssetsLessCurrentLiabilites;
import uk.gov.companieshouse.document.generator.accounts.mapper.model.ixbrl.smallfull.balancesheet.otherliabilitiesandassets.items.TotalNetAssets;

import java.util.Objects;

public class OtherLiabilitiesAndAssets {

    @JsonProperty("prepayment_and_accrued_income")
    private PrepaymentsAndAccruedIncome prepaymentsAndAccruedIncome;

    @JsonProperty("creditors_due_within_one_year")
    private CreditorsDueWithinOneYear creditorsDueWithinOneYear;

    @JsonProperty("net_current_assets_liabilities")
    private NetCurrentAssetsLiabilities netCurrentAssetsLiabilities;

    @JsonProperty("total_assets_less_current_liabilities")
    private TotalAssetsLessCurrentLiabilites totalAssetsLessCurrentLiabilites;

    @JsonProperty("creditors_after_more_than_one_year")
    private CreditorsAfterMoreThanOneYear creditorsAfterMoreThanOneYear;

    @JsonProperty("provisions_for_liabilities")
    private ProvisionsForLiabilities provisionsForLiabilities;

    @JsonProperty("accruals_and_deferred_income")
    private AccrualsAndDeferredIncome accrualsAndDeferredIncome;

    @JsonProperty("total_net_assets")
    private TotalNetAssets totalNetAssets;

    @JsonProperty("current_total_other_liabilities_and_assets")
    private long currentTotalOtherLiabilitiesAndAssets;

    @JsonProperty("previous_total_other_liabilities_and_assets")
    private long previousTotalOtherLiabilitiesAndAssets;

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

    public NetCurrentAssetsLiabilities getNetCurrentAssetsLiabilities() {
        return netCurrentAssetsLiabilities;
    }

    public void setNetCurrentAssetsLiabilities(NetCurrentAssetsLiabilities netCurrentAssetsLiabilities) {
        this.netCurrentAssetsLiabilities = netCurrentAssetsLiabilities;
    }

    public TotalAssetsLessCurrentLiabilites getTotalAssetsLessCurrentLiabilites() {
        return totalAssetsLessCurrentLiabilites;
    }

    public void setTotalAssetsLessCurrentLiabilites(TotalAssetsLessCurrentLiabilites totalAssetsLessCurrentLiabilites) {
        this.totalAssetsLessCurrentLiabilites = totalAssetsLessCurrentLiabilites;
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

    public TotalNetAssets getTotalNetAssets() {
        return totalNetAssets;
    }

    public void setTotalNetAssets(TotalNetAssets totalNetAssets) {
        this.totalNetAssets = totalNetAssets;
    }

    public long getCurrentTotalOtherLiabilitiesAndAssets() {
        return currentTotalOtherLiabilitiesAndAssets;
    }

    public void setCurrentTotalOtherLiabilitiesAndAssets(long currentTotalOtherLiabilitiesAndAssets) {
        this.currentTotalOtherLiabilitiesAndAssets = currentTotalOtherLiabilitiesAndAssets;
    }

    public long getPreviousTotalOtherLiabilitiesAndAssets() {
        return previousTotalOtherLiabilitiesAndAssets;
    }

    public void setPreviousTotalOtherLiabilitiesAndAssets(long previousTotalOtherLiabilitiesAndAssets) {
        this.previousTotalOtherLiabilitiesAndAssets = previousTotalOtherLiabilitiesAndAssets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OtherLiabilitiesAndAssets)) return false;
        OtherLiabilitiesAndAssets that = (OtherLiabilitiesAndAssets) o;
        return getCurrentTotalOtherLiabilitiesAndAssets() == that.getCurrentTotalOtherLiabilitiesAndAssets() &&
                getPreviousTotalOtherLiabilitiesAndAssets() == that.getPreviousTotalOtherLiabilitiesAndAssets() &&
                Objects.equals(getPrepaymentsAndAccruedIncome(), that.getPrepaymentsAndAccruedIncome()) &&
                Objects.equals(getCreditorsDueWithinOneYear(), that.getCreditorsDueWithinOneYear()) &&
                Objects.equals(getNetCurrentAssetsLiabilities(), that.getNetCurrentAssetsLiabilities()) &&
                Objects.equals(getTotalAssetsLessCurrentLiabilites(), that.getTotalAssetsLessCurrentLiabilites()) &&
                Objects.equals(getCreditorsAfterMoreThanOneYear(), that.getCreditorsAfterMoreThanOneYear()) &&
                Objects.equals(getProvisionsForLiabilities(), that.getProvisionsForLiabilities()) &&
                Objects.equals(getAccrualsAndDeferredIncome(), that.getAccrualsAndDeferredIncome()) &&
                Objects.equals(getTotalNetAssets(), that.getTotalNetAssets());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getPrepaymentsAndAccruedIncome(), getCreditorsDueWithinOneYear(),
                getNetCurrentAssetsLiabilities(), getTotalAssetsLessCurrentLiabilites(),
                getCreditorsAfterMoreThanOneYear(), getProvisionsForLiabilities(), getAccrualsAndDeferredIncome(),
                getTotalNetAssets(), getCurrentTotalOtherLiabilitiesAndAssets(), getPreviousTotalOtherLiabilitiesAndAssets());
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
