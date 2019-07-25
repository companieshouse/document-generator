package uk.gov.companieshouse.document.generator.company.report.mapping.mappers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.model.company.foreigncompany.ForeignCompanyDetailsApi;
import uk.gov.companieshouse.api.model.company.foreigncompany.OriginatingRegistryApi;
import uk.gov.companieshouse.api.model.company.foreigncompany.account.AccountPeriodApi;
import uk.gov.companieshouse.api.model.company.foreigncompany.account.ForeignAccountApi;
import uk.gov.companieshouse.api.model.company.foreigncompany.account.MustFileWithinApi;
import uk.gov.companieshouse.document.generator.company.report.mapping.mappers.foreigncompanydetails.ApiToForeignCompanyDetailsMapper;
import uk.gov.companieshouse.document.generator.company.report.mapping.mappers.foreigncompanydetails.ApiToForeignCompanyDetailsMapperImpl;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.foreigncompanydetails.ForeignCompanyDetails;

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
    private static final String ACCOUNT_PERIOD_FROM_DAY = "1";
    private static final String ACCOUNT_PERIOD_FROM_MONTH = "1";
    private static final String ACCOUNT_PERIOD_TO_DAY = "5";
    private static final String ACCOUNT_PERIOD_TO_MONTH = "3";
    private static final String FORMATTED_ACCOUNT_PERIOD_FROM_MONTH = "January";
    private static final String FORMATTED_ACCOUNT_PERIOD_TO_MONTH = "March";
    private static final Long MUST_FILE_WITHIN_MONTHS = 12L;


    @InjectMocks
    private ApiToForeignCompanyDetailsMapper apiToForeignCompanyDetailsMapper = new ApiToForeignCompanyDetailsMapperImpl();

    @Test
    @DisplayName("test foreign company details data maps to foreign company details model")
    void testApiToForeignCompanyDetailsMaps() {

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
        assertEquals(ACCOUNT_PERIOD_FROM_DAY, foreignCompanyDetails.getAccounts().getAccountPeriodFrom().getDay());
        assertEquals(FORMATTED_ACCOUNT_PERIOD_FROM_MONTH, foreignCompanyDetails.getAccounts().getAccountPeriodFrom().getMonth());
        assertEquals(ACCOUNT_PERIOD_TO_DAY, foreignCompanyDetails.getAccounts().getAccountPeriodTo().getDay());
        assertEquals(FORMATTED_ACCOUNT_PERIOD_TO_MONTH, foreignCompanyDetails.getAccounts().getAccountPeriodTo().getMonth());
        assertEquals(MUST_FILE_WITHIN_MONTHS, foreignCompanyDetails.getAccounts().getMustFileWithin().getMonths());
    }

    @Test
    @DisplayName("test foreign company with null value to foreign company details api model")
    void testApiToForeignCompanyDetailsMapsWithNullApiModel() {

        ForeignCompanyDetailsApi foreignCompanyDetailsApi = null;

        ForeignCompanyDetails foreignCompanyDetails =
            apiToForeignCompanyDetailsMapper.apiToForeignCompanyDetails(foreignCompanyDetailsApi);

        assertNull(foreignCompanyDetails);
    }

    @Test
    @DisplayName("test foreign company details data maps with null values to foreign company details model")
    void testApiToForeignCompanyDetailsMapsWithNullValues() {

        ForeignCompanyDetailsApi foreignCompanyDetailsApi = createForeignCompanyDetailsApiWithNullValues();

        ForeignCompanyDetails foreignCompanyDetails =
            apiToForeignCompanyDetailsMapper.apiToForeignCompanyDetails(foreignCompanyDetailsApi);

        assertNotNull(foreignCompanyDetails);
        assertEquals(null, foreignCompanyDetails.getCountry());
        assertEquals(null, foreignCompanyDetails.getName());
    }

    @Test
    @DisplayName("test foreign company details data maps to foreign company details model")
    void testApiToForeignCompanyDetailsMapsWithNullOriginatingRegistryObject() {

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
        foreignCompanyDetailsApi.setAccounts(createForeignAccountApi());

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

    private ForeignAccountApi createForeignAccountApi(){

        ForeignAccountApi foreignAccountApi = new ForeignAccountApi();
        AccountPeriodApi accountPeriodApiFrom = new AccountPeriodApi();
        AccountPeriodApi accountPeriodApiTo = new AccountPeriodApi();
        MustFileWithinApi mustFileWithinApi = new MustFileWithinApi();

        accountPeriodApiFrom.setDay(ACCOUNT_PERIOD_FROM_DAY);
        accountPeriodApiFrom.setMonth(ACCOUNT_PERIOD_FROM_MONTH);
        accountPeriodApiTo.setDay(ACCOUNT_PERIOD_TO_DAY);
        accountPeriodApiTo.setMonth(ACCOUNT_PERIOD_TO_MONTH);
        mustFileWithinApi.setMonths(MUST_FILE_WITHIN_MONTHS);

        foreignAccountApi.setAccountPeriodFrom(accountPeriodApiFrom);
        foreignAccountApi.setAccountPeriodTo(accountPeriodApiTo);
        foreignAccountApi.setMustFileWithin(mustFileWithinApi);

        return foreignAccountApi;
    }
}
