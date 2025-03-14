package uk.gov.companieshouse.document.generator.api.document.render.impl;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import uk.gov.companieshouse.document.generator.api.document.render.ConvertJsonHandler;

@Service
public class ConvertJsonHandlerImpl implements ConvertJsonHandler {

    /**
     * {@inheritDoc}
     */
    @Override
    public String convert(String jsonString) throws JSONException {
        if (jsonString != null && !jsonString.isEmpty()){
            JSONObject jsonObject = new JSONObject(jsonString);

            return Long.toString(jsonObject.getLong("document_size"));

        }
        return null;
    }
}
