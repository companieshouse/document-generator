package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.statements;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.annotation.RequestScope;
import uk.gov.companieshouse.api.model.statements.StatementApi;
import uk.gov.companieshouse.document.generator.common.descriptions.RetrieveApiEnumerationDescription;
import uk.gov.companieshouse.document.generator.company.report.exception.MapperException;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.statements.items.Statement;
import uk.gov.companieshouse.document.generator.company.report.service.CompanyReportApiClientService;

@RequestScope
@Mapper(componentModel = "spring")
public abstract class ApiToPscStatementMapper {

    @Autowired
    private RetrieveApiEnumerationDescription retrieveApiEnumerationDescription;

    @Autowired
    private CompanyReportApiClientService companyReportApiClientService;

    private static final String CONSTANTS = "CONSTANTS";
    private static final String D_MMMM_UUUU = "d MMMM uuuu";

    @Mappings({
            @Mapping(target = "statement", ignore = true),
            @Mapping(target = "notifiedOn", ignore = true),
            @Mapping(target = "ceasedOn", ignore = true)
    })
    public abstract Statement ApiToStatementMapper(StatementApi statementApi) throws MapperException;
    public abstract List<Statement> ApiToStatementMapper(List<StatementApi> statementApiList) throws MapperException;

    @AfterMapping
    protected void convertStatement(StatementApi statementApi, @MappingTarget Statement statement) {
    }

    @AfterMapping
    protected void formatNotifiedOnDate(StatementApi statementApi, @MappingTarget Statement statement) {

    }

    @AfterMapping
    protected void formatCeasedOnDate(StatementApi statementApi, @MappingTarget Statement statement) {

    }

    private Map<String, String> getDebugMap(String debugString) {

        Map<String, String> debugMap = new HashMap<>();
        debugMap.put("Enumeration mapping :", debugString);

        return debugMap;
    }

    private DateTimeFormatter getFormatter() {
        return DateTimeFormatter.ofPattern(D_MMMM_UUUU);
    }
}
