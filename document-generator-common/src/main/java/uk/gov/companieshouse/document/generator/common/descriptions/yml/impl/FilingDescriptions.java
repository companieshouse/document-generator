package uk.gov.companieshouse.document.generator.common.descriptions.yml.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;
import uk.gov.companieshouse.document.generator.common.descriptions.yml.Descriptions;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import static uk.gov.companieshouse.document.generator.common.descriptions.RetrieveApiEnumerationDescription.DESCRIPTIONS_MODULE_NAME_SPACE;

@Component
public class FilingDescriptions implements Descriptions {

    private Map<String, Object> filingDescriptions;

    @Value("${filing.descriptions}")
    private String filingDescriptionsYml;

    private static final Logger LOG = LoggerFactory.getLogger(DESCRIPTIONS_MODULE_NAME_SPACE);

    @PostConstruct
    public void init() {

        Yaml yaml = new Yaml();
        File descriptionsFile = new File(filingDescriptionsYml);

        try (InputStream inputStream = new FileInputStream(descriptionsFile)) {

            filingDescriptions = (Map<String, Object>) yaml.load(inputStream);
            LOG.info("filing_descriptions SUCCESSFULLY POPULATED");

        } catch (FileNotFoundException e) {
            LOG.error("file not found when obtaining api enumeration " +
                "descriptions for file name: " + descriptionsFile, e);
        } catch (IOException e) {
            LOG.error("unable to read file when obtaining api enumeration " +
                "descriptions for file name: " + descriptionsFile, e);
        }
    }

    @Override
    public Map<String, Object> getData() {
        return filingDescriptions;
    }
}