package uk.gov.companieshouse.document.generator.prosecution.mapping.model.defendant;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Defendant {

    @JsonProperty("address")
    private Address address;

    @JsonProperty("name")
    private String name;
    
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
