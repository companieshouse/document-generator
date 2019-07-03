package uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.currentappointments;

import com.fasterxml.jackson.annotation.JsonProperty;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.currentappointments.items.CurrentOfficer;

import java.util.List;

public class CurrentAppointments {

    @JsonProperty("active_count")
    private Long numberOfCurrentAppointments;

    @JsonProperty("current_officers")
    private List<CurrentOfficer> items;

    @JsonProperty("total_results")
    private int totalResults;

    @JsonProperty("resigned_count")
    private int resignedCount;

    public Long getNumberOfCurrentAppointments() {
        return numberOfCurrentAppointments;
    }

    public void setNumberOfCurrentAppointments(Long numberOfCurrentAppointments) {
        this.numberOfCurrentAppointments = numberOfCurrentAppointments;
    }

    public List<CurrentOfficer> getItems() {
        return items;
    }

    public void setItems(List<CurrentOfficer> items) {
        this.items = items;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getResignedCount() {
        return resignedCount;
    }

    public void setResignedCount(int resignedCount) {
        this.resignedCount = resignedCount;
    }
}
