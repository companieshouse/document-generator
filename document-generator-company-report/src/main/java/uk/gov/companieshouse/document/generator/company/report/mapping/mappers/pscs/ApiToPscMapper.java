package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.pscs;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.annotation.RequestScope;
import uk.gov.companieshouse.api.model.psc.PscApi;
import uk.gov.companieshouse.document.generator.common.descriptions.RetrieveApiEnumerationDescription;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.common.DateDayMonthYear;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.pscs.items.NaturesOfControl;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.pscs.items.Psc;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestScope
@Mapper(componentModel = "spring")
public abstract class ApiToPscMapper {

    private static final String ENUMERATION_MAPPING = "Enumeration mapping :";
    private static final String PSC_DESCRIPTIONS = "PSC_DESCRIPTIONS";
    private static final String IDENTIFIER = "short_description";
    private static final String SUPER_SECURE_DESCRIPTION_IDENTIFIER = "super_secure_description";
    private static final String SUPER_SECURE_PERSON_WITH_SIGNIFICANT_CONTROL_KIND = "super-secure-persons-with-significant-control";
    private static final String SUPER_SECURE_BENEFICIAL_OWNER_KIND = "super-secure-beneficial-owner";
    private static final String D_MMMM_UUUU = "d MMMM uuuu";

    @Mappings({

            @Mapping(target = "naturesOfControl", ignore = true),
            @Mapping(target = "dateOfBirth", ignore = true),
            @Mapping(target = "ceasedOn", ignore = true),
            @Mapping(target = "notifiedOn", ignore = true)
    })

    public abstract Psc apiToPsc(PscApi pscApi);

    public abstract List<Psc> apiToPsc(List<PscApi> pscApi);

    @Autowired
    private RetrieveApiEnumerationDescription retrieveApiEnumerationDescription;

    @AfterMapping
    protected void setNaturesOfControl(PscApi pscApi, @MappingTarget Psc psc) {

        if (pscApi != null && pscApi.getNaturesOfControl() != null) {
            psc.setNaturesOfControl(setNaturesOfControl(pscApi.getNaturesOfControl()));
        }
    }

    @AfterMapping
    protected void setCeasedOnDate(PscApi pscApi, @MappingTarget Psc psc) {

        if (pscApi != null && pscApi.getCeasedOn() != null) {
            LocalDate ceasedOn = pscApi.getCeasedOn();
            psc.setCeasedOn(ceasedOn.format(getFormatter()));
        }
    }

    @AfterMapping
    protected void setDateOfBirth(PscApi pscApi, @MappingTarget Psc psc) {

        if (pscApi != null && pscApi.getDateOfBirth() != null) {
            DateDayMonthYear dob = new DateDayMonthYear();
            String monthString = getNameOfMonth(pscApi);

            dob.setYear(pscApi.getDateOfBirth().getYear());
            //Sentence case month string
            dob.setMonth(monthString.substring(0, 1).toUpperCase()
                    + monthString.substring(1).toLowerCase());

            psc.setDateOfBirth(dob);
        }
    }

    @AfterMapping
    protected void setNotifiedOn(PscApi pscApi, @MappingTarget Psc psc) {

        if (pscApi != null && pscApi.getNotifiedOn() != null) {
            LocalDate notifiedOn = pscApi.getNotifiedOn();
            psc.setNotifiedOn(notifiedOn.format(getFormatter()));
        }
    }

    @AfterMapping
    protected void setSuperSecureDescription(PscApi pscApi, @MappingTarget Psc psc) {

        if (pscApi == null || pscApi.getKind() == null) {
            return;
        }

        if (pscApi.getKind().equals(SUPER_SECURE_BENEFICIAL_OWNER_KIND) ||
                pscApi.getKind().equals(SUPER_SECURE_PERSON_WITH_SIGNIFICANT_CONTROL_KIND)) {
            final String description = retrieveApiEnumerationDescription
                    .getApiEnumerationDescription(PSC_DESCRIPTIONS, SUPER_SECURE_DESCRIPTION_IDENTIFIER,
                            pscApi.getKind(), getDebugMap(pscApi.getKind()));
            psc.setSuperSecureDescription(description);
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

    private String getNameOfMonth(PscApi pscApi) {
        int month = Math.toIntExact(pscApi.getDateOfBirth().getMonth());
        return Month.of(month).name();
    }

    private DateTimeFormatter getFormatter() {
        return DateTimeFormatter.ofPattern(D_MMMM_UUUU);
    }
}
