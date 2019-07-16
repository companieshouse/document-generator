package uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.foreigncompanydetails.items;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.foreigncompanydetails.items.accountsItems.AccountPeriodFrom;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.foreigncompanydetails.items.accountsItems.AccountPeriodTo;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.foreigncompanydetails.items.accountsItems.MustFileWithin;

@JsonInclude(Include.NON_NULL)
public class Accounts {

    @JsonProperty("account_period_from")
    private AccountPeriodFrom accountPeriodFrom;

    @JsonProperty("account_period_to")
    private AccountPeriodTo accountPeriodTo;

    @JsonProperty("must_file_within")
    private MustFileWithin mustFileWithin;

    public AccountPeriodFrom getAccountPeriodFrom() {
        return accountPeriodFrom;
    }

    public void setAccountPeriodFrom(AccountPeriodFrom accountPeriodFrom) {
        this.accountPeriodFrom = accountPeriodFrom;
    }

    public AccountPeriodTo getAccountPeriodTo() {
        return accountPeriodTo;
    }

    public void setAccountPeriodTo(AccountPeriodTo accountPeriodTo) {
        this.accountPeriodTo = accountPeriodTo;
    }

    public MustFileWithin getMustFileWithin() {
        return mustFileWithin;
    }

    public void setMustFileWithin(MustFileWithin mustFileWithin) {
        this.mustFileWithin = mustFileWithin;
    }
}
