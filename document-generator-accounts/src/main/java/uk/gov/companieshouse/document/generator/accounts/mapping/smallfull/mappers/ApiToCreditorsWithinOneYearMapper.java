package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.web.context.annotation.RequestScope;
import uk.gov.companieshouse.api.model.accounts.smallfull.creditorswithinoneyear.CurrentPeriod;
import uk.gov.companieshouse.api.model.accounts.smallfull.creditorswithinoneyear.PreviousPeriod;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.creditorswithinoneyear.CreditorsWithinOneYear;

@RequestScope
@Mapper(componentModel = "spring")
public interface ApiToCreditorsWithinOneYearMapper {

    @Mappings({
            @Mapping(source = "creditorsWithinOneYearCurrentPeriod.accrualsAndDeferredIncome",
                    target = "accrualsAndDeferredIncome.currentAmount"),
            @Mapping(source = "creditorsWithinOneYearPreviousPeriod.accrualsAndDeferredIncome",
                    target = "accrualsAndDeferredIncome.previousAmount"),

            @Mapping(source = "creditorsWithinOneYearCurrentPeriod.bankLoansAndOverdrafts",
                    target = "bankLoansAndOverdrafts.currentAmount"),
            @Mapping(source = "creditorsWithinOneYearPreviousPeriod.bankLoansAndOverdrafts",
                    target = "bankLoansAndOverdrafts.previousAmount"),
            
            @Mapping(source = "creditorsWithinOneYearCurrentPeriod.financeLeasesAndHirePurchaseContracts",
                    target = "financeLeasesAndHirePurchaseContracts.currentAmount"),
            @Mapping(source = "creditorsWithinOneYearPreviousPeriod.financeLeasesAndHirePurchaseContracts",
                    target = "financeLeasesAndHirePurchaseContracts.previousAmount"),            

            @Mapping(source = "creditorsWithinOneYearCurrentPeriod.otherCreditors",
                    target = "otherCreditors.currentAmount"),
            @Mapping(source = "creditorsWithinOneYearPreviousPeriod.otherCreditors",
                    target = "otherCreditors.previousAmount"),             

            @Mapping(source = "creditorsWithinOneYearCurrentPeriod.taxationAndSocialSecurity",
                    target = "taxationAndSocialSecurity.currentAmount"),
            @Mapping(source = "creditorsWithinOneYearPreviousPeriod.taxationAndSocialSecurity",
                    target = "taxationAndSocialSecurity.previousAmount"),
            
            @Mapping(source = "creditorsWithinOneYearCurrentPeriod.tradeCreditors",
                    target = "tradeCreditors.currentAmount"),
            @Mapping(source = "creditorsWithinOneYearPreviousPeriod.tradeCreditors",
                    target = "tradeCreditors.previousAmount"),
            
            @Mapping(source = "creditorsWithinOneYearCurrentPeriod.total",
                    target = "total.currentAmount"),
            @Mapping(source = "creditorsWithinOneYearPreviousPeriod.total",
                    target = "total.previousAmount"),

            @Mapping(source = "creditorsWithinOneYearCurrentPeriod.details", target = "details")
    })
    CreditorsWithinOneYear apiToCreditorsWithinOneYear(CurrentPeriod creditorsWithinOneYearCurrentPeriod, PreviousPeriod creditorsWithinOneYearPreviousPeriod);

}
