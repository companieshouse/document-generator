package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import uk.gov.companieshouse.api.model.accounts.smallfull.CurrentPeriodApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.PreviousPeriodApi;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.capitalandreserves.CapitalAndReserve;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.currentassets.CurrentAssets;

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
}

