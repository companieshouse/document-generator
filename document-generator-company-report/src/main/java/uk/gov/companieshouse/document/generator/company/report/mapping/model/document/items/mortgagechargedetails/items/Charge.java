package uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.mortgagechargedetails.items;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

import java.time.LocalDate;
import java.util.List;

public class Charge {

    @JsonProperty("description")
    private String description;

    @JsonProperty("created_date")
    private String createdDate;

    @JsonProperty("delivered")
    private String delivered;

    @JsonProperty("status")
    private String status;

    @JsonProperty("satisfied_on")
    private String satisfiedOn;

    @JsonProperty("particulars_type")
    private String type;

    @JsonProperty("particulars_description")
    private String particularsDescription;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getDelivered() {
        return delivered;
    }

    public void setDelivered(String delivered) {
        this.delivered = delivered;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSatisfiedOn() {
        return satisfiedOn;
    }

    public void setSatisfiedOn(String satisfiedOn) {
        this.satisfiedOn = satisfiedOn;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParticularsDescription() {
        return particularsDescription;
    }

    public void setParticularsDescription(String particularsDescription) {
        this.particularsDescription = particularsDescription;
    }
}
