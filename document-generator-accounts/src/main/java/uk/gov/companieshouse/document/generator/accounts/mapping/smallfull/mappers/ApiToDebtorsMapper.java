package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.web.context.annotation.RequestScope;
import uk.gov.companieshouse.api.model.accounts.smallfull.Debtors.CurrentPeriod;
import uk.gov.companieshouse.api.model.accounts.smallfull.Debtors.PreviousPeriod;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.debtors.Debtors;

@RequestScope
@Mapper(componentModel = "spring")
public interface ApiToDebtorsMapper {

    @Mappings({
            @Mapping(source = "debtorsCurrentPeriod.greaterThanOneYear",
                    target = "greaterThanOneYear.currentAmount"),
            @Mapping(source = "debtorsPreviousPeriod.greaterThanOneYear",
                    target = "greaterThanOneYear.previousAmount"),

            @Mapping(source = "debtorsCurrentPeriod.otherDebtors",
                    target = "otherDebtors.currentAmount"),
            @Mapping(source = "debtorsPreviousPeriod.otherDebtors",
                    target = "otherDebtors.previousAmount"),

            @Mapping(source = "debtorsCurrentPeriod.prepaymentsAndAccruedIncome",
                    target = "prepaymentsAndAccruedIncome.currentAmount"),
            @Mapping(source = "debtorsPreviousPeriod.prepaymentsAndAccruedIncome",
                    target = "prepaymentsAndAccruedIncome.previousAmount"),

            @Mapping(source = "debtorsCurrentPeriod.tradeDebtors",
                    target = "tradeDebtors.currentAmount"),
            @Mapping(source = "debtorsPreviousPeriod.tradeDebtors",
                    target = "tradeDebtors.previousAmount"),

            @Mapping(source = "debtorsCurrentPeriod.total",
                    target = "total.currentAmount"),
            @Mapping(source = "debtorsPreviousPeriod.total",
                    target = "total.previousAmount"),

            @Mapping(source = "debtorsCurrentPeriod.details", target = "details"),
    })
    Debtors apiToDebtors(CurrentPeriod debtorsCurrentPeriod, PreviousPeriod debtorsPreviousPeriod);

}
