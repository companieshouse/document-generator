package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.insolvency;


import org.mapstruct.Mapper;
import org.springframework.web.context.annotation.RequestScope;
import uk.gov.companieshouse.api.model.insolvency.PractitionerApi;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.insolvency.items.Practitioner;

import java.util.List;

@RequestScope
@Mapper(componentModel = "spring")
public interface ApiToPractitionerMapper {

    Practitioner apiToPractitionerMapper(PractitionerApi practitionerApi);

    List<Practitioner> apiToPractitionerMapper(List<PractitionerApi> practitionerApi);
}
