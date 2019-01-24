package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.creditorswithinoneyear;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import java.util.Objects;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.creditorswithinoneyear.items.AccrualsAndDeferredIncome;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.creditorswithinoneyear.items.BankLoansAndOverdrafts;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.creditorswithinoneyear.items.FinanceLeasesAndHirePurchaseContracts;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.creditorswithinoneyear.items.OtherCreditors;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.creditorswithinoneyear.items.TaxationAndSocialSecurity;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.creditorswithinoneyear.items.TradeCreditors;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.debtors.items.Total;

@JsonInclude(Include.NON_NULL)
public class CreditorsWithinOneYear {

    @JsonProperty("details")
    private String details;

    @JsonProperty("accruals_and_deferred_income")
    private AccrualsAndDeferredIncome accrualsAndDeferredIncome;

    @JsonProperty("bank_loads_and_overdrafts")
    private BankLoansAndOverdrafts bankLoansAndOverdrafts;

    @JsonProperty("finance_leases_and_hire_purchase_contracts")
    private FinanceLeasesAndHirePurchaseContracts financeLeasesAndHirePurchaseContracts;

    @JsonProperty("other_creditors")
    private OtherCreditors otherCreditors;

    @JsonProperty("taxation_and_social_security")
    private TaxationAndSocialSecurity taxationAndSocialSecurity;

    @JsonProperty("trade_creditors")
    private TradeCreditors tradeCreditors;

    @JsonProperty("total")
    private Total total;

    public String getDetails() {
      return details;
    }

    public void setDetails(String details) {
      this.details = details;
    }

    public AccrualsAndDeferredIncome getAccrualsAndDeferredIncome() {
      return accrualsAndDeferredIncome;
    }

    public void setAccrualsAndDeferredIncome(AccrualsAndDeferredIncome accrualsAndDeferredIncome) {
      this.accrualsAndDeferredIncome = accrualsAndDeferredIncome;
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

    public TaxationAndSocialSecurity getTaxationAndSocialSecurity() {
      return taxationAndSocialSecurity;
    }

    public void setTaxationAndSocialSecurity(TaxationAndSocialSecurity taxationAndSocialSecurity) {
      this.taxationAndSocialSecurity = taxationAndSocialSecurity;
    }

    public TradeCreditors getTradeCreditors() {
      return tradeCreditors;
    }

    public void setTradeCreditors(TradeCreditors tradeCreditors) {
      this.tradeCreditors = tradeCreditors;
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
      if (!(obj instanceof CreditorsWithinOneYear))
        return false;
      CreditorsWithinOneYear other = (CreditorsWithinOneYear) obj;
      return Objects.equals(accrualsAndDeferredIncome, other.accrualsAndDeferredIncome)
          && Objects.equals(bankLoansAndOverdrafts, other.bankLoansAndOverdrafts)
          && Objects.equals(details, other.details)
          && Objects.equals(financeLeasesAndHirePurchaseContracts,
              other.financeLeasesAndHirePurchaseContracts)
          && Objects.equals(otherCreditors, other.otherCreditors)
          && Objects.equals(taxationAndSocialSecurity, other.taxationAndSocialSecurity)
          && Objects.equals(total, other.total)
          && Objects.equals(tradeCreditors, other.tradeCreditors);
    }

    @Override
    public int hashCode() {
      return Objects.hash(accrualsAndDeferredIncome, bankLoansAndOverdrafts, details,
          financeLeasesAndHirePurchaseContracts, otherCreditors, taxationAndSocialSecurity, total,
          tradeCreditors);
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
