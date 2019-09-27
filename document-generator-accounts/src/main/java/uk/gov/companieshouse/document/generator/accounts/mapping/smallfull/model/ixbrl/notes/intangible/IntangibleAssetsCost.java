package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.notes.intangible;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class IntangibleAssetsCost {

    @JsonProperty("at_period_start")
    private IntangibleAssetsColumns atPeriodStart;

    @JsonProperty("additions")
    private IntangibleAssetsColumns additions;

    @JsonProperty("disposals")
    private IntangibleAssetsColumns disposals;

    @JsonProperty("revaluations")
    private IntangibleAssetsColumns revaluations;

    @JsonProperty("transfers")
    private IntangibleAssetsColumns transfers;

    @JsonProperty("at_period_end")
    private IntangibleAssetsColumns atPeriodEnd;

    public IntangibleAssetsColumns getAtPeriodStart() {
        return atPeriodStart;
    }

    public void setAtPeriodStart(IntangibleAssetsColumns atPeriodStart) {
        this.atPeriodStart = atPeriodStart;
    }

    public IntangibleAssetsColumns getAdditions() {
        return additions;
    }

    public void setAdditions(IntangibleAssetsColumns additions) {
        this.additions = additions;
    }

    public IntangibleAssetsColumns getDisposals() {
        return disposals;
    }

    public void setDisposals(IntangibleAssetsColumns disposals) {
        this.disposals = disposals;
    }

    public IntangibleAssetsColumns getRevaluations() {
        return revaluations;
    }

    public void setRevaluations(IntangibleAssetsColumns revaluations) {
        this.revaluations = revaluations;
    }

    public IntangibleAssetsColumns getTransfers() {
        return transfers;
    }

    public void setTransfers(IntangibleAssetsColumns transfers) {
        this.transfers = transfers;
    }

    public IntangibleAssetsColumns getAtPeriodEnd() {
        return atPeriodEnd;
    }

    public void setAtPeriodEnd(IntangibleAssetsColumns atPeriodEnd) {
        this.atPeriodEnd = atPeriodEnd;
    }
}
