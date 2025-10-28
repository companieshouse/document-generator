package uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.mortgagechargedetails.items;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

public class Particulars {

    @JsonProperty("type")
    private String type;

    @JsonProperty("description")
    private String description;

    @JsonProperty("extra_particular_statements")
    private Map<String, String> extraParticularStatements;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, String> getExtraParticularStatements() {
        return extraParticularStatements;
    }

    public void setExtraParticularStatements(
        Map<String, String> extraParticularStatements) {
        this.extraParticularStatements = extraParticularStatements;
    }
}
