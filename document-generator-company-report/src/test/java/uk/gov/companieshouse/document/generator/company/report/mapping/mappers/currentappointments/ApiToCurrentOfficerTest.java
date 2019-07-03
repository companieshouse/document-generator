package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.currentappointments;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.model.common.Address;
import uk.gov.companieshouse.api.model.common.DateOfBirth;
import uk.gov.companieshouse.api.model.officers.CompanyOfficerApi;
import uk.gov.companieshouse.api.model.officers.FormerNamesApi;
import uk.gov.companieshouse.api.model.officers.IdentificationApi;
import uk.gov.companieshouse.api.model.officers.OfficerRoleApi;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.currentappointments.items.CurrentOfficer;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith({MockitoExtension.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApiToCurrentOfficerTest {

    @InjectMocks
    private ApiToCurrentOfficer apiToCurrentOfficer = new ApiToCurrentOfficerImpl();

    private static final String NAME = "name";
    private static final LocalDate APPOINTED_ON = LocalDate.of(
        2019, 06, 06);
    private static final LocalDate RESIGNED_ON = LocalDate.of(
            2019, 05, 05);
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

    @Test
    @DisplayName("tests company profile data maps to registration information model")
    void testApiToRegistrationInformationMaps() throws Exception {

        CompanyOfficerApi companyOfficerApi = createCompanyOfficerApi();

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

    }

    private CompanyOfficerApi createCompanyOfficerApi() {
        CompanyOfficerApi companyOfficerApi = new CompanyOfficerApi();

        companyOfficerApi.setName(NAME);
        companyOfficerApi.setAddress(createAddress());
        companyOfficerApi.setDateOfBirth(createDateOfBirth());
        companyOfficerApi.setCountryOfResidence(COUNTRY_OF_RESIDENCE);
        companyOfficerApi.setAppointedOn(APPOINTED_ON);
        companyOfficerApi.setResignedOn(RESIGNED_ON);
        companyOfficerApi.setIdentification(createIdentification());

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

        return identificationApi;
    }
}
