package uk.gov.companieshouse.document.generator.common.descriptions.yml.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import uk.gov.companieshouse.document.generator.common.descriptions.yml.Descriptions;
import uk.gov.companieshouse.document.generator.common.descriptions.yml.LoadYamlFile;

import javax.annotation.PostConstruct;
import java.util.Map;

@Component
public class MortgageDescriptions implements Descriptions {

    @Autowired
    private LoadYamlFile loadYamlFile;

    private Map<String, Object> mortgageDescriptions;

    @Value("${mortgage.descriptions}")
    private String mortgageDescriptionsYml;

    @PostConstruct
    public void init() {
        mortgageDescriptions = loadYamlFile
            .load(mortgageDescriptionsYml, "MortgageDescriptions");
    }

    @Override
    public Map<String, Object> getData() {
        return mortgageDescriptions;
    }
}
