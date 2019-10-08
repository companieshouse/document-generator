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

        if(exemptionsApi.getDisclosureTransparencyRulesChapterFiveApplies() != null) {
             disclosureTransparencyRulesChapterFiveAppliesList = exemptionsApi.getDisclosureTransparencyRulesChapterFiveApplies().getItems();

            exemptions.setActiveExemption(false);

            for (ExemptionItemsApi exemptionItemsApi : disclosureTransparencyRulesChapterFiveAppliesList) {
                if(exemptionItemsApi.getExemptTo() == null) {
                    exemptions.setActiveExemption(true);
                }
            }
        }

        if(exemptionsApi.getPscExemptAsSharesAdmittedOnMarket() != null) {
            pscExemptAsSharesAdmittedOnMarketList = exemptionsApi.getPscExemptAsSharesAdmittedOnMarket().getItems();

            exemptions.setActiveExemption(false);

            for (ExemptionItemsApi exemptionItemsApi : pscExemptAsSharesAdmittedOnMarketList) {
                if(exemptionItemsApi.getExemptTo() == null) {
                    exemptions.setActiveExemption(true);
                }
            }
        }

        if(exemptionsApi.getPscExemptAsTradingOnUkRegulatedMarket() != null) {
            pscExemptAsTradingOnRegulatedMarketList = exemptionsApi.getPscExemptAsTradingOnUkRegulatedMarket().getItems();

            exemptions.setActiveExemption(false);

            for (ExemptionItemsApi exemptionItemsApi : pscExemptAsTradingOnRegulatedMarketList) {
                if(exemptionItemsApi.getExemptTo() == null) {
                    exemptions.setActiveExemption(true);
                }
            }
        }

        if(exemptionsApi.getPscExemptAsTradingOnUkRegulatedMarket() != null) {
            pscExemptAsTradingOnUkRegulatedMarketList = exemptionsApi.getPscExemptAsTradingOnUkRegulatedMarket().getItems();

            exemptions.setActiveExemption(false);

            for (ExemptionItemsApi exemptionItemsApi : pscExemptAsTradingOnUkRegulatedMarketList) {
                if(exemptionItemsApi.getExemptTo() == null) {
                    exemptions.setActiveExemption(true);
                }
            }
        }
    }
}