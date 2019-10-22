package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.web.context.annotation.RequestScope;
import uk.gov.companieshouse.api.model.accounts.profitandloss.ProfitAndLossApi;
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
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.profitandloss.ProfitAndLoss;

@RequestScope
@Mapper(componentModel = "spring")
public interface ApiToProfitAndLossMapper {

    @Mappings({
            @Mapping(source = "currentPeriodProfitAndLoss.grossProfitOrLoss.costOfSales",
                    target = "costOfSales.currentAmount"),
            @Mapping(source = "previousPeriodProfitAndLoss.grossProfitOrLoss.costOfSales",
                    target = "costOfSales.previousAmount"),
            @Mapping(source = "currentPeriodProfitAndLoss.grossProfitOrLoss.grossTotal",
                    target = "grossTotal.currentAmount"),
            @Mapping(source = "previousPeriodProfitAndLoss.grossProfitOrLoss.grossTotal",
                    target = "grossTotal.previousAmount"),
            @Mapping(source = "currentPeriodProfitAndLoss.grossProfitOrLoss.turnover",
                    target = "turnover.currentAmount"),
            @Mapping(source = "previousPeriodProfitAndLoss.grossProfitOrLoss.turnover",
                    target = "turnover.previousAmount"),
    })
    ProfitAndLoss apiToProfitAndLoss(ProfitAndLossApi currentPeriodProfitAndLoss, ProfitAndLossApi previousPeriodProfitAndLoss);
}

