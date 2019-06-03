package uk.gov.companieshouse.document.generator.prosecution;

import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfoResponse;
import uk.gov.companieshouse.document.generator.prosecution.mapping.model.prosecutioncase.ProsecutionCase;
import uk.gov.companieshouse.environment.EnvironmentReader;

/**
 * Provides an instance of its contained {@link UltimatumDocumentInfoBuilder} to any caller of the
 * {@link #builder()} method. The builder is then used to create a {@link DocumentInfoResponse} for
 * a Prosecution Ultimatum.
 */
@Component
public class UltimatumDocumentInfoBuilderProvider {
    private final String assetId = "prosecution";
    private final String renderedDocDir = "/prosecution/ultimatum/";
    private final String templateName = "ultimatum.html";
    private final String templateRegistryAddress;

    /**
     * Builds template values, in the form of a {@link DocumentInfoResponse},
     * that can be used to render an ultimatum.
     * 
     * @param environmentReader Used to get hold of config.
     */
    public UltimatumDocumentInfoBuilderProvider(EnvironmentReader environmentReader) {
        // TODO: SJP-599 make these configurable: https://companieshouse.atlassian.net/browse/SJP-599
        // assetId = environmentReader.getMandatoryString(EnvironmentReaderKeys.ULTIMATUM_ASSET_ID);
        // renderedDocDir =
        // environmentReader.getMandatoryString(EnvironmentReaderKeys.ULTIMATUM_RENDERED_DOC_DIR);
        // renderedDocFileBase =
        // environmentReader.getMandatoryString(EnvironmentReaderKeys.ULTIMATUM_RENDERED_DOC_FILEBASE);
        // templateName =
        // environmentReader.getMandatoryString(EnvironmentReaderKeys.ULTIMATUM_TEMPLATE_NAME);
        templateRegistryAddress =
                        environmentReader.getMandatoryString(ConfigKeys.TEMPLATE_REGISTRY_ADDRESS);
    }

    public UltimatumDocumentInfoBuilder builder() {
        return new UltimatumDocumentInfoBuilder();
    }

    /**
     * Create a {@link DocumentInfoResponse} for a Prosecution Ultimatum.
     *
     */
    public class UltimatumDocumentInfoBuilder {
        private ProsecutionCase prosecutionCase;
        private String renderedDocFileName;

        private UltimatumDocumentInfoBuilder() {}

        /**
         * Sets the prosecution case, a source of info for building the template values.
         * @param prosecutionCase
         * @return
         */
        public UltimatumDocumentInfoBuilder prosecutionCase(ProsecutionCase prosecutionCase) {
            this.prosecutionCase = prosecutionCase;
            return this;
        }

        /**
         * Sets the name of file for the rendered doc.
         * @param renderedDocFileName
         * @return
         */
        public UltimatumDocumentInfoBuilder renderedDocFileName(String renderedDocFileName) {
            this.renderedDocFileName = renderedDocFileName;
            return this;
        }

        public DocumentInfoResponse build() throws DocumentInfoCreationException {
            DocumentInfoResponse documentInfoResponse = new DocumentInfoResponse();
            UltimatumTemplateValues templateValues = createTemplateValues();
            String templateValuesAsJson = toJson(templateValues);
            documentInfoResponse.setData(templateValuesAsJson);
            documentInfoResponse.setAssetId(assetId);
            documentInfoResponse.setTemplateName(templateName);
            documentInfoResponse.setPath(renderedDocDir + renderedDocFileName);
            return documentInfoResponse;
        }

        private UltimatumTemplateValues createTemplateValues() {
            String companyName = prosecutionCase.getCompanyName();
            String companyIncorporationNumber = prosecutionCase.getCompanyIncorporationNumber();

            UltimatumTemplateValues templateValues = new UltimatumTemplateValues();
            templateValues.setCompanyName(companyName);
            templateValues.setCompanyNumber(companyIncorporationNumber);
            templateValues.setTemplateRegistryAddress(templateRegistryAddress);
            //defendant name temporarily WS only, this will vanish in the template rework
            templateValues.setDefendantName(" ");
            return templateValues;
        }

        private String toJson(UltimatumTemplateValues templateValues)
                        throws DocumentInfoCreationException {
            ObjectMapper mapper = new ObjectMapper();
            try {
                return mapper.writeValueAsString(templateValues);
            } catch (JsonProcessingException e) {
                throw new DocumentInfoCreationException(
                                "Could not serialise Document Info for Ultimatum for prosecution case: ");
            }
        }
    }

}
