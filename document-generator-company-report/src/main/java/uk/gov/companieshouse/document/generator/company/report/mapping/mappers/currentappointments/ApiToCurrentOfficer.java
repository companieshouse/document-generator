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
import uk.gov.companieshouse.document.generator.company.report.service.CompanyReportApiClientService;

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

    private static final String CONSTANTS = "constants.yml";

    @Mappings({
        @Mapping(source = "appointedOn", target = "appointed"),
        @Mapping(ignore = true, target = "officerRole")
    })
    public abstract CurrentOfficer apiToCurrentOfficer(CompanyOfficerApi companyOfficerApi) throws MapperException;

    public abstract List<CurrentOfficer> apiToCurrentOfficer(List<CompanyOfficerApi> companyOfficerApis) throws MapperException;

    @AfterMapping
    protected void convertOfficerRole(CompanyOfficerApi companyOfficerApi, @MappingTarget CurrentOfficer currentOfficer) {

        if (hasOfficerRole(companyOfficerApi)) {
            currentOfficer.setOfficerRole(retrieveApiEnumerationDescription
                .getApiEnumerationDescription(CONSTANTS, "officer_role",
                    (companyOfficerApi.getOfficer_role().getOfficerRole().toLowerCase()), null));
        }
    }

    @AfterMapping
    protected void setOfficerAppointments(CompanyOfficerApi companyOfficerApi, @MappingTarget CurrentOfficer currentOfficer) throws MapperException {

        if (hasAppointmentLink(companyOfficerApi)) {

            ApiClient apiClient = companyReportApiClientService.getApiClient();
            OfficerAppointmentsApi officerAppointmentsApi;

            try {
                officerAppointmentsApi = apiClient.officerAppointment()
                    .get(new UriTemplate(companyOfficerApi.getLinks().getOfficer().getAppointments()).toString()).execute().getData();
            } catch (ApiErrorResponseException | URIValidationException e) {
                throw new MapperException("An error occurred when retrieving officer appointments", e);
            }

            if (officerAppointmentsApi != null) {
                currentOfficer.setNumberOfAppointments(officerAppointmentsApi.getTotalResults());
            }
        }
    }

    private boolean hasOfficerRole(CompanyOfficerApi companyOfficerApi) {
        return companyOfficerApi.getOfficer_role() != null &&
            companyOfficerApi.getOfficer_role().getOfficerRole() != null;
    }

    private boolean hasAppointmentLink(CompanyOfficerApi companyOfficerApi) {
        return companyOfficerApi.getLinks() != null &&
            companyOfficerApi.getLinks().getOfficer() != null &&
            companyOfficerApi.getLinks().getOfficer().getAppointments() != null;
    }

    private Map<String, String> getDebugMap(String debugString) {

        Map<String, String> debugMap = new HashMap<>();
        debugMap.put("Enumeration mapping :", debugString);

        return debugMap;
    }
}
