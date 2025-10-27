package uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.insolvency;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.insolvency.items.InsolvencyCase;

public class Insolvency {

    @JsonProperty("total_insolvency_cases")
    private Integer totalInsolvencyCases;

    @JsonProperty("cases")
    private List<InsolvencyCase> cases;

    public Integer getTotalInsolvencyCases() {
        return totalInsolvencyCases;
    }

    public void setTotalInsolvencyCases(Integer totalInsolvencyCases) {
        this.totalInsolvencyCases = totalInsolvencyCases;
    }

    public List<InsolvencyCase> getCases() {
        return cases;
    }

    public void setCases(
        List<InsolvencyCase> cases) {
        this.cases = cases;
    }
}
