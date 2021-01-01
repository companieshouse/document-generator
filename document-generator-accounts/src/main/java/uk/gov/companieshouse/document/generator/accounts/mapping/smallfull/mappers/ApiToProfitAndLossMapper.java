package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.web.context.annotation.RequestScope;
import uk.gov.companieshouse.api.model.accounts.profitandloss.ProfitAndLossApi;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.profitandloss.ProfitAndLoss;

@RequestScope
@Mapper(componentModel = "spring")
public interface ApiToProfitAndLossMapper {

    @Mappings({
            @Mapping(source = "currentPeriodProfitAndLoss.grossProfitOrLoss.costOfSales",
                    target = "grossProfitOrLoss.costOfSales.currentAmount"),
            @Mapping(source = "previousPeriodProfitAndLoss.grossProfitOrLoss.costOfSales",
                    target = "grossProfitOrLoss.costOfSales.previousAmount"),
            @Mapping(source = "currentPeriodProfitAndLoss.grossProfitOrLoss.grossTotal",
                    target = "grossProfitOrLoss.grossTotal.currentAmount"),
            @Mapping(source = "previousPeriodProfitAndLoss.grossProfitOrLoss.grossTotal",
                    target = "grossProfitOrLoss.grossTotal.previousAmount"),
            @Mapping(source = "currentPeriodProfitAndLoss.grossProfitOrLoss.turnover",
                    target = "grossProfitOrLoss.turnover.currentAmount"),
            @Mapping(source = "previousPeriodProfitAndLoss.grossProfitOrLoss.turnover",
                    target = "grossProfitOrLoss.turnover.previousAmount"),
            @Mapping(source = "currentPeriodProfitAndLoss.operatingProfitOrLoss.distributionCosts",
                    target = "operatingProfitOrLoss.distributionCosts.currentAmount"),
            @Mapping(source = "previousPeriodProfitAndLoss.operatingProfitOrLoss.distributionCosts",
                    target = "operatingProfitOrLoss.distributionCosts.previousAmount"),
            @Mapping(source = "currentPeriodProfitAndLoss.operatingProfitOrLoss.administrativeExpenses",
                    target = "operatingProfitOrLoss.administrativeExpenses.currentAmount"),
            @Mapping(source = "previousPeriodProfitAndLoss.operatingProfitOrLoss.administrativeExpenses",
                    target = "operatingProfitOrLoss.administrativeExpenses.previousAmount"),
            @Mapping(source = "currentPeriodProfitAndLoss.operatingProfitOrLoss.otherOperatingIncome",
                    target = "operatingProfitOrLoss.otherOperatingIncome.currentAmount"),
            @Mapping(source = "previousPeriodProfitAndLoss.operatingProfitOrLoss.otherOperatingIncome",
                    target = "operatingProfitOrLoss.otherOperatingIncome.previousAmount"),
            @Mapping(source = "currentPeriodProfitAndLoss.operatingProfitOrLoss.operatingTotal",
                    target = "operatingProfitOrLoss.operatingTotal.currentAmount"),
            @Mapping(source = "previousPeriodProfitAndLoss.operatingProfitOrLoss.operatingTotal",
                    target = "operatingProfitOrLoss.operatingTotal.previousAmount"),
            @Mapping(source = "currentPeriodProfitAndLoss.profitOrLossBeforeTax.interestReceivableAndSimilarIncome",
                    target = "profitOrLossBeforeTax.interestReceivableAndSimilarIncome.currentAmount"),
            @Mapping(source = "previousPeriodProfitAndLoss.profitOrLossBeforeTax.interestReceivableAndSimilarIncome",
                    target = "profitOrLossBeforeTax.interestReceivableAndSimilarIncome.previousAmount"),
            @Mapping(source = "currentPeriodProfitAndLoss.profitOrLossBeforeTax.interestPayableAndSimilarCharges",
                    target = "profitOrLossBeforeTax.interestPayableAndSimilarCharges.currentAmount"),
            @Mapping(source = "previousPeriodProfitAndLoss.profitOrLossBeforeTax.interestPayableAndSimilarCharges",
                    target = "profitOrLossBeforeTax.interestPayableAndSimilarCharges.previousAmount"),
            @Mapping(source = "currentPeriodProfitAndLoss.profitOrLossBeforeTax.totalProfitOrLossBeforeTax",
                    target = "profitOrLossBeforeTax.totalProfitOrLossBeforeTax.currentAmount"),
            @Mapping(source = "previousPeriodProfitAndLoss.profitOrLossBeforeTax.totalProfitOrLossBeforeTax",
                    target = "profitOrLossBeforeTax.totalProfitOrLossBeforeTax.previousAmount"),
            @Mapping(source = "currentPeriodProfitAndLoss.profitOrLossForFinancialYear.tax",
                    target = "profitOrLossForFinancialYear.tax.currentAmount"),
            @Mapping(source = "previousPeriodProfitAndLoss.profitOrLossForFinancialYear.tax",
                    target = "profitOrLossForFinancialYear.tax.previousAmount"),
            @Mapping(source = "currentPeriodProfitAndLoss.profitOrLossForFinancialYear.totalProfitOrLossForFinancialYear",
                    target = "profitOrLossForFinancialYear.totalProfitOrLossForFinancialYear.currentAmount"),
            @Mapping(source = "previousPeriodProfitAndLoss.profitOrLossForFinancialYear.totalProfitOrLossForFinancialYear",
                    target = "profitOrLossForFinancialYear.totalProfitOrLossForFinancialYear.previousAmount"),
    })
    ProfitAndLoss apiToProfitAndLoss(ProfitAndLossApi currentPeriodProfitAndLoss, ProfitAndLossApi previousPeriodProfitAndLoss);
}

