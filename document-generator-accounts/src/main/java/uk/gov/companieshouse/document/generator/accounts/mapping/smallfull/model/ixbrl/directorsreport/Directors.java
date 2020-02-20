package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.directorsreport;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Directors {

    @JsonProperty("directors")
    private List<Director> directors = new ArrayList<>();

    @JsonProperty("appointment_date_formatted")
    private String appointmentDate;

    @JsonProperty("resignation_date_formatted")
    private String resignationDate;

    public List<Director> getDirectors() {
        return directors;
    }

    public void setDirectors(List<Director> directors) {
        this.directors = directors;
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
                "directors=" + directors +
                ", appointmentDate='" + appointmentDate + '\'' +
                ", resignationDate='" + resignationDate + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Directors directors = (Directors) o;
        return Objects.equals(directors, directors.directors) &&
                Objects.equals(appointmentDate, directors.appointmentDate) &&
                Objects.equals(resignationDate, directors.resignationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(directors, appointmentDate, resignationDate);
    }
}
