package uk.gov.companieshouse.document.generator.common.descriptions.yml;

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
import java.util.function.Supplier;

public class DescriptionsFactory {

    final static Map<String, Supplier<Descriptions>> map = new HashMap<>();
    static {
        map.put("CONSTANTS", Constants::new);
        map.put("DISQUALIFIED_OFFICER_DESCRIPTIONS", DisqualifiedOfficerDescriptions::new);
        map.put("ERRORS", Errors::new);
        map.put("EXEMPTION_DESCRIPTIONS", ExemptionDescriptions::new);
        map.put("FILING_DESCRIPTIONS", FilingDescriptions::new);
        map.put("FILING_HISTORY_DESCRIPTIONS", FilingHistoryDescriptions::new);
        map.put("FILING_HISTORY_EXCEPTIONS", FilingHistoryExceptions::new);
        map.put("MORTGAGE_DESCRIPTIONS", MortgageDescriptions::new);
        map.put("PSC_DESCRIPTIONS", PscDescriptions::new);
        map.put("SEARCH_DESCRIPTIONS_RAW", SearchDescriptionsRaw::new);
    }

    public Descriptions createDescription(String descriptionType){
        Supplier<Descriptions> description = map.get(descriptionType.toUpperCase());
        if(description != null) {
            return description.get();
        }
        throw new IllegalArgumentException("No such description type: " + descriptionType.toUpperCase());
    }
}
