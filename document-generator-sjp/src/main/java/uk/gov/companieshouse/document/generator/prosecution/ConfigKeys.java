package uk.gov.companieshouse.document.generator.prosecution;
// DOCUMENT_GENERATOR_PROSECUTION_ULTIMATUM_ASSET_ID = "sjp";
// DOCUMENT_GENERATOR_PROSECUTION_ULTIMATUM_TEMPLATE_NAME = "sjp.html";
// DOCUMENT_GENERATOR_PROSECUTION_ULTIMATUM_RENDERED_DOC_DIR = "/sjp/";
// DOCUMENT_GENERATOR_PROSECUTION_ULTIMATUM_RENDERED_DOC_FILEBASE
// DOCUMENT_GENERATOR_PROSECUTION_ULTIMATUM_TEMPLATE_NAME = "SJP-1234.pdf";
// DOCUMENT_GENERATOR_PROSECUTION_TEMPLATE_REGISTRY_ADDRESS = "chs-dev:5002";

/**
 * The keys to environment variables that form the config for the Prosecution part of Document
 * Generator.
 */
public final class ConfigKeys {
    public static final String CHS_API_KEY = "CHS_API_KEY";
    public static final String PROSECUTION_SERVICE_URI = "PROSECUTION_CASE_URL";
    /**
     * The address of the template registry, which stores the templates to be filled in by the
     * document renderer.
     */
    public static final String TEMPLATE_REGISTRY_ADDRESS = "TEMPLATE_REGISTRY_ADDR";
    // public static final String ULTIMATUM_ASSET_ID =
    // "DOCUMENT_GENERATOR_PROSECUTION_ULTIMATUM_ASSET_ID";
    // public static final String ULTIMATUM_RENDERED_DOC_DIR =
    // "DOCUMENT_GENERATOR_PROSECUTION_ULTIMATUM_RENDERED_DOC_DIR";
    // public static final String ULTIMATUM_RENDERED_DOC_FILEBASE =
    // "DOCUMENT_GENERATOR_PROSECUTION_ULTIMATUM_RENDERED_DOC_FILEBASE";
    // public static final String ULTIMATUM_TEMPLATE_NAME =
    // "DOCUMENT_GENERATOR_PROSECUTION_ULTIMATUM_TEMPLATE_NAME";

    private ConfigKeys() {}
}
