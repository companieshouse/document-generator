package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.notes;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import java.util.Objects;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.creditorsafteroneyear.CreditorsAfterOneYear;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.creditorswithinoneyear.CreditorsWithinOneYear;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.debtors.Debtors;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.fixedassetsinvestments.FixedAssetsInvestments;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.notes.tangible.TangibleAssets;
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

    @JsonProperty("tangible_assets")
    private TangibleAssets tangibleAssets;
    
    @JsonProperty("fixed_assets_investments")
    private FixedAssetsInvestments fixedAssetsInvestments;

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

    public FixedAssetsInvestments getFixedAssetsInvestments() {
        return fixedAssetsInvestments;
    }

    public void setFixedAssetsInvestments(FixedAssetsInvestments fixedAssetsInvestments) {
        this.fixedAssetsInvestments = fixedAssetsInvestments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BalanceSheetNotes)) return false;
        BalanceSheetNotes that = (BalanceSheetNotes) o;
        return (Objects.equals(getStocksNote(), that.getStocksNote()) &&
                Objects.equals(getDebtorsNote(), that.getDebtorsNote()) &&
                Objects.equals(getCreditorsWithinOneYearNote(), that.getCreditorsWithinOneYearNote()) &&
                Objects.equals(getCreditorsAfterOneYearNote(), that.getCreditorsAfterOneYearNote()) &&
                Objects.equals(getTangibleAssets(), that.tangibleAssets) &&
                Objects.equals(getFixedAssetsInvestments(), that.fixedAssetsInvestments));
    }

    @Override
    public int hashCode() {
      return Objects.hash(creditorsWithinOneYearNote, creditorsAfterOneYearNote, stocksNote, 
          debtorsNote, tangibleAssets, fixedAssetsInvestments);
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
