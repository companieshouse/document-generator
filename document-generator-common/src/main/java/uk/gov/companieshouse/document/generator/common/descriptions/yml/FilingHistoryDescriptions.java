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
public class FilingHistoryDescriptions {

    private Map<String, Object> filingHistoryDescriptions;

    private static final String FILING_HISTORY_DESCRIPTIONS_YML = "document-generator-api/api-enumerations/filing_history_descriptions.yml";

    private static final Logger LOG = LoggerFactory.getLogger(MODULE_NAME_SPACE);

    @PostConstruct
    public void init() throws IOException {

        Yaml yaml = new Yaml();
        File descriptionsFile = new File(FILING_HISTORY_DESCRIPTIONS_YML);

        try (InputStream inputStream = new FileInputStream(descriptionsFile)) {

            filingHistoryDescriptions = (Map<String, Object>) yaml.load(inputStream);
            LOG.info("filing_history_descriptions.yml file pre loaded in document-generator");

        } catch (FileNotFoundException e) {
            LOG.error("file not found when obtaining api enumeration " +
                "descriptions for file name: " + descriptionsFile, e);
        }
    }

    public Map<String, Object> getFilingHistoryDescriptions() {
        return filingHistoryDescriptions;
    }

    public void setFilingHistoryDescriptions(
        Map<String, Object> filingHistoryDescriptions) {
        this.filingHistoryDescriptions = filingHistoryDescriptions;
    }
}
