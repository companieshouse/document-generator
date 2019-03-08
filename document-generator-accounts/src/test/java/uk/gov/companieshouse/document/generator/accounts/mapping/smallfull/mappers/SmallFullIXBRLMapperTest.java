package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.mappers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
import uk.gov.companieshouse.api.model.accounts.smallfull.creditorsafteroneyear.CreditorsAfterOneYearApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.creditorswithinoneyear.CreditorsWithinOneYearApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.fixedassetsinvestments.FixedAssetsInvestmentsApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.stocks.StocksApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.FixedAssetsApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.OtherLiabilitiesOrAssetsApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.PreviousPeriodApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.tangible.TangibleApi;
import uk.gov.companieshouse.api.model.company.CompanyProfileApi;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.SmallFullApiData;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.SmallFullAccountIxbrl;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.accountingpolicies.AccountingPolicies;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.BalanceSheetStatements;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.CalledUpSharedCapitalNotPaid;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.capitalandreserves.CapitalAndReserve;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.currentassets.CurrentAssets;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.fixedassets.FixedAssets;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.otherliabilitiesandassets.OtherLiabilitiesOrAssets;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.company.Company;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.creditorsafteroneyear.CreditorsAfterOneYear;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.creditorswithinoneyear.CreditorsWithinOneYear;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.debtors.Debtors;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.fixedassetsinvestments.FixedAssetsInvestments;
import java.time.LocalDate;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.notes.tangible.TangibleAssets;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.notes.tangible.TangibleAssetsColumns;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.period.Period;
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
    private ApiToCompanyMapper apiToCompanyMapper;

    @Mock
    private ApiToPeriodMapper apiToPeriodMapper;

    @Mock
    private ApiToBalanceSheetMapper apiToBalanceSheetMapper;

    @Mock
    private ApiToAccountingPoliciesMapper apiToAccountingPoliciesMapper;

    @Mock
    private ApiToStocksMapper apiToStocksMapper;

    @Mock
    private ApiToDebtorsMapper apiToDebtorsMapper;

    @Mock
    private ApiToCreditorsWithinOneYearMapper apiToCreditorsWithinOneYearMapper;

    @Mock
    private ApiToCreditorsAfterOneYearMapper apiToCreditorsAfterOneYearMapper;

    @Mock
    private ApiToTangibleAssetsNoteMapper apiToTangibleAssetsNoteMapper;
    
    @Mock
    private ApiToFixedAssetsInvestmentsMapper apiToFixedAssetsInvestmentsMapper;

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
    private BalanceSheetStatements balanceSheetStatements;

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
    private TangibleAssets tangibleAssets;

    @Mock
    private TangibleAssetsColumns column;
    
    @Mock
    private FixedAssetsInvestments fixedAssetsInvestments;

    @InjectMocks
    private SmallFullIXBRLMapper smallFullIXBRLMapper = new SmallFullIXBRLMapperImpl();

    @Test
    @DisplayName("Tests the mapping of the smallFull IXBRL model with optional resources")
    void testMapperWithOptionalResources() {

        SmallFullApiData smallFullApiData = createSmallFullData(true);

        mockMandatoryFieldMappers(smallFullApiData);
        mockOptionalFieldMappers(smallFullApiData);

        SmallFullAccountIxbrl smallFullAccountIxbrl = smallFullIXBRLMapper.mapSmallFullIXBRLModel(smallFullApiData);

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

        SmallFullAccountIxbrl smallFullAccountIxbrl = smallFullIXBRLMapper.mapSmallFullIXBRLModel(smallFullApiData);

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

        when(apiToBalanceSheetMapper.apiToStatements(smallFullApiData.getBalanceSheetStatements()))
                .thenReturn(balanceSheetStatements);

        when(apiToCompanyMapper.apiToCompany(smallFullApiData.getCompanyProfile()))
                .thenReturn(company);

        when(apiToPeriodMapper.apiToPeriod(smallFullApiData.getCompanyProfile()))
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

        verify(apiToBalanceSheetMapper, times(1))
                .apiToStatements(smallFullApiData.getBalanceSheetStatements());

        verify(apiToCompanyMapper, times(1))
                .apiToCompany(smallFullApiData.getCompanyProfile());

        verify(apiToPeriodMapper, times(1))
                .apiToPeriod(smallFullApiData.getCompanyProfile());
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
        assertEquals(balanceSheetStatements,
                smallFullAccountIxbrl.getBalanceSheet().getBalanceSheetStatements());

        assertEquals(company, smallFullAccountIxbrl.getCompany());
        assertEquals(period, smallFullAccountIxbrl.getPeriod());
    }

    private void mockOptionalFieldMappers(SmallFullApiData smallFullApiData) {

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
        
        when(apiToFixedAssetsInvestmentsMapper.apiToFixedAssetsInvestments(
                smallFullApiData.getFixedAssetsInvestments()))
                .thenReturn(fixedAssetsInvestments);
    }

    private void verifyOptionalFieldMappersExecuted(SmallFullApiData smallFullApiData) {

        verify(apiToAccountingPoliciesMapper, times(1))
                .apiToAccountingPolicies(smallFullApiData.getAccountingPolicies());

        verify(apiToStocksMapper, times(1)).apiToStocks(
                smallFullApiData.getStocks().getCurrentPeriod(),
                smallFullApiData.getStocks().getPreviousPeriod());

        verify(apiToDebtorsMapper, times(1)).apiToDebtors(
                smallFullApiData.getDebtors().getDebtorsCurrentPeriod(),
                smallFullApiData.getDebtors().getDebtorsPreviousPeriod());

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
    }

    private void assertIbxrlOptionalDataMapped(SmallFullAccountIxbrl smallFullAccountIxbrl) {

        assertNotNull(smallFullAccountIxbrl.getAdditionalNotes());
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
        assertEquals(fixedAssetsInvestments,
                smallFullAccountIxbrl.getBalanceSheetNotes().getFixedAssetsInvestments());
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

        if (hasOptionalResources) {
            smallFullApiData.setAccountingPolicies(createAccountingPolicies());
            smallFullApiData.setStocks(createStocks());
            smallFullApiData.setDebtors(createDebtors());
            smallFullApiData.setCreditorsWithinOneYear(createCreditorsWithinOneYear());
            smallFullApiData.setCreditorsAfterOneYear(createCreditorsAfterOneYear());
            smallFullApiData.setTangibleAssets(createTangible());
            smallFullApiData.setFixedAssetsInvestments(createFixedAssetsInvestments());
        }

        return smallFullApiData;
    }

    private CurrentPeriodApi createCurrentPeriod() {

        CurrentPeriodApi currentPeriodApi = new CurrentPeriodApi();
        currentPeriodApi.setBalanceSheet(createBalanceSheet());

        return currentPeriodApi;
    }

    private PreviousPeriodApi  createPreviousPeriod() {

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
    
    private FixedAssetsInvestmentsApi createFixedAssetsInvestments() {

        FixedAssetsInvestmentsApi fixedAssetsInvestmentsApi = new FixedAssetsInvestmentsApi();
        fixedAssetsInvestmentsApi.setDetails("details");

        return fixedAssetsInvestmentsApi;
    }   
}
