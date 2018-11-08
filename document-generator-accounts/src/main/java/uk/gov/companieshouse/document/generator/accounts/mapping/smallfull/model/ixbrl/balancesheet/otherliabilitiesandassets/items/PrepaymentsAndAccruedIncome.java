package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.otherliabilitiesandassets.items;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;

import java.util.Objects;

public class PrepaymentsAndAccruedIncome {

    @JsonProperty("current_amount")
    private long currentAmount;

    @JsonProperty("previous_amount")
    private long previousAmount;

    public long getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(int currentAmount) {
        this.currentAmount = currentAmount;
    }

    public long getPreviousAmount() {
        return previousAmount;
    }

    public void setPreviousAmount(int previousAmount) {
        this.previousAmount = previousAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccrualsAndDeferredIncome)) return false;
        AccrualsAndDeferredIncome that = (AccrualsAndDeferredIncome) o;
        return getCurrentAmount() == that.getCurrentAmount() &&
                getPreviousAmount() == that.getPreviousAmount();
    }

    @Override
    public int hashCode() {

        return Objects.hash(getCurrentAmount(), getPreviousAmount());
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
