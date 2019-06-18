package uk.gov.companieshouse.document.generator.common.descriptions.yml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.companieshouse.document.generator.common.descriptions.yml.impl.Constants;
import uk.gov.companieshouse.document.generator.common.descriptions.yml.impl.DisqualifiedOfficerDescriptions;
import uk.gov.companieshouse.document.generator.common.descriptions.yml.impl.Errors;
import uk.gov.companieshouse.document.generator.common.descriptions.yml.impl.ExemptionDescriptions;
import uk.gov.companieshouse.document.generator.common.descriptions.yml.impl.FilingDescriptions;
import uk.gov.companieshouse.document.generator.common.descriptions.yml.impl.FilingHistoryDescriptions;
import uk.gov.companieshouse.document.generator.common.descriptions.yml.impl.FilingHistoryExceptions;
import uk.gov.companieshouse.document.generator.common.descriptions.yml.impl.MortgageDescriptions;
import uk.gov.companieshouse.document.generator.common.descriptions.yml.impl.PscDescriptions;
import uk.gov.companieshouse.document.generator.common.descriptions.yml.impl.SearchDescriptionsRaw;

import java.util.HashMap;
import java.util.Map;

@Component
public class DescriptionsFactory {

    final static Map<String, Descriptions> map = new HashMap<>();

    @Autowired
    public DescriptionsFactory(Constants constants,
            DisqualifiedOfficerDescriptions disqualifiedOfficerDescriptions,
            Errors errors,
            ExemptionDescriptions exemptionDescriptions,
            FilingDescriptions filingDescriptions,
            FilingHistoryDescriptions filingHistoryDescriptions,
            FilingHistoryExceptions filingHistoryExceptions,
            MortgageDescriptions mortgageDescriptions,
            PscDescriptions pscDescriptions,
            SearchDescriptionsRaw searchDescriptionsRaw) {

        map.put("CONSTANTS", constants);
        map.put("DISQUALIFIED_OFFICER_DESCRIPTIONS", disqualifiedOfficerDescriptions);
        map.put("ERRORS", errors);
        map.put("EXEMPTION_DESCRIPTIONS", exemptionDescriptions);
        map.put("FILING_DESCRIPTIONS", filingDescriptions);
        map.put("FILING_HISTORY_DESCRIPTIONS", filingHistoryDescriptions);
        map.put("FILING_HISTORY_EXCEPTIONS", filingHistoryExceptions);
        map.put("MORTGAGE_DESCRIPTIONS", mortgageDescriptions);
        map.put("PSC_DESCRIPTIONS", pscDescriptions);
        map.put("SEARCH_DESCRIPTIONS_RAW", searchDescriptionsRaw);
    }

    public Descriptions createDescription(String descriptionType){
        Descriptions description = map.get(descriptionType.toUpperCase());
        if(description != null) {
            return description;
        }
        throw new IllegalArgumentException("No such description type: " + descriptionType.toUpperCase());
    }
}
