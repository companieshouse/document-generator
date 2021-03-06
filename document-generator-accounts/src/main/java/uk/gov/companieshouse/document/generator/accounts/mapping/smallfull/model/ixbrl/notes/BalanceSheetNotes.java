package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.notes;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.FinancialCommitments;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.creditorsafteroneyear.CreditorsAfterOneYear;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.creditorswithinoneyear.CreditorsWithinOneYear;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.currentassetsinvestments.CurrentAssetsInvestments;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.debtors.Debtors;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.fixedassetsinvestments.FixedAssetsInvestments;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.loanstodirectors.LoansToDirectors;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.notes.intangible.IntangibleAssets;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.notes.tangible.TangibleAssets;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.offbalancesheetarrangements.OffBalanceSheetArrangements;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.stocks.StocksNote;

@JsonInclude(Include.NON_NULL)
public class BalanceSheetNotes {

    @JsonProperty("stocks")
    private StocksNote stocksNote;

    @JsonProperty("debtors")
    private Debtors debtorsNote;

    @JsonProperty("creditors_within_one_year")
    private CreditorsWithinOneYear creditorsWithinOneYearNote;

    @JsonProperty("creditors_after_one_year")
    private CreditorsAfterOneYear creditorsAfterOneYearNote;

    @JsonProperty("intangible_assets")
    private IntangibleAssets intangibleAssets;

    @JsonProperty("tangible_assets")
    private TangibleAssets tangibleAssets;

    @JsonProperty("fixed_assets_investments")
    private FixedAssetsInvestments fixedAssetsInvestments;

    @JsonProperty("current_assets_investments")
    private CurrentAssetsInvestments currentAssetsInvestments;

    @JsonProperty("off_balance_sheet_arrangements")
    private OffBalanceSheetArrangements offBalanceSheetArrangements;

    @JsonProperty("financial_commitments")
    private FinancialCommitments financialCommitments;

    @JsonProperty("loans_to_directors")
    private LoansToDirectors loansToDirectors;

    public LoansToDirectors getLoansToDirectors() {
        return loansToDirectors;
    }

    public void setLoansToDirectors(
            LoansToDirectors loansToDirectors) {
        this.loansToDirectors = loansToDirectors;
    }

    public IntangibleAssets getIntangibleAssets() {
        return intangibleAssets;
    }

    public void setIntangibleAssets(IntangibleAssets intangibleAssets) {
        this.intangibleAssets = intangibleAssets;
    }

    public StocksNote getStocksNote() {
        return stocksNote;
    }

    public void setStocksNote(StocksNote stocksNote) {
        this.stocksNote = stocksNote;
    }

    public Debtors getDebtorsNote() {
        return debtorsNote;
    }

    public void setDebtorsNote(Debtors debtorsNote) {
        this.debtorsNote = debtorsNote;
    }

    public CreditorsWithinOneYear getCreditorsWithinOneYearNote() {
        return creditorsWithinOneYearNote;
    }

    public void setCreditorsWithinOneYearNote(CreditorsWithinOneYear creditorsWithinOneYearNote) {
        this.creditorsWithinOneYearNote = creditorsWithinOneYearNote;
    }

    public CreditorsAfterOneYear getCreditorsAfterOneYearNote() {
        return creditorsAfterOneYearNote;
    }

    public void setCreditorsAfterOneYearNote(CreditorsAfterOneYear creditorsAfterOneYearNote) {
        this.creditorsAfterOneYearNote = creditorsAfterOneYearNote;
    }

    public TangibleAssets getTangibleAssets() {
        return tangibleAssets;
    }

    public void setTangibleAssets(TangibleAssets tangibleAssets) {
        this.tangibleAssets = tangibleAssets;
    }

    public CurrentAssetsInvestments getCurrentAssetsInvestments() {
        return currentAssetsInvestments;
    }

    public void setCurrentAssetsInvestments(CurrentAssetsInvestments currentAssetsInvestments) {
        this.currentAssetsInvestments = currentAssetsInvestments;
    }

    public FixedAssetsInvestments getFixedAssetsInvestments() {
        return fixedAssetsInvestments;
    }

    public void setFixedAssetsInvestments(FixedAssetsInvestments fixedAssetsInvestments) {
        this.fixedAssetsInvestments = fixedAssetsInvestments;
    }

    public OffBalanceSheetArrangements getOffBalanceSheetArrangements() {
        return offBalanceSheetArrangements;
    }

    public void setOffBalanceSheetArrangements(
            OffBalanceSheetArrangements offBalanceSheetArrangements) {
        this.offBalanceSheetArrangements = offBalanceSheetArrangements;
    }

    public FinancialCommitments getFinancialCommitments() {
        return financialCommitments;
    }

    public void setFinancialCommitments(FinancialCommitments financialCommitments) {
        this.financialCommitments = financialCommitments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BalanceSheetNotes that = (BalanceSheetNotes) o;
        return Objects.equals(stocksNote, that.stocksNote) &&
                Objects.equals(debtorsNote, that.debtorsNote) &&
                Objects.equals(creditorsWithinOneYearNote, that.creditorsWithinOneYearNote) &&
                Objects.equals(creditorsAfterOneYearNote, that.creditorsAfterOneYearNote) &&
                Objects.equals(intangibleAssets, that.intangibleAssets) &&
                Objects.equals(tangibleAssets, that.tangibleAssets) &&
                Objects.equals(fixedAssetsInvestments, that.fixedAssetsInvestments) &&
                Objects.equals(currentAssetsInvestments, that.currentAssetsInvestments) &&
                Objects.equals(offBalanceSheetArrangements, that.offBalanceSheetArrangements) &&
                Objects.equals(financialCommitments, that.financialCommitments) &&
                Objects.equals(loansToDirectors, that.loansToDirectors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stocksNote, debtorsNote, creditorsWithinOneYearNote, creditorsAfterOneYearNote, intangibleAssets, tangibleAssets, fixedAssetsInvestments, currentAssetsInvestments, offBalanceSheetArrangements, financialCommitments, loansToDirectors);
    }

    @Override
    public String toString() {
        return "BalanceSheetNotes{" +
                "stocksNote=" + stocksNote +
                ", debtorsNote=" + debtorsNote +
                ", creditorsWithinOneYearNote=" + creditorsWithinOneYearNote +
                ", creditorsAfterOneYearNote=" + creditorsAfterOneYearNote +
                ", intangibleAssets=" + intangibleAssets +
                ", tangibleAssets=" + tangibleAssets +
                ", fixedAssetsInvestments=" + fixedAssetsInvestments +
                ", currentAssetsInvestments=" + currentAssetsInvestments +
                ", offBalanceSheetArrangements=" + offBalanceSheetArrangements +
                ", financialCommitments=" + financialCommitments +
                ", loansToDirectors=" + loansToDirectors +
                '}';
    }
}



