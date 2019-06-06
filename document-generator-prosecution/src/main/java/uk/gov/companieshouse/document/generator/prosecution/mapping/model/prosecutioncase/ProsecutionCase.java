package uk.gov.companieshouse.document.generator.prosecution.mapping.model.prosecutioncase;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.Map;

public class ProsecutionCase {

    @JsonProperty("company_incorporation_number")
    private String companyIncorporationNumber;

    @JsonProperty("company_name")
    private String companyName;
    
    /*@JsonProperty("case_reference_number")
    private String caseReferenceNumber;*/

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

	/*public String getCaseReferenceNumber() {
		return caseReferenceNumber;
	}

	public void setCaseReferenceNumber(String caseReferenceNumber) {
		this.caseReferenceNumber = caseReferenceNumber;
	}*/
}
