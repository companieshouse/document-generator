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

import java.time.LocalDate;

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

    @Test
    @DisplayName("tests the mapping of the smallFull IXBRL model with a current and previous period")
    void testSmallFullMapperCurrentAndPreious() {

        SmallFullApiData smallFullApiData = createSmallFullData(true);

        SmallFullAccountIxbrl smallFullAccountIxbrl = SmallFullIXBRLMapper.INSTANCE.mapSmallFullIXBRLModel(smallFullApiData);

        assertNotNull(smallFullAccountIxbrl);
        assertApprovalsMapped(smallFullAccountIxbrl.getApprovalDate(), smallFullAccountIxbrl.getApprovalName());
        assertBalanceSheetMapped(smallFullAccountIxbrl.getBalanceSheet(), true);
        assertCompanyProfileMapped(smallFullAccountIxbrl.getCompany());
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
    }

    private SmallFullApiData createSmallFullData(boolean isSameYearFiling) {

        SmallFullApiData smallFullApiData = new SmallFullApiData();

        smallFullApiData.setApproval(createApproval());
        smallFullApiData.setCompanyProfile(createCompanyProfile(isSameYearFiling));
        smallFullApiData.setCurrentPeriod(createCurrentPeriod());
        if(isSameYearFiling == true) {
            smallFullApiData.setPreviousPeriod(createPreviousPeriod());
        }

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


}
