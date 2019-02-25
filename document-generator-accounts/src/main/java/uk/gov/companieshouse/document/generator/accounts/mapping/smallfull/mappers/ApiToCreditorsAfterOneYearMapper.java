package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import uk.gov.companieshouse.api.model.accounts.smallfull.creditorsafteroneyear.CurrentPeriod;
import uk.gov.companieshouse.api.model.accounts.smallfull.creditorsafteroneyear.PreviousPeriod;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.creditorsafteroneyear.CreditorsAfterOneYear;

@Mapper
public interface ApiToCreditorsAfterOneYearMapper {

    ApiToCreditorsAfterOneYearMapper INSTANCE = Mappers.getMapper(ApiToCreditorsAfterOneYearMapper.class);

    @Mappings({

            @Mapping(source = "creditorsAfterOneYearCurrentPeriod.bankLoansAndOverdrafts",
                    target = "bankLoansAndOverdrafts.currentAmount"),
            @Mapping(source = "creditorsAfterOneYearPreviousPeriod.bankLoansAndOverdrafts",
                    target = "bankLoansAndOverdrafts.previousAmount"),
            
            @Mapping(source = "creditorsAfterOneYearCurrentPeriod.financeLeasesAndHirePurchaseContracts",
                    target = "financeLeasesAndHirePurchaseContracts.currentAmount"),
            @Mapping(source = "creditorsAfterOneYearPreviousPeriod.financeLeasesAndHirePurchaseContracts",
                    target = "financeLeasesAndHirePurchaseContracts.previousAmount"),            

            @Mapping(source = "creditorsAfterOneYearCurrentPeriod.otherCreditors",
                    target = "otherCreditors.currentAmount"),
            @Mapping(source = "creditorsAfterOneYearPreviousPeriod.otherCreditors",
                    target = "otherCreditors.previousAmount"),             
            
            @Mapping(source = "creditorsAfterOneYearCurrentPeriod.total",
                    target = "total.currentAmount"),
            @Mapping(source = "creditorsAfterOneYearPreviousPeriod.total",
                    target = "total.previousAmount"),

            @Mapping(source = "creditorsAfterOneYearCurrentPeriod.details", target = "details")
    })
    CreditorsAfterOneYear apiToCreditorsAfterOneYear(CurrentPeriod creditorsAfterOneYearCurrentPeriod, PreviousPeriod creditorsAfterOneYearPreviousPeriod);

}
