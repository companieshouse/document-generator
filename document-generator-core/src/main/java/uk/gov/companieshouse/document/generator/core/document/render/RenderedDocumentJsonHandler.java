package uk.gov.companieshouse.document.generator.core.document.render;

import uk.gov.companieshouse.document.generator.core.document.models.DocumentGenerationCompleted;

public interface RenderedDocumentJsonHandler {

    /**
     * Convert to Json Object
     *
     * @param jsonString
     * @return converted generatedDocument
     */
    DocumentGenerationCompleted convert(String jsonString);
}
