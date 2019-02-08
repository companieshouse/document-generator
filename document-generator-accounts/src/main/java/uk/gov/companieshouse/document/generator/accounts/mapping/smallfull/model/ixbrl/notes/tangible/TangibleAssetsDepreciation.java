package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.notes.tangible;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TangibleAssetsDepreciation {

    @JsonProperty("at_period_start")
    private TangibleAssetsColumns atPeriodStart;

    @JsonProperty("charge_for_year")
    private TangibleAssetsColumns chargeForYear;

    @JsonProperty("on_disposals")
    private TangibleAssetsColumns onDisposals;

    @JsonProperty("other_adjustments")
    private TangibleAssetsColumns otherAdjustments;

    @JsonProperty("at_period_end")
    private TangibleAssetsColumns atPeriodEnd;

    public TangibleAssetsColumns getAtPeriodStart() {
        return atPeriodStart;
    }

    public void setAtPeriodStart(TangibleAssetsColumns atPeriodStart) {
        this.atPeriodStart = atPeriodStart;
    }

    public TangibleAssetsColumns getChargeForYear() {
        return chargeForYear;
    }

    public void setChargeForYear(TangibleAssetsColumns chargeForYear) {
        this.chargeForYear = chargeForYear;
    }

    public TangibleAssetsColumns getOnDisposals() {
        return onDisposals;
    }

    public void setOnDisposals(TangibleAssetsColumns onDisposals) {
        this.onDisposals = onDisposals;
    }

    public TangibleAssetsColumns getOtherAdjustments() {
        return otherAdjustments;
    }

    public void setOtherAdjustments(TangibleAssetsColumns otherAdjustments) {
        this.otherAdjustments = otherAdjustments;
    }

    public TangibleAssetsColumns getAtPeriodEnd() {
        return atPeriodEnd;
    }

    public void setAtPeriodEnd(TangibleAssetsColumns atPeriodEnd) {
        this.atPeriodEnd = atPeriodEnd;
    }
}
