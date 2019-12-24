package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.profitandloss.profitorlossforfinancialyear;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.profitandloss.profitorlossforfinancialyear.items.Tax;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.profitandloss.profitorlossforfinancialyear.items.TotalProfitOrLossForFinancialYear;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfitOrLossForFinancialYear {

    @JsonProperty("tax")
    private Tax tax;

    @JsonProperty("total_profit_or_loss_for_financial_year")
    private TotalProfitOrLossForFinancialYear totalProfitOrLossForFinancialYear;

    public Tax getTax() {
        return tax;
    }

    public void setTax(Tax tax) {
        this.tax = tax;
    }

    public TotalProfitOrLossForFinancialYear getTotalProfitOrLossForFinancialYear() {
        return totalProfitOrLossForFinancialYear;
    }

    public void setTotalProfitOrLossForFinancialYear(TotalProfitOrLossForFinancialYear totalProfitOrLossForFinancialYear) {
        this.totalProfitOrLossForFinancialYear = totalProfitOrLossForFinancialYear;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProfitOrLossForFinancialYear)) return false;

        ProfitOrLossForFinancialYear that = (ProfitOrLossForFinancialYear) o;
        return Objects.equals(getTax(), that.getTax()) &&
                Objects.equals(getTotalProfitOrLossForFinancialYear(), that.getTotalProfitOrLossForFinancialYear());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTax(), getTotalProfitOrLossForFinancialYear());
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
