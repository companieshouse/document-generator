package uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.currentappointments;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.currentappointments.items.CurrentOfficer;

public class CurrentAppointments {

    @JsonProperty("active_count")
    private Long numberOfCurrentAppointments;

    @JsonProperty("current_officers")
    private List<CurrentOfficer> items;

    @JsonProperty("total_results")
    private Integer totalResults;

    @JsonProperty("resigned_count")
    private Integer resignedCount;

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

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public Integer getResignedCount() {
        return resignedCount;
    }

    public void setResignedCount(Integer resignedCount) {
        this.resignedCount = resignedCount;
    }
}
