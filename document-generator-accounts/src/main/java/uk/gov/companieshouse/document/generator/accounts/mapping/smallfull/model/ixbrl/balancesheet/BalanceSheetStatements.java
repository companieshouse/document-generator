package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import java.util.Objects;

@JsonInclude(Include.NON_NULL)
public class BalanceSheetStatements {

    @JsonProperty("section_477")
    private String section477;

    @JsonProperty("audit_not_required_by_members")
    private String auditNotRequiredByMembers;

    @JsonProperty("directors_responsibility")
    private String directorsResponsibility;

    @JsonProperty("small_companies_regime")
    private String smallCompaniesRegime;

    public String getSection477() {
        return section477;
    }

    public void setSection477(String section477) {
        this.section477 = section477;
    }

    public String getAuditNotRequiredByMembers() {
        return auditNotRequiredByMembers;
    }

    public void setAuditNotRequiredByMembers(String auditNotRequiredByMembers) {
        this.auditNotRequiredByMembers = auditNotRequiredByMembers;
    }

    public String getDirectorsResponsibility() {
        return directorsResponsibility;
    }

    public void setDirectorsResponsibility(String directorsResponsibility) {
        this.directorsResponsibility = directorsResponsibility;
    }

    public String getSmallCompaniesRegime() {
        return smallCompaniesRegime;
    }

    public void setSmallCompaniesRegime(String smallCompaniesRegime) {
        this.smallCompaniesRegime = smallCompaniesRegime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BalanceSheetStatements)) return false;
        BalanceSheetStatements that = (BalanceSheetStatements) o;
        return getSection477() == that.getSection477() &&
                getAuditNotRequiredByMembers() == that.getAuditNotRequiredByMembers() &&
                getDirectorsResponsibility() == that.getDirectorsResponsibility() &&
                getSmallCompaniesRegime() == that.getSmallCompaniesRegime();
    }

    @Override
    public int hashCode() {

        return Objects.hash(getSection477(), getAuditNotRequiredByMembers(), getDirectorsResponsibility(),
                            getSmallCompaniesRegime());
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
