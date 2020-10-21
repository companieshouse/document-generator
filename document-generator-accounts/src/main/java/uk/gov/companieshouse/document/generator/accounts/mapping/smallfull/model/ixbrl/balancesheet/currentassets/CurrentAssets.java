package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.currentassets;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.currentassets.items.CashAtBankAndInHand;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.currentassets.items.Debtors;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.currentassets.items.Investments;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.currentassets.items.Stocks;

import java.util.Objects;

@JsonInclude(Include.NON_NULL)
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

    @JsonProperty("investments")
    private Investments investments;

    public Investments getInvestments() {
        return investments;
    }

    public void setInvestments(Investments investments) {
        this.investments = investments;
    }

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
        if (this == o)
            return true;
        if (! (o instanceof CurrentAssets))
            return false;
        CurrentAssets that = (CurrentAssets) o;
        return Objects.equals(getStocks(), that.getStocks()) &&
                Objects.equals(getDebtors(), that.getDebtors()) &&
                Objects.equals(getCashAtBankAndInHand(), that.getCashAtBankAndInHand()) &&
                Objects.equals(getCurrentTotal(), that.getCurrentTotal()) &&
                Objects.equals(getPreviousTotal(), that.getPreviousTotal()) &&
                Objects.equals(getInvestments(), that.getInvestments());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStocks(), getDebtors(), getCashAtBankAndInHand(), getCurrentTotal(), getPreviousTotal(), getInvestments());
    }
}
