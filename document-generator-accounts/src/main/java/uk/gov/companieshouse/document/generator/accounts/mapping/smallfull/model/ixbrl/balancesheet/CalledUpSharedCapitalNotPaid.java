package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;

import java.util.Objects;

public class CalledUpSharedCapitalNotPaid {

    @JsonProperty("current_amount")
    private long currentAmount;

    @JsonProperty("previous_amount")
    private long previousAmount;

    public long getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(long currentAmount) {
        this.currentAmount = currentAmount;
    }

    public long getPreviousAmount() {
        return previousAmount;
    }

    public void setPreviousAmount(long previousAmount) {
        this.previousAmount = previousAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CalledUpSharedCapitalNotPaid)) return false;
        CalledUpSharedCapitalNotPaid that = (CalledUpSharedCapitalNotPaid) o;
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
