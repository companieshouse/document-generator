package uk.gov.companieshouse.document.generator.company.report.mapping.mappers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.model.company.CompanyProfileApi;
import uk.gov.companieshouse.api.model.company.RegisteredOfficeAddressApi;
import uk.gov.companieshouse.document.generator.common.descriptions.RetrieveApiEnumerationDescription;
import uk.gov.companieshouse.document.generator.company.report.mapping.mappers.registrationinformation.ApiToRegistrationInformationMapper;
import uk.gov.companieshouse.document.generator.company.report.mapping.mappers.registrationinformation.ApiToRegistrationInformationMapperImpl;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.registrationinformation.RegistrationInformation;

@ExtendWith({MockitoExtension.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ApiToRegistrationInformationMapperTest {

    public static final String MAPPED_VALUE = "Mapped Value";

    @InjectMocks
    private ApiToRegistrationInformationMapper apiToRegistrationInformationMapper = new ApiToRegistrationInformationMapperImpl();

    @Mock
    private RetrieveApiEnumerationDescription mockRetrieveApiEnumerations;
  
    private static final String COMPANY_NUMBER = "00004598";
    private static final String COMPANY_NAME = "Test Company LTD";
    private static final String COMPANY_STATUS = "status";
    private static final String COMPANY_STATUS_DETAILS = "status detail";
    private static final String COMPANY_TYPE = "type";
    private static final String COMPANY_SUB_TYPE = "subtype";
    private static final LocalDate DATE_OF_CREATION = LocalDate.of(2019, 06, 06);
    private static final String DATE_OF_CREATION_FORMATTED = "6 June 2019";
    private static final LocalDate DATE_OF_CESSATION = LocalDate.of(2020, 06, 06);
    private static final String DATE_OF_CESSATION_FORMATTED = "6 June 2020";
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

    private static final String[] SIC_CODES = new String[]{"5231", "5232", "5233"};
    private static final RegisteredOfficeAddressApi ADDRESS = createAddress();

    private static final int SUPER_SECURE_MANAGING_OFFICER_COUNT = 2;

    @Test
    @DisplayName("tests company profile data maps to registration information model")
    void testApiToRegistrationInformationMaps() {

        CompanyProfileApi companyProfileApi = createCompanyReportApiData();

        when(mockRetrieveApiEnumerations.getApiEnumerationDescription(anyString(), anyString(), anyString(), any())).thenReturn(MAPPED_VALUE);

        RegistrationInformation registrationInformation =
                apiToRegistrationInformationMapper.apiToRegistrationInformation(companyProfileApi);

        assertNotNull(registrationInformation);
        assertEquals(COMPANY_NAME, registrationInformation.getCompanyName());
        assertEquals(COMPANY_NUMBER, registrationInformation.getCompanyNumber());
        assertEquals(MAPPED_VALUE, registrationInformation.getCompanyType().getType());
        assertEquals(MAPPED_VALUE, registrationInformation.getCompanyType().getSubtype());
        assertEquals(DATE_OF_CREATION_FORMATTED, registrationInformation.getDateOfIncorporation());
        assertEquals(DATE_OF_CESSATION_FORMATTED, registrationInformation.getDateOfDissolution());
        assertEquals(MAPPED_VALUE, registrationInformation.getStatus().getCompanyStatus());
        assertEquals(MAPPED_VALUE, registrationInformation.getStatus().getCompanyStatusDetail());
        assertEquals(MAPPED_VALUE, registrationInformation.getDateOfIncorporationLabel());
        assertEquals(CHARITY_NUMBER, registrationInformation.getExternalRegistrationNumber());
        assertEquals(SUPER_SECURE_MANAGING_OFFICER_COUNT, registrationInformation.getSuperSecureManagingOfficerCount());
        assertEquals(true, registrationInformation.getHasSuperSecureManagingOfficers());
        assertEquals(true, registrationInformation.getHasMoreThanOneSuperSecureManagingOfficers());
        assertThat(registrationInformation.getRegisteredOffice()).isEqualToComparingFieldByField(ADDRESS);
        assertThat(registrationInformation.getServiceAddress()).isEqualToComparingFieldByField(ADDRESS);
    }

    private CompanyProfileApi createCompanyReportApiData() {

        CompanyProfileApi companyProfileApi = new CompanyProfileApi();

        companyProfileApi.setCompanyName(COMPANY_NAME);
        companyProfileApi.setCompanyNumber(COMPANY_NUMBER);
        companyProfileApi.setCompanyStatus(COMPANY_STATUS);
        companyProfileApi.setCompanyStatusDetail(COMPANY_STATUS_DETAILS);
        companyProfileApi.setType(COMPANY_TYPE);
        companyProfileApi.setSubtype(COMPANY_SUB_TYPE);
        companyProfileApi.setSicCodes(SIC_CODES);
        companyProfileApi.setDateOfCreation(DATE_OF_CREATION);
        companyProfileApi.setDateOfCessation(DATE_OF_CESSATION);
        companyProfileApi.setRegisteredOfficeAddress(ADDRESS);
        companyProfileApi.setServiceAddress(ADDRESS);
        companyProfileApi.setExternalRegistrationNumber(CHARITY_NUMBER);
        companyProfileApi.setSuperSecureManagingOfficerCount(SUPER_SECURE_MANAGING_OFFICER_COUNT);

        return  companyProfileApi;
    }

    private static RegisteredOfficeAddressApi createAddress() {

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
