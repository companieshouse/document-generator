package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.registers;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.annotation.RequestScope;
import uk.gov.companieshouse.api.model.registers.RegisterApi;
import uk.gov.companieshouse.document.generator.common.descriptions.RetrieveApiEnumerationDescription;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.registers.items.Register;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.registers.items.RegisterItems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestScope
@Mapper(componentModel = "spring", uses = {ApiToRegisterItems.class})
public abstract class ApiToRegister {

     @Autowired
     private RetrieveApiEnumerationDescription retrieveApiEnumerationDescription;

     private static final String REGISTER_TYPE_DESCRIPTION = "CONSTANTS";
     private static final String ENUMERATION_MAPPING = "Enumeration mapping :";

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
     protected void createFormattedDateAndLocationList(RegisterApi registerApi,
                                   @MappingTarget Register register) {
         String previousDate = null;

         List<RegisterItems> itemsList = new ArrayList<>();

         for (RegisterItems registerItem : register.getItems()) {

             RegisterItems item = new RegisterItems();

             if (previousDate == null || previousDate.isEmpty()) {
                 item.setFormattedDate(registerItem.getMovedOn());
                 item.setRegisterMovedTo(registerItem.getRegisterMovedTo());

                 itemsList.add(item);
                 previousDate = registerItem.getMovedOn();
             } else {
                 item.setFormattedDate(registerItem.getMovedOn() + " - " + previousDate);
                 itemsList.add(item);

                 item.setRegisterMovedTo(registerItem.getRegisterMovedTo());
                 previousDate = registerItem.getMovedOn();
             }
         }
         register.setItems(itemsList);
     }

     @AfterMapping
     protected void convertRegisterTypeDescription(RegisterApi registerApi,
                                        @MappingTarget Register register) {
         register.setRegisterType(setRegisterTypeDescription(registerApi.getRegisterType()));
     }

     private String setRegisterTypeDescription(String registerType) {

         String registerTypeDescription = retrieveApiEnumerationDescription.
             getApiEnumerationDescription(REGISTER_TYPE_DESCRIPTION, "register_types",
                 registerType, getDebugMap(registerType));

         return registerTypeDescription.toLowerCase();

     }

    private Map<String, String> getDebugMap(String debugString) {

        Map<String, String> debugMap = new HashMap<>();
        debugMap.put(ENUMERATION_MAPPING, debugString);

        return debugMap;
    }
}
