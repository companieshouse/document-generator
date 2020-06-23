package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.mappers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.accountsdates.AccountsDatesHelper;
import uk.gov.companieshouse.accountsdates.impl.AccountsDatesHelperImpl;
import uk.gov.companieshouse.api.model.accounts.directorsreport.DirectorApi;
import uk.gov.companieshouse.api.model.accounts.directorsreport.DirectorsReportApi;
import uk.gov.companieshouse.api.model.accounts.directorsreport.SecretaryApi;
import uk.gov.companieshouse.api.model.accounts.directorsreport.StatementsApi;
import uk.gov.companieshouse.api.model.accounts.profitandloss.ProfitAndLossApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.AccountingPoliciesApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.ApprovalApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.BalanceSheetApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.BalanceSheetStatementsApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.CapitalAndReservesApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.CurrentAssetsApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.CurrentPeriodApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.Debtors.CurrentPeriod;
import uk.gov.companieshouse.api.model.accounts.smallfull.Debtors.DebtorsApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.Debtors.PreviousPeriod;
import uk.gov.companieshouse.api.model.accounts.smallfull.MembersFundsApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.creditorsafteroneyear.CreditorsAfterOneYearApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.creditorswithinoneyear.CreditorsWithinOneYearApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.currentassetsinvestments.CurrentAssetsInvestmentsApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.fixedassetsinvestments.FixedAssetsInvestmentsApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.intangible.IntangibleApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.offBalanceSheet.OffBalanceSheetApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.stocks.StocksApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.FixedAssetsApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.OtherLiabilitiesOrAssetsApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.PreviousPeriodApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.employees.EmployeesApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.tangible.TangibleApi;

import uk.gov.companieshouse.api.model.company.CompanyProfileApi;
import uk.gov.companieshouse.api.model.statements.StatementApi;
import uk.gov.companieshouse.document.generator.accounts.mapping.cic.model.Statements;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.SmallFullApiData;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.SmallFullAccountIxbrl;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.accountingpolicies.AccountingPolicies;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.BalanceSheetStatements;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.CalledUpSharedCapitalNotPaid;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.capitalandreserves.CapitalAndReserve;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.currentassets.CurrentAssets;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.fixedassets.FixedAssets;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.membersfunds.MembersFunds;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.otherliabilitiesandassets.OtherLiabilitiesOrAssets;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.company.Company;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.creditorsafteroneyear.CreditorsAfterOneYear;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.creditorswithinoneyear.CreditorsWithinOneYear;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.currentassetsinvestments.CurrentAssetsInvestments;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.debtors.Debtors;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.directorsreport.Approval;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.directorsreport.Directors;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.directorsreport.DirectorsReport;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.directorsreport.DirectorsReportStatements;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.directorsreport.Secretary;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.fixedassetsinvestments.FixedAssetsInvestments;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.employees.Employees;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.notes.intangible.IntangibleAssets;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.notes.intangible.IntangibleAssetsColumns;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.notes.tangible.TangibleAssets;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.notes.tangible.TangibleAssetsColumns;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.offbalancesheetarrangements.OffBalanceSheetArrangements;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.period.Period;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.profitandloss.ProfitAndLoss;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.stocks.StocksNote;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SmallFullIXBRLMapperTest {

    private static final String NAME = "name";

    @Mock
    private SmallFullIXBRLMapper internalSmallFullIXBRLMapper;

    @Mock
    private ApiToProfitAndLossMapper apiToProfitAndLossMapper;

    @Mock
    private ApiToCompanyMapper apiToCompanyMapper;

    @Mock
    private ApiToPeriodMapper apiToPeriodMapper;

    @Mock
    private AccountsDatesHelper accountsDatesHelper;

    @Mock
    private ApiToBalanceSheetMapper apiToBalanceSheetMapper;

    @Mock
    private ApiToDirectorsReportMapper apiToDirectorsReportMapper;

    @Mock
    private ApiToAccountingPoliciesMapper apiToAccountingPoliciesMapper;

    @Mock
    private ApiToStocksMapper apiToStocksMapper;

    @Mock
    private ApiToDebtorsMapper apiToDebtorsMapper;

    @Mock
    private ApiToEmployeesMapper apiToEmployeesMapper;

    @Mock
    private ApiToCreditorsWithinOneYearMapper apiToCreditorsWithinOneYearMapper;

    @Mock
    private ApiToCreditorsAfterOneYearMapper apiToCreditorsAfterOneYearMapper;

    @Mock
    private ApiToTangibleAssetsNoteMapper apiToTangibleAssetsNoteMapper;

    @Mock
    private ApiToIntangibleAssetsNoteMapper apiToIntangibleAssetsNoteMapper;

    @Mock
    private ApiToCurrentAssetsInvestmentsMapper apiToCurrentAssetsInvestmentsMapper;

    @Mock
    private ApiToFixedAssetsInvestmentsMapper apiToFixedAssetsInvestmentsMapper;

    @Mock
    private ApiToOffBalanceSheetArrangementsMapper apiToOffBalanceSheetArrangementsMapper;

    @Mock
    private ProfitAndLoss profitAndLoss;

    @Mock
    private CalledUpSharedCapitalNotPaid calledUpSharedCapitalNotPaid;

    @Mock
    private OtherLiabilitiesOrAssets otherLiabilitiesOrAssets;

    @Mock
    private FixedAssets fixedAssets;

    @Mock
    private CurrentAssets currentAssets;

    @Mock
    private CapitalAndReserve capitalAndReserve;

    @Mock
    private MembersFunds membersFunds;

    @Mock
    private BalanceSheetStatements balanceSheetStatements;

    @Mock
    private DirectorsReport directorsReport;

    @Mock
    private Secretary secretary;

    private Directors[] directors;

    @Mock
    private Map<String, List<DirectorApi>> directorsList;

    @Mock
    private DirectorsReportStatements directorsReportStatements;

    @Mock
    private OffBalanceSheetArrangements offBalanceSheetArrangements;

    @Mock
    private Company company;

    @Mock
    private Period period;

    @Mock
    private AccountingPolicies accountingPolicies;

    @Mock
    private StocksNote stocksNote;

    @Mock
    private Debtors debtors;

    @Mock
    private CreditorsWithinOneYear creditorsWithinOneYear;

    @Mock
    private CreditorsAfterOneYear creditorsAfterOneYear;

    @Mock
    private Employees employees;

    @Mock
    private TangibleAssets tangibleAssets;

    @Mock
    private IntangibleAssets intangibleAssets;

    @Mock
    private TangibleAssetsColumns column;

    @Mock
    private IntangibleAssetsColumns intangibleAssetsColumns;

    @Mock
    private CurrentAssetsInvestments currentAssetsInvestments;

    @Mock
    private FixedAssetsInvestments fixedAssetsInvestments;

    @Mock
    private Approval directorApproval;

    @InjectMocks
    private SmallFullIXBRLMapper smallFullIXBRLMapper = new SmallFullIXBRLMapperImpl();

    @Test
    @DisplayName("Tests the mapping of the smallFull IXBRL model with optional resources")
    void testMapperWithOptionalResources() {

        SmallFullApiData smallFullApiData = createSmallFullData(true);

        mockMandatoryFieldMappers(smallFullApiData);
        mockOptionalFieldMappers(smallFullApiData);

        SmallFullAccountIxbrl smallFullAccountIxbrl =
                smallFullIXBRLMapper.mapSmallFullIXBRLModel(smallFullApiData);

        verifyMandatoryFieldMappersExecuted(smallFullApiData);
        verifyOptionalFieldMappersExecuted(smallFullApiData);

        assertNotNull(smallFullAccountIxbrl);
        assertIxbrlMandatoryDataMapped(smallFullAccountIxbrl);
        assertIbxrlOptionalDataMapped(smallFullAccountIxbrl);
    }

    @Test
    @DisplayName("Tests the mapping of the smallFull IXBRL model without optional resources")
    void testMapperWithoutOptionalResources() {

        SmallFullApiData smallFullApiData = createSmallFullData(false);

        mockMandatoryFieldMappers(smallFullApiData);

        SmallFullAccountIxbrl smallFullAccountIxbrl =
                smallFullIXBRLMapper.mapSmallFullIXBRLModel(smallFullApiData);

        verifyMandatoryFieldMappersExecuted(smallFullApiData);

        assertNotNull(smallFullAccountIxbrl);
        assertIxbrlMandatoryDataMapped(smallFullAccountIxbrl);
    }

    private void mockMandatoryFieldMappers(SmallFullApiData smallFullApiData) {

        when(internalSmallFullIXBRLMapper.mapSmallFullIXBRLModel(smallFullApiData))
                .thenReturn(createSmallFullAccountIxbrl());

        when(apiToBalanceSheetMapper.apiToCalledUpSharedCapitalNotPaid(
                smallFullApiData.getCurrentPeriod(), smallFullApiData.getPreviousPeriod()))
                .thenReturn(calledUpSharedCapitalNotPaid);

        when(apiToBalanceSheetMapper.apiToOtherLiabilitiesOrAssets(
                smallFullApiData.getCurrentPeriod(), smallFullApiData.getPreviousPeriod()))
                .thenReturn(otherLiabilitiesOrAssets);

        when(apiToBalanceSheetMapper.apiToFixedAssets(
                smallFullApiData.getCurrentPeriod(), smallFullApiData.getPreviousPeriod()))
                .thenReturn(fixedAssets);

        when(apiToBalanceSheetMapper.apiToCurrentAssets(
                smallFullApiData.getCurrentPeriod(), smallFullApiData.getPreviousPeriod()))
                .thenReturn(currentAssets);

        when(apiToBalanceSheetMapper.apiToCapitalAndReserve(
                smallFullApiData.getCurrentPeriod(), smallFullApiData.getPreviousPeriod()))
                .thenReturn(capitalAndReserve);

        when(apiToBalanceSheetMapper.apiToMembersFunds(
                smallFullApiData.getCurrentPeriod(), smallFullApiData.getPreviousPeriod()))
                .thenReturn(membersFunds);

        when(apiToBalanceSheetMapper.apiToStatements(smallFullApiData.getBalanceSheetStatements()))
                .thenReturn(balanceSheetStatements);

        when(apiToCompanyMapper.apiToCompany(smallFullApiData.getCompanyProfile()))
                .thenReturn(company);

        when(apiToPeriodMapper.apiToPeriod(smallFullApiData.getSmallFull()))
                .thenReturn(period);
    }

    private void verifyMandatoryFieldMappersExecuted(SmallFullApiData smallFullApiData) {

        verify(internalSmallFullIXBRLMapper, times(1)).mapSmallFullIXBRLModel(smallFullApiData);

        verify(apiToBalanceSheetMapper, times(1)).apiToCalledUpSharedCapitalNotPaid(
                smallFullApiData.getCurrentPeriod(), smallFullApiData.getPreviousPeriod());

        verify(apiToBalanceSheetMapper, times(1)).apiToOtherLiabilitiesOrAssets(
                smallFullApiData.getCurrentPeriod(), smallFullApiData.getPreviousPeriod());

        verify(apiToBalanceSheetMapper, times(1)).apiToFixedAssets(
                smallFullApiData.getCurrentPeriod(), smallFullApiData.getPreviousPeriod());

        verify(apiToBalanceSheetMapper, times(1)).apiToCurrentAssets(
                smallFullApiData.getCurrentPeriod(), smallFullApiData.getPreviousPeriod());

        verify(apiToBalanceSheetMapper, times(1)).apiToCapitalAndReserve(
                smallFullApiData.getCurrentPeriod(), smallFullApiData.getPreviousPeriod());

        verify(apiToBalanceSheetMapper, times(1)).apiToMembersFunds(
                smallFullApiData.getCurrentPeriod(), smallFullApiData.getPreviousPeriod());

        verify(apiToBalanceSheetMapper, times(1))
                .apiToStatements(smallFullApiData.getBalanceSheetStatements());

        verify(apiToCompanyMapper, times(1))
                .apiToCompany(smallFullApiData.getCompanyProfile());

        verify(apiToPeriodMapper, times(1))
                .apiToPeriod(smallFullApiData.getSmallFull());
    }

    private void assertIxbrlMandatoryDataMapped(SmallFullAccountIxbrl smallFullAccountIxbrl) {

        assertEquals(NAME, smallFullAccountIxbrl.getApprovalName());

        assertNotNull(smallFullAccountIxbrl.getBalanceSheet());
        assertEquals(calledUpSharedCapitalNotPaid,
                smallFullAccountIxbrl.getBalanceSheet().getCalledUpSharedCapitalNotPaid());
        assertEquals(otherLiabilitiesOrAssets,
                smallFullAccountIxbrl.getBalanceSheet().getOtherLiabilitiesOrAssets());
        assertEquals(fixedAssets,
                smallFullAccountIxbrl.getBalanceSheet().getFixedAssets());
        assertEquals(currentAssets,
                smallFullAccountIxbrl.getBalanceSheet().getCurrentAssets());
        assertEquals(capitalAndReserve,
                smallFullAccountIxbrl.getBalanceSheet().getCapitalAndReserve());
        assertEquals(membersFunds,
                smallFullAccountIxbrl.getBalanceSheet().getMembersFunds());
        assertEquals(balanceSheetStatements,
                smallFullAccountIxbrl.getBalanceSheet().getBalanceSheetStatements());

        assertEquals(company, smallFullAccountIxbrl.getCompany());
        assertEquals(period, smallFullAccountIxbrl.getPeriod());
    }

    private void mockOptionalFieldMappers(SmallFullApiData smallFullApiData) {

        directorApproval = new Approval();

        directorApproval.setSecretary(false);

        directorApproval.setDate(accountsDatesHelper.convertLocalDateToDisplayDate(accountsDatesHelper.convertStringToDate(directorApproval.getDate())));
        directorApproval.setName("director");


        when(apiToBalanceSheetMapper.apiToStatements(smallFullApiData.getBalanceSheetStatements()))
                .thenReturn(balanceSheetStatements);

        when(apiToProfitAndLossMapper.apiToProfitAndLoss(
                smallFullApiData.getCurrentPeriodProfitAndLoss(),
                smallFullApiData.getPreviousPeriodProfitAndLoss()))
                .thenReturn(profitAndLoss);

        when(apiToAccountingPoliciesMapper.apiToAccountingPolicies(smallFullApiData.getAccountingPolicies()))
                .thenReturn(accountingPolicies);

        when(apiToStocksMapper.apiToStocks(
                smallFullApiData.getStocks().getCurrentPeriod(),
                smallFullApiData.getStocks().getPreviousPeriod()))
                .thenReturn(stocksNote);

        when(apiToDebtorsMapper.apiToDebtors(
                smallFullApiData.getDebtors().getDebtorsCurrentPeriod(),
                smallFullApiData.getDebtors().getDebtorsPreviousPeriod()))
                .thenReturn(debtors);

        when(apiToEmployeesMapper.apiToEmployees(smallFullApiData.getEmployees().getCurrentPeriod(),
                smallFullApiData.getEmployees().getPreviousPeriod())).thenReturn(employees);

        when(apiToCreditorsWithinOneYearMapper.apiToCreditorsWithinOneYear(
                smallFullApiData.getCreditorsWithinOneYear().getCreditorsWithinOneYearCurrentPeriod(),
                smallFullApiData.getCreditorsWithinOneYear().getCreditorsWithinOneYearPreviousPeriod()))
                .thenReturn(creditorsWithinOneYear);

        when(apiToCreditorsAfterOneYearMapper.apiToCreditorsAfterOneYear(
                smallFullApiData.getCreditorsAfterOneYear().getCurrentPeriod(),
                smallFullApiData.getCreditorsAfterOneYear().getPreviousPeriod()))
                .thenReturn(creditorsAfterOneYear);

        when(apiToTangibleAssetsNoteMapper.apiToTangibleAssetsNoteAdditionalInformation(
                smallFullApiData.getTangibleAssets()))
                .thenReturn(tangibleAssets);

        when(apiToTangibleAssetsNoteMapper.apiToTangibleAssetsCostAtPeriodStartMapper(
                smallFullApiData.getTangibleAssets()))
                .thenReturn(column);

        when(apiToTangibleAssetsNoteMapper.apiToTangibleAssetsCostAdditionsMapper(
                smallFullApiData.getTangibleAssets()))
                .thenReturn(column);

        when(apiToTangibleAssetsNoteMapper.apiToTangibleAssetsCostDisposalsMapper(
                smallFullApiData.getTangibleAssets()))
                .thenReturn(column);

        when(apiToTangibleAssetsNoteMapper.apiToTangibleAssetsCostRevaluationsMapper(
                smallFullApiData.getTangibleAssets()))
                .thenReturn(column);

        when(apiToTangibleAssetsNoteMapper.apiToTangibleAssetsCostTransfersMapper(
                smallFullApiData.getTangibleAssets()))
                .thenReturn(column);

        when(apiToTangibleAssetsNoteMapper.apiToTangibleAssetsCostAtPeriodEndMapper(
                smallFullApiData.getTangibleAssets()))
                .thenReturn(column);

        when(apiToTangibleAssetsNoteMapper.apiToTangibleAssetsDepreciationAtPeriodStartMapper(
                smallFullApiData.getTangibleAssets()))
                .thenReturn(column);

        when(apiToTangibleAssetsNoteMapper.apiToTangibleAssetsDepreciationChargeForYearMapper(
                smallFullApiData.getTangibleAssets()))
                .thenReturn(column);

        when(apiToTangibleAssetsNoteMapper.apiToTangibleAssetsDepreciationOnDisposalsMapper(
                smallFullApiData.getTangibleAssets()))
                .thenReturn(column);

        when(apiToTangibleAssetsNoteMapper.apiToTangibleAssetsDepreciationOtherAdjustmentsMapper(
                smallFullApiData.getTangibleAssets()))
                .thenReturn(column);

        when(apiToTangibleAssetsNoteMapper.apiToTangibleAssetsDepreciationAtPeriodEndMapper(
                smallFullApiData.getTangibleAssets()))
                .thenReturn(column);

        when(apiToTangibleAssetsNoteMapper.apiToTangibleAssetsNetBookValueCurrentPeriodMapper(
                smallFullApiData.getTangibleAssets()))
                .thenReturn(column);

        when(apiToTangibleAssetsNoteMapper.apiToTangibleAssetsNetBookValuePreviousPeriodMapper(
                smallFullApiData.getTangibleAssets()))
                .thenReturn(column);

        when(apiToCurrentAssetsInvestmentsMapper.apiToCurrentAssetsInvestments(
                smallFullApiData.getCurrentAssetsInvestments()))
                .thenReturn(currentAssetsInvestments);

        when(apiToFixedAssetsInvestmentsMapper.apiToFixedAssetsInvestments(
                smallFullApiData.getFixedAssetsInvestments()))
                .thenReturn(fixedAssetsInvestments);

        when(apiToIntangibleAssetsNoteMapper.apiToIntangibleAssetsNoteAdditionalInformation(
                 smallFullApiData.getIntangibleAssets()))
                 .thenReturn(intangibleAssets);

        when(apiToIntangibleAssetsNoteMapper.apiToIntangibleAssetsCostAtPeriodStartMapper(
                smallFullApiData.getIntangibleAssets()))
                .thenReturn(intangibleAssetsColumns);

        when(apiToIntangibleAssetsNoteMapper.apiToIntangibleAssetsCostAdditionsMapper(
                smallFullApiData.getIntangibleAssets()))
                .thenReturn(intangibleAssetsColumns);

        when(apiToIntangibleAssetsNoteMapper.apiToIntangibleAssetsCostDisposalsMapper(
                smallFullApiData.getIntangibleAssets()))
                .thenReturn(intangibleAssetsColumns);

        when(apiToIntangibleAssetsNoteMapper.apiToIntangibleAssetsCostRevaluationsMapper(
                smallFullApiData.getIntangibleAssets()))
                .thenReturn(intangibleAssetsColumns);

        when(apiToIntangibleAssetsNoteMapper.apiToIntangibleAssetsCostTransfersMapper(
                smallFullApiData.getIntangibleAssets()))
                .thenReturn(intangibleAssetsColumns);

        when(apiToIntangibleAssetsNoteMapper.apiToIntangibleAssetsCostAtPeriodEndMapper(
                smallFullApiData.getIntangibleAssets()))
                .thenReturn(intangibleAssetsColumns);

        when(apiToIntangibleAssetsNoteMapper.apiToIntangibleAssetsAmortisationAtPeriodStartMapper(
                smallFullApiData.getIntangibleAssets()))
                .thenReturn(intangibleAssetsColumns);

        when(apiToIntangibleAssetsNoteMapper.apiToIntangibleAssetsAmortisationChargeForYearMapper(
                smallFullApiData.getIntangibleAssets()))
                .thenReturn(intangibleAssetsColumns);

        when(apiToIntangibleAssetsNoteMapper.apiToIntangibleAssetsAmortisationOnDisposalsMapper(
                smallFullApiData.getIntangibleAssets()))
                .thenReturn(intangibleAssetsColumns);

        when(apiToIntangibleAssetsNoteMapper.apiToIntangibleAssetsAmortisationOtherAdjustmentsMapper(
                smallFullApiData.getIntangibleAssets()))
                .thenReturn(intangibleAssetsColumns);

        when(apiToIntangibleAssetsNoteMapper.apiToIntangibleAssetsAmortisationAtPeriodEndMapper(
                smallFullApiData.getIntangibleAssets()))
                .thenReturn(intangibleAssetsColumns);

        when(apiToIntangibleAssetsNoteMapper.apiToIntangibleAssetsNetBookValueCurrentPeriodMapper(
                smallFullApiData.getIntangibleAssets()))
                .thenReturn(intangibleAssetsColumns);

        when(apiToIntangibleAssetsNoteMapper.apiToIntangibleAssetsNetBookValuePreviousPeriodMapper(
                smallFullApiData.getIntangibleAssets()))
                .thenReturn(intangibleAssetsColumns);

        when(apiToCurrentAssetsInvestmentsMapper.apiToCurrentAssetsInvestments(
                smallFullApiData.getCurrentAssetsInvestments()))
                .thenReturn(currentAssetsInvestments);

        when(apiToFixedAssetsInvestmentsMapper.apiToFixedAssetsInvestments(
                smallFullApiData.getFixedAssetsInvestments()))
                .thenReturn(fixedAssetsInvestments);

        when(apiToDirectorsReportMapper.apiToApproval(smallFullApiData.getDirectorsApproval()))
                .thenReturn(directorApproval);

        when(apiToOffBalanceSheetArrangementsMapper.apiToOffBalanceSheetArrangements(smallFullApiData.getOffBalanceSheet()))
                .thenReturn(offBalanceSheetArrangements);
    }

    private void verifyOptionalFieldMappersExecuted(SmallFullApiData smallFullApiData) {

        verify(apiToProfitAndLossMapper, times(1))
                .apiToProfitAndLoss(smallFullApiData.getCurrentPeriodProfitAndLoss(),
                        smallFullApiData.getPreviousPeriodProfitAndLoss());

        verify(apiToAccountingPoliciesMapper, times(1))
                .apiToAccountingPolicies(smallFullApiData.getAccountingPolicies());

        verify(apiToStocksMapper, times(1)).apiToStocks(
                smallFullApiData.getStocks().getCurrentPeriod(),
                smallFullApiData.getStocks().getPreviousPeriod());

        verify(apiToDebtorsMapper, times(1)).apiToDebtors(
                smallFullApiData.getDebtors().getDebtorsCurrentPeriod(),
                smallFullApiData.getDebtors().getDebtorsPreviousPeriod());

        verify(apiToEmployeesMapper, times(1)).apiToEmployees(
                smallFullApiData.getEmployees().getCurrentPeriod(),
                smallFullApiData.getEmployees().getPreviousPeriod());

        verify(apiToCreditorsWithinOneYearMapper, times(1)).apiToCreditorsWithinOneYear(
                smallFullApiData.getCreditorsWithinOneYear().getCreditorsWithinOneYearCurrentPeriod(),
                smallFullApiData.getCreditorsWithinOneYear().getCreditorsWithinOneYearPreviousPeriod());

        verify(apiToCreditorsAfterOneYearMapper, times(1)).apiToCreditorsAfterOneYear(
                smallFullApiData.getCreditorsAfterOneYear().getCurrentPeriod(),
                smallFullApiData.getCreditorsAfterOneYear().getPreviousPeriod());

        verify(apiToTangibleAssetsNoteMapper, times(1)).apiToTangibleAssetsNoteAdditionalInformation(
                smallFullApiData.getTangibleAssets());

        verify(apiToTangibleAssetsNoteMapper, times(1)).apiToTangibleAssetsCostAtPeriodStartMapper(
                smallFullApiData.getTangibleAssets());

        verify(apiToTangibleAssetsNoteMapper, times(1)).apiToTangibleAssetsCostAdditionsMapper(
                smallFullApiData.getTangibleAssets());

        verify(apiToTangibleAssetsNoteMapper, times(1)).apiToTangibleAssetsCostDisposalsMapper(
                smallFullApiData.getTangibleAssets());

        verify(apiToTangibleAssetsNoteMapper, times(1)).apiToTangibleAssetsCostRevaluationsMapper(
                smallFullApiData.getTangibleAssets());

        verify(apiToTangibleAssetsNoteMapper, times(1)).apiToTangibleAssetsCostTransfersMapper(
                smallFullApiData.getTangibleAssets());

        verify(apiToTangibleAssetsNoteMapper, times(1)).apiToTangibleAssetsCostAtPeriodEndMapper(
                smallFullApiData.getTangibleAssets());

        verify(apiToTangibleAssetsNoteMapper, times(1)).apiToTangibleAssetsDepreciationAtPeriodStartMapper(
                smallFullApiData.getTangibleAssets());

        verify(apiToTangibleAssetsNoteMapper, times(1)).apiToTangibleAssetsDepreciationChargeForYearMapper(
                smallFullApiData.getTangibleAssets());

        verify(apiToTangibleAssetsNoteMapper, times(1)).apiToTangibleAssetsDepreciationOnDisposalsMapper(
                smallFullApiData.getTangibleAssets());

        verify(apiToTangibleAssetsNoteMapper, times(1)).apiToTangibleAssetsDepreciationOtherAdjustmentsMapper(
                smallFullApiData.getTangibleAssets());

        verify(apiToTangibleAssetsNoteMapper, times(1)).apiToTangibleAssetsDepreciationAtPeriodEndMapper(
                smallFullApiData.getTangibleAssets());

        verify(apiToTangibleAssetsNoteMapper, times(1)).apiToTangibleAssetsNetBookValueCurrentPeriodMapper(
                smallFullApiData.getTangibleAssets());

        verify(apiToTangibleAssetsNoteMapper, times(1)).apiToTangibleAssetsNetBookValuePreviousPeriodMapper(
                smallFullApiData.getTangibleAssets());


        verify(apiToIntangibleAssetsNoteMapper, times(1)).apiToIntangibleAssetsNoteAdditionalInformation(
                smallFullApiData.getIntangibleAssets());

        verify(apiToIntangibleAssetsNoteMapper, times(1)).apiToIntangibleAssetsCostAtPeriodStartMapper(
                smallFullApiData.getIntangibleAssets());

        verify(apiToIntangibleAssetsNoteMapper, times(1)).apiToIntangibleAssetsCostAdditionsMapper(
                smallFullApiData.getIntangibleAssets());

        verify(apiToIntangibleAssetsNoteMapper, times(1)).apiToIntangibleAssetsCostDisposalsMapper(
                smallFullApiData.getIntangibleAssets());

        verify(apiToIntangibleAssetsNoteMapper, times(1)).apiToIntangibleAssetsCostRevaluationsMapper(
                smallFullApiData.getIntangibleAssets());

        verify(apiToIntangibleAssetsNoteMapper, times(1)).apiToIntangibleAssetsCostTransfersMapper(
                smallFullApiData.getIntangibleAssets());

        verify(apiToIntangibleAssetsNoteMapper, times(1)).apiToIntangibleAssetsCostAtPeriodEndMapper(
                smallFullApiData.getIntangibleAssets());

        verify(apiToIntangibleAssetsNoteMapper, times(1)).apiToIntangibleAssetsAmortisationAtPeriodStartMapper(
                smallFullApiData.getIntangibleAssets());

        verify(apiToIntangibleAssetsNoteMapper, times(1)).apiToIntangibleAssetsAmortisationChargeForYearMapper(
                smallFullApiData.getIntangibleAssets());

        verify(apiToIntangibleAssetsNoteMapper, times(1)).apiToIntangibleAssetsAmortisationOnDisposalsMapper(
                smallFullApiData.getIntangibleAssets());

        verify(apiToIntangibleAssetsNoteMapper, times(1)).apiToIntangibleAssetsAmortisationOtherAdjustmentsMapper(
                smallFullApiData.getIntangibleAssets());

        verify(apiToIntangibleAssetsNoteMapper, times(1)).apiToIntangibleAssetsAmortisationAtPeriodEndMapper(
                smallFullApiData.getIntangibleAssets());

        verify(apiToIntangibleAssetsNoteMapper, times(1)).apiToIntangibleAssetsNetBookValueCurrentPeriodMapper(
                smallFullApiData.getIntangibleAssets());

        verify(apiToIntangibleAssetsNoteMapper, times(1)).apiToIntangibleAssetsNetBookValuePreviousPeriodMapper(
                smallFullApiData.getIntangibleAssets());

        verify(apiToDirectorsReportMapper, times(1)).apiToSecretary(
                smallFullApiData.getSecretary());

        verify(apiToDirectorsReportMapper, times(1)).apiToStatements(
                smallFullApiData.getDirectorsReportStatements());

       verify(apiToDirectorsReportMapper, times(1)).apiToApproval(
                smallFullApiData.getDirectorsApproval());

       verify(apiToOffBalanceSheetArrangementsMapper, times(1)).apiToOffBalanceSheetArrangements(
               smallFullApiData.getOffBalanceSheet());
    }

    private void assertIbxrlOptionalDataMapped(SmallFullAccountIxbrl smallFullAccountIxbrl) {

        assertNotNull(smallFullAccountIxbrl.getAdditionalNotes());
        assertEquals(profitAndLoss,
                smallFullAccountIxbrl.getProfitAndLoss());
        assertEquals(accountingPolicies,
                smallFullAccountIxbrl.getAdditionalNotes().getAccountingPolicies());

        assertNotNull(smallFullAccountIxbrl.getBalanceSheetNotes());
        assertEquals(stocksNote,
                smallFullAccountIxbrl.getBalanceSheetNotes().getStocksNote());
        assertEquals(debtors,
                smallFullAccountIxbrl.getBalanceSheetNotes().getDebtorsNote());
        assertEquals(creditorsWithinOneYear,
                smallFullAccountIxbrl.getBalanceSheetNotes().getCreditorsWithinOneYearNote());
        assertEquals(creditorsAfterOneYear,
                smallFullAccountIxbrl.getBalanceSheetNotes().getCreditorsAfterOneYearNote());
        assertEquals(tangibleAssets,
                smallFullAccountIxbrl.getBalanceSheetNotes().getTangibleAssets());
        assertEquals(intangibleAssets,
                smallFullAccountIxbrl.getBalanceSheetNotes().getIntangibleAssets());
        assertEquals(currentAssetsInvestments, smallFullAccountIxbrl.getBalanceSheetNotes().getCurrentAssetsInvestments());
        assertEquals(fixedAssetsInvestments,
                smallFullAccountIxbrl.getBalanceSheetNotes().getFixedAssetsInvestments());
        assertEquals(employees, smallFullAccountIxbrl.getAdditionalNotes().getEmployees());
        assertEquals(offBalanceSheetArrangements, smallFullAccountIxbrl.getBalanceSheetNotes().getOffBalanceSheetArrangements());
    }

    private SmallFullAccountIxbrl createSmallFullAccountIxbrl() {

        SmallFullAccountIxbrl smallFullAccountIxbrl = new SmallFullAccountIxbrl();
        smallFullAccountIxbrl.setApprovalName(NAME);
        return smallFullAccountIxbrl;
    }

    private SmallFullApiData createSmallFullData(boolean hasOptionalResources) {

        SmallFullApiData smallFullApiData = new SmallFullApiData();

        smallFullApiData.setApproval(createApproval());
        smallFullApiData.setCompanyProfile(createCompanyProfile());
        smallFullApiData.setCurrentPeriod(createCurrentPeriod());
        smallFullApiData.setPreviousPeriod(createPreviousPeriod());
        smallFullApiData.setBalanceSheetStatements(createBalanceSheetStatements());
        smallFullApiData.setCurrentAssetsInvestments(createCurrentAssetsInvestments());

        if (hasOptionalResources) {
            smallFullApiData.setAccountingPolicies(createAccountingPolicies());
            smallFullApiData.setStocks(createStocks());
            smallFullApiData.setDebtors(createDebtors());
            smallFullApiData.setCreditorsWithinOneYear(createCreditorsWithinOneYear());
            smallFullApiData.setCreditorsAfterOneYear(createCreditorsAfterOneYear());
            smallFullApiData.setTangibleAssets(createTangible());
            smallFullApiData.setIntangibleAssets(createIntangible());
            smallFullApiData.setFixedAssetsInvestments(createFixedAssetsInvestments());
            smallFullApiData.setEmployees(createEmployees());
            smallFullApiData.setCurrentPeriodProfitAndLoss(createProfitAndLoss());
            smallFullApiData.setPreviousPeriodProfitAndLoss(createProfitAndLoss());
            smallFullApiData.setDirectors(createDirectors());
            smallFullApiData.setSecretary(new SecretaryApi());
            smallFullApiData.setDirectorsApproval(createDirectorsApproval());
            smallFullApiData.setDirectorsReportStatements(new StatementsApi());
            smallFullApiData.setDirectorsReport(createDirectorsReport());
            smallFullApiData.setOffBalanceSheet(new OffBalanceSheetApi());
        }

        return smallFullApiData;
    }

    private DirectorApi[] createDirectors() {

        DirectorApi[] directors = new DirectorApi[1];

        DirectorApi director = new DirectorApi();

        director.setName("director");
        director.setResignationDate(LocalDate.of(2018, 01, 02));
        director.setAppointmentDate(LocalDate.of(2018, 01, 01));
        directors[0] = director;

        return directors;
    }

    private uk.gov.companieshouse.api.model.accounts.directorsreport.ApprovalApi createDirectorsApproval() {

        uk.gov.companieshouse.api.model.accounts.directorsreport.ApprovalApi approvalApi = new uk.gov.companieshouse.api.model.accounts.directorsreport.ApprovalApi();

        approvalApi.setDate(LocalDate.of(2018, 1, 1));
        approvalApi.setName(NAME);

        return approvalApi;
    }

    private DirectorsReportApi createDirectorsReport() {

        Map<String, String> directors = new HashMap<>();

        DirectorsReportApi directorsReportApi = new DirectorsReportApi();

        directorsReportApi.setDirectors(directors);

        return directorsReportApi;
    }

    private CurrentPeriodApi createCurrentPeriod() {

        CurrentPeriodApi currentPeriodApi = new CurrentPeriodApi();
        currentPeriodApi.setBalanceSheet(createBalanceSheet());

        return currentPeriodApi;
    }

    private PreviousPeriodApi createPreviousPeriod() {

        PreviousPeriodApi previousPeriodApi = new PreviousPeriodApi();
        previousPeriodApi.setBalanceSheet(createBalanceSheet());

        return previousPeriodApi;
    }

    private BalanceSheetApi createBalanceSheet() {

        BalanceSheetApi balanceSheetApi = new BalanceSheetApi();
        balanceSheetApi.setCalledUpShareCapitalNotPaid(1L);
        balanceSheetApi.setOtherLiabilitiesOrAssets(new OtherLiabilitiesOrAssetsApi());
        balanceSheetApi.setFixedAssets(new FixedAssetsApi());
        balanceSheetApi.setCurrentAssets(new CurrentAssetsApi());
        balanceSheetApi.setCapitalAndReserves(new CapitalAndReservesApi());
        balanceSheetApi.setMembersFunds(new MembersFundsApi());

        return balanceSheetApi;
    }

    private BalanceSheetStatementsApi createBalanceSheetStatements() {

        return new BalanceSheetStatementsApi();
    }

    private ApprovalApi createApproval() {

        ApprovalApi approvalApi = new ApprovalApi();
        approvalApi.setDate(LocalDate.of(2018, 1, 1));

        return approvalApi;
    }

    private CompanyProfileApi createCompanyProfile() {

        return new CompanyProfileApi();

    }

    private AccountingPoliciesApi createAccountingPolicies() {

        return new AccountingPoliciesApi();
    }

    private DebtorsApi createDebtors() {

        DebtorsApi debtorsApi = new DebtorsApi();
        debtorsApi.setDebtorsCurrentPeriod(new CurrentPeriod());
        debtorsApi.setDebtorsPreviousPeriod(new PreviousPeriod());

        return debtorsApi;
    }

    private EmployeesApi createEmployees() {

        EmployeesApi employees = new EmployeesApi();

        employees.setCurrentPeriod(new uk.gov.companieshouse.api.model.accounts.smallfull.employees.CurrentPeriod());
        employees.setPreviousPeriod(new uk.gov.companieshouse.api.model.accounts.smallfull.employees.PreviousPeriod());

        return employees;
    }

    private CurrentAssetsInvestmentsApi createCurrentAssetsInvestments() {

        CurrentAssetsInvestmentsApi currentAssetsInvestmentsApi = new CurrentAssetsInvestmentsApi();
        currentAssetsInvestmentsApi.setDetails("details");

        return currentAssetsInvestmentsApi;
    }

    private StocksApi createStocks() {

        StocksApi stocksApi = new StocksApi();
        stocksApi.setCurrentPeriod(
                new uk.gov.companieshouse.api.model.accounts.smallfull.stocks.CurrentPeriod());
        stocksApi.setPreviousPeriod(
                new uk.gov.companieshouse.api.model.accounts.smallfull.stocks.PreviousPeriod());

        return stocksApi;
    }

    private CreditorsWithinOneYearApi createCreditorsWithinOneYear() {

        CreditorsWithinOneYearApi creditorsWithinOneYearApi = new CreditorsWithinOneYearApi();
        creditorsWithinOneYearApi.setCreditorsWithinOneYearCurrentPeriod(
                new uk.gov.companieshouse.api.model.accounts.smallfull.creditorswithinoneyear.CurrentPeriod());
        creditorsWithinOneYearApi.setCreditorsWithinOneYearPreviousPeriod(
                new uk.gov.companieshouse.api.model.accounts.smallfull.creditorswithinoneyear.PreviousPeriod());

        return creditorsWithinOneYearApi;
    }

    private CreditorsAfterOneYearApi createCreditorsAfterOneYear() {

        CreditorsAfterOneYearApi creditorsAfterOneYearApi = new CreditorsAfterOneYearApi();
        creditorsAfterOneYearApi.setCurrentPeriod(
                new uk.gov.companieshouse.api.model.accounts.smallfull.creditorsafteroneyear.CurrentPeriod());
        creditorsAfterOneYearApi.setPreviousPeriod(
                new uk.gov.companieshouse.api.model.accounts.smallfull.creditorsafteroneyear.PreviousPeriod());

        return creditorsAfterOneYearApi;
    }

    private TangibleApi createTangible() {

        return new TangibleApi();
    }

    private IntangibleApi createIntangible() {

        return new IntangibleApi();
    }

    private ProfitAndLossApi createProfitAndLoss() {

        return new ProfitAndLossApi();
    }

    private FixedAssetsInvestmentsApi createFixedAssetsInvestments() {

        FixedAssetsInvestmentsApi fixedAssetsInvestmentsApi = new FixedAssetsInvestmentsApi();
        fixedAssetsInvestmentsApi.setDetails("details");

        return fixedAssetsInvestmentsApi;
    }
}
