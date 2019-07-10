package uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.currentappointments.items;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Identification {

    @JsonProperty("identification_type")
    private String identificationType;

    @JsonProperty("legal_authority")
    private String legalAuthority;

    @JsonProperty("legal_form")
    private String legalForm;

    @JsonProperty("place_registration")
    private String placeRegistration;

    @JsonProperty("place_registered")
    private String placeRegistered;

    @JsonProperty("registration_number")
    private String registrationNumber;

    public String getIdentificationType() {
        return identificationType;
    }

    public void setIdentificationType(String identificationType) {
        this.identificationType = identificationType;
    }

    public String getLegalAuthority() {
        return legalAuthority;
    }

    public void setLegalAuthority(String legalAuthority) {
        this.legalAuthority = legalAuthority;
    }

    public String getLegalForm() {
        return legalForm;
    }

    public void setLegalForm(String legalForm) {
        this.legalForm = legalForm;
    }

    public String getPlaceRegistration() {
        return placeRegistration;
    }

    public void setPlaceRegistration(String placeRegistration) {
        this.placeRegistration = placeRegistration;
    }

    public String getPlaceRegistered() {
        return placeRegistered;
    }

    public void setPlaceRegistered(String placeRegistered) {
        this.placeRegistered = placeRegistered;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }
}
