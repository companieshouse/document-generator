package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.capitalandreserves.items;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;

import java.util.Objects;

public class OtherReserves {

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
        if (!(o instanceof OtherReserves)) return false;
        OtherReserves that = (OtherReserves) o;
        return Objects.equals(getCurrentAmount(), that.getCurrentAmount()) &&
                Objects.equals(getPreviousAmount(), that.getPreviousAmount());
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
