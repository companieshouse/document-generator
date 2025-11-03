package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.mortgagechargedetails;

import java.util.List;
import org.mapstruct.Mapper;
import org.springframework.web.context.annotation.RequestScope;
import uk.gov.companieshouse.api.model.charges.PersonsEntitledApi;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.mortgagechargedetails.items.PersonsEntitled;

@RequestScope
@Mapper(componentModel = "spring")
public interface ApiToPersonsEntitledMapper {

    PersonsEntitled apiToPersonsEntitledMapper(PersonsEntitledApi personsEntitledApi);

    List<PersonsEntitled> apiToPersonsEntitledMapper(List<PersonsEntitledApi> personsEntitledApi);
}
