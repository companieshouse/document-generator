package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.web.context.annotation.RequestScope;
import uk.gov.companieshouse.api.model.accounts.smallfull.intangible.IntangibleApi;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.notes.intangible.IntangibleAssets;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.notes.intangible.IntangibleAssetsColumns;

@RequestScope
@Mapper(componentModel = "spring")
public interface ApiToIntangibleAssetsNoteMapper {

    @Mappings({
            @Mapping(source = "intangible.goodwill.cost.additions",
                    target = "goodwill"),
            @Mapping(source = "intangible.otherIntangibleAssets.cost.additions",
            target = "otherIntangibleAssets"),
            @Mapping(source = "intangible.total.cost.additions",
            target = "total")
    })
    IntangibleAssetsColumns apiToIntangibleAssetsCostAdditionsMapper(IntangibleApi intangible);

    @Mappings({
            @Mapping(source = "intangible.goodwill.cost.atPeriodEnd",
            target = "goodwill"),
            @Mapping(source = "intangible.otherIntangibleAssets.cost.atPeriodEnd",
            target = "otherIntangibleAssets"),
            @Mapping(source = "intangible.total.cost.atPeriodEnd",
            target = "total")
    })

    IntangibleAssetsColumns apiToIntangibleAssetsCostAtPeriodEndMapper(IntangibleApi intangible);

    @Mappings({
            @Mapping(source = "intangible.goodwill.cost.atPeriodStart",
            target = "goodwill"),
            @Mapping(source = "intangible.otherIntangibleAssets.cost.atPeriodStart",
            target = "otherIntangibleAssets"),
            @Mapping(source = "intangible.total.cost.atPeriodStart",
            target = "total")
    })
    IntangibleAssetsColumns apiToIntangibleAssetsCostAtPeriodStartMapper(IntangibleApi intangible);

    @Mappings({
            @Mapping(source = "intangible.goodwill.cost.disposals",
            target = "goodwill"),
            @Mapping(source = "intangible.otherIntangibleAssets.cost.disposals",
            target = "otherIntangibleAssets"),
            @Mapping(source = "intangible.total.cost.disposals",
            target = "total")
    })
    IntangibleAssetsColumns apiToIntangibleAssetsCostDisposalsMapper(IntangibleApi intangible);

    @Mappings({
            @Mapping(source = "intangible.goodwill.cost.revaluations",
            target = "goodwill"),
            @Mapping(source = "intangible.otherIntangibleAssets.cost.revaluations",
            target = "otherIntangibleAssets"),
            @Mapping(source = "intangible.total.cost.revaluations",
            target = "total")
    })
    IntangibleAssetsColumns apiToIntangibleAssetsCostRevaluationsMapper(IntangibleApi intangible);

    @Mappings({
            @Mapping(source = "intangible.goodwill.cost.transfers",
            target = "goodwill"),
            @Mapping(source = "intangible.otherIntangibleAssets.cost.transfers",
            target = "otherIntangibleAssets"),
            @Mapping(source = "intangible.total.cost.transfers",
            target = "total")
    })
    IntangibleAssetsColumns apiToIntangibleAssetsCostTransfersMapper(IntangibleApi intangible);

    @Mappings({
            @Mapping(source = "intangible.goodwill.amortisation.onDisposals",
            target = "goodwill"),
            @Mapping(source = "intangible.otherIntangibleAssets.amortisation.onDisposals",
            target = "otherIntangibleAssets"),
            @Mapping(source = "intangible.total.amortisation.onDisposals",
            target = "total")
    })
    IntangibleAssetsColumns apiToIntangibleAssetsAmortisationOnDisposalsMapper(IntangibleApi intangible);

    @Mappings({
            @Mapping(source = "intangible.goodwill.amortisation.atPeriodEnd",
            target = "goodwill"),
            @Mapping(source = "intangible.otherIntangibleAssets.amortisation.atPeriodEnd",
            target = "otherIntangibleAssets"),
            @Mapping(source = "intangible.total.amortisation.atPeriodEnd",
                    target = "total")

    })
    IntangibleAssetsColumns apiToIntangibleAssetsAmortisationAtPeriodEndMapper(IntangibleApi intangible);

    @Mappings({
            @Mapping(source = "intangible.goodwill.amortisation.atPeriodStart",
            target = "goodwill"),
            @Mapping(source = "intangible.otherIntangibleAssets.amortisation.atPeriodStart",
            target = "otherIntangibleAssets"),
            @Mapping(source = "intangible.total.amortisation.atPeriodStart",
            target = "total")
    })
    IntangibleAssetsColumns apiToIntangibleAssetsAmortisationAtPeriodStartMapper(IntangibleApi intangible);

    @Mappings({
            @Mapping(source = "intangible.goodwill.amortisation.chargeForYear",
            target =  "goodwill"),
            @Mapping(source = "intangible.otherIntangibleAssets.amortisation.chargeForYear",
            target = "otherIntangibleAssets"),
            @Mapping(source = "intangible.total.amortisation.chargeForYear",
            target = "total")
    })
    IntangibleAssetsColumns apiToIntangibleAssetsAmortisationChargeForYearMapper(IntangibleApi intangible);

    @Mappings({
            @Mapping(source = "intangible.goodwill.amortisation.otherAdjustments",
            target = "goodwill"),
            @Mapping(source = "intangible.otherIntangibleAssets.amortisation.otherAdjustments",
            target = "otherIntangibleAssets"),
            @Mapping(source = "intangible.total.amortisation.otherAdjustments",
            target = "total")
    })
    IntangibleAssetsColumns apiToIntangibleAssetsAmortisationOtherAdjustmentsMapper(IntangibleApi intangible);

    @Mappings({
            @Mapping(source = "intangible.goodwill.netBookValueAtEndOfCurrentPeriod",
            target = "goodwill"),
            @Mapping(source = "intangible.otherIntangibleAssets.netBookValueAtEndOfCurrentPeriod",
            target = "otherIntangibleAssets"),
            @Mapping(source = "intangible.total.netBookValueAtEndOfCurrentPeriod",
            target = "total")
    })
    IntangibleAssetsColumns apiToIntangibleAssetsNetBookValueCurrentPeriodMapper(IntangibleApi intangible);

    @Mappings({
            @Mapping(source = "intangible.goodwill.netBookValueAtEndOfPreviousPeriod",
            target = "goodwill"),
            @Mapping(source = "intangible.otherIntangibleAssets.netBookValueAtEndOfPreviousPeriod",
            target = "otherIntangibleAssets"),
            @Mapping(source = "intangible.total.netBookValueAtEndOfPreviousPeriod",
            target = "total")
    })
    IntangibleAssetsColumns apiToIntangibleAssetsNetBookValuePreviousPeriodMapper(IntangibleApi intangible);

    @Mappings({
            @Mapping(source = "intangible.additionalInformation",
            target = "additionalInformation")
    })
    IntangibleAssets apiToIntangibleAssetsNoteAdditionalInformation(IntangibleApi intangible);

}
