package uk.gov.companieshouse.document.generator.prosecution.mapping.model.defendant;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Address {
    @JsonProperty("house_name_number")
    private String houseNameNumber;

    @JsonProperty("street")
    private String street;

    @JsonProperty("area")
    private String area;

    @JsonProperty("town")
    private String postTown;

    @JsonProperty("region")
    private String region;

    @JsonProperty("country")
    private String country;

    @JsonProperty("postcode")
    private String postCode;

    public Address() {
    }

    public Address(String houseNameNumber, String street, String area, String postTown, String region, String country, String postCode) {
        this.houseNameNumber = houseNameNumber;
        this.street = street;
        this.area = area;
        this.postTown = postTown;
        this.region = region;
        this.country = country;
        this.postCode = postCode;
    }

    public String getHouseNameNumber() {
        return houseNameNumber;
    }

    public void setHouseNameNumber(String houseNamenumber) {
        this.houseNameNumber = houseNamenumber;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPostTown() {
        return postTown;
    }

    public void setPostTown(String postTown) {
        this.postTown = postTown;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }
}
