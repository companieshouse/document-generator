package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model;

import uk.gov.companieshouse.api.model.accounts.CompanyAccountsApi;
import uk.gov.companieshouse.api.model.accounts.directorsreport.DirectorApi;
import uk.gov.companieshouse.api.model.accounts.directorsreport.DirectorsReportApi;
import uk.gov.companieshouse.api.model.accounts.directorsreport.SecretaryApi;
import uk.gov.companieshouse.api.model.accounts.directorsreport.StatementsApi;
import uk.gov.companieshouse.api.model.accounts.profitandloss.ProfitAndLossApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.SmallFullApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.intangible.IntangibleApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.AccountingPoliciesApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.ApprovalApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.BalanceSheetStatementsApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.CurrentPeriodApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.Debtors.DebtorsApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.creditorsafteroneyear.CreditorsAfterOneYearApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.creditorswithinoneyear.CreditorsWithinOneYearApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.currentassetsinvestments.CurrentAssetsInvestmentsApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.fixedassetsinvestments.FixedAssetsInvestmentsApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.loanstodirectors.AdditionalInformationApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.loanstodirectors.LoanApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.loanstodirectors.LoansToDirectorsApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.offBalanceSheet.OffBalanceSheetApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.stocks.StocksApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.PreviousPeriodApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.employees.EmployeesApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.tangible.TangibleApi;
import uk.gov.companieshouse.api.model.company.CompanyProfileApi;

import java.util.List;
import java.util.Objects;

public class SmallFullApiData {

    private CurrentPeriodApi currentPeriod;

    private PreviousPeriodApi previousPeriod;

    private CompanyProfileApi companyProfile;

    private SmallFullApi smallFull;

    private ApprovalApi approval;

    private BalanceSheetStatementsApi balanceSheetStatements;

    private AccountingPoliciesApi accountingPolicies;

    private TangibleApi tangibleAssets;

    private IntangibleApi intangibleAssets;

    private StocksApi stocks;

    private DebtorsApi debtors;

    private EmployeesApi employees;

    private CurrentAssetsInvestmentsApi currentAssetsInvestments;
    
    private CreditorsWithinOneYearApi creditorsWithinOneYear;
    
    private CreditorsAfterOneYearApi creditorsAfterOneYear;
    
    private FixedAssetsInvestmentsApi fixedAssetsInvestments;

    private ProfitAndLossApi currentPeriodProfitAndLoss;

    private ProfitAndLossApi previousPeriodProfitAndLoss;

    private DirectorsReportApi directorsReport;

    private StatementsApi directorsReportStatements;

    private DirectorApi[] directors;

    private SecretaryApi secretary;

    private OffBalanceSheetApi offBalanceSheet;

    private LoansToDirectorsApi loansToDirectors;

    private LoanApi[] loans;

    private AdditionalInformationApi loansAdditionalInfo;

    public LoansToDirectorsApi getLoansToDirectors() {
        return loansToDirectors;
    }

    public void setLoansToDirectors(
            LoansToDirectorsApi loansToDirectors) {
        this.loansToDirectors = loansToDirectors;
    }

    public LoanApi[] getLoans() {
        return loans;
    }

    public void setLoans(LoanApi[] loans) {
        this.loans = loans;
    }

    public AdditionalInformationApi getLoansAdditionalInfo() {
        return loansAdditionalInfo;
    }

    public void setLoansAdditionalInfo(
            AdditionalInformationApi loansAdditionalInfo) {
        this.loansAdditionalInfo = loansAdditionalInfo;
    }

    public OffBalanceSheetApi getOffBalanceSheet() {
        return offBalanceSheet;
    }

    public void setOffBalanceSheet(OffBalanceSheetApi offBalanceSheet) {
        this.offBalanceSheet = offBalanceSheet;
    }

    public SecretaryApi getSecretary() {
        return secretary;
    }

    public void setSecretary(SecretaryApi secretary) {
        this.secretary = secretary;
    }

    public uk.gov.companieshouse.api.model.accounts.directorsreport.ApprovalApi getDirectorsApproval() {
        return directorsApproval;
    }

    public void setDirectorsApproval(uk.gov.companieshouse.api.model.accounts.directorsreport.ApprovalApi directorsApproval) {
        this.directorsApproval = directorsApproval;
    }

    private uk.gov.companieshouse.api.model.accounts.directorsreport.ApprovalApi directorsApproval;

    public DirectorApi[] getDirectors() {
        return directors;
    }

    public void setDirectors(DirectorApi[] directors) {
        this.directors = directors;
    }

    public StatementsApi getDirectorsReportStatements() {
        return directorsReportStatements;
    }

    public void setDirectorsReportStatements(StatementsApi directorsReportStatements) {
        this.directorsReportStatements = directorsReportStatements;
    }

    public DirectorsReportApi getDirectorsReport() {
        return directorsReport;
    }

    public void setDirectorsReport(DirectorsReportApi directorsReport) {
        this.directorsReport = directorsReport;
    }

    public CurrentPeriodApi getCurrentPeriod() {
        return currentPeriod;
    }

    public void setCurrentPeriod(CurrentPeriodApi currentPeriod) {
        this.currentPeriod = currentPeriod;
    }

    public PreviousPeriodApi getPreviousPeriod() {
        return previousPeriod;
    }

    public void setPreviousPeriod(PreviousPeriodApi previousPeriod) {
        this.previousPeriod = previousPeriod;
    }

    public CompanyProfileApi getCompanyProfile() {
        return companyProfile;
    }

    public void setCompanyProfile(CompanyProfileApi companyProfile) {
        this.companyProfile = companyProfile;
    }

    public SmallFullApi getSmallFull() {
        return smallFull;
    }

    public void setSmallFull(SmallFullApi smallFull) {
        this.smallFull = smallFull;
    }

    public ApprovalApi getApproval() {
        return approval;
    }

    public void setApproval(ApprovalApi approval) {
        this.approval = approval;
    }

    public BalanceSheetStatementsApi getBalanceSheetStatements() { return balanceSheetStatements; }

    public void setBalanceSheetStatements(BalanceSheetStatementsApi balanceSheetStatements) {
        this.balanceSheetStatements = balanceSheetStatements;
    }

    public AccountingPoliciesApi getAccountingPolicies() { return accountingPolicies; }

    public void setAccountingPolicies(AccountingPoliciesApi accountingPolicies) {
        this.accountingPolicies = accountingPolicies;
    }

    public TangibleApi getTangibleAssets() {
        return tangibleAssets;
    }

    public void setTangibleAssets(TangibleApi tangibleAssets) {
        this.tangibleAssets = tangibleAssets;
    }

    public IntangibleApi getIntangibleAssets() {
        return intangibleAssets;
    }

    public void setIntangibleAssets(IntangibleApi intangibleAssets) {
        this.intangibleAssets = intangibleAssets;
    }
    
    public StocksApi getStocks() {
        return stocks;
    }

    public void setStocks(StocksApi stocks) {
        this.stocks = stocks;
    }
    
    public DebtorsApi getDebtors () {
        return debtors;
    }

    public void setDebtors (DebtorsApi debtors) {
        this.debtors = debtors;
    }
    
    public CreditorsWithinOneYearApi getCreditorsWithinOneYear() {
      return creditorsWithinOneYear;
    }

    public void setCreditorsWithinOneYear(CreditorsWithinOneYearApi creditorsWithinOneYear) {
      this.creditorsWithinOneYear = creditorsWithinOneYear;
    }

    public EmployeesApi getEmployees() {
        return employees;
    }

    public void setEmployees(EmployeesApi employees) {
        this.employees = employees;
    }

    public CurrentAssetsInvestmentsApi getCurrentAssetsInvestments() {
        return currentAssetsInvestments;
    }

    public void setCurrentAssetsInvestments(CurrentAssetsInvestmentsApi currentAssetsInvestments) {
        this.currentAssetsInvestments = currentAssetsInvestments;
    }

    public CreditorsAfterOneYearApi getCreditorsAfterOneYear() {
      return creditorsAfterOneYear;
    }

    public void setCreditorsAfterOneYear(CreditorsAfterOneYearApi creditorsAfterOneYear) {
      this.creditorsAfterOneYear = creditorsAfterOneYear;
    }

    public FixedAssetsInvestmentsApi getFixedAssetsInvestments() {
        return fixedAssetsInvestments;
    }

    public void setFixedAssetsInvestments(FixedAssetsInvestmentsApi fixedAssetsInvestments) {
        this.fixedAssetsInvestments = fixedAssetsInvestments;
    }
    public ProfitAndLossApi getCurrentPeriodProfitAndLoss() {
        return currentPeriodProfitAndLoss;
    }

    public void setCurrentPeriodProfitAndLoss(ProfitAndLossApi currentPeriodProfitAndLoss) {
        this.currentPeriodProfitAndLoss = currentPeriodProfitAndLoss;
    }

    public ProfitAndLossApi getPreviousPeriodProfitAndLoss() {
        return previousPeriodProfitAndLoss;
    }

    public void setPreviousPeriodProfitAndLoss(ProfitAndLossApi previousPeriodProfitAndLoss) {
        this.previousPeriodProfitAndLoss = previousPeriodProfitAndLoss;
    }

    @Override
    public String toString() {
        return "SmallFullApiData{" +
                "currentPeriod=" + currentPeriod +
                ", previousPeriod=" + previousPeriod +
                ", companyProfile=" + companyProfile +
                ", smallFull=" + smallFull +
                ", approval=" + approval +
                ", balanceSheetStatements=" + balanceSheetStatements +
                ", accountingPolicies=" + accountingPolicies +
                ", tangibleAssets=" + tangibleAssets +
                ", intangibleAssets=" + intangibleAssets +
                ", stocks=" + stocks +
                ", debtors=" + debtors +
                ", employees=" + employees +
                ", currentAssetsInvestments=" + currentAssetsInvestments +
                ", creditorsWithinOneYear=" + creditorsWithinOneYear +
                ", creditorsAfterOneYear=" + creditorsAfterOneYear +
                ", fixedAssetsInvestments=" + fixedAssetsInvestments +
                ", currentPeriodProfitAndLoss=" + currentPeriodProfitAndLoss +
                ", previousPeriodProfitAndLoss=" + previousPeriodProfitAndLoss +
                ", directorsReportApi=" + directorsReport +
                ", directorsReportStatements=" + directorsReportStatements +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SmallFullApiData that = (SmallFullApiData) o;
        return Objects.equals(currentPeriod, that.currentPeriod) &&
                Objects.equals(previousPeriod, that.previousPeriod) &&
                Objects.equals(companyProfile, that.companyProfile) &&
                Objects.equals(smallFull, that.smallFull) &&
                Objects.equals(approval, that.approval) &&
                Objects.equals(balanceSheetStatements, that.balanceSheetStatements) &&
                Objects.equals(accountingPolicies, that.accountingPolicies) &&
                Objects.equals(tangibleAssets, that.tangibleAssets) &&
                Objects.equals(intangibleAssets, that.intangibleAssets) &&
                Objects.equals(stocks, that.stocks) &&
                Objects.equals(debtors, that.debtors) &&
                Objects.equals(employees, that.employees) &&
                Objects.equals(currentAssetsInvestments, that.currentAssetsInvestments) &&
                Objects.equals(creditorsWithinOneYear, that.creditorsWithinOneYear) &&
                Objects.equals(creditorsAfterOneYear, that.creditorsAfterOneYear) &&
                Objects.equals(fixedAssetsInvestments, that.fixedAssetsInvestments) &&
                Objects.equals(currentPeriodProfitAndLoss, that.currentPeriodProfitAndLoss) &&
                Objects.equals(previousPeriodProfitAndLoss, that.previousPeriodProfitAndLoss) &&
                Objects.equals(directorsReport, that.directorsReport) &&
                Objects.equals(directorsReportStatements, that.directorsReportStatements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentPeriod, previousPeriod, companyProfile, smallFull, approval, balanceSheetStatements, accountingPolicies, tangibleAssets, intangibleAssets, stocks, debtors, employees, currentAssetsInvestments, creditorsWithinOneYear, creditorsAfterOneYear, fixedAssetsInvestments, currentPeriodProfitAndLoss, previousPeriodProfitAndLoss, directorsReport, directorsReportStatements);
    }

}
