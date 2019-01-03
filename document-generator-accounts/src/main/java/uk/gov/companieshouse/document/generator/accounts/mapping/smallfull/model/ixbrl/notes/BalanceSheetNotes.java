package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.notes;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
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
        if (!(o instanceof BalanceSheetNotes)) return false;
        BalanceSheetNotes that = (BalanceSheetNotes) o;
        return Objects.equals(getDebtorsNote(), that.getDebtorsNote());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDebtorsNote());
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
