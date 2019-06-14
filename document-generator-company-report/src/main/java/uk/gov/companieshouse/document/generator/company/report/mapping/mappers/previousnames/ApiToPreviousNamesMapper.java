package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.previousnames;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.web.context.annotation.RequestScope;
import uk.gov.companieshouse.api.model.company.PreviousCompanyNamesApi;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.previousnames.PreviousNames;

@RequestScope
@Mapper(componentModel = "spring")
public interface ApiToPreviousNamesMapper {

    @Mappings({
            @Mapping(source = "ceasedOn", target = "dateOfChange"),
            @Mapping(source = "name", target = "previousName")
    })
    PreviousNames apiToPreviousNameMapper(PreviousCompanyNamesApi previousCompanyNamesApi);

    List<PreviousNames> apiToPreviousNamesMapper(List<PreviousCompanyNamesApi> previousNames);
}