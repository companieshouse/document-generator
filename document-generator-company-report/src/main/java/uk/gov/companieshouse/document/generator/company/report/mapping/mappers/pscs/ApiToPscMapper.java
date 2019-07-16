package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.pscs;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.annotation.RequestScope;
import uk.gov.companieshouse.api.model.psc.PscApi;
import uk.gov.companieshouse.document.generator.common.descriptions.RetrieveApiEnumerationDescription;
import uk.gov.companieshouse.document.generator.company.report.exception.MapperException;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.pscs.items.NaturesOfControl;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.pscs.items.Psc;

@RequestScope
@Mapper(componentModel = "spring")
public abstract class ApiToPscMapper {

    private static final String ENUMERATION_MAPPING = "Enumeration mapping :";
    private static final String PSC_DESCRIPTIONS = "PSC_DESCRIPTIONS";
    private static final String IDENTIFIER = "description";

    @Mappings({

            @Mapping(target = "naturesOfControl", ignore = true)
    })

    public abstract Psc apiToPsc(PscApi pscApi) throws MapperException;
    public abstract List<Psc> apiToPsc(List<PscApi> pscApi) throws MapperException;

    @Autowired
    private RetrieveApiEnumerationDescription retrieveApiEnumerationDescription;

    @AfterMapping
    protected void setNaturesOfControl(PscApi pscApi, @MappingTarget Psc psc) {

        if (pscApi != null && pscApi.getNaturesOfControl() != null) {
                psc.setNaturesOfControl(setNaturesOfControl(pscApi.getNaturesOfControl()));
            }
        }

    private List<NaturesOfControl> setNaturesOfControl(String[] naturesOfControl) {

        List<NaturesOfControl> naturesOfControlList = new ArrayList<>();

        if (naturesOfControl != null) {
            for (String natureOfControl : naturesOfControl) {
                NaturesOfControl natures = new NaturesOfControl();
                natures.setNaturesOfControl(natureOfControl);
                String natureOfControlDescription = retrieveApiEnumerationDescription
                        .getApiEnumerationDescription(PSC_DESCRIPTIONS, IDENTIFIER,
                                natureOfControl, getDebugMap(natureOfControl));
                natures.setNaturesOfControlDescription(natureOfControlDescription);
                naturesOfControlList.add(natures);
            }
        }

        return naturesOfControlList;
    }

    private Map<String, String> getDebugMap(String debugString) {

        Map<String, String> debugMap = new HashMap<>();
        debugMap.put(ENUMERATION_MAPPING, debugString);

        return debugMap;
    }

}
