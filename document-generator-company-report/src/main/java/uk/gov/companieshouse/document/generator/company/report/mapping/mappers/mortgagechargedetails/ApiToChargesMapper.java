package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.mortgagechargedetails;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.annotation.RequestScope;
import uk.gov.companieshouse.api.model.charges.ChargeApi;
import uk.gov.companieshouse.document.generator.common.descriptions.RetrieveApiEnumerationDescription;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.mortgagechargedetails.items.Charge;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequestScope
@Mapper(componentModel = "spring", uses = {ApiToParticularsMapper.class, ApiToPersonsEntitledMapper.class, ApiToSecuredDetailsMapper.class})
public abstract class ApiToChargesMapper {

    @Autowired
    private RetrieveApiEnumerationDescription retrieveApiEnumerationDescription;

    private static final String MORTGAGE_DESCRIPTIONS = "mortgage_descriptions";
    private static final String ASSETS_CEASED_RELEASED = "assets-ceased-released";

    private static final String D_MMMM_UUUU = "d MMMM uuuu";

    @Mappings({
        @Mapping(source = "createdOn", target = "created", dateFormat = D_MMMM_UUUU),
        @Mapping(source = "deliveredOn", target = "delivered", dateFormat = D_MMMM_UUUU),
        @Mapping(source = "satisfiedOn", target = "satisfiedOn", dateFormat = D_MMMM_UUUU),
        @Mapping(target = "assetsCeasedReleased", ignore = true)
    })
    public abstract Charge apiToCharge(ChargeApi chargeApi);

    public abstract List<Charge> apiToCharge(List<ChargeApi> chargeApi);

    @AfterMapping
    protected void setChargeDescription(ChargeApi chargeApi, @MappingTarget Charge charge) {

        if(chargeApi != null) {
            if(chargeApi.getAcquiredOn() == null) {
                charge.setChargeDescription(Optional.ofNullable(
                    chargeApi.getClassification().getDescription()).orElse("Charge"));
            } else {
                charge.setChargeDescription("Charge code " + Optional.ofNullable(
                    chargeApi.getChargeCode()).orElse(""));
            }
        }
    }

    @AfterMapping
    protected void setAssetsCeasedReleased(ChargeApi chargeApi, @MappingTarget Charge charge) {

        if(chargeApi != null && chargeApi.getAssetsCeasedReleased() != null) {
            charge.setAssetsCeasedReleased(retrieveApiEnumerationDescription
                .getApiEnumerationDescription(MORTGAGE_DESCRIPTIONS, ASSETS_CEASED_RELEASED,
                    chargeApi.getAssetsCeasedReleased().getType(),
                    getDebugMap(chargeApi.getAssetsCeasedReleased().getType())));
        }
    }

    private Map<String, String> getDebugMap(String debugString) {

        Map<String, String> debugMap = new HashMap<>();
        debugMap.put("Enumeration mapping :", debugString);

        return debugMap;
    }}
