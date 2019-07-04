package uk.gov.companieshouse.document.generator.company.report.mapping.mappers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.model.company.foreigncompany.ForeignCompanyDetailsApi;
import uk.gov.companieshouse.api.model.company.foreigncompany.OriginatingRegistryApi;
import uk.gov.companieshouse.document.generator.company.report.mapping.mappers.foreigncompanydetails.ApiToForeignCompanyDetailsMapper;
import uk.gov.companieshouse.document.generator.company.report.mapping.mappers.foreigncompanydetails.ApiToForeignCompanyDetailsMapperImpl;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.foreigncompanydetails.ForeignCompanyDetails;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith({MockitoExtension.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApiToForeignCompanyDetailsTest {

    private static final String COUNTRY = "france";
    private static final String REGISTRATION_NUMBER = "FN000000H";
    private static final String LEGAL_FORM = "public limited company";
    private static final String NAME = "parent registry";
    private static final String GOVERNED_BY = "governed by";
    private static final String BUSINESS_ACTIVITY = "business activity";


    @InjectMocks
    private ApiToForeignCompanyDetailsMapper apiToForeignCompanyDetailsMapper = new ApiToForeignCompanyDetailsMapperImpl();

    @Test
    @DisplayName("test foreign company details data maps to foreign company details model")
    void testApiToForeignCompanyDetailsMaps() throws IOException {

        ForeignCompanyDetailsApi foreignCompanyDetailsApi = createForeignCompanyDetailsApi();

        ForeignCompanyDetails foreignCompanyDetails =
            apiToForeignCompanyDetailsMapper.apiToForeignCompanyDetails(foreignCompanyDetailsApi);

        assertNotNull(foreignCompanyDetails);
        assertEquals(COUNTRY, foreignCompanyDetails.getCountry());
        assertEquals(REGISTRATION_NUMBER, foreignCompanyDetails.getRegistrationNumber());
        assertEquals(LEGAL_FORM, foreignCompanyDetails.getLegalForm());
        assertEquals(NAME, foreignCompanyDetails.getName());
        assertEquals(GOVERNED_BY, foreignCompanyDetails.getGovernedBy());
        assertEquals(BUSINESS_ACTIVITY, foreignCompanyDetails.getBusinessActivity());
    }

    @Test
    @DisplayName("test foreign company with null value to foreign company details api model")
    void testApiToForeignCompanyDetailsMapsWithNullApiModel() throws IOException {

        ForeignCompanyDetailsApi foreignCompanyDetailsApi = null;

        ForeignCompanyDetails foreignCompanyDetails =
            apiToForeignCompanyDetailsMapper.apiToForeignCompanyDetails(foreignCompanyDetailsApi);

        assertNull(foreignCompanyDetails);
    }

    @Test
    @DisplayName("test foreign company details data maps with null values to foreign company details model")
    void testApiToForeignCompanyDetailsMapsWithNullValues() throws IOException {

        ForeignCompanyDetailsApi foreignCompanyDetailsApi = createForeignCompanyDetailsApiWithNullValues();

        ForeignCompanyDetails foreignCompanyDetails =
            apiToForeignCompanyDetailsMapper.apiToForeignCompanyDetails(foreignCompanyDetailsApi);

        assertNotNull(foreignCompanyDetails);
        assertEquals(null, foreignCompanyDetails.getCountry());
        assertEquals(null, foreignCompanyDetails.getName());
    }

    @Test
    @DisplayName("test foreign company details data maps to foreign company details model")
    void testApiToForeignCompanyDetailsMapsWithNullOriginatingRegistryObject() throws IOException {

        ForeignCompanyDetailsApi foreignCompanyDetailsApi =
            createForeignCompanyDetailsApiWithNullOriginatingRegistryObject();

        ForeignCompanyDetails foreignCompanyDetails =
            apiToForeignCompanyDetailsMapper.apiToForeignCompanyDetails(foreignCompanyDetailsApi);

        assertNotNull(foreignCompanyDetails);
        assertEquals(null, foreignCompanyDetails.getCountry());
        assertEquals(REGISTRATION_NUMBER, foreignCompanyDetails.getRegistrationNumber());
        assertEquals(LEGAL_FORM, foreignCompanyDetails.getLegalForm());
        assertEquals(null, foreignCompanyDetails.getName());
        assertEquals(GOVERNED_BY, foreignCompanyDetails.getGovernedBy());
        assertEquals(BUSINESS_ACTIVITY, foreignCompanyDetails.getBusinessActivity());
    }

    private ForeignCompanyDetailsApi createForeignCompanyDetailsApi() {

        ForeignCompanyDetailsApi foreignCompanyDetailsApi = new ForeignCompanyDetailsApi();

        foreignCompanyDetailsApi.setRegistrationNumber(REGISTRATION_NUMBER);
        foreignCompanyDetailsApi.setLegalForm(LEGAL_FORM);
        foreignCompanyDetailsApi.setGovernedBy(GOVERNED_BY);
        foreignCompanyDetailsApi.setBusinessActivity(BUSINESS_ACTIVITY);
        foreignCompanyDetailsApi.setOriginatingRegistry(createOriginatingRegistryApi());

        return foreignCompanyDetailsApi;
    }

    private ForeignCompanyDetailsApi createForeignCompanyDetailsApiWithNullValues() {

        ForeignCompanyDetailsApi foreignCompanyDetailsApi = new ForeignCompanyDetailsApi();
        OriginatingRegistryApi originatingRegistryApi = new OriginatingRegistryApi();

        originatingRegistryApi.setCountry(null);
        originatingRegistryApi.setName(null);
        foreignCompanyDetailsApi.setOriginatingRegistry(originatingRegistryApi);

        return foreignCompanyDetailsApi;
    }

    private ForeignCompanyDetailsApi createForeignCompanyDetailsApiWithNullOriginatingRegistryObject() {

        ForeignCompanyDetailsApi foreignCompanyDetailsApi = new ForeignCompanyDetailsApi();

        foreignCompanyDetailsApi.setRegistrationNumber(REGISTRATION_NUMBER);
        foreignCompanyDetailsApi.setLegalForm(LEGAL_FORM);
        foreignCompanyDetailsApi.setGovernedBy(GOVERNED_BY);
        foreignCompanyDetailsApi.setBusinessActivity(BUSINESS_ACTIVITY);
        foreignCompanyDetailsApi.setOriginatingRegistry(null);

        return foreignCompanyDetailsApi;
    }

    private OriginatingRegistryApi createOriginatingRegistryApi(){

        OriginatingRegistryApi originatingRegistryApi = new OriginatingRegistryApi();

        originatingRegistryApi.setCountry(COUNTRY);
        originatingRegistryApi.setName(NAME);

        return originatingRegistryApi;
    }
}
