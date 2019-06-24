package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.fixedassetsinvestments;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import java.util.Objects;

@JsonInclude(Include.NON_NULL)
public class FixedAssetsInvestments {

    @JsonProperty("details")
    private String details;

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (! (o instanceof FixedAssetsInvestments))
            return false;
        FixedAssetsInvestments debtors = (FixedAssetsInvestments) o;
        return Objects.equals(getDetails(), debtors.getDetails());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDetails());
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
