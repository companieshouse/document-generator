package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.mappers;

import uk.gov.companieshouse.accountsdates.AccountsDatesHelper;
import uk.gov.companieshouse.accountsdates.impl.AccountsDatesHelperImpl;
import uk.gov.companieshouse.api.model.accounts.smallfull.CurrentPeriodApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.PreviousPeriodApi;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.SmallFullApiData;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.SmallFullAccountIxbrl;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.BalanceSheet;

import java.time.LocalDate;

public abstract class SmallFullIXBRLMapperDecorator implements SmallFullIXBRLMapper {

    private final SmallFullIXBRLMapper smallFullIXBRLMapper;

    private AccountsDatesHelper accountsDatesHelper = new AccountsDatesHelperImpl();

    public SmallFullIXBRLMapperDecorator(SmallFullIXBRLMapper smallFullIXBRLMapper) {
        this.smallFullIXBRLMapper = smallFullIXBRLMapper;
    }

    @Override
    public SmallFullAccountIxbrl mapSmallFullIXBRLModel(SmallFullApiData smallFullApiData) {

        SmallFullAccountIxbrl smallFullAccountIxbrl = smallFullIXBRLMapper.mapSmallFullIXBRLModel(smallFullApiData);
        smallFullAccountIxbrl.setBalanceSheet(setBalanceSheet(smallFullApiData.getCurrentPeriod(), smallFullApiData.getPreviousPeriod()));
        smallFullAccountIxbrl.setCompany(ApiToCompanyMapper.INSTANCE.apiToCompany(smallFullApiData.getCompanyProfile()));
        smallFullAccountIxbrl.setPeriod(ApiToPeriodMapper.INSTANCE.apiToPeriod(smallFullApiData.getCompanyProfile()));

        if (smallFullApiData.getApproval() != null & smallFullApiData.getApproval().getDate() != null) {
            smallFullAccountIxbrl.setApprovalDate(convertToDisplayDate(smallFullApiData.getApproval().getDate()));
        }

        return smallFullAccountIxbrl;
    }

    private BalanceSheet setBalanceSheet(CurrentPeriodApi currentPeriod, PreviousPeriodApi previousPeriod) {

        BalanceSheet balanceSheet = new BalanceSheet();

        if (currentPeriod.getBalanceSheetApi() != null) {
            if (currentPeriod.getBalanceSheetApi().getCalledUpShareCapitalNotPaid() != null) {
                balanceSheet.setCalledUpSharedCapitalNotPaid(ApiToBalanceSheetMapper.INSTANCE.apiToCalledUpSharedCapitalNotPaid(currentPeriod, previousPeriod));
            }
            if (currentPeriod.getBalanceSheetApi().getOtherLiabilitiesOrAssetsApi() != null) {
                balanceSheet.setOtherLiabilitiesOrAssets(ApiToBalanceSheetMapper.INSTANCE.apiToOtherLiabilitiesOrAssets(currentPeriod, previousPeriod));
            }
            if (currentPeriod.getBalanceSheetApi().getFixedAssetsApi() != null) {
                balanceSheet.setFixedAssets(ApiToBalanceSheetMapper.INSTANCE.apiToFixedAssets(currentPeriod, previousPeriod));
            }
            if (currentPeriod.getBalanceSheetApi().getCurrentAssetsApi() != null) {
                balanceSheet.setCurrentAssets(ApiToBalanceSheetMapper.INSTANCE.apiToCurrentAssets(currentPeriod,previousPeriod));
            }
            if (currentPeriod.getBalanceSheetApi().getCapitalAndReservesApi() != null) {
                balanceSheet.setCapitalAndReserve(ApiToBalanceSheetMapper.INSTANCE.apiToCapitalAndReserve(currentPeriod, previousPeriod));
            }
        }

        return balanceSheet;
    }

    private String convertToDisplayDate(LocalDate date) {
        return accountsDatesHelper.convertLocalDateToDisplayDate(date);
    }
}
