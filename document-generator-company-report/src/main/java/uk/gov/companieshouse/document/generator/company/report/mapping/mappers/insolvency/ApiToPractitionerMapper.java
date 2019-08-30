package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.insolvency;


import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.web.context.annotation.RequestScope;
import uk.gov.companieshouse.api.model.insolvency.PractitionerApi;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.insolvency.items.Practitioner;

import java.time.format.DateTimeFormatter;
import java.util.List;

@RequestScope
@Mapper(componentModel = "spring")
public abstract class ApiToPractitionerMapper {

    private static final String D_MMMM_UUUU = "d MMMM uuuu";

    @Mappings({
        @Mapping(target = "appointedOn", ignore = true),
        @Mapping(target = "ceasedToActOn", ignore = true)
    })
    public abstract Practitioner apiToPractitionerMapper(PractitionerApi practitionerApi);

    public abstract List<Practitioner> apiToPractitionerMapper(List<PractitionerApi> practitionerApi);


    @AfterMapping
    protected void formatDates(PractitionerApi practitionerApi, @MappingTarget Practitioner practitioner) {

        if(practitionerApi != null && practitionerApi.getAppointedOn() != null) {
            practitioner.setAppointedOn(practitionerApi.getAppointedOn().format(getFormatter()));
        }

        if(practitionerApi != null && practitionerApi.getCeasedToActOn() != null) {
            practitioner.setCeasedToActOn(practitionerApi.getCeasedToActOn().format(getFormatter()));
        }
    }

    private DateTimeFormatter getFormatter() {
        return DateTimeFormatter.ofPattern(D_MMMM_UUUU);
    }
}
