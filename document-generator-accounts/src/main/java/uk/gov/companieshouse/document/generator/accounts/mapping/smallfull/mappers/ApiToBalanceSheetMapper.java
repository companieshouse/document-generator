package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import uk.gov.companieshouse.api.model.accounts.smallfull.CurrentPeriodApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.PreviousPeriodApi;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.CalledUpSharedCapitalNotPaid;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.capitalandreserves.CapitalAndReserve;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.currentassets.CurrentAssets;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.fixedassets.FixedAssets;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.otherliabilitiesandassets.OtherLiabilitiesOrAssets;

@Mapper
public interface ApiToBalanceSheetMapper {

    ApiToBalanceSheetMapper INSTANCE = Mappers.getMapper(ApiToBalanceSheetMapper.class);

    @Mappings({
            @Mapping(source = "currentPeriod.balanceSheetApi.capitalAndReservesApi.calledUpShareCapital",
                    target = "calledUpShareCapital.currentAmount"),
            @Mapping(source = "previousPeriod.balanceSheet.capitalAndReservesApi.calledUpShareCapital",
                    target = "calledUpShareCapital.previousAmount"),
            @Mapping(source = "currentPeriod.balanceSheetApi.capitalAndReservesApi.otherReserves",
                    target = "otherReserves.currentAmount"),
            @Mapping(source = "previousPeriod.balanceSheet.capitalAndReservesApi.otherReserves",
                    target = "otherReserves.previousAmount"),
            @Mapping(source = "currentPeriod.balanceSheetApi.capitalAndReservesApi.profitAndLoss",
                    target = "profitAndLoss.currentAmount"),
            @Mapping(source = "previousPeriod.balanceSheet.capitalAndReservesApi.profitAndLoss",
                    target = "profitAndLoss.previousAmount"),
            @Mapping(source = "currentPeriod.balanceSheetApi.capitalAndReservesApi.sharePremiumAccount",
                    target = "sharePremiumAccount.currentAmount"),
            @Mapping(source = "previousPeriod.balanceSheet.capitalAndReservesApi.sharePremiumAccount",
                    target = "sharePremiumAccount.previousAmount"),
            @Mapping(source = "currentPeriod.balanceSheetApi.capitalAndReservesApi.totalShareholdersFund",
                    target = "totalShareHoldersFund.currentAmount"),
            @Mapping(source = "previousPeriod.balanceSheet.capitalAndReservesApi.totalShareholdersFund",
                    target = "totalShareHoldersFund.previousAmount"),
    })
    CapitalAndReserve apiToCapitalAndReserve(CurrentPeriodApi currentPeriod, PreviousPeriodApi previousPeriod);

    @Mappings({
            @Mapping(source = "currentPeriod.balanceSheetApi.currentAssetsApi.stocks",
                    target = "stocks.currentAmount"),
            @Mapping(source = "previousPeriod.balanceSheet.currentAssetsApi.stocks",
                    target = "stocks.previousAmount"),
            @Mapping(source = "currentPeriod.balanceSheetApi.currentAssetsApi.debtors",
                    target = "debtors.currentAmount"),
            @Mapping(source = "previousPeriod.balanceSheet.currentAssetsApi.debtors",
                    target = "debtors.previousAmount"),
            @Mapping(source = "currentPeriod.balanceSheetApi.currentAssetsApi.cashAtBankAndInHand",
                    target = "cashAtBankAndInHand.currentAmount"),
            @Mapping(source = "previousPeriod.balanceSheet.currentAssetsApi.cashAtBankAndInHand",
                    target = "cashAtBankAndInHand.previousAmount"),
            @Mapping(source = "currentPeriod.balanceSheetApi.currentAssetsApi.total",
                    target = "currentTotal"),
            @Mapping(source = "previousPeriod.balanceSheet.currentAssetsApi.total",
                    target = "previousTotal"),
    })
    CurrentAssets apiToCurrentAssets(CurrentPeriodApi currentPeriod, PreviousPeriodApi previousPeriod);

    @Mappings({
            @Mapping(source = "currentPeriod.balanceSheetApi.fixedAssetsApi.tangible",
                    target = "tangibleAssets.currentAmount"),
            @Mapping(source = "previousPeriod.balanceSheet.fixedAssetsApi.tangible",
                    target = "tangibleAssets.previousAmount"),
            @Mapping(source = "currentPeriod.balanceSheetApi.fixedAssetsApi.total",
                    target = "totalFixedAssetsCurrent"),
            @Mapping(source = "previousPeriod.balanceSheet.fixedAssetsApi.total",
                    target = "totalFixedAssetsPrevious")
    })
    FixedAssets apiToFixedAssets(CurrentPeriodApi currentPeriod, PreviousPeriodApi previousPeriod);

    @Mappings({
            @Mapping(source = "currentPeriod.balanceSheetApi.otherLiabilitiesOrAssetsApi.prepaymentsAndAccruedIncome",
                    target = "prepaymentsAndAccruedIncome.currentAmount"),
            @Mapping(source = "previousPeriod.balanceSheet.otherLiabilitiesOrAssetsApi.prepaymentsAndAccruedIncome",
                    target = "prepaymentsAndAccruedIncome.previousAmount"),
            @Mapping(source = "currentPeriod.balanceSheetApi.otherLiabilitiesOrAssetsApi.creditorsDueWithinOneYear",
                    target = "creditorsAmountsFallingDueWithinOneYear.currentAmount"),
            @Mapping(source = "previousPeriod.balanceSheet.otherLiabilitiesOrAssetsApi.creditorsDueWithinOneYear",
                    target = "creditorsAmountsFallingDueWithinOneYear.previousAmount"),
            @Mapping(source = "currentPeriod.balanceSheetApi.otherLiabilitiesOrAssetsApi.netCurrentAssets",
                    target = "netCurrentAssets.currentAmount"),
            @Mapping(source = "previousPeriod.balanceSheet.otherLiabilitiesOrAssetsApi.netCurrentAssets",
                    target = "netCurrentAssets.previousAmount"),
            @Mapping(source = "currentPeriod.balanceSheetApi.otherLiabilitiesOrAssetsApi.totalAssetsLessCurrentLiabilities",
                    target = "totalAssetsLessCurrentLiabilities.currentAmount"),
            @Mapping(source = "previousPeriod.balanceSheet.otherLiabilitiesOrAssetsApi.totalAssetsLessCurrentLiabilities",
                    target = "totalAssetsLessCurrentLiabilities.previousAmount"),
            @Mapping(source = "currentPeriod.balanceSheetApi.otherLiabilitiesOrAssetsApi.creditorsAfterOneYear",
                    target = "creditorsAmountsFallingDueAfterMoreThanOneYear.currentAmount"),
            @Mapping(source = "previousPeriod.balanceSheet.otherLiabilitiesOrAssetsApi.creditorsAfterOneYear",
                    target = "creditorsAmountsFallingDueAfterMoreThanOneYear.previousAmount"),
            @Mapping(source = "currentPeriod.balanceSheetApi.otherLiabilitiesOrAssetsApi.provisionForLiabilities",
                    target = "provisionForLiabilities.currentAmount"),
            @Mapping(source = "previousPeriod.balanceSheet.otherLiabilitiesOrAssetsApi.provisionForLiabilities",
                    target = "provisionForLiabilities.previousAmount"),
            @Mapping(source = "currentPeriod.balanceSheetApi.otherLiabilitiesOrAssetsApi.accrualsAndDeferredIncome",
                    target = "accrualsAndDeferredIncome.currentAmount"),
            @Mapping(source = "previousPeriod.balanceSheet.otherLiabilitiesOrAssetsApi.accrualsAndDeferredIncome",
                    target = "accrualsAndDeferredIncome.previousAmount"),
            @Mapping(source = "currentPeriod.balanceSheetApi.otherLiabilitiesOrAssetsApi.totalNetAssets",
                    target = "currentTotalNetAssets"),
            @Mapping(source = "previousPeriod.balanceSheet.otherLiabilitiesOrAssetsApi.totalNetAssets",
                    target = "previousTotalNetAssets"),
    })
    OtherLiabilitiesOrAssets apiToOtherLiabilitiesOrAssets(CurrentPeriodApi currentPeriod, PreviousPeriodApi previousPeriod);

    @Mappings({
            @Mapping(source = "currentPeriod.balanceSheetApi.calledUpShareCapitalNotPaid", target = "currentAmount"),
            @Mapping(source = "previousPeriod.balanceSheet.calledUpShareCapitalNotPaid", target = "previousAmount")
    })
    CalledUpSharedCapitalNotPaid apiToCalledUpSharedCapitalNotPaid(CurrentPeriodApi currentPeriod, PreviousPeriodApi previousPeriod);
}

