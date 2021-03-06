package uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.statements;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.statements.items.Statement;

@JsonInclude(Include.NON_NULL)
public class Statements {

    @JsonProperty("items")
    private List<Statement> items;

    @JsonProperty("active_count")
    private Long activeStatements;

    @JsonProperty("ceased_count")
    private Long ceasedStatements;

    public List<Statement> getItems() {
        return items;
    }

    public void setItems(
        List<Statement> items) {
        this.items = items;
    }

    public Long getActiveStatements() {
        return activeStatements;
    }

    public void setActiveStatements(Long activeStatements) {
        this.activeStatements = activeStatements;
    }

    public Long getCeasedStatements() {
        return ceasedStatements;
    }

    public void setCeasedStatements(Long ceasedStatements) {
        this.ceasedStatements = ceasedStatements;
    }
}
