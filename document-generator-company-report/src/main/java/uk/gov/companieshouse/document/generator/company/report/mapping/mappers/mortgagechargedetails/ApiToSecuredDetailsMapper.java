package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.mortgagechargedetails;

import java.util.HashMap;
import java.util.Map;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.annotation.RequestScope;
import uk.gov.companieshouse.api.model.charges.SecuredDetailsApi;
import uk.gov.companieshouse.document.generator.common.descriptions.RetrieveApiEnumerationDescription;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.mortgagechargedetails.items.SecuredDetails;

@RequestScope
@Mapper(componentModel = "spring")
public abstract class ApiToSecuredDetailsMapper {

    @Autowired
    private RetrieveApiEnumerationDescription retrieveApiEnumerationDescription;

    private static final String MORTGAGE_DESCRIPTIONS = "mortgage_descriptions";
    private static final String SECURED_DETAILS_DESCRIPTION = "secured-details-description";

    public abstract SecuredDetails apiToSecuredDetailsMapper(SecuredDetailsApi securedDetailsApi);

    @AfterMapping
    protected void convertType(SecuredDetailsApi securedDetailsApi, @MappingTarget SecuredDetails securedDetails ) {

        if(securedDetailsApi.getType() != null) {
            securedDetails.setType(retrieveApiEnumerationDescription
                .getApiEnumerationDescription(MORTGAGE_DESCRIPTIONS, SECURED_DETAILS_DESCRIPTION,
                    securedDetailsApi.getType().getType(),
                    getDebugMap(securedDetailsApi.getType().getType())));
        }
    }

    private Map<String, String> getDebugMap(String debugString) {

        Map<String, String> debugMap = new HashMap<>();
        debugMap.put("Enumeration mapping :", debugString);

        return debugMap;
    }
}
