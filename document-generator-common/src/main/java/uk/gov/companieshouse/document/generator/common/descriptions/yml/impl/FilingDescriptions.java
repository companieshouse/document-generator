package uk.gov.companieshouse.document.generator.common.descriptions.yml.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import uk.gov.companieshouse.document.generator.common.descriptions.yml.Descriptions;
import uk.gov.companieshouse.document.generator.common.descriptions.yml.LoadYamlFile;

import jakarta.annotation.PostConstruct;
import java.util.Map;

@Component
public class FilingDescriptions implements Descriptions {

    @Autowired
    private LoadYamlFile loadYamlFile;

    private Map<String, Object> filingDescriptions;

    @Value("${filing.descriptions}")
    private String filingDescriptionsYml;

    @PostConstruct
    public void init() {
       filingDescriptions = loadYamlFile
           .load(filingDescriptionsYml, "FilingDescriptions");
    }

    @Override
    public Map<String, Object> getData() {
        return filingDescriptions;
    }
}