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
import uk.gov.companieshouse.api.model.accounts.smallfull.OtherLiabilitiesOrAssetsApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.PreviousPeriodApi;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.BalanceSheetStatements;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.CalledUpSharedCapitalNotPaid;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.capitalandreserves.CapitalAndReserve;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.currentassets.CurrentAssets;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.fixedassets.FixedAssets;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.otherliabilitiesandassets.OtherLiabilitiesOrAssets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApiToBalanceSheetMapperTest {

    private ApiToBalanceSheetMapper apiToBalanceSheetMapper = new ApiToBalanceSheetMapperImpl();

    private static final Long VALUE_ONE = 100L;

    private static final Long VALUE_TWO = 200L;

    private static final Long VALUE_THREE = 300L;

    private static final String SECTION_477 = "section477";

    private static final String AUDIT_NOT_REQUIRED = "auditNotRequiredByMembers";

    private static final String DIRECTORS_REPONSIBILITY = "directorsResponsibility";

    private static final String SMALL_COMPANIES_REGIME = "smallCompaniesRegime";

    @Test
    @DisplayName("tests that both current and previous period values map to capital and reserve IXBRL model")
    void testApiToCapitalReserveMapsCurrentAndPrevious() {

        CapitalAndReserve capitalAndReserve = apiToBalanceSheetMapper.apiToCapitalAndReserve(createCurrentPeriod(), createPreviousPeriod());

        assertNotNull(capitalAndReserve);
        assertEquals(new Long(VALUE_ONE), capitalAndReserve.getCalledUpShareCapital().getCurrentAmount());
        assertEquals(new Long(VALUE_TWO), capitalAndReserve.getOtherReserves().getCurrentAmount());
        assertEquals(new Long(VALUE_THREE), capitalAndReserve.getProfitAndLoss().getCurrentAmount());
        assertEquals(new Long(VALUE_ONE), capitalAndReserve.getSharePremiumAccount().getCurrentAmount());
        assertEquals(new Long(VALUE_TWO), capitalAndReserve.getTotalShareHoldersFunds().getCurrentAmount());
        assertEquals(new Long(VALUE_ONE), capitalAndReserve.getCalledUpShareCapital().getPreviousAmount());
        assertEquals(new Long(VALUE_TWO), capitalAndReserve.getOtherReserves().getPreviousAmount());
        assertEquals(new Long(VALUE_THREE), capitalAndReserve.getProfitAndLoss().getPreviousAmount());
        assertEquals(new Long(VALUE_ONE), capitalAndReserve.getSharePremiumAccount().getPreviousAmount());
        assertEquals(new Long(VALUE_TWO), capitalAndReserve.getTotalShareHoldersFunds().getPreviousAmount());
    }

    @Test
    @DisplayName("tests that current period values map to capital and reserve IXBRL model")
    void testApiToCapitalReserveMapsCurrentOnly() {

        CapitalAndReserve capitalAndReserve = apiToBalanceSheetMapper.apiToCapitalAndReserve(createCurrentPeriod(), null);

        assertNotNull(capitalAndReserve);
        assertEquals(new Long(VALUE_ONE), capitalAndReserve.getCalledUpShareCapital().getCurrentAmount());
        assertEquals(new Long(VALUE_TWO), capitalAndReserve.getOtherReserves().getCurrentAmount());
        assertEquals(new Long(VALUE_THREE), capitalAndReserve.getProfitAndLoss().getCurrentAmount());
        assertEquals(new Long(VALUE_ONE), capitalAndReserve.getSharePremiumAccount().getCurrentAmount());
        assertEquals(new Long(VALUE_TWO), capitalAndReserve.getTotalShareHoldersFunds().getCurrentAmount());
    }

    @Test
    @DisplayName("tests that both current and previous period values map to current assets IXBRL model")
    void testApiToCurrentAssetsMapsCurrentAndPrevious() {

        CurrentAssets currentAssets = apiToBalanceSheetMapper.apiToCurrentAssets(createCurrentPeriod(), createPreviousPeriod());

        assertNotNull(currentAssets);
        assertEquals(new Long(VALUE_ONE), currentAssets.getCashAtBankAndInHand().getCurrentAmount());
        assertEquals(new Long(VALUE_TWO), currentAssets.getDebtors().getCurrentAmount());
        assertEquals(new Long(VALUE_THREE), currentAssets.getStocks().getCurrentAmount());
        assertEquals(new Long(VALUE_TWO), currentAssets.getInvestments().getCurrentAmount());
        assertEquals(new Long(VALUE_ONE), currentAssets.getCurrentTotal());
        assertEquals(new Long(VALUE_ONE), currentAssets.getCashAtBankAndInHand().getPreviousAmount());
        assertEquals(new Long(VALUE_TWO), currentAssets.getDebtors().getPreviousAmount());
        assertEquals(new Long(VALUE_THREE), currentAssets.getStocks().getPreviousAmount());
        assertEquals(new Long(VALUE_TWO), currentAssets.getInvestments().getPreviousAmount());
        assertEquals(new Long(VALUE_ONE), currentAssets.getPreviousTotal());
    }

    @Test
    @DisplayName("tests that current period values mapped to current assets IXBRL model")
    void testApiToCurrentAssetsMapsCurrentOnly() {

        CurrentAssets currentAssets = apiToBalanceSheetMapper.apiToCurrentAssets(createCurrentPeriod(), null);

        assertNotNull(currentAssets);
        assertEquals(new Long(VALUE_ONE), currentAssets.getCashAtBankAndInHand().getCurrentAmount());
        assertEquals(new Long(VALUE_TWO), currentAssets.getDebtors().getCurrentAmount());
        assertEquals(new Long(VALUE_THREE), currentAssets.getStocks().getCurrentAmount());
        assertEquals(new Long(VALUE_ONE), currentAssets.getCurrentTotal());
    }

    @Test
    @DisplayName("tests that the current and previous period values map to fixed assets IXBRL model")
    void testApiToFixedAssetsMapsCurrentAndPrevious() {

        FixedAssets fixedAssets = apiToBalanceSheetMapper.apiToFixedAssets(createCurrentPeriod(), createPreviousPeriod());

        assertNotNull(fixedAssets);
        assertEquals(new Long(VALUE_ONE), fixedAssets.getTangibleAssets().getCurrentAmount());
        assertEquals(new Long(VALUE_TWO), fixedAssets.getInvestments().getCurrentAmount());
        assertEquals(new Long(VALUE_THREE), fixedAssets.getTotalFixedAssetsCurrent());
        assertEquals(new Long(VALUE_ONE), fixedAssets.getTangibleAssets().getPreviousAmount());
        assertEquals(new Long(VALUE_TWO), fixedAssets.getInvestments().getPreviousAmount());
        assertEquals(new Long(VALUE_THREE), fixedAssets.getTotalFixedAssetsPrevious());
    }

    @Test
    @DisplayName("tests that the current period values map to fixed assets IXBRL model")
    void testApiToFixedAssetsMapsCurrentOnly() {

        FixedAssets fixedAssets = apiToBalanceSheetMapper.apiToFixedAssets(createCurrentPeriod(), null);

        assertNotNull(fixedAssets);
        assertEquals(new Long(VALUE_ONE), fixedAssets.getTangibleAssets().getCurrentAmount());
        assertEquals(new Long(VALUE_TWO), fixedAssets.getInvestments().getCurrentAmount());
        assertEquals(new Long(VALUE_THREE), fixedAssets.getTotalFixedAssetsCurrent());
    }

    @Test
    @DisplayName("tests that the current and previous period values map to other liabilities or assets IXBRL model")
    void testApiToOtherLiabilitiesOrAssetsMapsCurrentAndPrevious() {

        OtherLiabilitiesOrAssets otherLiabilitiesOrAssets = apiToBalanceSheetMapper.apiToOtherLiabilitiesOrAssets(
                createCurrentPeriod(), createPreviousPeriod());

        assertNotNull(otherLiabilitiesOrAssets);
        assertEquals(new Long(VALUE_ONE), otherLiabilitiesOrAssets.getCurrentTotalNetAssets());
        assertEquals(new Long(VALUE_TWO), otherLiabilitiesOrAssets.getAccrualsAndDeferredIncome().getCurrentAmount());
        assertEquals(new Long(VALUE_THREE), otherLiabilitiesOrAssets.getCreditorsAmountsFallingDueAfterMoreThanOneYear().getCurrentAmount());
        assertEquals(new Long(VALUE_ONE), otherLiabilitiesOrAssets.getCreditorsAmountsFallingDueWithinOneYear().getCurrentAmount());
        assertEquals(new Long(VALUE_TWO), otherLiabilitiesOrAssets.getNetCurrentAssets().getCurrentAmount());
        assertEquals(new Long(VALUE_THREE), otherLiabilitiesOrAssets.getPrepaymentsAndAccruedIncome().getCurrentAmount());
        assertEquals(new Long(VALUE_ONE), otherLiabilitiesOrAssets.getTotalAssetsLessCurrentLiabilities().getCurrentAmount());
        assertEquals(new Long(VALUE_TWO), otherLiabilitiesOrAssets.getProvisionForLiabilities().getCurrentAmount());
        assertEquals(new Long(VALUE_ONE), otherLiabilitiesOrAssets.getPreviousTotalNetAssets());
        assertEquals(new Long(VALUE_TWO), otherLiabilitiesOrAssets.getAccrualsAndDeferredIncome().getPreviousAmount());
        assertEquals(new Long(VALUE_THREE), otherLiabilitiesOrAssets.getCreditorsAmountsFallingDueAfterMoreThanOneYear().getPreviousAmount());
        assertEquals(new Long(VALUE_ONE), otherLiabilitiesOrAssets.getCreditorsAmountsFallingDueWithinOneYear().getPreviousAmount());
        assertEquals(new Long(VALUE_TWO), otherLiabilitiesOrAssets.getNetCurrentAssets().getPreviousAmount());
        assertEquals(new Long(VALUE_THREE), otherLiabilitiesOrAssets.getPrepaymentsAndAccruedIncome().getPreviousAmount());
        assertEquals(new Long(VALUE_ONE), otherLiabilitiesOrAssets.getTotalAssetsLessCurrentLiabilities().getPreviousAmount());
        assertEquals(new Long(VALUE_TWO), otherLiabilitiesOrAssets.getProvisionForLiabilities().getPreviousAmount());

    }

    @Test
    @DisplayName("tests that the current period values map to other liabilities or assets IXBRL model")
    void testApiToOtherLiabilitiesOrAssetsMapsCurrentOnly() {

        OtherLiabilitiesOrAssets otherLiabilitiesOrAssets = apiToBalanceSheetMapper.apiToOtherLiabilitiesOrAssets(
                createCurrentPeriod(), null);

        assertNotNull(otherLiabilitiesOrAssets);
        assertEquals(new Long(VALUE_ONE), otherLiabilitiesOrAssets.getCurrentTotalNetAssets());
        assertEquals(new Long(VALUE_TWO), otherLiabilitiesOrAssets.getAccrualsAndDeferredIncome().getCurrentAmount());
        assertEquals(new Long(VALUE_THREE), otherLiabilitiesOrAssets.getCreditorsAmountsFallingDueAfterMoreThanOneYear().getCurrentAmount());
        assertEquals(new Long(VALUE_ONE), otherLiabilitiesOrAssets.getCreditorsAmountsFallingDueWithinOneYear().getCurrentAmount());
        assertEquals(new Long(VALUE_TWO), otherLiabilitiesOrAssets.getNetCurrentAssets().getCurrentAmount());
        assertEquals(new Long(VALUE_THREE), otherLiabilitiesOrAssets.getPrepaymentsAndAccruedIncome().getCurrentAmount());
        assertEquals(new Long(VALUE_ONE), otherLiabilitiesOrAssets.getTotalAssetsLessCurrentLiabilities().getCurrentAmount());
        assertEquals(new Long(VALUE_TWO), otherLiabilitiesOrAssets.getProvisionForLiabilities().getCurrentAmount());
    }

    @Test
    @DisplayName("tests that the current and previous period values map to called up share capital not paid IXBRL model")
    void testApiToCalledUpShareCapitalNotPaidMapsCurrentAndPrevious() {

        CalledUpSharedCapitalNotPaid calledUpSharedCapitalNotPaid = apiToBalanceSheetMapper
                .apiToCalledUpSharedCapitalNotPaid(createCurrentPeriod(), createPreviousPeriod());

        assertNotNull(calledUpSharedCapitalNotPaid);
        assertEquals(new Long(VALUE_ONE), calledUpSharedCapitalNotPaid.getCurrentAmount());
        assertEquals(new Long(VALUE_ONE), calledUpSharedCapitalNotPaid.getPreviousAmount());
    }

    @Test
    @DisplayName("tests that the current period values map to called up share capital not paid IXBRL model")
    void testApiToCalledUpShareCapitalNotPaidMapsCurrentOnly() {

        CalledUpSharedCapitalNotPaid calledUpSharedCapitalNotPaid = apiToBalanceSheetMapper
                .apiToCalledUpSharedCapitalNotPaid(createCurrentPeriod(), null);

        assertNotNull(calledUpSharedCapitalNotPaid);
        assertEquals(new Long(VALUE_ONE), calledUpSharedCapitalNotPaid.getCurrentAmount());
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

    private CurrentPeriodApi createCurrentPeriod() {

        CurrentPeriodApi currentPeriod = new CurrentPeriodApi();

        currentPeriod.setBalanceSheet(createBalanceSheetValues());

        return currentPeriod;
    }

    private PreviousPeriodApi createPreviousPeriod() {

        PreviousPeriodApi previousPeriod = new PreviousPeriodApi();

        previousPeriod.setBalanceSheet(createBalanceSheetValues());

        return previousPeriod;
    }

    private BalanceSheetApi createBalanceSheetValues() {

        BalanceSheetApi balanceSheet = new BalanceSheetApi();

        balanceSheet.setCapitalAndReserves(createCapitalAndReservesApiData());
        balanceSheet.setCurrentAssets(createCurrentAssetsApiData());
        balanceSheet.setFixedAssets(createFixedAssetsApiData());
        balanceSheet.setOtherLiabilitiesOrAssets(createOtherLiabilitiesOrAssetsApiData());
        balanceSheet.setCalledUpShareCapitalNotPaid(VALUE_ONE);

        return balanceSheet;
    }

    private OtherLiabilitiesOrAssetsApi createOtherLiabilitiesOrAssetsApiData() {

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

    private FixedAssetsApi createFixedAssetsApiData() {

        FixedAssetsApi fixedAssets = new FixedAssetsApi();
        fixedAssets.setTangible(new Long(VALUE_ONE));
        fixedAssets.setInvestments(new Long(VALUE_TWO));
        fixedAssets.setTotal(new Long(VALUE_THREE));

        return fixedAssets;
    }

    private CurrentAssetsApi createCurrentAssetsApiData() {

        CurrentAssetsApi currentAssets = new CurrentAssetsApi();
        currentAssets.setCashAtBankAndInHand(new Long(VALUE_ONE));
        currentAssets.setDebtors(new Long(VALUE_TWO));
        currentAssets.setStocks(new Long(VALUE_THREE));
        currentAssets.setInvestments(new Long (VALUE_TWO));
        currentAssets.setTotal(new Long(VALUE_ONE));

        return currentAssets;
    }

    private CapitalAndReservesApi createCapitalAndReservesApiData() {

        CapitalAndReservesApi capitalAndReserves = new CapitalAndReservesApi();
        capitalAndReserves.setCalledUpShareCapital(new Long(VALUE_ONE));
        capitalAndReserves.setOtherReserves(new Long(VALUE_TWO));
        capitalAndReserves.setProfitAndLoss(new Long(VALUE_THREE));
        capitalAndReserves.setSharePremiumAccount(new Long(VALUE_ONE));
        capitalAndReserves.setTotalShareholdersFunds(new Long(VALUE_TWO));

        return capitalAndReserves;
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
