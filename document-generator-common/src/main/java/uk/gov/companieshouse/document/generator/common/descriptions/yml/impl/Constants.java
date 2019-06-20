package uk.gov.companieshouse.document.generator.common.descriptions.yml.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import uk.gov.companieshouse.document.generator.common.descriptions.yml.Descriptions;
import uk.gov.companieshouse.document.generator.common.descriptions.yml.LoadYamlFile;

import javax.annotation.PostConstruct;
import java.util.Map;

@Component
public class Constants implements Descriptions {

    @Autowired
    private LoadYamlFile loadYamlFile;

    private Map<String, Object> constants;

    @Value("${constants.location}")
    private String constantsYml;

    @PostConstruct
    public void init() {
        constants = loadYamlFile.load(constantsYml, "Constants");
    }

    @Override
    public Map<String, Object> getData() {
        return constants;
    }
}
