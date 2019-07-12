package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.foreigncompanydetails;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.web.context.annotation.RequestScope;
import uk.gov.companieshouse.api.model.company.foreigncompany.ForeignCompanyDetailsApi;
import uk.gov.companieshouse.document.generator.company.report.exception.MapperException;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.foreigncompanydetails.ForeignCompanyDetails;

@RequestScope
@Mapper(componentModel = "spring")
public abstract class ApiToForeignCompanyDetailsMapper {

    @Mappings({
        @Mapping(source = "originatingRegistry.country", target = "country"),
        @Mapping(source = "originatingRegistry.name", target = "name")
    })
    public abstract ForeignCompanyDetails apiToForeignCompanyDetails(ForeignCompanyDetailsApi foreignCompanyDetailsApi) throws MapperException;

}
