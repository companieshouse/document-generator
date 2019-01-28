package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.stocks;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import java.util.Objects;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.debtors.items.Total;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.stocks.items.PaymentsOnAccount;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.stocks.items.Stocks;

@JsonInclude(Include.NON_NULL)
public class StocksNote {

    @JsonProperty("stocks")
    private Stocks stocks;

    @JsonProperty("payments_on_account")
    private PaymentsOnAccount paymentsOnAccount;

    public Stocks getStocks() {
        return stocks;
    }

    public void setStocks(Stocks stocks) {
        this.stocks = stocks;
    }

    public PaymentsOnAccount getPaymentsOnAccount() {
        return paymentsOnAccount;
    }

    public void setPaymentsOnAccount(PaymentsOnAccount paymentsOnAccount) {
        this.paymentsOnAccount = paymentsOnAccount;
    }
    
    @JsonProperty("total")
    private Total total;


    public Total getTotal() {
        return total;
    }

    public void setTotal(Total total) {
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (! (o instanceof StocksNote))
            return false;
        StocksNote stocks = (StocksNote) o;
        return Objects.equals(getStocks(), stocks.getStocks()) &&
                Objects.equals(getPaymentsOnAccount(), stocks.getPaymentsOnAccount()) &&
                Objects.equals(getTotal(), stocks.getTotal());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStocks(), getPaymentsOnAccount(), getTotal());
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
