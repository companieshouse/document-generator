package uk.gov.companieshouse.document.generator.accounts.mapping.cic.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import uk.gov.companieshouse.document.generator.accounts.mapping.cic.model.CicReport;
import uk.gov.companieshouse.document.generator.accounts.mapping.cic.model.CicReportApiData;

@RequestScope
@Component
public class CicReportMapper {

    @Autowired
    private ApiToStatementsMapper apiToStatementsMapper;

    @Autowired
    private ApiToApprovalMapper apiToApprovalMapper;

    public CicReport mapCicReport(CicReportApiData cicReportApiData) {

        CicReport cicReport = new CicReport();

        if (cicReportApiData.getCicStatements() != null) {

            cicReport.setStatements(apiToStatementsMapper.apiToStatements(cicReportApiData.getCicStatements()));
        }

        if (cicReportApiData.getCicApproval() != null) {

            cicReport.setApproval(apiToApprovalMapper.apiToApproval(cicReportApiData.getCicApproval()));
        }

        return cicReport;
    }
}
