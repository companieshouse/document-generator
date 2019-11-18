package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.profitandloss.grossprofitorloss;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.profitandloss.grossprofitorloss.items.CostOfSales;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.profitandloss.grossprofitorloss.items.GrossTotal;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.profitandloss.grossprofitorloss.items.Turnover;

import java.util.Objects;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class GrossProfitOrLoss {

    @JsonProperty("cost_of_sales")
    private CostOfSales costOfSales;

    @JsonProperty("gross_total")
    private GrossTotal grossTotal;

    @JsonProperty("turnover")
    private Turnover turnover;

    public CostOfSales getCostOfSales() {
        return costOfSales;
    }

    public void setCostOfSales(CostOfSales costOfSales) {
        this.costOfSales = costOfSales;
    }

    public GrossTotal getGrossTotal() {
        return grossTotal;
    }

    public void setGrossTotal(GrossTotal grossTotal) {
        this.grossTotal = grossTotal;
    }

    public Turnover getTurnover() {
        return turnover;
    }

    public void setTurnover(Turnover turnover) {
        this.turnover = turnover;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GrossProfitOrLoss)) return false;

        GrossProfitOrLoss that = (GrossProfitOrLoss) o;
        return Objects.equals(getCostOfSales(),that.getCostOfSales()) &&
                Objects.equals(getGrossTotal(),that.getGrossTotal()) &&
                        Objects.equals(getTurnover(),that.getTurnover());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCostOfSales(), getGrossTotal(), getTurnover());
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
