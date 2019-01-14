package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import uk.gov.companieshouse.api.model.accounts.smallfull.tangible.TangibleApi;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.notes.tangible.TangibleAssets;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.notes.tangible.TangibleAssetsColumns;

@Mapper
public interface ApiToTangibleAssetsNoteMapper {

    ApiToTangibleAssetsNoteMapper INSTANCE = Mappers.getMapper(ApiToTangibleAssetsNoteMapper.class);

    @Mappings({
            @Mapping(source = "tangible.fixturesAndFittings.cost.additions",
                    target = "fixturesAndFittings"),
            @Mapping(source = "tangible.landAndBuildings.cost.additions",
                    target = "landAndBuildings"),
            @Mapping(source = "tangible.motorVehicles.cost.additions",
                    target = "motorVehicles"),
            @Mapping(source = "tangible.officeEquipment.cost.additions",
                    target = "officeEquipment"),
            @Mapping(source = "tangible.plantAndMachinery.cost.additions",
                    target = "plantAndMachinery"),
            @Mapping(source = "tangible.total.cost.additions",
                    target = "total")
    })
    TangibleAssetsColumns apiToTangibleAssetsCostAdditionsMapper(TangibleApi tangible);

    @Mappings({
            @Mapping(source = "tangible.fixturesAndFittings.cost.atPeriodEnd",
                    target = "fixturesAndFittings"),
            @Mapping(source = "tangible.landAndBuildings.cost.atPeriodEnd",
                    target = "landAndBuildings"),
            @Mapping(source = "tangible.motorVehicles.cost.atPeriodEnd",
                    target = "motorVehicles"),
            @Mapping(source = "tangible.officeEquipment.cost.atPeriodEnd",
                    target = "officeEquipment"),
            @Mapping(source = "tangible.plantAndMachinery.cost.atPeriodEnd",
                    target = "plantAndMachinery"),
            @Mapping(source = "tangible.total.cost.atPeriodEnd",
                    target = "total")
    })
    TangibleAssetsColumns apiToTangibleAssetsCostAtPeriodEndMapper(TangibleApi tangible);

    @Mappings({
            @Mapping(source = "tangible.fixturesAndFittings.cost.atPeriodStart",
                    target = "fixturesAndFittings"),
            @Mapping(source = "tangible.landAndBuildings.cost.atPeriodStart",
                    target = "landAndBuildings"),
            @Mapping(source = "tangible.motorVehicles.cost.atPeriodStart",
                    target = "motorVehicles"),
            @Mapping(source = "tangible.officeEquipment.cost.atPeriodStart",
                    target = "officeEquipment"),
            @Mapping(source = "tangible.plantAndMachinery.cost.atPeriodStart",
                    target = "plantAndMachinery"),
            @Mapping(source = "tangible.total.cost.atPeriodStart",
                    target = "total")
    })
    TangibleAssetsColumns apiToTangibleAssetsCostAtPeriodStartMapper(TangibleApi tangible);

    @Mappings({
            @Mapping(source = "tangible.fixturesAndFittings.cost.disposals",
                    target = "fixturesAndFittings"),
            @Mapping(source = "tangible.landAndBuildings.cost.disposals",
                    target = "landAndBuildings"),
            @Mapping(source = "tangible.motorVehicles.cost.disposals",
                    target = "motorVehicles"),
            @Mapping(source = "tangible.officeEquipment.cost.disposals",
                    target = "officeEquipment"),
            @Mapping(source = "tangible.plantAndMachinery.cost.disposals",
                    target = "plantAndMachinery"),
            @Mapping(source = "tangible.total.cost.disposals",
                    target = "total")
    })
    TangibleAssetsColumns apiToTangibleAssetsCostDisposalsMapper(TangibleApi tangible);

    @Mappings({
            @Mapping(source = "tangible.fixturesAndFittings.cost.revaluations",
                    target = "fixturesAndFittings"),
            @Mapping(source = "tangible.landAndBuildings.cost.revaluations",
                    target = "landAndBuildings"),
            @Mapping(source = "tangible.motorVehicles.cost.revaluations",
                    target = "motorVehicles"),
            @Mapping(source = "tangible.officeEquipment.cost.revaluations",
                    target = "officeEquipment"),
            @Mapping(source = "tangible.plantAndMachinery.cost.revaluations",
                    target = "plantAndMachinery"),
            @Mapping(source = "tangible.total.cost.revaluations",
                    target = "total")
    })
    TangibleAssetsColumns apiToTangibleAssetsCostRevaluationsMapper(TangibleApi tangible);

    @Mappings({
            @Mapping(source = "tangible.fixturesAndFittings.cost.transfers",
                    target = "fixturesAndFittings"),
            @Mapping(source = "tangible.landAndBuildings.cost.transfers",
                    target = "landAndBuildings"),
            @Mapping(source = "tangible.motorVehicles.cost.transfers",
                    target = "motorVehicles"),
            @Mapping(source = "tangible.officeEquipment.cost.transfers",
                    target = "officeEquipment"),
            @Mapping(source = "tangible.plantAndMachinery.cost.transfers",
                    target = "plantAndMachinery"),
            @Mapping(source = "tangible.total.cost.transfers",
                    target = "total")
    })
    TangibleAssetsColumns apiToTangibleAssetsCostTransfersMapper(TangibleApi tangible);

    @Mappings({
            @Mapping(source = "tangible.fixturesAndFittings.depreciation.atPeriodEnd",
                    target = "fixturesAndFittings"),
            @Mapping(source = "tangible.landAndBuildings.depreciation.atPeriodEnd",
                    target = "landAndBuildings"),
            @Mapping(source = "tangible.motorVehicles.depreciation.atPeriodEnd",
                    target = "motorVehicles"),
            @Mapping(source = "tangible.officeEquipment.depreciation.atPeriodEnd",
                    target = "officeEquipment"),
            @Mapping(source = "tangible.plantAndMachinery.depreciation.atPeriodEnd",
                    target = "plantAndMachinery"),
            @Mapping(source = "tangible.total.depreciation.atPeriodEnd",
                    target = "total")
    })
    TangibleAssetsColumns apiToTangibleAssetsDepreciationAtPeriodEndMapper(TangibleApi tangible);

    @Mappings({
            @Mapping(source = "tangible.fixturesAndFittings.depreciation.atPeriodStart",
                    target = "fixturesAndFittings"),
            @Mapping(source = "tangible.landAndBuildings.depreciation.atPeriodStart",
                    target = "landAndBuildings"),
            @Mapping(source = "tangible.motorVehicles.depreciation.atPeriodStart",
                    target = "motorVehicles"),
            @Mapping(source = "tangible.officeEquipment.depreciation.atPeriodStart",
                    target = "officeEquipment"),
            @Mapping(source = "tangible.plantAndMachinery.depreciation.atPeriodStart",
                    target = "plantAndMachinery"),
            @Mapping(source = "tangible.total.depreciation.atPeriodStart",
                    target = "total")
    })
    TangibleAssetsColumns apiToTangibleAssetsDepreciationAtPeriodStartMapper(TangibleApi tangible);

    @Mappings({
            @Mapping(source = "tangible.fixturesAndFittings.depreciation.chargeForYear",
                    target = "fixturesAndFittings"),
            @Mapping(source = "tangible.landAndBuildings.depreciation.chargeForYear",
                    target = "landAndBuildings"),
            @Mapping(source = "tangible.motorVehicles.depreciation.chargeForYear",
                    target = "motorVehicles"),
            @Mapping(source = "tangible.officeEquipment.depreciation.chargeForYear",
                    target = "officeEquipment"),
            @Mapping(source = "tangible.plantAndMachinery.depreciation.chargeForYear",
                    target = "plantAndMachinery"),
            @Mapping(source = "tangible.total.depreciation.chargeForYear",
                    target = "total")
    })
    TangibleAssetsColumns apiToTangibleAssetsDepreciationChargeForYearMapper(TangibleApi tangible);

    @Mappings({
            @Mapping(source = "tangible.fixturesAndFittings.depreciation.onDisposals",
                    target = "fixturesAndFittings"),
            @Mapping(source = "tangible.landAndBuildings.depreciation.onDisposals",
                    target = "landAndBuildings"),
            @Mapping(source = "tangible.motorVehicles.depreciation.onDisposals",
                    target = "motorVehicles"),
            @Mapping(source = "tangible.officeEquipment.depreciation.onDisposals",
                    target = "officeEquipment"),
            @Mapping(source = "tangible.plantAndMachinery.depreciation.onDisposals",
                    target = "plantAndMachinery"),
            @Mapping(source = "tangible.total.depreciation.onDisposals",
                    target = "total")
    })
    TangibleAssetsColumns apiToTangibleAssetsDepreciationOnDisposalsMapper(TangibleApi tangible);

    @Mappings({
            @Mapping(source = "tangible.fixturesAndFittings.depreciation.otherAdjustments",
                    target = "fixturesAndFittings"),
            @Mapping(source = "tangible.landAndBuildings.depreciation.otherAdjustments",
                    target = "landAndBuildings"),
            @Mapping(source = "tangible.motorVehicles.depreciation.otherAdjustments",
                    target = "motorVehicles"),
            @Mapping(source = "tangible.officeEquipment.depreciation.otherAdjustments",
                    target = "officeEquipment"),
            @Mapping(source = "tangible.plantAndMachinery.depreciation.otherAdjustments",
                    target = "plantAndMachinery"),
            @Mapping(source = "tangible.total.depreciation.otherAdjustments",
                    target = "total")
    })
    TangibleAssetsColumns apiToTangibleAssetsDepreciationOtherAdjustmentsMapper(TangibleApi tangible);

    @Mappings({
            @Mapping(source = "tangible.fixturesAndFittings.netBookValueAtEndOfCurrentPeriod",
                    target = "fixturesAndFittings"),
            @Mapping(source = "tangible.landAndBuildings.netBookValueAtEndOfCurrentPeriod",
                    target = "landAndBuildings"),
            @Mapping(source = "tangible.motorVehicles.netBookValueAtEndOfCurrentPeriod",
                    target = "motorVehicles"),
            @Mapping(source = "tangible.officeEquipment.netBookValueAtEndOfCurrentPeriod",
                    target = "officeEquipment"),
            @Mapping(source = "tangible.plantAndMachinery.netBookValueAtEndOfCurrentPeriod",
                    target = "plantAndMachinery"),
            @Mapping(source = "tangible.total.netBookValueAtEndOfCurrentPeriod",
                    target = "total"),
    })
    TangibleAssetsColumns apiToTangibleAssetsNetBookValueCurrentPeriodMapper(TangibleApi tangible);

    @Mappings({
            @Mapping(source = "tangible.fixturesAndFittings.netBookValueAtEndOfPreviousPeriod",
                    target = "fixturesAndFittings"),
            @Mapping(source = "tangible.landAndBuildings.netBookValueAtEndOfPreviousPeriod",
                    target = "landAndBuildings"),
            @Mapping(source = "tangible.motorVehicles.netBookValueAtEndOfPreviousPeriod",
                    target = "motorVehicles"),
            @Mapping(source = "tangible.officeEquipment.netBookValueAtEndOfPreviousPeriod",
                    target = "officeEquipment"),
            @Mapping(source = "tangible.plantAndMachinery.netBookValueAtEndOfPreviousPeriod",
                    target = "plantAndMachinery"),
            @Mapping(source = "tangible.total.netBookValueAtEndOfPreviousPeriod",
                    target = "total")
    })
    TangibleAssetsColumns apiToTangibleAssetsNetBookValuePreviousPeriodMapper(TangibleApi tangible);

    @Mappings({
        @Mapping(source = "tangible.additionalInformation",
                target = "additionalInformation")
    })
    TangibleAssets apiToTangibleAssetsNoteAdditionalInformation(TangibleApi tangible);
}
