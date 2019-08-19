package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.mortgagechargedetails;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.web.context.annotation.RequestScope;
import uk.gov.companieshouse.api.model.charges.ChargesApi;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.mortgagechargedetails.MortgageChargeDetails;

@RequestScope
@Mapper(componentModel = "spring", uses = {ApiToChargesMapper.class})
public interface ApiToMortgageChargeDetailsMapper {

    @Mappings({
        @Mapping(source = "items", target = "charges")
    })
    MortgageChargeDetails apiToMortgageChargeDetails(ChargesApi chargesApi);
}
