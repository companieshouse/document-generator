package uk.gov.companieshouse.document.generator.prosecution.mapping.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.web.context.annotation.RequestScope;
import uk.gov.companieshouse.api.model.prosecution.offence.OffenceApi;
import uk.gov.companieshouse.document.generator.prosecution.mapping.model.offence.Offence;

@RequestScope
@Mapper(componentModel = "spring")
public interface ApiToOffenceMapper {

    @Mappings({
            @Mapping(source = "offence.filingPeriodType", target = "filingPeriodType"),
            @Mapping(source = "offence.status", target = "status"),
            @Mapping(source = "offence.filingPeriodEndsOn", target = "filingPeriodEndsOn"),
            @Mapping(source = "offence.filingDueOn", target = "filingDueOn"),
            @Mapping(source = "offence.filingPeriodId", target = "filingPeriodId")
    })
    Offence apiToOffence(OffenceApi offence);
}
