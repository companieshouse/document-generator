package uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.statements.items;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class Statement {

    @JsonProperty("statement")
    private String statement;

    @JsonProperty("notified_on")
    private String notifiedOn;

    @JsonProperty("ceased_on")
    private String ceasedOn;

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public String getNotifiedOn() {
        return notifiedOn;
    }

    public void setNotifiedOn(String notifiedOn) {
        this.notifiedOn = notifiedOn;
    }

    public String getCeasedOn() {
        return ceasedOn;
    }

    public void setCeasedOn(String ceasedOn) {
        this.ceasedOn = ceasedOn;
    }
}
