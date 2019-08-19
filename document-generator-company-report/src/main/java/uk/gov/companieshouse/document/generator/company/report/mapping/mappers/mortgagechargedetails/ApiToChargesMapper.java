package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.mortgagechargedetails;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.web.context.annotation.RequestScope;
import uk.gov.companieshouse.api.model.charges.ChargeApi;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.mortgagechargedetails.items.Charge;

import java.util.List;

@RequestScope
@Mapper(componentModel = "spring", uses = {ApiToPersonsEntitledMapper.class})
public abstract class ApiToChargesMapper {

    @Mappings({
        @Mapping(source = "acquiredOn", target = "acquisitionDate"),
        @Mapping(source = "assetsCeasedReleased", target = "assetsCeased"),
        @Mapping(source = "securedDetails.description", target = "securedDetailsDescription"),
        @Mapping(source = "particulars.type", target = "type"),
        @Mapping(source = "particulars.description", target = "particularsDescription"),
        @Mapping(source = "particulars.chargorActingAsBareTrustee", target = "chargorActingAsBareTrustee"),
        @Mapping(source = "particulars.containsFixedCharge", target = "containsFixedCharge"),
        @Mapping(source = "particulars.containsFloatingCharge", target = "containsFloatingCharge"),
        @Mapping(source = "particulars.containsNegativeCharge", target = "containsNegativePledge"),
        @Mapping(source = "particulars.floatingChargeCoversAll", target = "floatingChargeCoversAll"),
    })
    public abstract Charge apiToCharge(ChargeApi chargeApi);

    public abstract List<Charge> apiToCharge(List<ChargeApi> chargeApi);
}
