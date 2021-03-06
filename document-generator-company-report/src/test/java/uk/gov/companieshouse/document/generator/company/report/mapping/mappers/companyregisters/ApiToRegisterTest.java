package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.companyregisters;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.model.registers.RegisterApi;
import uk.gov.companieshouse.api.model.registers.RegisterItemsApi;
import uk.gov.companieshouse.document.generator.common.descriptions.RetrieveApiEnumerationDescription;
import uk.gov.companieshouse.document.generator.company.report.mapping.mappers.registers.ApiToRegister;
import uk.gov.companieshouse.document.generator.company.report.mapping.mappers.registers.ApiToRegisterImpl;
import uk.gov.companieshouse.document.generator.company.report.mapping.mappers.registers.ApiToRegisterItems;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.registers.items.Register;
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
public class ApiToRegisterTest {

    private static final String INFORMATION_SENTENCE= "moved to place";
    private static final String REGISTER_TYPE = "register type";
    private static final String MOVED_TO = "moved to place";
    private static final String MOVED_ON_STRING = "11 January 2017";
    private static final String REGISTER_MOVED_TO_MAPPED_VALUE = "register moved to mapped value";
    private static final String FORMATTED_DATE = "11 January 2017 - 11 January 2017";
    private static final LocalDate MOVED_ON = LocalDate.of(2016,01,01);

    @Mock
    private RetrieveApiEnumerationDescription mockRetrieveApiEnumerationDescription;

    @Mock
    private ApiToRegisterItems mockApiToRegisterItems;

    @InjectMocks
    private ApiToRegister apiToRegister = new ApiToRegisterImpl();

    @Test
    @DisplayName("tests information sentence")
    void testInformationSentence() {

        RegisterApi registerApi = createRegisterApi();
        when(mockApiToRegisterItems.apiToRegister(registerApi.getItems())).thenReturn(createRegisterItems());

        when(mockRetrieveApiEnumerationDescription.getApiEnumerationDescription(anyString(), anyString(),
            anyString(), any())).thenReturn(REGISTER_TYPE);

        Register register = apiToRegister.apiToRegister(registerApi);

        assertNotNull(register);
        assertEquals(INFORMATION_SENTENCE, register.getInformation());
    }

    @Test
    @DisplayName("test formatted date")
    void testFormattedDate(){

        RegisterApi registerApi = createRegisterApi();
        when(mockApiToRegisterItems.apiToRegister(registerApi.getItems())).thenReturn(createRegisterItems());

        when(mockRetrieveApiEnumerationDescription.getApiEnumerationDescription(anyString(), anyString(),
            anyString(), any())).thenReturn(REGISTER_TYPE);

        Register register = apiToRegister.apiToRegister(registerApi);

        assertNotNull(register);
        assertEquals(FORMATTED_DATE, register.getItems().get(1).getFormattedDate());
    }

    private RegisterApi createRegisterApi() {

        RegisterApi registerApi = new RegisterApi();

        registerApi.setRegisterType(REGISTER_TYPE);
        registerApi.setItems(createRegisterItemsApiList());

        return registerApi;
    }

    private RegisterItemsApi createRegisterItem() {

        RegisterItemsApi registerItem = new RegisterItemsApi();
        registerItem.setRegisterMovedTo(REGISTER_MOVED_TO_MAPPED_VALUE);
        registerItem.setMovedOn(MOVED_ON);

        return registerItem;
    }

    private List<RegisterItemsApi> createRegisterItemsApiList() {

        List<RegisterItemsApi> registerItemsApiList = new ArrayList<>();

        RegisterItemsApi registerItemsApi = createRegisterItem();
        registerItemsApiList.add(registerItemsApi);

        return registerItemsApiList;
    }

    private List<RegisterItems> createRegisterItems() {
        List<RegisterItems> registerItems = new ArrayList<>();

        RegisterItems registerItem1 = new RegisterItems();
        RegisterItems registerItem2 = new RegisterItems();

        registerItem1.setRegisterMovedTo(MOVED_TO);
        registerItem1.setMovedOn(MOVED_ON_STRING);

        registerItem2.setRegisterMovedTo(MOVED_TO);
        registerItem2.setMovedOn(MOVED_ON_STRING);

        registerItems.add(registerItem1);
        registerItems.add(registerItem2);
        return registerItems;
    }
}
