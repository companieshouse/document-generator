package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.membersfunds;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import java.util.Objects;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.membersfunds.items.ProfitAndLossAccount;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.membersfunds.items.TotalMembersFunds;

@JsonInclude(Include.NON_NULL)
public class MembersFunds {

    @JsonProperty("profit_and_loss_account")
    private ProfitAndLossAccount profitAndLossAccount;

    @JsonProperty("total_members_funds")
    private TotalMembersFunds totalMembersFunds;

    public ProfitAndLossAccount getProfitAndLossAccount() {
        return profitAndLossAccount;
    }

    public void setProfitAndLossAccount(ProfitAndLossAccount profitAndLossAccount) {
        this.profitAndLossAccount = profitAndLossAccount;
    }

    public TotalMembersFunds getTotalMembersFunds() {
        return totalMembersFunds;
    }

    public void setTotalMembersFunds(TotalMembersFunds totalMembersFunds) {
        this.totalMembersFunds = totalMembersFunds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MembersFunds)) return false;
        MembersFunds that = (MembersFunds) o;
        return Objects.equals(getProfitAndLossAccount(), that.getProfitAndLossAccount()) &&
                Objects.equals(getTotalMembersFunds(), that.getTotalMembersFunds());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getProfitAndLossAccount(), getTotalMembersFunds());
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
