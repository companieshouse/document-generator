package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.notes;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import java.util.Objects;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.creditorswithinoneyear.CreditorsWithinOneYear;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.debtors.Debtors;


@JsonInclude(Include.NON_NULL)
public class BalanceSheetNotes {

    @JsonProperty("debtors")
    private Debtors debtorsNote;
    
    @JsonProperty("creditors-within-one-year")
    private CreditorsWithinOneYear creditorsWithinOneYearNote;

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

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (!(obj instanceof BalanceSheetNotes))
        return false;
      BalanceSheetNotes other = (BalanceSheetNotes) obj;
      return Objects.equals(creditorsWithinOneYearNote, other.creditorsWithinOneYearNote)
          && Objects.equals(debtorsNote, other.debtorsNote);
    }

    @Override
    public int hashCode() {
      return Objects.hash(creditorsWithinOneYearNote, debtorsNote);
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
