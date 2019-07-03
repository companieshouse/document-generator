package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.currentappointments;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.web.context.annotation.RequestScope;
import uk.gov.companieshouse.api.model.officers.OfficersApi;
import uk.gov.companieshouse.document.generator.company.report.exception.MapperException;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.currentappointments.CurrentAppointments;


@RequestScope
@Mapper(componentModel = "spring", uses = {ApiToCurrentOfficer.class})
public interface ApiToCurrentAppointmentsMapper {

    @Mappings({
            @Mapping(source = "activeCount", target = "numberOfCurrentAppointments"),
            @Mapping(source = "totalResults", target = "totalResults")
    })
    CurrentAppointments apiToCurrentAppointmentsMapper(OfficersApi officerApi) throws MapperException;
}
