package uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.mortgagechargedetails;

import com.fasterxml.jackson.annotation.JsonProperty;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.mortgagechargedetails.items.Charge;

import java.util.List;

public class MortgageChargeDetails {

    @JsonProperty("total_count")
    private Integer totalCount;

    @JsonProperty("satisfied_count")
    private Integer satisfiedCount;

    @JsonProperty("part_satisfied_count")
    private Integer partSatisfiedCount;

    @JsonProperty("outstanding")
    private Integer outstanding;

    @JsonProperty("charges")
    private List<Charge> charges;

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getSatisfiedCount() {
        return satisfiedCount;
    }

    public void setSatisfiedCount(Integer satisfiedCount) {
        this.satisfiedCount = satisfiedCount;
    }

    public Integer getPartSatisfiedCount() {
        return partSatisfiedCount;
    }

    public void setPartSatisfiedCount(Integer partSatisfiedCount) {
        this.partSatisfiedCount = partSatisfiedCount;
    }

    public Integer getOutstanding() {
        return outstanding;
    }

    public void setOutstanding(Integer outstanding) {
        this.outstanding = outstanding;
    }

    public List<Charge> getCharges() {
        return charges;
    }

    public void setCharges(List<Charge> charges) {
        this.charges = charges;
    }
}
