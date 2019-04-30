package uk.gov.companieshouse.document.generator.prosecution.tmpclient;

enum ProsecutionCaseStatus {
    PREPARING("Preparing"),
    REFERRED("Referred"),
    ACCEPTED("Accepted"),
    REJECTED("Rejected"),
    ULTIMATUM_ISSUED("Ultimatum Issued"),
    SJPN_ISSUED("SJPN Issued"),
    OUTCOME_ENTERED("Outcome Entered"),
    CLOSED("Closed"),
    ADJOURNED("Adjourned"),
    RESTARTED("Restarted"),
    REOPENED("Reopened"),
    APPEALED("Appealed"),
    POST_CONVICTION("Post Conviction");

    private String value;
    
    ProsecutionCaseStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
