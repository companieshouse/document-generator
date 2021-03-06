package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import uk.gov.companieshouse.accountsdates.AccountsDatesHelper;
import uk.gov.companieshouse.accountsdates.impl.AccountsDatesHelperImpl;
import uk.gov.companieshouse.api.model.accounts.smallfull.AccountingPeriodApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.SmallFullApi;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.period.Period;

import java.time.LocalDate;

public abstract class ApiToPeriodMapperDecorator implements ApiToPeriodMapper {

    @Autowired
    @Qualifier("delegate")
    private ApiToPeriodMapper apiToPeriodMapper;

    private AccountsDatesHelper accountsDatesHelper = new AccountsDatesHelperImpl();

    @Override
    public Period apiToPeriod(SmallFullApi smallFullApi) {

        Period period = apiToPeriodMapper.apiToPeriod(smallFullApi);

        boolean isSameYear = isSameYearFiler(smallFullApi);

        setCurrentPeriod(period, smallFullApi, isSameYear);
        setPreviousPeriod(period, smallFullApi, isSameYear);

        return period;
    }

    private void setCurrentPeriod(Period period, SmallFullApi smallFullApi, boolean isSameYear) {

        AccountingPeriodApi nextAccounts = smallFullApi.getNextAccounts();
        period.setCurrentPeriodStartOnFormatted(convertToDisplayDate(nextAccounts.getPeriodStartOn()));
        period.setCurrentPeriodEndOnFormatted(convertToDisplayDate(nextAccounts.getPeriodEndOn()));
        period.setCurrentPeriodBSDate(convertDateToBSDate(nextAccounts.getPeriodStartOn(),
                nextAccounts.getPeriodEndOn(), isSameYear));
    }

    private void setPreviousPeriod(Period period, SmallFullApi smallFullApi, boolean isSameYear) {

        if (isMultipleYearFiler(smallFullApi)) {
            AccountingPeriodApi lastAccounts = smallFullApi.getLastAccounts();
            period.setPreviousPeriodStartOnFormatted(convertToDisplayDate(lastAccounts.getPeriodStartOn()));
            period.setPreviousPeriodEndOnFormatted(convertToDisplayDate(lastAccounts.getPeriodEndOn()));
            period.setPreviousPeriodBSDate(convertDateToBSDate(lastAccounts.getPeriodStartOn(),
                    lastAccounts.getPeriodEndOn(), isSameYear));
        }
    }

    private boolean isMultipleYearFiler(SmallFullApi smallFullApi) {
        AccountingPeriodApi lastAccountsApi = smallFullApi.getLastAccounts();
        return lastAccountsApi != null && lastAccountsApi.getPeriodEndOn() != null;
    }

    private boolean isSameYearFiler(SmallFullApi smallFullApi) {
        if (isMultipleYearFiler(smallFullApi)) {
            AccountingPeriodApi lastAccountsApi = smallFullApi.getLastAccounts();
            AccountingPeriodApi nextAccountsApi = smallFullApi.getNextAccounts();
            return accountsDatesHelper.isSameYear(lastAccountsApi.getPeriodEndOn(), nextAccountsApi.getPeriodEndOn());
        }
        return false;
    }

    private String convertToDisplayDate(LocalDate date) {
        return accountsDatesHelper.convertLocalDateToDisplayDate(date);
    }

    private String convertDateToBSDate(LocalDate dateStartOn, LocalDate dateEndOn, Boolean isSameYear) {
        return accountsDatesHelper.generateBalanceSheetHeading(dateStartOn, dateEndOn, isSameYear);
    }
}
