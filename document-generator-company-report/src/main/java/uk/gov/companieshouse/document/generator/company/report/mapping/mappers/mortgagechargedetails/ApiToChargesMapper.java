package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.mortgagechargedetails;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.web.context.annotation.RequestScope;
import uk.gov.companieshouse.api.model.charges.ChargeApi;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.mortgagechargedetails.items.Charge;

import java.time.format.DateTimeFormatter;
import java.util.List;

@RequestScope
@Mapper(componentModel = "spring")
public abstract class ApiToChargesMapper {

    private static final String D_MMMM_UUUU = "d MMMM uuuu";

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

        if(chargeApi != null) {
            charge.setCreatedDate(chargeApi.getCreatedOn().format(DateTimeFormatter.ofPattern(D_MMMM_UUUU)));
            charge.setDelivered(chargeApi.getDeliveredOn().format(DateTimeFormatter.ofPattern(D_MMMM_UUUU)));

            if (chargeApi.getSatisfiedOn() != null) {
                charge.setSatisfiedOn(chargeApi.getSatisfiedOn().format(DateTimeFormatter.ofPattern(D_MMMM_UUUU)));
            }
        }
    }
}
