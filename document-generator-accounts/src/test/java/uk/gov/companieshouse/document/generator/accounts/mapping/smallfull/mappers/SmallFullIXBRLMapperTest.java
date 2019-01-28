package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.mappers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.model.accounts.smallfull.ApprovalApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.BalanceSheetApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.CapitalAndReservesApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.CurrentAssetsApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.CurrentPeriodApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.Debtors.CurrentPeriod;
import uk.gov.companieshouse.api.model.accounts.smallfull.Debtors.DebtorsApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.Debtors.PreviousPeriod;
import uk.gov.companieshouse.api.model.accounts.smallfull.creditorsafteroneyear.CreditorsAfterOneYearApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.creditorswithinoneyear.CreditorsWithinOneYearApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.stocks.StocksApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.FixedAssetsApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.OtherLiabilitiesOrAssetsApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.PreviousPeriodApi;
import uk.gov.companieshouse.api.model.company.CompanyProfileApi;
import uk.gov.companieshouse.api.model.company.account.CompanyAccountApi;
import uk.gov.companieshouse.api.model.company.account.LastAccountsApi;
import uk.gov.companieshouse.api.model.company.account.NextAccountsApi;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.SmallFullApiData;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.SmallFullAccountIxbrl;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.BalanceSheet;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.CalledUpSharedCapitalNotPaid;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.capitalandreserves.CapitalAndReserve;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.currentassets.CurrentAssets;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.fixedassets.FixedAssets;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.otherliabilitiesandassets.OtherLiabilitiesOrAssets;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.company.Company;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.creditorsafteroneyear.CreditorsAfterOneYear;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.creditorswithinoneyear.CreditorsWithinOneYear;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.debtors.Debtors;
import java.time.LocalDate;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.notes.BalanceSheetNotes;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.stocks.StocksNote;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SmallFullIXBRLMapperTest {

    private static final  Long VALUE_ONE = 100L;
    private static final  Long VALUE_TWO = 200L;
    private static final  Long VALUE_THREE = 300L;
    private static final String JURISDICTION = "jurisdiction";
    private static final String COMPANY_NAME = "companyName";
    private static final String COMPANY_NUMBER = "companyNumber";
    private static final String NAME = "name";
    private static final String DETAILS = "details";

    @Test
    @DisplayName("tests the mapping of the smallFull IXBRL model with a current and previous period")
    void testSmallFullMapperCurrentAndPrevious() {

        SmallFullApiData smallFullApiData = createSmallFullData(true);

        SmallFullAccountIxbrl smallFullAccountIxbrl = SmallFullIXBRLMapper.INSTANCE.mapSmallFullIXBRLModel(smallFullApiData);

        assertNotNull(smallFullAccountIxbrl);
        assertApprovalsMapped(smallFullAccountIxbrl.getApprovalDate(), smallFullAccountIxbrl.getApprovalName());
        assertBalanceSheetMapped(smallFullAccountIxbrl.getBalanceSheet(), true);
        assertCompanyProfileMapped(smallFullAccountIxbrl.getCompany());
        assertBalanceSheetNotesMapped(smallFullAccountIxbrl.getBalanceSheetNotes(), true );
    }

    @Test
    @DisplayName("tests the mapping of the smallFull IXBRL model with a current period Only")
    void testSmallFullMapperCurrentOny() {

        SmallFullApiData smallFullApiData = createSmallFullData(false);

        SmallFullAccountIxbrl smallFullAccountIxbrl = SmallFullIXBRLMapper.INSTANCE.mapSmallFullIXBRLModel(smallFullApiData);

        assertNotNull(smallFullAccountIxbrl);
        assertApprovalsMapped(smallFullAccountIxbrl.getApprovalDate(), smallFullAccountIxbrl.getApprovalName());
        assertBalanceSheetMapped(smallFullAccountIxbrl.getBalanceSheet(), false);
        assertCompanyProfileMapped(smallFullAccountIxbrl.getCompany());
        assertBalanceSheetNotesMapped(smallFullAccountIxbrl.getBalanceSheetNotes(),false);
    }

    private SmallFullApiData createSmallFullData(boolean isSameYearFiling) {

        SmallFullApiData smallFullApiData = new SmallFullApiData();

        smallFullApiData.setApproval(createApproval());
        smallFullApiData.setCompanyProfile(createCompanyProfile(isSameYearFiling));
        smallFullApiData.setCurrentPeriod(createCurrentPeriod());
        if(isSameYearFiling == true) {
            smallFullApiData.setPreviousPeriod(createPreviousPeriod());
        }

        smallFullApiData.setStocks(createStocks());
        smallFullApiData.setDebtors(createDebtors());
        smallFullApiData.setCreditorsWithinOneYear(createCreditorsWithinOneYear());
        smallFullApiData.setCreditorsAfterOneYear(createCreditorsAfterOneYear());

        return smallFullApiData;
    }

    private CurrentPeriodApi createCurrentPeriod() {

        CurrentPeriodApi currentPeriod = new CurrentPeriodApi();
        currentPeriod.setBalanceSheet(createBalanceSheetValues());

        return currentPeriod;
    }

    private PreviousPeriodApi  createPreviousPeriod() {

        PreviousPeriodApi previousPeriod = new PreviousPeriodApi();
        previousPeriod.setBalanceSheet(createBalanceSheetValues());

        return previousPeriod;
    }

    private ApprovalApi createApproval() {

        ApprovalApi approval = new ApprovalApi();
        approval.setDate(LocalDate.of(2018, 12, 31));
        approval.setName(NAME);

        return approval;
    }

    private CompanyProfileApi createCompanyProfile(boolean isMultiYearFiling) {

        CompanyProfileApi companyProfile = new CompanyProfileApi();
        companyProfile.setCompanyName(COMPANY_NAME);
        companyProfile.setCompanyNumber(COMPANY_NUMBER);
        companyProfile.setJurisdiction(JURISDICTION);
        createAccountsFilingDates(isMultiYearFiling, companyProfile);

        return companyProfile;
    }

    private CompanyProfileApi createAccountsFilingDates(boolean isMultiYearFiling, CompanyProfileApi companyProfileApi ) {

        CompanyAccountApi companyAccountsApi = new CompanyAccountApi();
        LastAccountsApi lastAccountsApi = new LastAccountsApi();
        NextAccountsApi nextAccountsApi = new NextAccountsApi();

        if (isMultiYearFiling == true) {
            lastAccountsApi.setPeriodEndOn(LocalDate.of(2017,12,31));
            lastAccountsApi.setPeriodStartOn(LocalDate.of(2017, 01, 01));
        }

        nextAccountsApi.setPeriodEndOn(LocalDate.of(2018,12,31));
        nextAccountsApi.setPeriodStartOn(LocalDate.of(2018,01,01));

        companyAccountsApi.setLastAccounts(lastAccountsApi);
        companyAccountsApi.setNextAccounts(nextAccountsApi);
        companyProfileApi.setAccounts(companyAccountsApi);

        return companyProfileApi;
    }

    private BalanceSheetApi createBalanceSheetValues() {

        BalanceSheetApi balanceSheet = new BalanceSheetApi();
        balanceSheet.setCapitalAndReserves(createCapitalAndReserve());
        balanceSheet.setCurrentAssets(createCurrentAssets());
        balanceSheet.setFixedAssets(createFixedAssets());
        balanceSheet.setOtherLiabilitiesOrAssets(createOtherLiabilitiesOrAssets());
        balanceSheet.setCalledUpShareCapitalNotPaid(VALUE_ONE);

        return balanceSheet;
    }

    private OtherLiabilitiesOrAssetsApi createOtherLiabilitiesOrAssets() {

        OtherLiabilitiesOrAssetsApi otherLiabilitiesOrAssets = new OtherLiabilitiesOrAssetsApi();
        otherLiabilitiesOrAssets.setTotalNetAssets(new Long(VALUE_ONE));
        otherLiabilitiesOrAssets.setAccrualsAndDeferredIncome(new Long(VALUE_TWO));
        otherLiabilitiesOrAssets.setCreditorsAfterOneYear(new Long(VALUE_THREE));
        otherLiabilitiesOrAssets.setCreditorsDueWithinOneYear(new Long(VALUE_ONE));
        otherLiabilitiesOrAssets.setNetCurrentAssets(new Long(VALUE_TWO));
        otherLiabilitiesOrAssets.setPrepaymentsAndAccruedIncome(new Long(VALUE_THREE));
        otherLiabilitiesOrAssets.setTotalAssetsLessCurrentLiabilities(new Long(VALUE_ONE));
        otherLiabilitiesOrAssets.setProvisionForLiabilities(new Long(VALUE_TWO));

        return otherLiabilitiesOrAssets;
    }

    private FixedAssetsApi createFixedAssets() {

        FixedAssetsApi fixedAssets = new FixedAssetsApi();
        fixedAssets.setTangible(new Long(VALUE_ONE));
        fixedAssets.setTotal(new Long(VALUE_TWO));

        return fixedAssets;
    }

    private CurrentAssetsApi createCurrentAssets() {

        CurrentAssetsApi currentAssets = new CurrentAssetsApi();
        currentAssets.setCashInBankAndInHand(new Long(VALUE_ONE));
        currentAssets.setDebtors(new Long(VALUE_TWO));
        currentAssets.setStocks(new Long(VALUE_THREE));
        currentAssets.setTotal(new Long(VALUE_ONE));

        return currentAssets;
    }

    private CapitalAndReservesApi createCapitalAndReserve() {

        CapitalAndReservesApi capitalAndReserves = new CapitalAndReservesApi();
        capitalAndReserves.setCalledUpShareCapital(new Long(VALUE_ONE));
        capitalAndReserves.setOtherReserves(new Long(VALUE_TWO));
        capitalAndReserves.setProfitAndLoss(new Long(VALUE_THREE));
        capitalAndReserves.setSharePremiumAccount(new Long(VALUE_ONE));
        capitalAndReserves.setTotalShareholdersFunds(new Long(VALUE_TWO));

        return capitalAndReserves;
    }


    private void assertBalanceSheetMapped(BalanceSheet balanceSheet, boolean isMultiYearFiling) {

        assertCapitalAndReserve(balanceSheet.getCapitalAndReserve(), isMultiYearFiling);
        assertCurrentAssets(balanceSheet.getCurrentAssets(), isMultiYearFiling);
        assertFixedAssets(balanceSheet.getFixedAssets(), isMultiYearFiling);
        assertOtherLiabilitiesOrAssets(balanceSheet.getOtherLiabilitiesOrAssets(), isMultiYearFiling);
        assertCalledUpSharedCapitalNotPaid(balanceSheet.getCalledUpSharedCapitalNotPaid(), isMultiYearFiling);
    }

    private void assertCalledUpSharedCapitalNotPaid(CalledUpSharedCapitalNotPaid calledUpSharedCapitalNotPaid, boolean isMultiYearFiling) {

        assertNotNull(calledUpSharedCapitalNotPaid);
        assertEquals(new Long(VALUE_ONE), calledUpSharedCapitalNotPaid.getCurrentAmount());

        if (isMultiYearFiling == true) {
            assertEquals(new Long(VALUE_ONE), calledUpSharedCapitalNotPaid.getPreviousAmount());
        }
    }

    private void assertOtherLiabilitiesOrAssets(OtherLiabilitiesOrAssets otherLiabilitiesOrAssets, boolean isMultiYearFiling) {

        assertNotNull(otherLiabilitiesOrAssets);
        assertEquals(new Long(VALUE_ONE), otherLiabilitiesOrAssets.getCurrentTotalNetAssets());
        assertEquals(new Long(VALUE_TWO), otherLiabilitiesOrAssets.getAccrualsAndDeferredIncome().getCurrentAmount());
        assertEquals(new Long(VALUE_THREE), otherLiabilitiesOrAssets.getCreditorsAmountsFallingDueAfterMoreThanOneYear().getCurrentAmount());
        assertEquals(new Long(VALUE_ONE), otherLiabilitiesOrAssets.getCreditorsAmountsFallingDueWithinOneYear().getCurrentAmount());
        assertEquals(new Long(VALUE_TWO), otherLiabilitiesOrAssets.getNetCurrentAssets().getCurrentAmount());
        assertEquals(new Long(VALUE_THREE), otherLiabilitiesOrAssets.getPrepaymentsAndAccruedIncome().getCurrentAmount());
        assertEquals(new Long(VALUE_ONE), otherLiabilitiesOrAssets.getTotalAssetsLessCurrentLiabilities().getCurrentAmount());
        assertEquals(new Long(VALUE_TWO), otherLiabilitiesOrAssets.getProvisionForLiabilities().getCurrentAmount());

        if (isMultiYearFiling == true) {

            assertEquals(new Long(VALUE_ONE), otherLiabilitiesOrAssets.getPreviousTotalNetAssets());
            assertEquals(new Long(VALUE_TWO), otherLiabilitiesOrAssets.getAccrualsAndDeferredIncome().getPreviousAmount());
            assertEquals(new Long(VALUE_THREE), otherLiabilitiesOrAssets.getCreditorsAmountsFallingDueAfterMoreThanOneYear().getPreviousAmount());
            assertEquals(new Long(VALUE_ONE), otherLiabilitiesOrAssets.getCreditorsAmountsFallingDueWithinOneYear().getPreviousAmount());
            assertEquals(new Long(VALUE_TWO), otherLiabilitiesOrAssets.getNetCurrentAssets().getPreviousAmount());
            assertEquals(new Long(VALUE_THREE),otherLiabilitiesOrAssets.getPrepaymentsAndAccruedIncome().getPreviousAmount());
            assertEquals(new Long(VALUE_ONE), otherLiabilitiesOrAssets.getTotalAssetsLessCurrentLiabilities().getPreviousAmount());
            assertEquals(new Long(VALUE_TWO), otherLiabilitiesOrAssets.getProvisionForLiabilities().getPreviousAmount());
        }
    }

    private void assertFixedAssets(FixedAssets fixedAssets, boolean isMultiYearFiling) {

        assertNotNull(fixedAssets);
        assertEquals(new Long(VALUE_ONE), fixedAssets.getTangibleAssets().getCurrentAmount());
        assertEquals(new Long(VALUE_TWO), fixedAssets.getTotalFixedAssetsCurrent());

        if (isMultiYearFiling == true) {

            assertEquals(new Long(VALUE_ONE), fixedAssets.getTangibleAssets().getPreviousAmount());
            assertEquals(new Long(VALUE_TWO), fixedAssets.getTotalFixedAssetsPrevious());
        }
    }

    private void assertCurrentAssets(CurrentAssets currentAssets, boolean isMultiYearFiling) {

        assertNotNull(currentAssets);
        assertEquals(new Long(VALUE_ONE), currentAssets.getCashAtBankAndInHand().getCurrentAmount());
        assertEquals(new Long(VALUE_TWO), currentAssets.getDebtors().getCurrentAmount());
        assertEquals(new Long(VALUE_THREE), currentAssets.getStocks().getCurrentAmount());
        assertEquals(new Long(VALUE_ONE), currentAssets.getCurrentTotal());

        if (isMultiYearFiling == true) {

            assertEquals(new Long(VALUE_ONE), currentAssets.getCashAtBankAndInHand().getPreviousAmount());
            assertEquals(new Long(VALUE_TWO), currentAssets.getDebtors().getPreviousAmount());
            assertEquals(new Long(VALUE_THREE), currentAssets.getStocks().getPreviousAmount());
            assertEquals(new Long(VALUE_ONE), currentAssets.getPreviousTotal());
        }
    }

    private void assertCapitalAndReserve(CapitalAndReserve capitalAndReserve, boolean isMultiYearFiling) {

        assertNotNull(capitalAndReserve);
        assertEquals(new Long(VALUE_ONE), capitalAndReserve.getCalledUpShareCapital().getCurrentAmount());
        assertEquals(new Long(VALUE_TWO), capitalAndReserve.getOtherReserves().getCurrentAmount());
        assertEquals(new Long(VALUE_THREE), capitalAndReserve.getProfitAndLoss().getCurrentAmount());
        assertEquals(new Long(VALUE_ONE), capitalAndReserve.getSharePremiumAccount().getCurrentAmount());
        assertEquals(new Long(VALUE_TWO), capitalAndReserve.getTotalShareHoldersFunds().getCurrentAmount());

        if (isMultiYearFiling == true) {

            assertEquals(new Long(VALUE_ONE),capitalAndReserve.getCalledUpShareCapital().getPreviousAmount());
            assertEquals(new Long(VALUE_TWO), capitalAndReserve.getOtherReserves().getPreviousAmount());
            assertEquals(new Long(VALUE_THREE), capitalAndReserve.getProfitAndLoss().getPreviousAmount());
            assertEquals(new Long(VALUE_ONE), capitalAndReserve.getSharePremiumAccount().getPreviousAmount());
            assertEquals(new Long(VALUE_TWO), capitalAndReserve.getTotalShareHoldersFunds().getPreviousAmount());
        }
    }

    private void assertCompanyProfileMapped(Company company) {

        assertNotNull(company);
        assertEquals(COMPANY_NAME, company.getCompanyName());
        assertEquals(COMPANY_NUMBER, company.getCompanyNumber());
        assertEquals(JURISDICTION, company.getJurisdiction());
    }

    private void assertApprovalsMapped(String approvalDate, String approvalName) {

        assertEquals("31 December 2018", approvalDate);
        assertEquals(NAME, approvalName);
    }

    private void assertBalanceSheetNotesMapped(BalanceSheetNotes balanceSheetNotes, boolean isMultiYearFiling) {
        assertStocksNoteMapped(balanceSheetNotes.getStocksNote(), isMultiYearFiling);
        assertDebtorsNoteMapped(balanceSheetNotes.getDebtorsNote(), isMultiYearFiling);
        assertCreditorsWithinOneYearNoteMapped(balanceSheetNotes.getCreditorsWithinOneYearNote(), isMultiYearFiling);
        assertCreditorsAfterOneYearNoteMapped(balanceSheetNotes.getCreditorsAfterOneYearNote(), isMultiYearFiling);
    }

    private void assertDebtorsNoteMapped(Debtors debtorsNote, boolean isMultiYearFiling) {
        assertEquals(DETAILS, debtorsNote.getDetails());
        assertEquals(VALUE_ONE, debtorsNote.getGreaterThanOneYear().getCurrentAmount());
        assertEquals(VALUE_TWO, debtorsNote.getOtherDebtors().getCurrentAmount());
        assertEquals(VALUE_THREE, debtorsNote.getPrepaymentsAndAccruedIncome().getCurrentAmount());
        assertEquals(VALUE_ONE, debtorsNote.getTradeDebtors().getCurrentAmount());
        assertEquals(VALUE_TWO, debtorsNote.getTotal().getCurrentAmount());

        if (isMultiYearFiling == true) {

            assertEquals(VALUE_ONE, debtorsNote.getGreaterThanOneYear().getPreviousAmount());
            assertEquals(VALUE_TWO, debtorsNote.getOtherDebtors().getPreviousAmount());
            assertEquals(VALUE_THREE, debtorsNote.getPrepaymentsAndAccruedIncome().getPreviousAmount());
            assertEquals(VALUE_ONE, debtorsNote.getTradeDebtors().getPreviousAmount());
            assertEquals(VALUE_TWO, debtorsNote.getTotal().getPreviousAmount());
        }
    }
    
    private void assertStocksNoteMapped(StocksNote stocksNote, boolean isMultiYearFiling) {
        assertEquals(VALUE_ONE, stocksNote.getStocks().getCurrentAmount());
        assertEquals(VALUE_TWO, stocksNote.getPaymentsOnAccount().getCurrentAmount());
        assertEquals(VALUE_THREE, stocksNote.getTotal().getCurrentAmount());

        if (isMultiYearFiling == true) {

            assertEquals(VALUE_ONE, stocksNote.getStocks().getPreviousAmount());
            assertEquals(VALUE_TWO, stocksNote.getPaymentsOnAccount().getPreviousAmount());
            assertEquals(VALUE_THREE, stocksNote.getTotal().getPreviousAmount());
        }
    }
    
    private void assertCreditorsWithinOneYearNoteMapped(CreditorsWithinOneYear creditorsWithinOneYearNote, boolean isMultiYearFiling) {
        assertEquals(DETAILS, creditorsWithinOneYearNote.getDetails());
        assertEquals(VALUE_ONE, creditorsWithinOneYearNote.getAccrualsAndDeferredIncome().getCurrentAmount());
        assertEquals(VALUE_TWO, creditorsWithinOneYearNote.getBankLoansAndOverdrafts().getCurrentAmount());
        assertEquals(VALUE_THREE, creditorsWithinOneYearNote.getFinanceLeasesAndHirePurchaseContracts().getCurrentAmount());
        assertEquals(VALUE_ONE, creditorsWithinOneYearNote.getOtherCreditors().getCurrentAmount());
        assertEquals(VALUE_TWO, creditorsWithinOneYearNote.getTaxationAndSocialSecurity().getCurrentAmount());
        assertEquals(VALUE_THREE, creditorsWithinOneYearNote.getTradeCreditors().getCurrentAmount());
        assertEquals(VALUE_ONE, creditorsWithinOneYearNote.getTotal().getCurrentAmount());

        if (isMultiYearFiling == true) {

            assertEquals(VALUE_ONE, creditorsWithinOneYearNote.getAccrualsAndDeferredIncome().getPreviousAmount());
            assertEquals(VALUE_TWO, creditorsWithinOneYearNote.getBankLoansAndOverdrafts().getPreviousAmount());
            assertEquals(VALUE_THREE, creditorsWithinOneYearNote.getFinanceLeasesAndHirePurchaseContracts().getPreviousAmount());
            assertEquals(VALUE_ONE, creditorsWithinOneYearNote.getOtherCreditors().getPreviousAmount());
            assertEquals(VALUE_TWO, creditorsWithinOneYearNote.getTaxationAndSocialSecurity().getPreviousAmount());
            assertEquals(VALUE_THREE, creditorsWithinOneYearNote.getTradeCreditors().getPreviousAmount());
            assertEquals(VALUE_ONE, creditorsWithinOneYearNote.getTotal().getPreviousAmount());
        }
    }

    private void assertCreditorsAfterOneYearNoteMapped(CreditorsAfterOneYear creditorsAfterOneYearNote, boolean isMultiYearFiling) {
        assertEquals(DETAILS, creditorsAfterOneYearNote.getDetails());
        assertEquals(VALUE_ONE, creditorsAfterOneYearNote.getBankLoansAndOverdrafts().getCurrentAmount());
        assertEquals(VALUE_TWO, creditorsAfterOneYearNote.getFinanceLeasesAndHirePurchaseContracts().getCurrentAmount());
        assertEquals(VALUE_THREE, creditorsAfterOneYearNote.getOtherCreditors().getCurrentAmount());
        assertEquals(VALUE_ONE, creditorsAfterOneYearNote.getTotal().getCurrentAmount());

        if (isMultiYearFiling == true) {

            assertEquals(VALUE_ONE, creditorsAfterOneYearNote.getBankLoansAndOverdrafts().getPreviousAmount());
            assertEquals(VALUE_TWO, creditorsAfterOneYearNote.getFinanceLeasesAndHirePurchaseContracts().getPreviousAmount());
            assertEquals(VALUE_THREE, creditorsAfterOneYearNote.getOtherCreditors().getPreviousAmount());
            assertEquals(VALUE_ONE, creditorsAfterOneYearNote.getTotal().getPreviousAmount());
        }
    }
    
    private DebtorsApi createDebtors() {

        DebtorsApi debtors = new DebtorsApi();

        CurrentPeriod debtorsCurrentPeriod = new CurrentPeriod();
        debtorsCurrentPeriod.setDetails(DETAILS);
        debtorsCurrentPeriod.setGreaterThanOneYear(VALUE_ONE);
        debtorsCurrentPeriod.setOtherDebtors(VALUE_TWO);
        debtorsCurrentPeriod.setPrepaymentsAndAccruedIncome(VALUE_THREE);
        debtorsCurrentPeriod.setTradeDebtors(VALUE_ONE);
        debtorsCurrentPeriod.setTotal(VALUE_TWO);
        debtors.setDebtorsCurrentPeriod(debtorsCurrentPeriod);

        PreviousPeriod debtorsPreviousPeriod = new PreviousPeriod();
        debtorsPreviousPeriod.setGreaterThanOneYear(VALUE_ONE);
        debtorsPreviousPeriod.setOtherDebtors(VALUE_TWO);
        debtorsPreviousPeriod.setPrepaymentsAndAccruedIncome(VALUE_THREE);
        debtorsPreviousPeriod.setTradeDebtors(VALUE_ONE);
        debtorsPreviousPeriod.setTotal(VALUE_TWO);
        debtors.setDebtorsPreviousPeriod(debtorsPreviousPeriod);

        return debtors;
    }
    
    private StocksApi createStocks() {

        StocksApi stocks = new StocksApi();

        uk.gov.companieshouse.api.model.accounts.smallfull.stocks.CurrentPeriod stocksCurrentPeriod = 
                new uk.gov.companieshouse.api.model.accounts.smallfull.stocks.CurrentPeriod();
        
        stocksCurrentPeriod.setStocks(VALUE_ONE);
        stocksCurrentPeriod.setPaymentsOnAccount(VALUE_TWO);
        stocksCurrentPeriod.setTotal(VALUE_THREE);
        stocks.setCurrentPeriod(stocksCurrentPeriod);

        uk.gov.companieshouse.api.model.accounts.smallfull.stocks.PreviousPeriod stocksPreviousPeriod
        = new uk.gov.companieshouse.api.model.accounts.smallfull.stocks.PreviousPeriod();
        
        stocksPreviousPeriod.setStocks(VALUE_ONE);
        stocksPreviousPeriod.setPaymentsOnAccount(VALUE_TWO);
        stocksPreviousPeriod.setTotal(VALUE_THREE);
        stocks.setPreviousPeriod(stocksPreviousPeriod);

        return stocks;
    }    
    
    private CreditorsWithinOneYearApi createCreditorsWithinOneYear() {

      CreditorsWithinOneYearApi creditorsWithinOneYearApi = new CreditorsWithinOneYearApi();

      uk.gov.companieshouse.api.model.accounts.smallfull.creditorswithinoneyear.CurrentPeriod creditorsWithinOneYearCurrentPeriod =
          new uk.gov.companieshouse.api.model.accounts.smallfull.creditorswithinoneyear.CurrentPeriod();
      
      creditorsWithinOneYearCurrentPeriod.setDetails(DETAILS);
      creditorsWithinOneYearCurrentPeriod.setAccrualsAndDeferredIncome(VALUE_ONE);
      creditorsWithinOneYearCurrentPeriod.setBankLoansAndOverdrafts(VALUE_TWO);
      creditorsWithinOneYearCurrentPeriod.setFinanceLeasesAndHirePurchaseContracts(VALUE_THREE);
      creditorsWithinOneYearCurrentPeriod.setOtherCreditors(VALUE_ONE);
      creditorsWithinOneYearCurrentPeriod.setTaxationAndSocialSecurity(VALUE_TWO);
      creditorsWithinOneYearCurrentPeriod.setTradeCreditors(VALUE_THREE);
      creditorsWithinOneYearCurrentPeriod.setTotal(VALUE_ONE);
      creditorsWithinOneYearApi.setCreditorsWithinOneYearCurrentPeriod(creditorsWithinOneYearCurrentPeriod);

      uk.gov.companieshouse.api.model.accounts.smallfull.creditorswithinoneyear.PreviousPeriod creditorsWithinOneYearPreviousPeriod =
          new uk.gov.companieshouse.api.model.accounts.smallfull.creditorswithinoneyear.PreviousPeriod();
      
      creditorsWithinOneYearPreviousPeriod.setAccrualsAndDeferredIncome(VALUE_ONE);
      creditorsWithinOneYearPreviousPeriod.setBankLoansAndOverdrafts(VALUE_TWO);
      creditorsWithinOneYearPreviousPeriod.setFinanceLeasesAndHirePurchaseContracts(VALUE_THREE);
      creditorsWithinOneYearPreviousPeriod.setOtherCreditors(VALUE_ONE);
      creditorsWithinOneYearPreviousPeriod.setTaxationAndSocialSecurity(VALUE_TWO);
      creditorsWithinOneYearPreviousPeriod.setTradeCreditors(VALUE_THREE);
      creditorsWithinOneYearPreviousPeriod.setTotal(VALUE_ONE);
      creditorsWithinOneYearApi.setCreditorsWithinOneYearPreviousPeriod(creditorsWithinOneYearPreviousPeriod);

      return creditorsWithinOneYearApi;
  }
    
    private CreditorsAfterOneYearApi createCreditorsAfterOneYear() {

        CreditorsAfterOneYearApi creditorsAfterOneYearApi = new CreditorsAfterOneYearApi();

        uk.gov.companieshouse.api.model.accounts.smallfull.creditorsafteroneyear.CurrentPeriod creditorsAfterOneYearCurrentPeriod =
            new uk.gov.companieshouse.api.model.accounts.smallfull.creditorsafteroneyear.CurrentPeriod();
        
        creditorsAfterOneYearCurrentPeriod.setDetails(DETAILS);
        creditorsAfterOneYearCurrentPeriod.setBankLoansAndOverdrafts(VALUE_ONE);
        creditorsAfterOneYearCurrentPeriod.setFinanceLeasesAndHirePurchaseContracts(VALUE_TWO);
        creditorsAfterOneYearCurrentPeriod.setOtherCreditors(VALUE_THREE);
        creditorsAfterOneYearCurrentPeriod.setTotal(VALUE_ONE);
        
        creditorsAfterOneYearApi.setCurrentPeriod(creditorsAfterOneYearCurrentPeriod);


        uk.gov.companieshouse.api.model.accounts.smallfull.creditorsafteroneyear.PreviousPeriod creditorsAfterOneYearPreviousPeriod =
            new uk.gov.companieshouse.api.model.accounts.smallfull.creditorsafteroneyear.PreviousPeriod();
        
        creditorsAfterOneYearPreviousPeriod.setBankLoansAndOverdrafts(VALUE_ONE);
        creditorsAfterOneYearPreviousPeriod.setFinanceLeasesAndHirePurchaseContracts(VALUE_TWO);
        creditorsAfterOneYearPreviousPeriod.setOtherCreditors(VALUE_THREE);
        creditorsAfterOneYearPreviousPeriod.setTotal(VALUE_ONE);
        
        creditorsAfterOneYearApi.setPreviousPeriod(creditorsAfterOneYearPreviousPeriod);

        return creditorsAfterOneYearApi;
    }    
}
