package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.employees;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.employees.items.AverageNumberOfEmployees;

@JsonInclude(Include.NON_NULL)
public class Employees {

    @JsonProperty("details")
    private String details;

    @JsonProperty("average_number_of_employees")
    private AverageNumberOfEmployees averageNumberOfEmployees;

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public AverageNumberOfEmployees getAverageNumberOfEmployees() {
        return averageNumberOfEmployees;
    }

    public void setAverageNumberOfEmployees(AverageNumberOfEmployees averageNumberOfEmployees) {
        this.averageNumberOfEmployees = averageNumberOfEmployees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (! (o instanceof Employees))
            return false;
        Employees employees = (Employees) o;
        return Objects.equals(getDetails(), employees.getDetails()) &&
                Objects.equals(getAverageNumberOfEmployees(), employees.getAverageNumberOfEmployees());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDetails(), getAverageNumberOfEmployees());
    }

    @Override
    public String toString() {
        return "Employees{" +
                "details='" + details + '\'' +
                ", averageNumberOfEmployees=" + averageNumberOfEmployees +
                '}';
    }
}
