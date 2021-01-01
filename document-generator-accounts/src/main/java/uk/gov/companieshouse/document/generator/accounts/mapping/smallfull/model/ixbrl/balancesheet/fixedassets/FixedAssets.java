package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.fixedassets;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.fixedassets.items.IntangibleAssets;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.fixedassets.items.Investments;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.fixedassets.items.TangibleAssets;

import java.util.Objects;

@JsonInclude(Include.NON_NULL)
public class FixedAssets {

    @JsonProperty("tangible")
    public TangibleAssets tangibleAssets;

    @JsonProperty("intangible")
    public IntangibleAssets intangibleAssets;

    public IntangibleAssets getIntangibleAssets() {
        return intangibleAssets;
    }

    public void setIntangibleAssets(IntangibleAssets intangibleAssets) {
        this.intangibleAssets = intangibleAssets;
    }

    @JsonProperty("investments")
    public Investments investments;

    @JsonProperty("current_total")
    public Long totalFixedAssetsCurrent;

    @JsonProperty("previous_total")
    public Long totalFixedAssetsPrevious;

    public TangibleAssets getTangibleAssets() {
        return tangibleAssets;
    }

    public void setTangibleAssets(TangibleAssets tangibleAssets) {
        this.tangibleAssets = tangibleAssets;
    }

    public Long getTotalFixedAssetsCurrent() {
        return totalFixedAssetsCurrent;
    }

    public void setTotalFixedAssetsCurrent(Long totalFixedAssetsCurrent) {
        this.totalFixedAssetsCurrent = totalFixedAssetsCurrent;
    }

    public Long getTotalFixedAssetsPrevious() {
        return totalFixedAssetsPrevious;
    }

    public void setTotalFixedAssetsPrevious(Long totalFixedAssetsPrevious) {
        this.totalFixedAssetsPrevious = totalFixedAssetsPrevious;
    }

    public Investments getInvestments() {
        return investments;
    }

    public void setInvestments(Investments investments) {
        this.investments = investments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FixedAssets that = (FixedAssets) o;
        return Objects.equals(tangibleAssets, that.tangibleAssets) &&
                Objects.equals(intangibleAssets, that.intangibleAssets) &&
                Objects.equals(investments, that.investments) &&
                Objects.equals(totalFixedAssetsCurrent, that.totalFixedAssetsCurrent) &&
                Objects.equals(totalFixedAssetsPrevious, that.totalFixedAssetsPrevious);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTangibleAssets(), getIntangibleAssets(), getInvestments(), getTotalFixedAssetsCurrent(), getTotalFixedAssetsPrevious());
    }

    @Override
    public String toString() {
        return "FixedAssets{" +
                "tangibleAssets=" + tangibleAssets +
                ", intangibleAssets=" + intangibleAssets +
                ", investments=" + investments +
                ", totalFixedAssetsCurrent=" + totalFixedAssetsCurrent +
                ", totalFixedAssetsPrevious=" + totalFixedAssetsPrevious +
                '}';
    }
}
