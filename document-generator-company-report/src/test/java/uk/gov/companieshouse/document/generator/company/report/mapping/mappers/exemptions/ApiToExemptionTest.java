package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.exemptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.model.exemptions.ExemptionApi;
import uk.gov.companieshouse.api.model.exemptions.ExemptionItemsApi;
import uk.gov.companieshouse.document.generator.common.descriptions.RetrieveApiEnumerationDescription;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.exemptions.items.Exemption;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.exemptions.items.ExemptionItems;

@ExtendWith({MockitoExtension.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApiToExemptionTest {

    private static String EXEMPTION_TYPE = "exemption type";
    private static String MOVED_ON_DATE_FORMATTED = "1 January 2017";
    private static LocalDate MOVED_ON = LocalDate.of(2016,01,01);

    @Mock
    private RetrieveApiEnumerationDescription mockRetrieveApiEnumerationDescription;

    @Mock
    private ApiToExemptionItems mockApiToExemptionItems;

    @InjectMocks
    private  ApiToExemption apiToExemption = new ApiToExemptionImpl();

    @Test
    @DisplayName("tests exemption type description")
    void testExemptionTypeDescription(){

        ExemptionApi exemptionApi = createExemptionApi();

        when(mockRetrieveApiEnumerationDescription.getApiEnumerationDescription(anyString(),
            anyString(), anyString(), any())).thenReturn(EXEMPTION_TYPE);

        Exemption exemption = apiToExemption.apiToExemption(exemptionApi);

        assertNotNull(exemption);
        assertEquals(EXEMPTION_TYPE, exemption.getExemptionType());
    }

    @Test
    @DisplayName("tests formatted move on date set")
    void testFormattedMoveOnDate(){

        ExemptionApi exemptionApi = createExemptionApi();

        when(mockApiToExemptionItems.apiToExemptionItems(exemptionApi.getItems())).thenReturn(createExemptionItems());

        when(mockRetrieveApiEnumerationDescription.getApiEnumerationDescription(anyString(),
            anyString(), anyString(), any())).thenReturn(EXEMPTION_TYPE);

        Exemption exemption = apiToExemption.apiToExemption(exemptionApi);

        assertNotNull(exemption);
        assertEquals(MOVED_ON_DATE_FORMATTED, exemption.getMovedOnDateFormatted());
    }

    private ExemptionApi createExemptionApi() {

        ExemptionApi exemptionApi = new ExemptionApi();

        exemptionApi.setExemptionType(EXEMPTION_TYPE);
        exemptionApi.setItems(createExemptionItemsApiList());

        return exemptionApi;
    }

    private List<ExemptionItemsApi> createExemptionItemsApiList() {

        List<ExemptionItemsApi> exemptionItemsApiList = new ArrayList<>();

        ExemptionItemsApi exemptionItemsApi = createExemptionItemApi();
        exemptionItemsApiList.add(exemptionItemsApi);

        return exemptionItemsApiList;
    }

    private ExemptionItemsApi createExemptionItemApi() {

        ExemptionItemsApi exemptionItem = new ExemptionItemsApi();
        exemptionItem.setExemptFrom(MOVED_ON);

        return exemptionItem;
    }

    private List<ExemptionItems> createExemptionItems() {
        List<ExemptionItems> exemptionItems = new ArrayList<>();
        ExemptionItems exemptionItems1 = new ExemptionItems();

        exemptionItems1.setExemptFrom(MOVED_ON_DATE_FORMATTED);

        exemptionItems.add(exemptionItems1);
        return exemptionItems;
    }
}
