package uk.gov.companieshouse.document.generator.prosecution.mapping.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.web.context.annotation.RequestScope;
import uk.gov.companieshouse.api.model.prosecution.defendant.DefendantApi;
import uk.gov.companieshouse.document.generator.prosecution.mapping.model.defendant.Defendant;

@RequestScope
@Mapper(componentModel = "spring")
public interface ApiToDefendantMapper {
    @Mappings({
            @Mapping(source = "defendant.officerId", target = "officerId"),
            @Mapping(source = "defendant.addressApi", target = "address"),
            @Mapping(source = "defendant.personOfficerDetailsApi", target = "personOfficerDetails"),
            @Mapping(source = "defendant.companyOfficerDetailsApi", target = "companyOfficerDetails"),
            @Mapping(source = "defendant.dateAppointedOn", target = "dateAppointedOn"),
            @Mapping(source = "defendant.dateTerminatedOn", target = "dateTerminatedOn"),
            @Mapping(source = "defendant.isCorporateAppointment", target = "isCorporateAppointment")
    })
    Defendant apiToDefendant(DefendantApi defendant);
}

