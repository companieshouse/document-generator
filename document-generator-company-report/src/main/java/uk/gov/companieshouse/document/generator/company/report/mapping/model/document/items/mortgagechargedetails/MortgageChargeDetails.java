package uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.mortgagechargedetails;

import com.fasterxml.jackson.annotation.JsonProperty;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.mortgagechargedetails.items.Charge;

import java.util.List;

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
    private List<Charge> charges;

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

    public List<Charge> getCharges() {
        return charges;
    }

    public void setCharges(List<Charge> charges) {
        this.charges = charges;
    }
}
