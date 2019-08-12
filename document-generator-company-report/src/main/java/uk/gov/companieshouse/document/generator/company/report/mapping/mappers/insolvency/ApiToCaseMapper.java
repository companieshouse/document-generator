package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.insolvency;

import org.mapstruct.Mapper;
import org.springframework.web.context.annotation.RequestScope;
import uk.gov.companieshouse.api.model.insolvency.CaseApi;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.insolvency.items.InsolvencyCase;

import java.util.List;

@RequestScope
@Mapper(componentModel = "spring", uses = {ApiToPractitionerMapper.class})
public interface ApiToCaseMapper {

    InsolvencyCase apiToCaseMapper(CaseApi caseApi);

    List<InsolvencyCase> apiToCaseMapper(List<CaseApi> caseApis);
}
