package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.model.accounts.smallfull.creditorswithinoneyear.CurrentPeriod;
import uk.gov.companieshouse.api.model.accounts.smallfull.creditorswithinoneyear.PreviousPeriod;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.creditorswithinoneyear.CreditorsWithinOneYear;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApiToCreditorsWithinOneYearMapperTest {

    private static final String DETAILS = "Details";
    
    private static final Long ACCRUALS_AND_DEFERRED_INCOME_CURRENT = 1L;
    private static final Long ACCRUALS_AND_DEFERRED_INCOME_PREVIOUS = 10L;
    private static final Long BANK_LOANS_AND_OVERDRAFTS_CURRENT = 2L;
    private static final Long BANK_LOANS_AND_OVERDRAFTS_PREVIOUS = 20L;
    private static final Long FINANCE_LEASES_AND_HIRE_PURCHASE_CONTRACTS_CURRENT = 3L;
    private static final Long FINANCE_LEASES_AND_HIRE_PURCHASE_CONTRACTS_PREVIOUS = 30L;
    private static final Long OTHER_CREDITORS_CURRENT = 4L;
    private static final Long OTHER_CREDITORS_PREVIOUS = 40L;
    private static final Long TAXATION_AND_SOCIAL_SECURITY_CURRENT = 5L;
    private static final Long TAXATION_AND_SOCIAL_SECURITY_PREVIOUS = 50L;
    private static final Long TRADE_CREDITORS_CURRENT = 6L;
    private static final Long TRADE_CREDITORS_PREVIOUS = 60L;
    private static final Long TOTAL_CURRENT = 7L;
    private static final Long TOTAL_PREVIOUS = 70L;

    @Test
    @DisplayName("tests populated creditors within one year API maps to creditors within one year IXBRL model")
    void testApiToCreditorsMaps() {

        CreditorsWithinOneYear creditorsWithinOneYear = ApiToCreditorsWithinOneYearMapper.INSTANCE.apiToCreditorsWithinOneYear(createCurrentPeriodCreditorsWithinOneYear(),
                createPreviousPeriodCreditorsWithinOneYear());

        assertNotNull(creditorsWithinOneYear);
        
        assertEquals(DETAILS, creditorsWithinOneYear.getDetails());
        assertEquals(ACCRUALS_AND_DEFERRED_INCOME_CURRENT, creditorsWithinOneYear.getAccrualsAndDeferredIncome().getCurrentAmount());
        assertEquals(ACCRUALS_AND_DEFERRED_INCOME_PREVIOUS, creditorsWithinOneYear.getAccrualsAndDeferredIncome().getPreviousAmount());
        assertEquals(BANK_LOANS_AND_OVERDRAFTS_CURRENT, creditorsWithinOneYear.getBankLoansAndOverdrafts().getCurrentAmount());
        assertEquals(BANK_LOANS_AND_OVERDRAFTS_PREVIOUS, creditorsWithinOneYear.getBankLoansAndOverdrafts().getPreviousAmount());
        assertEquals(FINANCE_LEASES_AND_HIRE_PURCHASE_CONTRACTS_CURRENT, creditorsWithinOneYear.getFinanceLeasesAndHirePurchaseContracts().getCurrentAmount());
        assertEquals(FINANCE_LEASES_AND_HIRE_PURCHASE_CONTRACTS_PREVIOUS, creditorsWithinOneYear.getFinanceLeasesAndHirePurchaseContracts().getPreviousAmount());
        assertEquals(OTHER_CREDITORS_CURRENT, creditorsWithinOneYear.getOtherCreditors().getCurrentAmount());
        assertEquals(OTHER_CREDITORS_PREVIOUS, creditorsWithinOneYear.getOtherCreditors().getPreviousAmount());
        assertEquals(TAXATION_AND_SOCIAL_SECURITY_CURRENT, creditorsWithinOneYear.getTaxationAndSocialSecurity().getCurrentAmount());
        assertEquals(TAXATION_AND_SOCIAL_SECURITY_PREVIOUS, creditorsWithinOneYear.getTaxationAndSocialSecurity().getPreviousAmount());
        assertEquals(TRADE_CREDITORS_CURRENT, creditorsWithinOneYear.getTradeCreditors().getCurrentAmount());
        assertEquals(TRADE_CREDITORS_PREVIOUS, creditorsWithinOneYear.getTradeCreditors().getPreviousAmount());
        assertEquals(TOTAL_CURRENT, creditorsWithinOneYear.getTotal().getCurrentAmount());
        assertEquals(TOTAL_PREVIOUS, creditorsWithinOneYear.getTotal().getPreviousAmount());
    }
    
    @Test
    @DisplayName("tests creditors within one year API with null previous period maps to null creditors within one year IXBRL model")
    void testApiToCreditorsMapsWhenPreviousPeriodsNull() {

        CreditorsWithinOneYear creditorsWithinOneYear = ApiToCreditorsWithinOneYearMapper.INSTANCE.apiToCreditorsWithinOneYear(createCurrentPeriodCreditorsWithinOneYear(),
                null);

        assertNotNull(creditorsWithinOneYear);
        
        assertEquals(DETAILS, creditorsWithinOneYear.getDetails());
        assertEquals(ACCRUALS_AND_DEFERRED_INCOME_CURRENT, creditorsWithinOneYear.getAccrualsAndDeferredIncome().getCurrentAmount());
        assertEquals(null, creditorsWithinOneYear.getAccrualsAndDeferredIncome().getPreviousAmount());
        assertEquals(BANK_LOANS_AND_OVERDRAFTS_CURRENT, creditorsWithinOneYear.getBankLoansAndOverdrafts().getCurrentAmount());
        assertEquals(null, creditorsWithinOneYear.getBankLoansAndOverdrafts().getPreviousAmount());
        assertEquals(FINANCE_LEASES_AND_HIRE_PURCHASE_CONTRACTS_CURRENT, creditorsWithinOneYear.getFinanceLeasesAndHirePurchaseContracts().getCurrentAmount());
        assertEquals(null, creditorsWithinOneYear.getFinanceLeasesAndHirePurchaseContracts().getPreviousAmount());
        assertEquals(OTHER_CREDITORS_CURRENT, creditorsWithinOneYear.getOtherCreditors().getCurrentAmount());
        assertEquals(null, creditorsWithinOneYear.getOtherCreditors().getPreviousAmount());
        assertEquals(TAXATION_AND_SOCIAL_SECURITY_CURRENT, creditorsWithinOneYear.getTaxationAndSocialSecurity().getCurrentAmount());
        assertEquals(null, creditorsWithinOneYear.getTaxationAndSocialSecurity().getPreviousAmount());
        assertEquals(TRADE_CREDITORS_CURRENT, creditorsWithinOneYear.getTradeCreditors().getCurrentAmount());
        assertEquals(null, creditorsWithinOneYear.getTradeCreditors().getPreviousAmount());
        assertEquals(TOTAL_CURRENT, creditorsWithinOneYear.getTotal().getCurrentAmount());
        assertEquals(null, creditorsWithinOneYear.getTotal().getPreviousAmount());
    }
    
    @Test
    @DisplayName("tests creditors within one year API with null current period maps to null creditors within one year IXBRL model")
    void testApiToCreditorsMapsWhenCurrentPeriodsNull() {

        CreditorsWithinOneYear creditorsWithinOneYear = ApiToCreditorsWithinOneYearMapper.INSTANCE.apiToCreditorsWithinOneYear(null,
                createPreviousPeriodCreditorsWithinOneYear());

        assertNotNull(creditorsWithinOneYear);
        
        assertEquals(null, creditorsWithinOneYear.getDetails());
        assertEquals(null, creditorsWithinOneYear.getAccrualsAndDeferredIncome().getCurrentAmount());
        assertEquals(ACCRUALS_AND_DEFERRED_INCOME_PREVIOUS, creditorsWithinOneYear.getAccrualsAndDeferredIncome().getPreviousAmount());
        assertEquals(null, creditorsWithinOneYear.getBankLoansAndOverdrafts().getCurrentAmount());
        assertEquals(BANK_LOANS_AND_OVERDRAFTS_PREVIOUS, creditorsWithinOneYear.getBankLoansAndOverdrafts().getPreviousAmount());
        assertEquals(null, creditorsWithinOneYear.getFinanceLeasesAndHirePurchaseContracts().getCurrentAmount());
        assertEquals(FINANCE_LEASES_AND_HIRE_PURCHASE_CONTRACTS_PREVIOUS, creditorsWithinOneYear.getFinanceLeasesAndHirePurchaseContracts().getPreviousAmount());
        assertEquals(null, creditorsWithinOneYear.getOtherCreditors().getCurrentAmount());
        assertEquals(OTHER_CREDITORS_PREVIOUS, creditorsWithinOneYear.getOtherCreditors().getPreviousAmount());
        assertEquals(null, creditorsWithinOneYear.getTaxationAndSocialSecurity().getCurrentAmount());
        assertEquals(TAXATION_AND_SOCIAL_SECURITY_PREVIOUS, creditorsWithinOneYear.getTaxationAndSocialSecurity().getPreviousAmount());
        assertEquals(null, creditorsWithinOneYear.getTradeCreditors().getCurrentAmount());
        assertEquals(TRADE_CREDITORS_PREVIOUS, creditorsWithinOneYear.getTradeCreditors().getPreviousAmount());
        assertEquals(null, creditorsWithinOneYear.getTotal().getCurrentAmount());
        assertEquals(TOTAL_PREVIOUS, creditorsWithinOneYear.getTotal().getPreviousAmount());
    }
    
    @Test
    @DisplayName("tests creditors within one year API with null periods maps to null creditors within one year IXBRL model")
    void testApiToCreditorsMapsWhenBothPeriodsNull() {

        CreditorsWithinOneYear creditorsWithinOneYear = ApiToCreditorsWithinOneYearMapper.INSTANCE.apiToCreditorsWithinOneYear(null, null);

        assertNull(creditorsWithinOneYear);
    }

    private CurrentPeriod createCurrentPeriodCreditorsWithinOneYear() {

        CurrentPeriod creditorsWithinOneYearCurrentPeriod = new CurrentPeriod();
        creditorsWithinOneYearCurrentPeriod.setDetails(DETAILS);
        creditorsWithinOneYearCurrentPeriod.setAccrualsAndDeferredIncome(ACCRUALS_AND_DEFERRED_INCOME_CURRENT);
        creditorsWithinOneYearCurrentPeriod.setBankLoansAndOverdrafts(BANK_LOANS_AND_OVERDRAFTS_CURRENT);
        creditorsWithinOneYearCurrentPeriod.setFinanceLeasesAndHirePurchaseContracts(FINANCE_LEASES_AND_HIRE_PURCHASE_CONTRACTS_CURRENT);
        creditorsWithinOneYearCurrentPeriod.setOtherCreditors(OTHER_CREDITORS_CURRENT);
        creditorsWithinOneYearCurrentPeriod.setTaxationAndSocialSecurity(TAXATION_AND_SOCIAL_SECURITY_CURRENT);
        creditorsWithinOneYearCurrentPeriod.setTradeCreditors(TRADE_CREDITORS_CURRENT);
        creditorsWithinOneYearCurrentPeriod.setTotal(TOTAL_CURRENT);

        return creditorsWithinOneYearCurrentPeriod;
    }

    private PreviousPeriod createPreviousPeriodCreditorsWithinOneYear() {

        PreviousPeriod creditorsWithinOneYearPreviousPeriod = new PreviousPeriod();
        creditorsWithinOneYearPreviousPeriod.setAccrualsAndDeferredIncome(ACCRUALS_AND_DEFERRED_INCOME_PREVIOUS);
        creditorsWithinOneYearPreviousPeriod.setBankLoansAndOverdrafts(BANK_LOANS_AND_OVERDRAFTS_PREVIOUS);
        creditorsWithinOneYearPreviousPeriod.setFinanceLeasesAndHirePurchaseContracts(FINANCE_LEASES_AND_HIRE_PURCHASE_CONTRACTS_PREVIOUS);
        creditorsWithinOneYearPreviousPeriod.setOtherCreditors(OTHER_CREDITORS_PREVIOUS);
        creditorsWithinOneYearPreviousPeriod.setTaxationAndSocialSecurity(TAXATION_AND_SOCIAL_SECURITY_PREVIOUS);
        creditorsWithinOneYearPreviousPeriod.setTradeCreditors(TRADE_CREDITORS_PREVIOUS);
        creditorsWithinOneYearPreviousPeriod.setTotal(TOTAL_PREVIOUS);

        return creditorsWithinOneYearPreviousPeriod;
    }
}
