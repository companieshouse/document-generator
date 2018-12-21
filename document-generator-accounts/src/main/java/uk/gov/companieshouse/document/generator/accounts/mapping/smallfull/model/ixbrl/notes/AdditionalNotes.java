package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.notes;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import java.util.Objects;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.accountingpolicies.AccountingPolicies;

@JsonInclude(Include.NON_NULL)
public class AdditionalNotes {

    @JsonProperty("accounting_policies")
    private AccountingPolicies accountingPolicies;

    public AccountingPolicies getAccountingPolicies() { return accountingPolicies; }

    public void setAccountingPolicies(AccountingPolicies accountingPolicies) {
        this.accountingPolicies = accountingPolicies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AdditionalNotes)) return false;
        AdditionalNotes additionalNotes = (AdditionalNotes) o;
        return Objects.equals(getAccountingPolicies(), additionalNotes.getAccountingPolicies());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getAccountingPolicies());
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
