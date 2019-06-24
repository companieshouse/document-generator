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

    private static final PersonOfficerDetailsApi PERSON_OFFICER_DETAILS = new PersonOfficerDetailsApi();
    private static final CompanyOfficerDetailsApi COMPANY_OFFICER_DETAILS = new CompanyOfficerDetailsApi();

    private static final String TITLE = "title";
    private static final String FORENAME = "forename";
    private static final String MIDDLENAME = "middlename";
    private static final String SURNAME = "surname";

    @Test
    @DisplayName("Tests defendant API values map to defendant DocGen model for a person defendant")
    void testApiToPersonDefendantMaps() {
    	Defendant defendant = apiToDefendantMapper.apiToDefendant(createPersonDefendant(TITLE, FORENAME, MIDDLENAME, SURNAME));
        String defendantName = TITLE + " " + FORENAME + " " + MIDDLENAME + " " + SURNAME;

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
        Defendant defendant = apiToDefendantMapper.apiToDefendant(createPersonDefendant(" " + TITLE, FORENAME + "   ", " " + MIDDLENAME + " ", " " + SURNAME));
        String defendantName = TITLE + " " + FORENAME + " " + MIDDLENAME + " " + SURNAME;

        assertNotNull(defendant);
        assertEquals(TITLE + " " + FORENAME + " " + MIDDLENAME + " " + SURNAME, defendant.getName());
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


    @Test
    @DisplayName("Tests the name is correctly formatted when the title is missing")
    public void testMapNoTitle() {
        Defendant defendant = apiToDefendantMapper.apiToDefendant(createPersonDefendant(null, FORENAME, MIDDLENAME, SURNAME));
        assertEquals(FORENAME + " " + MIDDLENAME + " " + SURNAME, defendant.getName());
    }

    @Test
    @DisplayName("Tests the name is correctly formatted when the title is empty")
    public void testMapEmptyTitle() {
        Defendant defendant = apiToDefendantMapper.apiToDefendant(createPersonDefendant("", FORENAME, MIDDLENAME, SURNAME));
        assertEquals(FORENAME + " " + MIDDLENAME + " " + SURNAME, defendant.getName());
    }

    @Test
    @DisplayName("Tests the name is correctly formatted when the forename is missing")
    public void testMapNoForename() {
        Defendant defendant = apiToDefendantMapper.apiToDefendant(createPersonDefendant(TITLE, null, MIDDLENAME, SURNAME));
        assertEquals(TITLE + " " + MIDDLENAME + " " + SURNAME, defendant.getName());
    }

    @Test
    @DisplayName("Tests the name is correctly formatted when the forename is empty")
    public void testMapEmptyForename() {
        Defendant defendant = apiToDefendantMapper.apiToDefendant(createPersonDefendant(TITLE, "", MIDDLENAME, SURNAME));
        assertEquals(TITLE + " " + MIDDLENAME + " " + SURNAME, defendant.getName());
    }

    @Test
    @DisplayName("Tests the name is correctly formatted when the middle name is missing")
    public void testMapNoMiddleName() {
        Defendant defendant = apiToDefendantMapper.apiToDefendant(createPersonDefendant(TITLE, FORENAME, null, SURNAME));
        assertEquals(TITLE + " " + FORENAME + " " + SURNAME, defendant.getName());
    }

    @Test
    @DisplayName("Tests the name is correctly formatted when the middle name is empty")
    public void testMapEmptyMiddleName() {
        Defendant defendant = apiToDefendantMapper.apiToDefendant(createPersonDefendant(TITLE, FORENAME, "", SURNAME));
        assertEquals(TITLE + " " + FORENAME + " " + SURNAME, defendant.getName());
    }

    @Test
    @DisplayName("Tests the name is correctly formatted when the surname is missing")
    public void testMapNoSurname() {
        Defendant defendant = apiToDefendantMapper.apiToDefendant(createPersonDefendant(TITLE, FORENAME, MIDDLENAME, null));
        assertEquals(TITLE + " " + FORENAME + " " + MIDDLENAME, defendant.getName());
    }

    @Test
    @DisplayName("Tests the name is correctly formatted when the surname is empty")
    public void testMapEmptySurname() {
        Defendant defendant = apiToDefendantMapper.apiToDefendant(createPersonDefendant(TITLE, FORENAME, MIDDLENAME, ""));
        assertEquals(TITLE + " " + FORENAME + " " + MIDDLENAME, defendant.getName());
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
