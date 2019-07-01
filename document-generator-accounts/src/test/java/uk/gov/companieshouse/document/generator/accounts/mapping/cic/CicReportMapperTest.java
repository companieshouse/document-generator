package uk.gov.companieshouse.document.generator.accounts.mapping.cic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.model.accounts.cic.approval.CicApprovalApi;
import uk.gov.companieshouse.api.model.accounts.cic.statements.CicStatementsApi;
import uk.gov.companieshouse.document.generator.accounts.mapping.cic.mappers.ApiToApprovalMapper;
import uk.gov.companieshouse.document.generator.accounts.mapping.cic.mappers.ApiToStatementsMapper;
import uk.gov.companieshouse.document.generator.accounts.mapping.cic.mappers.CicReportMapper;
import uk.gov.companieshouse.document.generator.accounts.mapping.cic.model.Approval;
import uk.gov.companieshouse.document.generator.accounts.mapping.cic.model.CicReport;
import uk.gov.companieshouse.document.generator.accounts.mapping.cic.model.CicReportApiData;
import uk.gov.companieshouse.document.generator.accounts.mapping.cic.model.Statements;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CicReportMapperTest {

    @Mock
    private ApiToApprovalMapper apiToApprovalMapper;

    @Mock
    private ApiToStatementsMapper apiToStatementsMapper;

    @InjectMocks
    private CicReportMapper cicReportMapper;

    @Mock
    private Statements statements;

    @Mock
    private Approval approval;

    @Test
    @DisplayName("Map cic report with statements and approval")
    void mapCicReportWithStatementsAndApproval() {

        CicReportApiData cicReportApiData = new CicReportApiData();

        CicStatementsApi cicStatementsApi = new CicStatementsApi();
        cicReportApiData.setCicStatements(cicStatementsApi);

        CicApprovalApi cicApprovalApi = new CicApprovalApi();
        cicReportApiData.setCicApproval(cicApprovalApi);

        when(apiToStatementsMapper.apiToStatements(cicStatementsApi))
                .thenReturn(statements);

        when(apiToApprovalMapper.apiToApproval(cicApprovalApi))
                .thenReturn(approval);

        CicReport cicReport = cicReportMapper.mapCicReport(cicReportApiData);

        assertNotNull(cicReport);
        assertEquals(statements, cicReport.getStatements());
        assertEquals(approval, cicReport.getApproval());
    }

    @Test
    @DisplayName("Map cic report without statements and approval")
    void mapCicReportWithoutStatementsAndApproval() {

        CicReport cicReport = cicReportMapper.mapCicReport(new CicReportApiData());

        assertNotNull(cicReport);
        assertNull(cicReport.getStatements());
        assertNull(cicReport.getApproval());

        verify(apiToStatementsMapper, never()).apiToStatements(any(CicStatementsApi.class));

        verify(apiToApprovalMapper, never()).apiToApproval(any(CicApprovalApi.class));
    }
}
