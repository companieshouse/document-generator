package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.currentassets;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.currentassets.items.CashAtBankAndInHand;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.currentassets.items.Debtors;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.currentassets.items.Stocks;

import java.util.Objects;

public class CurrentAssets {

    @JsonProperty("stocks")
    private Stocks stocks;

    @JsonProperty("debtors")
    private Debtors debtors;

    @JsonProperty("cash_at_bank_and_in_hand")
    private CashAtBankAndInHand cashAtBankAndInHand;

    @JsonProperty("current_total")
    private Long currentTotal;

    @JsonProperty("previous_total")
    private Long previousTotal;

    public Stocks getStocks() {
        return stocks;
    }

    public void setStocks(Stocks stocks) {
        this.stocks = stocks;
    }

    public Debtors getDebtors() {
        return debtors;
    }

    public void setDebtors(Debtors debtors) {
        this.debtors = debtors;
    }

    public CashAtBankAndInHand getCashAtBankAndInHand() {
        return cashAtBankAndInHand;
    }

    public void setCashAtBankAndInHand(CashAtBankAndInHand cashAtBankAndInHand) {
        this.cashAtBankAndInHand = cashAtBankAndInHand;
    }

    public Long getCurrentTotal() {
        return currentTotal;
    }

    public void setCurrentTotal(Long currentTotal) {
        this.currentTotal = currentTotal;
    }

    public Long getPreviousTotal() {
        return previousTotal;
    }

    public void setPreviousTotal(Long previousTotal) {
        this.previousTotal = previousTotal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CurrentAssets)) return false;
        CurrentAssets that = (CurrentAssets) o;
        return Objects.equals(getStocks(), that.getStocks()) &&
                Objects.equals(getDebtors(), that.getDebtors()) &&
                Objects.equals(getCashAtBankAndInHand(), that.getCashAtBankAndInHand()) &&
                Objects.equals(getCurrentTotal(), that.getCurrentTotal()) &&
                Objects.equals(getPreviousTotal(), that.getPreviousTotal());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getStocks(), getDebtors(), getCashAtBankAndInHand(), getCurrentTotal(), getPreviousTotal());
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
