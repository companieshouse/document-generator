package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.directorsreport;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DirectorsReport {

    @JsonProperty("directors_report_statements")
    private DirectorsReportStatements directorsReportStatements;

    public DirectorsReportStatements getDirectorsReportStatements() {
        return directorsReportStatements;
    }

    public void setDirectorsReportStatements(DirectorsReportStatements directorsReportStatements) {
        this.directorsReportStatements = directorsReportStatements;
    }
   }
