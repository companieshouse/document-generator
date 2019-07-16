package uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.foreigncompanydetails.items.accountsItems;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class AccountPeriodFrom {

    @JsonProperty("day")
    private String day;

    @JsonProperty("month")
    private String month;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
