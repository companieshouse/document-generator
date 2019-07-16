package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.pscs;

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
import uk.gov.companieshouse.api.model.common.Address;
import uk.gov.companieshouse.api.model.common.DateOfBirth;
import uk.gov.companieshouse.api.model.psc.PscApi;
import uk.gov.companieshouse.document.generator.common.descriptions.RetrieveApiEnumerationDescription;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.pscs.items.Psc;

@ExtendWith({MockitoExtension.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApiToPscMapperTest {


    @InjectMocks
    private ApiToPscMapper apiToPscMapper = new ApiToPscMapperImpl();

    private static final String NAME = "name";
    private static final LocalDate CEASED_ON = LocalDate.of(
            2019, 06, 06);
    private static final LocalDate NOTIFIED_ON = LocalDate.of(
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
    private static final String NATIONALITY = "nationality";
    private static final Long MONTH = 1L;
    private static final Long YEAR = 1993L;
    private static final String LEGAL_AUTHORITY = "legal authority";
    private static final String LEGAL_FORM = "legal form";
    private static final String PLACE_REGISTRATION = "place registration";
    private static final String REGISTRATION_NUMBER = "registration number";
    private static final String KIND = "kind";

    private String[] NATURE_OF_CONTROL = new String[]{"test1", "test2", "test3"};

    @Mock
   RetrieveApiEnumerationDescription mockRetrieveApiEnumerations;


    @Test
    @DisplayName("tests single PSC data maps to PSC model")
    void testApiToPSCMaps() throws Exception {

        PscApi pscApi = createPscApi();


        when(mockRetrieveApiEnumerations.getApiEnumerationDescription(anyString(), anyString(),
 anyString(), any())).thenReturn("mapped value");

        Psc psc = apiToPscMapper.apiToPsc(pscApi);

        assertNotNull(psc);

        assertEquals(NAME, psc.getName());
        assertEquals(ADDRESS_LINE_ONE, psc.getAddress().getAddressLine1());
        assertEquals(ADDRESS_LINE_TWO, psc.getAddress().getAddressLine2());
        assertEquals(CARE_OF, psc.getAddress().getCareOf());
        assertEquals(COUNTRY, psc.getAddress().getCountry());
        assertEquals(LOCALITY, psc.getAddress().getLocality());
        assertEquals(PO_BOX, psc.getAddress().getPoBox());
        assertEquals(POSTAL_CODE, psc.getAddress().getPostalCode());
        assertEquals(REGION, psc.getAddress().getRegion());
        assertEquals(PREMISE, psc.getAddress().getPremises());
        assertEquals(1L, psc.getDateOfBirth().getMonth().longValue());
        assertEquals(1993L, psc.getDateOfBirth().getYear().longValue());
        assertEquals(COUNTRY_OF_RESIDENCE, psc.getCountryOfResidence());
//        assertEquals(2019-06-06, psc.getCeasedOn());
//        assertEquals(2019-05-05, psc.getNotifiedOn());
        assertEquals(COUNTRY, psc.getIdentification().getCountryRegistered());
        assertEquals(LEGAL_AUTHORITY, psc.getIdentification().getLegalAuthority());
        assertEquals(LEGAL_FORM, psc.getIdentification().getLegalForm());
        assertEquals(PLACE_REGISTRATION, psc.getIdentification().getPlaceRegistered());
        assertEquals(REGISTRATION_NUMBER, psc.getIdentification().getRegistrationNumber());
        assertEquals("mapped value", psc.getNaturesOfControl().get(0).getNaturesOfControlDescription());
        assertEquals("mapped value", psc.getNaturesOfControl().get(1).getNaturesOfControlDescription());
        assertEquals("mapped value", psc.getNaturesOfControl().get(2).getNaturesOfControlDescription());
    }

    private PscApi createPscApi() {
        PscApi pscApi = new PscApi();

        pscApi.setAddress(createAddress());
        pscApi.setDateOfBirth(createDateOfBirth());
        pscApi.setCeasedOn(CEASED_ON);
        pscApi.setCountryOfResidence(COUNTRY_OF_RESIDENCE);
        pscApi.setIdentification(createIdentification());
        pscApi.setNaturesOfControl(NATURE_OF_CONTROL);
        ;
        pscApi.setNotifiedOn(NOTIFIED_ON);
        pscApi.setNationality(NATIONALITY);
        pscApi.setName(NAME);

        return pscApi;
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

    private uk.gov.companieshouse.api.model.psc.Identification createIdentification() {
        uk.gov.companieshouse.api.model.psc.Identification identification =
                new uk.gov.companieshouse.api.model.psc.Identification();

        identification.setCountryRegistered(COUNTRY);
        identification.setLegalAuthority(LEGAL_AUTHORITY);
        identification.setLegalForm(LEGAL_FORM);
        identification.setPlaceRegistered(PLACE_REGISTRATION);
        identification.setRegistrationNumber(REGISTRATION_NUMBER);

        return identification;
    }
}