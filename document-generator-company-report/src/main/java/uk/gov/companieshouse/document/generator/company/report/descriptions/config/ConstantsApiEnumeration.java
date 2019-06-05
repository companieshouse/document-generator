package uk.gov.companieshouse.document.generator.company.report.descriptions.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;

import static uk.gov.companieshouse.document.generator.company.report.CompanyReportDocumentInfoServiceImpl.MODULE_NAME_SPACE;

@Component
public class ConstantsApiEnumeration {


    private Map<String, Object> constants;

    private static final String CONSTANTS_FILE_LOCATION = "document-generator-api/api-enumerations/constants.yml";

    private static final Logger LOG = LoggerFactory.getLogger(MODULE_NAME_SPACE);

    @PostConstruct
    public void init() throws IOException {

        Yaml yaml = new Yaml();
        File descriptionsFile = new File(CONSTANTS_FILE_LOCATION);

        try (InputStream inputStream = new FileInputStream(descriptionsFile)) {

            constants = (Map<String, Object>) yaml.load(inputStream);

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
