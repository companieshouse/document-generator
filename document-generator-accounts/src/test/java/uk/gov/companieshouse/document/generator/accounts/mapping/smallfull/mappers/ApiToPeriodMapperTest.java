package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.mappers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.model.accounts.smallfull.AccountingPeriodApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.SmallFullApi;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.period.Period;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApiToPeriodMapperTest {

    @Mock
    private ApiToPeriodMapper internalApiToPeriodMapper;

    @InjectMocks
    private ApiToPeriodMapper apiToPeriodMapper = new ApiToPeriodMapperImpl();

    private static final String CURRENT_PERIOD_START_ON = "2018-01-01";

    private static final String CURRENT_PERIOD_END_ON = "2018-12-31";

    private static final String PREVIOUS_PERIOD_START_ON = "2017-01-01";

    private static final String PREVIOUS_PERIOD_END_ON = "2017-12-31";

    private static final String CURRENT_PERIOD_START_ON_FORMATTED = "1 January 2018";

    private static final String CURRENT_PERIOD_END_ON_FORMATTED = "31 December 2018";

    private static final String PREVIOUS_PERIOD_START_ON_FORMATTED = "1 January 2017";

    private static final String PREVIOUS_PERIOD_END_ON_FORMATTED = "31 December 2017";

    private static final String CURRENT_PERIOD_BS_DATE = "2018";

    private static final String PREVIOUS_PERIOD_BS_DATE = "2017";

    @Test
    @DisplayName("tests that the dates from Api are mapped to Period IXBRL model for multi year filings")
    void testApiMapsDatesToPeriodModelForMultiYearFiling() {

        SmallFullApi smallFullApi = createAccountsFilingDates(true);

        when(internalApiToPeriodMapper
                .apiToPeriod(smallFullApi)).thenReturn(createPeriod(true));

        Period period = apiToPeriodMapper.apiToPeriod(smallFullApi);

        assertNotNull(period);
        assertEquals(CURRENT_PERIOD_START_ON_FORMATTED, period.getCurrentPeriodStartOnFormatted());
        assertEquals(CURRENT_PERIOD_END_ON_FORMATTED, period.getCurrentPeriodEndOnFormatted());
        assertEquals(PREVIOUS_PERIOD_START_ON_FORMATTED, period.getPreviousPeriodStartOnFormatted());
        assertEquals(PREVIOUS_PERIOD_END_ON_FORMATTED, period.getPreviousPeriodEndOnFormatted());
        assertEquals(CURRENT_PERIOD_BS_DATE, period.getCurrentPeriodBSDate());
        assertEquals(PREVIOUS_PERIOD_BS_DATE, period.getPreviousPeriodBSDate());
    }

    @Test
    @DisplayName("tests that the dates from Api are mapped to Period IXBRL model for single year filings")
    void testApiMapsDatesToPeriodModelForSingleYearFiling() {

        SmallFullApi smallFullApi = createAccountsFilingDates(false);

        when(internalApiToPeriodMapper
                .apiToPeriod(smallFullApi)).thenReturn(createPeriod(false));

        Period period = apiToPeriodMapper.apiToPeriod(smallFullApi);

        assertNotNull(period);
        assertEquals(CURRENT_PERIOD_START_ON_FORMATTED, period.getCurrentPeriodStartOnFormatted());
        assertEquals(CURRENT_PERIOD_END_ON_FORMATTED, period.getCurrentPeriodEndOnFormatted());
        assertEquals(null, period.getPreviousPeriodStartOnFormatted());
        assertEquals(null, period.getPreviousPeriodEndOnFormatted());
        assertEquals(CURRENT_PERIOD_BS_DATE, period.getCurrentPeriodBSDate());
        assertEquals(null, period.getPreviousPeriodBSDate());
    }

    private SmallFullApi createAccountsFilingDates(boolean multiYearFiling) {

        SmallFullApi smallFullApi = new SmallFullApi();
        AccountingPeriodApi lastAccountsApi = new AccountingPeriodApi();
        AccountingPeriodApi nextAccountsApi = new AccountingPeriodApi();

        if (multiYearFiling == true) {
            lastAccountsApi.setPeriodEndOn(LocalDate.of(2017,12,31));
            lastAccountsApi.setPeriodStartOn(LocalDate.of(2017, 01, 01));
        }

        nextAccountsApi.setPeriodEndOn(LocalDate.of(2018,12,31));
        nextAccountsApi.setPeriodStartOn(LocalDate.of(2018,01,01));

        smallFullApi.setLastAccounts(lastAccountsApi);
        smallFullApi.setNextAccounts(nextAccountsApi);

        return smallFullApi;
    }

    private Period createPeriod(boolean multiYearFiling) {

        Period period = new Period();

        period.setCurrentPeriodStartOn(CURRENT_PERIOD_START_ON);
        period.setCurrentPeriodEndsOn(CURRENT_PERIOD_END_ON);

        if (multiYearFiling) {
            period.setPreviousPeriodStartOn(PREVIOUS_PERIOD_START_ON);
            period.setPreviousPeriodEndsOn(PREVIOUS_PERIOD_END_ON);
        }

        return period;
    }
}
