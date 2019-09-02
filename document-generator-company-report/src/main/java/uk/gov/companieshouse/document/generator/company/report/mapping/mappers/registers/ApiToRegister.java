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

import java.util.HashMap;
import java.util.Map;

@RequestScope
@Mapper(componentModel = "spring", uses = {ApiToRegisterItems.class})
public abstract class ApiToRegister {

     private static final String REGISTER_TYPE_DESCRIPTION = "CONSTANTS";
     private static final String ENUMERATION_MAPPING = "Enumeration mapping :";

     public abstract Register apiToRegister(RegisterApi registerApi);

     @Autowired
     private RetrieveApiEnumerationDescription retrieveApiEnumerationDescription;

     @AfterMapping
     protected void setInformationSentenceDetails(RegisterApi registerApi,
                                              @MappingTarget Register register) {

         if (register != null && !register.getItems().isEmpty()) {
             RegisterItems firstItem = register.getItems().get(0);

             register.setInformation(firstItem.getRegisterMovedTo().toLowerCase());
             register.setInformationMovedOnDate(firstItem.getMovedOn());
         }
     }

     @AfterMapping
     protected void createFormattedDateList(@MappingTarget Register register) {
         String previousDate = null;

         for (RegisterItems registerItem : register.getItems()) {

             if (previousDate == null || previousDate.isEmpty()) {
                 registerItem.setFormattedDate(registerItem.getMovedOn());
                 previousDate = registerItem.getMovedOn();
             } else {
                 registerItem.setFormattedDate(registerItem.getMovedOn() + " - " + previousDate);
                 previousDate = registerItem.getMovedOn();
             }
         }
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

         String test = registerTypeDescription.substring(0, 1).toLowerCase() + registerTypeDescription.substring(1);
         return registerTypeDescription.substring(0, 1).toLowerCase() + registerTypeDescription.substring(1);
     }

    private Map<String, String> getDebugMap(String debugString) {

        Map<String, String> debugMap = new HashMap<>();
        debugMap.put(ENUMERATION_MAPPING, debugString);

        return debugMap;
    }
}
