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
            @Mapping(source = "currentPeriod.balanceSheet.capitalAndReserves.calledUpShareCapital",
                    target = "calledUpShareCapital.currentAmount"),
            @Mapping(source = "previousPeriod.balanceSheet.capitalAndReserves.calledUpShareCapital",
                    target = "calledUpShareCapital.previousAmount"),
            @Mapping(source = "currentPeriod.balanceSheet.capitalAndReserves.otherReserves",
                    target = "otherReserves.currentAmount"),
            @Mapping(source = "previousPeriod.balanceSheet.capitalAndReserves.otherReserves",
                    target = "otherReserves.previousAmount"),
            @Mapping(source = "currentPeriod.balanceSheet.capitalAndReserves.profitAndLoss",
                    target = "profitAndLoss.currentAmount"),
            @Mapping(source = "previousPeriod.balanceSheet.capitalAndReserves.profitAndLoss",
                    target = "profitAndLoss.previousAmount"),
            @Mapping(source = "currentPeriod.balanceSheet.capitalAndReserves.sharePremiumAccount",
                    target = "sharePremiumAccount.currentAmount"),
            @Mapping(source = "previousPeriod.balanceSheet.capitalAndReserves.sharePremiumAccount",
                    target = "sharePremiumAccount.previousAmount"),
            @Mapping(source = "currentPeriod.balanceSheet.capitalAndReserves.totalShareholdersFunds",
                    target = "totalShareHoldersFunds.currentAmount"),
            @Mapping(source = "previousPeriod.balanceSheet.capitalAndReserves.totalShareholdersFunds",
                    target = "totalShareHoldersFunds.previousAmount"),
    })
    CapitalAndReserve apiToCapitalAndReserve(CurrentPeriodApi currentPeriod, PreviousPeriodApi previousPeriod);

    @Mappings({
            @Mapping(source = "currentPeriod.balanceSheet.currentAssets.stocks",
                    target = "stocks.currentAmount"),
            @Mapping(source = "previousPeriod.balanceSheet.currentAssets.stocks",
                    target = "stocks.previousAmount"),
            @Mapping(source = "currentPeriod.balanceSheet.currentAssets.debtors",
                    target = "debtors.currentAmount"),
            @Mapping(source = "previousPeriod.balanceSheet.currentAssets.debtors",
                    target = "debtors.previousAmount"),
            @Mapping(source = "currentPeriod.balanceSheet.currentAssets.cashAtBankAndInHand",
                    target = "cashAtBankAndInHand.currentAmount"),
            @Mapping(source = "previousPeriod.balanceSheet.currentAssets.cashAtBankAndInHand",
                    target = "cashAtBankAndInHand.previousAmount"),
            @Mapping(source = "currentPeriod.balanceSheet.currentAssets.total",
                    target = "currentTotal"),
            @Mapping(source = "previousPeriod.balanceSheet.currentAssets.total",
                    target = "previousTotal"),
    })
    CurrentAssets apiToCurrentAssets(CurrentPeriodApi currentPeriod, PreviousPeriodApi previousPeriod);

    @Mappings({
            @Mapping(source = "currentPeriod.balanceSheet.fixedAssets.tangible",
                    target = "tangibleAssets.currentAmount"),
            @Mapping(source = "previousPeriod.balanceSheet.fixedAssets.tangible",
                    target = "tangibleAssets.previousAmount"),
            @Mapping(source = "currentPeriod.balanceSheet.fixedAssets.total",
                    target = "totalFixedAssetsCurrent"),
            @Mapping(source = "previousPeriod.balanceSheet.fixedAssets.total",
                    target = "totalFixedAssetsPrevious")
    })
    FixedAssets apiToFixedAssets(CurrentPeriodApi currentPeriod, PreviousPeriodApi previousPeriod);

    @Mappings({
            @Mapping(source = "currentPeriod.balanceSheet.otherLiabilitiesOrAssets.prepaymentsAndAccruedIncome",
                    target = "prepaymentsAndAccruedIncome.currentAmount"),
            @Mapping(source = "previousPeriod.balanceSheet.otherLiabilitiesOrAssets.prepaymentsAndAccruedIncome",
                    target = "prepaymentsAndAccruedIncome.previousAmount"),
            @Mapping(source = "currentPeriod.balanceSheet.otherLiabilitiesOrAssets.creditorsDueWithinOneYear",
                    target = "creditorsAmountsFallingDueWithinOneYear.currentAmount"),
            @Mapping(source = "previousPeriod.balanceSheet.otherLiabilitiesOrAssets.creditorsDueWithinOneYear",
                    target = "creditorsAmountsFallingDueWithinOneYear.previousAmount"),
            @Mapping(source = "currentPeriod.balanceSheet.otherLiabilitiesOrAssets.netCurrentAssets",
                    target = "netCurrentAssets.currentAmount"),
            @Mapping(source = "previousPeriod.balanceSheet.otherLiabilitiesOrAssets.netCurrentAssets",
                    target = "netCurrentAssets.previousAmount"),
            @Mapping(source = "currentPeriod.balanceSheet.otherLiabilitiesOrAssets.totalAssetsLessCurrentLiabilities",
                    target = "totalAssetsLessCurrentLiabilities.currentAmount"),
            @Mapping(source = "previousPeriod.balanceSheet.otherLiabilitiesOrAssets.totalAssetsLessCurrentLiabilities",
                    target = "totalAssetsLessCurrentLiabilities.previousAmount"),
            @Mapping(source = "currentPeriod.balanceSheet.otherLiabilitiesOrAssets.creditorsAfterOneYear",
                    target = "creditorsAmountsFallingDueAfterMoreThanOneYear.currentAmount"),
            @Mapping(source = "previousPeriod.balanceSheet.otherLiabilitiesOrAssets.creditorsAfterOneYear",
                    target = "creditorsAmountsFallingDueAfterMoreThanOneYear.previousAmount"),
            @Mapping(source = "currentPeriod.balanceSheet.otherLiabilitiesOrAssets.provisionForLiabilities",
                    target = "provisionForLiabilities.currentAmount"),
            @Mapping(source = "previousPeriod.balanceSheet.otherLiabilitiesOrAssets.provisionForLiabilities",
                    target = "provisionForLiabilities.previousAmount"),
            @Mapping(source = "currentPeriod.balanceSheet.otherLiabilitiesOrAssets.accrualsAndDeferredIncome",
                    target = "accrualsAndDeferredIncome.currentAmount"),
            @Mapping(source = "previousPeriod.balanceSheet.otherLiabilitiesOrAssets.accrualsAndDeferredIncome",
                    target = "accrualsAndDeferredIncome.previousAmount"),
            @Mapping(source = "currentPeriod.balanceSheet.otherLiabilitiesOrAssets.totalNetAssets",
                    target = "currentTotalNetAssets"),
            @Mapping(source = "previousPeriod.balanceSheet.otherLiabilitiesOrAssets.totalNetAssets",
                    target = "previousTotalNetAssets"),
    })
    OtherLiabilitiesOrAssets apiToOtherLiabilitiesOrAssets(CurrentPeriodApi currentPeriod, PreviousPeriodApi previousPeriod);

    @Mappings({
            @Mapping(source = "currentPeriod.balanceSheet.calledUpShareCapitalNotPaid", target = "currentAmount"),
            @Mapping(source = "previousPeriod.balanceSheet.calledUpShareCapitalNotPaid", target = "previousAmount")
    })
    CalledUpSharedCapitalNotPaid apiToCalledUpSharedCapitalNotPaid(CurrentPeriodApi currentPeriod, PreviousPeriodApi previousPeriod);
}

