package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.statements;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.annotation.RequestScope;
import uk.gov.companieshouse.api.model.statements.StatementApi;
import uk.gov.companieshouse.document.generator.common.descriptions.RetrieveApiEnumerationDescription;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.statements.items.Statement;
import uk.gov.companieshouse.document.generator.company.report.service.CompanyReportApiClientService;

@RequestScope
@Mapper(componentModel = "spring")
public abstract class ApiToPscStatementMapper {

    @Autowired
    private RetrieveApiEnumerationDescription retrieveApiEnumerationDescription;

    @Autowired
    private CompanyReportApiClientService companyReportApiClientService;

    private static final String PSC_DESCRIPTIONS = "PSC_DESCRIPTIONS";
    private static final String IDENTIFIER = "statement_description";
    private static final String D_MMMM_UUUU = "d MMMM uuuu";
    private static final String PSC_STATEMENT_NAME_PLACEHOLDER = "\\{linked_psc_name}";

    @Mappings({
            @Mapping(target = "statement", ignore = true),
            @Mapping(target = "notifiedOn", ignore = true),
            @Mapping(target = "ceasedOn", ignore = true)
    })
    public abstract Statement ApiToStatementMapper(StatementApi statementApi);

    public abstract List<Statement> ApiToStatementMapper(List<StatementApi> statementApiList);

    @AfterMapping
    protected void convertStatement(StatementApi statementApi, @MappingTarget Statement statement) {

        String statementDescription = retrieveApiEnumerationDescription
            .getApiEnumerationDescription(PSC_DESCRIPTIONS, IDENTIFIER, statementApi.getStatement(),
                getDebugMap(statementApi.getStatement()));

        if (statementDescription.contains(PSC_STATEMENT_NAME_PLACEHOLDER) && StringUtils.isNotBlank(statementApi.getLinkedPscName())) {
            statementDescription = statementDescription.replaceAll(PSC_STATEMENT_NAME_PLACEHOLDER, statementApi.getLinkedPscName());
        }

        statement.setStatement(statementDescription);
    }

    @AfterMapping
    protected void formatNotifiedOnDate(StatementApi statementApi, @MappingTarget Statement statement) {

        if(statementApi != null && statementApi.getNotifiedOn() != null) {
            LocalDate notifiedOnDate = statementApi.getNotifiedOn();
            statement.setNotifiedOn(notifiedOnDate.format(getFormatter()));
        }
    }

    @AfterMapping
    protected void formatCeasedOnDate(StatementApi statementApi, @MappingTarget Statement statement) {

        if(statementApi != null && statementApi.getCeasedOn() != null) {
            LocalDate ceasedOnDate = statementApi.getCeasedOn();
            statement.setCeasedOn(ceasedOnDate.format(getFormatter()));
        }
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
