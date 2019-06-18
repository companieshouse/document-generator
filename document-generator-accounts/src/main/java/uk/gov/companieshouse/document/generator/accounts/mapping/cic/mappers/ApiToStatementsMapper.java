package uk.gov.companieshouse.document.generator.accounts.mapping.cic.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.springframework.web.context.annotation.RequestScope;
import uk.gov.companieshouse.api.model.accounts.cic.statements.CicStatementsApi;
import uk.gov.companieshouse.document.generator.accounts.mapping.cic.model.Statements;

@RequestScope
@Mapper(componentModel = "spring")
public interface ApiToStatementsMapper {

    ApiToStatementsMapper INSTANCE = Mappers.getMapper(ApiToStatementsMapper.class);

    @Mappings({
            @Mapping(source = "reportStatements.companyActivitiesAndImpact",
                    target = "companyActivitiesAndImpact"),
            @Mapping(source = "reportStatements.consultationWithStakeholders",
                    target = "consultationWithStakeholders"),
            @Mapping(source = "reportStatements.directorsRemuneration",
                    target = "directorsRemuneration"),
            @Mapping(source = "reportStatements.transferOfAssets",
                    target = "transferOfAssets"),
    })
    Statements apiToStatements(CicStatementsApi cicStatements);
}
