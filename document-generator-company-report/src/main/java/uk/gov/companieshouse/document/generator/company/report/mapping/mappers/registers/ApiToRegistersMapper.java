package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.registers;

import org.mapstruct.Mapper;
import org.springframework.web.context.annotation.RequestScope;
import uk.gov.companieshouse.api.model.registers.RegistersApi;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.registers.CompanyRegisters;

@RequestScope
@Mapper(componentModel = "spring", uses = {ApiToRegister.class})
public interface ApiToRegistersMapper {

    CompanyRegisters apiToRegistersMapper(RegistersApi registersApi);
}
