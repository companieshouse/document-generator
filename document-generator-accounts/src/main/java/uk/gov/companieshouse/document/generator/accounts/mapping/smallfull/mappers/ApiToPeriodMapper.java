package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.mappers;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.web.context.annotation.RequestScope;
import uk.gov.companieshouse.api.model.accounts.smallfull.SmallFullApi;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.period.Period;

@RequestScope
@Mapper(componentModel = "spring")
@DecoratedWith(ApiToPeriodMapperDecorator.class)
public interface ApiToPeriodMapper {

    @Mappings({
            @Mapping(source = "nextAccounts.periodStartOn", target = "currentPeriodStartOn"),
            @Mapping(source = "nextAccounts.periodEndOn", target = "currentPeriodEndsOn"),
            @Mapping(source = "lastAccounts.periodStartOn", target = "previousPeriodStartOn"),
            @Mapping(source = "lastAccounts.periodEndOn", target = "previousPeriodEndsOn"),
    })
    Period apiToPeriod(SmallFullApi smallFullApi);
}

