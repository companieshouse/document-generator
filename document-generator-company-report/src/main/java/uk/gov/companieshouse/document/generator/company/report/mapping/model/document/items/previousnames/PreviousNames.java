package uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.previousnames;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import java.time.LocalDate;

@JsonInclude(Include.NON_NULL)
public class PreviousNames {

    @JsonProperty("date_of_change")
    private String dateOfChange;

    @JsonProperty("previous_name")
    private String previousName;

    public String getDateOfChange() {
        return dateOfChange;
    }

    public void setDateOfChange(String dateOfChange) {
        this.dateOfChange = dateOfChange;
    }

    public String getPreviousName() {
        return previousName;
    }

    public void setPreviousName(String previousName) {
        this.previousName = previousName;
    }
}
