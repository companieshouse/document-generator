package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.statements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.model.statements.StatementApi;
import uk.gov.companieshouse.document.generator.common.descriptions.RetrieveApiEnumerationDescription;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.statements.items.Statement;

@ExtendWith({MockitoExtension.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApiToPscStatementMapperTest {

    public static final String MAPPED_VALUE = "mapped value";
    public static final String PSC_STATEMENT_NAME_PLACEHOLDER = "{linked_psc_name} has failed to comply with a notice given by the company under section 790E or EA of the Act";
    public static final String PSC_STATEMENT_LINKED_NAME = "A Valid Linked PSC Name";
    public static final String PSC_STATEMENT_EXPECTED_VALUE = PSC_STATEMENT_LINKED_NAME + " has failed to comply with a notice given by the company under section 790E or EA of the Act";
    @InjectMocks
    private ApiToPscStatementMapper apiToPscStatementMapper = new ApiToPscStatementMapperImpl();

    private static final LocalDate CEASED_ON = LocalDate.of(
            2019, 06, 06);
    private static final LocalDate NOTIFIED_ON = LocalDate.of(
            2019, 05, 05);

    @Mock
    RetrieveApiEnumerationDescription mockRetrieveApiEnumerations;

    @Test
    @DisplayName("tests PSC statement maps to PSC statement model")
    void testApiToPSCStatementMaps() {

        StatementApi statementApi = createStatementApi();

        when(mockRetrieveApiEnumerations.getApiEnumerationDescription(anyString(), anyString(),
                anyString(), any())).thenReturn(MAPPED_VALUE);

        Statement statement = apiToPscStatementMapper.ApiToStatementMapper(statementApi);

        assertNotNull(statement);

        assertEquals(MAPPED_VALUE, statement.getStatement());
        assertEquals("6 June 2019", statement.getCeasedOn());
        assertEquals("5 May 2019", statement.getNotifiedOn());

    }

    @Test
    @DisplayName("tests PSC statement with placeholder statement value maps to PSC statement model")
    void testApiWithPlaceholderToPSCStatementMaps() {

        StatementApi statementApi = createStatementApiWithPlaceholder();

        when(mockRetrieveApiEnumerations.getApiEnumerationDescription(anyString(), anyString(),
                anyString(), any())).thenReturn(PSC_STATEMENT_NAME_PLACEHOLDER);

        Statement statement = apiToPscStatementMapper.ApiToStatementMapper(statementApi);

        assertNotNull(statement);

        assertEquals(PSC_STATEMENT_EXPECTED_VALUE, statement.getStatement());

    }

    private StatementApi createStatementApi() {

        StatementApi statementApi = new StatementApi();

        statementApi.setStatement("statement1");
        statementApi.setCeasedOn(CEASED_ON);
        statementApi.setNotifiedOn(NOTIFIED_ON);

        return statementApi;

    }

    private StatementApi createStatementApiWithPlaceholder() {
        StatementApi statementApi = new StatementApi();

        statementApi.setLinkedPscName(PSC_STATEMENT_LINKED_NAME);
        statementApi.setStatement(PSC_STATEMENT_NAME_PLACEHOLDER);

        return statementApi;
    }

    @Test
    @DisplayName("tests muliple PSC statement data maps to PSC statement model")
    void testMultipleApiToPSCStatementMaps() {

        List<StatementApi> statementApiList = createStatementApiList();

        when(mockRetrieveApiEnumerations.getApiEnumerationDescription(anyString(), anyString(),
                anyString(), any())).thenReturn(MAPPED_VALUE);


        List<Statement> statement = apiToPscStatementMapper.ApiToStatementMapper(statementApiList);

        assertNotNull(statement);
        assertEquals(MAPPED_VALUE, statement.get(0).getStatement());
        assertEquals(MAPPED_VALUE,statement.get(1).getStatement());
        assertEquals(MAPPED_VALUE, statement.get(2).getStatement());

    }

    private List<StatementApi> createStatementApiList() {
        List<StatementApi> statementList = new ArrayList<>();

        StatementApi statementApi1 = new StatementApi();
        statementApi1.setStatement("statement1");
        StatementApi statementApi2 = new StatementApi();
        statementApi2.setStatement("statement2");
        StatementApi statementApi3 = new StatementApi();
        statementApi3.setStatement("statement3");

        statementList.add(statementApi1);
        statementList.add(statementApi2);
        statementList.add(statementApi3);

        return  statementList;
    }
}
