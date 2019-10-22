package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.mappers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import uk.gov.companieshouse.api.model.accounts.profitandloss.ProfitAndLossApi;
import uk.gov.companieshouse.api.model.accounts.profitandloss.GrossProfitOrLoss;
import uk.gov.companieshouse.api.model.accounts.profitandloss.OperatingProfitOrLoss;
import uk.gov.companieshouse.api.model.accounts.profitandloss.ProfitOrLossForFinancialYear;
import uk.gov.companieshouse.api.model.accounts.profitandloss.ProfitOrLossBeforeTax;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.profitandloss.ProfitAndLoss;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApiToProfitAndLossMapperTest {

    private ApiToProfitAndLossMapper apiToProfitAndLossMapper = new ApiToProfitAndLossMapperImpl();

    private static final Long COST_OF_SALES_CURRENT = 101L;
    private static final Long COST_OF_SALES_PREVIOUS = 101L;
    private static final Long GROSS_TOTAL_CURRENT = 101L;
    private static final Long GROSS_TOTAL_PREVIOUS = 101L;
    private static final Long TURNOVER_CURRENT = 101L;
    private static final Long TURNOVER_PREVIOUS = 101L;
    private static final Long DISTRIBUTION_COSTS_CURRENT = 101L;
    private static final Long DISTRIBUTION_COSTS_PREVIOUS = 101L;
    private static final Long ADMINISTRATIVE_EXPENSES_CURRENT = 101L;
    private static final Long ADMINISTRATIVE_EXPENSES_PREVIOUS = 101L;
    private static final Long OTHER_OPERATING_INCOME_CURRENT = 101L;
    private static final Long OTHER_OPERATING_INCOME_PREVIOUS = 101L;
    private static final Long OPERATING_TOTAL_CURRENT = 101L;
    private static final Long OPERATING_TOTAL_PREVIOUS = 101L;
    private static final Long INTEREST_RECEIVABLE_AND_SIMILAR_INCOME_CURRENT = 101L;
    private static final Long INTEREST_RECEIVABLE_AND_SIMILAR_INCOME_PREVIOUS = 101L;
    private static final Long INTEREST_PAYABLE_AND_SIMILAR_INCOME_CURRENT = 101L;
    private static final Long INTEREST_PAYABLE_AND_SIMILAR_INCOME_PREVIOUS = 101L;
    private static final Long TOTAL_PROFIT_OR_LOSS_BEFORE_TAX_CURRENT = 101L;
    private static final Long TOTAL_PROFIT_OR_LOSS_BEFORE_TAX_PREVIOUS = 101L;
    private static final Long TAX_CURRENT = 101L;
    private static final Long TAX_PREVIOUS = 101L;
    private static final Long TOTAL_PROFIT_OR_LOSS_FOR_FINANCIAL_YEAR_CURRENT = 101L;
    private static final Long TOTAL_PROFIT_OR_LOSS_FOR_FINANCIAL_YEAR_PREVIOUS = 101L;

    @Test
    @DisplayName("tests populated profit and loss API maps to profit and loss IXBRL model")
    void testApiToProfitAndLossMaps() {

        ProfitAndLoss profitAndLoss = apiToProfitAndLossMapper.apiToProfitAndLoss(createCurrentPeriodProfitAndLoss(),createPreviousPeriodProfitAndLoss());

        assertNotNull(profitAndLoss);
        assertNotNull(profitAndLoss.getGrossProfitOrLoss());
        assertNotNull(profitAndLoss.getOperatingProfitOrLoss());
        assertNotNull(profitAndLoss.getProfitOrLossForFinancialYear());
        assertNotNull(profitAndLoss.getProfitOrLossBeforeTax());

        assertGrossProfitOrLoss(profitAndLoss);
        assertOperatingProfitOrLoss(profitAndLoss);
        assertProfitOrLossBeforeTax(profitAndLoss);
        assertProfitOrLossForFinancialYear(profitAndLoss);
    }
    @Test
    @DisplayName("tests profit and loss API with null previous period maps to null profit and loss  IXBRL model")
    void testApiToProfitAndLossMapsWhenPreviousPeriodsNull(){
        ProfitAndLoss profitAndLoss = apiToProfitAndLossMapper.apiToProfitAndLoss(createCurrentPeriodProfitAndLoss(),null);
        assertNotNull(profitAndLoss);
        assertNotNull(profitAndLoss.getGrossProfitOrLoss());
        assertNotNull(profitAndLoss.getOperatingProfitOrLoss());
        assertNotNull(profitAndLoss.getProfitOrLossForFinancialYear());
        assertNotNull(profitAndLoss.getProfitOrLossBeforeTax());
        assertEquals(COST_OF_SALES_CURRENT,profitAndLoss.getGrossProfitOrLoss().getCostOfSales().getCurrentAmount());
        assertEquals(null,profitAndLoss.getGrossProfitOrLoss().getCostOfSales().getPreviousAmount());
        assertEquals(GROSS_TOTAL_CURRENT,profitAndLoss.getGrossProfitOrLoss().getGrossTotal().getCurrentAmount());
        assertEquals(null,profitAndLoss.getGrossProfitOrLoss().getGrossTotal().getPreviousAmount());
        assertEquals(TURNOVER_CURRENT,profitAndLoss.getGrossProfitOrLoss().getTurnover().getCurrentAmount());
        assertEquals(null,profitAndLoss.getGrossProfitOrLoss().getTurnover().getPreviousAmount());
        assertEquals(DISTRIBUTION_COSTS_CURRENT,profitAndLoss.getOperatingProfitOrLoss().getDistributionCosts().getCurrentAmount());
        assertEquals(null,profitAndLoss.getOperatingProfitOrLoss().getDistributionCosts().getPreviousAmount());
        assertEquals(ADMINISTRATIVE_EXPENSES_CURRENT,profitAndLoss.getOperatingProfitOrLoss().getAdministrativeExpenses().getCurrentAmount());
        assertEquals(null,profitAndLoss.getOperatingProfitOrLoss().getAdministrativeExpenses().getPreviousAmount());
        assertEquals(OTHER_OPERATING_INCOME_CURRENT,profitAndLoss.getOperatingProfitOrLoss().getOtherOperatingIncome().getCurrentAmount());
        assertEquals(null,profitAndLoss.getOperatingProfitOrLoss().getOtherOperatingIncome().getPreviousAmount());
        assertEquals(OPERATING_TOTAL_CURRENT,profitAndLoss.getOperatingProfitOrLoss().getOperatingTotal().getCurrentAmount());
        assertEquals(null,profitAndLoss.getOperatingProfitOrLoss().getOperatingTotal().getPreviousAmount());
        assertEquals(INTEREST_RECEIVABLE_AND_SIMILAR_INCOME_CURRENT,profitAndLoss.getProfitOrLossBeforeTax().getInterestReceivableAndSimilarIncome().getCurrentAmount());
        assertEquals(null,profitAndLoss.getProfitOrLossBeforeTax().getInterestReceivableAndSimilarIncome().getPreviousAmount());
        assertEquals(INTEREST_PAYABLE_AND_SIMILAR_INCOME_CURRENT,profitAndLoss.getProfitOrLossBeforeTax().getInterestPayableAndSimilarCharges().getCurrentAmount());
        assertEquals(null,profitAndLoss.getProfitOrLossBeforeTax().getInterestPayableAndSimilarCharges().getPreviousAmount());
        assertEquals(TOTAL_PROFIT_OR_LOSS_BEFORE_TAX_CURRENT,profitAndLoss.getProfitOrLossBeforeTax().getTotalProfitOrLossBeforeTax().getCurrentAmount());
        assertEquals(null,profitAndLoss.getProfitOrLossBeforeTax().getTotalProfitOrLossBeforeTax().getPreviousAmount());
        assertEquals(TAX_CURRENT,profitAndLoss.getProfitOrLossForFinancialYear().getTax().getCurrentAmount());
        assertEquals(null,profitAndLoss.getProfitOrLossForFinancialYear().getTax().getPreviousAmount());
        assertEquals(TOTAL_PROFIT_OR_LOSS_FOR_FINANCIAL_YEAR_CURRENT,profitAndLoss.getProfitOrLossForFinancialYear().getTotalProfitOrLossForFinancialYear().getCurrentAmount());
        assertEquals(null,profitAndLoss.getProfitOrLossForFinancialYear().getTotalProfitOrLossForFinancialYear().getPreviousAmount());

    }
    @Test
    @DisplayName("tests profit and loss API with null current period maps to null profit and loss IXBRL model")
    void testApiToProfitAndLossMapsWhenCurrentPeriodsNull() {
        ProfitAndLoss profitAndLoss = apiToProfitAndLossMapper.apiToProfitAndLoss(null,createPreviousPeriodProfitAndLoss());
        assertNotNull(profitAndLoss);
        assertNotNull(profitAndLoss.getGrossProfitOrLoss());
        assertNotNull(profitAndLoss.getOperatingProfitOrLoss());
        assertNotNull(profitAndLoss.getProfitOrLossForFinancialYear());
        assertNotNull(profitAndLoss.getProfitOrLossBeforeTax());
        assertEquals(null,profitAndLoss.getGrossProfitOrLoss().getCostOfSales().getCurrentAmount());
        assertEquals(COST_OF_SALES_PREVIOUS,profitAndLoss.getGrossProfitOrLoss().getCostOfSales().getPreviousAmount());
        assertEquals(null,profitAndLoss.getGrossProfitOrLoss().getGrossTotal().getCurrentAmount());
        assertEquals(GROSS_TOTAL_PREVIOUS,profitAndLoss.getGrossProfitOrLoss().getGrossTotal().getPreviousAmount());
        assertEquals(null,profitAndLoss.getGrossProfitOrLoss().getTurnover().getCurrentAmount());
        assertEquals(TURNOVER_PREVIOUS,profitAndLoss.getGrossProfitOrLoss().getTurnover().getPreviousAmount());
        assertEquals(null,profitAndLoss.getOperatingProfitOrLoss().getDistributionCosts().getCurrentAmount());
        assertEquals(DISTRIBUTION_COSTS_PREVIOUS,profitAndLoss.getOperatingProfitOrLoss().getDistributionCosts().getPreviousAmount());
        assertEquals(null,profitAndLoss.getOperatingProfitOrLoss().getAdministrativeExpenses().getCurrentAmount());
        assertEquals(ADMINISTRATIVE_EXPENSES_PREVIOUS,profitAndLoss.getOperatingProfitOrLoss().getAdministrativeExpenses().getPreviousAmount());
        assertEquals(null,profitAndLoss.getOperatingProfitOrLoss().getOtherOperatingIncome().getCurrentAmount());
        assertEquals(OTHER_OPERATING_INCOME_PREVIOUS,profitAndLoss.getOperatingProfitOrLoss().getOtherOperatingIncome().getPreviousAmount());
        assertEquals(null,profitAndLoss.getOperatingProfitOrLoss().getOperatingTotal().getCurrentAmount());
        assertEquals(OPERATING_TOTAL_PREVIOUS,profitAndLoss.getOperatingProfitOrLoss().getOperatingTotal().getPreviousAmount());
        assertEquals(null,profitAndLoss.getProfitOrLossBeforeTax().getInterestReceivableAndSimilarIncome().getCurrentAmount());
        assertEquals(INTEREST_RECEIVABLE_AND_SIMILAR_INCOME_PREVIOUS,profitAndLoss.getProfitOrLossBeforeTax().getInterestReceivableAndSimilarIncome().getPreviousAmount());
        assertEquals(null,profitAndLoss.getProfitOrLossBeforeTax().getInterestPayableAndSimilarCharges().getCurrentAmount());
        assertEquals(INTEREST_PAYABLE_AND_SIMILAR_INCOME_PREVIOUS,profitAndLoss.getProfitOrLossBeforeTax().getInterestPayableAndSimilarCharges().getPreviousAmount());
        assertEquals(null,profitAndLoss.getProfitOrLossBeforeTax().getTotalProfitOrLossBeforeTax().getCurrentAmount());
        assertEquals(TOTAL_PROFIT_OR_LOSS_BEFORE_TAX_PREVIOUS,profitAndLoss.getProfitOrLossBeforeTax().getTotalProfitOrLossBeforeTax().getPreviousAmount());
        assertEquals(null,profitAndLoss.getProfitOrLossForFinancialYear().getTax().getCurrentAmount());
        assertEquals(TAX_PREVIOUS,profitAndLoss.getProfitOrLossForFinancialYear().getTax().getPreviousAmount());
        assertEquals(null,profitAndLoss.getProfitOrLossForFinancialYear().getTotalProfitOrLossForFinancialYear().getCurrentAmount());
        assertEquals(TOTAL_PROFIT_OR_LOSS_FOR_FINANCIAL_YEAR_PREVIOUS,profitAndLoss.getProfitOrLossForFinancialYear().getTotalProfitOrLossForFinancialYear().getPreviousAmount());
    }

    @Test
    @DisplayName("tests profit and loss API with null periods maps to null profit and loss IXBRL model")
    void testApiToProfitAndLossMapsWhenBothPeriodsNull() {

        ProfitAndLoss profitAndLoss = apiToProfitAndLossMapper.apiToProfitAndLoss(null,null);

        assertNull(profitAndLoss);
    }

    @Test
    @DisplayName("tests populated profit and loss API maps to profit and loss IXBRL model with null gross profit or loss")
    void testApiToProfitAndLossMapsWithNullGrossProfitOrLoss() {
        ProfitAndLoss profitAndLoss = apiToProfitAndLossMapper.apiToProfitAndLoss(createCurrentPeriodProfitAndLossWithNullGrossProfitOrLoss(),createPreviousPeriodProfitAndLossWithNullGrossProfitOrLoss());
        assertNotNull(profitAndLoss);
        assertNull(profitAndLoss.getGrossProfitOrLoss());
        assertNotNull(profitAndLoss.getOperatingProfitOrLoss());
        assertNotNull(profitAndLoss.getProfitOrLossForFinancialYear());
        assertNotNull(profitAndLoss.getProfitOrLossBeforeTax());

        assertOperatingProfitOrLoss(profitAndLoss);
        assertProfitOrLossBeforeTax(profitAndLoss);
        assertProfitOrLossForFinancialYear(profitAndLoss);
    }

    @Test
    @DisplayName("tests populated profit and loss API maps to profit and loss IXBRL model with null operating profit or loss")
    void testApiToProfitAndLossMapsWithNullOperatingProfitOrLoss() {
        ProfitAndLoss profitAndLoss = apiToProfitAndLossMapper.apiToProfitAndLoss(createCurrentPeriodProfitAndLossWithNullOperatingProfitOrLoss(),createPreviousPeriodProfitAndLossWithNullOperatingProfitOrLoss());
        assertNotNull(profitAndLoss);
        assertNotNull(profitAndLoss.getGrossProfitOrLoss());
        assertNull(profitAndLoss.getOperatingProfitOrLoss());
        assertNotNull(profitAndLoss.getProfitOrLossForFinancialYear());
        assertNotNull(profitAndLoss.getProfitOrLossBeforeTax());

        assertGrossProfitOrLoss(profitAndLoss);
        assertProfitOrLossBeforeTax(profitAndLoss);
        assertProfitOrLossForFinancialYear(profitAndLoss);
    }

    @Test
    @DisplayName("tests populated profit and loss API maps to profit and loss IXBRL model with null profit or loss before tax")
    void testApiToProfitAndLossMapsWithNullProfitOrLossBeforeTax() {
        ProfitAndLoss profitAndLoss = apiToProfitAndLossMapper.apiToProfitAndLoss(createCurrentPeriodProfitAndLossWithNullProfitOrLossBeforeTax(),createPreviousPeriodProfitAndLossWithNullProfitOrLossBeforeTax());
        assertNotNull(profitAndLoss);
        assertNotNull(profitAndLoss.getGrossProfitOrLoss());
        assertNotNull(profitAndLoss.getOperatingProfitOrLoss());
        assertNotNull(profitAndLoss.getProfitOrLossForFinancialYear());
        assertNull(profitAndLoss.getProfitOrLossBeforeTax());

        assertGrossProfitOrLoss(profitAndLoss);
        assertOperatingProfitOrLoss(profitAndLoss);
        assertProfitOrLossForFinancialYear(profitAndLoss);
    }

    @Test
    @DisplayName("tests populated profit and loss API maps to profit and loss IXBRL model with null profit or loss for financial year")
    void testApiToProfitAndLossMapsWithNullProfitOrLossForFinancialYear() {
        ProfitAndLoss profitAndLoss = apiToProfitAndLossMapper.apiToProfitAndLoss(createCurrentPeriodProfitAndLossWithNullProfitOrLossForFinancialYear(),createPreviousPeriodProfitAndLossWithNullProfitOrLossForFinancialYear());
        assertNotNull(profitAndLoss);
        assertNotNull(profitAndLoss.getGrossProfitOrLoss());
        assertNotNull(profitAndLoss.getOperatingProfitOrLoss());
        assertNull(profitAndLoss.getProfitOrLossForFinancialYear());
        assertNotNull(profitAndLoss.getProfitOrLossBeforeTax());

        assertGrossProfitOrLoss(profitAndLoss);
        assertOperatingProfitOrLoss(profitAndLoss);
        assertProfitOrLossBeforeTax(profitAndLoss);

    }

    private ProfitAndLossApi createCurrentPeriodProfitAndLoss() {

        ProfitAndLossApi currentPeriodProfitAndLoss = new ProfitAndLossApi();

        GrossProfitOrLoss grossProfitOrLoss = new GrossProfitOrLoss();
        grossProfitOrLoss.setGrossTotal(GROSS_TOTAL_CURRENT);
        grossProfitOrLoss.setCostOfSales(COST_OF_SALES_CURRENT);
        grossProfitOrLoss.setTurnover(TURNOVER_CURRENT);

        OperatingProfitOrLoss operatingProfitOrLoss = new OperatingProfitOrLoss();
        operatingProfitOrLoss.setAdministrativeExpenses(ADMINISTRATIVE_EXPENSES_CURRENT);
        operatingProfitOrLoss.setDistributionCosts(DISTRIBUTION_COSTS_CURRENT);
        operatingProfitOrLoss.setOperatingTotal(OPERATING_TOTAL_CURRENT);
        operatingProfitOrLoss.setOtherOperatingIncome(OTHER_OPERATING_INCOME_CURRENT);

        ProfitOrLossBeforeTax profitOrLossBeforeTax = new ProfitOrLossBeforeTax();
        profitOrLossBeforeTax.setInterestPayableAndSimilarCharges(INTEREST_PAYABLE_AND_SIMILAR_INCOME_CURRENT);
        profitOrLossBeforeTax.setInterestReceivableAndSimilarIncome(INTEREST_RECEIVABLE_AND_SIMILAR_INCOME_CURRENT);
        profitOrLossBeforeTax.setTotalProfitOrLossBeforeTax(TOTAL_PROFIT_OR_LOSS_BEFORE_TAX_CURRENT);

        ProfitOrLossForFinancialYear profitOrLossForFinancialYear = new ProfitOrLossForFinancialYear();
        profitOrLossForFinancialYear.setTax(TAX_CURRENT);
        profitOrLossForFinancialYear.setTotalProfitOrLossForFinancialYear(TOTAL_PROFIT_OR_LOSS_FOR_FINANCIAL_YEAR_CURRENT);

        currentPeriodProfitAndLoss.setGrossProfitOrLoss(grossProfitOrLoss);
        currentPeriodProfitAndLoss.setOperatingProfitOrLoss(operatingProfitOrLoss);
        currentPeriodProfitAndLoss.setProfitOrLossBeforeTax(profitOrLossBeforeTax);
        currentPeriodProfitAndLoss.setProfitOrLossForFinancialYear(profitOrLossForFinancialYear);

        return currentPeriodProfitAndLoss;
    }

    private ProfitAndLossApi createPreviousPeriodProfitAndLoss() {

        ProfitAndLossApi previousPeriodProfitAndLoss = new ProfitAndLossApi();

        GrossProfitOrLoss grossProfitOrLoss = new GrossProfitOrLoss();
        grossProfitOrLoss.setGrossTotal(GROSS_TOTAL_PREVIOUS);
        grossProfitOrLoss.setCostOfSales(COST_OF_SALES_PREVIOUS);
        grossProfitOrLoss.setTurnover(TURNOVER_PREVIOUS);

        OperatingProfitOrLoss operatingProfitOrLoss = new OperatingProfitOrLoss();
        operatingProfitOrLoss.setAdministrativeExpenses(ADMINISTRATIVE_EXPENSES_PREVIOUS);
        operatingProfitOrLoss.setDistributionCosts(DISTRIBUTION_COSTS_PREVIOUS);
        operatingProfitOrLoss.setOperatingTotal(OPERATING_TOTAL_PREVIOUS);
        operatingProfitOrLoss.setOtherOperatingIncome(OTHER_OPERATING_INCOME_PREVIOUS);

        ProfitOrLossBeforeTax profitOrLossBeforeTax = new ProfitOrLossBeforeTax();
        profitOrLossBeforeTax.setInterestPayableAndSimilarCharges(INTEREST_PAYABLE_AND_SIMILAR_INCOME_PREVIOUS);
        profitOrLossBeforeTax.setInterestReceivableAndSimilarIncome(INTEREST_RECEIVABLE_AND_SIMILAR_INCOME_PREVIOUS);
        profitOrLossBeforeTax.setTotalProfitOrLossBeforeTax(TOTAL_PROFIT_OR_LOSS_BEFORE_TAX_PREVIOUS);

        ProfitOrLossForFinancialYear profitOrLossForFinancialYear = new ProfitOrLossForFinancialYear();
        profitOrLossForFinancialYear.setTax(TAX_PREVIOUS);
        profitOrLossForFinancialYear.setTotalProfitOrLossForFinancialYear(TOTAL_PROFIT_OR_LOSS_FOR_FINANCIAL_YEAR_PREVIOUS);

        previousPeriodProfitAndLoss.setGrossProfitOrLoss(grossProfitOrLoss);
        previousPeriodProfitAndLoss.setOperatingProfitOrLoss(operatingProfitOrLoss);
        previousPeriodProfitAndLoss.setProfitOrLossBeforeTax(profitOrLossBeforeTax);
        previousPeriodProfitAndLoss.setProfitOrLossForFinancialYear(profitOrLossForFinancialYear);

        return previousPeriodProfitAndLoss;
    }

    private ProfitAndLossApi createCurrentPeriodProfitAndLossWithNullGrossProfitOrLoss(){
        ProfitAndLossApi currentPeriodProfitAndLoss = createCurrentPeriodProfitAndLoss();
        currentPeriodProfitAndLoss.setGrossProfitOrLoss(null);
        return currentPeriodProfitAndLoss;
    }

    private ProfitAndLossApi createPreviousPeriodProfitAndLossWithNullGrossProfitOrLoss(){
        ProfitAndLossApi previousPeriodProfitAndLoss = createPreviousPeriodProfitAndLoss();
        previousPeriodProfitAndLoss.setGrossProfitOrLoss(null);
        return previousPeriodProfitAndLoss;
    }

    private ProfitAndLossApi createCurrentPeriodProfitAndLossWithNullOperatingProfitOrLoss(){
        ProfitAndLossApi currentPeriodProfitAndLoss = createCurrentPeriodProfitAndLoss();
        currentPeriodProfitAndLoss.setOperatingProfitOrLoss(null);
        return currentPeriodProfitAndLoss;
    }

    private ProfitAndLossApi createPreviousPeriodProfitAndLossWithNullOperatingProfitOrLoss(){
        ProfitAndLossApi previousPeriodProfitAndLoss = createPreviousPeriodProfitAndLoss();
        previousPeriodProfitAndLoss.setOperatingProfitOrLoss(null);
        return previousPeriodProfitAndLoss;
    }
    private ProfitAndLossApi createCurrentPeriodProfitAndLossWithNullProfitOrLossBeforeTax(){
        ProfitAndLossApi currentPeriodProfitAndLoss = createCurrentPeriodProfitAndLoss();
        currentPeriodProfitAndLoss.setProfitOrLossBeforeTax(null);
        return currentPeriodProfitAndLoss;
    }

    private ProfitAndLossApi createPreviousPeriodProfitAndLossWithNullProfitOrLossBeforeTax(){
        ProfitAndLossApi previousPeriodProfitAndLoss = createPreviousPeriodProfitAndLoss();
        previousPeriodProfitAndLoss.setProfitOrLossBeforeTax(null);
        return previousPeriodProfitAndLoss;
    }

    private ProfitAndLossApi createCurrentPeriodProfitAndLossWithNullProfitOrLossForFinancialYear(){
        ProfitAndLossApi currentPeriodProfitAndLoss = createCurrentPeriodProfitAndLoss();
        currentPeriodProfitAndLoss.setProfitOrLossForFinancialYear(null);
        return currentPeriodProfitAndLoss;
    }

    private ProfitAndLossApi createPreviousPeriodProfitAndLossWithNullProfitOrLossForFinancialYear(){
        ProfitAndLossApi previousPeriodProfitAndLoss = createPreviousPeriodProfitAndLoss();
        previousPeriodProfitAndLoss.setProfitOrLossForFinancialYear(null);
        return previousPeriodProfitAndLoss;
    }

    private void assertGrossProfitOrLoss(ProfitAndLoss profitAndLoss) {
        assertEquals(COST_OF_SALES_CURRENT,profitAndLoss.getGrossProfitOrLoss().getCostOfSales().getCurrentAmount());
        assertEquals(COST_OF_SALES_PREVIOUS,profitAndLoss.getGrossProfitOrLoss().getCostOfSales().getPreviousAmount());
        assertEquals(GROSS_TOTAL_CURRENT,profitAndLoss.getGrossProfitOrLoss().getGrossTotal().getCurrentAmount());
        assertEquals(GROSS_TOTAL_PREVIOUS,profitAndLoss.getGrossProfitOrLoss().getGrossTotal().getPreviousAmount());
        assertEquals(TURNOVER_CURRENT,profitAndLoss.getGrossProfitOrLoss().getTurnover().getCurrentAmount());
        assertEquals(TURNOVER_PREVIOUS,profitAndLoss.getGrossProfitOrLoss().getTurnover().getPreviousAmount());
    }

    private void assertOperatingProfitOrLoss(ProfitAndLoss profitAndLoss) {
        assertEquals(DISTRIBUTION_COSTS_CURRENT,profitAndLoss.getOperatingProfitOrLoss().getDistributionCosts().getCurrentAmount());
        assertEquals(DISTRIBUTION_COSTS_PREVIOUS,profitAndLoss.getOperatingProfitOrLoss().getDistributionCosts().getPreviousAmount());
        assertEquals(ADMINISTRATIVE_EXPENSES_CURRENT,profitAndLoss.getOperatingProfitOrLoss().getAdministrativeExpenses().getCurrentAmount());
        assertEquals(ADMINISTRATIVE_EXPENSES_PREVIOUS,profitAndLoss.getOperatingProfitOrLoss().getAdministrativeExpenses().getPreviousAmount());
        assertEquals(OTHER_OPERATING_INCOME_CURRENT,profitAndLoss.getOperatingProfitOrLoss().getOtherOperatingIncome().getCurrentAmount());
        assertEquals(OTHER_OPERATING_INCOME_PREVIOUS,profitAndLoss.getOperatingProfitOrLoss().getOtherOperatingIncome().getPreviousAmount());
        assertEquals(OPERATING_TOTAL_CURRENT,profitAndLoss.getOperatingProfitOrLoss().getOperatingTotal().getCurrentAmount());
        assertEquals(OPERATING_TOTAL_PREVIOUS,profitAndLoss.getOperatingProfitOrLoss().getOperatingTotal().getPreviousAmount());
    }

    private void assertProfitOrLossBeforeTax(ProfitAndLoss profitAndLoss) {
        assertEquals(INTEREST_RECEIVABLE_AND_SIMILAR_INCOME_CURRENT,profitAndLoss.getProfitOrLossBeforeTax().getInterestReceivableAndSimilarIncome().getCurrentAmount());
        assertEquals(INTEREST_RECEIVABLE_AND_SIMILAR_INCOME_PREVIOUS,profitAndLoss.getProfitOrLossBeforeTax().getInterestReceivableAndSimilarIncome().getPreviousAmount());
        assertEquals(INTEREST_PAYABLE_AND_SIMILAR_INCOME_CURRENT,profitAndLoss.getProfitOrLossBeforeTax().getInterestPayableAndSimilarCharges().getCurrentAmount());
        assertEquals(INTEREST_PAYABLE_AND_SIMILAR_INCOME_PREVIOUS,profitAndLoss.getProfitOrLossBeforeTax().getInterestPayableAndSimilarCharges().getPreviousAmount());
        assertEquals(TOTAL_PROFIT_OR_LOSS_BEFORE_TAX_CURRENT,profitAndLoss.getProfitOrLossBeforeTax().getTotalProfitOrLossBeforeTax().getCurrentAmount());
        assertEquals(TOTAL_PROFIT_OR_LOSS_BEFORE_TAX_PREVIOUS,profitAndLoss.getProfitOrLossBeforeTax().getTotalProfitOrLossBeforeTax().getPreviousAmount());
    }

    private void assertProfitOrLossForFinancialYear(ProfitAndLoss profitAndLoss) {
        assertEquals(TAX_CURRENT,profitAndLoss.getProfitOrLossForFinancialYear().getTax().getCurrentAmount());
        assertEquals(TAX_PREVIOUS,profitAndLoss.getProfitOrLossForFinancialYear().getTax().getPreviousAmount());
        assertEquals(TOTAL_PROFIT_OR_LOSS_FOR_FINANCIAL_YEAR_CURRENT,profitAndLoss.getProfitOrLossForFinancialYear().getTotalProfitOrLossForFinancialYear().getCurrentAmount());
        assertEquals(TOTAL_PROFIT_OR_LOSS_FOR_FINANCIAL_YEAR_PREVIOUS,profitAndLoss.getProfitOrLossForFinancialYear().getTotalProfitOrLossForFinancialYear().getPreviousAmount());
    }
}
