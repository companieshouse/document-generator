package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model;

import com.google.gson.Gson;
import uk.gov.companieshouse.api.model.accounts.smallfull.ApprovalApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.CurrentPeriodApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.PreviousPeriodApi;
import uk.gov.companieshouse.api.model.company.CompanyProfileApi;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.period.Period;

import java.util.Objects;

public class SmallFullApiData {

    private CurrentPeriodApi currentPeriod;

    private PreviousPeriodApi previousPeriod;

    private CompanyProfileApi companyProfile;

    private ApprovalApi approval;

    private Period period;

    public CurrentPeriodApi getCurrentPeriod() {
        return currentPeriod;
    }

    public void setCurrentPeriod(CurrentPeriodApi currentPeriod) {
        this.currentPeriod = currentPeriod;
    }

    public PreviousPeriodApi getPreviousPeriod() {
        return previousPeriod;
    }

    public void setPreviousPeriod(PreviousPeriodApi previousPeriod) {
        this.previousPeriod = previousPeriod;
    }

    public CompanyProfileApi getCompanyProfile() {
        return companyProfile;
    }

    public void setCompanyProfile(CompanyProfileApi companyProfile) {
        this.companyProfile = companyProfile;
    }

    public ApprovalApi getApproval() {
        return approval;
    }

    public void setApproval(ApprovalApi approval) {
        this.approval = approval;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SmallFullApiData)) return false;
        SmallFullApiData that = (SmallFullApiData) o;
        return Objects.equals(getCurrentPeriod(), that.getCurrentPeriod()) &&
                Objects.equals(getPreviousPeriod(), that.getPreviousPeriod()) &&
                Objects.equals(getCompanyProfile(), that.getCompanyProfile()) &&
                Objects.equals(getApproval(), that.getApproval()) &&
                Objects.equals(getPeriod(), that.getPeriod());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getCurrentPeriod(), getPreviousPeriod(), getCompanyProfile(), getApproval(), getPeriod());
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
