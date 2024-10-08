package uk.gov.companieshouse.document.generator.common.descriptions.yml.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import uk.gov.companieshouse.document.generator.common.descriptions.yml.Descriptions;
import uk.gov.companieshouse.document.generator.common.descriptions.yml.LoadYamlFile;

import jakarta.annotation.PostConstruct;
import java.util.Map;

@Component
public class FilingHistoryExceptions implements Descriptions {

    @Autowired
    private LoadYamlFile loadYamlFile;

    private Map<String, Object> filingHistoryExceptions;

    @Value("${filing.history.exceptions}")
    private String filingHistoryExceptionsYml;

    @PostConstruct
    public void init() {
        filingHistoryExceptions = loadYamlFile
            .load(filingHistoryExceptionsYml, "FilingHistoryExceptions");
    }

    @Override
    public Map<String, Object> getData() {
        return filingHistoryExceptions;
    }
}
