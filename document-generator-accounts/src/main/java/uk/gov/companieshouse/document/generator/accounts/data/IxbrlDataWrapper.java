package uk.gov.companieshouse.document.generator.accounts.data;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import uk.gov.companieshouse.document.generator.accounts.mapping.PeriodAwareIxbrl;
import uk.gov.companieshouse.document.generator.accounts.mapping.cic.model.CicReport;

public class IxbrlDataWrapper {

    private Map<String, PeriodAwareIxbrl> accounts;

    @JsonProperty("cic_report")
    private CicReport cicReport;

    @JsonAnyGetter
    public Map<String, PeriodAwareIxbrl> getAccounts() {
        return accounts;
    }

    public void setAccounts(
            Map<String, PeriodAwareIxbrl> accounts) {
        this.accounts = accounts;
    }

    public CicReport getCicReport() {
        return cicReport;
    }

    public void setCicReport(CicReport cicReport) {
        this.cicReport = cicReport;
    }
}
