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

        List<List<ExemptionItemsApi>> exemptionItemsList = new ArrayList<>();

        if(exemptionsApi.getDisclosureTransparencyRulesChapterFiveApplies() != null) {
            exemptionItemsList.add(exemptionsApi.getDisclosureTransparencyRulesChapterFiveApplies().getItems());
        }
        if(exemptionsApi.getPscExemptAsSharesAdmittedOnMarket() != null) {
            exemptionItemsList.add(exemptionsApi.getPscExemptAsSharesAdmittedOnMarket().getItems());
        }
        if(exemptionsApi.getPscExemptAsTradingOnRegulatedMarket() != null) {
            exemptionItemsList.add(exemptionsApi.getPscExemptAsTradingOnRegulatedMarket().getItems());
        }
        if(exemptionsApi.getPscExemptAsTradingOnUkRegulatedMarket() != null) {
            exemptionItemsList.add(exemptionsApi.getPscExemptAsTradingOnUkRegulatedMarket().getItems());
        }

        for( int x =0; x < exemptionItemsList.size(); x ++){
            for(int y = 0; y <exemptionItemsList.get(x).size(); y ++ ) {
                exemptions.setActiveExemption(false);
                    if(exemptionItemsList.get(x).get(y).getExemptTo() == null){
                        exemptions.setActiveExemption(true);
                    }
            }
        }
    }
}




