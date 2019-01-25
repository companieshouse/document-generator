package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.notes;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import java.util.Objects;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.creditorswithinoneyear.CreditorsWithinOneYear;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.debtors.Debtors;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.notes.tangible.TangibleAssets;


@JsonInclude(Include.NON_NULL)
public class BalanceSheetNotes {

    @JsonProperty("debtors")
    private Debtors debtorsNote;
    
    @JsonProperty("creditors_within_one_year")
    private CreditorsWithinOneYear creditorsWithinOneYearNote;

    @JsonProperty("tangible_assets")
    private TangibleAssets tangibleAssets;

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

    public TangibleAssets getTangibleAssets() {
        return tangibleAssets;
    }

    public void setTangibleAssets(TangibleAssets tangibleAssets) {
        this.tangibleAssets = tangibleAssets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BalanceSheetNotes)) return false;
        BalanceSheetNotes that = (BalanceSheetNotes) o;
        return (Objects.equals(getDebtorsNote(), that.getDebtorsNote()) &&
                Objects.equals(getCreditorsWithinOneYearNote(), that.getCreditorsWithinOneYearNote()) &&
                Objects.equals(getTangibleAssets(), that.tangibleAssets));
    }

    @Override
    public int hashCode() {
      return Objects.hash(creditorsWithinOneYearNote, debtorsNote, tangibleAssets);
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
