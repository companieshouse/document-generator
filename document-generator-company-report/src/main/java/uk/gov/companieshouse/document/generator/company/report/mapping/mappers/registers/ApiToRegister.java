package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.registers;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.web.context.annotation.RequestScope;
import uk.gov.companieshouse.api.model.registers.RegisterApi;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.registers.items.FormattedDates;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.registers.items.Register;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.registers.items.RegisterItems;

import java.util.ArrayList;
import java.util.List;

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

     @AfterMapping
     protected void createDateList(RegisterApi registerApi,
                                   @MappingTarget Register register) {
         String previousDate = null;

         List<FormattedDates> dates = new ArrayList<>();

         for (RegisterItems registerItem : register.getItems()) {

             FormattedDates formattedDates = new FormattedDates();

             if (previousDate == null || previousDate.isEmpty()) {
                 formattedDates.setFormattedDate(registerItem.getMovedOn());

                 dates.add(formattedDates);
                 previousDate = registerItem.getMovedOn();
             } else {
                 formattedDates.setFormattedDate(registerItem.getMovedOn() + " - " + previousDate);
                 dates.add(formattedDates);
             }
         }
         register.setDates(dates);
     }
}
