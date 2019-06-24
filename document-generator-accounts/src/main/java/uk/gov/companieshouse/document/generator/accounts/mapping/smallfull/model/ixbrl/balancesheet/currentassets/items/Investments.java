package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.currentassets.items;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

@JsonInclude(Include.NON_NULL)
public class Investments {

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
    public String toString() {
        return "Investments{" +
                "currentAmount=" + currentAmount +
                ", previousAmount=" + previousAmount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (! (o instanceof Investments))
            return false;
        Investments that = (Investments) o;
        return Objects.equals(getCurrentAmount(), that.getCurrentAmount()) &&
                Objects.equals(previousAmount, that.previousAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCurrentAmount(), previousAmount);
    }
}
