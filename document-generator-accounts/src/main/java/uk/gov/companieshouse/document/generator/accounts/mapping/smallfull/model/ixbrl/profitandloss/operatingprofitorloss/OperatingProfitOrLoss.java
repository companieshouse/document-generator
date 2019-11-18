package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.profitandloss.operatingprofitorloss;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.profitandloss.operatingprofitorloss.items.AdministrativeExpenses;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.profitandloss.operatingprofitorloss.items.DistributionCosts;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.profitandloss.operatingprofitorloss.items.OperatingTotal;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.profitandloss.operatingprofitorloss.items.OtherOperatingIncome;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OperatingProfitOrLoss {

    @JsonProperty("administrative_expenses")
    private AdministrativeExpenses administrativeExpenses;

    @JsonProperty("distribution_costs")
    private DistributionCosts distributionCosts;

    @JsonProperty("operating_total")
    private OperatingTotal operatingTotal;

    @JsonProperty("other_operating_income")
    private OtherOperatingIncome otherOperatingIncome;

    public AdministrativeExpenses getAdministrativeExpenses() {
        return administrativeExpenses;
    }

    public void setAdministrativeExpenses(AdministrativeExpenses administrativeExpenses) {
        this.administrativeExpenses = administrativeExpenses;
    }

    public DistributionCosts getDistributionCosts() {
        return distributionCosts;
    }

    public void setDistributionCosts(DistributionCosts distributionCosts) {
        this.distributionCosts = distributionCosts;
    }

    public OperatingTotal getOperatingTotal() {
        return operatingTotal;
    }

    public void setOperatingTotal(OperatingTotal operatingTotal) {
        this.operatingTotal = operatingTotal;
    }

    public OtherOperatingIncome getOtherOperatingIncome() {
        return otherOperatingIncome;
    }

    public void setOtherOperatingIncome(OtherOperatingIncome otherOperatingIncome) {
        this.otherOperatingIncome = otherOperatingIncome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OperatingProfitOrLoss)) return false;

        OperatingProfitOrLoss that = (OperatingProfitOrLoss) o;
        return Objects.equals(getAdministrativeExpenses(), that.getAdministrativeExpenses()) &&
                Objects.equals(getDistributionCosts(), that.getDistributionCosts()) &&
                Objects.equals(getOperatingTotal(), that.getOperatingTotal()) &&
                Objects.equals(getOtherOperatingIncome(), that.getOtherOperatingIncome());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAdministrativeExpenses(), getDistributionCosts(), getOperatingTotal(), getOtherOperatingIncome());
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
