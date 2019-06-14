package uk.gov.companieshouse.document.generator.prosecution.mapping;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.model.prosecution.defendant.AddressApi;
import uk.gov.companieshouse.api.model.prosecution.defendant.CompanyOfficerDetailsApi;
import uk.gov.companieshouse.api.model.prosecution.defendant.DefendantApi;
import uk.gov.companieshouse.api.model.prosecution.defendant.PersonOfficerDetailsApi;
import uk.gov.companieshouse.document.generator.prosecution.mapping.mappers.ApiToDefendantMapper;
import uk.gov.companieshouse.document.generator.prosecution.mapping.mappers.ApiToDefendantMapperImpl;
import uk.gov.companieshouse.document.generator.prosecution.mapping.model.defendant.Defendant;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApiToDefendantMapperTest {

    private ApiToDefendantMapper apiToDefendantMapper = new ApiToDefendantMapperImpl();

    private static final AddressApi ADDRESS =
            new AddressApi("1", "street", "area", "town", "region", "country", "postcode");
    private static final PersonOfficerDetailsApi PERSON_OFFICER_DETAILS =
            new PersonOfficerDetailsApi();
    private static final CompanyOfficerDetailsApi COMPANY_OFFICER_DETAILS =
            new CompanyOfficerDetailsApi();

    @Test
    @DisplayName("Tests defendant API values map to defendant DocGen model for a person defendant")
    void testApiToPersonDefendantMaps() {
        Defendant defendant = apiToDefendantMapper.apiToDefendant(
                createPersonDefendant("title", "forename", "middlename", "surname"));
        String defendantName =
                PERSON_OFFICER_DETAILS.getTitle() + " " + PERSON_OFFICER_DETAILS.getForename() + " "
                        + PERSON_OFFICER_DETAILS.getMiddleName() + " "
                        + PERSON_OFFICER_DETAILS.getSurname();

        assertNotNull(defendant);
        assertEquals(ADDRESS.getHouseNameNumber(), defendant.getAddress().getHouseNameNumber());
        assertEquals(ADDRESS.getStreet(), defendant.getAddress().getStreet());
        assertEquals(ADDRESS.getArea(), defendant.getAddress().getArea());
        assertEquals(ADDRESS.getPostTown(), defendant.getAddress().getPostTown());
        assertEquals(ADDRESS.getRegion(), defendant.getAddress().getRegion());
        assertEquals(ADDRESS.getCountry(), defendant.getAddress().getCountry());
        assertEquals(ADDRESS.getPostCode(), defendant.getAddress().getPostCode());
        assertEquals(defendantName, defendant.getName());
    }

    @Test
    @DisplayName("Tests defendant API values map to defendant DocGen model for a company defendant")
    void testApiToCompanyDefendantMaps() {
        Defendant defendant = apiToDefendantMapper.apiToDefendant(createCompanyDefendant());

        assertNotNull(defendant);
        assertEquals(ADDRESS.getHouseNameNumber(), defendant.getAddress().getHouseNameNumber());
        assertEquals(ADDRESS.getStreet(), defendant.getAddress().getStreet());
        assertEquals(ADDRESS.getArea(), defendant.getAddress().getArea());
        assertEquals(ADDRESS.getPostTown(), defendant.getAddress().getPostTown());
        assertEquals(ADDRESS.getRegion(), defendant.getAddress().getRegion());
        assertEquals(ADDRESS.getCountry(), defendant.getAddress().getCountry());
        assertEquals(ADDRESS.getPostCode(), defendant.getAddress().getPostCode());
        assertEquals(COMPANY_OFFICER_DETAILS.getCompanyName(), defendant.getName());
    }

    @Test
    @DisplayName("Tests defendant API correctly formats name when extra whitespace values are submitted")
    void testApiToPersonDefendantMapsWithExtraWhitespaceValues() {
        Defendant defendant = apiToDefendantMapper.apiToDefendant(
                createPersonDefendant(" Title", "Forename   ", " Middlename ", " Surname"));

        String defendantName =
                PERSON_OFFICER_DETAILS.getTitle() + " " + PERSON_OFFICER_DETAILS.getForename() + " "
                        + PERSON_OFFICER_DETAILS.getMiddleName() + " "
                        + PERSON_OFFICER_DETAILS.getSurname();

        assertNotNull(defendant);
        assertNotEquals(defendantName, defendant.getName());
        assertEquals("Title Forename Middlename Surname", defendant.getName());
    }

    @Test
    @DisplayName("Tests defendant API handles null values")
    void testApiToPersonDefendantMapsWithNullValue() {
        Defendant defendant =
                apiToDefendantMapper.apiToDefendant(createPersonDefendant(null, null, null, null));

        assertNotNull(defendant);
        assertEquals("", defendant.getName());
    }

    @Test
    @DisplayName("Tests defendant API handles empty values")
    void testApiToPersonDefendantMapsWithEmptyValues() {
        Defendant defendant =
                apiToDefendantMapper.apiToDefendant(createPersonDefendant("", "", "", ""));

        assertNotNull(defendant);
        assertEquals("", defendant.getName());
        assertEquals(0, defendant.getName().length());
    }

    @Test
    @DisplayName("Tests defendant API handles whitespace values")
    void testApiToPersonDefendantMapsWithWhitespaceValues() {
        Defendant defendant =
                apiToDefendantMapper.apiToDefendant(createPersonDefendant(" ", " ", " ", " "));

        assertNotNull(defendant);
        assertEquals("", defendant.getName());
        assertNotEquals(" ", defendant.getName());
        assertEquals(0, defendant.getName().length());
    }

    private DefendantApi createPersonDefendant(String title, String forename, String middlename,
                                               String surname) {
        DefendantApi defendant = new DefendantApi();

        PERSON_OFFICER_DETAILS.setTitle(title);
        PERSON_OFFICER_DETAILS.setForename(forename);
        PERSON_OFFICER_DETAILS.setMiddleName(middlename);
        PERSON_OFFICER_DETAILS.setSurname(surname);
        PERSON_OFFICER_DETAILS.setDateOfBirth(LocalDate.now());

        defendant.setAddressApi(ADDRESS);
        defendant.setPersonOfficerDetailsApi(PERSON_OFFICER_DETAILS);
        defendant.setIsCorporateAppointment(false);

        return defendant;
    }

    private DefendantApi createCompanyDefendant() {
        DefendantApi defendant = new DefendantApi();
        defendant.setIsCorporateAppointment(true);
        COMPANY_OFFICER_DETAILS.setCompanyName("companyName");
        defendant.setCompanyOfficerDetailsApi(COMPANY_OFFICER_DETAILS);
        defendant.setAddressApi(ADDRESS);

        return defendant;
    }
}
