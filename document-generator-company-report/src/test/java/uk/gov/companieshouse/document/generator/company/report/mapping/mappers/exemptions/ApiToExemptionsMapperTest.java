package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.exemptions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.model.exemptions.ExemptionApi;
import uk.gov.companieshouse.api.model.exemptions.ExemptionItemsApi;
import uk.gov.companieshouse.api.model.exemptions.ExemptionsApi;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.exemptions.Exemptions;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.exemptions.items.Exemption;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApiToExemptionsMapperTest {

    private static LocalDate EXEMPT_FROM = LocalDate.of(2016,01,01);
    private static String EXEMPTION_TYPE = "exemption type";

    @InjectMocks
    private ApiToExemptionsMapper apiToExemptionsMapper = new ApiToExemptionsMapperImpl();

    @Mock
    private ApiToExemption mockApiToExemption;

    @Test
    @DisplayName("tests exemptions maps to model")
    void testExemptionMaps() {

        when(mockApiToExemption.apiToExemption(any(ExemptionApi.class))).thenReturn(new Exemption());

        Exemptions exemptions = apiToExemptionsMapper.apiToExemptionsMapper(createExemptionsApi());
        exemptions.setActiveExemption(true);
        assertNotNull(exemptions);
    }

    private ExemptionsApi createExemptionsApi() {

        ExemptionsApi exemptionsApi = new ExemptionsApi();

        exemptionsApi.setDisclosureTransparencyRulesChapterFiveApplies(createExemptionApi());
        exemptionsApi.setPscExemptAsSharesAdmittedOnMarket(createExemptionApi());
        exemptionsApi.setPscExemptAsTradingOnRegulatedMarket(createExemptionApi());
        exemptionsApi.setPscExemptAsTradingOnUkRegulatedMarket(createExemptionApi());


        return exemptionsApi;
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
            exemptionItem.setExemptFrom(EXEMPT_FROM);

            return exemptionItem;
        }

}


