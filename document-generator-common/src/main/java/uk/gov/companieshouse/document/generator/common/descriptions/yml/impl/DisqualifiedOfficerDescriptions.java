package uk.gov.companieshouse.document.generator.common.descriptions.yml.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import uk.gov.companieshouse.document.generator.common.descriptions.yml.Descriptions;
import uk.gov.companieshouse.document.generator.common.descriptions.yml.LoadYamlFile;

import javax.annotation.PostConstruct;
import java.util.Map;

@Component
public class DisqualifiedOfficerDescriptions implements Descriptions {

    @Autowired
    private LoadYamlFile loadYamlFile;

    private Map<String, Object> disqualifiedOfficerDescriptions;

    @Value("${disqualified.officer.descriptions}")
    private String disqualifiedOfficerDescriptionsYml;

    @PostConstruct
    public void init() {
        disqualifiedOfficerDescriptions = loadYamlFile
                .load(disqualifiedOfficerDescriptionsYml, "DisqualifiedOfficerDescriptions");
    }

    @Override
    public Map<String, Object> getData() {
        return disqualifiedOfficerDescriptions;
    }
}
