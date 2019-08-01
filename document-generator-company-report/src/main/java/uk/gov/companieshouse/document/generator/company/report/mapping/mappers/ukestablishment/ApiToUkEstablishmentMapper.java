package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.ukestablishment;

import org.mapstruct.Mapper;
import org.springframework.web.context.annotation.RequestScope;
import uk.gov.companieshouse.api.model.ukestablishments.UkEstablishmentsItemsApi;
import uk.gov.companieshouse.document.generator.company.report.exception.MapperException;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.ukestablishment.UkEstablishment;

import java.util.List;

@RequestScope
@Mapper(componentModel = "spring")
public interface ApiToUkEstablishmentMapper {

    UkEstablishment apiToUkEstablishmentMapper(UkEstablishmentsItemsApi ukEstablishmentsItemsApi) throws MapperException;

    List<UkEstablishment> apiToUkEstablishmentMapper(List<UkEstablishmentsItemsApi> ukEstablishmentsItemsApi) throws MapperException;
}
