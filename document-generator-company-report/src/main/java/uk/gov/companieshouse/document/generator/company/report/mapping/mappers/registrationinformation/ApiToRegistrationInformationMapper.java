package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.registrationinformation;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.web.context.annotation.RequestScope;
import uk.gov.companieshouse.api.model.company.CompanyProfileApi;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.registrationinformation.RegistrationInformation;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.registrationinformation.items.CompanyType;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.registrationinformation.items.SicCodes;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.registrationinformation.items.Status;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequestScope
@Mapper(componentModel = "spring")
public abstract class ApiToRegistrationInformationMapper {

    @Mappings({
            @Mapping(source = "companyName", target = "companyName"),
            @Mapping(source = "companyNumber", target = "companyNumber"),
            @Mapping(source = "dateOfCreation", target = "dateOfIncorporation"),
            @Mapping(source = "registeredOfficeAddress.addressLine1", target = "registeredOffice.addressLine1"),
            @Mapping(source = "registeredOfficeAddress.addressLine2", target = "registeredOffice.addressLine2"),
            @Mapping(source = "registeredOfficeAddress.careOf", target = "registeredOffice.careOf"),
            @Mapping(source = "registeredOfficeAddress.country", target = "registeredOffice.country"),
            @Mapping(source = "registeredOfficeAddress.locality", target = "registeredOffice.locality"),
            @Mapping(source = "registeredOfficeAddress.poBox", target = "registeredOffice.poBox"),
            @Mapping(source = "registeredOfficeAddress.postalCode", target = "registeredOffice.postalCode"),
            @Mapping(source = "registeredOfficeAddress.premises", target = "registeredOffice.premises"),
            @Mapping(source = "registeredOfficeAddress.region", target = "registeredOffice.region")
    })
    public abstract RegistrationInformation apiToRegistrationInformation(CompanyProfileApi companyProfileApi) throws IOException;

    @AfterMapping
    protected void convertEnumerationValues(CompanyProfileApi companyProfileApi,
        @MappingTarget RegistrationInformation registrationInformation) {

        if (companyProfileApi != null) {
            registrationInformation.setCompanyType(
                setCompanyType(companyProfileApi.getType(),
                    companyProfileApi.getSubtype()));

            registrationInformation.setNatureOfBusiness(
                setNatureOfBusiness(companyProfileApi.getSicCodes()));

            registrationInformation.setStatus(
                setCompanyStatus(companyProfileApi.getCompanyStatus(),
                    companyProfileApi.getCompanyStatusDetail()));
        }
    }

    //TODO convert companyStatus and companyStatusDetail param to api-enumeration value in constants.yml PCI-77
    private Status setCompanyStatus(String companyStatus, String companyStatusDetail) {

        Status status = new Status();

        if (companyStatus != null && !companyStatus.isEmpty()) {
            status.setCompanyStatus(companyStatus);
        }

        if (companyStatusDetail != null && !companyStatusDetail.isEmpty()) {
            status.setCompanyStatusDetail(companyStatusDetail);
        }

        return status;
    }

    //TODO convert sicCodes to obtain SicCodesDescription from api-enumeration value in constants.yml PCI-77
    private List<SicCodes> setNatureOfBusiness(String[] sicCodes) {

        List<SicCodes> listNatureOfBusiness = new ArrayList<>();

        for (String sicCode : sicCodes) {
            SicCodes codes = new SicCodes();
            codes.setSicCodes(sicCode);
            codes.setSicCodesDescription(sicCode + "To be converted to api enumeration value PCI-77");
            listNatureOfBusiness.add(codes);
        }

        return listNatureOfBusiness;
    }

    //TODO convert type and subtype param to api-enumeration value in constants.yml PCI-77
    private CompanyType setCompanyType(String type, String subtype) {

        CompanyType companyType = new CompanyType();

        if (type != null && !type.isEmpty()) {
            companyType.setType(type);
        }

        if (subtype != null && !subtype.isEmpty()) {
            companyType.setSubtype(subtype);
        }

        return companyType;
    }
}