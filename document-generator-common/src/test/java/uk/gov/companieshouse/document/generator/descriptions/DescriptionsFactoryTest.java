package uk.gov.companieshouse.document.generator.descriptions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.document.generator.common.descriptions.yml.Descriptions;
import uk.gov.companieshouse.document.generator.common.descriptions.yml.DescriptionsFactory;
import uk.gov.companieshouse.document.generator.common.descriptions.yml.impl.Constants;
import uk.gov.companieshouse.document.generator.common.descriptions.yml.impl.DisqualifiedOfficerDescriptions;
import uk.gov.companieshouse.document.generator.common.descriptions.yml.impl.Errors;
import uk.gov.companieshouse.document.generator.common.descriptions.yml.impl.ExemptionDescriptions;
import uk.gov.companieshouse.document.generator.common.descriptions.yml.impl.FilingDescriptions;
import uk.gov.companieshouse.document.generator.common.descriptions.yml.impl.FilingHistoryDescriptions;
import uk.gov.companieshouse.document.generator.common.descriptions.yml.impl.FilingHistoryExceptions;
import uk.gov.companieshouse.document.generator.common.descriptions.yml.impl.MortgageDescriptions;
import uk.gov.companieshouse.document.generator.common.descriptions.yml.impl.PscDescriptions;
import uk.gov.companieshouse.document.generator.common.descriptions.yml.impl.SearchDescriptionsRaw;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DescriptionsFactoryTest {

    @InjectMocks
    private DescriptionsFactory descriptionsFactory;

    @Mock
    private Constants mockConstants;

    @Mock
    private DisqualifiedOfficerDescriptions mockDisqualifiedOfficerDescriptions;

    @Mock
    private Errors mockErrors;

    @Mock
    private ExemptionDescriptions mockExemptionDescriptions;

    @Mock
    private FilingDescriptions mockFilingDescriptions;

    @Mock
    private FilingHistoryDescriptions mockFilingHistoryDescriptions;

    @Mock
    private FilingHistoryExceptions mockFilingHistoryExceptions;

    @Mock
    private MortgageDescriptions mockMortgageDescriptions;

    @Mock
    private PscDescriptions mockPscDescriptions;

    @Mock
    private SearchDescriptionsRaw mockSearchDescriptionsRaw;

    @Test
    @DisplayName("test description returned for constants")
    public void testDescriptionReturnedForConstants() {

        Descriptions result = descriptionsFactory.createDescription("constants");
        assertNotNull(result);
    }

    @Test
    @DisplayName("test description returned for disqualified_officer_descriptions")
    public void testDescriptionReturnedForDisqualifiedOfficerDescriptions() {

        Descriptions result = descriptionsFactory.createDescription("disqualified_officer_descriptions");
        assertNotNull(result);
    }

    @Test
    @DisplayName("test description returned for errors")
    public void testDescriptionReturnedForErrors() {

        Descriptions result = descriptionsFactory.createDescription("errors");
        assertNotNull(result);
    }

    @Test
    @DisplayName("test description returned for exemption_descriptions")
    public void testDescriptionReturnedForExemptionDescriptions() {

        Descriptions result = descriptionsFactory.createDescription("exemption_descriptions");
        assertNotNull(result);
    }

    @Test
    @DisplayName("test description returned for filing_descriptions")
    public void testDescriptionReturnedForFilingDescriptions() {

        Descriptions result = descriptionsFactory.createDescription("filing_descriptions");
        assertNotNull(result);
    }

    @Test
    @DisplayName("test description returned for filing_history_descriptions")
    public void testDescriptionReturnedForFilingHistoryDescriptions() {

        Descriptions result = descriptionsFactory.createDescription("filing_history_descriptions");
        assertNotNull(result);
    }

    @Test
    @DisplayName("test description returned for mortgage_descriptions")
    public void testDescriptionReturnedForMortgageDescriptions() {

        Descriptions result = descriptionsFactory.createDescription("mortgage_descriptions");
        assertNotNull(result);
    }

    @Test
    @DisplayName("test description returned for psc_descriptions")
    public void testDescriptionReturnedForPscDescriptions() {

        Descriptions result = descriptionsFactory.createDescription("psc_descriptions");
        assertNotNull(result);
    }

    @Test
    @DisplayName("test description returned for search_descriptions_raw")
    public void testDescriptionReturnedForSearchDescriptionsRaw() {

        Descriptions result = descriptionsFactory.createDescription("search_descriptions_raw");
        assertNotNull(result);
    }

    @Test
    @DisplayName("test error thrown when invalid description identifier submitted")
    public void testErrorThrownWhenNoDescriptionPresent() {

        assertThrows(IllegalArgumentException.class, () -> descriptionsFactory.createDescription("invalid_descriptions"));
    }
}
