package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.capitalandreserves;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.capitalandreserves.items.CalledUpShareCapital;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.capitalandreserves.items.OtherReserves;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.capitalandreserves.items.ProfitAndLoss;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.capitalandreserves.items.SharePremiumAccount;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.capitalandreserves.items.TotalShareHoldersFund;

import java.util.Objects;

public class CapitalAndReserve {

    @JsonProperty("called_up_share_capital")
    private CalledUpShareCapital calledUpShareCapital;

    @JsonProperty("other_reserves")
    private OtherReserves otherReserves;

    @JsonProperty("profit_and_loss")
    private ProfitAndLoss profitAndLoss;

    @JsonProperty("share_premium_account")
    private SharePremiumAccount sharePremiumAccount;

    @JsonProperty("total_share_holders_fund")
    private TotalShareHoldersFund totalShareHoldersFund;

    @JsonProperty("current_total_capital_and_reserve")
    private long currentTotalCapitalAndReserve;

    @JsonProperty("previous_total_capital_and_reserve")
    private long previousTotalCapitalAndReserve;

    public CalledUpShareCapital getCalledUpShareCapital() {
        return calledUpShareCapital;
    }

    public void setCalledUpShareCapital(CalledUpShareCapital calledUpShareCapital) {
        this.calledUpShareCapital = calledUpShareCapital;
    }

    public OtherReserves getOtherReserves() {
        return otherReserves;
    }

    public void setOtherReserves(OtherReserves otherReserves) {
        this.otherReserves = otherReserves;
    }

    public ProfitAndLoss getProfitAndLoss() {
        return profitAndLoss;
    }

    public void setProfitAndLoss(ProfitAndLoss profitAndLoss) {
        this.profitAndLoss = profitAndLoss;
    }

    public SharePremiumAccount getSharePremiumAccount() {
        return sharePremiumAccount;
    }

    public void setSharePremiumAccount(SharePremiumAccount sharePremiumAccount) {
        this.sharePremiumAccount = sharePremiumAccount;
    }

    public TotalShareHoldersFund getTotalShareHoldersFund() {
        return totalShareHoldersFund;
    }

    public void setTotalShareHoldersFund(TotalShareHoldersFund totalShareHoldersFund) {
        this.totalShareHoldersFund = totalShareHoldersFund;
    }

    public long getCurrentTotalCapitalAndReserve() {
        return currentTotalCapitalAndReserve;
    }

    public void setCurrentTotalCapitalAndReserve(long currentTotalCapitalAndReserve) {
        this.currentTotalCapitalAndReserve = currentTotalCapitalAndReserve;
    }

    public long getPreviousTotalCapitalAndReserve() {
        return previousTotalCapitalAndReserve;
    }

    public void setPreviousTotalCapitalAndReserve(long previousTotalCapitalAndReserve) {
        this.previousTotalCapitalAndReserve = previousTotalCapitalAndReserve;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CapitalAndReserve)) return false;
        CapitalAndReserve that = (CapitalAndReserve) o;
        return getCurrentTotalCapitalAndReserve() == that.getCurrentTotalCapitalAndReserve() &&
                getPreviousTotalCapitalAndReserve() == that.getPreviousTotalCapitalAndReserve() &&
                Objects.equals(getCalledUpShareCapital(), that.getCalledUpShareCapital()) &&
                Objects.equals(getOtherReserves(), that.getOtherReserves()) &&
                Objects.equals(getProfitAndLoss(), that.getProfitAndLoss()) &&
                Objects.equals(getSharePremiumAccount(), that.getSharePremiumAccount()) &&
                Objects.equals(getTotalShareHoldersFund(), that.getTotalShareHoldersFund());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getCalledUpShareCapital(), getOtherReserves(), getProfitAndLoss(), getSharePremiumAccount(),
                getTotalShareHoldersFund(), getCurrentTotalCapitalAndReserve(), getPreviousTotalCapitalAndReserve());
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
