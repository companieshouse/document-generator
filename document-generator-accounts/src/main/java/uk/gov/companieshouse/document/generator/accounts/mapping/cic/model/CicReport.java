package uk.gov.companieshouse.document.generator.accounts.mapping.cic.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class CicReport {

    @JsonProperty("statements")
    private Statements statements;

    @JsonProperty("approval")
    private Approval approval;

    public Statements getStatements() {
        return statements;
    }

    public void setStatements(
            Statements statements) {
        this.statements = statements;
    }

    public Approval getApproval() {
        return approval;
    }

    public void setApproval(
            Approval approval) {
        this.approval = approval;
    }
}
