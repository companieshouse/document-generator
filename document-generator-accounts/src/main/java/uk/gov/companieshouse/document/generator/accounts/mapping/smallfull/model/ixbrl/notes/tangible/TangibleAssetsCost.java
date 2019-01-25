package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.notes.tangible;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TangibleAssetsCost {

    @JsonProperty("at_period_start")
    private TangibleAssetsColumns atPeriodStart;

    @JsonProperty("additions")
    private TangibleAssetsColumns additions;

    @JsonProperty("disposals")
    private TangibleAssetsColumns disposals;

    @JsonProperty("revaluations")
    private TangibleAssetsColumns revaluations;

    @JsonProperty("transfers")
    private TangibleAssetsColumns transfers;

    @JsonProperty("at_period_end")
    private TangibleAssetsColumns atPeriodEnd;

    public TangibleAssetsColumns getAtPeriodStart() {
        return atPeriodStart;
    }

    public void setAtPeriodStart(TangibleAssetsColumns atPeriodStart) {
        this.atPeriodStart = atPeriodStart;
    }

    public TangibleAssetsColumns getAdditions() {
        return additions;
    }

    public void setAdditions(TangibleAssetsColumns additions) {
        this.additions = additions;
    }

    public TangibleAssetsColumns getDisposals() {
        return disposals;
    }

    public void setDisposals(TangibleAssetsColumns disposals) {
        this.disposals = disposals;
    }

    public TangibleAssetsColumns getRevaluations() {
        return revaluations;
    }

    public void setRevaluations(TangibleAssetsColumns revaluations) {
        this.revaluations = revaluations;
    }

    public TangibleAssetsColumns getTransfers() {
        return transfers;
    }

    public void setTransfers(TangibleAssetsColumns transfers) {
        this.transfers = transfers;
    }

    public TangibleAssetsColumns getAtPeriodEnd() {
        return atPeriodEnd;
    }

    public void setAtPeriodEnd(TangibleAssetsColumns atPeriodEnd) {
        this.atPeriodEnd = atPeriodEnd;
    }
}
