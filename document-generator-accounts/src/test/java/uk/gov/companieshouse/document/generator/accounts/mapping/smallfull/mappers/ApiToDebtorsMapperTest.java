package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.model.accounts.smallfull.Debtors.CurrentPeriod;
import uk.gov.companieshouse.api.model.accounts.smallfull.Debtors.PreviousPeriod;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.debtors.Debtors;


@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApiToDebtorsMapperTest {

    private static final String DETAILS = "Details";
    private static final Long GREATER_THAN_ONE_YEAR_CURRENT = 1L;
    private static final Long GREATER_THAN_ONE_YEAR_PREVIOUS = 10L;
    private static final Long OTHER_DEBTORS_CURRENT = 2L;
    private static final Long PREPAYMENTS_AND_ACCRUED_INCOME_CURRENT = 3L;
    private static final Long TRADE_DEBTORS_CURRENT = 4L;
    private static final Long TOTAL_CURRENT = 10L;
    private static final Long OTHER_DEBTORS_PREVIOUS = 20L;
    private static final Long PREPAYMENTS_AND_ACCRUED_INCOME_PREVIOUS = 30L;
    private static final Long TRADE_DEBTORS_PREVIOUS = 40L;
    private static final Long TOTAL_PREVIOUS = 100L;

    @Test
    @DisplayName("tests debtors API values map to debtors IXBRL model")
    void testApiToCompanyMaps() {

        Debtors debtors = ApiToDebtorsMapper.INSTANCE.apiToDebtors(createCurrentPeriodDebtors(),
                createPreviousPeriodDebtors());

        assertNotNull(debtors);
        assertEquals(GREATER_THAN_ONE_YEAR_CURRENT, debtors.getGreaterThanOneYear().getCurrentAmount());
        assertEquals(GREATER_THAN_ONE_YEAR_PREVIOUS, debtors.getGreaterThanOneYear().getPreviousAmount());
        assertEquals(DETAILS, debtors.getDetails());
        assertEquals(OTHER_DEBTORS_CURRENT, debtors.getOtherDebtors().getCurrentAmount());
        assertEquals(OTHER_DEBTORS_PREVIOUS, debtors.getOtherDebtors().getPreviousAmount());
        assertEquals(PREPAYMENTS_AND_ACCRUED_INCOME_CURRENT, debtors.getPrepaymentsAndAccruedIncome().getCurrentAmount());
        assertEquals(PREPAYMENTS_AND_ACCRUED_INCOME_PREVIOUS, debtors.getPrepaymentsAndAccruedIncome().getPreviousAmount());
        assertEquals(TRADE_DEBTORS_CURRENT, debtors.getTradeDebtors().getCurrentAmount());
        assertEquals(TRADE_DEBTORS_PREVIOUS, debtors.getTradeDebtors().getPreviousAmount());
        assertEquals(TOTAL_CURRENT, debtors.getTotal().getCurrentAmount());
        assertEquals(TOTAL_PREVIOUS, debtors.getTotal().getPreviousAmount());

    }

    private CurrentPeriod createCurrentPeriodDebtors() {

        CurrentPeriod debtorsCurrentPeriod = new CurrentPeriod();
        debtorsCurrentPeriod.setDetails(DETAILS);
        debtorsCurrentPeriod.setGreaterThanOneYear(GREATER_THAN_ONE_YEAR_CURRENT);
        debtorsCurrentPeriod.setOtherDebtors(OTHER_DEBTORS_CURRENT);
        debtorsCurrentPeriod.setPrepaymentsAndAccruedIncome(PREPAYMENTS_AND_ACCRUED_INCOME_CURRENT);
        debtorsCurrentPeriod.setTradeDebtors(TRADE_DEBTORS_CURRENT);
        debtorsCurrentPeriod.setTotal(TOTAL_CURRENT);

        return debtorsCurrentPeriod;
    }

    private PreviousPeriod createPreviousPeriodDebtors() {

        PreviousPeriod debtorsPreviousPeriod = new PreviousPeriod();
        debtorsPreviousPeriod.setGreaterThanOneYear(GREATER_THAN_ONE_YEAR_PREVIOUS);
        debtorsPreviousPeriod.setOtherDebtors(OTHER_DEBTORS_PREVIOUS);
        debtorsPreviousPeriod.setPrepaymentsAndAccruedIncome(PREPAYMENTS_AND_ACCRUED_INCOME_PREVIOUS);
        debtorsPreviousPeriod.setTradeDebtors(TRADE_DEBTORS_PREVIOUS);
        debtorsPreviousPeriod.setTotal(TOTAL_PREVIOUS);

        return debtorsPreviousPeriod;
    }
}
