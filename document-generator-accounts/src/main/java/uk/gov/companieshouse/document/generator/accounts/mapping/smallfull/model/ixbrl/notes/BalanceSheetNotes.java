package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.notes;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.debtors.Debtors;


@JsonInclude(Include.NON_NULL)
public class BalanceSheetNotes {

    @JsonProperty("debtors")
    private Debtors debtorsNote;

    public Debtors getDebtorsNote() {
        return debtorsNote;
    }

    public void setDebtorsNote(Debtors debtorsNote) {
        this.debtorsNote = debtorsNote;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BalanceSheetNotes that = (BalanceSheetNotes) o;
        return Objects.equals(debtorsNote, that.debtorsNote);
    }

    @Override
    public int hashCode() {
        return Objects.hash(debtorsNote);
    }

    @Override
    public String toString() {
        return "BalanceSheetNotes{" + "debtorsNote=" + debtorsNote + '}';
    }
}
