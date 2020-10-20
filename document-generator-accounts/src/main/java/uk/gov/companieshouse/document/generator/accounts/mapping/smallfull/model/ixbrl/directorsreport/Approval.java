package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.directorsreport;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)

public class Approval {

    @JsonProperty("date")
    private String date;

    @JsonProperty("approval_name")
    private String name;

    @JsonProperty("director_index")
    private int directorIndex;

    @JsonProperty("is_secretary")
    private Boolean isSecretary;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDirectorIndex() {
        return directorIndex;
    }

    public void setDirectorIndex(int directorIndex) {
        this.directorIndex = directorIndex;
    }

    public Boolean getSecretary() {
        return isSecretary;
    }

    public void setSecretary(Boolean secretary) {
        isSecretary = secretary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Approval approval = (Approval) o;
        return Objects.equals(date, approval.date) &&
                Objects.equals(name, approval.name) &&
                Objects.equals(isSecretary, approval.isSecretary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, name, isSecretary);
    }

    @Override
    public String toString() {
        return "Approval{" +
                "date=" + date +
                ", name='" + name + '\'' +
                ", isSecretary=" + isSecretary +
                '}';
    }
}
