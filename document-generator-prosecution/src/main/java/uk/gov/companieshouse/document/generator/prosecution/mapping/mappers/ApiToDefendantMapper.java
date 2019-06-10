package uk.gov.companieshouse.document.generator.prosecution.mapping.mappers;

import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.web.context.annotation.RequestScope;
import uk.gov.companieshouse.api.model.prosecution.defendant.DefendantApi;
import uk.gov.companieshouse.document.generator.prosecution.mapping.model.defendant.Defendant;

@RequestScope
@Mapper(componentModel = "spring")
public interface ApiToDefendantMapper {

    @BeforeMapping
    default void beforeMapping(@MappingTarget Defendant defendant, DefendantApi defendantApi) {
        if(defendantApi.getIsCorporateAppointment()) {
            defendant.setName(defendantApi.getCompanyOfficerDetailsApi().getCompanyName());
        } else {
            StringBuilder name = new StringBuilder();
            String title = defendantApi.getPersonOfficerDetailsApi().getTitle();
            String forename = defendantApi.getPersonOfficerDetailsApi().getForename();
            String middleName = defendantApi.getPersonOfficerDetailsApi().getMiddleName();
            String surname = defendantApi.getPersonOfficerDetailsApi().getSurname();

            if(title != null && title.trim().length() > 0) {
                name.append(title).append(" ");
            }
            if(forename != null && forename.trim().length() > 0) {
                name.append(forename).append(" ");
            }
            if(middleName != null && middleName.trim().length() > 0){
                name.append(middleName).append(" ");
            }
            if(surname != null && surname.trim().length() > 0) {
                name.append(surname);
            }
            defendant.setName(name.toString());
        }
    }

    @Mappings({
            @Mapping(source = "defendant.addressApi", target = "address"),
            @Mapping(target = "name", ignore = true)
    }) Defendant apiToDefendant(DefendantApi defendant);
}
