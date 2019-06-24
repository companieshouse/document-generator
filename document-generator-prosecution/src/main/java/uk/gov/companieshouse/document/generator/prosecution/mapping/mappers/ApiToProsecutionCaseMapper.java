package uk.gov.companieshouse.document.generator.prosecution.mapping.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.web.context.annotation.RequestScope;
import uk.gov.companieshouse.api.model.prosecution.prosecutioncase.ProsecutionCaseApi;
import uk.gov.companieshouse.document.generator.prosecution.mapping.model.prosecutioncase.ProsecutionCase;

@RequestScope
@Mapper(componentModel = "spring")
public interface ApiToProsecutionCaseMapper {

    @Mappings({
            @Mapping(source = "prosecutionCase.companyIncorporationNumber", target = "companyIncorporationNumber"),
            @Mapping(source = "prosecutionCase.companyName", target = "companyName"),
    })
    ProsecutionCase apiToProsecutionCase(ProsecutionCaseApi prosecutionCase);
}
