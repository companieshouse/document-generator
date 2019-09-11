package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.statements;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.web.context.annotation.RequestScope;
import uk.gov.companieshouse.api.model.statements.StatementsApi;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.statements.Statements;

@RequestScope
@Mapper(componentModel = "spring", uses = {ApiToPscStatementMapper.class})
public interface ApiToPscStatementsMapper {

    @Mappings({
            @Mapping(source = "activeCount", target = "activeStatements"),
            @Mapping(source = "ceasedCount", target = "ceasedStatements"),
    })
    Statements apiToStatementsMapper(StatementsApi statementsApi);
}

