package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.exemptions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.model.exemptions.ExemptionApi;
import uk.gov.companieshouse.api.model.exemptions.ExemptionsApi;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.exemptions.Exemptions;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.exemptions.items.Exemption;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApiToExemptionsMapperTest {

    @InjectMocks
    private ApiToExemptionsMapper apiToExemptionsMapper = new ApiToExemptionsMapperImpl();

    @Mock
    private ApiToExemption mockApiToExemption;

    @Test
    @DisplayName("tests exemptions maps to model")
    void testExemptionMaps(){

        when(mockApiToExemption.apiToExemption(any(ExemptionApi.class))).thenReturn(new Exemption());

        Exemptions exemptions = apiToExemptionsMapper.apiToExemptionsMapper(createExemptionsApi());

        assertNotNull(exemptions);
    }

    private ExemptionsApi createExemptionsApi() {

        ExemptionsApi exemptionsApi = new ExemptionsApi();

        exemptionsApi.setDisclosureTransparencyRulesChapterFiveApplies(new ExemptionApi());
        exemptionsApi.setPscExemptAsSharesAdmittedOnMarket(new ExemptionApi());
        exemptionsApi.setPscExemptAsTradingOnRegulatedMarket(new ExemptionApi());
        exemptionsApi.setPscExemptAsTradingOnUkRegulatedMarket(new ExemptionApi());

        return exemptionsApi;
    }
}
