package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.foreigncompanydetails;


import java.time.Month;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.web.context.annotation.RequestScope;
import uk.gov.companieshouse.api.model.company.foreigncompany.ForeignCompanyDetailsApi;
import uk.gov.companieshouse.api.model.company.foreigncompany.account.ForeignAccountApi;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.foreigncompanydetails.ForeignCompanyDetails;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.foreigncompanydetails.items.Accounts;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.foreigncompanydetails.items.accountsItems.AccountPeriodDayMonth;

@RequestScope
@Mapper(componentModel = "spring")
public abstract class ApiToForeignCompanyDetailsMapper {

    @Mappings({
            @Mapping(source = "originatingRegistry.country", target = "country"),
            @Mapping(source = "originatingRegistry.name", target = "name"),
            @Mapping(target = "accounts.accountPeriodFrom", ignore = true),
            @Mapping(target = "accounts.accountPeriodTo", ignore = true)

    })

    public abstract ForeignCompanyDetails apiToForeignCompanyDetails(ForeignCompanyDetailsApi foreignCompanyDetailsApi);

    @AfterMapping
    protected void convertForeignCompanyDetailsAccountsDates(ForeignAccountApi foreignAccountApi,
            @MappingTarget Accounts accounts) {

        if (foreignAccountApi != null) {

            if (foreignAccountApi.getAccountPeriodFrom() != null &&
                    foreignAccountApi.getAccountPeriodFrom().getMonth() != null &&
                    foreignAccountApi.getAccountPeriodFrom().getDay() != null) {

                accounts.setAccountPeriodFrom(formatAccountPeriod(foreignAccountApi.getAccountPeriodFrom().getDay(), foreignAccountApi.getAccountPeriodFrom().getMonth()));
            }

            if (foreignAccountApi.getAccountPeriodTo() != null &&
                    foreignAccountApi.getAccountPeriodTo().getMonth() != null &&
                    foreignAccountApi.getAccountPeriodTo().getDay() != null) {

                accounts.setAccountPeriodTo(formatAccountPeriod(foreignAccountApi.getAccountPeriodTo().getDay(), foreignAccountApi.getAccountPeriodTo().getMonth()));
            }
        }
    }

    private AccountPeriodDayMonth formatAccountPeriod(String day, String month) {

        AccountPeriodDayMonth accountPeriod = new AccountPeriodDayMonth();
        String monthPeriodToString = getNameOfMonth(month);
        accountPeriod.setDay(day);
        //Sentence case month string
        accountPeriod.setMonth(monthPeriodToString.substring(0, 1).toUpperCase()
                + monthPeriodToString.substring(1).toLowerCase());

        return accountPeriod;
    }

    private String getNameOfMonth(String name) {
        return String.valueOf(Month.of(Integer.valueOf(name)));
    }
}
