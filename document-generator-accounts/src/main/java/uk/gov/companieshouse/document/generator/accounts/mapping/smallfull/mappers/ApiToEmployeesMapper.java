package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import uk.gov.companieshouse.api.model.accounts.smallfull.employees.CurrentPeriod;
import uk.gov.companieshouse.api.model.accounts.smallfull.employees.PreviousPeriod;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.employees.Employees;

@Mapper
public interface ApiToEmployeesMapper {

    ApiToEmployeesMapper INSTANCE = Mappers.getMapper(ApiToEmployeesMapper.class);

    @Mappings({
            @Mapping(source = "employeesCurrentPeriod.averageNumberOfEmployees",
                    target = "averageNumberOfEmployees.currentAmount"),
            @Mapping(source = "employeesPreviousPeriod.averageNumberOfEmployees",
                    target = "averageNumberOfEmployees.previousAmount"),

            @Mapping(source = "employeesCurrentPeriod.details", target = "details"),
    })
    Employees apiToEmployees(CurrentPeriod employeesCurrentPeriod,
            PreviousPeriod employeesPreviousPeriod);
}
