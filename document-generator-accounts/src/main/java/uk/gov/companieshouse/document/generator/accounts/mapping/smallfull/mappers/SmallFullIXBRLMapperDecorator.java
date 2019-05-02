package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.mappers;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import uk.gov.companieshouse.accountsdates.AccountsDatesHelper;
import uk.gov.companieshouse.accountsdates.impl.AccountsDatesHelperImpl;
import uk.gov.companieshouse.api.model.accounts.smallfull.AccountingPoliciesApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.BalanceSheetStatementsApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.CurrentPeriodApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.PreviousPeriodApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.Debtors.DebtorsApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.creditorsafteroneyear.CreditorsAfterOneYearApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.creditorswithinoneyear.CreditorsWithinOneYearApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.employees.EmployeesApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.stocks.StocksApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.tangible.TangibleApi;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.SmallFullApiData;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.SmallFullAccountIxbrl;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.accountingpolicies.AccountingPolicies;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.BalanceSheet;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.creditorsafteroneyear.CreditorsAfterOneYear;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.creditorswithinoneyear.CreditorsWithinOneYear;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.debtors.Debtors;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.employees.Employees;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.notes.tangible.TangibleAssets;

import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.notes.AdditionalNotes;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.notes.BalanceSheetNotes;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.notes.tangible.TangibleAssetsCost;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.notes.tangible.TangibleAssetsDepreciation;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.notes.tangible.TangibleAssetsNetBookValue;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.stocks.StocksNote;

public abstract class SmallFullIXBRLMapperDecorator implements SmallFullIXBRLMapper {

    @Autowired
    @Qualifier("delegate")
    private SmallFullIXBRLMapper smallFullIXBRLMapper;

    @Autowired
    private ApiToCompanyMapper apiToCompanyMapper;

    @Autowired
    private ApiToPeriodMapper apiToPeriodMapper;

    @Autowired
    private ApiToBalanceSheetMapper apiToBalanceSheetMapper;

    @Autowired
    private ApiToAccountingPoliciesMapper apiToAccountingPoliciesMapper;

    @Autowired
    private ApiToStocksMapper apiToStocksMapper;

    @Autowired
    private ApiToDebtorsMapper apiToDebtorsMapper;

    @Autowired
    private ApiToCreditorsWithinOneYearMapper apiToCreditorsWithinOneYearMapper;

    @Autowired
    private ApiToCreditorsAfterOneYearMapper apiToCreditorsAfterOneYearMapper;

    @Autowired
    private ApiToEmployeesMapper apiToEmployeesMapper;

    @Autowired
    private ApiToTangibleAssetsNoteMapper apiToTangibleAssetsNoteMapper;

    private AccountsDatesHelper accountsDatesHelper = new AccountsDatesHelperImpl();

    @Override
    public SmallFullAccountIxbrl mapSmallFullIXBRLModel(SmallFullApiData smallFullApiData) {

        SmallFullAccountIxbrl smallFullAccountIxbrl =
                smallFullIXBRLMapper.mapSmallFullIXBRLModel(smallFullApiData);
        smallFullAccountIxbrl.setBalanceSheet(
                setBalanceSheet(smallFullApiData.getCurrentPeriod(),
                        smallFullApiData.getPreviousPeriod(),
                        smallFullApiData.getBalanceSheetStatements()));
        smallFullAccountIxbrl.setCompany(apiToCompanyMapper.apiToCompany(smallFullApiData.getCompanyProfile()));
        smallFullAccountIxbrl.setPeriod(apiToPeriodMapper.apiToPeriod(smallFullApiData.getCompanyAccounts()));

        if (smallFullApiData.getApproval() != null && smallFullApiData.getApproval().getDate() != null) {
            smallFullAccountIxbrl.setApprovalDate(convertToDisplayDate(smallFullApiData.getApproval().getDate()));
        }

        AdditionalNotes additionalNotes = new AdditionalNotes();
        Boolean hasAdditionalNotes = false;

        if (smallFullApiData.getAccountingPolicies() != null) {

            additionalNotes.setAccountingPolicies(mapAccountingPolicies(smallFullApiData.getAccountingPolicies()));

            hasAdditionalNotes = true;
        }

        if (smallFullApiData.getEmployees() != null) {

            additionalNotes.setEmployees(mapEmployees(smallFullApiData.getEmployees()));

            hasAdditionalNotes = true;
        }

        BalanceSheetNotes balanceSheetNotes = new BalanceSheetNotes();
        Boolean hasBalanceSheetNotes = false;

        if (smallFullApiData.getTangibleAssets() != null) {

            balanceSheetNotes.setTangibleAssets(mapTangibleAssets(smallFullApiData.getTangibleAssets()));

            hasBalanceSheetNotes = true;
        }

        if (smallFullApiData.getStocks() != null) {

            balanceSheetNotes.setStocksNote(mapStocks(smallFullApiData.getStocks()));

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
                balanceSheet.setCalledUpSharedCapitalNotPaid(apiToBalanceSheetMapper.apiToCalledUpSharedCapitalNotPaid(currentPeriod, previousPeriod));
            }
            if (currentPeriod.getBalanceSheet().getOtherLiabilitiesOrAssets() != null) {
                balanceSheet.setOtherLiabilitiesOrAssets(apiToBalanceSheetMapper.apiToOtherLiabilitiesOrAssets(currentPeriod, previousPeriod));
            }
            if (currentPeriod.getBalanceSheet().getFixedAssets() != null) {
                balanceSheet.setFixedAssets(apiToBalanceSheetMapper.apiToFixedAssets(currentPeriod, previousPeriod));
            }
            if (currentPeriod.getBalanceSheet().getCurrentAssets() != null) {
                balanceSheet.setCurrentAssets(apiToBalanceSheetMapper.apiToCurrentAssets(currentPeriod, previousPeriod));
            }
            if (currentPeriod.getBalanceSheet().getCapitalAndReserves() != null) {
                balanceSheet.setCapitalAndReserve(apiToBalanceSheetMapper.apiToCapitalAndReserve(currentPeriod, previousPeriod));
            }
        }

        if (balanceSheetStatements != null) {
            balanceSheet.setBalanceSheetStatements(apiToBalanceSheetMapper.apiToStatements(balanceSheetStatements));
        }

        return balanceSheet;
    }

    private AccountingPolicies mapAccountingPolicies(AccountingPoliciesApi accountingPolicies) {

        return apiToAccountingPoliciesMapper
                .apiToAccountingPolicies(accountingPolicies);
    }

    private StocksNote mapStocks(StocksApi stocks) {

        return apiToStocksMapper
                .apiToStocks(stocks.getCurrentPeriod(),
                        stocks.getPreviousPeriod());
    }
    
    private Debtors mapDebtors(DebtorsApi debtors) {

        return apiToDebtorsMapper
                .apiToDebtors(debtors.getDebtorsCurrentPeriod(),
                        debtors.getDebtorsPreviousPeriod());
    }

    private Employees mapEmployees(EmployeesApi employees) {

        return apiToEmployeesMapper.apiToEmployees(employees.getCurrentPeriod(), employees.getPreviousPeriod());
    }

    private CreditorsWithinOneYear mapCreditorsWithinOneYear(CreditorsWithinOneYearApi creditorsWithinOneYearApi) {

        return apiToCreditorsWithinOneYearMapper
                .apiToCreditorsWithinOneYear(creditorsWithinOneYearApi.getCreditorsWithinOneYearCurrentPeriod(),
                        creditorsWithinOneYearApi.getCreditorsWithinOneYearPreviousPeriod());
    }
    
    private CreditorsAfterOneYear mapCreditorsAfterOneYear(CreditorsAfterOneYearApi creditorsAfterOneYearApi) {

        return apiToCreditorsAfterOneYearMapper
                .apiToCreditorsAfterOneYear(creditorsAfterOneYearApi.getCurrentPeriod(),
                        creditorsAfterOneYearApi.getPreviousPeriod());
    }

    private TangibleAssets mapTangibleAssets(TangibleApi tangible) {

        TangibleAssets tangibleAssets = apiToTangibleAssetsNoteMapper.apiToTangibleAssetsNoteAdditionalInformation(tangible);

        tangibleAssets.setCost(mapTangibleAssetsCost(tangible));
        tangibleAssets.setDepreciation(mapTangibleAssetsDepreciation(tangible));
        tangibleAssets.setNetBookValue(mapTangibleAssetsNetBookValue(tangible));

        return tangibleAssets;
    }

    private TangibleAssetsCost mapTangibleAssetsCost(TangibleApi tangible) {

        TangibleAssetsCost cost = new TangibleAssetsCost();

        cost.setAdditions(apiToTangibleAssetsNoteMapper.apiToTangibleAssetsCostAdditionsMapper(tangible));
        cost.setAtPeriodEnd(apiToTangibleAssetsNoteMapper.apiToTangibleAssetsCostAtPeriodEndMapper(tangible));
        cost.setAtPeriodStart(apiToTangibleAssetsNoteMapper.apiToTangibleAssetsCostAtPeriodStartMapper(tangible));
        cost.setDisposals(apiToTangibleAssetsNoteMapper.apiToTangibleAssetsCostDisposalsMapper(tangible));
        cost.setRevaluations(apiToTangibleAssetsNoteMapper.apiToTangibleAssetsCostRevaluationsMapper(tangible));
        cost.setTransfers(apiToTangibleAssetsNoteMapper.apiToTangibleAssetsCostTransfersMapper(tangible));

        return cost;
    }

    private TangibleAssetsDepreciation mapTangibleAssetsDepreciation(TangibleApi tangible) {

        TangibleAssetsDepreciation depreciation = new TangibleAssetsDepreciation();

        depreciation.setAtPeriodEnd(apiToTangibleAssetsNoteMapper
                .apiToTangibleAssetsDepreciationAtPeriodEndMapper(tangible));
        depreciation.setAtPeriodStart(apiToTangibleAssetsNoteMapper
                .apiToTangibleAssetsDepreciationAtPeriodStartMapper(tangible));
        depreciation.setChargeForYear(apiToTangibleAssetsNoteMapper
                .apiToTangibleAssetsDepreciationChargeForYearMapper(tangible));
        depreciation.setOnDisposals(apiToTangibleAssetsNoteMapper
                .apiToTangibleAssetsDepreciationOnDisposalsMapper(tangible));
        depreciation.setOtherAdjustments(apiToTangibleAssetsNoteMapper
                .apiToTangibleAssetsDepreciationOtherAdjustmentsMapper(tangible));

        return depreciation;
    }

    private TangibleAssetsNetBookValue mapTangibleAssetsNetBookValue(TangibleApi tangible) {

        TangibleAssetsNetBookValue netBookValue = new TangibleAssetsNetBookValue();

        netBookValue.setCurrentPeriod(apiToTangibleAssetsNoteMapper
                .apiToTangibleAssetsNetBookValueCurrentPeriodMapper(tangible));
        netBookValue.setPreviousPeriod(apiToTangibleAssetsNoteMapper
                .apiToTangibleAssetsNetBookValuePreviousPeriodMapper(tangible));

        return netBookValue;
    }

    private String convertToDisplayDate(LocalDate date) {
        return accountsDatesHelper.convertLocalDateToDisplayDate(date);
    }
}
