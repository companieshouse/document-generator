package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.companyregisters;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.model.registers.RegisterItemsApi;
import uk.gov.companieshouse.document.generator.common.descriptions.RetrieveApiEnumerationDescription;
import uk.gov.companieshouse.document.generator.company.report.mapping.mappers.registers.ApiToRegisterItems;
import uk.gov.companieshouse.document.generator.company.report.mapping.mappers.registers.ApiToRegisterItemsImpl;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.registers.items.RegisterItems;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApiToRegisterItemsTest {

    private static final String REGISTER_MOVED_TO_MAPPED_VALUE = "Register moved to mapped value psc";
    private static final LocalDate MOVED_ON = LocalDate.of(2016,01,01);
    private static final String MOVED_ON_CONVERTED = "1 January 2016";

    @Mock
    private RetrieveApiEnumerationDescription mockRetrieveApiEnumerations;

    @InjectMocks
    private ApiToRegisterItems apiToRegisterItems = new ApiToRegisterItemsImpl();

    @Test
    @DisplayName("tests single register items data maps to register items model")
    void testApiToRegisterItemsMaps() {

        when(mockRetrieveApiEnumerations.getApiEnumerationDescription(anyString(), anyString(),
            anyString(), any())).thenReturn(REGISTER_MOVED_TO_MAPPED_VALUE);

        RegisterItems registerItems =
            apiToRegisterItems.apiToRegister(createRegisterItemsApi());

        assertNotNull(registerItems);
        assertEquals(REGISTER_MOVED_TO_MAPPED_VALUE, registerItems.getRegisterMovedTo());
        assertEquals(MOVED_ON_CONVERTED, registerItems.getMovedOn());
    }

    @Test
    @DisplayName("test list of register items data maps to register items model")
    void testApiToRegisterItemsListMaps() {

        when(mockRetrieveApiEnumerations.getApiEnumerationDescription(anyString(), anyString()
            , anyString(), any())).thenReturn(REGISTER_MOVED_TO_MAPPED_VALUE);

        List<RegisterItemsApi> registerItemsApiList = createRegisterItemsList();
        List<RegisterItems> registerItems = apiToRegisterItems.apiToRegister(registerItemsApiList);

        assertNotNull(registerItems);
        assertEquals(REGISTER_MOVED_TO_MAPPED_VALUE, registerItems.get(0).getRegisterMovedTo());
        assertEquals(MOVED_ON_CONVERTED, registerItems.get(0).getMovedOn());

        assertEquals(REGISTER_MOVED_TO_MAPPED_VALUE, registerItems.get(1).getRegisterMovedTo());
        assertEquals(MOVED_ON_CONVERTED, registerItems.get(1).getMovedOn());
    }

    @Test
    @DisplayName("tests register items api with null value data maps to register items model")
    void testApiToRegisterItemsWithNullValue() {

        RegisterItemsApi registerItemsApi = null;
        RegisterItems registerItems = apiToRegisterItems. apiToRegister(registerItemsApi);

        assertEquals(null, registerItems);
    }

    private RegisterItemsApi createRegisterItemsApi() {

        RegisterItemsApi registerItemsApi = new RegisterItemsApi();
        registerItemsApi.setRegisterMovedTo(REGISTER_MOVED_TO_MAPPED_VALUE);
        registerItemsApi.setMovedOn(MOVED_ON);

        return registerItemsApi;
    }

    private List<RegisterItemsApi> createRegisterItemsList() {

        List<RegisterItemsApi> registerItemsApiList = new ArrayList<>();

        RegisterItemsApi registerItemsApi1 = createRegisterItemsApi();
        RegisterItemsApi registerItemsApi2 = createRegisterItemsApi();

        registerItemsApiList.add(registerItemsApi1);
        registerItemsApiList.add(registerItemsApi2);

        return registerItemsApiList;
    }
}
