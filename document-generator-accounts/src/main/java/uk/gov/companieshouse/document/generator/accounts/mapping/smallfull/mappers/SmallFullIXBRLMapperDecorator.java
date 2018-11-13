package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.mappers;

import uk.gov.companieshouse.api.model.accounts.smallfull.CurrentPeriodApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.PreviousPeriodApi;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.SmallFullApiData;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.SmallFullAccountIxbrl;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.BalanceSheet;

public class SmallFullIXBRLMapperDecorator implements SmallFullIXBRLMapper {

    private final SmallFullIXBRLMapper smallFullIXBRLMapper;

    public SmallFullIXBRLMapperDecorator(SmallFullIXBRLMapper smallFullIXBRLMapper) {
        this.smallFullIXBRLMapper = smallFullIXBRLMapper;
    }

    @Override
    public SmallFullAccountIxbrl mapSmallFullIXBRLModel(SmallFullApiData smallFullApiData) {

        SmallFullAccountIxbrl smallFullAccountIxbrl = smallFullIXBRLMapper.mapSmallFullIXBRLModel(smallFullApiData);
        smallFullAccountIxbrl.setBalanceSheet(setBalanceSheet(smallFullApiData.getCurrentPeriod(), smallFullApiData.getPreviousPeriod()));
        smallFullAccountIxbrl.setCompany(ApiToCompanyMapper.INSTANCE.apiToCompany(smallFullApiData.getCompanyProfile()));
        smallFullAccountIxbrl.setPeriod(ApiToPeriodMapper.INSTANCE.apiToPeriod(smallFullApiData.getCompanyProfile()));

        return smallFullAccountIxbrl;
    }

    private BalanceSheet setBalanceSheet(CurrentPeriodApi currentPeriod, PreviousPeriodApi previousPeriod) {

        BalanceSheet balanceSheet = new BalanceSheet();

        balanceSheet.setCalledUpSharedCapitalNotPaid(ApiToBalanceSheetMapper.INSTANCE.apiToCalledUpSharedCapitalNotPaid(currentPeriod, previousPeriod));
        balanceSheet.setOtherLiabilitiesOrAssets(ApiToBalanceSheetMapper.INSTANCE.apiToOtherLiabilitiesOrAssets(currentPeriod, previousPeriod));
        balanceSheet.setFixedAssets(ApiToBalanceSheetMapper.INSTANCE.apiToFixedAssets(currentPeriod, previousPeriod));
        balanceSheet.setCurrentAssets(ApiToBalanceSheetMapper.INSTANCE.apiToCurrentAssets(currentPeriod,previousPeriod));
        balanceSheet.setCapitalAndReserve(ApiToBalanceSheetMapper.INSTANCE.apiToCapitalAndReserve(currentPeriod, previousPeriod));

        return balanceSheet;
    }
}
