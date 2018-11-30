package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.capitalandreserves.CapitalAndReserve;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.currentassets.CurrentAssets;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.fixedassets.FixedAssets;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.otherliabilitiesandassets.OtherLiabilitiesOrAssets;

import java.util.Objects;

@JsonInclude(Include.NON_NULL)
public class BalanceSheet {

    @JsonProperty("called_up_share_capital_not_paid")
    private CalledUpSharedCapitalNotPaid calledUpSharedCapitalNotPaid;

    @JsonProperty("fixed_assets")
    private FixedAssets fixedAssets;

    @JsonProperty("current_assets")
    private CurrentAssets currentAssets;

    @JsonProperty("other_liabilities_or_assets")
    private OtherLiabilitiesOrAssets otherLiabilitiesOrAssets;

    @JsonProperty("capital_and_reserve")
    private CapitalAndReserve capitalAndReserve;

    public CalledUpSharedCapitalNotPaid getCalledUpSharedCapitalNotPaid() {
        return calledUpSharedCapitalNotPaid;
    }

    public void setCalledUpSharedCapitalNotPaid(CalledUpSharedCapitalNotPaid calledUpSharedCapitalNotPaid) {
        this.calledUpSharedCapitalNotPaid = calledUpSharedCapitalNotPaid;
    }

    public FixedAssets getFixedAssets() {
        return fixedAssets;
    }

    public void setFixedAssets(FixedAssets fixedAssets) {
        this.fixedAssets = fixedAssets;
    }

    public CurrentAssets getCurrentAssets() {
        return currentAssets;
    }

    public void setCurrentAssets(CurrentAssets currentAssets) {
        this.currentAssets = currentAssets;
    }

    public OtherLiabilitiesOrAssets getOtherLiabilitiesOrAssets() {
        return otherLiabilitiesOrAssets;
    }

    public void setOtherLiabilitiesOrAssets(OtherLiabilitiesOrAssets otherLiabilitiesOrAssets) {
        this.otherLiabilitiesOrAssets = otherLiabilitiesOrAssets;
    }

    public CapitalAndReserve getCapitalAndReserve() {
        return capitalAndReserve;
    }

    public void setCapitalAndReserve(CapitalAndReserve capitalAndReserve) {
        this.capitalAndReserve = capitalAndReserve;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BalanceSheet)) return false;
        BalanceSheet that = (BalanceSheet) o;
        return Objects.equals(getCalledUpSharedCapitalNotPaid(), that.getCalledUpSharedCapitalNotPaid()) &&
                Objects.equals(getFixedAssets(), that.getFixedAssets()) &&
                Objects.equals(getCurrentAssets(), that.getCurrentAssets()) &&
                Objects.equals(getOtherLiabilitiesOrAssets(), that.getOtherLiabilitiesOrAssets()) &&
                Objects.equals(getCapitalAndReserve(), that.getCapitalAndReserve());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getCalledUpSharedCapitalNotPaid(), getFixedAssets(), getCurrentAssets(), getOtherLiabilitiesOrAssets(), getCapitalAndReserve());
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
