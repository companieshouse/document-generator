package uk.gov.companieshouse.document.generator.core.document.render.impl;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import uk.gov.companieshouse.document.generator.core.document.render.ConvertJsonHandler;

@Service
public class ConvertJsonHandlerImpl implements ConvertJsonHandler {

    /**
     * Convert to Json Object
     *
     * @param jsonString
     * @return converted String for documentSize
     */
    @Override
    public String convert(String jsonString) {
        if (jsonString != null && !jsonString.isEmpty()){
            JSONObject jsonObject = new JSONObject(jsonString);

            String documentSize = Long.toString(jsonObject.getLong("document_size"));

            return documentSize;
        }
        return null;
    }
}
