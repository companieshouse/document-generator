package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.currentappointments;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static uk.gov.companieshouse.document.generator.company.report.utils.TestUtils.getFormatter;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.companieshouse.api.model.common.Address;
import uk.gov.companieshouse.api.model.common.ContactDetails;
import uk.gov.companieshouse.api.model.common.DateOfBirth;
import uk.gov.companieshouse.api.model.officers.CompanyOfficerApi;
import uk.gov.companieshouse.api.model.officers.IdentificationApi;
import uk.gov.companieshouse.api.model.officers.IdentityVerificationDetails;
import uk.gov.companieshouse.document.generator.common.descriptions.RetrieveApiEnumerationDescription;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.currentappointments.items.CurrentOfficer;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.currentappointments.items.Identification;

@ExtendWith({MockitoExtension.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ApiToCurrentOfficerTest {

    private static final Logger log = LoggerFactory.getLogger(ApiToCurrentOfficerTest.class);
    @Mock
    private RetrieveApiEnumerationDescription mockRetrieveApiEnumerations;

    @InjectMocks
    private ApiToCurrentOfficer apiToCurrentOfficer = new ApiToCurrentOfficerImpl();

    private static final String NAME = "name";
    private static final LocalDate APPOINTED_ON = LocalDate.of(
        2019, 06, 06);
    private static final LocalDate RESIGNED_ON = LocalDate.of(
            2019, 05, 05);
    private static final LocalDate IDV_HISTORIC_DATE = LocalDate.of(1992, 4, 7);
    private static final LocalDate IDV_FUTURE_DATE = IDV_HISTORIC_DATE.withYear(LocalDate.now().plusYears(1).getYear());
    private static final String IDV_PREFERRED_NAME = "Preferred Name";
    private static final List<String> IDV_AML_BODIES = List.of("AML1", "AML2");
    private static final String COUNTRY_OF_RESIDENCE = "country of residence";
    private static final String ADDRESS_LINE_ONE = "address line 1";
    private static final String ADDRESS_LINE_TWO = "address line 2";
    private static final String CARE_OF = "care of";
    private static final String COUNTRY = "country";
    private static final String LOCALITY = "locality";
    private static final String PO_BOX = "po box";
    private static final String POSTAL_CODE = "postal code";
    private static final String REGION = "region";
    private static final String PREMISE = "premise";

    private static final Long MONTH = 1L;
    private static final Long YEAR = 1993L;

    private static final String IDENTIFICATION_TYPE = "identification type";
    private static final String LEGAL_AUTHORITY = "legal authority";
    private static final String LEGAL_FORM = "legal form";
    private static final String PLACE_REGISTRATION = "place registration";
    private static final String REGISTRATION_NUMBER = "registration number";
    private static final String REGISTER_LOCATION = "register location";

    private static final String RESPONSIBILITIES = "Window cleaner and security guard";
    private static final String CONTACT_NAME = "Craig Frankie Baldwin";

    @Test
    @DisplayName("tests api officer IDV data maps to internal officer model")
    void testApiToLocalModelIdvMaps() {

        CompanyOfficerApi companyOfficerApi = createCompanyOfficerApiWithIdv();

        when(mockRetrieveApiEnumerations.getApiEnumerationDescription(anyString(), anyString(), anyString(), any())).thenReturn(IDENTIFICATION_TYPE);

        CurrentOfficer currentOfficer =
                apiToCurrentOfficer.apiToCurrentOfficer(companyOfficerApi);

        assertNotNull(currentOfficer);

        assertEquals(NAME, currentOfficer.getName());
        assertEquals(ADDRESS_LINE_ONE, currentOfficer.getAddress().getAddressLine1());
        assertEquals(ADDRESS_LINE_TWO, currentOfficer.getAddress().getAddressLine2());
        assertEquals(CARE_OF, currentOfficer.getAddress().getCareOf());
        assertEquals(COUNTRY, currentOfficer.getAddress().getCountry());
        assertEquals(LOCALITY, currentOfficer.getAddress().getLocality());
        assertEquals(PO_BOX, currentOfficer.getAddress().getPoBox());
        assertEquals(POSTAL_CODE, currentOfficer.getAddress().getPostalCode());
        assertEquals(REGION, currentOfficer.getAddress().getRegion());
        assertEquals(PREMISE, currentOfficer.getAddress().getPremises());
        assertEquals("January", currentOfficer.getDateOfBirth().getMonth());
        assertEquals(1993L, currentOfficer.getDateOfBirth().getYear().longValue());
        assertEquals(COUNTRY_OF_RESIDENCE, currentOfficer.getCountryOfResidence());
        assertEquals("6 June 2019", currentOfficer.getAppointed());
        assertEquals("5 May 2019", currentOfficer.getResigned());

        checkIdentificationData(currentOfficer.getIdentification());

        String idvHistoricDateExpectedFormat = IDV_HISTORIC_DATE.format(getFormatter());
        String idvFutureDateExpectedFormat = IDV_FUTURE_DATE.format(getFormatter());

        uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.currentappointments.items.IdentityVerificationDetails idv = currentOfficer.getIdentityVerificationDetails();

        assertEquals(idvHistoricDateExpectedFormat, idv.getAppointmentVerificationStatementDate());
        assertEquals(idvHistoricDateExpectedFormat, idv.getAppointmentVerificationStartOn());
        assertEquals(idvHistoricDateExpectedFormat, idv.getIdentityVerifiedOn());
        assertEquals(idvFutureDateExpectedFormat, idv.getAppointmentVerificationEndOn());
        assertEquals(IDV_AML_BODIES, idv.getAntiMoneyLaunderingSupervisoryBodies());
        assertEquals(IDV_PREFERRED_NAME, idv.getPreferredName());

    }

    @Test
    @DisplayName("tests company profile data maps to registration information model")
    void testApiToRegistrationInformationMaps() {

        CompanyOfficerApi companyOfficerApi = createCompanyOfficerApi();

        when(mockRetrieveApiEnumerations.getApiEnumerationDescription(anyString(), anyString(), anyString(), any())).thenReturn(IDENTIFICATION_TYPE);

        CurrentOfficer currentOfficer =
            apiToCurrentOfficer.apiToCurrentOfficer(companyOfficerApi);

        assertNotNull(currentOfficer);

        assertEquals(NAME, currentOfficer.getName());
        assertEquals(ADDRESS_LINE_ONE, currentOfficer.getAddress().getAddressLine1());
        assertEquals(ADDRESS_LINE_TWO, currentOfficer.getAddress().getAddressLine2());
        assertEquals(CARE_OF, currentOfficer.getAddress().getCareOf());
        assertEquals(COUNTRY, currentOfficer.getAddress().getCountry());
        assertEquals(LOCALITY, currentOfficer.getAddress().getLocality());
        assertEquals(PO_BOX, currentOfficer.getAddress().getPoBox());
        assertEquals(POSTAL_CODE, currentOfficer.getAddress().getPostalCode());
        assertEquals(REGION, currentOfficer.getAddress().getRegion());
        assertEquals(PREMISE, currentOfficer.getAddress().getPremises());
        assertEquals("January", currentOfficer.getDateOfBirth().getMonth());
        assertEquals(1993L, currentOfficer.getDateOfBirth().getYear().longValue());
        assertEquals(COUNTRY_OF_RESIDENCE, currentOfficer.getCountryOfResidence());
        assertEquals("6 June 2019", currentOfficer.getAppointed());
        assertEquals("5 May 2019", currentOfficer.getResigned());

        checkIdentificationData(currentOfficer.getIdentification());
    }

    @Test
    @DisplayName("managing officer fields are mapped correctly to model for ROE")
    void testApiToModelMapsForManagingOfficers() {

        final CompanyOfficerApi companyOfficerApi = createCompanyOfficerApi();

        when(mockRetrieveApiEnumerations.getApiEnumerationDescription(anyString(), anyString(), anyString(), any())).thenReturn(IDENTIFICATION_TYPE);

        final CurrentOfficer currentOfficer =
                apiToCurrentOfficer.apiToCurrentOfficer(companyOfficerApi);

        assertNotNull(currentOfficer);

        assertEquals(ADDRESS_LINE_ONE, currentOfficer.getPrincipalOfficeAddress().getAddressLine1());
        assertEquals(ADDRESS_LINE_TWO, currentOfficer.getPrincipalOfficeAddress().getAddressLine2());
        assertEquals(CARE_OF, currentOfficer.getPrincipalOfficeAddress().getCareOf());
        assertEquals(COUNTRY, currentOfficer.getPrincipalOfficeAddress().getCountry());
        assertEquals(LOCALITY, currentOfficer.getPrincipalOfficeAddress().getLocality());
        assertEquals(PO_BOX, currentOfficer.getPrincipalOfficeAddress().getPoBox());
        assertEquals(POSTAL_CODE, currentOfficer.getPrincipalOfficeAddress().getPostalCode());
        assertEquals(REGION, currentOfficer.getPrincipalOfficeAddress().getRegion());
        assertEquals(PREMISE, currentOfficer.getPrincipalOfficeAddress().getPremises());

        assertEquals(RESPONSIBILITIES, currentOfficer.getResponsibilities());


        assertEquals(CONTACT_NAME, currentOfficer.getContactDetails().getContactName());

    }

    private CompanyOfficerApi createCompanyOfficerApi() {
        CompanyOfficerApi companyOfficerApi = new CompanyOfficerApi();

        final Address address = createAddress();
        companyOfficerApi.setName(NAME);
        companyOfficerApi.setAddress(address);
        companyOfficerApi.setDateOfBirth(createDateOfBirth());
        companyOfficerApi.setCountryOfResidence(COUNTRY_OF_RESIDENCE);
        companyOfficerApi.setAppointedOn(APPOINTED_ON);
        companyOfficerApi.setResignedOn(RESIGNED_ON);
        companyOfficerApi.setIdentification(createIdentification());
        companyOfficerApi.setPrincipalOfficeAddress(address);
        companyOfficerApi.setResponsibilities(RESPONSIBILITIES);
        companyOfficerApi.setContactDetails(createContactDetails());

        return companyOfficerApi;
    }

    private CompanyOfficerApi createCompanyOfficerApiWithIdv() {
        IdentityVerificationDetails idv = new IdentityVerificationDetails();

        idv.setAppointmentVerificationStatementDate(IDV_HISTORIC_DATE);
        idv.setAppointmentVerificationEndOn(IDV_FUTURE_DATE);
        idv.setAntiMoneyLaunderingSupervisoryBodies(IDV_AML_BODIES);
        idv.setPreferredName(IDV_PREFERRED_NAME);
        idv.setAppointmentVerificationStartOn(IDV_HISTORIC_DATE);
        idv.setIdentityVerifiedOn(IDV_HISTORIC_DATE);

        CompanyOfficerApi companyOfficerApi = createCompanyOfficerApi();

        companyOfficerApi.setIdentityVerificationDetails(idv);

        return companyOfficerApi;
    }

    private Address createAddress() {
        Address address = new Address();

        address.setAddressLine1(ADDRESS_LINE_ONE);
        address.setAddressLine2(ADDRESS_LINE_TWO);
        address.setCareOf(CARE_OF);
        address.setCountry(COUNTRY);
        address.setLocality(LOCALITY);
        address.setPoBox(PO_BOX);
        address.setPostalCode(POSTAL_CODE);
        address.setRegion(REGION);
        address.setPremises(PREMISE);

        return address;
    }

    private DateOfBirth createDateOfBirth() {
        DateOfBirth dateOfBirth = new DateOfBirth();
        dateOfBirth.setMonth(MONTH);
        dateOfBirth.setYear(YEAR);

        return dateOfBirth;
    }

    private IdentificationApi createIdentification() {
        IdentificationApi identificationApi = new IdentificationApi();

        identificationApi.setIdentificationType(IDENTIFICATION_TYPE);
        identificationApi.setLegalAuthority(LEGAL_AUTHORITY);
        identificationApi.setLegalForm(LEGAL_FORM);
        identificationApi.setPlaceRegistration(PLACE_REGISTRATION);
        identificationApi.setRegistrationNumber(REGISTRATION_NUMBER);
        identificationApi.setRegisterLocation(REGISTER_LOCATION);

        return identificationApi;
    }

    private ContactDetails createContactDetails() {
        final ContactDetails contactDetails = new ContactDetails();

        contactDetails.setContactName(CONTACT_NAME);

        return contactDetails;
    }

    private void checkIdentificationData(Identification identification) {
        assertEquals(IDENTIFICATION_TYPE, identification.getIdentificationType());
        assertEquals(LEGAL_AUTHORITY, identification.getLegalAuthority());
        assertEquals(LEGAL_FORM, identification.getLegalForm());
        assertEquals(PLACE_REGISTRATION, identification.getPlaceRegistration());
        assertEquals(REGISTRATION_NUMBER, identification.getRegistrationNumber());
        assertEquals(REGISTER_LOCATION, identification.getRegisterLocation());
    }
}
