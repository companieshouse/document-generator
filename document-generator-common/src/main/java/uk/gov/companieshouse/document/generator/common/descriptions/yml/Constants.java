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
public class Constants {

    private Map<String, Object> constants;

    private static final String CONSTANTS_YML = "document-generator-api/api-enumerations/constants.yml";

    private static final Logger LOG = LoggerFactory.getLogger(MODULE_NAME_SPACE);

    @PostConstruct
    public void init() throws IOException {

        Yaml yaml = new Yaml();
        File descriptionsFile = new File(CONSTANTS_YML);

        try (InputStream inputStream = new FileInputStream(descriptionsFile)) {

            constants = (Map<String, Object>) yaml.load(inputStream);
            LOG.info("constants.yml file pre loaded in document-generator");

        } catch (FileNotFoundException e) {
            LOG.error("file not found when obtaining api enumeration " +
                "descriptions for file name: " + descriptionsFile, e);
        }
    }

    public Map<String, Object> getConstants() {
        return constants;
    }

    public void setConstants(Map<String, Object> constants) {
        this.constants = constants;
    }
}
