package uk.gov.companieshouse.document.generator.accounts.mapping.cic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uk.gov.companieshouse.api.model.accounts.cic.statements.CicStatementsApi;
import uk.gov.companieshouse.api.model.accounts.cic.statements.ReportStatementsApi;
import uk.gov.companieshouse.document.generator.accounts.mapping.cic.mappers.ApiToStatementsMapper;
import uk.gov.companieshouse.document.generator.accounts.mapping.cic.mappers.ApiToStatementsMapperImpl;
import uk.gov.companieshouse.document.generator.accounts.mapping.cic.model.Statements;

public class ApiToStatementsMapperTest {

    private ApiToStatementsMapper apiToStatementsMapper = new ApiToStatementsMapperImpl();

    private static final String COMPANY_ACTIVITIES_AND_IMPACT = "companyActivitiesAndImpact";

    private static final String CONSULTATION_WITH_STAKEHOLDERS = "consultationWithStakeholders";

    private static final String DIRECTORS_REMUNERATION = "directorsRemuneration";

    private static final String TRANSFER_OF_ASSETS = "transferOfAssets";

    @Test
    @DisplayName("API to statments")
    void apiToStatements() {

        ReportStatementsApi reportStatementsApi = new ReportStatementsApi();
        reportStatementsApi.setCompanyActivitiesAndImpact(COMPANY_ACTIVITIES_AND_IMPACT);
        reportStatementsApi.setConsultationWithStakeholders(CONSULTATION_WITH_STAKEHOLDERS);
        reportStatementsApi.setDirectorsRemuneration(DIRECTORS_REMUNERATION);
        reportStatementsApi.setTransferOfAssets(TRANSFER_OF_ASSETS);

        CicStatementsApi cicStatementsApi = new CicStatementsApi();
        cicStatementsApi.setReportStatements(reportStatementsApi);

        Statements statements = apiToStatementsMapper.apiToStatements(cicStatementsApi);

        assertNotNull(statements);
        assertEquals(COMPANY_ACTIVITIES_AND_IMPACT, statements.getCompanyActivitiesAndImpact());
        assertEquals(CONSULTATION_WITH_STAKEHOLDERS, statements.getConsultationWithStakeholders());
        assertEquals(DIRECTORS_REMUNERATION, statements.getDirectorsRemuneration());
        assertEquals(TRANSFER_OF_ASSETS, statements.getTransferOfAssets());
    }
}
