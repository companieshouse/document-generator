package uk.gov.companieshouse.document.generator.api.document.render;

public interface ConvertJsonHandler {

    /**
     * Convert to Json Object
     *
     * @param jsonString
     * @return converted String for documentSize
     */
    String convert(String jsonString);
}
