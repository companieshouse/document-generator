package uk.gov.companieshouse.document.generator.accounts.mapping.cic.model;

import uk.gov.companieshouse.api.model.accounts.cic.approval.CicApprovalApi;
import uk.gov.companieshouse.api.model.accounts.cic.statements.CicStatementsApi;

public class CicReportApiData {

    private CicStatementsApi cicStatements;

    private CicApprovalApi cicApproval;

    public CicStatementsApi getCicStatements() {
        return cicStatements;
    }

    public void setCicStatements(
            CicStatementsApi cicStatements) {
        this.cicStatements = cicStatements;
    }

    public CicApprovalApi getCicApproval() {
        return cicApproval;
    }

    public void setCicApproval(
            CicApprovalApi cicApproval) {
        this.cicApproval = cicApproval;
    }
}
