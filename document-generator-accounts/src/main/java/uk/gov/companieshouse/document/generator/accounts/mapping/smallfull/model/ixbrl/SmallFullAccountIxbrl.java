package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.google.gson.Gson;
import uk.gov.companieshouse.document.generator.accounts.mapping.PeriodAwareIxbrl;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.BalanceSheet;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.company.Company;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.notes.Notes;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.period.Period;

import java.util.Objects;

@JsonInclude(Include.NON_NULL)
@JsonTypeName("small_full_accounts")
@JsonTypeInfo(include = As.WRAPPER_OBJECT, use = Id.NAME)
public class SmallFullAccountIxbrl implements PeriodAwareIxbrl {

    @JsonProperty("period")
    private Period period;

    @JsonProperty("balance_sheet")
    private BalanceSheet balanceSheet;

    @JsonProperty("notes")
    private Notes notes;

    @JsonProperty("company")
    private Company company;

    @JsonProperty("approval_date")
    private String approvalDate;

    @JsonProperty("approval_name")
    private String approvalName;

    @Override
    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public BalanceSheet getBalanceSheet() {
        return balanceSheet;
    }

    public void setBalanceSheet(BalanceSheet balanceSheet) {
        this.balanceSheet = balanceSheet;
    }

    public Notes getNotes() {
        return notes;
    }

    public void setNotes(Notes notes) {
        this.notes = notes;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(String approvalDate) {
        this.approvalDate = approvalDate;
    }

    public String getApprovalName() {
        return approvalName;
    }

    public void setApprovalName(String approvalName) {
        this.approvalName = approvalName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SmallFullAccountIxbrl)) return false;
        SmallFullAccountIxbrl smallFullAccountIxbrl = (SmallFullAccountIxbrl) o;
        return Objects.equals(getPeriod(), smallFullAccountIxbrl.getPeriod()) &&
                Objects.equals(getBalanceSheet(), smallFullAccountIxbrl.getBalanceSheet()) &&
                Objects.equals(getNotes(), smallFullAccountIxbrl.getNotes()) &&
                Objects.equals(getCompany(), smallFullAccountIxbrl.getCompany()) &&
                Objects.equals(getApprovalDate(), smallFullAccountIxbrl.getApprovalDate()) &&
                Objects.equals(getApprovalName(), smallFullAccountIxbrl.getApprovalName());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getPeriod(), getBalanceSheet(), getNotes(), getCompany(), getApprovalDate(),
                getApprovalName());
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
