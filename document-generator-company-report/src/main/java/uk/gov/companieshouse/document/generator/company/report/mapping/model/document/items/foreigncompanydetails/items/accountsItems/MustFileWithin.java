package uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.foreigncompanydetails.items.accountsItems;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class MustFileWithin {

    @JsonProperty("months")
    private Long months;

    public Long getMonths() {
        return months;
    }

    public void setMonths(Long months) {
        this.months = months;
    }
}
