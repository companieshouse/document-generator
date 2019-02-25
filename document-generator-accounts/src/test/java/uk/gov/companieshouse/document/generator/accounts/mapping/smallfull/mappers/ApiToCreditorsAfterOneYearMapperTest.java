package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.model.accounts.smallfull.creditorsafteroneyear.CurrentPeriod;
import uk.gov.companieshouse.api.model.accounts.smallfull.creditorsafteroneyear.PreviousPeriod;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.creditorsafteroneyear.CreditorsAfterOneYear;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApiToCreditorsAfterOneYearMapperTest {

    private static final String DETAILS = "Details";
    
    private static final Long BANK_LOANS_AND_OVERDRAFTS_CURRENT = 1L;
    private static final Long BANK_LOANS_AND_OVERDRAFTS_PREVIOUS = 10L;
    private static final Long FINANCE_LEASES_AND_HIRE_PURCHASE_CONTRACTS_CURRENT = 2L;
    private static final Long FINANCE_LEASES_AND_HIRE_PURCHASE_CONTRACTS_PREVIOUS = 20L;
    private static final Long OTHER_CREDITORS_CURRENT = 3L;
    private static final Long OTHER_CREDITORS_PREVIOUS = 30L;
    private static final Long TOTAL_CURRENT = 4L;
    private static final Long TOTAL_PREVIOUS = 40L;

    @Test
    @DisplayName("tests populated creditors after one year API maps to creditors after one year IXBRL model")
    void testApiToCreditorsMaps() {

        CreditorsAfterOneYear creditorsAfterOneYear = ApiToCreditorsAfterOneYearMapper.INSTANCE.apiToCreditorsAfterOneYear(createCurrentPeriodCreditorsAfterOneYear(),
                createPreviousPeriodCreditorsAfterOneYear());

        assertNotNull(creditorsAfterOneYear);
        
        assertEquals(DETAILS, creditorsAfterOneYear.getDetails());
        assertEquals(BANK_LOANS_AND_OVERDRAFTS_CURRENT, creditorsAfterOneYear.getBankLoansAndOverdrafts().getCurrentAmount());
        assertEquals(BANK_LOANS_AND_OVERDRAFTS_PREVIOUS, creditorsAfterOneYear.getBankLoansAndOverdrafts().getPreviousAmount());
        assertEquals(FINANCE_LEASES_AND_HIRE_PURCHASE_CONTRACTS_CURRENT, creditorsAfterOneYear.getFinanceLeasesAndHirePurchaseContracts().getCurrentAmount());
        assertEquals(FINANCE_LEASES_AND_HIRE_PURCHASE_CONTRACTS_PREVIOUS, creditorsAfterOneYear.getFinanceLeasesAndHirePurchaseContracts().getPreviousAmount());
        assertEquals(OTHER_CREDITORS_CURRENT, creditorsAfterOneYear.getOtherCreditors().getCurrentAmount());
        assertEquals(OTHER_CREDITORS_PREVIOUS, creditorsAfterOneYear.getOtherCreditors().getPreviousAmount());
        assertEquals(TOTAL_CURRENT, creditorsAfterOneYear.getTotal().getCurrentAmount());
        assertEquals(TOTAL_PREVIOUS, creditorsAfterOneYear.getTotal().getPreviousAmount());
    }
    
    @Test
    @DisplayName("tests creditors after one year API with null previous period maps to null creditors after one year IXBRL model")
    void testApiToCreditorsMapsWhenPreviousPeriodsNull() {

        CreditorsAfterOneYear creditorsAfterOneYear = ApiToCreditorsAfterOneYearMapper.INSTANCE.apiToCreditorsAfterOneYear(createCurrentPeriodCreditorsAfterOneYear(),
                null);

        assertNotNull(creditorsAfterOneYear);
        
        assertEquals(DETAILS, creditorsAfterOneYear.getDetails());
        assertEquals(BANK_LOANS_AND_OVERDRAFTS_CURRENT, creditorsAfterOneYear.getBankLoansAndOverdrafts().getCurrentAmount());
        assertEquals(null, creditorsAfterOneYear.getBankLoansAndOverdrafts().getPreviousAmount());
        assertEquals(FINANCE_LEASES_AND_HIRE_PURCHASE_CONTRACTS_CURRENT, creditorsAfterOneYear.getFinanceLeasesAndHirePurchaseContracts().getCurrentAmount());
        assertEquals(null, creditorsAfterOneYear.getFinanceLeasesAndHirePurchaseContracts().getPreviousAmount());
        assertEquals(OTHER_CREDITORS_CURRENT, creditorsAfterOneYear.getOtherCreditors().getCurrentAmount());
        assertEquals(null, creditorsAfterOneYear.getOtherCreditors().getPreviousAmount());
        assertEquals(TOTAL_CURRENT, creditorsAfterOneYear.getTotal().getCurrentAmount());
        assertEquals(null, creditorsAfterOneYear.getTotal().getPreviousAmount());
    }
    
    @Test
    @DisplayName("tests creditors after one year API with null current period maps to null creditors after one year IXBRL model")
    void testApiToCreditorsMapsWhenCurrentPeriodsNull() {

        CreditorsAfterOneYear creditorsAfterOneYear = ApiToCreditorsAfterOneYearMapper.INSTANCE.apiToCreditorsAfterOneYear(null,
                createPreviousPeriodCreditorsAfterOneYear());

        assertNotNull(creditorsAfterOneYear);
        
        assertEquals(null, creditorsAfterOneYear.getDetails());
        assertEquals(null, creditorsAfterOneYear.getBankLoansAndOverdrafts().getCurrentAmount());
        assertEquals(BANK_LOANS_AND_OVERDRAFTS_PREVIOUS, creditorsAfterOneYear.getBankLoansAndOverdrafts().getPreviousAmount());
        assertEquals(null, creditorsAfterOneYear.getFinanceLeasesAndHirePurchaseContracts().getCurrentAmount());
        assertEquals(FINANCE_LEASES_AND_HIRE_PURCHASE_CONTRACTS_PREVIOUS, creditorsAfterOneYear.getFinanceLeasesAndHirePurchaseContracts().getPreviousAmount());
        assertEquals(null, creditorsAfterOneYear.getOtherCreditors().getCurrentAmount());
        assertEquals(OTHER_CREDITORS_PREVIOUS, creditorsAfterOneYear.getOtherCreditors().getPreviousAmount());
        assertEquals(null, creditorsAfterOneYear.getTotal().getCurrentAmount());
        assertEquals(TOTAL_PREVIOUS, creditorsAfterOneYear.getTotal().getPreviousAmount());
    }
    
    @Test
    @DisplayName("tests creditors after one year API with null periods maps to null creditors after one year IXBRL model")
    void testApiToCreditorsMapsWhenBothPeriodsNull() {

        CreditorsAfterOneYear creditorsAfterOneYear = ApiToCreditorsAfterOneYearMapper.INSTANCE.apiToCreditorsAfterOneYear(null, null);

        assertNull(creditorsAfterOneYear);
    }

    private CurrentPeriod createCurrentPeriodCreditorsAfterOneYear() {

        CurrentPeriod creditorsAfterOneYearCurrentPeriod = new CurrentPeriod();
        creditorsAfterOneYearCurrentPeriod.setDetails(DETAILS);
        creditorsAfterOneYearCurrentPeriod.setBankLoansAndOverdrafts(BANK_LOANS_AND_OVERDRAFTS_CURRENT);
        creditorsAfterOneYearCurrentPeriod.setFinanceLeasesAndHirePurchaseContracts(FINANCE_LEASES_AND_HIRE_PURCHASE_CONTRACTS_CURRENT);
        creditorsAfterOneYearCurrentPeriod.setOtherCreditors(OTHER_CREDITORS_CURRENT);
        creditorsAfterOneYearCurrentPeriod.setTotal(TOTAL_CURRENT);

        return creditorsAfterOneYearCurrentPeriod;
    }

    private PreviousPeriod createPreviousPeriodCreditorsAfterOneYear() {

        PreviousPeriod creditorsAfterOneYearPreviousPeriod = new PreviousPeriod();
        creditorsAfterOneYearPreviousPeriod.setBankLoansAndOverdrafts(BANK_LOANS_AND_OVERDRAFTS_PREVIOUS);
        creditorsAfterOneYearPreviousPeriod.setFinanceLeasesAndHirePurchaseContracts(FINANCE_LEASES_AND_HIRE_PURCHASE_CONTRACTS_PREVIOUS);
        creditorsAfterOneYearPreviousPeriod.setOtherCreditors(OTHER_CREDITORS_PREVIOUS);
        creditorsAfterOneYearPreviousPeriod.setTotal(TOTAL_PREVIOUS);

        return creditorsAfterOneYearPreviousPeriod;
    }
}
