package uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.pscs;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.pscs.items.Psc;

public class Pscs {

    @JsonProperty("active_count")
    private Long activeCount;

    @JsonProperty("ceased_count")
    private Long ceasedCount;

    @JsonProperty("items")
    private List<Psc> items;

    @JsonProperty("kind")
    private String kind;

    public Long getActiveCount() {
        return activeCount;
    }

    public void setActiveCount(Long activeCount) {
        this.activeCount = activeCount;
    }

    public Long getCeasedCount() {
        return ceasedCount;
    }

    public void setCeasedCount(Long ceasedCount) {
        this.ceasedCount = ceasedCount;
    }

    public List<Psc> getItems() {
        return items;
    }

    public void setItems(List<Psc> items) {
        this.items = items;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }
}
