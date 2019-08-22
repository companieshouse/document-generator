package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.registers;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.web.context.annotation.RequestScope;
import uk.gov.companieshouse.api.model.registers.RegisterApi;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.registers.items.Register;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.registers.items.RegisterItems;

@RequestScope
@Mapper(componentModel = "spring", uses = {ApiToRegisterItems.class})
public abstract class ApiToRegister {

     public abstract Register apiToRegister(RegisterApi registerApi);

     @AfterMapping
     protected void createInformationSentence(RegisterApi registerApi,
                                              @MappingTarget Register register) {

          StringBuilder informationSentence = new StringBuilder();
          RegisterItems firstItem = register.getItems().get(0);

          informationSentence
              .append("Information ")
              .append(firstItem.getRegisterMovedTo().toLowerCase())
              .append(" since ")
              .append(firstItem.getMovedOn());

          register.setInformation(informationSentence.toString());
     }
}
