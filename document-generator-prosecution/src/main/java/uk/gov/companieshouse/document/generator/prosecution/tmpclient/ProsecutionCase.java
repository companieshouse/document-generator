package uk.gov.companieshouse.document.generator.prosecution.tmpclient;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.Map;

public class ProsecutionCase {
    @JsonProperty("kind")
    private String kind;

    @JsonProperty("status")
    private ProsecutionCaseStatus status;

    @JsonProperty("company_incorporation_number")
    private String companyIncorporationNumber;

    @JsonProperty("company_name")
    private String companyName;

    @JsonProperty("compliance_case_id")
    private String complianceCaseId;

    @JsonProperty("compliance_user_id")
    private String complianceUserId;

    @JsonProperty("submitted_at")
    private LocalDateTime submittedAt;

    @JsonProperty("links")
    private Map<String, String> links;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public ProsecutionCaseStatus getStatus() {
        return status;
    }

    public void setStatus(ProsecutionCaseStatus status) {
        this.status = status;
    }

    public String getCompanyIncorporationNumber() {
        return companyIncorporationNumber;
    }

    public void setCompanyIncorporationNumber(String companyIncorporationNumber) {
        this.companyIncorporationNumber = companyIncorporationNumber;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getComplianceCaseId() {
        return complianceCaseId;
    }

    public void setComplianceCaseId(String complianceCaseId) {
        this.complianceCaseId = complianceCaseId;
    }

    public String getComplianceUserId() {
        return complianceUserId;
    }

    public void setComplianceUserId(String complianceUserId) {
        this.complianceUserId = complianceUserId;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }

    public Map<String, String> getLinks() {
        return links;
    }

    public void setLinks(Map<String, String> links) {
        this.links = links;
    }
}
