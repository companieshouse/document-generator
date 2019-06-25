package uk.gov.companieshouse.document.generator.company.report.mapping.mappers;

import java.util.Map;
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
import uk.gov.companieshouse.document.generator.common.descriptions.yml.Descriptions;
import uk.gov.companieshouse.document.generator.common.descriptions.yml.DescriptionsFactory;
import uk.gov.companieshouse.document.generator.company.report.mapping.mappers.registrationinformation.ApiToRegistrationInformationMapper;
import uk.gov.companieshouse.document.generator.company.report.mapping.mappers.registrationinformation.ApiToRegistrationInformationMapperImpl;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.registrationinformation.RegistrationInformation;

import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApiToRegistrationInformationMapperTest {

    public static final String MAPPED_VALUE = "Mapped Value";
    @InjectMocks
    private ApiToRegistrationInformationMapper apiToRegistrationInformationMapper = new ApiToRegistrationInformationMapperImpl();

    @Mock
    private RetrieveApiEnumerationDescription mockRetrieveApiEnumerations;

    @Mock
    private DescriptionsFactory descriptionsFactory;

    @Mock
    Descriptions descriptions;

    private String COMPANY_NUMBER = "00004598";
    private String COMPANY_NAME = "Test Company LTD";
    private String COMPANY_STATUS = "active-proposal-to-strike-off";
    private String COMPANY_STATUS_MAPPED = "Active proposal to strike off";
    private String COMPANY_STATUS_DETAILS = "community-interest-company";
    private String COMPANY_STATUS_DETAILS_MAPPED = "Community Interest Company (CIC)";
    private String COMPANY_TYPE = "private-unlimited";
    private String COMPANY_TYPE_MAPPED = "Private unlimited company";
    private String COMPANY_SUB_TYPE = "community-interest-company";
    private String COMPANY_SUB_TYPE_MAPPED = "Community Interest Company (CIC)";
    private LocalDate DATE_OF_CREATION = LocalDate.of(2019, 06, 06);
    private String DATE_OF_CREATION_FORMATTED = "6 June 2019";

    public static final String CONSTANTS = "CONSTANTS";

    private String ADDRESS_LINE_ONE = "address line 1";
    private String ADDRESS_LINE_TWO = "address line 2";
    private String CARE_OF = "care of";
    private String COUNTRY = "country";
    private String LOCALITY = "locality";
    private String PO_BOX = "po box";
    private String POSTAL_CODE = "postal code";
    private String REGION = "region";
    private String PREMISE = "premise";

    private static Map<String, String> requestParameters;

    private String[] SIC_CODES = new String[]{"5231", "5232", "5233"};

    @Test
    @DisplayName("tests company profile data maps to registration information model")
    void testApiToRegistrationInformationMaps() throws IOException {

        CompanyProfileApi companyProfileApi = createCompanyReportApiData();

        RegistrationInformation registrationInformation =
            apiToRegistrationInformationMapper.apiToRegistrationInformation(companyProfileApi);

        when(mockRetrieveApiEnumerations.getApiEnumerationDescription(anyString(), anyString(), anyString(), any())).thenReturn(MAPPED_VALUE);

        assertNotNull(registrationInformation);
        assertEquals(COMPANY_NAME, registrationInformation.getCompanyName());
        assertEquals(COMPANY_NUMBER, registrationInformation.getCompanyNumber());
        assertEquals(COMPANY_TYPE_MAPPED, registrationInformation.getCompanyType().getType());
        assertEquals(COMPANY_SUB_TYPE_MAPPED, registrationInformation.getCompanyType().getSubtype());
        assertEquals(DATE_OF_CREATION_FORMATTED, registrationInformation.getDateOfIncorporation());
        assertEquals(COMPANY_STATUS_MAPPED, registrationInformation.getStatus().getCompanyStatus());
        assertEquals(COMPANY_STATUS_DETAILS_MAPPED, registrationInformation.getStatus().getCompanyStatusDetail());

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
        companyProfileApi.setRegisteredOfficeAddress(setAddress());

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
