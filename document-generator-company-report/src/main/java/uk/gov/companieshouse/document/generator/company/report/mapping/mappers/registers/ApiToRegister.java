package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.registers;

import org.mapstruct.Mapper;
import org.springframework.web.context.annotation.RequestScope;
import uk.gov.companieshouse.api.model.registers.RegisterApi;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.registers.items.Register;

@RequestScope
@Mapper(componentModel = "spring", uses = {ApiToRegisterItems.class})
public interface ApiToRegister {

     Register apiToRegister(RegisterApi registerApi);
}
