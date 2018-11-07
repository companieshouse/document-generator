package uk.gov.companieshouse.document.generator.accounts.mapper.model.ixbrl.smallfull.balancesheet.fixedassets.items;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;

import java.util.Objects;

public class TangibleAssets {

    @JsonProperty("current_amount")
    private int currentAmount;

    @JsonProperty("previous_amount")
    private int previousAmount;

    public int getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(int currentAmount) {
        this.currentAmount = currentAmount;
    }

    public int getPreviousAmount() {
        return previousAmount;
    }

    public void setPreviousAmount(int previousAmount) {
        this.previousAmount = previousAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TangibleAssets)) return false;
        TangibleAssets that = (TangibleAssets) o;
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
