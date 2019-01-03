package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.debtors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import java.util.Objects;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.debtors.items.GreaterThanOneYear;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.debtors.items.OtherDebtors;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.debtors.items.PrepaymentsAndAccruedIncome;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.debtors.items.Total;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.debtors.items.TradeDebtors;

public class Debtors {

    @JsonProperty("details")
    private String Details;

    @JsonProperty("greater_than_one_year")
    private GreaterThanOneYear greaterThanOneYear;

    @JsonProperty("other_debtors")
    private OtherDebtors otherDebtors;

    @JsonProperty("prepayments_and_accrued_income")
    private PrepaymentsAndAccruedIncome prepaymentsAndAccruedIncome;

    @JsonProperty("trade_debtors")
    private TradeDebtors tradeDebtors;

    @JsonProperty("total")
    private Total total;

    public String getDetails() {
        return Details;
    }

    public void setDetails(String details) {
        Details = details;
    }

    public GreaterThanOneYear getGreaterThanOneYear() {
        return greaterThanOneYear;
    }

    public void setGreaterThanOneYear(GreaterThanOneYear greaterThanOneYear) {
        this.greaterThanOneYear = greaterThanOneYear;
    }

    public OtherDebtors getOtherDebtors() {
        return otherDebtors;
    }

    public void setOtherDebtors(OtherDebtors otherDebtors) {
        this.otherDebtors = otherDebtors;
    }

    public PrepaymentsAndAccruedIncome getPrepaymentsAndAccruedIncome() {
        return prepaymentsAndAccruedIncome;
    }

    public void setPrepaymentsAndAccruedIncome(
        PrepaymentsAndAccruedIncome prepaymentsAndAccruedIncome) {
        this.prepaymentsAndAccruedIncome = prepaymentsAndAccruedIncome;
    }

    public TradeDebtors getTradeDebtors() {
        return tradeDebtors;
    }

    public void setTradeDebtors(TradeDebtors tradeDebtors) {
        this.tradeDebtors = tradeDebtors;
    }

    public Total getTotal() {
        return total;
    }

    public void setTotal(Total total) {
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Debtors)) return false;
        
        Debtors debtors = (Debtors) o;
        return Objects.equals(Details, debtors.Details) &&
            Objects.equals(greaterThanOneYear, debtors.greaterThanOneYear) &&
            Objects.equals(otherDebtors, debtors.otherDebtors) &&
            Objects.equals(prepaymentsAndAccruedIncome, debtors.prepaymentsAndAccruedIncome) &&
            Objects.equals(tradeDebtors, debtors.tradeDebtors) &&
            Objects.equals(total, debtors.total);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Details, greaterThanOneYear, otherDebtors, prepaymentsAndAccruedIncome,
            tradeDebtors, total);
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
