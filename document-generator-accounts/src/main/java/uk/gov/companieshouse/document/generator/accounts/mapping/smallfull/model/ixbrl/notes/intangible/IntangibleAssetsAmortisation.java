package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.notes.intangible;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class IntangibleAssetsAmortisation {

    @JsonProperty("at_period_start")
    private IntangibleAssetsColumns atPeriodStart;

    @JsonProperty("charge_for_year")
    private IntangibleAssetsColumns chargeForYear;

    @JsonProperty("on_disposals")
    private IntangibleAssetsColumns onDisposals;

    @JsonProperty("other_adjustments")
    private IntangibleAssetsColumns otherAdjustments;

    @JsonProperty("at_period_end")
    private IntangibleAssetsColumns atPeriodEnd;

    public IntangibleAssetsColumns getAtPeriodStart() {
        return atPeriodStart;
    }

    public void setAtPeriodStart(IntangibleAssetsColumns atPeriodStart) {
        this.atPeriodStart = atPeriodStart;
    }

    public IntangibleAssetsColumns getChargeForYear() {
        return chargeForYear;
    }

    public void setChargeForYear(IntangibleAssetsColumns chargeForYear) {
        this.chargeForYear = chargeForYear;
    }

    public IntangibleAssetsColumns getOnDisposals() {
        return onDisposals;
    }

    public void setOnDisposals(IntangibleAssetsColumns onDisposals) {
        this.onDisposals = onDisposals;
    }

    public IntangibleAssetsColumns getOtherAdjustments() {
        return otherAdjustments;
    }

    public void setOtherAdjustments(IntangibleAssetsColumns otherAdjustments) {
        this.otherAdjustments = otherAdjustments;
    }

    public IntangibleAssetsColumns getAtPeriodEnd() {
        return atPeriodEnd;
    }

    public void setAtPeriodEnd(IntangibleAssetsColumns atPeriodEnd) {
        this.atPeriodEnd = atPeriodEnd;
    }
}
