package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.statements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

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
import uk.gov.companieshouse.api.model.statements.StatementsApi;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.statements.Statements;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.statements.items.Statement;

@ExtendWith({MockitoExtension.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApiToPscStatementsMapperTest {

    @Mock
    private ApiToPscStatementMapper mockApiToStatementMapper;

    @InjectMocks
    private ApiToPscStatementsMapper apiToPscStatementsMapper = new ApiToPscStatementsMapperImpl();

    @Test
    @DisplayName("tests psc statement api data maps to pscs model")
    void testApiToStatementsMaps() {

        StatementsApi statementsApi = createStatementsApi();

        List<Statement> statementList = generatestatementsList();

        when(mockApiToStatementMapper.apiToStatementMapper(statementsApi.getItems())).thenReturn(statementList);

        Statements statements = apiToPscStatementsMapper.apiToStatementsMapper(statementsApi);

        assertNotNull(statements);
        assertEquals(1L, statements.getActiveStatements().longValue());
        assertEquals(2L, statements.getCeasedStatements().longValue());
        assertEquals(statementList, statements.getItems());
    }

    private List<Statement> generatestatementsList() {
        List<Statement> statementList = new ArrayList<>();
        Statement statement1 = new Statement();
        statement1.setStatement("TEST1");
        statementList.add(statement1);

        Statement statement2 = new Statement();
        statement1.setStatement("TEST2");
        statementList.add(statement2);
        return statementList;
    }

    private StatementsApi createStatementsApi() {

        List<StatementApi> statementApiList = new ArrayList<>();

        StatementApi statementApi1 = new StatementApi();
        statementApi1.setStatement("TEST1");

        StatementApi statementApi2 = new StatementApi();
        statementApi1.setStatement("TEST2");

        statementApiList.add(statementApi1);
        statementApiList.add(statementApi2);

        StatementsApi statementsApi = new StatementsApi();
        statementsApi.setItems(statementApiList);
        statementsApi.setActiveCount(1L);
        statementsApi.setCeasedCount(2L);

        return statementsApi;
    }
}
