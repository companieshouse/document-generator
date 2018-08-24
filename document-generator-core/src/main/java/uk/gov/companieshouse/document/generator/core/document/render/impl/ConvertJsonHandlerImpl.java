package uk.gov.companieshouse.document.generator.core.document.render.impl;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import uk.gov.companieshouse.document.generator.core.document.models.DocumentGenerationCompleted;
import uk.gov.companieshouse.document.generator.core.document.render.ConvertJsonHandler;

@Service
public class ConvertJsonHandlerImpl implements ConvertJsonHandler {

    /**
     * Convert to Json Object
     *
     * @param jsonString
     * @return converted generatedDocument
     */
    @Override
    public DocumentGenerationCompleted convert(String jsonString) {
        if (jsonString != null && !jsonString.isEmpty()){
            JSONObject jsonObject = new JSONObject(jsonString);

            DocumentGenerationCompleted generatedDocument = new DocumentGenerationCompleted();
            generatedDocument.setDocumentSize(Long.toString(jsonObject.getLong("document_size")));

            return generatedDocument;

        }
        return null;
    }
}
