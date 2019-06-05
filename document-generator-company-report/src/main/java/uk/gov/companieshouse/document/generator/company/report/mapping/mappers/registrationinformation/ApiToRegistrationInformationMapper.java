package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.registrationinformation;

import java.io.IOException;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.web.context.annotation.RequestScope;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.CompanyReportApiData;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.registrationinformation.RegistrationInformation;

@RequestScope
@Mapper(componentModel = "spring")
@DecoratedWith(ApiToRegistrationInformationMapperDecorator.class)
public interface ApiToRegistrationInformationMapper {

    @Mappings({
            @Mapping(source = "companyProfileApi.companyName", target = "companyName"),
            @Mapping(source = "companyProfileApi.companyNumber", target = "companyNumber"),
            @Mapping(source = "companyProfileApi.dateOfCreation", target = "dateOfIncorporation"),
            @Mapping(source = "companyProfileApi.registeredOfficeAddress.addressLine1", target = "registeredOffice.addressLine1"),
            @Mapping(source = "companyProfileApi.registeredOfficeAddress.addressLine2", target = "registeredOffice.addressLine2"),
            @Mapping(source = "companyProfileApi.registeredOfficeAddress.careOf", target = "registeredOffice.careOf"),
            @Mapping(source = "companyProfileApi.registeredOfficeAddress.country", target = "registeredOffice.country"),
            @Mapping(source = "companyProfileApi.registeredOfficeAddress.locality", target = "registeredOffice.locality"),
            @Mapping(source = "companyProfileApi.registeredOfficeAddress.poBox", target = "registeredOffice.poBox"),
            @Mapping(source = "companyProfileApi.registeredOfficeAddress.postalCode", target = "registeredOffice.postalCode"),
            @Mapping(source = "companyProfileApi.registeredOfficeAddress.premises", target = "registeredOffice.premises"),
            @Mapping(source = "companyProfileApi.registeredOfficeAddress.region", target = "registeredOffice.region")
    })
    RegistrationInformation apiToRegistrationInformation(CompanyReportApiData companyReportApiData) throws IOException;
}