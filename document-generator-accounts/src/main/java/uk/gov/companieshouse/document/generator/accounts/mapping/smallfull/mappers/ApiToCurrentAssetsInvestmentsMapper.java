package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.web.context.annotation.RequestScope;
import uk.gov.companieshouse.api.model.accounts.smallfull.currentassetsinvestments.CurrentAssetsInvestmentsApi;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.currentassetsinvestments.CurrentAssetsInvestments;

@RequestScope
    @Mapper(componentModel = "spring")
    public interface ApiToCurrentAssetsInvestmentsMapper {

        @Mappings({
                @Mapping(source = "currentAssetsInvestmentsApi.details", target = "details"),
        })
        CurrentAssetsInvestments apiToCurrentAssetsInvestments(CurrentAssetsInvestmentsApi currentAssetsInvestmentsApi);
}
