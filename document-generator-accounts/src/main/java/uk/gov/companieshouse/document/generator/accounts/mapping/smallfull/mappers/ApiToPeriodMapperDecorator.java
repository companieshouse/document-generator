package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import uk.gov.companieshouse.accountsdates.AccountsDatesHelper;
import uk.gov.companieshouse.accountsdates.impl.AccountsDatesHelperImpl;
import uk.gov.companieshouse.api.model.company.CompanyProfileApi;
import uk.gov.companieshouse.api.model.company.account.LastAccountsApi;
import uk.gov.companieshouse.api.model.company.account.NextAccountsApi;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.period.Period;

import java.time.LocalDate;


public abstract class ApiToPeriodMapperDecorator implements ApiToPeriodMapper {

    @Autowired
    @Qualifier("delegate")
    private ApiToPeriodMapper apiToPeriodMapper;

    private AccountsDatesHelper accountsDatesHelper = new AccountsDatesHelperImpl();

    @Override
    public Period apiToPeriod(CompanyProfileApi companyProfile) {

        Period period = apiToPeriodMapper.apiToPeriod(companyProfile);

        boolean isSameYear = isSameYearFiler(companyProfile);

        setCurrentPeriod(period, companyProfile, isSameYear);
        setPreviousPeriod(period, companyProfile, isSameYear);

        return period;
    }

    private void setCurrentPeriod(Period period, CompanyProfileApi companyProfile, boolean isSameYear) {

        NextAccountsApi nextAccounts = companyProfile.getAccounts().getNextAccounts();
        period.setCurrentPeriodStartOnFormatted(convertToDisplayDate(nextAccounts.getPeriodStartOn()));
        period.setCurrentPeriodEndOnFormatted(convertToDisplayDate(nextAccounts.getPeriodEndOn()));
        period.setCurrentPeriodBSDate(convertDateToBSDate(nextAccounts.getPeriodStartOn(),
                nextAccounts.getPeriodEndOn(), isSameYear));
    }

    private void setPreviousPeriod(Period period, CompanyProfileApi companyProfile, boolean isSameYear) {

        if (isMultipleYearFiler(companyProfile)) {
            LastAccountsApi lastAccounts = companyProfile.getAccounts().getLastAccounts();
            period.setPreviousPeriodStartOnFormatted(convertToDisplayDate(lastAccounts.getPeriodStartOn()));
            period.setPreviousPeriodEndOnFormatted(convertToDisplayDate(lastAccounts.getPeriodEndOn()));
            period.setPreviousPeriodBSDate(convertDateToBSDate(lastAccounts.getPeriodStartOn(),
                    lastAccounts.getPeriodEndOn(), isSameYear));
        }
    }

    private boolean isMultipleYearFiler(CompanyProfileApi companyProfile) {
        LastAccountsApi lastAccountsApi = companyProfile.getAccounts().getLastAccounts();
        return lastAccountsApi != null && lastAccountsApi.getPeriodEndOn() != null;
    }

    private boolean isSameYearFiler(CompanyProfileApi companyProfile) {
        if (isMultipleYearFiler(companyProfile)) {
            LastAccountsApi lastAccountsApi = companyProfile.getAccounts().getLastAccounts();
            NextAccountsApi nextAccountsApi = companyProfile.getAccounts().getNextAccounts();
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
