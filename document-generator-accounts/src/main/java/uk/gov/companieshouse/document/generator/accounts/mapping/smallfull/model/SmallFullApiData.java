package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model;

import uk.gov.companieshouse.api.model.accounts.CompanyAccountsApi;
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
import uk.gov.companieshouse.api.model.accounts.smallfull.stocks.StocksApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.PreviousPeriodApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.employees.EmployeesApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.tangible.TangibleApi;
import uk.gov.companieshouse.api.model.company.CompanyProfileApi;

import java.util.Objects;

public class SmallFullApiData {

    private CurrentPeriodApi currentPeriod;

    private PreviousPeriodApi previousPeriod;

    private CompanyProfileApi companyProfile;

    private CompanyAccountsApi companyAccounts;

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

    public CompanyAccountsApi getCompanyAccounts() { return companyAccounts; }

    public void setCompanyAccounts(CompanyAccountsApi companyAccounts) {
        this.companyAccounts = companyAccounts;
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (! (o instanceof SmallFullApiData))
            return false;
        SmallFullApiData that = (SmallFullApiData) o;
        return Objects.equals(getCurrentPeriod(), that.getCurrentPeriod()) &&
                Objects.equals(getPreviousPeriod(), that.getPreviousPeriod()) &&
                Objects.equals(getCompanyProfile(), that.getCompanyProfile()) &&
                Objects.equals(getCompanyAccounts(), that.getCompanyAccounts()) &&
                Objects.equals(getApproval(), that.getApproval()) &&
                Objects.equals(getBalanceSheetStatements(), that.getBalanceSheetStatements()) &&
                Objects.equals(getAccountingPolicies(), that.getAccountingPolicies()) &&
                Objects.equals(getTangibleAssets(), that.getTangibleAssets()) &&
                Objects.equals(getStocks(), that.getStocks()) &&
                Objects.equals(getDebtors(), that.getDebtors()) &&
                Objects.equals(getEmployees(), that.getEmployees()) &&
                Objects.equals(getCurrentAssetsInvestments(), that.getCurrentAssetsInvestments()) &&
                Objects.equals(getCreditorsWithinOneYear(), that.getCreditorsWithinOneYear()) &&
                Objects.equals(getCreditorsAfterOneYear(), that.getCreditorsAfterOneYear()) &&
                Objects.equals(getFixedAssetsInvestments(), that.getFixedAssetsInvestments());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCurrentPeriod(), getPreviousPeriod(), getCompanyProfile(),
                getCompanyAccounts(), getApproval(), getBalanceSheetStatements(),
                getAccountingPolicies(), getTangibleAssets(), getStocks(), getDebtors(),
                getEmployees(), getCurrentAssetsInvestments(), getCreditorsWithinOneYear(),
                getCreditorsAfterOneYear(), getFixedAssetsInvestments());
    }

    @Override
    public String toString() {
        return "SmallFullApiData{" +
                "currentPeriod=" + currentPeriod +
                ", previousPeriod=" + previousPeriod +
                ", companyProfile=" + companyProfile +
                ", companyAccounts=" + companyAccounts +
                ", approval=" + approval +
                ", balanceSheetStatements=" + balanceSheetStatements +
                ", accountingPolicies=" + accountingPolicies +
                ", tangibleAssets=" + tangibleAssets +
                ", stocks=" + stocks +
                ", debtors=" + debtors +
                ", employees=" + employees +
                ", currentAssetsInvestments=" + currentAssetsInvestments +
                ", creditorsWithinOneYear=" + creditorsWithinOneYear +
                ", creditorsAfterOneYear=" + creditorsAfterOneYear +
                ", fixedAssetsInvestments=" + fixedAssetsInvestments +
                '}';
    }
}
