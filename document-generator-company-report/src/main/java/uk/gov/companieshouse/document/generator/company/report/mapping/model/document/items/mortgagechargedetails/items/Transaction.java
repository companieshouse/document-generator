package uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.mortgagechargedetails.items;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

import java.time.LocalDate;

public class Transaction {

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonProperty("delivered_on")
    private LocalDate deliveredOn;

    @JsonProperty("filing_type")
    private String filingType;

    public LocalDate getDeliveredOn() {
        return deliveredOn;
    }

    public void setDeliveredOn(LocalDate deliveredOn) {
        this.deliveredOn = deliveredOn;
    }

    public String getFilingType() {
        return filingType;
    }

    public void setFilingType(String filingType) {
        this.filingType = filingType;
    }
}
