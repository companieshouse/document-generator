package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import uk.gov.companieshouse.accountsdates.AccountsDatesHelper;
import uk.gov.companieshouse.accountsdates.impl.AccountsDatesHelperImpl;
import uk.gov.companieshouse.api.model.accounts.AccountingPeriodApi;
import uk.gov.companieshouse.api.model.accounts.CompanyAccountsApi;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.period.Period;

import java.time.LocalDate;

public abstract class ApiToPeriodMapperDecorator implements ApiToPeriodMapper {

    @Autowired
    @Qualifier("delegate")
    private ApiToPeriodMapper apiToPeriodMapper;

    private AccountsDatesHelper accountsDatesHelper = new AccountsDatesHelperImpl();

    @Override
    public Period apiToPeriod(CompanyAccountsApi companyAccountsApi) {

        Period period = apiToPeriodMapper.apiToPeriod(companyAccountsApi);

        boolean isSameYear = isSameYearFiler(companyAccountsApi);

        setCurrentPeriod(period, companyAccountsApi, isSameYear);
        setPreviousPeriod(period, companyAccountsApi, isSameYear);

        return period;
    }

    private void setCurrentPeriod(Period period, CompanyAccountsApi companyAccountsApi, boolean isSameYear) {

        AccountingPeriodApi nextAccounts = companyAccountsApi.getNextAccounts();
        period.setCurrentPeriodStartOnFormatted(convertToDisplayDate(nextAccounts.getPeriodStartOn()));
        period.setCurrentPeriodEndOnFormatted(convertToDisplayDate(nextAccounts.getPeriodEndOn()));
        period.setCurrentPeriodBSDate(convertDateToBSDate(nextAccounts.getPeriodStartOn(),
                nextAccounts.getPeriodEndOn(), isSameYear));
    }

    private void setPreviousPeriod(Period period, CompanyAccountsApi companyAccountsApi, boolean isSameYear) {

        if (isMultipleYearFiler(companyAccountsApi)) {
            AccountingPeriodApi lastAccounts = companyAccountsApi.getLastAccounts();
            period.setPreviousPeriodStartOnFormatted(convertToDisplayDate(lastAccounts.getPeriodStartOn()));
            period.setPreviousPeriodEndOnFormatted(convertToDisplayDate(lastAccounts.getPeriodEndOn()));
            period.setPreviousPeriodBSDate(convertDateToBSDate(lastAccounts.getPeriodStartOn(),
                    lastAccounts.getPeriodEndOn(), isSameYear));
        }
    }

    private boolean isMultipleYearFiler(CompanyAccountsApi companyAccountsApi) {
        AccountingPeriodApi lastAccountsApi = companyAccountsApi.getLastAccounts();
        return lastAccountsApi != null && lastAccountsApi.getPeriodEndOn() != null;
    }

    private boolean isSameYearFiler(CompanyAccountsApi companyAccountsApi) {
        if (isMultipleYearFiler(companyAccountsApi)) {
            AccountingPeriodApi lastAccountsApi = companyAccountsApi.getLastAccounts();
            AccountingPeriodApi nextAccountsApi = companyAccountsApi.getNextAccounts();
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
