package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.directorsreport;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Directors {

    @JsonProperty("names")
    private List<String> names = new ArrayList<>();

    @JsonProperty("appointment_date_formatted")
    private String appointmentDate;

    @JsonProperty("resignation_date_formatted")
    private String resignationDate;

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getResignationDate() {
        return resignationDate;
    }

    public void setResignationDate(String resignationDate) {
        this.resignationDate = resignationDate;
    }

    @Override
    public String toString() {
        return "Directors{" +
                "names=" + names +
                ", appointmentDate='" + appointmentDate + '\'' +
                ", resignationDate='" + resignationDate + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Directors directors = (Directors) o;
        return Objects.equals(names, directors.names) &&
                Objects.equals(appointmentDate, directors.appointmentDate) &&
                Objects.equals(resignationDate, directors.resignationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(names, appointmentDate, resignationDate);
    }
}
