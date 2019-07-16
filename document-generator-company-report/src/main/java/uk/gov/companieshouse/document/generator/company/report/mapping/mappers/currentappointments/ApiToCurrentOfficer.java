package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.currentappointments;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.util.UriTemplate;
import uk.gov.companieshouse.api.ApiClient;
import uk.gov.companieshouse.api.error.ApiErrorResponseException;
import uk.gov.companieshouse.api.handler.exception.URIValidationException;
import uk.gov.companieshouse.api.model.officerappointments.OfficerAppointmentsApi;
import uk.gov.companieshouse.api.model.officers.CompanyOfficerApi;
import uk.gov.companieshouse.document.generator.common.descriptions.RetrieveApiEnumerationDescription;
import uk.gov.companieshouse.document.generator.company.report.exception.MapperException;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.currentappointments.items.CurrentOfficer;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.currentappointments.items.DateOfBirth;
import uk.gov.companieshouse.document.generator.company.report.service.CompanyReportApiClientService;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestScope
@Mapper(componentModel = "spring")
public abstract class ApiToCurrentOfficer {

    @Autowired
    private RetrieveApiEnumerationDescription retrieveApiEnumerationDescription;

    @Autowired
    private CompanyReportApiClientService companyReportApiClientService;

    private static final String CONSTANTS = "CONSTANTS";
    private static final String D_MMMM_UUUU = "d MMMM uuuu";

    @Mappings({
            @Mapping(source = "appointedOn", target = "appointed"),
            @Mapping(source = "countryOfResidence", target = "countryOfResidence")
    })
    public abstract CurrentOfficer apiToCurrentOfficer(CompanyOfficerApi companyOfficerApi) throws MapperException;
    public abstract List<CurrentOfficer> apiToCurrentOfficer(List<CompanyOfficerApi> companyOfficerApis) throws MapperException;

    @AfterMapping
    protected void convertOfficerRole(CompanyOfficerApi companyOfficerApi,
            @MappingTarget CurrentOfficer currentOfficer) {

        if (hasOfficerRole(companyOfficerApi)) {
            currentOfficer.setOfficerRole(retrieveApiEnumerationDescription
                    .getApiEnumerationDescription(CONSTANTS, "officer_role",
                            companyOfficerApi.getOfficerRole().getOfficerRole(),
                            getDebugMap(companyOfficerApi.getOfficerRole().getOfficerRole())));
        }
    }

    @AfterMapping
    protected void formatOfficerDateOfBirth(CompanyOfficerApi companyOfficerApi,
            @MappingTarget CurrentOfficer currentOfficer) {

        if (companyOfficerApi.getDateOfBirth() != null) {

            DateOfBirth dob = new DateOfBirth();
            String monthString = getNameOfMonth(companyOfficerApi);

            dob.setYear(companyOfficerApi.getDateOfBirth().getYear());
            //Sentence case month string
            dob.setMonth(monthString.substring(0, 1).toUpperCase()
                    + monthString.substring(1).toLowerCase());

            currentOfficer.setDateOfBirth(dob);
        }
    }

    @AfterMapping
    protected void convertIdentificationType(CompanyOfficerApi companyOfficerApi,
                                             @MappingTarget CurrentOfficer currentOfficer) {

        if (hasIdentificationType(companyOfficerApi)) {
            currentOfficer.getIdentification().setIdentificationType(retrieveApiEnumerationDescription
                .getApiEnumerationDescription(CONSTANTS, "identification_type",
                    companyOfficerApi.getIdentification().getIdentificationType(),
                    getDebugMap(companyOfficerApi.getIdentification().getIdentificationType())));
        }
    }

    @AfterMapping
    protected void formatAppointedOnDate(CompanyOfficerApi companyOfficerApi,
            @MappingTarget CurrentOfficer currentOfficer) {

        if (companyOfficerApi != null && companyOfficerApi.getAppointedOn() != null) {
            LocalDate appointedOn = companyOfficerApi.getAppointedOn();
            currentOfficer.setAppointed(appointedOn.format(getFormatter()));
        }
    }

    @AfterMapping
    protected void formatResignedOnDate(CompanyOfficerApi companyOfficerApi,
            @MappingTarget CurrentOfficer currentOfficer) {
        if (companyOfficerApi != null && companyOfficerApi.getResignedOn() != null) {
            LocalDate resignedOn = companyOfficerApi.getResignedOn();
            currentOfficer.setResigned(resignedOn.format(getFormatter()));
        }
    }

    private boolean hasOfficerRole(CompanyOfficerApi companyOfficerApi) {
        return companyOfficerApi.getOfficerRole() != null &&
                companyOfficerApi.getOfficerRole().getOfficerRole() != null;
    }

    private boolean hasIdentificationType(CompanyOfficerApi companyOfficerApi) {
        return companyOfficerApi.getIdentification() != null &&
                 companyOfficerApi.getIdentification().getIdentificationType() != null;
    }

    private Map<String, String> getDebugMap(String debugString) {

        Map<String, String> debugMap = new HashMap<>();
        debugMap.put("Enumeration mapping :", debugString);

        return debugMap;
    }

    private DateTimeFormatter getFormatter() {
        return DateTimeFormatter.ofPattern(D_MMMM_UUUU);
    }

    private String getNameOfMonth(CompanyOfficerApi companyOfficerApi) {
        int month = Math.toIntExact(companyOfficerApi.getDateOfBirth().getMonth());
        return Month.of(month).name();
    }
}
