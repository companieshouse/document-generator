package uk.gov.companieshouse.document.generator.prosecution.mapping.mappers;

import org.apache.commons.lang.StringUtils;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.web.context.annotation.RequestScope;
import uk.gov.companieshouse.api.model.prosecution.defendant.DefendantApi;
import uk.gov.companieshouse.document.generator.prosecution.mapping.model.defendant.Defendant;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequestScope
@Mapper(componentModel = "spring")
public interface ApiToDefendantMapper {

    @BeforeMapping
    default void beforeMapping(@MappingTarget Defendant defendant, DefendantApi defendantApi) {
        if(defendantApi.getIsCorporateAppointment()) {
            defendant.setName(defendantApi.getCompanyOfficerDetailsApi().getCompanyName());
        } else {
            String title = defendantApi.getPersonOfficerDetailsApi().getTitle();
            String forename = defendantApi.getPersonOfficerDetailsApi().getForename();
            String middleName = defendantApi.getPersonOfficerDetailsApi().getMiddleName();
            String surname = defendantApi.getPersonOfficerDetailsApi().getSurname();

            String name = Stream.of(title, forename, middleName, surname)
                    .filter(StringUtils::isNotBlank)
                    .map(String::trim)
                    .collect(Collectors.joining(" "));

            defendant.setName(name.trim());
        }
    }

    @Mappings({
            @Mapping(source = "defendant.addressApi", target = "address"),
            @Mapping(target = "name", ignore = true)
    }) Defendant apiToDefendant(DefendantApi defendant);
}
