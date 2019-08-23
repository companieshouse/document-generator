package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.insolvency;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.model.common.Address;
import uk.gov.companieshouse.api.model.insolvency.PractitionerApi;
import uk.gov.companieshouse.api.model.insolvency.RoleApi;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.insolvency.items.Practitioner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith({MockitoExtension.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApiToPractitionerMapperTest {

    @InjectMocks
    private ApiToPractitionerMapper apiToPractitionerMapper = new ApiToPractitionerMapperImpl();

    private String NAME = "test name";
    private LocalDate DATE = LocalDate.of(2019,01,01);
    private String FORMATTED_DATE = "1 January 2019";
    private String ADDRESS_LINE_ONE = "address line one";
    private String ADDRESS_LINE_TWO = "address line two";
    private String CARE_OF = "care of";
    private String COUNTRY = "country";
    private String LOCALITY = "locality";
    private String PO_BOX = "po box";
    private String POSTAL_CODE = "postal code";
    private String PREMISES = "premises";
    private String REGION = "region";

    @Test
    @DisplayName("tests practitioner api data maps to practitioner model")
    void testApiToPractitionerMaps() {

        List<Practitioner> practitioners = apiToPractitionerMapper.apiToPractitionerMapper(createPractitionerApi());

        assertNotNull(practitioners);
        assertEquals(practitioners.get(0).getName(), NAME);
        assertEquals(practitioners.get(0).getAppointedOn(), FORMATTED_DATE);
        assertEquals(practitioners.get(0).getCeasedToActOn(), FORMATTED_DATE);
        assertEquals(practitioners.get(0).getAddress().getAddressLine1(), ADDRESS_LINE_ONE);
        assertEquals(practitioners.get(0).getAddress().getAddressLine2(), ADDRESS_LINE_TWO);
        assertEquals(practitioners.get(0).getAddress().getCareOf(), CARE_OF);
        assertEquals(practitioners.get(0).getAddress().getCountry(), COUNTRY);
        assertEquals(practitioners.get(0).getAddress().getLocality(), LOCALITY);
        assertEquals(practitioners.get(0).getAddress().getPoBox(), PO_BOX);
        assertEquals(practitioners.get(0).getAddress().getPostalCode(), POSTAL_CODE);
        assertEquals(practitioners.get(0).getAddress().getPremises(), PREMISES);
        assertEquals(practitioners.get(0).getAddress().getRegion(), REGION);
    }

    private List<PractitionerApi> createPractitionerApi() {

        List<PractitionerApi> practitionerApiList = new ArrayList<>();
        PractitionerApi practitionerApi = new PractitionerApi();

        practitionerApi.setName(NAME);
        practitionerApi.setCeasedToActOn(DATE);
        practitionerApi.setAppointedOn(DATE);

        Address address = new Address();
        address.setAddressLine1(ADDRESS_LINE_ONE);
        address.setAddressLine2(ADDRESS_LINE_TWO);
        address.setCareOf(CARE_OF);
        address.setCountry(COUNTRY);
        address.setLocality(LOCALITY);
        address.setPoBox(PO_BOX);
        address.setPostalCode(POSTAL_CODE);
        address.setPremises(PREMISES);
        address.setRegion(REGION);

        practitionerApi.setAddress(address);

        practitionerApiList.add(practitionerApi);

        return practitionerApiList;
    }


}
