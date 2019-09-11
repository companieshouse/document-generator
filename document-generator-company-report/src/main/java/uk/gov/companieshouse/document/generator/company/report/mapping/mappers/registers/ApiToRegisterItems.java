package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.registers;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.annotation.RequestScope;
import uk.gov.companieshouse.api.model.registers.RegisterItemsApi;
import uk.gov.companieshouse.document.generator.common.descriptions.RetrieveApiEnumerationDescription;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.registers.items.RegisterItems;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestScope
@Mapper(componentModel = "spring")
public abstract class ApiToRegisterItems {

    @Autowired
    private RetrieveApiEnumerationDescription retrieveApiEnumerationDescription;

    private static final String REGISTERS_DESCRIPTION ="CONSTANTS";
    private static final String ENUMERATION_MAPPING = "Enumeration mapping :";
    private static final String D_MMMM_UUUU = "d MMMM uuuu";

    public abstract RegisterItems apiToRegister(RegisterItemsApi registerItemsApi);

    public abstract List<RegisterItems> apiToRegister(List<RegisterItemsApi> registerItemsApi);

    @AfterMapping
    protected void convertRegistersDescription(RegisterItemsApi registerItemsApi,
            @MappingTarget RegisterItems registerItems) {

        registerItems.setRegisterMovedTo(setRegistersDescription(registerItemsApi.getRegisterMovedTo()));
    }

    private String setRegistersDescription(String description) {

        return retrieveApiEnumerationDescription.
            getApiEnumerationDescription(REGISTERS_DESCRIPTION, "register_locations",
                description, getDebugMap(description));
    }

    @AfterMapping
    protected void formatRegisterDate(RegisterItemsApi registerItemsApi,
              @MappingTarget RegisterItems registerItems) {
        if (registerItemsApi != null && registerItemsApi.getMovedOn() != null) {
            LocalDate registerDate = registerItemsApi.getMovedOn();
            registerItems.setMovedOn(registerDate.format(getDateFormatter()));
        }
    }

    private Map<String, String> getDebugMap(String debugString) {

        Map<String, String> debugMap = new HashMap<>();
        debugMap.put(ENUMERATION_MAPPING, debugString);

        return debugMap;
    }

    private DateTimeFormatter getDateFormatter() {
        return DateTimeFormatter.ofPattern(D_MMMM_UUUU);
    }
}
