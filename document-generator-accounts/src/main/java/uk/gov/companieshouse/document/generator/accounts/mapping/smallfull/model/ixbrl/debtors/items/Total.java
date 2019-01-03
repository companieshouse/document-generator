package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.debtors.items;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import java.util.Objects;

public class Total {

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
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Total total = (Total) o;
        return Objects.equals(currentAmount, total.currentAmount) &&
            Objects.equals(previousAmount, total.previousAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentAmount, previousAmount);
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
