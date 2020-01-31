package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.directorsreport;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import uk.gov.companieshouse.api.model.accounts.directorsreport.DirectorApi;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DirectorsReport {

    @JsonProperty("directors_report_statements")
    private DirectorsReportStatements directorsReportStatements;

    @JsonProperty("directors")
    private Directors[] director;

    public Directors[] getDirector() {
        return director;
    }

    @JsonProperty("sorted_directors")
    private List<Directors> sortedDirectors;

    @JsonProperty("secretary")
    private Secretary secretary;

    @JsonProperty("approval")
    private Approval approval;

    public Secretary getSecretary() {
        return secretary;
    }

    public void setSecretary(Secretary secretary) {
        this.secretary = secretary;
    }

    public Approval getApproval() {
        return approval;
    }

    public void setApproval(Approval approval) {
        this.approval = approval;
    }

    public List<Directors> getSortedDirectors() {
        return sortedDirectors;
    }

    public void setSortedDirectors(List<Directors> sortedDirectors) {
        this.sortedDirectors = sortedDirectors;
    }



    public void setDirector(Directors[] director) {
        this.director = director;
    }

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
        return Objects.equals(directorsReportStatements, that.directorsReportStatements) &&
                Arrays.equals(director, that.director) &&
                Objects.equals(sortedDirectors, that.sortedDirectors) &&
                Objects.equals(secretary, that.secretary) &&
                Objects.equals(approval, that.approval);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(directorsReportStatements, sortedDirectors, secretary, approval);
        result = 31 * result + Arrays.hashCode(director);
        return result;
    }

    @Override
    public String toString() {
        return "DirectorsReport{" +
                "directorsReportStatements=" + directorsReportStatements +
                ", director=" + Arrays.toString(director) +
                ", sortedDirectors=" + sortedDirectors +
                ", secretary=" + secretary +
                ", approval=" + approval +
                '}';
    }
}
