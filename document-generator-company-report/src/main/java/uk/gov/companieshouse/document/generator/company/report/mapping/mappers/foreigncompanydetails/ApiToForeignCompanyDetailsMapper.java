package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.foreigncompanydetails;


import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.web.context.annotation.RequestScope;
import uk.gov.companieshouse.api.model.company.foreigncompany.ForeignCompanyDetailsApi;
import uk.gov.companieshouse.api.model.company.foreigncompany.account.ForeignAccountApi;
import uk.gov.companieshouse.document.generator.company.report.exception.MapperException;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.foreigncompanydetails.ForeignCompanyDetails;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.foreigncompanydetails.items.Accounts;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.foreigncompanydetails.items.accountsItems.AccountPeriodFrom;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.foreigncompanydetails.items.accountsItems.AccountPeriodTo;

import java.time.Month;

@RequestScope
@Mapper(componentModel = "spring")
public abstract class ApiToForeignCompanyDetailsMapper {

    @Mappings({
        @Mapping(source = "originatingRegistry.country", target = "country"),
        @Mapping(source = "originatingRegistry.name", target = "name"),
        @Mapping(target = "accounts.accountPeriodFrom", ignore = true),
        @Mapping(target = "accounts.accountPeriodTo", ignore = true),
    })

    public abstract ForeignCompanyDetails apiToForeignCompanyDetails(ForeignCompanyDetailsApi foreignCompanyDetailsApi) throws MapperException;

    @AfterMapping
    protected void convertForeignCompanyDetailsAccountsDates(ForeignAccountApi foreignAccountApi,
                                                             @MappingTarget Accounts accounts) {

        if(foreignAccountApi != null) {

            if(foreignAccountApi.getAccountPeriodFrom() != null &&
                foreignAccountApi.getAccountPeriodFrom().getMonth() !=null &&
                foreignAccountApi.getAccountPeriodFrom().getDay() !=null) {

                formatAccountPeriod(foreignAccountApi, accounts);
            }

            if(foreignAccountApi.getAccountPeriodTo() != null &&
                foreignAccountApi.getAccountPeriodTo().getMonth() !=null &&
                foreignAccountApi.getAccountPeriodTo().getDay() !=null) {

                formatAccountPeriod(foreignAccountApi, accounts);
            }
        }
    }

    private void formatAccountPeriod(ForeignAccountApi foreignAccountApi, Accounts accounts) {

        AccountPeriodTo accountPeriodTo = new AccountPeriodTo();
        AccountPeriodFrom accountPeriodFrom = new AccountPeriodFrom();

        String monthPeriodFromString = getNameOfMonthAccountPeriodFrom(foreignAccountApi);
        String monthPeriodToString = getNameOfMonthAccountPeriodTo(foreignAccountApi);

        accountPeriodFrom.setDay((foreignAccountApi.getAccountPeriodFrom().getDay()));
        //Sentence case month string
        accountPeriodFrom.setMonth(monthPeriodFromString.substring(0,1).toUpperCase()
            + monthPeriodFromString.substring(1).toLowerCase());

        accountPeriodTo.setDay((foreignAccountApi.getAccountPeriodTo().getDay()));
        //Sentence case month string
        accountPeriodTo.setMonth(monthPeriodToString.substring(0,1).toUpperCase()
            + monthPeriodToString.substring(1).toLowerCase());

        accounts.setAccountPeriodFrom(accountPeriodFrom);
        accounts.setAccountPeriodTo(accountPeriodTo);
    }

    private String getNameOfMonthAccountPeriodFrom(ForeignAccountApi foreignAccountApi) {
        return Month.of(Integer.valueOf(foreignAccountApi.getAccountPeriodFrom().getMonth())).name();
    }

    private String getNameOfMonthAccountPeriodTo(ForeignAccountApi foreignAccountApi) {
        return Month.of(Integer.valueOf(foreignAccountApi.getAccountPeriodTo().getMonth())).name();
    }
}
