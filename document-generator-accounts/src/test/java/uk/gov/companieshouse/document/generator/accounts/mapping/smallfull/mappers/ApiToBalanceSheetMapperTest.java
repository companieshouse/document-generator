package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.mappers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.model.accounts.smallfull.BalanceSheetApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.BalanceSheetLegalStatementsApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.BalanceSheetStatementsApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.CapitalAndReservesApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.CurrentAssetsApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.CurrentPeriodApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.FixedAssetsApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.MembersFundsApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.OtherLiabilitiesOrAssetsApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.PreviousPeriodApi;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.BalanceSheetStatements;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.CalledUpSharedCapitalNotPaid;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.capitalandreserves.CapitalAndReserve;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.currentassets.CurrentAssets;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.fixedassets.FixedAssets;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.membersfunds.MembersFunds;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.otherliabilitiesandassets.OtherLiabilitiesOrAssets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApiToBalanceSheetMapperTest {

    private ApiToBalanceSheetMapper apiToBalanceSheetMapper = new ApiToBalanceSheetMapperImpl();

    private static final Long CURRENT_CALLED_UP_SHARE_CAPITAL_NOT_PAID = 101L;

    private static final Long CURRENT_CAPITAL_AND_RESERVES_CALLED_UP_SHARE_CAPITAL = 111L;
    private static final Long CURRENT_CAPITAL_AND_RESERVES_OTHER_RESERVES = 112L;
    private static final Long CURRENT_CAPITAL_AND_RESERVES_PROFIT_AND_LOSS = 113L;
    private static final Long CURRENT_CAPITAL_AND_RESERVES_SHARE_PREMIUM_ACCOUNT = 114L;
    private static final Long CURRENT_CAPITAL_AND_RESERVES_TOTAL_SHAREHOLDERS_FUNDS = 115L;

    private static final Long CURRENT_CURRENT_ASSETS_CASH_AT_BANK_AND_IN_HAND = 121L;
    private static final Long CURRENT_CURRENT_ASSETS_DEBTORS = 122L;
    private static final Long CURRENT_CURRENT_ASSETS_STOCKS = 123L;
    private static final Long CURRENT_CURRENT_ASSETS_INVESTMENTS = 124L;
    private static final Long CURRENT_CURRENT_ASSETS_TOTAL = 125L;

    private static final Long CURRENT_FIXED_ASSETS_TANGIBLE = 131L;
    private static final Long CURRENT_FIXED_ASSETS_INVESTMENTS = 132L;
    private static final Long CURRENT_FIXED_ASSETS_TOTAL = 133L;

    private static final Long CURRENT_OTHER_LIABILITIES_ACCRUALS_AND_DEFERRED_INCOME = 141L;
    private static final Long CURRENT_OTHER_LIABILITIES_CREDITORS_AFTER = 142L;
    private static final Long CURRENT_OTHER_LIABILITIES_CREDITORS_WITHIN = 143L;
    private static final Long CURRENT_OTHER_LIABILITIES_NET_CURRENT_ASSETS = 144L;
    private static final Long CURRENT_OTHER_LIABILITIES_PREPAYMENTS_AND_ACCRUED_INCOME = 145L;
    private static final Long CURRENT_OTHER_LIABILITIES_PROVISION_FOR_LIABILITIES = 146L;
    private static final Long CURRENT_OTHER_LIABILITIES_TOTAL_ASSETS_LESS_CURRENT_LIABILITIES = 147L;
    private static final Long CURRENT_OTHER_LIABILITIES_TOTAL_NET_ASSETS = 148L;

    private static final Long CURRENT_MEMBERS_FUNDS_PROFIT_AND_LOSS_ACCOUNT = 151L;
    private static final Long CURRENT_MEMBERS_FUNDS_TOTAL = 152L;

    private static final Long PREVIOUS_CALLED_UP_SHARE_CAPITAL_NOT_PAID = 201L;

    private static final Long PREVIOUS_CAPITAL_AND_RESERVES_CALLED_UP_SHARE_CAPITAL = 211L;
    private static final Long PREVIOUS_CAPITAL_AND_RESERVES_OTHER_RESERVES = 212L;
    private static final Long PREVIOUS_CAPITAL_AND_RESERVES_PROFIT_AND_LOSS = 213L;
    private static final Long PREVIOUS_CAPITAL_AND_RESERVES_SHARE_PREMIUM_ACCOUNT = 214L;
    private static final Long PREVIOUS_CAPITAL_AND_RESERVES_TOTAL_SHAREHOLDERS_FUNDS = 215L;

    private static final Long PREVIOUS_CURRENT_ASSETS_CASH_AT_BANK_AND_IN_HAND = 221L;
    private static final Long PREVIOUS_CURRENT_ASSETS_DEBTORS = 222L;
    private static final Long PREVIOUS_CURRENT_ASSETS_STOCKS = 223L;
    private static final Long PREVIOUS_CURRENT_ASSETS_INVESTMENTS = 224L;
    private static final Long PREVIOUS_CURRENT_ASSETS_TOTAL = 225L;

    private static final Long PREVIOUS_FIXED_ASSETS_TANGIBLE = 231L;
    private static final Long PREVIOUS_FIXED_ASSETS_INVESTMENTS = 232L;
    private static final Long PREVIOUS_FIXED_ASSETS_TOTAL = 233L;

    private static final Long PREVIOUS_OTHER_LIABILITIES_ACCRUALS_AND_DEFERRED_INCOME = 241L;
    private static final Long PREVIOUS_OTHER_LIABILITIES_CREDITORS_AFTER = 242L;
    private static final Long PREVIOUS_OTHER_LIABILITIES_CREDITORS_WITHIN = 243L;
    private static final Long PREVIOUS_OTHER_LIABILITIES_NET_CURRENT_ASSETS = 244L;
    private static final Long PREVIOUS_OTHER_LIABILITIES_PREPAYMENTS_AND_ACCRUED_INCOME = 245L;
    private static final Long PREVIOUS_OTHER_LIABILITIES_PROVISION_FOR_LIABILITIES = 246L;
    private static final Long PREVIOUS_OTHER_LIABILITIES_TOTAL_ASSETS_LESS_CURRENT_LIABILITIES = 247L;
    private static final Long PREVIOUS_OTHER_LIABILITIES_TOTAL_NET_ASSETS = 248L;

    private static final Long PREVIOUS_MEMBERS_FUNDS_PROFIT_AND_LOSS_ACCOUNT = 251L;
    private static final Long PREVIOUS_MEMBERS_FUNDS_TOTAL = 252L;

    private static final String SECTION_477 = "section477";

    private static final String AUDIT_NOT_REQUIRED = "auditNotRequiredByMembers";

    private static final String DIRECTORS_REPONSIBILITY = "directorsResponsibility";

    private static final String SMALL_COMPANIES_REGIME = "smallCompaniesRegime";

    @Test
    @DisplayName("tests that both current and previous period values map to capital and reserve IXBRL model")
    void testApiToCapitalReserveMapsCurrentAndPrevious() {

        BalanceSheetApi currentBalanceSheet = new BalanceSheetApi();
        currentBalanceSheet.setCapitalAndReserves(createCurrentCapitalAndReserves());

        BalanceSheetApi previousBalanceSheet = new BalanceSheetApi();
        previousBalanceSheet.setCapitalAndReserves(createPreviousCapitalAndReserves());

        CurrentPeriodApi currentPeriod = new CurrentPeriodApi();
        currentPeriod.setBalanceSheet(currentBalanceSheet);

        PreviousPeriodApi previousPeriod = new PreviousPeriodApi();
        previousPeriod.setBalanceSheet(previousBalanceSheet);

        CapitalAndReserve capitalAndReserve = apiToBalanceSheetMapper.apiToCapitalAndReserve(currentPeriod, previousPeriod);

        assertNotNull(capitalAndReserve);
        assertEquals(CURRENT_CAPITAL_AND_RESERVES_CALLED_UP_SHARE_CAPITAL, capitalAndReserve.getCalledUpShareCapital().getCurrentAmount());
        assertEquals(CURRENT_CAPITAL_AND_RESERVES_OTHER_RESERVES, capitalAndReserve.getOtherReserves().getCurrentAmount());
        assertEquals(CURRENT_CAPITAL_AND_RESERVES_PROFIT_AND_LOSS, capitalAndReserve.getProfitAndLoss().getCurrentAmount());
        assertEquals(CURRENT_CAPITAL_AND_RESERVES_SHARE_PREMIUM_ACCOUNT, capitalAndReserve.getSharePremiumAccount().getCurrentAmount());
        assertEquals(CURRENT_CAPITAL_AND_RESERVES_TOTAL_SHAREHOLDERS_FUNDS, capitalAndReserve.getTotalShareHoldersFunds().getCurrentAmount());
        assertEquals(PREVIOUS_CAPITAL_AND_RESERVES_CALLED_UP_SHARE_CAPITAL, capitalAndReserve.getCalledUpShareCapital().getPreviousAmount());
        assertEquals(PREVIOUS_CAPITAL_AND_RESERVES_OTHER_RESERVES, capitalAndReserve.getOtherReserves().getPreviousAmount());
        assertEquals(PREVIOUS_CAPITAL_AND_RESERVES_PROFIT_AND_LOSS, capitalAndReserve.getProfitAndLoss().getPreviousAmount());
        assertEquals(PREVIOUS_CAPITAL_AND_RESERVES_SHARE_PREMIUM_ACCOUNT, capitalAndReserve.getSharePremiumAccount().getPreviousAmount());
        assertEquals(PREVIOUS_CAPITAL_AND_RESERVES_TOTAL_SHAREHOLDERS_FUNDS, capitalAndReserve.getTotalShareHoldersFunds().getPreviousAmount());
    }

    @Test
    @DisplayName("tests that current period values map to capital and reserve IXBRL model")
    void testApiToCapitalReserveMapsCurrentOnly() {

        BalanceSheetApi currentBalanceSheet = new BalanceSheetApi();
        currentBalanceSheet.setCapitalAndReserves(createCurrentCapitalAndReserves());

        CurrentPeriodApi currentPeriod = new CurrentPeriodApi();
        currentPeriod.setBalanceSheet(currentBalanceSheet);

        CapitalAndReserve capitalAndReserve = apiToBalanceSheetMapper.apiToCapitalAndReserve(currentPeriod, null);

        assertNotNull(capitalAndReserve);
        assertEquals(CURRENT_CAPITAL_AND_RESERVES_CALLED_UP_SHARE_CAPITAL, capitalAndReserve.getCalledUpShareCapital().getCurrentAmount());
        assertEquals(CURRENT_CAPITAL_AND_RESERVES_OTHER_RESERVES, capitalAndReserve.getOtherReserves().getCurrentAmount());
        assertEquals(CURRENT_CAPITAL_AND_RESERVES_PROFIT_AND_LOSS, capitalAndReserve.getProfitAndLoss().getCurrentAmount());
        assertEquals(CURRENT_CAPITAL_AND_RESERVES_SHARE_PREMIUM_ACCOUNT, capitalAndReserve.getSharePremiumAccount().getCurrentAmount());
        assertEquals(CURRENT_CAPITAL_AND_RESERVES_TOTAL_SHAREHOLDERS_FUNDS, capitalAndReserve.getTotalShareHoldersFunds().getCurrentAmount());
        assertNull(capitalAndReserve.getCalledUpShareCapital().getPreviousAmount());
        assertNull(capitalAndReserve.getOtherReserves().getPreviousAmount());
        assertNull(capitalAndReserve.getProfitAndLoss().getPreviousAmount());
        assertNull(capitalAndReserve.getSharePremiumAccount().getPreviousAmount());
        assertNull(capitalAndReserve.getTotalShareHoldersFunds().getPreviousAmount());
    }

    @Test
    @DisplayName("tests that both current and previous period values map to current assets IXBRL model")
    void testApiToCurrentAssetsMapsCurrentAndPrevious() {

        BalanceSheetApi currentBalanceSheet = new BalanceSheetApi();
        currentBalanceSheet.setCurrentAssets(createCurrentCurrentAssets());

        BalanceSheetApi previousBalanceSheet = new BalanceSheetApi();
        previousBalanceSheet.setCurrentAssets(createPreviousCurrentAssets());

        CurrentPeriodApi currentPeriod = new CurrentPeriodApi();
        currentPeriod.setBalanceSheet(currentBalanceSheet);

        PreviousPeriodApi previousPeriod = new PreviousPeriodApi();
        previousPeriod.setBalanceSheet(previousBalanceSheet);

        CurrentAssets currentAssets = apiToBalanceSheetMapper.apiToCurrentAssets(currentPeriod, previousPeriod);

        assertNotNull(currentAssets);
        assertEquals(CURRENT_CURRENT_ASSETS_CASH_AT_BANK_AND_IN_HAND, currentAssets.getCashAtBankAndInHand().getCurrentAmount());
        assertEquals(CURRENT_CURRENT_ASSETS_DEBTORS, currentAssets.getDebtors().getCurrentAmount());
        assertEquals(CURRENT_CURRENT_ASSETS_STOCKS, currentAssets.getStocks().getCurrentAmount());
        assertEquals(CURRENT_CURRENT_ASSETS_INVESTMENTS, currentAssets.getInvestments().getCurrentAmount());
        assertEquals(CURRENT_CURRENT_ASSETS_TOTAL, currentAssets.getCurrentTotal());
        assertEquals(PREVIOUS_CURRENT_ASSETS_CASH_AT_BANK_AND_IN_HAND, currentAssets.getCashAtBankAndInHand().getPreviousAmount());
        assertEquals(PREVIOUS_CURRENT_ASSETS_DEBTORS, currentAssets.getDebtors().getPreviousAmount());
        assertEquals(PREVIOUS_CURRENT_ASSETS_STOCKS, currentAssets.getStocks().getPreviousAmount());
        assertEquals(PREVIOUS_CURRENT_ASSETS_INVESTMENTS, currentAssets.getInvestments().getPreviousAmount());
        assertEquals(PREVIOUS_CURRENT_ASSETS_TOTAL, currentAssets.getPreviousTotal());
    }

    @Test
    @DisplayName("tests that current period values mapped to current assets IXBRL model")
    void testApiToCurrentAssetsMapsCurrentOnly() {

        BalanceSheetApi currentBalanceSheet = new BalanceSheetApi();
        currentBalanceSheet.setCurrentAssets(createCurrentCurrentAssets());

        CurrentPeriodApi currentPeriod = new CurrentPeriodApi();
        currentPeriod.setBalanceSheet(currentBalanceSheet);

        CurrentAssets currentAssets = apiToBalanceSheetMapper.apiToCurrentAssets(currentPeriod, null);

        assertNotNull(currentAssets);
        assertEquals(CURRENT_CURRENT_ASSETS_CASH_AT_BANK_AND_IN_HAND, currentAssets.getCashAtBankAndInHand().getCurrentAmount());
        assertEquals(CURRENT_CURRENT_ASSETS_DEBTORS, currentAssets.getDebtors().getCurrentAmount());
        assertEquals(CURRENT_CURRENT_ASSETS_STOCKS, currentAssets.getStocks().getCurrentAmount());
        assertEquals(CURRENT_CURRENT_ASSETS_INVESTMENTS, currentAssets.getInvestments().getCurrentAmount());
        assertEquals(CURRENT_CURRENT_ASSETS_TOTAL, currentAssets.getCurrentTotal());
        assertNull(currentAssets.getCashAtBankAndInHand().getPreviousAmount());
        assertNull(currentAssets.getDebtors().getPreviousAmount());
        assertNull(currentAssets.getStocks().getPreviousAmount());
        assertNull(currentAssets.getInvestments().getPreviousAmount());
        assertNull(currentAssets.getPreviousTotal());
    }

    @Test
    @DisplayName("tests that the current and previous period values map to fixed assets IXBRL model")
    void testApiToFixedAssetsMapsCurrentAndPrevious() {

        BalanceSheetApi currentBalanceSheet = new BalanceSheetApi();
        currentBalanceSheet.setFixedAssets(createCurrentFixedAssets());

        BalanceSheetApi previousBalanceSheet = new BalanceSheetApi();
        previousBalanceSheet.setFixedAssets(createPreviousFixedAssets());

        CurrentPeriodApi currentPeriod = new CurrentPeriodApi();
        currentPeriod.setBalanceSheet(currentBalanceSheet);

        PreviousPeriodApi previousPeriod = new PreviousPeriodApi();
        previousPeriod.setBalanceSheet(previousBalanceSheet);

        FixedAssets fixedAssets = apiToBalanceSheetMapper.apiToFixedAssets(currentPeriod, previousPeriod);

        assertNotNull(fixedAssets);
        assertEquals(CURRENT_FIXED_ASSETS_TANGIBLE, fixedAssets.getTangibleAssets().getCurrentAmount());
        assertEquals(CURRENT_FIXED_ASSETS_INVESTMENTS, fixedAssets.getInvestments().getCurrentAmount());
        assertEquals(CURRENT_FIXED_ASSETS_TOTAL, fixedAssets.getTotalFixedAssetsCurrent());
        assertEquals(PREVIOUS_FIXED_ASSETS_TANGIBLE, fixedAssets.getTangibleAssets().getPreviousAmount());
        assertEquals(PREVIOUS_FIXED_ASSETS_INVESTMENTS, fixedAssets.getInvestments().getPreviousAmount());
        assertEquals(PREVIOUS_FIXED_ASSETS_TOTAL, fixedAssets.getTotalFixedAssetsPrevious());
    }

    @Test
    @DisplayName("tests that the current period values map to fixed assets IXBRL model")
    void testApiToFixedAssetsMapsCurrentOnly() {

        BalanceSheetApi currentBalanceSheet = new BalanceSheetApi();
        currentBalanceSheet.setFixedAssets(createCurrentFixedAssets());

        CurrentPeriodApi currentPeriod = new CurrentPeriodApi();
        currentPeriod.setBalanceSheet(currentBalanceSheet);

        FixedAssets fixedAssets = apiToBalanceSheetMapper.apiToFixedAssets(currentPeriod, null);

        assertNotNull(fixedAssets);
        assertEquals(CURRENT_FIXED_ASSETS_TANGIBLE, fixedAssets.getTangibleAssets().getCurrentAmount());
        assertEquals(CURRENT_FIXED_ASSETS_INVESTMENTS, fixedAssets.getInvestments().getCurrentAmount());
        assertEquals(CURRENT_FIXED_ASSETS_TOTAL, fixedAssets.getTotalFixedAssetsCurrent());
        assertNull(fixedAssets.getTangibleAssets().getPreviousAmount());
        assertNull(fixedAssets.getInvestments().getPreviousAmount());
        assertNull(fixedAssets.getTotalFixedAssetsPrevious());
    }

    @Test
    @DisplayName("tests that the current and previous period values map to other liabilities or assets IXBRL model")
    void testApiToOtherLiabilitiesOrAssetsMapsCurrentAndPrevious() {

        BalanceSheetApi currentBalanceSheet = new BalanceSheetApi();
        currentBalanceSheet.setOtherLiabilitiesOrAssets(createCurrentOtherLiabilitiesOrAssets());

        BalanceSheetApi previousBalanceSheet = new BalanceSheetApi();
        previousBalanceSheet.setOtherLiabilitiesOrAssets(createPreviousOtherLiabilitiesOrAssets());

        CurrentPeriodApi currentPeriod = new CurrentPeriodApi();
        currentPeriod.setBalanceSheet(currentBalanceSheet);

        PreviousPeriodApi previousPeriod = new PreviousPeriodApi();
        previousPeriod.setBalanceSheet(previousBalanceSheet);

        OtherLiabilitiesOrAssets otherLiabilitiesOrAssets = apiToBalanceSheetMapper.apiToOtherLiabilitiesOrAssets(
                currentPeriod, previousPeriod);

        assertNotNull(otherLiabilitiesOrAssets);
        assertEquals(CURRENT_OTHER_LIABILITIES_TOTAL_NET_ASSETS, otherLiabilitiesOrAssets.getCurrentTotalNetAssets());
        assertEquals(CURRENT_OTHER_LIABILITIES_ACCRUALS_AND_DEFERRED_INCOME, otherLiabilitiesOrAssets.getAccrualsAndDeferredIncome().getCurrentAmount());
        assertEquals(CURRENT_OTHER_LIABILITIES_CREDITORS_AFTER, otherLiabilitiesOrAssets.getCreditorsAmountsFallingDueAfterMoreThanOneYear().getCurrentAmount());
        assertEquals(CURRENT_OTHER_LIABILITIES_CREDITORS_WITHIN, otherLiabilitiesOrAssets.getCreditorsAmountsFallingDueWithinOneYear().getCurrentAmount());
        assertEquals(CURRENT_OTHER_LIABILITIES_NET_CURRENT_ASSETS, otherLiabilitiesOrAssets.getNetCurrentAssets().getCurrentAmount());
        assertEquals(CURRENT_OTHER_LIABILITIES_PREPAYMENTS_AND_ACCRUED_INCOME, otherLiabilitiesOrAssets.getPrepaymentsAndAccruedIncome().getCurrentAmount());
        assertEquals(CURRENT_OTHER_LIABILITIES_TOTAL_ASSETS_LESS_CURRENT_LIABILITIES, otherLiabilitiesOrAssets.getTotalAssetsLessCurrentLiabilities().getCurrentAmount());
        assertEquals(CURRENT_OTHER_LIABILITIES_PROVISION_FOR_LIABILITIES, otherLiabilitiesOrAssets.getProvisionForLiabilities().getCurrentAmount());
        assertEquals(PREVIOUS_OTHER_LIABILITIES_TOTAL_NET_ASSETS, otherLiabilitiesOrAssets.getPreviousTotalNetAssets());
        assertEquals(PREVIOUS_OTHER_LIABILITIES_ACCRUALS_AND_DEFERRED_INCOME, otherLiabilitiesOrAssets.getAccrualsAndDeferredIncome().getPreviousAmount());
        assertEquals(PREVIOUS_OTHER_LIABILITIES_CREDITORS_AFTER, otherLiabilitiesOrAssets.getCreditorsAmountsFallingDueAfterMoreThanOneYear().getPreviousAmount());
        assertEquals(PREVIOUS_OTHER_LIABILITIES_CREDITORS_WITHIN, otherLiabilitiesOrAssets.getCreditorsAmountsFallingDueWithinOneYear().getPreviousAmount());
        assertEquals(PREVIOUS_OTHER_LIABILITIES_NET_CURRENT_ASSETS, otherLiabilitiesOrAssets.getNetCurrentAssets().getPreviousAmount());
        assertEquals(PREVIOUS_OTHER_LIABILITIES_PREPAYMENTS_AND_ACCRUED_INCOME, otherLiabilitiesOrAssets.getPrepaymentsAndAccruedIncome().getPreviousAmount());
        assertEquals(PREVIOUS_OTHER_LIABILITIES_TOTAL_ASSETS_LESS_CURRENT_LIABILITIES, otherLiabilitiesOrAssets.getTotalAssetsLessCurrentLiabilities().getPreviousAmount());
        assertEquals(PREVIOUS_OTHER_LIABILITIES_PROVISION_FOR_LIABILITIES, otherLiabilitiesOrAssets.getProvisionForLiabilities().getPreviousAmount());
    }

    @Test
    @DisplayName("tests that the current period values map to other liabilities or assets IXBRL model")
    void testApiToOtherLiabilitiesOrAssetsMapsCurrentOnly() {

        BalanceSheetApi currentBalanceSheet = new BalanceSheetApi();
        currentBalanceSheet.setOtherLiabilitiesOrAssets(createCurrentOtherLiabilitiesOrAssets());

        CurrentPeriodApi currentPeriod = new CurrentPeriodApi();
        currentPeriod.setBalanceSheet(currentBalanceSheet);

        OtherLiabilitiesOrAssets otherLiabilitiesOrAssets = apiToBalanceSheetMapper.apiToOtherLiabilitiesOrAssets(
                currentPeriod, null);

        assertNotNull(otherLiabilitiesOrAssets);
        assertEquals(CURRENT_OTHER_LIABILITIES_TOTAL_NET_ASSETS, otherLiabilitiesOrAssets.getCurrentTotalNetAssets());
        assertEquals(CURRENT_OTHER_LIABILITIES_ACCRUALS_AND_DEFERRED_INCOME, otherLiabilitiesOrAssets.getAccrualsAndDeferredIncome().getCurrentAmount());
        assertEquals(CURRENT_OTHER_LIABILITIES_CREDITORS_AFTER, otherLiabilitiesOrAssets.getCreditorsAmountsFallingDueAfterMoreThanOneYear().getCurrentAmount());
        assertEquals(CURRENT_OTHER_LIABILITIES_CREDITORS_WITHIN, otherLiabilitiesOrAssets.getCreditorsAmountsFallingDueWithinOneYear().getCurrentAmount());
        assertEquals(CURRENT_OTHER_LIABILITIES_NET_CURRENT_ASSETS, otherLiabilitiesOrAssets.getNetCurrentAssets().getCurrentAmount());
        assertEquals(CURRENT_OTHER_LIABILITIES_PREPAYMENTS_AND_ACCRUED_INCOME, otherLiabilitiesOrAssets.getPrepaymentsAndAccruedIncome().getCurrentAmount());
        assertEquals(CURRENT_OTHER_LIABILITIES_TOTAL_ASSETS_LESS_CURRENT_LIABILITIES, otherLiabilitiesOrAssets.getTotalAssetsLessCurrentLiabilities().getCurrentAmount());
        assertEquals(CURRENT_OTHER_LIABILITIES_PROVISION_FOR_LIABILITIES, otherLiabilitiesOrAssets.getProvisionForLiabilities().getCurrentAmount());
        assertNull(otherLiabilitiesOrAssets.getPreviousTotalNetAssets());
        assertNull(otherLiabilitiesOrAssets.getAccrualsAndDeferredIncome().getPreviousAmount());
        assertNull(otherLiabilitiesOrAssets.getCreditorsAmountsFallingDueAfterMoreThanOneYear().getPreviousAmount());
        assertNull(otherLiabilitiesOrAssets.getCreditorsAmountsFallingDueWithinOneYear().getPreviousAmount());
        assertNull(otherLiabilitiesOrAssets.getNetCurrentAssets().getPreviousAmount());
        assertNull(otherLiabilitiesOrAssets.getPrepaymentsAndAccruedIncome().getPreviousAmount());
        assertNull(otherLiabilitiesOrAssets.getTotalAssetsLessCurrentLiabilities().getPreviousAmount());
        assertNull(otherLiabilitiesOrAssets.getProvisionForLiabilities().getPreviousAmount());
    }

    @Test
    @DisplayName("tests that the current and previous period values map to called up share capital not paid IXBRL model")
    void testApiToCalledUpShareCapitalNotPaidMapsCurrentAndPrevious() {

        BalanceSheetApi currentBalanceSheet = new BalanceSheetApi();
        currentBalanceSheet.setCalledUpShareCapitalNotPaid(CURRENT_CALLED_UP_SHARE_CAPITAL_NOT_PAID);

        BalanceSheetApi previousBalanceSheet = new BalanceSheetApi();
        previousBalanceSheet.setCalledUpShareCapitalNotPaid(PREVIOUS_CALLED_UP_SHARE_CAPITAL_NOT_PAID);

        CurrentPeriodApi currentPeriod = new CurrentPeriodApi();
        currentPeriod.setBalanceSheet(currentBalanceSheet);

        PreviousPeriodApi previousPeriod = new PreviousPeriodApi();
        previousPeriod.setBalanceSheet(previousBalanceSheet);

        CalledUpSharedCapitalNotPaid calledUpSharedCapitalNotPaid = apiToBalanceSheetMapper
                .apiToCalledUpSharedCapitalNotPaid(currentPeriod, previousPeriod);

        assertNotNull(calledUpSharedCapitalNotPaid);
        assertEquals(CURRENT_CALLED_UP_SHARE_CAPITAL_NOT_PAID, calledUpSharedCapitalNotPaid.getCurrentAmount());
        assertEquals(PREVIOUS_CALLED_UP_SHARE_CAPITAL_NOT_PAID, calledUpSharedCapitalNotPaid.getPreviousAmount());
    }

    @Test
    @DisplayName("tests that the current period values map to called up share capital not paid IXBRL model")
    void testApiToCalledUpShareCapitalNotPaidMapsCurrentOnly() {

        BalanceSheetApi currentBalanceSheet = new BalanceSheetApi();
        currentBalanceSheet.setCalledUpShareCapitalNotPaid(CURRENT_CALLED_UP_SHARE_CAPITAL_NOT_PAID);

        CurrentPeriodApi currentPeriod = new CurrentPeriodApi();
        currentPeriod.setBalanceSheet(currentBalanceSheet);

        CalledUpSharedCapitalNotPaid calledUpSharedCapitalNotPaid = apiToBalanceSheetMapper
                .apiToCalledUpSharedCapitalNotPaid(currentPeriod, null);

        assertNotNull(calledUpSharedCapitalNotPaid);
        assertEquals(CURRENT_CALLED_UP_SHARE_CAPITAL_NOT_PAID, calledUpSharedCapitalNotPaid.getCurrentAmount());
        assertNull(calledUpSharedCapitalNotPaid.getPreviousAmount());
    }

    @Test
    @DisplayName("tests that the current and previous period values map to members' funds IXBRL model")
    void testApiToMembersFundsMapsCurrentAndPrevious() {

        BalanceSheetApi currentBalanceSheet = new BalanceSheetApi();
        currentBalanceSheet.setMembersFunds(createCurrentMembersFunds());

        BalanceSheetApi previousBalanceSheet = new BalanceSheetApi();
        previousBalanceSheet.setMembersFunds(createPreviousMembersFunds());

        CurrentPeriodApi currentPeriod = new CurrentPeriodApi();
        currentPeriod.setBalanceSheet(currentBalanceSheet);

        PreviousPeriodApi previousPeriod = new PreviousPeriodApi();
        previousPeriod.setBalanceSheet(previousBalanceSheet);

        MembersFunds membersFunds = apiToBalanceSheetMapper.apiToMembersFunds(currentPeriod, previousPeriod);

        assertNotNull(membersFunds);
        assertEquals(CURRENT_MEMBERS_FUNDS_PROFIT_AND_LOSS_ACCOUNT, membersFunds.getProfitAndLossAccount().getCurrentAmount());
        assertEquals(CURRENT_MEMBERS_FUNDS_TOTAL, membersFunds.getTotalMembersFunds().getCurrentAmount());
        assertEquals(PREVIOUS_MEMBERS_FUNDS_PROFIT_AND_LOSS_ACCOUNT, membersFunds.getProfitAndLossAccount().getPreviousAmount());
        assertEquals(PREVIOUS_MEMBERS_FUNDS_TOTAL, membersFunds.getTotalMembersFunds().getPreviousAmount());
    }

    @Test
    @DisplayName("tests that the current period values map to members' funds IXBRL model")
    void testApiToMembersFundsMapsCurrentOnly() {

        BalanceSheetApi currentBalanceSheet = new BalanceSheetApi();
        currentBalanceSheet.setMembersFunds(createCurrentMembersFunds());

        CurrentPeriodApi currentPeriod = new CurrentPeriodApi();
        currentPeriod.setBalanceSheet(currentBalanceSheet);

        MembersFunds membersFunds = apiToBalanceSheetMapper.apiToMembersFunds(currentPeriod, null);

        assertNotNull(membersFunds);
        assertEquals(CURRENT_MEMBERS_FUNDS_PROFIT_AND_LOSS_ACCOUNT, membersFunds.getProfitAndLossAccount().getCurrentAmount());
        assertEquals(CURRENT_MEMBERS_FUNDS_TOTAL, membersFunds.getTotalMembersFunds().getCurrentAmount());
        assertNull(membersFunds.getProfitAndLossAccount().getPreviousAmount());
        assertNull(membersFunds.getTotalMembersFunds().getPreviousAmount());
    }

    @Test
    @DisplayName("tests that the balance sheet statements map to balance sheet statements IXBRL model")
    void testApiToBalanceSheetStatementsMapsCorrectly() {

        BalanceSheetStatements balanceSheetStatements =
                apiToBalanceSheetMapper.apiToStatements(createBalanceSheetStatements());

        assertNotNull(balanceSheetStatements);
        assertEquals(SECTION_477, balanceSheetStatements.getSection477());
        assertEquals(AUDIT_NOT_REQUIRED, balanceSheetStatements.getAuditNotRequiredByMembers());
        assertEquals(DIRECTORS_REPONSIBILITY, balanceSheetStatements.getDirectorsResponsibility());
        assertEquals(SMALL_COMPANIES_REGIME, balanceSheetStatements.getSmallCompaniesRegime());
    }

    private OtherLiabilitiesOrAssetsApi createCurrentOtherLiabilitiesOrAssets() {

        OtherLiabilitiesOrAssetsApi otherLiabilitiesOrAssets = new OtherLiabilitiesOrAssetsApi();
        otherLiabilitiesOrAssets.setTotalNetAssets(CURRENT_OTHER_LIABILITIES_TOTAL_NET_ASSETS);
        otherLiabilitiesOrAssets.setAccrualsAndDeferredIncome(CURRENT_OTHER_LIABILITIES_ACCRUALS_AND_DEFERRED_INCOME);
        otherLiabilitiesOrAssets.setCreditorsAfterOneYear(CURRENT_OTHER_LIABILITIES_CREDITORS_AFTER);
        otherLiabilitiesOrAssets.setCreditorsDueWithinOneYear(CURRENT_OTHER_LIABILITIES_CREDITORS_WITHIN);
        otherLiabilitiesOrAssets.setNetCurrentAssets(CURRENT_OTHER_LIABILITIES_NET_CURRENT_ASSETS);
        otherLiabilitiesOrAssets.setPrepaymentsAndAccruedIncome(CURRENT_OTHER_LIABILITIES_PREPAYMENTS_AND_ACCRUED_INCOME);
        otherLiabilitiesOrAssets.setTotalAssetsLessCurrentLiabilities(CURRENT_OTHER_LIABILITIES_TOTAL_ASSETS_LESS_CURRENT_LIABILITIES);
        otherLiabilitiesOrAssets.setProvisionForLiabilities(CURRENT_OTHER_LIABILITIES_PROVISION_FOR_LIABILITIES);

        return otherLiabilitiesOrAssets;
    }

    private OtherLiabilitiesOrAssetsApi createPreviousOtherLiabilitiesOrAssets() {

        OtherLiabilitiesOrAssetsApi otherLiabilitiesOrAssets = new OtherLiabilitiesOrAssetsApi();
        otherLiabilitiesOrAssets.setTotalNetAssets(PREVIOUS_OTHER_LIABILITIES_TOTAL_NET_ASSETS);
        otherLiabilitiesOrAssets.setAccrualsAndDeferredIncome(PREVIOUS_OTHER_LIABILITIES_ACCRUALS_AND_DEFERRED_INCOME);
        otherLiabilitiesOrAssets.setCreditorsAfterOneYear(PREVIOUS_OTHER_LIABILITIES_CREDITORS_AFTER);
        otherLiabilitiesOrAssets.setCreditorsDueWithinOneYear(PREVIOUS_OTHER_LIABILITIES_CREDITORS_WITHIN);
        otherLiabilitiesOrAssets.setNetCurrentAssets(PREVIOUS_OTHER_LIABILITIES_NET_CURRENT_ASSETS);
        otherLiabilitiesOrAssets.setPrepaymentsAndAccruedIncome(PREVIOUS_OTHER_LIABILITIES_PREPAYMENTS_AND_ACCRUED_INCOME);
        otherLiabilitiesOrAssets.setTotalAssetsLessCurrentLiabilities(PREVIOUS_OTHER_LIABILITIES_TOTAL_ASSETS_LESS_CURRENT_LIABILITIES);
        otherLiabilitiesOrAssets.setProvisionForLiabilities(PREVIOUS_OTHER_LIABILITIES_PROVISION_FOR_LIABILITIES);

        return otherLiabilitiesOrAssets;
    }

    private FixedAssetsApi createCurrentFixedAssets() {

        FixedAssetsApi fixedAssets = new FixedAssetsApi();
        fixedAssets.setTangible(CURRENT_FIXED_ASSETS_TANGIBLE);
        fixedAssets.setInvestments(CURRENT_FIXED_ASSETS_INVESTMENTS);
        fixedAssets.setTotal(CURRENT_FIXED_ASSETS_TOTAL);

        return fixedAssets;
    }

    private FixedAssetsApi createPreviousFixedAssets() {

        FixedAssetsApi fixedAssets = new FixedAssetsApi();
        fixedAssets.setTangible(PREVIOUS_FIXED_ASSETS_TANGIBLE);
        fixedAssets.setInvestments(PREVIOUS_FIXED_ASSETS_INVESTMENTS);
        fixedAssets.setTotal(PREVIOUS_FIXED_ASSETS_TOTAL);

        return fixedAssets;
    }

    private CurrentAssetsApi createCurrentCurrentAssets() {

        CurrentAssetsApi currentAssets = new CurrentAssetsApi();
        currentAssets.setCashAtBankAndInHand(CURRENT_CURRENT_ASSETS_CASH_AT_BANK_AND_IN_HAND);
        currentAssets.setDebtors(CURRENT_CURRENT_ASSETS_DEBTORS);
        currentAssets.setStocks(CURRENT_CURRENT_ASSETS_STOCKS);
        currentAssets.setInvestments(CURRENT_CURRENT_ASSETS_INVESTMENTS);
        currentAssets.setTotal(CURRENT_CURRENT_ASSETS_TOTAL);

        return currentAssets;
    }

    private CurrentAssetsApi createPreviousCurrentAssets() {

        CurrentAssetsApi currentAssets = new CurrentAssetsApi();
        currentAssets.setCashAtBankAndInHand(PREVIOUS_CURRENT_ASSETS_CASH_AT_BANK_AND_IN_HAND);
        currentAssets.setDebtors(PREVIOUS_CURRENT_ASSETS_DEBTORS);
        currentAssets.setStocks(PREVIOUS_CURRENT_ASSETS_STOCKS);
        currentAssets.setInvestments(PREVIOUS_CURRENT_ASSETS_INVESTMENTS);
        currentAssets.setTotal(PREVIOUS_CURRENT_ASSETS_TOTAL);

        return currentAssets;
    }

    private CapitalAndReservesApi createCurrentCapitalAndReserves() {

        CapitalAndReservesApi capitalAndReserves = new CapitalAndReservesApi();
        capitalAndReserves.setCalledUpShareCapital(CURRENT_CAPITAL_AND_RESERVES_CALLED_UP_SHARE_CAPITAL);
        capitalAndReserves.setOtherReserves(CURRENT_CAPITAL_AND_RESERVES_OTHER_RESERVES);
        capitalAndReserves.setProfitAndLoss(CURRENT_CAPITAL_AND_RESERVES_PROFIT_AND_LOSS);
        capitalAndReserves.setSharePremiumAccount(CURRENT_CAPITAL_AND_RESERVES_SHARE_PREMIUM_ACCOUNT);
        capitalAndReserves.setTotalShareholdersFunds(CURRENT_CAPITAL_AND_RESERVES_TOTAL_SHAREHOLDERS_FUNDS);

        return capitalAndReserves;
    }

    private CapitalAndReservesApi createPreviousCapitalAndReserves() {

        CapitalAndReservesApi capitalAndReserves = new CapitalAndReservesApi();
        capitalAndReserves.setCalledUpShareCapital(PREVIOUS_CAPITAL_AND_RESERVES_CALLED_UP_SHARE_CAPITAL);
        capitalAndReserves.setOtherReserves(PREVIOUS_CAPITAL_AND_RESERVES_OTHER_RESERVES);
        capitalAndReserves.setProfitAndLoss(PREVIOUS_CAPITAL_AND_RESERVES_PROFIT_AND_LOSS);
        capitalAndReserves.setSharePremiumAccount(PREVIOUS_CAPITAL_AND_RESERVES_SHARE_PREMIUM_ACCOUNT);
        capitalAndReserves.setTotalShareholdersFunds(PREVIOUS_CAPITAL_AND_RESERVES_TOTAL_SHAREHOLDERS_FUNDS);

        return capitalAndReserves;
    }

    private MembersFundsApi createCurrentMembersFunds() {

        MembersFundsApi membersFunds = new MembersFundsApi();
        membersFunds.setProfitAndLossAccount(CURRENT_MEMBERS_FUNDS_PROFIT_AND_LOSS_ACCOUNT);
        membersFunds.setTotalMembersFunds(CURRENT_MEMBERS_FUNDS_TOTAL);

        return membersFunds;
    }

    private MembersFundsApi createPreviousMembersFunds() {

        MembersFundsApi membersFunds = new MembersFundsApi();
        membersFunds.setProfitAndLossAccount(PREVIOUS_MEMBERS_FUNDS_PROFIT_AND_LOSS_ACCOUNT);
        membersFunds.setTotalMembersFunds(PREVIOUS_MEMBERS_FUNDS_TOTAL);

        return membersFunds;
    }

    private BalanceSheetStatementsApi createBalanceSheetStatements() {

        BalanceSheetLegalStatementsApi legalStatements = new BalanceSheetLegalStatementsApi();
        legalStatements.setSection477(SECTION_477);
        legalStatements.setAuditNotRequiredByMembers(AUDIT_NOT_REQUIRED);
        legalStatements.setDirectorsResponsibility(DIRECTORS_REPONSIBILITY);
        legalStatements.setSmallCompaniesRegime(SMALL_COMPANIES_REGIME);

        BalanceSheetStatementsApi balanceSheetStatements = new BalanceSheetStatementsApi();
        balanceSheetStatements.setLegalStatements(legalStatements);

        return balanceSheetStatements;
    }
}
