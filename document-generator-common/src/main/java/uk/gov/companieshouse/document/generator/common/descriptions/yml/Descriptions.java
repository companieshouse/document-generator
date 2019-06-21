package uk.gov.companieshouse.document.generator.common.descriptions.yml;

import java.util.Map;

/**
 * Interface to getData from Api-Enumeration models
 */
public interface Descriptions {

    /**
     * Get the data from required api-enumeration model
     * @return Map<String, Object> api enumeration data
     */
    Map<String, Object> getData();
}
