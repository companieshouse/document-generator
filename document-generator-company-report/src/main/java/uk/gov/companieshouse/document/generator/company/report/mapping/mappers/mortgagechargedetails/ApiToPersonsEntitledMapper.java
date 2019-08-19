package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.mortgagechargedetails;

import org.mapstruct.Mapper;
import org.springframework.web.context.annotation.RequestScope;
import uk.gov.companieshouse.api.model.charges.PersonsEntitledApi;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.mortgagechargedetails.items.PersonEntitled;

import java.util.List;

@RequestScope
@Mapper(componentModel = "spring")
public interface ApiToPersonsEntitledMapper {

    PersonEntitled apiToPersonsEntitled(PersonsEntitledApi personsEntitledApi);

    List<PersonEntitled> apiToPersonsEntitled(List<PersonsEntitledApi> personsEntitledApi);
}
