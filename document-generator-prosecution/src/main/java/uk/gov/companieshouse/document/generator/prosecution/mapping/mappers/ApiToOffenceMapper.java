package uk.gov.companieshouse.document.generator.prosecution.mapping.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.web.context.annotation.RequestScope;
import uk.gov.companieshouse.api.model.prosecution.offence.OffenceApi;
import uk.gov.companieshouse.document.generator.prosecution.mapping.model.offence.Offence;

import java.util.List;

@RequestScope
@Mapper(componentModel = "spring")
public interface ApiToOffenceMapper {

    @Mappings({
            @Mapping(source = "offence.filingPeriodType", target = "filingPeriodType"),
            @Mapping(source = "offence.filingPeriodEndsOn", target = "filingPeriodEndsOn"),
            @Mapping(source = "offence.filingDueOn", target = "filingDueOn"),
    })
    Offence apiToOffence(OffenceApi offence);
    List<Offence> apiToOffences(OffenceApi[] offences);
}
