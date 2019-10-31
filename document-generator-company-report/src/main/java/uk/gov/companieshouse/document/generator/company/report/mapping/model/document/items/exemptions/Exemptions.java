package uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.exemptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.exemptions.items.Exemption;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Exemptions {

    @JsonProperty("disclosure_transparency_rules_chapter_five_applies")
    private Exemption disclosureTransparencyRulesChapterFiveApplies;

    @JsonProperty("psc_exempt_as_shares_admitted_on_market")
    private Exemption pscExemptAsSharesAdmittedOnMarket;

    @JsonProperty("psc_exempt_as_trading_on_regulated_market")
    private Exemption pscExemptAsTradingOnRegulatedMarket;

    @JsonProperty("psc_exempt_as_trading_on_uk_regulated_market")
    private Exemption pscExemptAsTradingOnUkRegulatedMarket;

    @JsonProperty("active_exemption")
    private boolean activeExemption;

    public Exemption getDisclosureTransparencyRulesChapterFiveApplies() {
        return disclosureTransparencyRulesChapterFiveApplies;
    }

    public void setDisclosureTransparencyRulesChapterFiveApplies(Exemption disclosureTransparencyRulesChapterFiveApplies) {
        this.disclosureTransparencyRulesChapterFiveApplies = disclosureTransparencyRulesChapterFiveApplies;
    }

    public Exemption getPscExemptAsSharesAdmittedOnMarket() {
        return pscExemptAsSharesAdmittedOnMarket;
    }

    public void setPscExemptAsSharesAdmittedOnMarket(Exemption pscExemptAsSharesAdmittedOnMarket) {
        this.pscExemptAsSharesAdmittedOnMarket = pscExemptAsSharesAdmittedOnMarket;
    }

    public Exemption getPscExemptAsTradingOnRegulatedMarket() {
        return pscExemptAsTradingOnRegulatedMarket;
    }

    public void setPscExemptAsTradingOnRegulatedMarket(Exemption pscExemptAsTradingOnRegulatedMarket) {
        this.pscExemptAsTradingOnRegulatedMarket = pscExemptAsTradingOnRegulatedMarket;
    }

    public Exemption getPscExemptAsTradingOnUkRegulatedMarket() {
        return pscExemptAsTradingOnUkRegulatedMarket;
    }

    public void setPscExemptAsTradingOnUkRegulatedMarket(Exemption pscExemptAsTradingOnUkRegulatedMarket) {
        this.pscExemptAsTradingOnUkRegulatedMarket = pscExemptAsTradingOnUkRegulatedMarket;
    }

    public boolean isActiveExemption() {
        return activeExemption;
    }

    public void setActiveExemption(boolean activeExemption) {
        this.activeExemption = activeExemption;
    }
}
