package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.mappers;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import uk.gov.companieshouse.api.model.company.CompanyProfileApi;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.period.Period;

@Mapper
@DecoratedWith(ApiToPeriodMapperDecorator.class)
public interface ApiToPeriodMapper {

    ApiToPeriodMapper INSTANCE = Mappers.getMapper(ApiToPeriodMapper.class);

    @Mappings({
            @Mapping(source = "accounts.nextAccounts.periodStartOn", target = "currentPeriodStartOn"),
            @Mapping(source = "accounts.nextAccounts.periodEndOn", target = "currentPeriodEndsOn"),
            @Mapping(source = "accounts.lastAccounts.periodStartOn", target = "previousPeriodStartOn"),
            @Mapping(source = "accounts.lastAccounts.periodEndOn", target = "previousPeriodEndsOn"),
    })
    Period apiToPeriod(CompanyProfileApi companyProfile);
}

