package uk.gov.companieshouse.document.generator.prosecution.mapping.model.prosecutioncase;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import uk.gov.companieshouse.api.model.prosecution.prosecutioncase.ProsecutionCaseStatusApi;

public class ProsecutionCase {

    @JsonProperty("CompanyIncorporationNumber")
    private String companyIncorporationNumber;

    @JsonProperty("CompanyName")
    private String companyName;

    @JsonIgnoreProperties("status")
    private ProsecutionCaseStatusApi status;

    /*
     * @JsonProperty("case_reference_number") private String caseReferenceNumber;
     */

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

    /*
     * public String getCaseReferenceNumber() { return caseReferenceNumber; }
     * 
     * public void setCaseReferenceNumber(String caseReferenceNumber) { this.caseReferenceNumber =
     * caseReferenceNumber; }
     */

    public ProsecutionCaseStatusApi getStatus() {
        return status;
    }

    public void setStatus(ProsecutionCaseStatusApi status) {
        this.status = status;
    }
}
