package uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.mortgagechargedetails.items;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

import java.time.LocalDate;
import java.util.List;

public class Charge {

    @JsonProperty("description")
    private String description;

    @JsonProperty("charge_code")
    private String chargeCode;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonProperty("created_date")
    private LocalDate createdDate;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonProperty("delivered")
    private LocalDate delivered;

    @JsonProperty("status")
    private String status;

    @JsonProperty("transaction")
    private String transaction;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonProperty("acquisition_date")
    private LocalDate acquisitionDate;

    @JsonProperty("assets_ceased")
    private String assetsCeased;

    @JsonProperty("persons_entitled")
    private List<PersonEntitled> personsEntitled;

    @JsonProperty("more_than_four_persons_entitled")
    private Boolean moreThanFourPersonsEntitled;

    @JsonProperty("secured_details_description")
    private String securedDetailsDescription;

    @JsonProperty("secured_details_type")
    private String securedDetailsType;

    @JsonProperty("particulars_type")
    private String type;

    @JsonProperty("particulars_description")
    private String particularsDescription;

    @JsonProperty("chargor_acting_as_bare_trustee")
    private Boolean chargorActingAsBareTrustee;

    @JsonProperty("contains_fixed_charge")
    private Boolean containsFixedCharge;

    @JsonProperty("contains_floating_charge")
    private Boolean containsFloatingCharge;

    @JsonProperty("contains_negative_pledge")
    private Boolean containsNegativePledge;

    @JsonProperty("floating_charge_covers_all")
    private Boolean floatingChargeCoversAll;
}
