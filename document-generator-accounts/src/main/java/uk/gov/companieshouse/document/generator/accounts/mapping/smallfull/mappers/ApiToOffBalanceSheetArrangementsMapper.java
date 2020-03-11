package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.web.context.annotation.RequestScope;
import uk.gov.companieshouse.api.model.accounts.smallfull.offBalanceSheet.OffBalanceSheetApi;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.offbalancesheetarrangements.OffBalanceSheetArrangements;

@RequestScope
@Mapper(componentModel = "spring")
public interface ApiToOffBalanceSheetArrangementsMapper {

    @Mappings({
            @Mapping(source = "offBalanceSheetApi.details", target = "details"),
    })
    OffBalanceSheetArrangements apiToOffBalanceSheetArrangements(OffBalanceSheetApi offBalanceSheetApi);
}
