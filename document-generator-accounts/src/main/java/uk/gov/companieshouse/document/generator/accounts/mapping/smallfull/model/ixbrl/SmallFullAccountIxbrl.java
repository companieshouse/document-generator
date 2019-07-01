package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.google.gson.Gson;
import uk.gov.companieshouse.document.generator.accounts.mapping.PeriodAwareIxbrl;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.BalanceSheet;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.company.Company;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.notes.AdditionalNotes;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.notes.BalanceSheetNotes;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.period.Period;

import java.util.Objects;

@JsonInclude(Include.NON_NULL)
public class SmallFullAccountIxbrl implements PeriodAwareIxbrl {

    @JsonProperty("period")
    private Period period;

    @JsonProperty("balance_sheet")
    private BalanceSheet balanceSheet;

    @JsonProperty("additional_notes")
    private AdditionalNotes additionalNotes;

    @JsonProperty("balance_sheet_notes")
    private BalanceSheetNotes balanceSheetNotes;

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

    public AdditionalNotes getAdditionalNotes() {
        return additionalNotes;
    }

    public void setAdditionalNotes(AdditionalNotes additionalNotes) {
        this.additionalNotes = additionalNotes;
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

    public BalanceSheetNotes getBalanceSheetNotes() { return balanceSheetNotes;  }

    public void setBalanceSheetNotes(BalanceSheetNotes balanceSheetNotes) {
        this.balanceSheetNotes = balanceSheetNotes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SmallFullAccountIxbrl)) return false;
        SmallFullAccountIxbrl smallFullAccountIxbrl = (SmallFullAccountIxbrl) o;
        return Objects.equals(getPeriod(), smallFullAccountIxbrl.getPeriod()) &&
                Objects.equals(getBalanceSheet(), smallFullAccountIxbrl.getBalanceSheet()) &&
                Objects.equals(getAdditionalNotes(), smallFullAccountIxbrl.getAdditionalNotes()) &&
                Objects.equals(getCompany(), smallFullAccountIxbrl.getCompany()) &&
                Objects.equals(getApprovalDate(), smallFullAccountIxbrl.getApprovalDate()) &&
                Objects.equals(getApprovalName(), smallFullAccountIxbrl.getApprovalName()) &&
                Objects.equals(getBalanceSheetNotes(), smallFullAccountIxbrl.getBalanceSheetNotes());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getPeriod(), getBalanceSheet(), getAdditionalNotes(), getCompany(), getApprovalDate(),
                getApprovalName(), getBalanceSheetNotes());
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
