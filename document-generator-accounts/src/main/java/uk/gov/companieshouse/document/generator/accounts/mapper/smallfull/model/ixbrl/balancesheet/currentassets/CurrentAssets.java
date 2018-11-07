package uk.gov.companieshouse.document.generator.accounts.mapper.smallfull.model.ixbrl.balancesheet.currentassets;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import uk.gov.companieshouse.document.generator.accounts.mapper.smallfull.model.ixbrl.balancesheet.currentassets.items.CashAtBankInHand;
import uk.gov.companieshouse.document.generator.accounts.mapper.smallfull.model.ixbrl.balancesheet.currentassets.items.Debtors;
import uk.gov.companieshouse.document.generator.accounts.mapper.smallfull.model.ixbrl.balancesheet.currentassets.items.Stocks;

import java.util.Objects;

public class CurrentAssets {

    @JsonProperty("stocks")
    private Stocks stocks;

    @JsonProperty("debtors")
    private Debtors debtors;

    @JsonProperty("cash_at_bank_in_hand")
    private CashAtBankInHand cashAtBankInHand;

    @JsonProperty("current_total_current_assets")
    private Long currentTotalCurrentAssets;

    @JsonProperty("previous_total_current_assets")
    private Long previousTotalCurrentAssets;

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

    public CashAtBankInHand getCashAtBankInHand() {
        return cashAtBankInHand;
    }

    public void setCashAtBankInHand(CashAtBankInHand cashAtBankInHand) {
        this.cashAtBankInHand = cashAtBankInHand;
    }

    public Long getCurrentTotalCurrentAssets() {
        return currentTotalCurrentAssets;
    }

    public void setCurrentTotalCurrentAssets(Long currentTotalCurrentAssets) {
        this.currentTotalCurrentAssets = currentTotalCurrentAssets;
    }

    public Long getPreviousTotalCurrentAssets() {
        return previousTotalCurrentAssets;
    }

    public void setPreviousTotalCurrentAssets(Long previousTotalCurrentAssets) {
        this.previousTotalCurrentAssets = previousTotalCurrentAssets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CurrentAssets)) return false;
        CurrentAssets that = (CurrentAssets) o;
        return Objects.equals(getStocks(), that.getStocks()) &&
                Objects.equals(getDebtors(), that.getDebtors()) &&
                Objects.equals(getCashAtBankInHand(), that.getCashAtBankInHand()) &&
                Objects.equals(getCurrentTotalCurrentAssets(), that.getCurrentTotalCurrentAssets()) &&
                Objects.equals(getPreviousTotalCurrentAssets(), that.getPreviousTotalCurrentAssets());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getStocks(), getDebtors(), getCashAtBankInHand(), getCurrentTotalCurrentAssets(),
                getPreviousTotalCurrentAssets());
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
