package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.insolvency;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.web.context.annotation.RequestScope;
import uk.gov.companieshouse.api.model.insolvency.InsolvencyApi;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.insolvency.Insolvency;

@RequestScope
@Mapper(componentModel = "spring", uses = {ApiToCaseMapper.class})
public abstract class ApiToInsolvencyMapper {

    public abstract Insolvency apiToInsolvencyMapper(InsolvencyApi insolvencyApi);

    @AfterMapping
    protected void calculateTotalNumberOfCases(InsolvencyApi insolvencyApi,
        @MappingTarget Insolvency insolvency) {

        if(insolvencyApi.getCases() != null) {
            insolvency.setTotalInsolvencyCases(insolvencyApi.getCases().size());
        }
    }
}
