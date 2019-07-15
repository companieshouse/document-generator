package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.pscs;


import java.util.List;
import org.mapstruct.Mapper;
import org.springframework.web.context.annotation.RequestScope;
import uk.gov.companieshouse.api.model.psc.PscApi;
import uk.gov.companieshouse.document.generator.company.report.exception.MapperException;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.pscs.items.Psc;

@RequestScope
@Mapper(componentModel = "spring")
public abstract class ApiToPscMapper {

    public abstract Psc apiToPsc(PscApi pscApi) throws MapperException;
    public abstract List<Psc> apiToPsc(List<PscApi> pscApi) throws MapperException;

}
