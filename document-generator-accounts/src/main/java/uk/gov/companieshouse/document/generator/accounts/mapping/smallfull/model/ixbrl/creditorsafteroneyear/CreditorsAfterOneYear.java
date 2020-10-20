package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.creditorsafteroneyear;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import java.util.Objects;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.creditorswithinoneyear.items.BankLoansAndOverdrafts;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.creditorswithinoneyear.items.FinanceLeasesAndHirePurchaseContracts;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.creditorswithinoneyear.items.OtherCreditors;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.debtors.items.Total;

@JsonInclude(Include.NON_NULL)
public class CreditorsAfterOneYear {

    @JsonProperty("details")
    private String details;

    @JsonProperty("bank_loans_and_overdrafts")
    private BankLoansAndOverdrafts bankLoansAndOverdrafts;

    @JsonProperty("finance_leases_and_hire_purchase_contracts")
    private FinanceLeasesAndHirePurchaseContracts financeLeasesAndHirePurchaseContracts;

    @JsonProperty("other_creditors")
    private OtherCreditors otherCreditors;

    @JsonProperty("total")
    private Total total;

    public String getDetails() {
      return details;
    }

    public void setDetails(String details) {
      this.details = details;
    }

    public BankLoansAndOverdrafts getBankLoansAndOverdrafts() {
      return bankLoansAndOverdrafts;
    }

    public void setBankLoansAndOverdrafts(BankLoansAndOverdrafts bankLoansAndOverdrafts) {
      this.bankLoansAndOverdrafts = bankLoansAndOverdrafts;
    }

    public FinanceLeasesAndHirePurchaseContracts getFinanceLeasesAndHirePurchaseContracts() {
      return financeLeasesAndHirePurchaseContracts;
    }

    public void setFinanceLeasesAndHirePurchaseContracts(
        FinanceLeasesAndHirePurchaseContracts financeLeasesAndHirePurchaseContracts) {
      this.financeLeasesAndHirePurchaseContracts = financeLeasesAndHirePurchaseContracts;
    }

    public OtherCreditors getOtherCreditors() {
      return otherCreditors;
    }

    public void setOtherCreditors(OtherCreditors otherCreditors) {
      this.otherCreditors = otherCreditors;
    }

    public Total getTotal() {
      return total;
    }

    public void setTotal(Total total) {
      this.total = total;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (!(obj instanceof CreditorsAfterOneYear))
        return false;
      CreditorsAfterOneYear other = (CreditorsAfterOneYear) obj;
      return Objects.equals(bankLoansAndOverdrafts, other.bankLoansAndOverdrafts)
          && Objects.equals(details, other.details)
          && Objects.equals(financeLeasesAndHirePurchaseContracts,
              other.financeLeasesAndHirePurchaseContracts)
          && Objects.equals(otherCreditors, other.otherCreditors)
          && Objects.equals(total, other.total);
    }

    @Override
    public int hashCode() {
      return Objects.hash(bankLoansAndOverdrafts, details,
          financeLeasesAndHirePurchaseContracts, otherCreditors, total);
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
