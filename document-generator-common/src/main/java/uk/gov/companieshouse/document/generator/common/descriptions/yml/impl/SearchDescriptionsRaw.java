package uk.gov.companieshouse.document.generator.common.descriptions.yml.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import uk.gov.companieshouse.document.generator.common.descriptions.yml.Descriptions;
import uk.gov.companieshouse.document.generator.common.descriptions.yml.LoadYamlFile;

import jakarta.annotation.PostConstruct;
import java.util.Map;

@Component
public class SearchDescriptionsRaw implements Descriptions {

    @Autowired
    private LoadYamlFile loadYamlFile;

    private Map<String, Object> searchDescriptionsRaw;

    @Value("${search.descriptions.raw}")
    private String searchDescriptionsRawYml;

    @PostConstruct
    public void init() {
        searchDescriptionsRaw = loadYamlFile.load(searchDescriptionsRawYml, "SearchDescriptionsRaw");
    }

    @Override
    public Map<String, Object> getData() {
        return searchDescriptionsRaw;
    }
}
