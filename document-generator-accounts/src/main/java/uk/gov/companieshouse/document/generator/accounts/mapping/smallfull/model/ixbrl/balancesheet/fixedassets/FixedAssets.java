package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.fixedassets;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.fixedassets.items.TangibleAssets;

import java.util.Objects;

public class FixedAssets {

    @JsonProperty("tangible")
    public TangibleAssets tangibleAssets;

    @JsonProperty("current_total")
    public long totalFixedAssetsCurrent;

    @JsonProperty("previous_total")
    public long totalFixedAssetsPrevious;

    public TangibleAssets getTangibleAssets() {
        return tangibleAssets;
    }

    public void setTangibleAssets(TangibleAssets tangibleAssets) {
        this.tangibleAssets = tangibleAssets;
    }

    public long getTotalFixedAssetsCurrent() {
        return totalFixedAssetsCurrent;
    }

    public void setTotalFixedAssetsCurrent(long totalFixedAssetsCurrent) {
        this.totalFixedAssetsCurrent = totalFixedAssetsCurrent;
    }

    public long getTotalFixedAssetsPrevious() {
        return totalFixedAssetsPrevious;
    }

    public void setTotalFixedAssetsPrevious(long totalFixedAssetsPrevious) {
        this.totalFixedAssetsPrevious = totalFixedAssetsPrevious;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FixedAssets)) return false;
        FixedAssets that = (FixedAssets) o;
        return getTotalFixedAssetsCurrent() == that.getTotalFixedAssetsCurrent() &&
                getTotalFixedAssetsPrevious() == that.getTotalFixedAssetsPrevious() &&
                Objects.equals(getTangibleAssets(), that.getTangibleAssets());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getTangibleAssets(), getTotalFixedAssetsCurrent(), getTotalFixedAssetsPrevious());
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
