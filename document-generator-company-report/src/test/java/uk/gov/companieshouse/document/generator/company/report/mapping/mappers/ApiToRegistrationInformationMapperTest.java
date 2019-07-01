package uk.gov.companieshouse.document.generator.company.report.mapping.mappers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.model.company.CompanyProfileApi;
import uk.gov.companieshouse.api.model.company.RegisteredOfficeAddressApi;
import uk.gov.companieshouse.document.generator.company.report.mapping.mappers.registrationinformation.ApiToRegistrationInformationMapper;
import uk.gov.companieshouse.document.generator.company.report.mapping.mappers.registrationinformation.ApiToRegistrationInformationMapperImpl;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.registrationinformation.RegistrationInformation;

import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith({MockitoExtension.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApiToRegistrationInformationMapperTest {

    @InjectMocks
    private ApiToRegistrationInformationMapper apiToRegistrationInformationMapper = new ApiToRegistrationInformationMapperImpl();

    private static final String COMPANY_NUMBER = "00004598";
    private static final String COMPANY_NAME = "Test Company LTD";
    private static final String COMPANY_STATUS = "status";
    private static final String COMPANY_STATUS_DETAILS = "status detail";
    private static final String COMPANY_TYPE = "type";
    private static final String COMPANY_SUB_TYPE = "subtype";
    private static final LocalDate DATE_OF_CREATION = LocalDate.of(2019, 06, 06);
    private static final String DATE_OF_CREATION_FORMATTED = "6 June 2019";
    private static final String CHARITY_NUMBER = "12345";

    private static final String ADDRESS_LINE_ONE = "address line 1";
    private static final String ADDRESS_LINE_TWO = "address line 2";
    private static final String CARE_OF = "care of";
    private static final String COUNTRY = "country";
    private static final String LOCALITY = "locality";
    private static final String PO_BOX = "po box";
    private static final String POSTAL_CODE = "postal code";
    private static final String REGION = "region";
    private static final String PREMISE = "premise";

    private static final String[] SIC_CODES = new String[]{"6000", "6001", "6002"};

    @Test
    @DisplayName("tests company profile data maps to registration information model")
    void testApiToRegistrationInformationMaps() throws IOException {

        CompanyProfileApi companyProfileApi = createCompanyReportApitData();

        RegistrationInformation registrationInformation =
            apiToRegistrationInformationMapper.apiToRegistrationInformation(companyProfileApi);

        assertNotNull(registrationInformation);
        assertEquals(COMPANY_NAME, registrationInformation.getCompanyName());
        assertEquals(COMPANY_NUMBER, registrationInformation.getCompanyNumber());
        assertEquals(COMPANY_TYPE, registrationInformation.getCompanyType().getType());
        assertEquals(COMPANY_SUB_TYPE, registrationInformation.getCompanyType().getSubtype());
        assertEquals(DATE_OF_CREATION_FORMATTED, registrationInformation.getDateOfIncorporation());
        assertEquals(COMPANY_STATUS, registrationInformation.getStatus().getCompanyStatus());
        assertEquals(COMPANY_STATUS_DETAILS, registrationInformation.getStatus().getCompanyStatusDetail());
        assertEquals(CHARITY_NUMBER,registrationInformation.getExternalRegistrationNumber());

    }

    private CompanyProfileApi createCompanyReportApitData() {

        CompanyProfileApi companyProfileApi = new CompanyProfileApi();

        companyProfileApi.setCompanyName(COMPANY_NAME);
        companyProfileApi.setCompanyNumber(COMPANY_NUMBER);
        companyProfileApi.setCompanyStatus(COMPANY_STATUS);
        companyProfileApi.setCompanyStatusDetail(COMPANY_STATUS_DETAILS);
        companyProfileApi.setType(COMPANY_TYPE);
        companyProfileApi.setSubtype(COMPANY_SUB_TYPE);
        companyProfileApi.setSicCodes(SIC_CODES);
        companyProfileApi.setDateOfCreation(DATE_OF_CREATION);
        companyProfileApi.setRegisteredOfficeAddress(setAddress());
        companyProfileApi.setExternalRegistrationNumber(CHARITY_NUMBER);

        return  companyProfileApi;
    }

    private RegisteredOfficeAddressApi setAddress() {

        RegisteredOfficeAddressApi registeredOfficeAddress = new RegisteredOfficeAddressApi();
        registeredOfficeAddress.setAddressLine1(ADDRESS_LINE_ONE);
        registeredOfficeAddress.setAddressLine2(ADDRESS_LINE_TWO);
        registeredOfficeAddress.setCareOf(CARE_OF);
        registeredOfficeAddress.setCountry(COUNTRY);
        registeredOfficeAddress.setLocality(LOCALITY);
        registeredOfficeAddress.setPoBox(PO_BOX);
        registeredOfficeAddress.setPostalCode(POSTAL_CODE);
        registeredOfficeAddress.setRegion(REGION);
        registeredOfficeAddress.setPremises(PREMISE);

        return registeredOfficeAddress;
    }
}
