package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.directorsreport;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DirectorsReport that = (DirectorsReport) o;
        return Objects.equals(directorsReportStatements, that.directorsReportStatements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(directorsReportStatements);
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
