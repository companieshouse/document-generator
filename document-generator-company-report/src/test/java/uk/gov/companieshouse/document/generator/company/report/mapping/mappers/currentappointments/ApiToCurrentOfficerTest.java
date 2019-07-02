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
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.currentappointments.items.CurrentOfficer;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith({MockitoExtension.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApiToCurrentOfficerTest {

    @InjectMocks
    private ApiToCurrentOfficer apiToCurrentOfficer = new ApiToCurrentOfficerImpl();

    private static final String NAME = "name";
    private static final LocalDate APPOINTED_ON = LocalDate.of(
        2019, 06, 06);
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

    @Test
    @DisplayName("tests company profile data maps to registration information model")
    void testApiToRegistrationInformationMaps() throws Exception {

        CompanyOfficerApi companyOfficerApi = createCompanyOfficerApi();

        CurrentOfficer currentOfficer =
            apiToCurrentOfficer.apiToCurrentOfficer(companyOfficerApi);

        assertNotNull(currentOfficer);
        
    }

    private CompanyOfficerApi createCompanyOfficerApi() {
        CompanyOfficerApi companyOfficerApi = new CompanyOfficerApi();

        companyOfficerApi.setName(NAME);
        companyOfficerApi.setAddress(createAddress());
        companyOfficerApi.setDateOfBirth(createDateOfBirth());
        companyOfficerApi.setCountryOfResidence(COUNTRY_OF_RESIDENCE);
        companyOfficerApi.setAppointedOn(APPOINTED_ON);
        companyOfficerApi.setFormerNames(createFormerNames());
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
        dateOfBirth.setMonth(1L);
        dateOfBirth.setYear(1993L);

        return dateOfBirth;
    }

    private List<FormerNamesApi> createFormerNames() {
        List<FormerNamesApi> formerNames = new ArrayList<>();

        FormerNamesApi formerName1 = new FormerNamesApi();
        FormerNamesApi formerName2 = new FormerNamesApi();

        formerName1.setForenames("forename1");
        formerName2.setForenames("forename2");

        formerName1.setSurname("surname1");
        formerName2.setSurname("surname2");

        formerNames.add(formerName1);
        formerNames.add(formerName2);

        return formerNames;
    }

    private IdentificationApi createIdentification() {
        IdentificationApi identificationApi = new IdentificationApi();

        identificationApi.setIdentificationType("identification type");
        identificationApi.setLegalAuthority("legal authority");
        identificationApi.setLegalForm("legal form");
        identificationApi.setPlaceRegistration("place registration");
        identificationApi.setRegistrationNumber("registration number");

        return identificationApi;
    }
}
