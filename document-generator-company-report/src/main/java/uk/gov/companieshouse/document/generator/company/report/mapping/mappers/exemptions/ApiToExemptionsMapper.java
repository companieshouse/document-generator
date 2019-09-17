package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.exemptions;

import org.mapstruct.Mapper;
import org.springframework.web.context.annotation.RequestScope;
import uk.gov.companieshouse.api.model.exemptions.ExemptionsApi;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.exemptions.Exemptions;

@RequestScope
@Mapper(componentModel = "spring", uses = {ApiToExemption.class})
public interface ApiToExemptionsMapper {

    Exemptions apiToExemptionsMapper(ExemptionsApi exemptionsApi);
}
