package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.exemptions;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.annotation.RequestScope;
import uk.gov.companieshouse.api.model.exemptions.ExemptionApi;
import uk.gov.companieshouse.document.generator.common.descriptions.RetrieveApiEnumerationDescription;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.exemptions.items.Exemption;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.exemptions.items.ExemptionItems;

import java.util.HashMap;
import java.util.Map;

@RequestScope
@Mapper(componentModel = "spring", uses = {ApiToExemptionItems.class})
public abstract class ApiToExemption {

    private static final String EXEMPTION_TYPE_DESCRIPTION ="EXEMPTION_DESCRIPTIONS";
    private static final String ENUMERATION_MAPPING = "Enumeration mapping :";

    public abstract Exemption apiToExemption(ExemptionApi exemptionApi);

    @Autowired
    private RetrieveApiEnumerationDescription retrieveApiEnumerationDescription;

    @AfterMapping
    protected void convertExemptionTypeDescription(ExemptionApi exemptionApi,
                                                   @MappingTarget Exemption exemption) {
        exemption.setExemptionType(setExemptionTypeDescription(exemptionApi.getExemptionType()));
    }

    private String setExemptionTypeDescription(String exemptionType) {

        String exemptionTypeDescription = retrieveApiEnumerationDescription.
            getApiEnumerationDescription(EXEMPTION_TYPE_DESCRIPTION, "exemption_type",
                exemptionType, getDebugMap(exemptionType));

        return exemptionTypeDescription.substring(0, 1).toLowerCase() + exemptionTypeDescription.substring(1);
    }

    @AfterMapping
    protected void setExemptFromDate(ExemptionApi exemptionApi,
                                     @MappingTarget Exemption exemption) {
        if (exemption != null && !exemption.getItems().isEmpty()) {
            ExemptionItems firstItem = exemption.getItems().get(0);

            exemption.setMovedOnDateFormatted(firstItem.getExemptFrom());
        }
    }

    private Map<String, String> getDebugMap(String debugString) {

        Map<String, String> debugMap = new HashMap<>();
        debugMap.put(ENUMERATION_MAPPING, debugString);

        return debugMap;
    }
}
