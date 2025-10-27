package uk.gov.companieshouse.document.generator.company.report.mapping.mappers;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.model.ukestablishments.UkEstablishmentsItemsApi;
import uk.gov.companieshouse.document.generator.company.report.mapping.mappers.ukestablishment.ApiToUkEstablishmentMapper;
import uk.gov.companieshouse.document.generator.company.report.mapping.mappers.ukestablishment.ApiToUkEstablishmentMapperImpl;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.ukestablishment.UkEstablishment;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApiToUkEstablishmentMapperTest {

    private static final String COMPANY_NAME = "company name";
    private static final String COMPANY_NUMBER = "FC000005";
    private static final String COMPANY_STATUS = "company status";
    private static final String LOCALITY = "locality";

    @InjectMocks
    private ApiToUkEstablishmentMapper apiToUkEstablishmentMapper = new ApiToUkEstablishmentMapperImpl();

    @Test
    @DisplayName("test Uk establishments data maps to uk establishment model")
    void testApiToUkEstablishmentMaps() {

        UkEstablishment ukEstablishment =
            apiToUkEstablishmentMapper.apiToUkEstablishmentMapper(createUkEstablishmentApi());

        assertNotNull(ukEstablishment);
        assertEquals(COMPANY_NAME, ukEstablishment.getCompanyName());
        assertEquals(COMPANY_NUMBER, ukEstablishment.getCompanyNumber());
        assertEquals(COMPANY_STATUS, ukEstablishment.getCompanyStatus());
        assertEquals(LOCALITY, ukEstablishment.getLocality());
    }

    @Test
    @DisplayName("test a list of uk establishments data maps to uk establishment model")
    void testMultipleUkEstablishmentsMap() {

        List<UkEstablishmentsItemsApi> ukEstablishmentsItemsApiList = new ArrayList<>();

        ukEstablishmentsItemsApiList.add(createUkEstablishmentApi());
        ukEstablishmentsItemsApiList.add(createUkEstablishmentApi());
        ukEstablishmentsItemsApiList.add(createUkEstablishmentApi());

        List<UkEstablishment> ukEstablishmentList =
            apiToUkEstablishmentMapper.apiToUkEstablishmentMapper(ukEstablishmentsItemsApiList);

        assertNotNull(ukEstablishmentList);
        assertEquals(3, ukEstablishmentList.size());

        assertEquals(COMPANY_NAME, ukEstablishmentList.get(0).getCompanyName());
        assertEquals(COMPANY_NUMBER, ukEstablishmentList.get(0).getCompanyNumber());
        assertEquals(COMPANY_STATUS, ukEstablishmentList.get(0).getCompanyStatus());
        assertEquals(LOCALITY, ukEstablishmentList.get(0).getLocality());

        assertEquals(COMPANY_NAME, ukEstablishmentList.get(1).getCompanyName());
        assertEquals(COMPANY_NUMBER, ukEstablishmentList.get(1).getCompanyNumber());
        assertEquals(COMPANY_STATUS, ukEstablishmentList.get(1).getCompanyStatus());
        assertEquals(LOCALITY, ukEstablishmentList.get(1).getLocality());

        assertEquals(COMPANY_NAME, ukEstablishmentList.get(2).getCompanyName());
        assertEquals(COMPANY_NUMBER, ukEstablishmentList.get(2).getCompanyNumber());
        assertEquals(COMPANY_STATUS, ukEstablishmentList.get(2).getCompanyStatus());
        assertEquals(LOCALITY, ukEstablishmentList.get(2).getLocality());
    }

    @Test
    @DisplayName("test uk establishment with null value to uk establishment api model")
    void testApiToUkEstablishmentMapsWithNullApiModel() {

        UkEstablishmentsItemsApi ukEstablishmentsItemsApi = null;

        UkEstablishment ukEstablishment =
            apiToUkEstablishmentMapper.apiToUkEstablishmentMapper(ukEstablishmentsItemsApi);

        assertNull(ukEstablishment);
    }

    private UkEstablishmentsItemsApi createUkEstablishmentApi() {

        UkEstablishmentsItemsApi ukEstablishmentsItemsApi = new UkEstablishmentsItemsApi();
        ukEstablishmentsItemsApi.setCompanyName(COMPANY_NAME);
        ukEstablishmentsItemsApi.setCompanyNumber(COMPANY_NUMBER);
        ukEstablishmentsItemsApi.setCompanyStatus(COMPANY_STATUS);
        ukEstablishmentsItemsApi.setLocality(LOCALITY);

        return ukEstablishmentsItemsApi;
    }
}
