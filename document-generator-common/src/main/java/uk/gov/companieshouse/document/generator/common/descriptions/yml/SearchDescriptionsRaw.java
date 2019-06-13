package uk.gov.companieshouse.document.generator.common.descriptions.yml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import static uk.gov.companieshouse.document.generator.common.descriptions.RetrieveApiEnumerationDescription.MODULE_NAME_SPACE;

@Component
public class SearchDescriptionsRaw {

    private Map<String, Object> searchDescriptionsRaw;

    private static final String SEARCH_DESCRIPTIONS_RAW_YAML = "document-generator-api/api-enumerations/search_descriptions_raw.yaml";

    private static final Logger LOG = LoggerFactory.getLogger(MODULE_NAME_SPACE);

    @PostConstruct
    public void init() throws IOException {

        Yaml yaml = new Yaml();
        File descriptionsFile = new File(SEARCH_DESCRIPTIONS_RAW_YAML);

        try (InputStream inputStream = new FileInputStream(descriptionsFile)) {

            searchDescriptionsRaw = (Map<String, Object>) yaml.load(inputStream);
            LOG.info("search_descriptions_raw.yml file pre loaded in document-generator");

        } catch (FileNotFoundException e) {
            LOG.error("file not found when obtaining api enumeration " +
                "descriptions for file name: " + descriptionsFile, e);
        }
    }

    public Map<String, Object> getSearchDescriptionsRaw() {
        return searchDescriptionsRaw;
    }

    public void setSearchDescriptionsRaw(
        Map<String, Object> searchDescriptionsRaw) {
        this.searchDescriptionsRaw = searchDescriptionsRaw;
    }
}
