package uk.gov.companieshouse.document.generator.descriptions.yml;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.document.generator.common.descriptions.yml.LoadYamlFile;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LoadYamlFileTest {

    @InjectMocks
    private LoadYamlFile loadYamlFile;

    @Test
    @DisplayName("Tests constants yml data returned")
    public void testConstantsYmlDataReturned(){

        Map<String, Object> response = loadYamlFile
            .load("api-enumerations/constants.yml", "Constants");

        assertNotNull(response);
    }

    @Test
    @DisplayName("Tests disqualified_officer_descriptions yml data returned")
    public void testDisqualifiedOfficerDescriptionsYmlDataReturned(){

        Map<String, Object> response = loadYamlFile
            .load("api-enumerations/disqualified_officer_descriptions.yml",
                "DisqualifiedOfficerDescriptions");

        assertNotNull(response);
    }

    @Test
    @DisplayName("Tests errors yml data returned")
    public void testErrorsYmlDataReturned(){

        Map<String, Object> response = loadYamlFile
            .load("api-enumerations/errors.yml", "Errors");

        assertNotNull(response);
    }

    @Test
    @DisplayName("Tests exemption_descriptions yml data returned")
    public void testExemptionDescriptionsYmlDataReturned(){

        Map<String, Object> response = loadYamlFile
            .load("api-enumerations/exemption_descriptions.yml",
                "ExemptionDescriptions");

        assertNotNull(response);
    }

    @Test
    @DisplayName("Tests filing_descriptions yml data returned")
    public void testFilingDescriptionsYmlDataReturned(){

        Map<String, Object> response = loadYamlFile
            .load("api-enumerations/filing_descriptions.yml",
                "FilingDescriptions");

        assertNotNull(response);
    }

    @Test
    @DisplayName("Tests filing_history_descriptions yml data returned")
    public void testFilingHistoryDescriptionsYmlDataReturned(){

        Map<String, Object> response = loadYamlFile
            .load("api-enumerations/filing_history_descriptions.yml",
                "FilingHistoryDescriptions");

        assertNotNull(response);
    }

    @Test
    @DisplayName("Tests filing_history_exceptions yml data returned")
    public void testFilingHistoryExceptionsYmlDataReturned(){

        Map<String, Object> response = loadYamlFile
            .load("api-enumerations/filing_history_exceptions.yml",
                "FilingHistoryExceptions");

        assertNotNull(response);
    }

    @Test
    @DisplayName("Tests mortgage_descriptions yml data returned")
    public void testMortgageDescriptionsYmlDataReturned(){

        Map<String, Object> response = loadYamlFile
            .load("api-enumerations/mortgage_descriptions.yml",
                "MortgageDescriptions");

        assertNotNull(response);
    }

    @Test
    @DisplayName("Tests psc_descriptions yml data returned")
    public void testPscDescriptionsYmlDataReturned(){

        Map<String, Object> response = loadYamlFile
            .load("api-enumerations/psc_descriptions.yml",
                "PscDescriptions");

        assertNotNull(response);
    }

    @Test
    @DisplayName("Tests search_descriptions_raw yml data returned")
    public void testSearchDescriptionsRawYmlDataReturned(){

        Map<String, Object> response = loadYamlFile
            .load("api-enumerations/search_descriptions_raw.yaml",
                "SearchDescriptionsRaw");

        assertNotNull(response);
    }
}
