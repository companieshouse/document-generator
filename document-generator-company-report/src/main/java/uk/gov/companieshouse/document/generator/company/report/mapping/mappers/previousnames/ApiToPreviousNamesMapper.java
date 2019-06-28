package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.previousnames;

import java.time.format.DateTimeFormatter;
import java.util.List;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.web.context.annotation.RequestScope;
import uk.gov.companieshouse.api.model.company.PreviousCompanyNamesApi;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.previousnames.PreviousNames;

@RequestScope
@Mapper(componentModel = "spring")
public abstract class ApiToPreviousNamesMapper {

    @Mappings({

            @Mapping(source = "name", target = "previousName")
    })

    public abstract PreviousNames apiToPreviousNameMapper(PreviousCompanyNamesApi previousCompanyNamesApi);

    public abstract List<PreviousNames> apiToPreviousNamesMapper(List<PreviousCompanyNamesApi> previousNames);

    @AfterMapping
    protected void convertDate(PreviousCompanyNamesApi previousCompanyNamesApi,
            @MappingTarget PreviousNames previousNames) {

        if (previousCompanyNamesApi != null) {

            previousNames.setDateOfChange(previousCompanyNamesApi.getCeasedOn().format(DateTimeFormatter.ofPattern("d MMMM uuuu")));
        }
    }
}