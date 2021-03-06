package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.exemptions;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.web.context.annotation.RequestScope;
import uk.gov.companieshouse.api.model.exemptions.ExemptionItemsApi;
import uk.gov.companieshouse.api.model.exemptions.ExemptionsApi;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.exemptions.Exemptions;

import java.util.List;

@RequestScope
@Mapper(componentModel = "spring", uses = {ApiToExemption.class})
public abstract class  ApiToExemptionsMapper {

    public abstract Exemptions apiToExemptionsMapper(ExemptionsApi exemptionsApi);

    @AfterMapping
    void checkActiveExemption(ExemptionsApi exemptionsApi, @MappingTarget Exemptions exemptions) {

        List<ExemptionItemsApi> disclosureTransparencyRulesChapterFiveAppliesList;
        List<ExemptionItemsApi> pscExemptAsSharesAdmittedOnMarketList;
        List<ExemptionItemsApi> pscExemptAsTradingOnRegulatedMarketList;
        List<ExemptionItemsApi> pscExemptAsTradingOnUkRegulatedMarketList;

        exemptions.setActiveExemption(false);

        if(exemptionsApi.getDisclosureTransparencyRulesChapterFiveApplies() != null) {
             disclosureTransparencyRulesChapterFiveAppliesList = exemptionsApi.getDisclosureTransparencyRulesChapterFiveApplies().getItems();

             if(!exemptions.isActiveExemption()) {
                 checkAndSetActiveExemption(disclosureTransparencyRulesChapterFiveAppliesList, exemptions);
             }
        }

        if(exemptionsApi.getPscExemptAsSharesAdmittedOnMarket() != null) {
            pscExemptAsSharesAdmittedOnMarketList = exemptionsApi.getPscExemptAsSharesAdmittedOnMarket().getItems();

            if(!exemptions.isActiveExemption()) {
                checkAndSetActiveExemption(pscExemptAsSharesAdmittedOnMarketList, exemptions);
            }
        }

        if(exemptionsApi.getPscExemptAsTradingOnRegulatedMarket() != null) {
            pscExemptAsTradingOnRegulatedMarketList = exemptionsApi.getPscExemptAsTradingOnRegulatedMarket().getItems();

            if(!exemptions.isActiveExemption()) {
                checkAndSetActiveExemption(pscExemptAsTradingOnRegulatedMarketList, exemptions);
            }
        }

        if(exemptionsApi.getPscExemptAsTradingOnUkRegulatedMarket() != null) {
            pscExemptAsTradingOnUkRegulatedMarketList = exemptionsApi.getPscExemptAsTradingOnUkRegulatedMarket().getItems();

            if(!exemptions.isActiveExemption()) {
                checkAndSetActiveExemption(pscExemptAsTradingOnUkRegulatedMarketList, exemptions);
            }
        }
    }

    private void checkAndSetActiveExemption(List<ExemptionItemsApi> exemptionItemsList, Exemptions exemptions) {
        for (ExemptionItemsApi exemptionItemsApi : exemptionItemsList) {
            if(exemptionItemsApi.getExemptTo() == null) {
                exemptions.setActiveExemption(true);
            }
        }
    }
}