package uk.gov.companieshouse.document.generator.prosecution.mapping.model.defendant;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Defendant {

    @JsonProperty("address")
    private List<String> address;

    @JsonProperty("name")
    private String name;
    
    public List<String> getAddress() {
        return address;
    }

    public void setAddress(List<String> address) {
        this.address = address;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
