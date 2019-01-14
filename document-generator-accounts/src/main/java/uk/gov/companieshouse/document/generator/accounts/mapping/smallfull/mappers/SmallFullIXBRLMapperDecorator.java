package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.mappers;

import uk.gov.companieshouse.accountsdates.AccountsDatesHelper;
import uk.gov.companieshouse.accountsdates.impl.AccountsDatesHelperImpl;
import uk.gov.companieshouse.api.model.accounts.smallfull.AccountingPoliciesApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.BalanceSheetStatementsApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.CurrentPeriodApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.PreviousPeriodApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.tangible.TangibleApi;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.SmallFullApiData;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.SmallFullAccountIxbrl;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.BalanceSheet;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.accountingpolicies.AccountingPolicies;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.notes.tangible.TangibleAssets;

import java.time.LocalDate;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.notes.AdditionalNotes;

public abstract class SmallFullIXBRLMapperDecorator implements SmallFullIXBRLMapper {

    private final SmallFullIXBRLMapper smallFullIXBRLMapper;

    private AccountsDatesHelper accountsDatesHelper = new AccountsDatesHelperImpl();

    public SmallFullIXBRLMapperDecorator(SmallFullIXBRLMapper smallFullIXBRLMapper) {
        this.smallFullIXBRLMapper = smallFullIXBRLMapper;
    }

    @Override
    public SmallFullAccountIxbrl mapSmallFullIXBRLModel(SmallFullApiData smallFullApiData) {

        SmallFullAccountIxbrl smallFullAccountIxbrl = smallFullIXBRLMapper.mapSmallFullIXBRLModel(smallFullApiData);
        smallFullAccountIxbrl.setBalanceSheet(
                setBalanceSheet(smallFullApiData.getCurrentPeriod(), smallFullApiData.getPreviousPeriod(), smallFullApiData.getBalanceSheetStatements()));
        smallFullAccountIxbrl.setCompany(ApiToCompanyMapper.INSTANCE.apiToCompany(smallFullApiData.getCompanyProfile()));
        smallFullAccountIxbrl.setPeriod(ApiToPeriodMapper.INSTANCE.apiToPeriod(smallFullApiData.getCompanyProfile()));

        if (smallFullApiData.getApproval() != null && smallFullApiData.getApproval().getDate() != null) {
            smallFullAccountIxbrl.setApprovalDate(convertToDisplayDate(smallFullApiData.getApproval().getDate()));
        }

        AdditionalNotes additionalNotes = new AdditionalNotes();
        Boolean hasAdditionalNotes = false;

        if (smallFullApiData.getAccountingPolicies() != null) {

            additionalNotes.setAccountingPolicies(mapAccountingPolicies(smallFullApiData.getAccountingPolicies()));

            hasAdditionalNotes = true;
        }

        if (smallFullApiData.getTangibleAssets() != null) {

            additionalNotes.setTangibleAssets(mapTangibleAssets(smallFullApiData.getTangibleAssets()));

            hasAdditionalNotes = true;
        }

        //We only want to set the additional notes if we have any
        if (hasAdditionalNotes) {
            smallFullAccountIxbrl.setAdditionalNotes(additionalNotes);
        }

        return smallFullAccountIxbrl;
    }

    private BalanceSheet setBalanceSheet(CurrentPeriodApi currentPeriod, PreviousPeriodApi previousPeriod, BalanceSheetStatementsApi balanceSheetStatements) {

        BalanceSheet balanceSheet = new BalanceSheet();

        if (currentPeriod.getBalanceSheet() != null) {
            if (currentPeriod.getBalanceSheet().getCalledUpShareCapitalNotPaid() != null) {
                balanceSheet.setCalledUpSharedCapitalNotPaid(ApiToBalanceSheetMapper.INSTANCE.apiToCalledUpSharedCapitalNotPaid(currentPeriod, previousPeriod));
            }
            if (currentPeriod.getBalanceSheet().getOtherLiabilitiesOrAssets() != null) {
                balanceSheet.setOtherLiabilitiesOrAssets(ApiToBalanceSheetMapper.INSTANCE.apiToOtherLiabilitiesOrAssets(currentPeriod, previousPeriod));
            }
            if (currentPeriod.getBalanceSheet().getFixedAssets() != null) {
                balanceSheet.setFixedAssets(ApiToBalanceSheetMapper.INSTANCE.apiToFixedAssets(currentPeriod, previousPeriod));
            }
            if (currentPeriod.getBalanceSheet().getCurrentAssets() != null) {
                balanceSheet.setCurrentAssets(ApiToBalanceSheetMapper.INSTANCE.apiToCurrentAssets(currentPeriod,previousPeriod));
            }
            if (currentPeriod.getBalanceSheet().getCapitalAndReserves() != null) {
                balanceSheet.setCapitalAndReserve(ApiToBalanceSheetMapper.INSTANCE.apiToCapitalAndReserve(currentPeriod, previousPeriod));
            }
        }

        if (balanceSheetStatements != null) {
            balanceSheet.setBalanceSheetStatements(ApiToBalanceSheetMapper.INSTANCE.apiToStatements(balanceSheetStatements));
        }

        return balanceSheet;
    }

    private AccountingPolicies mapAccountingPolicies(AccountingPoliciesApi accountingPolicies) {

        return ApiToAccountingPoliciesMapper.INSTANCE
                .apiToAccountingPolicies(accountingPolicies);
    }

    private TangibleAssets mapTangibleAssets(TangibleApi tangible) {

        return ApiToTangibleAssetsNoteMapper.INSTANCE.apiToTangibleAssetsNote(tangible);
    }

    private String convertToDisplayDate(LocalDate date) {
        return accountsDatesHelper.convertLocalDateToDisplayDate(date);
    }
}
