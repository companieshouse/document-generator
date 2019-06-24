package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.web.context.annotation.RequestScope;
import uk.gov.companieshouse.api.model.accounts.smallfull.fixedassetsinvestments.FixedAssetsInvestmentsApi;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.fixedassetsinvestments.FixedAssetsInvestments;

@RequestScope
@Mapper(componentModel = "spring")
public interface ApiToFixedAssetsInvestmentsMapper {

    @Mappings({
            @Mapping(source = "fixedAssetsInvestmentsApi.details", target = "details"),
    })
    FixedAssetsInvestments apiToFixedAssetsInvestments(FixedAssetsInvestmentsApi fixedAssetsInvestmentsApi);
}
