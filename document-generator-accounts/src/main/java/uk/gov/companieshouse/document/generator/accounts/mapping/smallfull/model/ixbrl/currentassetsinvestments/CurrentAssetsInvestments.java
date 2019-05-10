package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.currentassetsinvestments;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

@JsonInclude(Include.NON_NULL)
public class CurrentAssetsInvestments {

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
        if (! (o instanceof CurrentAssetsInvestments))
            return false;
        CurrentAssetsInvestments that = (CurrentAssetsInvestments) o;
        return Objects.equals(getDetails(), that.getDetails());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDetails());
    }

    @Override
    public String toString() {
        return "CurrentAssetsInvestments{" +
                "details='" + details + '\'' +
                '}';
    }
}
