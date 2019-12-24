package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.profitandloss.profitorlossbeforetax;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.profitandloss.profitorlossbeforetax.items.InterestPayableAndSimilarCharges;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.profitandloss.profitorlossbeforetax.items.InterestReceivableAndSimilarIncome;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.profitandloss.profitorlossbeforetax.items.TotalProfitOrLossBeforeTax;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfitOrLossBeforeTax {

    @JsonProperty("interest_payable_and_similar_charges")
    private InterestPayableAndSimilarCharges interestPayableAndSimilarCharges;

    @JsonProperty("interest_receivable_and_similar_income")
    private InterestReceivableAndSimilarIncome interestReceivableAndSimilarIncome;

    @JsonProperty("total_profit_or_loss_before_tax")
    private TotalProfitOrLossBeforeTax totalProfitOrLossBeforeTax;

    public InterestPayableAndSimilarCharges getInterestPayableAndSimilarCharges() {
        return interestPayableAndSimilarCharges;
    }

    public void setInterestPayableAndSimilarCharges(InterestPayableAndSimilarCharges interestPayableAndSimilarCharges) {
        this.interestPayableAndSimilarCharges = interestPayableAndSimilarCharges;
    }

    public InterestReceivableAndSimilarIncome getInterestReceivableAndSimilarIncome() {
        return interestReceivableAndSimilarIncome;
    }

    public void setInterestReceivableAndSimilarIncome(InterestReceivableAndSimilarIncome interestReceivableAndSimilarIncome) {
        this.interestReceivableAndSimilarIncome = interestReceivableAndSimilarIncome;
    }

    public TotalProfitOrLossBeforeTax getTotalProfitOrLossBeforeTax() {
        return totalProfitOrLossBeforeTax;
    }

    public void setTotalProfitOrLossBeforeTax(TotalProfitOrLossBeforeTax totalProfitOrLossBeforeTax) {
        this.totalProfitOrLossBeforeTax = totalProfitOrLossBeforeTax;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProfitOrLossBeforeTax)) return false;

        ProfitOrLossBeforeTax that = (ProfitOrLossBeforeTax) o;
        return Objects.equals(getInterestPayableAndSimilarCharges(), that.getInterestPayableAndSimilarCharges()) &&
                Objects.equals(getInterestReceivableAndSimilarIncome(), that.getInterestReceivableAndSimilarIncome()) &&
                Objects.equals(getTotalProfitOrLossBeforeTax(), that.getTotalProfitOrLossBeforeTax());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getInterestPayableAndSimilarCharges(), getInterestReceivableAndSimilarIncome(), getTotalProfitOrLossBeforeTax());
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
