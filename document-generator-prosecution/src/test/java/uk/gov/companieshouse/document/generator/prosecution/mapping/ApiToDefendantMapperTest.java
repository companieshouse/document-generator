//package uk.gov.companieshouse.document.generator.prosecution.mapping;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestInstance;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.junit.jupiter.MockitoExtension;
//import uk.gov.companieshouse.api.model.prosecution.defendant.AddressApi;
//import uk.gov.companieshouse.api.model.prosecution.defendant.CompanyOfficerDetailsApi;
//import uk.gov.companieshouse.api.model.prosecution.defendant.DefendantApi;
//import uk.gov.companieshouse.api.model.prosecution.defendant.PersonOfficerDetailsApi;
//import uk.gov.companieshouse.document.generator.prosecution.mapping.mappers.ApiToDefendantMapper;
//import uk.gov.companieshouse.document.generator.prosecution.mapping.mappers.ApiToDefendantMapperImpl;
//import uk.gov.companieshouse.document.generator.prosecution.mapping.model.defendant.Defendant;
//
//import java.time.LocalDate;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//@ExtendWith(MockitoExtension.class)
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//public class ApiToDefendantMapperTest {
//
//    private ApiToDefendantMapper apiToDefendantMapper = new ApiToDefendantMapperImpl();
//
//    private static final String OFFICER_ID = "officerId";
//    private static final AddressApi ADDRESS =
//            new AddressApi("1", "street", "area", "town", "region", "country", "postcode");
//    private static final PersonOfficerDetailsApi PERSON_OFFICER_DETAILS = new PersonOfficerDetailsApi();
//    private static final CompanyOfficerDetailsApi COMPANY_OFFICER_DETAILS = new CompanyOfficerDetailsApi();
//    private static final LocalDate DATE_APPOINTED_ON = LocalDate.now();
//    private static final LocalDate DATE_TERMINATED_ON = LocalDate.now();
//    private static final String APPOINTMENT_TYPE = "appointmentType";
//    private static final boolean IS_CORPORATE_APPOINTMENT = true;
//
//    @Test
//    @DisplayName("Tests defendant API values map to defendant DocGen model")
//    void testApiToDefendantMaps() {
//        Defendant defendant = apiToDefendantMapper.apiToDefendant(createDefendant());
//
//        assertNotNull(defendant);
//        assertEquals(OFFICER_ID, defendant.getOfficerId());
//        assertEquals(ADDRESS.getHouseNameNumber(), defendant.getAddress().getHouseNameNumber());
//        assertEquals(ADDRESS.getStreet(), defendant.getAddress().getStreet());
//        assertEquals(ADDRESS.getArea(), defendant.getAddress().getArea());
//        assertEquals(ADDRESS.getPostTown(), defendant.getAddress().getPostTown());
//        assertEquals(ADDRESS.getRegion(), defendant.getAddress().getRegion());
//        assertEquals(ADDRESS.getCountry(), defendant.getAddress().getCountry());
//        assertEquals(ADDRESS.getPostCode(), defendant.getAddress().getPostCode());
//        assertEquals(PERSON_OFFICER_DETAILS.getDateOfBirth(), defendant.getPersonOfficerDetails().getDateOfBirth());
//        assertEquals(PERSON_OFFICER_DETAILS.getTitle(), defendant.getPersonOfficerDetails().getTitle());
//        assertEquals(PERSON_OFFICER_DETAILS.getForename(), defendant.getPersonOfficerDetails().getForename());
//        assertEquals(PERSON_OFFICER_DETAILS.getMiddleName(), defendant.getPersonOfficerDetails().getMiddleName());
//        assertEquals(PERSON_OFFICER_DETAILS.getSurname(), defendant.getPersonOfficerDetails().getSurname());
//        assertEquals(COMPANY_OFFICER_DETAILS.getCompanyName(), defendant.getCompanyOfficerDetails().getCompanyName());
//        assertEquals(APPOINTMENT_TYPE, defendant.getAppointmentType());
//        assertEquals(IS_CORPORATE_APPOINTMENT, defendant.getIsCorporateAppointment());
//    }
//
//    private DefendantApi createDefendant() {
//        DefendantApi defendant = new DefendantApi();
//
//        PERSON_OFFICER_DETAILS.setTitle("title");
//        PERSON_OFFICER_DETAILS.setForename("forename");
//        PERSON_OFFICER_DETAILS.setMiddleName("middlename");
//        PERSON_OFFICER_DETAILS.setSurname("surname");
//        PERSON_OFFICER_DETAILS.setDateOfBirth(LocalDate.now());
//
//        COMPANY_OFFICER_DETAILS.setCompanyName("companyName");
//
//        defendant.setOfficerId(OFFICER_ID);
//        defendant.setAddressApi(ADDRESS);
//        defendant.setPersonOfficerDetailsApi(PERSON_OFFICER_DETAILS);
//        defendant.setCompanyOfficerDetailsApi(COMPANY_OFFICER_DETAILS);
//        defendant.setDateAppointedOn(DATE_APPOINTED_ON);
//        defendant.setDateTerminatedOn(DATE_TERMINATED_ON);
//        defendant.setAppointmentType(APPOINTMENT_TYPE);
//        defendant.setIsCorporateAppointment(IS_CORPORATE_APPOINTMENT);
//
//        return defendant;
//    }
//}
