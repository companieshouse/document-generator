package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.registrationinformation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.CompanyReportApiData;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.registrationinformation.RegistrationInformation;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.registrationinformation.items.CompanyType;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.registrationinformation.items.SicCodes;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.registrationinformation.items.Status;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ApiToRegistrationInformationMapperDecorator implements ApiToRegistrationInformationMapper {

    @Autowired
    @Qualifier("delegate")
    private ApiToRegistrationInformationMapper apiToRegistrationInformationMapper;

    @Override
    public RegistrationInformation apiToRegistrationInformation(CompanyReportApiData companyReportApiData) throws IOException {

        RegistrationInformation registrationInformation =
                apiToRegistrationInformationMapper.apiToRegistrationInformation(companyReportApiData);

        registrationInformation.setCompanyType(
                setCompanyType(companyReportApiData.getCompanyProfileApi().getType(),
                        companyReportApiData.getCompanyProfileApi().getSubtype()));

        registrationInformation.setNatureOfBusiness(
                setNatureOfBusiness(companyReportApiData.getCompanyProfileApi().getSicCodes()));

        registrationInformation.setStatus(
                setCompanyStatus(companyReportApiData.getCompanyProfileApi().getCompanyStatus(),
                        companyReportApiData.getCompanyProfileApi().getCompanyStatusDetail()));

        return registrationInformation;
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
