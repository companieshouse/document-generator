package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.registrationinformation;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.annotation.RequestScope;
import uk.gov.companieshouse.api.model.company.CompanyProfileApi;
import uk.gov.companieshouse.document.generator.common.descriptions.RetrieveApiEnumerationDescription;
import uk.gov.companieshouse.document.generator.company.report.mapping.mappers.foreigncompanydetails.ApiToForeignCompanyDetailsMapper;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.registrationinformation.RegistrationInformation;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.registrationinformation.items.CompanyType;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.registrationinformation.items.SicCodes;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.registrationinformation.items.Status;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestScope
@Mapper(componentModel = "spring", uses={ApiToForeignCompanyDetailsMapper.class})
public abstract class ApiToRegistrationInformationMapper {

    private static final String CONSTANTS = "CONSTANTS";
    private static final String COMPANY_STATUS = "company_status";
    private static final String COMPANY_STATUS_DETAIL = "company_status_detail";
    private static final String COMPANY_TYPE = "company_type";
    private static final String COMPANY_SUBTYPE = "company_subtype";
    private static final String SIC_DESCRIPTIONS = "sic_descriptions";
    private static final String REPORT_DATE_FORMAT = "d MMMM uuuu";
    private static final String ENUMERATION_MAPPING = "Enumeration mapping :";
    private static final String COMPANY_BIRTH_TYPE = "company_birth_type";

    @Mappings({
            @Mapping(source = "companyName", target = "companyName"),
            @Mapping(source = "companyNumber", target = "companyNumber"),
            @Mapping(source = "registeredOfficeAddress.addressLine1", target = "registeredOffice.addressLine1"),
            @Mapping(source = "registeredOfficeAddress.addressLine2", target = "registeredOffice.addressLine2"),
            @Mapping(source = "registeredOfficeAddress.careOf", target = "registeredOffice.careOf"),
            @Mapping(source = "registeredOfficeAddress.country", target = "registeredOffice.country"),
            @Mapping(source = "registeredOfficeAddress.locality", target = "registeredOffice.locality"),
            @Mapping(source = "registeredOfficeAddress.poBox", target = "registeredOffice.poBox"),
            @Mapping(source = "registeredOfficeAddress.postalCode", target = "registeredOffice.postalCode"),
            @Mapping(source = "registeredOfficeAddress.premises", target = "registeredOffice.premises"),
            @Mapping(source = "registeredOfficeAddress.region", target = "registeredOffice.region"),
            @Mapping(source = "externalRegistrationNumber", target = "externalRegistrationNumber")
    })
    public abstract RegistrationInformation apiToRegistrationInformation(CompanyProfileApi companyProfileApi) throws IOException;

    @Autowired
    private RetrieveApiEnumerationDescription retrieveApiEnumerationDescription;

    @AfterMapping
    protected void convertEnumerationValues(CompanyProfileApi companyProfileApi,
            @MappingTarget RegistrationInformation registrationInformation) {

        if (companyProfileApi != null) {
            registrationInformation
                    .setCompanyType(setCompanyType(companyProfileApi.getType(), companyProfileApi.getSubtype()));
            registrationInformation
                    .setNatureOfBusiness(setNatureOfBusiness(companyProfileApi.getSicCodes()));
            registrationInformation
                    .setStatus(setCompanyStatus(companyProfileApi.getCompanyStatus(), companyProfileApi.getCompanyStatusDetail()));

        }
    }

    @AfterMapping
    protected void convertDate(CompanyProfileApi companyProfileApi, @MappingTarget RegistrationInformation registrationInformation) {

        if (companyProfileApi != null && companyProfileApi.getDateOfCreation() != null) {
            registrationInformation.setDateOfIncorporation(companyProfileApi.getDateOfCreation().
                    format(DateTimeFormatter.ofPattern(REPORT_DATE_FORMAT)));
        }
    }

    @AfterMapping
    protected void setIncorporationDateLabel(CompanyProfileApi companyProfileApi, @MappingTarget RegistrationInformation registrationInformation) {

        if (companyProfileApi != null && companyProfileApi.getDateOfCreation() != null) {

            registrationInformation.setDateOfIncorporationLabel(retrieveApiEnumerationDescription
                    .getApiEnumerationDescription(CONSTANTS, COMPANY_BIRTH_TYPE, companyProfileApi.getType(), getDebugMap(companyProfileApi.getType())));
        }
    }

    private Map<String, String> getDebugMap(String debugString) {

        Map<String, String> debugMap = new HashMap<>();
        debugMap.put(ENUMERATION_MAPPING, debugString);

        return debugMap;
    }

    private Status setCompanyStatus(String companyStatus, String companyStatusDetail) {

        Status status = new Status();

        if (companyStatus != null && ! companyStatus.isEmpty()) {
            status.setCompanyStatus(retrieveApiEnumerationDescription.
                    getApiEnumerationDescription(CONSTANTS, COMPANY_STATUS, companyStatus, getDebugMap(companyStatus)));
        }

        if (companyStatusDetail != null && ! companyStatusDetail.isEmpty()) {
            status.setCompanyStatusDetail(retrieveApiEnumerationDescription
                    .getApiEnumerationDescription(CONSTANTS, COMPANY_STATUS_DETAIL, companyStatusDetail, getDebugMap(companyStatusDetail)));
        }

        return status;
    }

    private List<SicCodes> setNatureOfBusiness(String[] sicCodes) {

        List<SicCodes> listNatureOfBusiness = new ArrayList<>();

        if (sicCodes != null) {
            for (String sicCode : sicCodes) {
                SicCodes codes = new SicCodes();
                codes.setSicCodes(sicCode);
                String sicCodeDescription = retrieveApiEnumerationDescription
                        .getApiEnumerationDescription(CONSTANTS, SIC_DESCRIPTIONS, sicCode, getDebugMap(sicCode));
                codes.setSicCodesDescription(sicCodeDescription);
                listNatureOfBusiness.add(codes);
            }
        }

        return listNatureOfBusiness;
    }

    private CompanyType setCompanyType(String type, String subtype) {

        CompanyType companyType = new CompanyType();

        if (type != null && ! type.isEmpty()) {
            companyType.setType(retrieveApiEnumerationDescription
                    .getApiEnumerationDescription(CONSTANTS, COMPANY_TYPE, type, getDebugMap(type)));
        }

        if (subtype != null && ! subtype.isEmpty()) {
            companyType.setSubtype(retrieveApiEnumerationDescription
                    .getApiEnumerationDescription(CONSTANTS, COMPANY_SUBTYPE, subtype, getDebugMap(subtype)));
        }

        return companyType;
    }
}