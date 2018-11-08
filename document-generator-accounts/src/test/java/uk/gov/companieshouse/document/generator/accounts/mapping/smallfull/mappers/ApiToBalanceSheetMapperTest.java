package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.mappers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.model.accounts.smallfull.BalanceSheetApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.CapitalAndReservesApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.CurrentPeriodApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.PreviousPeriodApi;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.capitalandreserves.CapitalAndReserve;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApiToBalanceSheetMapperTest {

    @Test
    @DisplayName("tests that both current and previous period values mapped to capital and reserve IXBRL model")
    public void testApiToCapitalReserveMapsCurrentAndPrevious() {

        CurrentPeriodApi currentPeriod = setCurrentPeriod();
        PreviousPeriodApi previousPeriod = setPreviousPeriod();

        CapitalAndReserve capitalAndReserve = ApiToBalanceSheetMapper.INSTANCE.apiToCapitalAndReserve(currentPeriod, previousPeriod);

        assertNotNull(capitalAndReserve);
        assertEquals(new Long(100), capitalAndReserve.getCalledUpShareCapital().getCurrentAmount());
        assertEquals(new Long(200), capitalAndReserve.getOtherReserves().getCurrentAmount());
        assertEquals(new Long(300), capitalAndReserve.getProfitAndLoss().getCurrentAmount());
        assertEquals(new Long(400), capitalAndReserve.getSharePremiumAccount().getCurrentAmount());
        assertEquals(new Long(500), capitalAndReserve.getTotalShareHoldersFund().getCurrentAmount());
        assertEquals(new Long(50), capitalAndReserve.getCalledUpShareCapital().getPreviousAmount());
        assertEquals(new Long(150), capitalAndReserve.getOtherReserves().getPreviousAmount());
        assertEquals(new Long(250), capitalAndReserve.getProfitAndLoss().getPreviousAmount());
        assertEquals(new Long(350), capitalAndReserve.getSharePremiumAccount().getPreviousAmount());
        assertEquals(new Long(450), capitalAndReserve.getTotalShareHoldersFund().getPreviousAmount());
    }

    @Test
    @DisplayName("tests that current period values mapped to capital and reserve IXBRL model")
    public void testApiToCapitalReserveMapsCurrentOnly() {

        CurrentPeriodApi currentPeriod = setCurrentPeriod();
        PreviousPeriodApi previousPeriod = setPreviousPeriod();

        CapitalAndReserve capitalAndReserve = ApiToBalanceSheetMapper.INSTANCE.apiToCapitalAndReserve(currentPeriod, previousPeriod);

        assertNotNull(capitalAndReserve);
        assertEquals(new Long(100), capitalAndReserve.getCalledUpShareCapital().getCurrentAmount());
        assertEquals(new Long(200), capitalAndReserve.getOtherReserves().getCurrentAmount());
        assertEquals(new Long(300), capitalAndReserve.getProfitAndLoss().getCurrentAmount());
        assertEquals(new Long(400), capitalAndReserve.getSharePremiumAccount().getCurrentAmount());
        assertEquals(new Long(500), capitalAndReserve.getTotalShareHoldersFund().getCurrentAmount());
    }

    private PreviousPeriodApi setPreviousPeriod() {

        PreviousPeriodApi previousPeriod = new PreviousPeriodApi();
        BalanceSheetApi balanceSheetPrevious = new BalanceSheetApi();

        CapitalAndReservesApi capitalAndReservesCurrent = new CapitalAndReservesApi();
        capitalAndReservesCurrent.setCalledUpShareCapital(new Long(50));
        capitalAndReservesCurrent.setOtherReserves(new Long(150));
        capitalAndReservesCurrent.setProfitAndLoss(new Long(250));
        capitalAndReservesCurrent.setSharePremiumAccount(new Long(350));
        capitalAndReservesCurrent.setTotalShareholdersFund(new Long(450));

        balanceSheetPrevious.setCapitalAndReservesApi(capitalAndReservesCurrent);

        previousPeriod.setBalanceSheet(balanceSheetPrevious);

        return previousPeriod;

    }

    private CurrentPeriodApi setCurrentPeriod() {

        CurrentPeriodApi currentPeriod = new CurrentPeriodApi();
        BalanceSheetApi balanceSheetCurrent = new BalanceSheetApi();

        CapitalAndReservesApi capitalAndReservesCurrent = new CapitalAndReservesApi();
        capitalAndReservesCurrent.setCalledUpShareCapital(new Long(100));
        capitalAndReservesCurrent.setOtherReserves(new Long(200));
        capitalAndReservesCurrent.setProfitAndLoss(new Long(300));
        capitalAndReservesCurrent.setSharePremiumAccount(new Long(400));
        capitalAndReservesCurrent.setTotalShareholdersFund(new Long(500));

        balanceSheetCurrent.setCapitalAndReservesApi(capitalAndReservesCurrent);

        currentPeriod.setBalanceSheetApi(balanceSheetCurrent);

        return currentPeriod;
    }
}
