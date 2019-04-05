package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model;

import com.google.gson.Gson;
import uk.gov.companieshouse.api.model.accounts.CompanyAccountsApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.AccountingPoliciesApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.ApprovalApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.BalanceSheetStatementsApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.CurrentPeriodApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.Debtors.DebtorsApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.creditorsafteroneyear.CreditorsAfterOneYearApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.creditorswithinoneyear.CreditorsWithinOneYearApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.stocks.StocksApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.PreviousPeriodApi;
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

    private StocksApi stocks;

    private DebtorsApi debtors;
    
    private CreditorsWithinOneYearApi creditorsWithinOneYear;
    
    private CreditorsAfterOneYearApi creditorsAfterOneYear;

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

    public CreditorsAfterOneYearApi getCreditorsAfterOneYear() {
      return creditorsAfterOneYear;
    }

    public void setCreditorsAfterOneYear(CreditorsAfterOneYearApi creditorsAfterOneYear) {
      this.creditorsAfterOneYear = creditorsAfterOneYear;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SmallFullApiData)) return false;
        SmallFullApiData that = (SmallFullApiData) o;
        return Objects.equals(getCurrentPeriod(), that.getCurrentPeriod()) &&
                Objects.equals(getPreviousPeriod(), that.getPreviousPeriod()) &&
                Objects.equals(getCompanyProfile(), that.getCompanyProfile()) &&
                Objects.equals(getApproval(), that.getApproval()) &&
                Objects.equals(getBalanceSheetStatements(), that.getBalanceSheetStatements()) &&
                Objects.equals(getAccountingPolicies(), that.getAccountingPolicies()) &&
                Objects.equals(getStocks(), that.getStocks()) &&
                Objects.equals(getDebtors(), that.getDebtors()) &&
                Objects.equals(getCreditorsWithinOneYear(), that.getCreditorsWithinOneYear()) &&
                Objects.equals(getCreditorsAfterOneYear(), that.getCreditorsAfterOneYear()) &&
                Objects.equals(getTangibleAssets(), that.getTangibleAssets());
    }

    @Override
    public int hashCode() {
      return Objects.hash(accountingPolicies, approval, balanceSheetStatements, companyProfile,
          creditorsWithinOneYear, creditorsWithinOneYear, currentPeriod, stocks, debtors, previousPeriod, 
          tangibleAssets);
    }
    
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
