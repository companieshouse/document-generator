package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.pscs;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.web.context.annotation.RequestScope;
import uk.gov.companieshouse.api.model.psc.PscsApi;
import uk.gov.companieshouse.document.generator.company.report.exception.MapperException;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.pscs.Pscs;

@RequestScope
@Mapper(componentModel = "spring", uses = {ApiToPscMapper.class})
public interface ApiToPscsMapper {

    Pscs apiToPscsMapper(PscsApi pscsApi) throws MapperException;

}
