package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import uk.gov.companieshouse.document.generator.accounts.mapping.PeriodAwareIxbrl;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.BalanceSheet;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.company.Company;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.directorsreport.DirectorsReport;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.directorsreport.DirectorsReportStatements;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.notes.AdditionalNotes;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.notes.BalanceSheetNotes;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.period.Period;

import java.util.Objects;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.profitandloss.ProfitAndLoss;

@JsonInclude(Include.NON_NULL)
public class SmallFullAccountIxbrl implements PeriodAwareIxbrl {

    @JsonProperty("profit_and_loss")
    private ProfitAndLoss profitAndLoss;

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

    @JsonProperty("approval_index")
    private int approvalIndex = 1;

    @JsonProperty("directors_report")
    private DirectorsReport directorsReport;

    public int getApprovalIndex() {
        return approvalIndex;
    }

    public void setApprovalIndex(int approvalIndex) {
        this.approvalIndex = approvalIndex;
    }

    public DirectorsReport getDirectorsReport() {
        return directorsReport;
    }

    public void setDirectorsReport(DirectorsReport directorsReport) {
        this.directorsReport = directorsReport;
    }

    public ProfitAndLoss getProfitAndLoss() {
        return profitAndLoss;
    }

    public void setProfitAndLoss(ProfitAndLoss profitAndLoss) {
        this.profitAndLoss = profitAndLoss;
    }

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
        if (o == null || getClass() != o.getClass()) return false;
        SmallFullAccountIxbrl that = (SmallFullAccountIxbrl) o;
        return Objects.equals(profitAndLoss, that.profitAndLoss) &&
                Objects.equals(period, that.period) &&
                Objects.equals(balanceSheet, that.balanceSheet) &&
                Objects.equals(additionalNotes, that.additionalNotes) &&
                Objects.equals(balanceSheetNotes, that.balanceSheetNotes) &&
                Objects.equals(company, that.company) &&
                Objects.equals(approvalDate, that.approvalDate) &&
                Objects.equals(approvalName, that.approvalName) &&
                Objects.equals(directorsReport, that.directorsReport);
    }

    @Override
    public int hashCode() {
        return Objects.hash(profitAndLoss, period, balanceSheet, additionalNotes, balanceSheetNotes, company, approvalDate, approvalName, directorsReport);
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

}
