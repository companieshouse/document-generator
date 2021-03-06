package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.notes;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.accountingpolicies.AccountingPolicies;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.employees.Employees;

@JsonInclude(Include.NON_NULL)
public class AdditionalNotes {

    @JsonProperty("accounting_policies")
    private AccountingPolicies accountingPolicies;

    @JsonProperty("employees")
    private Employees employees;

    public AccountingPolicies getAccountingPolicies() {
        return accountingPolicies;
    }

    public void setAccountingPolicies(AccountingPolicies accountingPolicies) {
        this.accountingPolicies = accountingPolicies;
    }

    public Employees getEmployees() {
        return employees;
    }

    public void setEmployees(Employees employees) {
        this.employees = employees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (! (o instanceof AdditionalNotes))
            return false;
        AdditionalNotes that = (AdditionalNotes) o;
        return Objects.equals(getAccountingPolicies(), that.getAccountingPolicies()) &&
                Objects.equals(getEmployees(), that.getEmployees());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAccountingPolicies(), getEmployees());
    }

    @Override
    public String toString() {
        return "AdditionalNotes{" +
                "accountingPolicies=" + accountingPolicies +
                ", employees=" + employees +
                '}';
    }
}

