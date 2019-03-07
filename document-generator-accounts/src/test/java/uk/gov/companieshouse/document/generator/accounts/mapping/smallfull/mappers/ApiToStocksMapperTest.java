package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.model.accounts.smallfull.stocks.CurrentPeriod;
import uk.gov.companieshouse.api.model.accounts.smallfull.stocks.PreviousPeriod;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.stocks.StocksNote;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApiToStocksMapperTest {

    private ApiToStocksMapper apiToStocksMapper = new ApiToStocksMapperImpl();

    private static final Long STOCKS_CURRENT = 1L;
    private static final Long STOCKS_PREVIOUS = 10L;
    private static final Long PAYMENTS_ON_ACCOUNT_CURRENT = 2L;
    private static final Long PAYMENTS_ON_ACCOUNT_PREVIOUS = 20L;
    private static final Long TOTAL_CURRENT = 3L;
    private static final Long TOTAL_PREVIOUS = 30L;

    @Test
    @DisplayName("tests stocks API values map to stocks IXBRL model")
    void testApiToCompanyMaps() {

        StocksNote stocks = apiToStocksMapper.apiToStocks(createCurrentPeriodStocks(),
                createPreviousPeriodStocks());

        assertNotNull(stocks);
        assertEquals(STOCKS_CURRENT, stocks.getStocks().getCurrentAmount());
        assertEquals(STOCKS_PREVIOUS, stocks.getStocks().getPreviousAmount());
        assertEquals(PAYMENTS_ON_ACCOUNT_CURRENT, stocks.getPaymentsOnAccount().getCurrentAmount());
        assertEquals(PAYMENTS_ON_ACCOUNT_PREVIOUS, stocks.getPaymentsOnAccount().getPreviousAmount());
        assertEquals(TOTAL_CURRENT, stocks.getTotal().getCurrentAmount());
        assertEquals(TOTAL_PREVIOUS, stocks.getTotal().getPreviousAmount());

    }

    @Test
    @DisplayName("tests stocks API with null previous period maps to stocks IXBRL model")
    void testApiToCreditorsMapsWhenPreviousPeriodsNull() {

        StocksNote stocks = apiToStocksMapper.apiToStocks(createCurrentPeriodStocks(), null);

        assertNotNull(stocks);
        
        assertEquals(STOCKS_CURRENT, stocks.getStocks().getCurrentAmount());
        assertEquals(null, stocks.getStocks().getPreviousAmount());
        assertEquals(PAYMENTS_ON_ACCOUNT_CURRENT, stocks.getPaymentsOnAccount().getCurrentAmount());
        assertEquals(null, stocks.getPaymentsOnAccount().getPreviousAmount());
        assertEquals(TOTAL_CURRENT, stocks.getTotal().getCurrentAmount());
        assertEquals(null, stocks.getTotal().getPreviousAmount());
    }
    
    @Test
    @DisplayName("tests stocks API with null current period maps to stocks IXBRL model")
    void testApiToCreditorsMapsWhenCurrentPeriodsNull() {

        StocksNote stocks = apiToStocksMapper.apiToStocks(null, createPreviousPeriodStocks());

        assertNotNull(stocks);
        assertEquals(null, stocks.getStocks().getCurrentAmount());
        assertEquals(STOCKS_PREVIOUS, stocks.getStocks().getPreviousAmount());
        assertEquals(null, stocks.getPaymentsOnAccount().getCurrentAmount());
        assertEquals(PAYMENTS_ON_ACCOUNT_PREVIOUS, stocks.getPaymentsOnAccount().getPreviousAmount());
        assertEquals(null, stocks.getTotal().getCurrentAmount());
        assertEquals(TOTAL_PREVIOUS, stocks.getTotal().getPreviousAmount());
    }
    
    private CurrentPeriod createCurrentPeriodStocks() {

        CurrentPeriod stocksCurrentPeriod = new CurrentPeriod();

        stocksCurrentPeriod.setStocks(STOCKS_CURRENT);
        stocksCurrentPeriod.setPaymentsOnAccount(PAYMENTS_ON_ACCOUNT_CURRENT);
        stocksCurrentPeriod.setTotal(TOTAL_CURRENT);

        return stocksCurrentPeriod;
    }

    private PreviousPeriod createPreviousPeriodStocks() {

        PreviousPeriod stocksPreviousPeriod = new PreviousPeriod();
        
        stocksPreviousPeriod.setStocks(STOCKS_PREVIOUS);
        stocksPreviousPeriod.setPaymentsOnAccount(PAYMENTS_ON_ACCOUNT_PREVIOUS);
        stocksPreviousPeriod.setTotal(TOTAL_PREVIOUS);

        return stocksPreviousPeriod;
    }
    
    @Test
    @DisplayName("tests stocks API with null periods maps to null stocks IXBRL model")
    void testApiToCreditorsMapsWhenBothPeriodsNull() {

        StocksNote stocksNote = apiToStocksMapper.apiToStocks(null, null);

        assertNull(stocksNote);
    }
}
