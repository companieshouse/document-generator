package uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.recentfilinghistory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.recentfilinghistory.items.Annotations;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.recentfilinghistory.items.AssociatedFilings;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.recentfilinghistory.items.Resolutions;

import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RecentFilingHistory {

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonProperty("date")
    private String date;

    @JsonProperty("form")
    private String form;

    @JsonProperty("description")
    private String description;

    @JsonProperty("resolutions")
    private List<Resolutions> resolutions;

    @JsonProperty("annotations")
    private List<Annotations> annotations;

    @JsonProperty("associated_filings")
    private List<AssociatedFilings> associatedFilings;

    @JsonProperty("description_values")
    private Map<String, Object> descriptionValues;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Resolutions> getResolutions() {
        return resolutions;
    }

    public void setResolutions(List<Resolutions> resolutions) {
        this.resolutions = resolutions;
    }

    public List<Annotations> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(List<Annotations> annotations) {
        this.annotations = annotations;
    }

    public List<AssociatedFilings> getAssociatedFilings() {
        return associatedFilings;
    }

    public void setAssociatedFilings(List<AssociatedFilings> associatedFilings) {
        this.associatedFilings = associatedFilings;
    }

    public Map<String, Object> getDescriptionValues() {
        return descriptionValues;
    }

    public void setDescriptionValues(Map<String, Object> descriptionValues) {
        this.descriptionValues = descriptionValues;
    }
}
