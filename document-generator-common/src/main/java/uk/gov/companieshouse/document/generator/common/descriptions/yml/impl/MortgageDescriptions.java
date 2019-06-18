package uk.gov.companieshouse.document.generator.common.descriptions.yml.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;
import uk.gov.companieshouse.document.generator.common.descriptions.yml.Descriptions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import static uk.gov.companieshouse.document.generator.common.descriptions.RetrieveApiEnumerationDescription.DESCRIPTIONS_MODULE_NAME_SPACE;

@Component
public class MortgageDescriptions implements Descriptions {

    private Map<String, Object> mortgageDescriptions;

    private static final String MORTGAGE_DESCRIPTIONS_YML = "document-generator-common/api-enumerations/mortgage_descriptions.yml";

    private static final Logger LOG = LoggerFactory.getLogger(DESCRIPTIONS_MODULE_NAME_SPACE);

    public MortgageDescriptions() throws IOException {

        Yaml yaml = new Yaml();
        File descriptionsFile = new File(MORTGAGE_DESCRIPTIONS_YML);

        try (InputStream inputStream = new FileInputStream(descriptionsFile)) {

            mortgageDescriptions = (Map<String, Object>) yaml.load(inputStream);
            LOG.info("mortgage_descriptions.yml file pre loaded in document-generator");

        } catch (FileNotFoundException e) {
            LOG.error("file not found when obtaining api enumeration " +
                "descriptions for file name: " + descriptionsFile, e);
        }
    }

    @Override
    public Map<String, Object> getData() {
        return mortgageDescriptions;
    }
}
