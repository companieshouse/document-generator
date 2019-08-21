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

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestScope
@Mapper(componentModel = "spring")
public abstract class ApiToChargesMapper {

    @Autowired
    private RetrieveApiEnumerationDescription retrieveApiEnumerationDescription;

    private static final String D_MMMM_UUUU = "d MMMM uuuu";
    private static final String MORTGAGE_DESCRIPTIONS = "MORTGAGE_DESCRIPTIONS";

    @Mappings({
        @Mapping(source = "classification.description", target = "description"),
        @Mapping(source = "createdOn", target = "createdDate"),
        @Mapping(source = "deliveredOn", target = "delivered"),
        @Mapping(source = "particulars.type", target = "type"),
        @Mapping(source = "particulars.description", target = "particularsDescription"),
    })
    public abstract Charge apiToCharge(ChargeApi chargeApi);

    public abstract List<Charge> apiToCharge(List<ChargeApi> chargeApi);

    @AfterMapping
    protected void convertDate(ChargeApi chargeApi, @MappingTarget Charge charge) {

        if (chargeApi != null) {
            charge.setCreatedDate(chargeApi.getCreatedOn().format(DateTimeFormatter.ofPattern(D_MMMM_UUUU)));
            charge.setDelivered(chargeApi.getDeliveredOn().format(DateTimeFormatter.ofPattern(D_MMMM_UUUU)));

            if (chargeApi.getSatisfiedOn() != null) {
                charge.setSatisfiedOn(chargeApi.getSatisfiedOn().format(DateTimeFormatter.ofPattern(D_MMMM_UUUU)));
            }
        }
    }

    @AfterMapping
    protected void convertParticularsType(ChargeApi chargeApi, @MappingTarget Charge charge) {

        if (hasParticularsType(chargeApi)) {

            charge.setType(retrieveApiEnumerationDescription.getApiEnumerationDescription(
                    MORTGAGE_DESCRIPTIONS,
                    "particular-description",
                    chargeApi.getParticulars().getType().getType(),
                    getDebugMap(chargeApi.getParticulars().getType().getType())));
        }
    }

    @AfterMapping
    protected void convertStatus(ChargeApi chargeApi, @MappingTarget Charge charge) {

        if (chargeApi != null && chargeApi.getStatus() != null) {

            charge.setStatus(retrieveApiEnumerationDescription.getApiEnumerationDescription(
                MORTGAGE_DESCRIPTIONS,
                "status",
                chargeApi.getStatus(),
                getDebugMap(chargeApi.getStatus())));
        }
    }

    private boolean hasParticularsType(ChargeApi chargeApi) {
        return chargeApi != null && chargeApi.getParticulars() != null
            && chargeApi.getParticulars().getType() != null;
    }

    private Map<String, String> getDebugMap(String debugString) {

        Map<String, String> debugMap = new HashMap<>();
        debugMap.put("Enumeration mapping :", debugString);

        return debugMap;
    }
}
