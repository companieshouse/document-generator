package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.mappers;

import uk.gov.companieshouse.accountsdates.AccountsDatesHelper;
import uk.gov.companieshouse.accountsdates.impl.AccountsDatesHelperImpl;
import uk.gov.companieshouse.api.model.accounts.smallfull.AccountingPoliciesApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.BalanceSheetStatementsApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.CurrentPeriodApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.Debtors.DebtorsApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.creditorsafteroneyear.CreditorsAfterOneYearApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.creditorswithinoneyear.CreditorsWithinOneYearApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.PreviousPeriodApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.tangible.TangibleApi;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.SmallFullApiData;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.SmallFullAccountIxbrl;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.BalanceSheet;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.creditorsafteroneyear.CreditorsAfterOneYear;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.creditorswithinoneyear.CreditorsWithinOneYear;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.accountingpolicies.AccountingPolicies;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.debtors.Debtors;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.notes.tangible.TangibleAssets;

import java.time.LocalDate;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.notes.AdditionalNotes;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.notes.tangible.TangibleAssetsCost;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.notes.tangible.TangibleAssetsDepreciation;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.notes.tangible.TangibleAssetsNetBookValue;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.notes.BalanceSheetNotes;

public abstract class SmallFullIXBRLMapperDecorator implements SmallFullIXBRLMapper {

    private final SmallFullIXBRLMapper smallFullIXBRLMapper;

    private AccountsDatesHelper accountsDatesHelper = new AccountsDatesHelperImpl();

    public SmallFullIXBRLMapperDecorator(SmallFullIXBRLMapper smallFullIXBRLMapper) {
        this.smallFullIXBRLMapper = smallFullIXBRLMapper;
    }

    @Override
    public SmallFullAccountIxbrl mapSmallFullIXBRLModel(SmallFullApiData smallFullApiData) {

        SmallFullAccountIxbrl smallFullAccountIxbrl =
                smallFullIXBRLMapper.mapSmallFullIXBRLModel(smallFullApiData);
        smallFullAccountIxbrl.setBalanceSheet(
                setBalanceSheet(smallFullApiData.getCurrentPeriod(),
                        smallFullApiData.getPreviousPeriod(),
                        smallFullApiData.getBalanceSheetStatements()));
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

        BalanceSheetNotes balanceSheetNotes = new BalanceSheetNotes();
        Boolean hasBalanceSheetNotes = false;

        if (smallFullApiData.getTangibleAssets() != null) {

            balanceSheetNotes.setTangibleAssets(mapTangibleAssets(smallFullApiData.getTangibleAssets()));

            hasBalanceSheetNotes = true;
        }

        if (smallFullApiData.getDebtors() != null) {

            balanceSheetNotes.setDebtorsNote(mapDebtors(smallFullApiData.getDebtors()));

            hasBalanceSheetNotes = true;
        }
        
        if (smallFullApiData.getCreditorsWithinOneYear() != null) {

            balanceSheetNotes.setCreditorsWithinOneYearNote(mapCreditorsWithinOneYear(smallFullApiData.getCreditorsWithinOneYear()));

            hasBalanceSheetNotes = true;
        }
        
        if (smallFullApiData.getCreditorsAfterOneYear() != null) {

            balanceSheetNotes.setCreditorsAfterOneYearNote(mapCreditorsAfterOneYear(smallFullApiData.getCreditorsAfterOneYear()));

            hasBalanceSheetNotes = true;
        }        

        //We only want to set the additional notes if we have any
        if (hasAdditionalNotes) {
            smallFullAccountIxbrl.setAdditionalNotes(additionalNotes);
        }

        //We only want to set the balance sheet notes if we have any
        if (hasBalanceSheetNotes) {
            smallFullAccountIxbrl.setBalanceSheetNotes(balanceSheetNotes);
        }

        return smallFullAccountIxbrl;
    }

    private BalanceSheet setBalanceSheet(CurrentPeriodApi currentPeriod,
            PreviousPeriodApi previousPeriod, BalanceSheetStatementsApi balanceSheetStatements) {

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
                balanceSheet.setCurrentAssets(ApiToBalanceSheetMapper.INSTANCE.apiToCurrentAssets(currentPeriod, previousPeriod));
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

    private Debtors mapDebtors(DebtorsApi debtors) {

        return ApiToDebtorsMapper.INSTANCE
                .apiToDebtors(debtors.getDebtorsCurrentPeriod(),
                        debtors.getDebtorsPreviousPeriod());
    }
    
    private CreditorsWithinOneYear mapCreditorsWithinOneYear(CreditorsWithinOneYearApi creditorsWithinOneYearApi) {

        return ApiToCreditorsWithinOneYearMapper.INSTANCE
                .apiToCreditorsWithinOneYear(creditorsWithinOneYearApi.getCreditorsWithinOneYearCurrentPeriod(),
                        creditorsWithinOneYearApi.getCreditorsWithinOneYearPreviousPeriod());
    }
    
    private CreditorsAfterOneYear mapCreditorsAfterOneYear(CreditorsAfterOneYearApi creditorsAfterOneYearApi) {

        return ApiToCreditorsAfterOneYearMapper.INSTANCE
                .apiToCreditorsAfterOneYear(creditorsAfterOneYearApi.getCurrentPeriod(),
                        creditorsAfterOneYearApi.getPreviousPeriod());
    }

    private TangibleAssets mapTangibleAssets(TangibleApi tangible) {

        TangibleAssets tangibleAssets = ApiToTangibleAssetsNoteMapper.INSTANCE.apiToTangibleAssetsNoteAdditionalInformation(tangible);

        tangibleAssets.setCost(mapTangibleAssetsCost(tangible));
        tangibleAssets.setDepreciation(mapTangibleAssetsDepreciation(tangible));
        tangibleAssets.setNetBookValue(mapTangibleAssetsNetBookValue(tangible));

        return tangibleAssets;
    }

    private TangibleAssetsCost mapTangibleAssetsCost(TangibleApi tangible) {

        TangibleAssetsCost cost = new TangibleAssetsCost();

        cost.setAdditions(ApiToTangibleAssetsNoteMapper.INSTANCE.apiToTangibleAssetsCostAdditionsMapper(tangible));
        cost.setAtPeriodEnd(ApiToTangibleAssetsNoteMapper.INSTANCE.apiToTangibleAssetsCostAtPeriodEndMapper(tangible));
        cost.setAtPeriodStart(ApiToTangibleAssetsNoteMapper.INSTANCE.apiToTangibleAssetsCostAtPeriodStartMapper(tangible));
        cost.setDisposals(ApiToTangibleAssetsNoteMapper.INSTANCE.apiToTangibleAssetsCostDisposalsMapper(tangible));
        cost.setRevaluations(ApiToTangibleAssetsNoteMapper.INSTANCE.apiToTangibleAssetsCostRevaluationsMapper(tangible));
        cost.setTransfers(ApiToTangibleAssetsNoteMapper.INSTANCE.apiToTangibleAssetsCostTransfersMapper(tangible));

        return cost;
    }

    private TangibleAssetsDepreciation mapTangibleAssetsDepreciation(TangibleApi tangible) {

        TangibleAssetsDepreciation depreciation = new TangibleAssetsDepreciation();

        depreciation.setAtPeriodEnd(ApiToTangibleAssetsNoteMapper.INSTANCE
                .apiToTangibleAssetsDepreciationAtPeriodEndMapper(tangible));
        depreciation.setAtPeriodStart(ApiToTangibleAssetsNoteMapper.INSTANCE
                .apiToTangibleAssetsDepreciationAtPeriodStartMapper(tangible));
        depreciation.setChargeForYear(ApiToTangibleAssetsNoteMapper.INSTANCE
                .apiToTangibleAssetsDepreciationChargeForYearMapper(tangible));
        depreciation.setOnDisposals(ApiToTangibleAssetsNoteMapper.INSTANCE
                .apiToTangibleAssetsDepreciationOnDisposalsMapper(tangible));
        depreciation.setOtherAdjustments(ApiToTangibleAssetsNoteMapper.INSTANCE
                .apiToTangibleAssetsDepreciationOtherAdjustmentsMapper(tangible));

        return depreciation;
    }

    private TangibleAssetsNetBookValue mapTangibleAssetsNetBookValue(TangibleApi tangible) {

        TangibleAssetsNetBookValue netBookValue = new TangibleAssetsNetBookValue();

        netBookValue.setCurrentPeriod(ApiToTangibleAssetsNoteMapper.INSTANCE
                .apiToTangibleAssetsNetBookValueCurrentPeriodMapper(tangible));
        netBookValue.setPreviousPeriod(ApiToTangibleAssetsNoteMapper.INSTANCE
                .apiToTangibleAssetsNetBookValuePreviousPeriodMapper(tangible));

        return netBookValue;
    }

    private String convertToDisplayDate(LocalDate date) {
        return accountsDatesHelper.convertLocalDateToDisplayDate(date);
    }
}
