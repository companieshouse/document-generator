package uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.mortgagechargedetails;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.mortgagechargedetails.items.Charge;

public class MortgageChargeDetails {

    @JsonProperty("total_count")
    private Long totalCount;

    @JsonProperty("satisfied_count")
    private Long satisfiedCount;

    @JsonProperty("part_satisfied_count")
    private Long partSatisfiedCount;

    @JsonProperty("outstanding")
    private Long outstanding;

    @JsonProperty("charges")
    private List<Charge> items;

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public Long getSatisfiedCount() {
        return satisfiedCount;
    }

    public void setSatisfiedCount(Long satisfiedCount) {
        this.satisfiedCount = satisfiedCount;
    }

    public Long getPartSatisfiedCount() {
        return partSatisfiedCount;
    }

    public void setPartSatisfiedCount(Long partSatisfiedCount) {
        this.partSatisfiedCount = partSatisfiedCount;
    }

    public Long getOutstanding() {
        return outstanding;
    }

    public void setOutstanding(Long outstanding) {
        this.outstanding = outstanding;
    }

    public List<Charge> getItems() {
        return items;
    }

    public void setItems(
        List<Charge> items) {
        this.items = items;
    }
}
