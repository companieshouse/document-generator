package uk.gov.companieshouse.document.generator.accounts.mapper.smallfull.model.ixbrl.balancesheet.fixedassets;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import uk.gov.companieshouse.document.generator.accounts.mapper.smallfull.model.ixbrl.balancesheet.fixedassets.items.TangibleAssets;

import java.util.Objects;

public class FixedAssets {

    @JsonProperty("tangible")
    public TangibleAssets tangibleAssets;

    @JsonProperty("current_total")
    public int totalFixedAssetsCurrent;

    @JsonProperty("previous_total")
    public int totalFixedAssetsPrevious;

    public TangibleAssets getTangibleAssets() {
        return tangibleAssets;
    }

    public void setTangibleAssets(TangibleAssets tangibleAssets) {
        this.tangibleAssets = tangibleAssets;
    }

    public int getTotalFixedAssetsCurrent() {
        return totalFixedAssetsCurrent;
    }

    public void setTotalFixedAssetsCurrent(int totalFixedAssetsCurrent) {
        this.totalFixedAssetsCurrent = totalFixedAssetsCurrent;
    }

    public int getTotalFixedAssetsPrevious() {
        return totalFixedAssetsPrevious;
    }

    public void setTotalFixedAssetsPrevious(int totalFixedAssetsPrevious) {
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
