package uk.gov.companieshouse.document.generator.accounts.mapper.smallfull.model.ixbrl.balancesheet.currentassets.items;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;

import java.util.Objects;

public class Debtors {

    @JsonProperty("current_amount")
    private Long currentAmount;

    @JsonProperty("previous_amount")
    private Long previousAmount;

    public Long getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(Long currentAmount) {
        this.currentAmount = currentAmount;
    }

    public Long getPreviousAmount() {
        return previousAmount;
    }

    public void setPreviousAmount(Long previousAmount) {
        this.previousAmount = previousAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Debtors)) return false;
        Debtors debtors = (Debtors) o;
        return Objects.equals(getCurrentAmount(), debtors.getCurrentAmount()) &&
                Objects.equals(getPreviousAmount(), debtors.getPreviousAmount());
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
