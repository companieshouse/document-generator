package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.web.context.annotation.RequestScope;
import uk.gov.companieshouse.api.model.accounts.smallfull.BalanceSheetStatementsApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.CurrentPeriodApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.PreviousPeriodApi;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.BalanceSheetStatements;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.CalledUpSharedCapitalNotPaid;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.capitalandreserves.CapitalAndReserve;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.currentassets.CurrentAssets;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.fixedassets.FixedAssets;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.membersfunds.MembersFunds;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.otherliabilitiesandassets.OtherLiabilitiesOrAssets;

@RequestScope
@Mapper(componentModel = "spring")
public interface ApiToBalanceSheetMapper {

    @Mappings({
            @Mapping(source = "currentPeriod.balanceSheet.membersFunds.profitAndLossAccount",
                    target = "profitAndLossAccount.currentAmount"),
            @Mapping(source = "previousPeriod.balanceSheet.membersFunds.profitAndLossAccount",
                    target = "profitAndLossAccount.previousAmount"),
            @Mapping(source = "currentPeriod.balanceSheet.membersFunds.totalMembersFunds",
                    target = "totalMembersFunds.currentAmount"),
            @Mapping(source = "previousPeriod.balanceSheet.membersFunds.totalMembersFunds",
                    target = "totalMembersFunds.previousAmount")
    })
    MembersFunds apiToMembersFunds(CurrentPeriodApi currentPeriod, PreviousPeriodApi previousPeriod);

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
            @Mapping(source = "currentPeriod.balanceSheet.capitalAndReserves" +
                    ".totalShareholdersFunds",
                    target = "totalShareHoldersFunds.currentAmount"),
            @Mapping(source = "previousPeriod.balanceSheet.capitalAndReserves" +
                    ".totalShareholdersFunds",
                    target = "totalShareHoldersFunds.previousAmount"),
    })
    CapitalAndReserve apiToCapitalAndReserve(CurrentPeriodApi currentPeriod,
            PreviousPeriodApi previousPeriod);

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
    CurrentAssets apiToCurrentAssets(CurrentPeriodApi currentPeriod,
            PreviousPeriodApi previousPeriod);

    @Mappings({
            @Mapping(source = "currentPeriod.balanceSheet.fixedAssets.tangible",
                    target = "tangibleAssets.currentAmount"),
            @Mapping(source = "previousPeriod.balanceSheet.fixedAssets.tangible",
                    target = "tangibleAssets.previousAmount"),
            @Mapping(source = "currentPeriod.balanceSheet.fixedAssets.investments",
                    target = "investments.currentAmount"),
            @Mapping(source = "previousPeriod.balanceSheet.fixedAssets.investments",
                    target = "investments.previousAmount"),
            @Mapping(source = "currentPeriod.balanceSheet.fixedAssets.total",
                    target = "totalFixedAssetsCurrent"),
            @Mapping(source = "previousPeriod.balanceSheet.fixedAssets.total",
                    target = "totalFixedAssetsPrevious")
    })
    FixedAssets apiToFixedAssets(CurrentPeriodApi currentPeriod, PreviousPeriodApi previousPeriod);

    @Mappings({
            @Mapping(source = "currentPeriod.balanceSheet.otherLiabilitiesOrAssets" +
                    ".prepaymentsAndAccruedIncome",
                    target = "prepaymentsAndAccruedIncome.currentAmount"),
            @Mapping(source = "previousPeriod.balanceSheet.otherLiabilitiesOrAssets" +
                    ".prepaymentsAndAccruedIncome",
                    target = "prepaymentsAndAccruedIncome.previousAmount"),
            @Mapping(source = "currentPeriod.balanceSheet.otherLiabilitiesOrAssets" +
                    ".creditorsDueWithinOneYear",
                    target = "creditorsAmountsFallingDueWithinOneYear.currentAmount"),
            @Mapping(source = "previousPeriod.balanceSheet.otherLiabilitiesOrAssets" +
                    ".creditorsDueWithinOneYear",
                    target = "creditorsAmountsFallingDueWithinOneYear.previousAmount"),
            @Mapping(source = "currentPeriod.balanceSheet.otherLiabilitiesOrAssets" +
                    ".netCurrentAssets",
                    target = "netCurrentAssets.currentAmount"),
            @Mapping(source = "previousPeriod.balanceSheet.otherLiabilitiesOrAssets" +
                    ".netCurrentAssets",
                    target = "netCurrentAssets.previousAmount"),
            @Mapping(source = "currentPeriod.balanceSheet.otherLiabilitiesOrAssets" +
                    ".totalAssetsLessCurrentLiabilities",
                    target = "totalAssetsLessCurrentLiabilities.currentAmount"),
            @Mapping(source = "previousPeriod.balanceSheet.otherLiabilitiesOrAssets" +
                    ".totalAssetsLessCurrentLiabilities",
                    target = "totalAssetsLessCurrentLiabilities.previousAmount"),
            @Mapping(source = "currentPeriod.balanceSheet.otherLiabilitiesOrAssets" +
                    ".creditorsAfterOneYear",
                    target = "creditorsAmountsFallingDueAfterMoreThanOneYear.currentAmount"),
            @Mapping(source = "previousPeriod.balanceSheet.otherLiabilitiesOrAssets" +
                    ".creditorsAfterOneYear",
                    target = "creditorsAmountsFallingDueAfterMoreThanOneYear.previousAmount"),
            @Mapping(source = "currentPeriod.balanceSheet.otherLiabilitiesOrAssets" +
                    ".provisionForLiabilities",
                    target = "provisionForLiabilities.currentAmount"),
            @Mapping(source = "previousPeriod.balanceSheet.otherLiabilitiesOrAssets" +
                    ".provisionForLiabilities",
                    target = "provisionForLiabilities.previousAmount"),
            @Mapping(source = "currentPeriod.balanceSheet.otherLiabilitiesOrAssets" +
                    ".accrualsAndDeferredIncome",
                    target = "accrualsAndDeferredIncome.currentAmount"),
            @Mapping(source = "previousPeriod.balanceSheet.otherLiabilitiesOrAssets" +
                    ".accrualsAndDeferredIncome",
                    target = "accrualsAndDeferredIncome.previousAmount"),
            @Mapping(source = "currentPeriod.balanceSheet.otherLiabilitiesOrAssets.totalNetAssets",
                    target = "currentTotalNetAssets"),
            @Mapping(source = "previousPeriod.balanceSheet.otherLiabilitiesOrAssets.totalNetAssets",
                    target = "previousTotalNetAssets"),
    })
    OtherLiabilitiesOrAssets apiToOtherLiabilitiesOrAssets(CurrentPeriodApi currentPeriod,
            PreviousPeriodApi previousPeriod);

    @Mappings({
            @Mapping(source = "currentPeriod.balanceSheet.calledUpShareCapitalNotPaid", target =
                    "currentAmount"),
            @Mapping(source = "previousPeriod.balanceSheet.calledUpShareCapitalNotPaid", target =
                    "previousAmount")
    })
    CalledUpSharedCapitalNotPaid apiToCalledUpSharedCapitalNotPaid(CurrentPeriodApi currentPeriod
            , PreviousPeriodApi previousPeriod);

    @Mappings({
            @Mapping(source = "balanceSheetStatements.legalStatements.section477",
                    target = "section477"),
            @Mapping(source = "balanceSheetStatements.legalStatements.auditNotRequiredByMembers",
                    target = "auditNotRequiredByMembers"),
            @Mapping(source = "balanceSheetStatements.legalStatements.directorsResponsibility",
                    target = "directorsResponsibility"),
            @Mapping(source = "balanceSheetStatements.legalStatements.smallCompaniesRegime",
                    target = "smallCompaniesRegime"),
            @Mapping(source = "balanceSheetStatements.legalStatements.noProfitAndLoss",
                    target = "noProfitAndLoss")
    })
    BalanceSheetStatements apiToStatements(BalanceSheetStatementsApi balanceSheetStatements);
}

