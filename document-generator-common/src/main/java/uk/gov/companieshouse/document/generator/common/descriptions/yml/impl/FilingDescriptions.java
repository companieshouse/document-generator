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

import static uk.gov.companieshouse.document.generator.common.descriptions.RetrieveApiEnumerationDescription.MODULE_NAME_SPACE;

@Component
public class FilingDescriptions implements Descriptions {

    private Map<String, Object> filingDescriptions;

    private static final String FILING_DESCRIPTIONS_YML = "document-generator-common/api-enumerations/filing_descriptions.yml";

    private static final Logger LOG = LoggerFactory.getLogger(MODULE_NAME_SPACE);

    public FilingDescriptions() throws IOException {

        Yaml yaml = new Yaml();
        File descriptionsFile = new File(FILING_DESCRIPTIONS_YML);

        try (InputStream inputStream = new FileInputStream(descriptionsFile)) {

            filingDescriptions = (Map<String, Object>) yaml.load(inputStream);
            LOG.info("filing_descriptions.yml file pre loaded in document-generator");

        } catch (FileNotFoundException e) {
            LOG.error("file not found when obtaining api enumeration " +
                "descriptions for file name: " + descriptionsFile, e);
        }
    }

    @Override
    public Map<String, Object> getData() {
        return filingDescriptions;
    }
}