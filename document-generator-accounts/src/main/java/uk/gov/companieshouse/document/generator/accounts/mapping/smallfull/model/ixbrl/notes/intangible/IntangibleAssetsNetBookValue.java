package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.notes.intangible;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class IntangibleAssetsNetBookValue {

    @JsonProperty("current_period")
    private IntangibleAssetsColumns currentPeriod;

    @JsonProperty("previous_period")
    private IntangibleAssetsColumns previousPeriod;

    public IntangibleAssetsColumns getCurrentPeriod() {
        return currentPeriod;
    }

    public void setCurrentPeriod(IntangibleAssetsColumns currentPeriod) {
        this.currentPeriod = currentPeriod;
    }

    public IntangibleAssetsColumns getPreviousPeriod() {
        return previousPeriod;
    }

    public void setPreviousPeriod(IntangibleAssetsColumns previousPeriod) {
        this.previousPeriod = previousPeriod;
    }
}
