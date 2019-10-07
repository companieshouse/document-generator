package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.exemptions;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.web.context.annotation.RequestScope;
import uk.gov.companieshouse.api.model.exemptions.ExemptionItemsApi;
import uk.gov.companieshouse.api.model.exemptions.ExemptionsApi;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.exemptions.Exemptions;

import java.util.ArrayList;
import java.util.List;

@RequestScope
@Mapper(componentModel = "spring", uses = {ApiToExemption.class})
public abstract class  ApiToExemptionsMapper {

    public abstract Exemptions apiToExemptionsMapper(ExemptionsApi exemptionsApi);

    @AfterMapping
    void checkActiveExemption(ExemptionsApi exemptionsApi, @MappingTarget Exemptions exemptions) {

        List<ExemptionItemsApi> disclosureTransparencyRulesChapterFiveAppliesList = new ArrayList<>();
        List<ExemptionItemsApi> pscExemptAsSharesAdmittedOnMarketList = new ArrayList<>();
        List<ExemptionItemsApi> pscExemptAsTradingOnRegulatedMarketList = new ArrayList<>();
        List<ExemptionItemsApi> pscExemptAsTradingOnUkRegulatedMarketList = new ArrayList<>();

        if(exemptionsApi.getDisclosureTransparencyRulesChapterFiveApplies() != null) {
            disclosureTransparencyRulesChapterFiveAppliesList = exemptionsApi.getDisclosureTransparencyRulesChapterFiveApplies().getItems();

            exemptions.setActiveExemption(false);

            for (int i = 0; i < disclosureTransparencyRulesChapterFiveAppliesList.size(); i++) {
                if (disclosureTransparencyRulesChapterFiveAppliesList.get(i).getExemptTo() == null) {
                    exemptions.setActiveExemption(true);
                }
            }
        }
        if(exemptionsApi.getPscExemptAsSharesAdmittedOnMarket() != null) {
            pscExemptAsSharesAdmittedOnMarketList = exemptionsApi.getPscExemptAsSharesAdmittedOnMarket().getItems();

            exemptions.setActiveExemption(false);

            for (int i = 0; i < pscExemptAsSharesAdmittedOnMarketList.size(); i++) {

                if (pscExemptAsSharesAdmittedOnMarketList.get(i).getExemptTo() == null) {
                    exemptions.setActiveExemption(true);
                }
            }
        }
        if(exemptionsApi.getPscExemptAsTradingOnUkRegulatedMarket() != null) {
            pscExemptAsTradingOnRegulatedMarketList = exemptionsApi.getPscExemptAsTradingOnUkRegulatedMarket().getItems();

            exemptions.setActiveExemption(false);

            for (int i = 0; i < pscExemptAsTradingOnRegulatedMarketList.size(); i++) {
                if (pscExemptAsTradingOnRegulatedMarketList.get(i).getExemptTo() == null) {
                    exemptions.setActiveExemption(true);
                }
            }
        }
        if(exemptionsApi.getPscExemptAsTradingOnUkRegulatedMarket() != null) {
            pscExemptAsTradingOnUkRegulatedMarketList = exemptionsApi.getPscExemptAsTradingOnUkRegulatedMarket().getItems();

            exemptions.setActiveExemption(false);

            for (int i = 0; i < pscExemptAsTradingOnUkRegulatedMarketList.size(); i++) {
                if (pscExemptAsTradingOnUkRegulatedMarketList.get(i).getExemptTo() == null) {
                    exemptions.setActiveExemption(true);
                }
            }
        }
    }
}




