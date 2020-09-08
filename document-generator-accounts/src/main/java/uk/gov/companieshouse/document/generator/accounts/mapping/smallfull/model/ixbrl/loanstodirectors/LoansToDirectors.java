package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.loanstodirectors;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class LoansToDirectors {

    @JsonProperty("loans")
    private List<Loan> loans;

    @JsonProperty("additional_information")
    private AdditionalInformation additionalInformation;

    public List<Loan> getLoans() {
        return loans;
    }

    public void setLoans(
            List<Loan> loans) {
        this.loans = loans;
    }

    public AdditionalInformation getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(
            AdditionalInformation additionalInformation) {
        this.additionalInformation = additionalInformation;
    }
}
