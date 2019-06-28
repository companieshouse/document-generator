package uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.keyfilingdates;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.keyfilingdates.items.AccountingReferenceDate;

@JsonInclude(Include.NON_NULL)
public class KeyFilingDates {

    @JsonProperty("accounting_reference_date")
    private AccountingReferenceDate accountingReferenceDate;

    @JsonProperty("last_accounts_made_up_to")
    private String lastAccountsMadeUpTo;

    @JsonProperty("accounts_type")
    private String accountsType;

    @JsonProperty("next_accounts_due")
    private String nextAccountsDue;

    @JsonProperty("last_confirmation_statement")
    private String lastConfirmationStatement;

    @JsonProperty("next_confirmation_statement")
    private String nextConfirmationStatement;

    @JsonProperty("last_members_list")
    private String lastMembersList;

    public AccountingReferenceDate getAccountingReferenceDate() {
        return accountingReferenceDate;
    }

    public void setAccountingReferenceDate(AccountingReferenceDate accountingReferenceDate) {
        this.accountingReferenceDate = accountingReferenceDate;
    }

    public String getLastAccountsMadeUpTo() {
        return lastAccountsMadeUpTo;
    }

    public void setLastAccountsMadeUpTo(String lastAccountsMadeUpTo) {
        this.lastAccountsMadeUpTo = lastAccountsMadeUpTo;
    }

    public String getAccountsType() {
        return accountsType;
    }

    public void setAccountsType(String accountsType) {
        this.accountsType = accountsType;
    }

    public String getNextAccountsDue() {
        return nextAccountsDue;
    }

    public void setNextAccountsDue(String nextAccountsDue) {
        this.nextAccountsDue = nextAccountsDue;
    }

    public String getLastConfirmationStatement() {
        return lastConfirmationStatement;
    }

    public void setLastConfirmationStatement(String lastConfirmationStatement) {
        this.lastConfirmationStatement = lastConfirmationStatement;
    }

    public String getNextConfirmationStatement() {
        return nextConfirmationStatement;
    }

    public void setNextConfirmationStatement(String nextConfirmationStatement) {
        this.nextConfirmationStatement = nextConfirmationStatement;
    }

    public String getLastMembersList() {
        return lastMembersList;
    }

    public void setLastMembersList(String lastMembersList) {
        this.lastMembersList = lastMembersList;
    }
}
