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
            @Mapping(source = "prosecutionCase.status", target ="status"),
            @Mapping(source = "prosecutionCase.companyIncorporationNumber", target = "companyIncorporationNumber"),
            @Mapping(source = "prosecutionCase.companyName", target = "companyName"),
            @Mapping(source = "prosecutionCase.complianceCaseId", target = "complianceCaseId"),
            @Mapping(source = "prosecutionCase.complianceUserId", target = "complianceUserId"),
            @Mapping(source = "prosecutionCase.submittedAt", target = "submittedAt"),
            @Mapping(source = "prosecutionCase.links", target = "links")
    })
    ProsecutionCase apiToProsecutionCase(ProsecutionCaseApi prosecutionCase);
}
