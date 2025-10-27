package uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.currentappointments.items;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;

public class IdentityVerificationDetails {

    @JsonProperty("anti_money_laundering_supervisory_bodies")
    private @Valid List<String> antiMoneyLaunderingSupervisoryBodies;

    @JsonProperty("appointment_verification_end_on")
    private String appointmentVerificationEndOn;

    @JsonProperty("appointment_verification_statement_date")
    private String appointmentVerificationStatementDate;

    @JsonProperty("appointment_verification_statement_due_on")
    private String appointmentVerificationStatementDueOn;

    @JsonProperty("appointment_verification_start_on")
    private String appointmentVerificationStartOn;

    @JsonProperty("identity_verified_on")
    private String identityVerifiedOn;

    @JsonProperty("authorised_corporate_service_provider_name")
    private String authorisedCorporateServiceProviderName;

    @JsonProperty("preferred_name")
    private String preferredName;

    public List<String> getAntiMoneyLaunderingSupervisoryBodies() {
        return antiMoneyLaunderingSupervisoryBodies;
    }

    public void setAntiMoneyLaunderingSupervisoryBodies(List<String> antiMoneyLaunderingSupervisoryBodies) {
        this.antiMoneyLaunderingSupervisoryBodies = antiMoneyLaunderingSupervisoryBodies;
    }

    public String getAppointmentVerificationEndOn() {
        return appointmentVerificationEndOn;
    }

    public void setAppointmentVerificationEndOn(String appointmentVerificationEndOn) {
        this.appointmentVerificationEndOn = appointmentVerificationEndOn;
    }

    public String getAppointmentVerificationStatementDate() {
        return appointmentVerificationStatementDate;
    }

    public void setAppointmentVerificationStatementDate(String appointmentVerificationStatementDate) {
        this.appointmentVerificationStatementDate = appointmentVerificationStatementDate;
    }

    public String getAppointmentVerificationStatementDueOn() {
        return appointmentVerificationStatementDueOn;
    }

    public void setAppointmentVerificationStatementDueOn(String appointmentVerificationStatementDueOn) {
        this.appointmentVerificationStatementDueOn = appointmentVerificationStatementDueOn;
    }

    public String getAppointmentVerificationStartOn() {
        return appointmentVerificationStartOn;
    }

    public void setAppointmentVerificationStartOn(String appointmentVerificationStartOn) {
        this.appointmentVerificationStartOn = appointmentVerificationStartOn;
    }

    public String getIdentityVerifiedOn() {
        return identityVerifiedOn;
    }

    public void setIdentityVerifiedOn(String identityVerifiedOn) {
        this.identityVerifiedOn = identityVerifiedOn;
    }

    public String getAuthorisedCorporateServiceProviderName() {
        return authorisedCorporateServiceProviderName;
    }

    public void setAuthorisedCorporateServiceProviderName(String authorisedCorporateServiceProviderName) {
        this.authorisedCorporateServiceProviderName = authorisedCorporateServiceProviderName;
    }

    public String getPreferredName() {
        return preferredName;
    }

    public void setPreferredName(String preferredName) {
        this.preferredName = preferredName;
    }
}