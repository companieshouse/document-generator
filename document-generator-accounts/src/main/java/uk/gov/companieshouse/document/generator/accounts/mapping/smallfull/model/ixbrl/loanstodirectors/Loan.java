package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.loanstodirectors;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Loan {

    @JsonProperty("director_name")
    private String directorName;

    @JsonProperty("director_index")
    private Integer directorIndex;

    @JsonProperty("description")
    private String description;

    @JsonProperty("balance_at_period_start")
    private Long balanceAtPeriodStart;

    @JsonProperty("advances_credits_made")
    private Long advancesCreditsMade;

    @JsonProperty("advances_credits_repaid")
    private Long advancesCreditsRepaid;

    @JsonProperty("balance_at_period_end")
    private Long balanceAtPeriodEnd;

    @JsonProperty("director_loan_index")
    private Integer directorLoanIndex;

    public Loan(String directorName, String description, Long balanceAtPeriodStart,
                Long advancesCreditsMade, Long advancesCreditsRepaid, Long balanceAtPeriodEnd) {

        this.directorName = directorName;
        this.description = description;
        this.balanceAtPeriodStart = balanceAtPeriodStart;
        this.advancesCreditsMade = advancesCreditsMade;
        this.advancesCreditsRepaid = advancesCreditsRepaid;
        this.balanceAtPeriodEnd = balanceAtPeriodEnd;
    }

    public String getDirectorName() {
        return directorName;
    }

    public void setDirectorName(String directorName) {
        this.directorName = directorName;
    }

    public Integer getDirectorIndex() {
        return directorIndex;
    }

    public void setDirectorIndex(Integer directorIndex) {
        this.directorIndex = directorIndex;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getBalanceAtPeriodStart() {
        return balanceAtPeriodStart;
    }

    public void setBalanceAtPeriodStart(Long balanceAtPeriodStart) {
        this.balanceAtPeriodStart = balanceAtPeriodStart;
    }

    public Long getAdvancesCreditsMade() {
        return advancesCreditsMade;
    }

    public void setAdvancesCreditsMade(Long advancesCreditsMade) {
        this.advancesCreditsMade = advancesCreditsMade;
    }

    public Long getAdvancesCreditsRepaid() {
        return advancesCreditsRepaid;
    }

    public void setAdvancesCreditsRepaid(Long advancesCreditsRepaid) {
        this.advancesCreditsRepaid = advancesCreditsRepaid;
    }

    public Long getBalanceAtPeriodEnd() {
        return balanceAtPeriodEnd;
    }

    public void setBalanceAtPeriodEnd(Long balanceAtPeriodEnd) {
        this.balanceAtPeriodEnd = balanceAtPeriodEnd;
    }

    public Integer getDirectorLoanIndex() {
        return directorLoanIndex;
    }

    public void setDirectorLoanIndex(Integer directorLoanIndex) {
        this.directorLoanIndex = directorLoanIndex;
    }
}
