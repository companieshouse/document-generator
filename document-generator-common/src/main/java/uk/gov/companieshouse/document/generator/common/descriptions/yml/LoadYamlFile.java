package uk.gov.companieshouse.document.generator.common.descriptions.yml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static uk.gov.companieshouse.document.generator.common.descriptions.RetrieveApiEnumerationDescription.DESCRIPTIONS_MODULE_NAME_SPACE;

@Component
public class LoadYamlFile {

    private static final Logger LOG = LoggerFactory.getLogger(DESCRIPTIONS_MODULE_NAME_SPACE);

    public Map<String, Object> load(String fileLocation, String name) {

        Map<String, Object> descriptions = new HashMap<>();

        Yaml yaml = new Yaml();
        File descriptionsFile = new File(fileLocation);

        try (
            InputStream inputStream = new FileInputStream(descriptionsFile)) {

            descriptions = (Map<String, Object>) yaml.load(inputStream);
            LOG.info(name + " SUCCESSFULLY POPULATED");

        } catch (
            FileNotFoundException e) {
            LOG.error("file not found when obtaining api enumeration " +
                "descriptions for: " + name, e);
        } catch (
            IOException e) {
            LOG.error("unable to read file when obtaining api enumeration " +
                "descriptions for: " + name, e);
        }

        return descriptions;
    }
}
