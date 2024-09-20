package uk.gov.companieshouse.document.generator.common.descriptions.yml.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import uk.gov.companieshouse.document.generator.common.descriptions.yml.Descriptions;
import uk.gov.companieshouse.document.generator.common.descriptions.yml.LoadYamlFile;

import jakarta.annotation.PostConstruct;
import java.util.Map;

@Component
public class FilingHistoryDescriptions implements Descriptions {

    @Autowired
    private LoadYamlFile loadYamlFile;

    private static Map<String, Object> filingHistoryDescriptions;

    @Value("${filing.history.descriptions}")
    private String filingHistoryDescriptionsYml;

    @PostConstruct
    public void init() {
        filingHistoryDescriptions = loadYamlFile
            .load(filingHistoryDescriptionsYml, "FilingHistoryDescriptions");
    }

    @Override
    public Map<String, Object> getData() {
        return filingHistoryDescriptions;
    }
}
