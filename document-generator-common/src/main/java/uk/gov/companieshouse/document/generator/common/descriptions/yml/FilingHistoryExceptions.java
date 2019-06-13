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
public class FilingHistoryExceptions {

    private Map<String, Object> filingHistoryExceptions;

    private static final String FILING_HISTORY_EXCEPTIONS_YML = "document-generator-api/api-enumerations/filing_history_exceptions.yml";

    private static final Logger LOG = LoggerFactory.getLogger(MODULE_NAME_SPACE);

    @PostConstruct
    public void init() throws IOException {

        Yaml yaml = new Yaml();
        File descriptionsFile = new File(FILING_HISTORY_EXCEPTIONS_YML);

        try (InputStream inputStream = new FileInputStream(descriptionsFile)) {

            filingHistoryExceptions = (Map<String, Object>) yaml.load(inputStream);
            LOG.info("filing_history_exceptions.yml file pre loaded in document-generator");

        } catch (FileNotFoundException e) {
            LOG.error("file not found when obtaining api enumeration " +
                "descriptions for file name: " + descriptionsFile, e);
        }
    }

    public Map<String, Object> getFilingHistoryExceptions() {
        return filingHistoryExceptions;
    }

    public void setFilingHistoryExceptions(Map<String, Object> filingHistoryExceptions) {
        this.filingHistoryExceptions = filingHistoryExceptions;
    }
}
