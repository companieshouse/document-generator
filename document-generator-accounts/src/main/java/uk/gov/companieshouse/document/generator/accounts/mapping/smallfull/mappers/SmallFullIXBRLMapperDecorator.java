package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.mappers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import uk.gov.companieshouse.accountsdates.AccountsDatesHelper;
import uk.gov.companieshouse.accountsdates.impl.AccountsDatesHelperImpl;
import uk.gov.companieshouse.api.model.accounts.directorsreport.ApprovalApi;
import uk.gov.companieshouse.api.model.accounts.directorsreport.DirectorApi;
import uk.gov.companieshouse.api.model.accounts.directorsreport.DirectorsReportApi;
import uk.gov.companieshouse.api.model.accounts.directorsreport.SecretaryApi;
import uk.gov.companieshouse.api.model.accounts.directorsreport.StatementsApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.AccountingPoliciesApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.BalanceSheetStatementsApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.CurrentPeriodApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.PreviousPeriodApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.Debtors.DebtorsApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.creditorsafteroneyear.CreditorsAfterOneYearApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.creditorswithinoneyear.CreditorsWithinOneYearApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.currentassetsinvestments.CurrentAssetsInvestmentsApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.fixedassetsinvestments.FixedAssetsInvestmentsApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.employees.EmployeesApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.intangible.IntangibleApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.stocks.StocksApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.tangible.TangibleApi;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.SmallFullApiData;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.SmallFullAccountIxbrl;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.accountingpolicies.AccountingPolicies;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.BalanceSheet;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.creditorsafteroneyear.CreditorsAfterOneYear;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.creditorswithinoneyear.CreditorsWithinOneYear;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.currentassetsinvestments.CurrentAssetsInvestments;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.debtors.Debtors;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.directorsreport.Approval;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.directorsreport.Directors;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.directorsreport.DirectorsReport;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.fixedassetsinvestments.FixedAssetsInvestments;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.employees.Employees;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.notes.intangible.IntangibleAssets;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.notes.intangible.IntangibleAssetsAmortisation;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.notes.intangible.IntangibleAssetsCost;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.notes.intangible.IntangibleAssetsNetBookValue;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.notes.tangible.TangibleAssets;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.notes.AdditionalNotes;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.notes.BalanceSheetNotes;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.notes.tangible.TangibleAssetsCost;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.notes.tangible.TangibleAssetsDepreciation;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.notes.tangible.TangibleAssetsNetBookValue;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.stocks.StocksNote;

import static java.util.Arrays.asList;
import static java.util.Arrays.sort;
import static java.util.Map.Entry.comparingByKey;
import static java.util.stream.Collectors.toMap;

public abstract class SmallFullIXBRLMapperDecorator implements SmallFullIXBRLMapper {

    @Autowired
    @Qualifier("delegate")
    private SmallFullIXBRLMapper smallFullIXBRLMapper;

    @Autowired
    private ApiToProfitAndLossMapper apiToProfitAndLossMapper;

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
    private ApiToCurrentAssetsInvestmentsMapper apiToCurrentAssetsInvestmentsMapper;

    @Autowired
    private ApiToTangibleAssetsNoteMapper apiToTangibleAssetsNoteMapper;

    @Autowired
    private ApiToIntangibleAssetsNoteMapper apiToIntangibleAssetsNoteMapper;

    @Autowired
    private ApiToFixedAssetsInvestmentsMapper apiToFixedAssetsInvestmentsMapper;

    @Autowired
    private ApiToDirectorsReportMapper apiToDirectorsReportMapper;

    private AccountsDatesHelper accountsDatesHelper = new AccountsDatesHelperImpl();

    @Override
    public SmallFullAccountIxbrl mapSmallFullIXBRLModel(SmallFullApiData smallFullApiData) {

        SmallFullAccountIxbrl smallFullAccountIxbrl =
                smallFullIXBRLMapper.mapSmallFullIXBRLModel(smallFullApiData);

        if (smallFullApiData.getCurrentPeriodProfitAndLoss() != null) {
            smallFullAccountIxbrl.setProfitAndLoss(
                    apiToProfitAndLossMapper.apiToProfitAndLoss(
                            smallFullApiData.getCurrentPeriodProfitAndLoss(),
                            smallFullApiData.getPreviousPeriodProfitAndLoss()));
        }

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

        if (smallFullApiData.getIntangibleAssets() != null) {

            balanceSheetNotes.setIntangibleAssets(mapIntangibleAssets(smallFullApiData.getIntangibleAssets()));

            hasBalanceSheetNotes = true;
        }

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

        if (smallFullApiData.getCurrentAssetsInvestments() != null) {

            balanceSheetNotes.setCurrentAssetsInvestments(mapCurrentAssetsInvestments(smallFullApiData.getCurrentAssetsInvestments()));

            hasBalanceSheetNotes = true;
        }

        if (smallFullApiData.getFixedAssetsInvestments() != null) {

            balanceSheetNotes.setFixedAssetsInvestments(mapFixedAssetInvestments(smallFullApiData.getFixedAssetsInvestments()));

            hasBalanceSheetNotes = true;
        }

        if (smallFullApiData.getDirectorsReport() != null) {

            smallFullAccountIxbrl.setDirectorsReport(setDirectorsReport(
                            smallFullApiData.getDirectorsReportStatements(), smallFullApiData.getDirectors(),
                    smallFullApiData.getSecretary(), smallFullApiData.getDirectorsApproval()));
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

    private IntangibleAssets mapIntangibleAssets(IntangibleApi intangible) {

        IntangibleAssets intangibleAssets =
                apiToIntangibleAssetsNoteMapper.apiToIntangibleAssetsNoteAdditionalInformation(intangible);

        intangibleAssets.setCost(mapIntangibleAssetsCost(intangible));
        intangibleAssets.setAmortisation(mapIntangibleAssetsAmortisation(intangible));
        intangibleAssets.setNetBookValue(mapIntangibleAssetsNetBookValue(intangible));

        return intangibleAssets;
    }

    private IntangibleAssetsNetBookValue mapIntangibleAssetsNetBookValue(IntangibleApi intangible) {

        IntangibleAssetsNetBookValue netBookValue = new IntangibleAssetsNetBookValue();

        netBookValue.setCurrentPeriod(apiToIntangibleAssetsNoteMapper
                .apiToIntangibleAssetsNetBookValueCurrentPeriodMapper(intangible));
        netBookValue.setPreviousPeriod(apiToIntangibleAssetsNoteMapper
                .apiToIntangibleAssetsNetBookValuePreviousPeriodMapper(intangible));

        return netBookValue;

    }

    private IntangibleAssetsAmortisation mapIntangibleAssetsAmortisation(IntangibleApi intangible) {

        IntangibleAssetsAmortisation amortisation = new IntangibleAssetsAmortisation();

        amortisation.setAtPeriodEnd(apiToIntangibleAssetsNoteMapper
                .apiToIntangibleAssetsAmortisationAtPeriodEndMapper(intangible));
        amortisation.setAtPeriodStart(apiToIntangibleAssetsNoteMapper
                .apiToIntangibleAssetsAmortisationAtPeriodStartMapper(intangible));
        amortisation.setChargeForYear(apiToIntangibleAssetsNoteMapper
                .apiToIntangibleAssetsAmortisationChargeForYearMapper(intangible));
        amortisation.setOnDisposals(apiToIntangibleAssetsNoteMapper
                .apiToIntangibleAssetsAmortisationOnDisposalsMapper(intangible));
        amortisation.setOtherAdjustments(apiToIntangibleAssetsNoteMapper
                .apiToIntangibleAssetsAmortisationOtherAdjustmentsMapper(intangible));

        return amortisation;

    }

    private IntangibleAssetsCost mapIntangibleAssetsCost(IntangibleApi intangible) {

        IntangibleAssetsCost cost = new IntangibleAssetsCost();

        cost.setAdditions(apiToIntangibleAssetsNoteMapper.apiToIntangibleAssetsCostAdditionsMapper(intangible));
        cost.setAtPeriodEnd(apiToIntangibleAssetsNoteMapper.apiToIntangibleAssetsCostAtPeriodEndMapper(intangible));
        cost.setAtPeriodStart(apiToIntangibleAssetsNoteMapper.apiToIntangibleAssetsCostAtPeriodStartMapper(intangible));
        cost.setDisposals(apiToIntangibleAssetsNoteMapper.apiToIntangibleAssetsCostDisposalsMapper(intangible));
        cost.setRevaluations(apiToIntangibleAssetsNoteMapper.apiToIntangibleAssetsCostRevaluationsMapper(intangible));
        cost.setTransfers(apiToIntangibleAssetsNoteMapper.apiToIntangibleAssetsCostTransfersMapper(intangible));

        return cost;
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
            if (currentPeriod.getBalanceSheet().getMembersFunds() != null) {
                balanceSheet.setMembersFunds(apiToBalanceSheetMapper.apiToMembersFunds(currentPeriod, previousPeriod));
            }
        }

        if (balanceSheetStatements != null) {
            balanceSheet.setBalanceSheetStatements(apiToBalanceSheetMapper.apiToStatements(balanceSheetStatements));
        }

        return balanceSheet;
    }

    private DirectorsReport setDirectorsReport(StatementsApi directorsReportStatements, DirectorApi[] directorsApi,
                                               SecretaryApi secretary, ApprovalApi approval) {

        DirectorsReport directorsReport = new DirectorsReport();

        if (directorsReportStatements != null) {
            directorsReport.setDirectorsReportStatements(apiToDirectorsReportMapper.apiToStatements(directorsReportStatements));
        }

        if(secretary != null) {
            directorsReport.setSecretary(apiToDirectorsReportMapper.apiToSecretary(secretary));
        }

        Map<String, List<DirectorApi>> sortedDirectors = new TreeMap<>(Collections.reverseOrder());

        sortedDirectors.putAll( Arrays.stream(directorsApi)
                .collect(
                        Collectors.groupingBy(d ->
                                (d.getAppointmentDate() == null ? "null" : d.getAppointmentDate().toString()) +
                                        (d.getResignationDate() == null ? "null" : d.getResignationDate().toString()))));

        Set<Map.Entry<String, List<DirectorApi>>> keys = sortedDirectors.entrySet();

        List<Directors> directors = new ArrayList<>();

         Iterator<Map.Entry<String, List<DirectorApi>>> iterator = keys.iterator();
        while(iterator.hasNext()) {

            Directors dir = new Directors();
            List<DirectorApi> directorList = iterator.next().getValue();

            for(DirectorApi d : directorList) {
                if(d.getAppointmentDate() != null) {
                    dir.setAppointmentDate(convertToDisplayDate(d.getAppointmentDate()));
                }
                if(d.getResignationDate() != null) {

                    dir.setResignationDate(convertToDisplayDate(d.getResignationDate()));
                }
                dir.getNames().add(d.getName());
            }

            dir.getNames().sort(Comparator.naturalOrder());
            directors.add(dir);
        }

        Approval directorsApproval = apiToDirectorsReportMapper.apiToApproval(approval);

        directorsApproval.setDate(convertToDisplayDate(accountsDatesHelper.convertStringToDate(directorsApproval.getDate())));

        if(directorsReport.getSecretary() != null &&
                        directorsApproval.getName().equals(directorsReport.getSecretary().getName())) {

                directorsApproval.setSecretary(true);
        }

        directorsReport.setApproval(directorsApproval);

        directorsReport.setSortedDirectors(directors );

        return directorsReport;
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

        return apiToEmployeesMapper.apiToEmployees(employees.getCurrentPeriod(),
                employees.getPreviousPeriod());
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

        TangibleAssets tangibleAssets =
                apiToTangibleAssetsNoteMapper.apiToTangibleAssetsNoteAdditionalInformation(tangible);

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


  /*INTANGIBLE ASSETS START HERE*/

    private CurrentAssetsInvestments mapCurrentAssetsInvestments(CurrentAssetsInvestmentsApi currentAssetsInvestmentsApi) {
        return apiToCurrentAssetsInvestmentsMapper
                .apiToCurrentAssetsInvestments(currentAssetsInvestmentsApi);
    }

    private FixedAssetsInvestments mapFixedAssetInvestments(FixedAssetsInvestmentsApi fixedAssetsInvestmentsApi) {
        return apiToFixedAssetsInvestmentsMapper
                .apiToFixedAssetsInvestments(fixedAssetsInvestmentsApi);
    }

    private String convertToDisplayDate(LocalDate date) {
        return accountsDatesHelper.convertLocalDateToDisplayDate(date);
    }
}
