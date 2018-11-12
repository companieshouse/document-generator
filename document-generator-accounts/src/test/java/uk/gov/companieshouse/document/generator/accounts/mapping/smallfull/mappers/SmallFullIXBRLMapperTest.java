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

    @Test
    @DisplayName("tests the mapping of the smallFull IXBRL model with a current and previous period")
    public void testSmallFullMapperCurrentAndPreious() {

        SmallFullApiData smallFullApiData = createSmallFullData(true);

        SmallFullAccountIxbrl smallFullAccountIxbrl = SmallFullIXBRLMapper.INSTANCE.mapSmallFullIXBRLModel(smallFullApiData);

        assertNotNull(smallFullAccountIxbrl);
        assertApprovalsMapped(smallFullAccountIxbrl.getApprovalDate(), smallFullAccountIxbrl.getApprovalName());
        assertBalanceSheetMapped(smallFullAccountIxbrl.getBalanceSheet(), true);
        assertCompanyProfileMapped(smallFullAccountIxbrl.getCompany());
    }

    @Test
    @DisplayName("tests the mapping of the smallFull IXBRL model with a current period Only")
    public void testSmallFullMapperCurrentOny() {

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
        smallFullApiData.setCurrentPeriod(setCurrentPeriod());
        if(isSameYearFiling == true) {
            smallFullApiData.setPreviousPeriod(setPreviousPeriod());
        }

        return smallFullApiData;
    }

    private CurrentPeriodApi setCurrentPeriod() {

        CurrentPeriodApi currentPeriod = new CurrentPeriodApi();

        currentPeriod.setBalanceSheetApi(setBalanceSheetValues());

        return currentPeriod;
    }

    private PreviousPeriodApi setPreviousPeriod() {

        PreviousPeriodApi previousPeriod = new PreviousPeriodApi();

        previousPeriod.setBalanceSheet(setBalanceSheetValues());

        return previousPeriod;
    }

    private ApprovalApi createApproval() {

        ApprovalApi approval = new ApprovalApi();
        approval.setDate(LocalDate.of(2018, 12, 31));
        approval.setName("name");

        return approval;
    }

    private CompanyProfileApi createCompanyProfile(boolean isMultiYearFiling) {

        CompanyProfileApi companyProfile = new CompanyProfileApi();
        companyProfile.setCompanyName("companyName");
        companyProfile.setCompanyNumber("companyNumber");
        companyProfile.setJurisdiction("jurisdiction");
        setAccountsFilingDates(isMultiYearFiling, companyProfile);

        return companyProfile;
    }

    private CompanyProfileApi setAccountsFilingDates(boolean isMultiYearFiling, CompanyProfileApi companyProfileApi ) {

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

    private BalanceSheetApi setBalanceSheetValues() {

        BalanceSheetApi balanceSheet = new BalanceSheetApi();

        CapitalAndReservesApi capitalAndReserves = new CapitalAndReservesApi();
        capitalAndReserves.setCalledUpShareCapital(new Long(VALUE_ONE));
        capitalAndReserves.setOtherReserves(new Long(VALUE_TWO));
        capitalAndReserves.setProfitAndLoss(new Long(VALUE_THREE));
        capitalAndReserves.setSharePremiumAccount(new Long(VALUE_ONE));
        capitalAndReserves.setTotalShareholdersFund(new Long(VALUE_TWO));

        CurrentAssetsApi currentAssets = new CurrentAssetsApi();
        currentAssets.setCashInBankAndInHand(new Long(VALUE_ONE));
        currentAssets.setDebtors(new Long(VALUE_TWO));
        currentAssets.setStocks(new Long(VALUE_THREE));
        currentAssets.setTotal(new Long(VALUE_ONE));

        FixedAssetsApi fixedAssets = new FixedAssetsApi();
        fixedAssets.setTangible(new Long(VALUE_ONE));
        fixedAssets.setTotal(new Long(VALUE_TWO));

        OtherLiabilitiesOrAssetsApi otherLiabilitiesOrAssets = new OtherLiabilitiesOrAssetsApi();
        otherLiabilitiesOrAssets.setTotalNetAssets(new Long(VALUE_ONE));
        otherLiabilitiesOrAssets.setAccrualsAndDeferredIncome(new Long(VALUE_TWO));
        otherLiabilitiesOrAssets.setCreditorsAfterOneYear(new Long(VALUE_THREE));
        otherLiabilitiesOrAssets.setCreditorsDueWithinOneYear(new Long(VALUE_ONE));
        otherLiabilitiesOrAssets.setNetCurrentAssets(new Long(VALUE_TWO));
        otherLiabilitiesOrAssets.setPrepaymentsAndAccruedIncome(new Long(VALUE_THREE));
        otherLiabilitiesOrAssets.setTotalAssetsLessCurrentLiabilities(new Long(VALUE_ONE));
        otherLiabilitiesOrAssets.setProvisionForLiabilities(new Long(VALUE_TWO));

        balanceSheet.setCapitalAndReservesApi(capitalAndReserves);
        balanceSheet.setCurrentAssetsApi(currentAssets);
        balanceSheet.setFixedAssetsApi(fixedAssets);
        balanceSheet.setOtherLiabilitiesOrAssetsApi(otherLiabilitiesOrAssets);
        balanceSheet.setCalledUpShareCapitalNotPaid(VALUE_ONE);

        return balanceSheet;
    }

    private void assertCompanyProfileMapped(Company company) {

        assertNotNull(company);
        assertEquals("companyName", company.getCompanyName());
        assertEquals("companyNumber", company.getCompanyNumber());
        assertEquals("jurisdiction", company.getJurisdiction());
    }

    private void assertBalanceSheetMapped(BalanceSheet balanceSheet, boolean isMultiYearFiling) {

        assertNotNull(balanceSheet.getCapitalAndReserve());
        assertEquals(new Long(VALUE_ONE), balanceSheet.getCapitalAndReserve().getCalledUpShareCapital().getCurrentAmount());
        assertEquals(new Long(VALUE_TWO), balanceSheet.getCapitalAndReserve().getOtherReserves().getCurrentAmount());
        assertEquals(new Long(VALUE_THREE), balanceSheet.getCapitalAndReserve().getProfitAndLoss().getCurrentAmount());
        assertEquals(new Long(VALUE_ONE), balanceSheet.getCapitalAndReserve().getSharePremiumAccount().getCurrentAmount());
        assertEquals(new Long(VALUE_TWO), balanceSheet.getCapitalAndReserve().getTotalShareHoldersFund().getCurrentAmount());

        assertNotNull(balanceSheet.getCurrentAssets());
        assertEquals(new Long(VALUE_ONE), balanceSheet.getCurrentAssets().getCashAtBankAndInHand().getCurrentAmount());
        assertEquals(new Long(VALUE_TWO), balanceSheet.getCurrentAssets().getDebtors().getCurrentAmount());
        assertEquals(new Long(VALUE_THREE), balanceSheet.getCurrentAssets().getStocks().getCurrentAmount());
        assertEquals(new Long(VALUE_ONE), balanceSheet.getCurrentAssets().getCurrentTotal());

        assertNotNull(balanceSheet.getFixedAssets());
        assertEquals(new Long(VALUE_ONE), balanceSheet.getFixedAssets().getTangibleAssets().getCurrentAmount());
        assertEquals(new Long(VALUE_TWO), balanceSheet.getFixedAssets().getTotalFixedAssetsCurrent());

        assertNotNull(balanceSheet.getOtherLiabilitiesOrAssets());
        assertEquals(new Long(VALUE_ONE), balanceSheet.getOtherLiabilitiesOrAssets().getCurrentTotalNetAssets());
        assertEquals(new Long(VALUE_TWO), balanceSheet.getOtherLiabilitiesOrAssets().getAccrualsAndDeferredIncome().getCurrentAmount());
        assertEquals(new Long(VALUE_THREE), balanceSheet.getOtherLiabilitiesOrAssets().getCreditorsAmountsFallingDueAfterMoreThanOneYear().getCurrentAmount());
        assertEquals(new Long(VALUE_ONE), balanceSheet.getOtherLiabilitiesOrAssets().getCreditorsAmountsFallingDueWithinOneYear().getCurrentAmount());
        assertEquals(new Long(VALUE_TWO), balanceSheet.getOtherLiabilitiesOrAssets().getNetCurrentAssets().getCurrentAmount());
        assertEquals(new Long(VALUE_THREE), balanceSheet.getOtherLiabilitiesOrAssets().getPrepaymentsAndAccruedIncome().getCurrentAmount());
        assertEquals(new Long(VALUE_ONE), balanceSheet.getOtherLiabilitiesOrAssets().getTotalAssetsLessCurrentLiabilities().getCurrentAmount());
        assertEquals(new Long(VALUE_TWO), balanceSheet.getOtherLiabilitiesOrAssets().getProvisionForLiabilities().getCurrentAmount());

        assertNotNull(balanceSheet.getCalledUpSharedCapitalNotPaid());
        assertEquals(new Long(VALUE_ONE), balanceSheet.getCalledUpSharedCapitalNotPaid().getCurrentAmount());

        if (isMultiYearFiling == true) {

            assertEquals(new Long(VALUE_ONE), balanceSheet.getCapitalAndReserve().getCalledUpShareCapital().getPreviousAmount());
            assertEquals(new Long(VALUE_TWO), balanceSheet.getCapitalAndReserve().getOtherReserves().getPreviousAmount());
            assertEquals(new Long(VALUE_THREE), balanceSheet.getCapitalAndReserve().getProfitAndLoss().getPreviousAmount());
            assertEquals(new Long(VALUE_ONE), balanceSheet.getCapitalAndReserve().getSharePremiumAccount().getPreviousAmount());
            assertEquals(new Long(VALUE_TWO), balanceSheet.getCapitalAndReserve().getTotalShareHoldersFund().getPreviousAmount());

            assertEquals(new Long(VALUE_ONE), balanceSheet.getCurrentAssets().getCashAtBankAndInHand().getPreviousAmount());
            assertEquals(new Long(VALUE_TWO), balanceSheet.getCurrentAssets().getDebtors().getPreviousAmount());
            assertEquals(new Long(VALUE_THREE), balanceSheet.getCurrentAssets().getStocks().getPreviousAmount());
            assertEquals(new Long(VALUE_ONE), balanceSheet.getCurrentAssets().getPreviousTotal());

            assertEquals(new Long(VALUE_ONE), balanceSheet.getFixedAssets().getTangibleAssets().getPreviousAmount());
            assertEquals(new Long(VALUE_TWO), balanceSheet.getFixedAssets().getTotalFixedAssetsPrevious());

            assertEquals(new Long(VALUE_ONE), balanceSheet.getOtherLiabilitiesOrAssets().getPreviousTotalNetAssets());
            assertEquals(new Long(VALUE_TWO), balanceSheet.getOtherLiabilitiesOrAssets().getAccrualsAndDeferredIncome().getPreviousAmount());
            assertEquals(new Long(VALUE_THREE), balanceSheet.getOtherLiabilitiesOrAssets().getCreditorsAmountsFallingDueAfterMoreThanOneYear().getPreviousAmount());
            assertEquals(new Long(VALUE_ONE), balanceSheet.getOtherLiabilitiesOrAssets().getCreditorsAmountsFallingDueWithinOneYear().getPreviousAmount());
            assertEquals(new Long(VALUE_TWO), balanceSheet.getOtherLiabilitiesOrAssets().getNetCurrentAssets().getPreviousAmount());
            assertEquals(new Long(VALUE_THREE), balanceSheet.getOtherLiabilitiesOrAssets().getPrepaymentsAndAccruedIncome().getPreviousAmount());
            assertEquals(new Long(VALUE_ONE), balanceSheet.getOtherLiabilitiesOrAssets().getTotalAssetsLessCurrentLiabilities().getPreviousAmount());
            assertEquals(new Long(VALUE_TWO), balanceSheet.getOtherLiabilitiesOrAssets().getProvisionForLiabilities().getPreviousAmount());

            assertEquals(new Long(VALUE_ONE), balanceSheet.getCalledUpSharedCapitalNotPaid().getPreviousAmount());
        }
    }

    private void assertApprovalsMapped(String approvalDate, String approvalName) {

        assertEquals("2018-12-31", approvalDate);
        assertEquals("name", approvalName);
    }


}
