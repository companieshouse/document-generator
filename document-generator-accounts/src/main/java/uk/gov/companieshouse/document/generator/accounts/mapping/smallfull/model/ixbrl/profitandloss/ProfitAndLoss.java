package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.profitandloss;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.profitandloss.grossprofitorloss.GrossProfitOrLoss;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.profitandloss.operatingprofitorloss.OperatingProfitOrLoss;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.profitandloss.profitorlossbeforetax.ProfitOrLossBeforeTax;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.profitandloss.profitorlossforfinancialyear.ProfitOrLossForFinancialYear;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfitAndLoss {

    @JsonProperty("gross_profit_or_loss")
    private GrossProfitOrLoss grossProfitOrLoss;

    @JsonProperty("operating_profit_or_loss")
    private OperatingProfitOrLoss operatingProfitOrLoss;

    @JsonProperty("profit_or_loss_before_tax")
    private ProfitOrLossBeforeTax profitOrLossBeforeTax;

    @JsonProperty("profit_or_loss_for_financial_year")
    private ProfitOrLossForFinancialYear profitOrLossForFinancialYear;

    public GrossProfitOrLoss getGrossProfitOrLoss() {
        return grossProfitOrLoss;
    }

    public void setGrossProfitOrLoss(GrossProfitOrLoss grossProfitOrLoss) {
        this.grossProfitOrLoss = grossProfitOrLoss;
    }

    public OperatingProfitOrLoss getOperatingProfitOrLoss() {
        return operatingProfitOrLoss;
    }

    public void setOperatingProfitOrLoss(OperatingProfitOrLoss operatingProfitOrLoss) {
        this.operatingProfitOrLoss = operatingProfitOrLoss;
    }

    public ProfitOrLossBeforeTax getProfitOrLossBeforeTax() {
        return profitOrLossBeforeTax;
    }

    public void setProfitOrLossBeforeTax(ProfitOrLossBeforeTax profitOrLossBeforeTax) {
        this.profitOrLossBeforeTax = profitOrLossBeforeTax;
    }

    public ProfitOrLossForFinancialYear getProfitOrLossForFinancialYear() {
        return profitOrLossForFinancialYear;
    }

    public void setProfitOrLossForFinancialYear(ProfitOrLossForFinancialYear profitOrLossForFinancialYear) {
        this.profitOrLossForFinancialYear = profitOrLossForFinancialYear;
    }
}
