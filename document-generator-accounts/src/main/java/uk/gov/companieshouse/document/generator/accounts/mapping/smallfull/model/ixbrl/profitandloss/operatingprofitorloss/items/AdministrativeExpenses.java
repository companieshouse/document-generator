package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.profitandloss.operatingprofitorloss.items;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.profitandloss.grossprofitorloss.items.CostOfSales;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdministrativeExpenses {

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
        if (!(o instanceof AdministrativeExpenses)) return false;

        AdministrativeExpenses that = (AdministrativeExpenses) o;
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
