package uk.gov.companieshouse.document.generator.prosecution;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfoResponse;
import uk.gov.companieshouse.document.generator.prosecution.UltimatumDocumentInfoBuilderProvider.UltimatumDocumentInfoBuilder;
import uk.gov.companieshouse.document.generator.prosecution.mapping.model.prosecutioncase.ProsecutionCase;
import uk.gov.companieshouse.environment.EnvironmentReader;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
class UltimatumDocumentInfoBuilderProviderTest {
    private static final String CONFIG_KEY_REGISTRY_ADDRESS = "TEMPLATE_REGISTRY_ADDR";
    private static final String TEMPLATE_REGISTRY_ADDRESS = "http://localhost:9999/some/address";
    private static final String COMPANY_NUMBER = "22";
    private static final String COMPANY_NAME = "FooCom";
    private static final String PATH_FILENAME = "Foo.pdf";
    private static final String PATH_BASE = "/prosecution/ultimatum/";
    private static final String TEMPLATE_NAME = "ultimatum.html";
    private static final String ASSET_ID = "prosecution";
    @Mock
    private EnvironmentReader environmentReader;

    @Mock
    private ProsecutionCase prosecutionCase;

    @Test
    void testConstructorPicksUpConfig() {
        when(environmentReader.getMandatoryString(CONFIG_KEY_REGISTRY_ADDRESS))
                        .thenReturn(TEMPLATE_REGISTRY_ADDRESS);
        new UltimatumDocumentInfoBuilderProvider(environmentReader);
    }

    @Test
    void testBasicBuild() throws DocumentInfoCreationException, JsonParseException,
                    JsonMappingException, IOException {
        when(environmentReader.getMandatoryString(CONFIG_KEY_REGISTRY_ADDRESS))
                        .thenReturn(TEMPLATE_REGISTRY_ADDRESS);
        when(prosecutionCase.getCompanyIncorporationNumber()).thenReturn(COMPANY_NUMBER);
        when(prosecutionCase.getCompanyName()).thenReturn(COMPANY_NAME);

        UltimatumDocumentInfoBuilderProvider provider =
                        new UltimatumDocumentInfoBuilderProvider(environmentReader);
        UltimatumDocumentInfoBuilder builderToTest = provider.builder();
        builderToTest.prosecutionCase(prosecutionCase);
        builderToTest.renderedDocFileName(PATH_FILENAME);
        DocumentInfoResponse documentInfoResponse = builderToTest.build();
        String templateValuesAsJson = documentInfoResponse.getData();
        UltimatumTemplateValues parsedTemplateValues = new ObjectMapper()
                        .readValue(templateValuesAsJson, UltimatumTemplateValues.class);
        assertEquals(COMPANY_NAME, parsedTemplateValues.getCompanyName());
        assertEquals(COMPANY_NUMBER, parsedTemplateValues.getCompanyNumber());
        assertEquals(PATH_BASE + PATH_FILENAME, documentInfoResponse.getPath());
        assertEquals(TEMPLATE_NAME, documentInfoResponse.getTemplateName());
        assertEquals(ASSET_ID, documentInfoResponse.getAssetId());
        assertEquals(TEMPLATE_REGISTRY_ADDRESS, parsedTemplateValues.getTemplateRegistryAddress());
    }
}
