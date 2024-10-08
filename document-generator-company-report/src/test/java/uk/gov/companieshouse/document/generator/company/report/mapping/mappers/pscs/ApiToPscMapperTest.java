package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.pscs;

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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ApiToPscMapperTest {

    private static final String MAPPED_VALUE = "mapped value";
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
    private static final boolean IS_SANCTIONED = true;

    private final String[] NATURE_OF_CONTROL = new String[]{"test1", "test2", "test3"};

    private static final String SUPER_SECURE_PERSON_WITH_SIGNIFICANT_CONTROL_KIND = "super-secure-person-with-significant-control";
    private static final String SUPER_SECURE_BENEFICIAL_OWNER_KIND = "super-secure-beneficial-owner";


    @InjectMocks
    private ApiToPscMapper apiToPscMapper = new ApiToPscMapperImpl();

    @Mock
    RetrieveApiEnumerationDescription mockRetrieveApiEnumerations;

    @Test
    @DisplayName("tests multiple PSC data maps to PSC model")
    void testApiToMultiplePSCMaps() {

        List<PscApi> pscList = createPscList();

        List<Psc> psc = apiToPscMapper.apiToPsc(pscList);

        assertNotNull(psc);
        assertEquals(NAME, psc.get(0).getName());
        assertEquals("6 June 2019", psc.get(1).getCeasedOn());
        assertEquals("5 May 2019", psc.get(2).getNotifiedOn());

    }

    @Test
    @DisplayName("tests single PSC data maps to PSC model")
    void testApiToPSCMaps() {

        PscApi pscApi = createPscApiWithKind(SUPER_SECURE_PERSON_WITH_SIGNIFICANT_CONTROL_KIND);

        when(mockRetrieveApiEnumerations.getApiEnumerationDescription(anyString(), anyString(),
                anyString(), any())).thenReturn(MAPPED_VALUE);

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
        assertEquals("January", psc.getDateOfBirth().getMonth());
        assertEquals(1993L, psc.getDateOfBirth().getYear().longValue());
        assertEquals(COUNTRY_OF_RESIDENCE, psc.getCountryOfResidence());
        assertEquals("6 June 2019", psc.getCeasedOn());
        assertEquals("5 May 2019", psc.getNotifiedOn());
        assertEquals(LEGAL_AUTHORITY, psc.getIdentification().getLegalAuthority());
        assertEquals(LEGAL_FORM, psc.getIdentification().getLegalForm());
        assertEquals(PLACE_REGISTRATION, psc.getIdentification().getPlaceRegistered());
        assertEquals(REGISTRATION_NUMBER, psc.getIdentification().getRegistrationNumber());
        assertEquals(MAPPED_VALUE,
                psc.getNaturesOfControl().get(0).getNaturesOfControlDescription());
        assertEquals(MAPPED_VALUE,
                psc.getNaturesOfControl().get(1).getNaturesOfControlDescription());
        assertEquals(MAPPED_VALUE,
                psc.getNaturesOfControl().get(2).getNaturesOfControlDescription());

        assertEquals(MAPPED_VALUE, psc.getSuperSecureDescription());
    }

    @Test
    @DisplayName("beneficial owner fields are mapped correctly to model for ROE")
    void testApiToPscMapsForBeneficialOwners() {

        final PscApi pscApi = createPscApiWithKind(SUPER_SECURE_BENEFICIAL_OWNER_KIND);

        when(mockRetrieveApiEnumerations.getApiEnumerationDescription(anyString(), anyString(),
                anyString(), any())).thenReturn(MAPPED_VALUE);

        final Psc psc = apiToPscMapper.apiToPsc(pscApi);

        assertNotNull(psc);

        assertEquals(IS_SANCTIONED, psc.isSanctioned());

        assertEquals(ADDRESS_LINE_ONE, psc.getPrincipalOfficeAddress().getAddressLine1());
        assertEquals(ADDRESS_LINE_TWO, psc.getPrincipalOfficeAddress().getAddressLine2());
        assertEquals(CARE_OF, psc.getPrincipalOfficeAddress().getCareOf());
        assertEquals(COUNTRY, psc.getPrincipalOfficeAddress().getCountry());
        assertEquals(LOCALITY, psc.getPrincipalOfficeAddress().getLocality());
        assertEquals(PO_BOX, psc.getPrincipalOfficeAddress().getPoBox());
        assertEquals(POSTAL_CODE, psc.getPrincipalOfficeAddress().getPostalCode());
        assertEquals(REGION, psc.getPrincipalOfficeAddress().getRegion());
        assertEquals(PREMISE, psc.getPrincipalOfficeAddress().getPremises());

        assertEquals(MAPPED_VALUE, psc.getSuperSecureDescription());
    }

    private PscApi createPscApiWithKind(final String kind) {
        final PscApi pscApi = new PscApi();

        final Address address = createAddress();
        pscApi.setAddress(address);
        pscApi.setDateOfBirth(createDateOfBirth());
        pscApi.setCeasedOn(CEASED_ON);
        pscApi.setCountryOfResidence(COUNTRY_OF_RESIDENCE);
        pscApi.setIdentification(createIdentification());
        pscApi.setNaturesOfControl(NATURE_OF_CONTROL);
        pscApi.setNotifiedOn(NOTIFIED_ON);
        pscApi.setNationality(NATIONALITY);
        pscApi.setName(NAME);

        pscApi.setSanctioned(IS_SANCTIONED);
        pscApi.setPrincipalOfficeAddress(address);
        pscApi.setKind(kind);

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

    private List<PscApi> createPscList() {
        List<PscApi> pscList = new ArrayList<>();

        PscApi psc1 = new PscApi();
        psc1.setName(NAME);
        PscApi psc2 = new PscApi();
        psc2.setCeasedOn(CEASED_ON);
        PscApi psc3 = new PscApi();
        psc3.setNotifiedOn(NOTIFIED_ON);

        pscList.add(psc1);
        pscList.add(psc2);
        pscList.add(psc3);
        return pscList;
    }
}