package uk.gov.companieshouse.document.generator.api.document.render;

import org.json.JSONException;

public interface ConvertJsonHandler {

    /**
     * Convert to Json Object
     *
     * @param jsonString a string of Json
     * @return converted String for documentSize
     */
    String convert(String jsonString) throws JSONException;
}
