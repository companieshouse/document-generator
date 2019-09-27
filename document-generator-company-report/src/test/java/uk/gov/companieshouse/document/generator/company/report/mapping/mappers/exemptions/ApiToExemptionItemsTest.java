package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.exemptions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.model.exemptions.ExemptionItemsApi;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.exemptions.items.ExemptionItems;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith({MockitoExtension.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApiToExemptionItemsTest {

    private static String MOVED_ON_DATE_FORMATTED = "1 January 2017";
    private static LocalDate MOVED_ON = LocalDate.of(2017,01,01);

    @InjectMocks
    ApiToExemptionItems apiToExemptionItems = new ApiToExemptionItemsImpl();

    @Test
    @DisplayName("test formatted exempt from date")
    void testFormattedExemptFromDate() {

        List<ExemptionItemsApi> exemptionItemsApi = createExemptionItemsApiList();
        List<ExemptionItems> exemptionItems = apiToExemptionItems.apiToExemptionItems(exemptionItemsApi);

        assertNotNull(exemptionItems);
        assertEquals(MOVED_ON_DATE_FORMATTED, exemptionItems.get(0).getExemptFrom());
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

}
