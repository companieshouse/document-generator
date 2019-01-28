package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.mappers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.model.company.CompanyProfileApi;
import uk.gov.companieshouse.api.model.company.account.CompanyAccountApi;
import uk.gov.companieshouse.api.model.company.account.LastAccountsApi;
import uk.gov.companieshouse.api.model.company.account.NextAccountsApi;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.period.Period;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApiToPeriodMapperTest {

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

        Period period = ApiToPeriodMapper.INSTANCE.apiToPeriod(createAccountsFilingDates(true));

        assertNotNull(period);
        assertEquals(CURRENT_PERIOD_START_ON, period.getCurrentPeriodStartOn());
        assertEquals(CURRENT_PERIOD_END_ON, period.getCurrentPeriodEndsOn());
        assertEquals(PREVIOUS_PERIOD_START_ON, period.getPreviousPeriodStartOn());
        assertEquals(PREVIOUS_PERIOD_END_ON, period.getPreviousPeriodEndsOn());
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

        Period period = ApiToPeriodMapper.INSTANCE.apiToPeriod(createAccountsFilingDates(false));

        assertNotNull(period);
        assertEquals(CURRENT_PERIOD_START_ON, period.getCurrentPeriodStartOn());
        assertEquals(CURRENT_PERIOD_END_ON, period.getCurrentPeriodEndsOn());
        assertEquals(null, period.getPreviousPeriodStartOn());
        assertEquals(null, period.getPreviousPeriodEndsOn());
        assertEquals(CURRENT_PERIOD_START_ON_FORMATTED, period.getCurrentPeriodStartOnFormatted());
        assertEquals(CURRENT_PERIOD_END_ON_FORMATTED, period.getCurrentPeriodEndOnFormatted());
        assertEquals(null, period.getPreviousPeriodStartOnFormatted());
        assertEquals(null, period.getPreviousPeriodEndOnFormatted());
        assertEquals(CURRENT_PERIOD_BS_DATE, period.getCurrentPeriodBSDate());
        assertEquals(null, period.getPreviousPeriodBSDate());
    }

    private CompanyProfileApi createAccountsFilingDates(boolean multiYearFiling) {

        CompanyProfileApi companyProfileApi = new CompanyProfileApi();
        CompanyAccountApi companyAccountsApi = new CompanyAccountApi();
        LastAccountsApi lastAccountsApi = new LastAccountsApi();
        NextAccountsApi nextAccountsApi = new NextAccountsApi();

        if (multiYearFiling == true) {
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
}
