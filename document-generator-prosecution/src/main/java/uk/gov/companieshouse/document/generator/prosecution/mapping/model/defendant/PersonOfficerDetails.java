package uk.gov.companieshouse.document.generator.prosecution.mapping.model.defendant;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class PersonOfficerDetails {

    @JsonProperty("title")
    private String title;

    @JsonProperty("forename")
    private String forename;

    @JsonProperty("middle_name")
    private String middleName;

    @JsonProperty("surname")
    private String surname;

    @JsonProperty("date_of_birth")
    private LocalDate dateOfBirth;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

}
