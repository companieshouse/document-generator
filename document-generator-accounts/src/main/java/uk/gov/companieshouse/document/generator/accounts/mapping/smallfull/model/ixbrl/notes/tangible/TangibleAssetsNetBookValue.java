package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.notes.tangible;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TangibleAssetsNetBookValue {

    @JsonProperty("current_period")
    private TangibleAssetsColumns currentPeriod;

    @JsonProperty("previous_period")
    private TangibleAssetsColumns previousPeriod;

    public TangibleAssetsColumns getCurrentPeriod() {
        return currentPeriod;
    }

    public void setCurrentPeriod(TangibleAssetsColumns currentPeriod) {
        this.currentPeriod = currentPeriod;
    }

    public TangibleAssetsColumns getPreviousPeriod() {
        return previousPeriod;
    }

    public void setPreviousPeriod(TangibleAssetsColumns previousPeriod) {
        this.previousPeriod = previousPeriod;
    }
}
