package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.mortgagechargedetails;

import java.util.HashMap;
import java.util.Map;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.annotation.RequestScope;
import uk.gov.companieshouse.api.model.charges.ParticularsApi;
import uk.gov.companieshouse.document.generator.common.descriptions.RetrieveApiEnumerationDescription;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.mortgagechargedetails.items.Particulars;

@RequestScope
@Mapper(componentModel = "spring")
public abstract class ApiToParticularsMapper {

    @Autowired
    private RetrieveApiEnumerationDescription retrieveApiEnumerationDescription;

    private static final String MORTGAGE_DESCRIPTIONS = "mortgage_descriptions";
    private static final String PARTICULAR_DESCRIPTION = "particular-description";
    private static final String PARTICULAR_FLAG = "particular-flags";
    private static final String CHARGOR_ACTING = "chargor_acting_as_bare_trustee";
    private static final String CONTAINS_FIXED = "contains_fixed_charge";
    private static final String CONTAINS_FLOATING = "contains_floating_charge";
    private static final String CONTAINS_NEGATIVE = "contains_negative_pledge";
    private static final String FLOATING_CHARGE = "floating_charge_covers_all";


    public abstract Particulars apiToParticularsMapper(ParticularsApi particularsApi);

    @AfterMapping
    protected void convertType(ParticularsApi particularsApi, @MappingTarget Particulars particulars) {

        if(particulars.getType() != null) {
            particulars.setType(retrieveApiEnumerationDescription
                .getApiEnumerationDescription(MORTGAGE_DESCRIPTIONS, PARTICULAR_DESCRIPTION,
                    particularsApi.getType().getType(),
                    getDebugMap(particularsApi.getType().getType())));
        }
    }

    @AfterMapping
    protected void addExtraParticularStatements(ParticularsApi particularsApi, @MappingTarget Particulars particulars) {

        if(particularsApi != null) {

            Map<String, String> extraParticularStatements = new HashMap<>();

            if(particularsApi.isChargorActingAsBareTrustee() == true) {
                extraParticularStatements.put(CHARGOR_ACTING, getStatement(CHARGOR_ACTING));
            }

            if(particularsApi.isContainsFixedCharge() == true) {
                extraParticularStatements.put(CONTAINS_FIXED, getStatement(CONTAINS_FIXED));
            }

            if(particularsApi.isContainsFloatingCharge() == true) {
                extraParticularStatements.put(CONTAINS_FLOATING, getStatement(CONTAINS_FLOATING));
            }

            if(particularsApi.isContainsNegativePledge() == true) {
                extraParticularStatements.put(CONTAINS_NEGATIVE, getStatement(CONTAINS_NEGATIVE));
            }

            if(particularsApi.isFloatingChargeCoversAll() == true) {
                extraParticularStatements.put(FLOATING_CHARGE, getStatement(FLOATING_CHARGE));
            }

            particulars.setExtraParticularStatements(extraParticularStatements);
        }
    }

    private String getStatement(String identifier) {

        return retrieveApiEnumerationDescription
            .getApiEnumerationDescription(MORTGAGE_DESCRIPTIONS, PARTICULAR_FLAG,
                identifier, getDebugMap(identifier));
    }

    private Map<String, String> getDebugMap(String debugString) {

        Map<String, String> debugMap = new HashMap<>();
        debugMap.put("Enumeration mapping :", debugString);

        return debugMap;
    }
}
