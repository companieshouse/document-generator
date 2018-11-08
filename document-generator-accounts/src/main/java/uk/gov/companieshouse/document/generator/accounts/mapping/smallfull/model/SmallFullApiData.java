package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model;

import com.google.gson.Gson;
import uk.gov.companieshouse.api.model.accounts.smallfull.CurrentPeriodApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.PreviousPeriodApi;
import uk.gov.companieshouse.api.model.company.CompanyProfileApi;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.period.Period;

import java.util.Objects;

public class SmallFullApiData {

    private CurrentPeriodApi currentPeriodApi;

    private PreviousPeriodApi previousPeriodApi;

    private CompanyProfileApi companyProfileApi;

    private Period period;

    public CurrentPeriodApi getCurrentPeriodApi() {
        return currentPeriodApi;
    }

    public void setCurrentPeriodApi(CurrentPeriodApi currentPeriodApi) {
        this.currentPeriodApi = currentPeriodApi;
    }

    public PreviousPeriodApi getPreviousPeriodApi() {
        return previousPeriodApi;
    }

    public void setPreviousPeriodApi(PreviousPeriodApi previousPeriodApi) {
        this.previousPeriodApi = previousPeriodApi;
    }

    public CompanyProfileApi getCompanyProfileApi() {
        return companyProfileApi;
    }

    public void setCompanyProfileApi(CompanyProfileApi companyProfileApi) {
        this.companyProfileApi = companyProfileApi;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SmallFullApiData)) return false;
        SmallFullApiData that = (SmallFullApiData) o;
        return Objects.equals(getCurrentPeriodApi(), that.getCurrentPeriodApi()) &&
                Objects.equals(getPreviousPeriodApi(), that.getPreviousPeriodApi()) &&
                Objects.equals(getCompanyProfileApi(), that.getCompanyProfileApi());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getCurrentPeriodApi(), getPreviousPeriodApi(), getCompanyProfileApi());
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
