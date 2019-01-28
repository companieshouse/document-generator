package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uk.gov.companieshouse.api.model.accounts.smallfull.tangible.Cost;
import uk.gov.companieshouse.api.model.accounts.smallfull.tangible.Depreciation;
import uk.gov.companieshouse.api.model.accounts.smallfull.tangible.TangibleApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.tangible.TangibleAssetsResource;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.notes.tangible.TangibleAssets;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.notes.tangible.TangibleAssetsCost;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.notes.tangible.TangibleAssetsDepreciation;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.notes.tangible.TangibleAssetsNetBookValue;

public class ApiToTangibleAssetsNoteMapperTest {

    @Test
    @DisplayName("test cost additions API values map to iXBRL model")
    public void testCostAdditionsAPIToIXBRL() {
        TangibleApi tangibleApi = createTangibleAssets();

        TangibleAssets tangible = new TangibleAssets();
        TangibleAssetsCost cost = new TangibleAssetsCost();

        cost.setAdditions(ApiToTangibleAssetsNoteMapper.INSTANCE.apiToTangibleAssetsCostAdditionsMapper(tangibleApi));

        tangible.setCost(cost);

        assertNotNull(tangible);
        assertNotNull(tangible.getCost());
        assertNotNull(tangible.getCost().getAdditions());
        assertNotNull(tangible.getCost().getAdditions().getLandAndBuildings());
        assertNotNull(tangible.getCost().getAdditions().getFixturesAndFittings());
        assertNotNull(tangible.getCost().getAdditions().getMotorVehicles());
        assertNotNull(tangible.getCost().getAdditions().getOfficeEquipment());
        assertNotNull(tangible.getCost().getAdditions().getPlantAndMachinery());
        assertNotNull(tangible.getCost().getAdditions().getTotal());

        assertEquals((Long)2L, tangible.getCost().getAdditions().getLandAndBuildings());
        assertEquals((Long)28L, tangible.getCost().getAdditions().getFixturesAndFittings());
        assertEquals((Long)54L, tangible.getCost().getAdditions().getMotorVehicles());
        assertEquals((Long)41L, tangible.getCost().getAdditions().getOfficeEquipment());
        assertEquals((Long)15L, tangible.getCost().getAdditions().getPlantAndMachinery());
        assertEquals((Long)67L, tangible.getCost().getAdditions().getTotal());
    }

    @Test
    @DisplayName("test cost at period end API values map to iXBRL model")
    public void testCostAtPeriodEndAPIToIXBRL() {

        TangibleApi tangibleApi = createTangibleAssets();

        TangibleAssets tangible = new TangibleAssets();
        TangibleAssetsCost cost = new TangibleAssetsCost();

        cost.setAtPeriodEnd(ApiToTangibleAssetsNoteMapper.INSTANCE.apiToTangibleAssetsCostAtPeriodEndMapper(tangibleApi));
        tangible.setCost(cost);

        assertNotNull(tangible);
        assertNotNull(tangible.getCost());
        assertNotNull(tangible.getCost().getAtPeriodEnd());
        assertNotNull(tangible.getCost().getAtPeriodEnd().getLandAndBuildings());
        assertNotNull(tangible.getCost().getAtPeriodEnd().getFixturesAndFittings());
        assertNotNull(tangible.getCost().getAtPeriodEnd().getMotorVehicles());
        assertNotNull(tangible.getCost().getAtPeriodEnd().getOfficeEquipment());
        assertNotNull(tangible.getCost().getAtPeriodEnd().getPlantAndMachinery());
        assertNotNull(tangible.getCost().getAtPeriodEnd().getTotal());

        assertEquals((Long)6L, tangible.getCost().getAtPeriodEnd().getLandAndBuildings());
        assertEquals((Long)32L, tangible.getCost().getAtPeriodEnd().getFixturesAndFittings());
        assertEquals((Long)58L, tangible.getCost().getAtPeriodEnd().getMotorVehicles());
        assertEquals((Long)45L, tangible.getCost().getAtPeriodEnd().getOfficeEquipment());
        assertEquals((Long)19L, tangible.getCost().getAtPeriodEnd().getPlantAndMachinery());
        assertEquals((Long)71L, tangible.getCost().getAtPeriodEnd().getTotal());
    }

    @Test
    @DisplayName("test cost at period start API values map to iXBRL model")
    public void testCostAtPeriodStartAPIToIXBRL() {

        TangibleApi tangibleApi = createTangibleAssets();

        TangibleAssets tangible = new TangibleAssets();
        TangibleAssetsCost cost = new TangibleAssetsCost();

        cost.setAtPeriodStart(ApiToTangibleAssetsNoteMapper.INSTANCE.apiToTangibleAssetsCostAtPeriodStartMapper(tangibleApi));
        tangible.setCost(cost);

        assertNotNull(tangible);
        assertNotNull(tangible.getCost());
        assertNotNull(tangible.getCost().getAtPeriodStart());
        assertNotNull(tangible.getCost().getAtPeriodStart().getLandAndBuildings());
        assertNotNull(tangible.getCost().getAtPeriodStart().getFixturesAndFittings());
        assertNotNull(tangible.getCost().getAtPeriodStart().getMotorVehicles());
        assertNotNull(tangible.getCost().getAtPeriodStart().getOfficeEquipment());
        assertNotNull(tangible.getCost().getAtPeriodStart().getPlantAndMachinery());
        assertNotNull(tangible.getCost().getAtPeriodStart().getTotal());

        assertEquals((Long)1L, tangible.getCost().getAtPeriodStart().getLandAndBuildings());
        assertEquals((Long)27L, tangible.getCost().getAtPeriodStart().getFixturesAndFittings());
        assertEquals((Long)53L, tangible.getCost().getAtPeriodStart().getMotorVehicles());
        assertEquals((Long)40L, tangible.getCost().getAtPeriodStart().getOfficeEquipment());
        assertEquals((Long)14L, tangible.getCost().getAtPeriodStart().getPlantAndMachinery());
        assertEquals((Long)66L, tangible.getCost().getAtPeriodStart().getTotal());
    }

    @Test
    @DisplayName("test cost disposals API values map to iXBRL model")
    public void testCostDisposalsAPIToIXBRL() {

        TangibleApi tangibleApi = createTangibleAssets();

        TangibleAssets tangible = new TangibleAssets();
        TangibleAssetsCost cost = new TangibleAssetsCost();

        cost.setDisposals(ApiToTangibleAssetsNoteMapper.INSTANCE.apiToTangibleAssetsCostDisposalsMapper(tangibleApi));
        tangible.setCost(cost);

        assertNotNull(tangible);
        assertNotNull(tangible.getCost());
        assertNotNull(tangible.getCost().getDisposals());
        assertNotNull(tangible.getCost().getDisposals().getLandAndBuildings());
        assertNotNull(tangible.getCost().getDisposals().getFixturesAndFittings());
        assertNotNull(tangible.getCost().getDisposals().getMotorVehicles());
        assertNotNull(tangible.getCost().getDisposals().getOfficeEquipment());
        assertNotNull(tangible.getCost().getDisposals().getPlantAndMachinery());
        assertNotNull(tangible.getCost().getDisposals().getTotal());

        assertEquals((Long)3L, tangible.getCost().getDisposals().getLandAndBuildings());
        assertEquals((Long)29L, tangible.getCost().getDisposals().getFixturesAndFittings());
        assertEquals((Long)55L, tangible.getCost().getDisposals().getMotorVehicles());
        assertEquals((Long)42L, tangible.getCost().getDisposals().getOfficeEquipment());
        assertEquals((Long)16L, tangible.getCost().getDisposals().getPlantAndMachinery());
        assertEquals((Long)68L, tangible.getCost().getDisposals().getTotal());
    }

    @Test
    @DisplayName("test cost revaluation API values map to iXBRL model")
    public void testCostRevaluationsAPIToIXBRL() {

        TangibleApi tangibleApi = createTangibleAssets();

        TangibleAssets tangible = new TangibleAssets();
        TangibleAssetsCost cost = new TangibleAssetsCost();

        cost.setRevaluations(ApiToTangibleAssetsNoteMapper.INSTANCE.apiToTangibleAssetsCostRevaluationsMapper(tangibleApi));
        tangible.setCost(cost);

        assertNotNull(tangible);
        assertNotNull(tangible.getCost());
        assertNotNull(tangible.getCost().getRevaluations());
        assertNotNull(tangible.getCost().getRevaluations().getLandAndBuildings());
        assertNotNull(tangible.getCost().getRevaluations().getFixturesAndFittings());
        assertNotNull(tangible.getCost().getRevaluations().getMotorVehicles());
        assertNotNull(tangible.getCost().getRevaluations().getOfficeEquipment());
        assertNotNull(tangible.getCost().getRevaluations().getPlantAndMachinery());
        assertNotNull(tangible.getCost().getRevaluations().getTotal());

        assertEquals((Long)4L, tangible.getCost().getRevaluations().getLandAndBuildings());
        assertEquals((Long)30L, tangible.getCost().getRevaluations().getFixturesAndFittings());
        assertEquals((Long)56L, tangible.getCost().getRevaluations().getMotorVehicles());
        assertEquals((Long)43L, tangible.getCost().getRevaluations().getOfficeEquipment());
        assertEquals((Long)17L, tangible.getCost().getRevaluations().getPlantAndMachinery());
        assertEquals((Long)69L, tangible.getCost().getRevaluations().getTotal());
    }

    @Test
    @DisplayName("test cost transfers API values map to iXBRL model")
    public void testCostTransfersAPIToIXBRL() {

        TangibleApi tangibleApi = createTangibleAssets();

        TangibleAssets tangible = new TangibleAssets();
        TangibleAssetsCost cost = new TangibleAssetsCost();

        cost.setTransfers(ApiToTangibleAssetsNoteMapper.INSTANCE.apiToTangibleAssetsCostTransfersMapper(tangibleApi));
        tangible.setCost(cost);

        assertNotNull(tangible);
        assertNotNull(tangible.getCost());
        assertNotNull(tangible.getCost().getTransfers());
        assertNotNull(tangible.getCost().getTransfers().getLandAndBuildings());
        assertNotNull(tangible.getCost().getTransfers().getFixturesAndFittings());
        assertNotNull(tangible.getCost().getTransfers().getMotorVehicles());
        assertNotNull(tangible.getCost().getTransfers().getOfficeEquipment());
        assertNotNull(tangible.getCost().getTransfers().getPlantAndMachinery());
        assertNotNull(tangible.getCost().getTransfers().getTotal());

        assertEquals((Long)5L, tangible.getCost().getTransfers().getLandAndBuildings());
        assertEquals((Long)31L, tangible.getCost().getTransfers().getFixturesAndFittings());
        assertEquals((Long)57L, tangible.getCost().getTransfers().getMotorVehicles());
        assertEquals((Long)44L, tangible.getCost().getTransfers().getOfficeEquipment());
        assertEquals((Long)18L, tangible.getCost().getTransfers().getPlantAndMachinery());
        assertEquals((Long)70L, tangible.getCost().getTransfers().getTotal());
    }

    @Test
    @DisplayName("test depreciation at period end API values map to iXBRL model")
    public void testDepreciationAtPeriodEndAPIToIXBRL() {

        TangibleApi tangibleApi = createTangibleAssets();

        TangibleAssets tangible = new TangibleAssets();
        TangibleAssetsDepreciation depreciation = new TangibleAssetsDepreciation();

        depreciation.setAtPeriodEnd(ApiToTangibleAssetsNoteMapper.INSTANCE
                .apiToTangibleAssetsDepreciationAtPeriodEndMapper(tangibleApi));
        tangible.setDepreciation(depreciation);

        assertNotNull(tangible);
        assertNotNull(tangible.getDepreciation());
        assertNotNull(tangible.getDepreciation().getAtPeriodEnd());
        assertNotNull(tangible.getDepreciation().getAtPeriodEnd().getLandAndBuildings());
        assertNotNull(tangible.getDepreciation().getAtPeriodEnd().getFixturesAndFittings());
        assertNotNull(tangible.getDepreciation().getAtPeriodEnd().getMotorVehicles());
        assertNotNull(tangible.getDepreciation().getAtPeriodEnd().getOfficeEquipment());
        assertNotNull(tangible.getDepreciation().getAtPeriodEnd().getPlantAndMachinery());
        assertNotNull(tangible.getDepreciation().getAtPeriodEnd().getTotal());

        assertEquals((Long)11L, tangible.getDepreciation().getAtPeriodEnd().getLandAndBuildings());
        assertEquals((Long)37L, tangible.getDepreciation().getAtPeriodEnd().getFixturesAndFittings());
        assertEquals((Long)63L, tangible.getDepreciation().getAtPeriodEnd().getMotorVehicles());
        assertEquals((Long)50L, tangible.getDepreciation().getAtPeriodEnd().getOfficeEquipment());
        assertEquals((Long)24L, tangible.getDepreciation().getAtPeriodEnd().getPlantAndMachinery());
        assertEquals((Long)76L, tangible.getDepreciation().getAtPeriodEnd().getTotal());
    }

    @Test
    @DisplayName("test depreciation at period start API values map to iXBRL model")
    public void testDepreciationAtPeriodStartAPIToIXBRL() {

        TangibleApi tangibleApi = createTangibleAssets();

        TangibleAssets tangible = new TangibleAssets();
        TangibleAssetsDepreciation depreciation = new TangibleAssetsDepreciation();

        depreciation.setAtPeriodStart(ApiToTangibleAssetsNoteMapper.INSTANCE
                .apiToTangibleAssetsDepreciationAtPeriodStartMapper(tangibleApi));
        tangible.setDepreciation(depreciation);

        assertNotNull(tangible);
        assertNotNull(tangible.getDepreciation());
        assertNotNull(tangible.getDepreciation().getAtPeriodStart());
        assertNotNull(tangible.getDepreciation().getAtPeriodStart().getLandAndBuildings());
        assertNotNull(tangible.getDepreciation().getAtPeriodStart().getFixturesAndFittings());
        assertNotNull(tangible.getDepreciation().getAtPeriodStart().getMotorVehicles());
        assertNotNull(tangible.getDepreciation().getAtPeriodStart().getOfficeEquipment());
        assertNotNull(tangible.getDepreciation().getAtPeriodStart().getPlantAndMachinery());
        assertNotNull(tangible.getDepreciation().getAtPeriodStart().getTotal());

        assertEquals((Long)7L, tangible.getDepreciation().getAtPeriodStart().getLandAndBuildings());
        assertEquals((Long)33L, tangible.getDepreciation().getAtPeriodStart().getFixturesAndFittings());
        assertEquals((Long)59L, tangible.getDepreciation().getAtPeriodStart().getMotorVehicles());
        assertEquals((Long)46L, tangible.getDepreciation().getAtPeriodStart().getOfficeEquipment());
        assertEquals((Long)20L, tangible.getDepreciation().getAtPeriodStart().getPlantAndMachinery());
        assertEquals((Long)72L, tangible.getDepreciation().getAtPeriodStart().getTotal());
    }

    @Test
    @DisplayName("test depreciation charge for year API values map to iXBRL model")
    public void testDepreciationChargeForYearAPIToIXBRL() {

        TangibleApi tangibleApi = createTangibleAssets();

        TangibleAssets tangible = new TangibleAssets();
        TangibleAssetsDepreciation depreciation = new TangibleAssetsDepreciation();

        depreciation.setChargeForYear(ApiToTangibleAssetsNoteMapper.INSTANCE
                .apiToTangibleAssetsDepreciationChargeForYearMapper(tangibleApi));
        tangible.setDepreciation(depreciation);

        assertNotNull(tangible);
        assertNotNull(tangible.getDepreciation());
        assertNotNull(tangible.getDepreciation().getChargeForYear());
        assertNotNull(tangible.getDepreciation().getChargeForYear().getLandAndBuildings());
        assertNotNull(tangible.getDepreciation().getChargeForYear().getFixturesAndFittings());
        assertNotNull(tangible.getDepreciation().getChargeForYear().getMotorVehicles());
        assertNotNull(tangible.getDepreciation().getChargeForYear().getOfficeEquipment());
        assertNotNull(tangible.getDepreciation().getChargeForYear().getPlantAndMachinery());
        assertNotNull(tangible.getDepreciation().getChargeForYear().getTotal());

        assertEquals((Long)8L, tangible.getDepreciation().getChargeForYear().getLandAndBuildings());
        assertEquals((Long)34L, tangible.getDepreciation().getChargeForYear().getFixturesAndFittings());
        assertEquals((Long)60L, tangible.getDepreciation().getChargeForYear().getMotorVehicles());
        assertEquals((Long)47L, tangible.getDepreciation().getChargeForYear().getOfficeEquipment());
        assertEquals((Long)21L, tangible.getDepreciation().getChargeForYear().getPlantAndMachinery());
        assertEquals((Long)73L, tangible.getDepreciation().getChargeForYear().getTotal());
    }

    @Test
    @DisplayName("test depreciation on disposals API values map to iXBRL model")
    public void testDepreciationOnDisposalsAPIToIXBRL() {

        TangibleApi tangibleApi = createTangibleAssets();

        TangibleAssets tangible = new TangibleAssets();
        TangibleAssetsDepreciation depreciation = new TangibleAssetsDepreciation();

        depreciation.setOnDisposals(ApiToTangibleAssetsNoteMapper.INSTANCE
                .apiToTangibleAssetsDepreciationOnDisposalsMapper(tangibleApi));
        tangible.setDepreciation(depreciation);

        assertNotNull(tangible);
        assertNotNull(tangible.getDepreciation());
        assertNotNull(tangible.getDepreciation().getOnDisposals());
        assertNotNull(tangible.getDepreciation().getOnDisposals().getLandAndBuildings());
        assertNotNull(tangible.getDepreciation().getOnDisposals().getFixturesAndFittings());
        assertNotNull(tangible.getDepreciation().getOnDisposals().getMotorVehicles());
        assertNotNull(tangible.getDepreciation().getOnDisposals().getOfficeEquipment());
        assertNotNull(tangible.getDepreciation().getOnDisposals().getPlantAndMachinery());
        assertNotNull(tangible.getDepreciation().getOnDisposals().getTotal());

        assertEquals((Long)9L, tangible.getDepreciation().getOnDisposals().getLandAndBuildings());
        assertEquals((Long)35L, tangible.getDepreciation().getOnDisposals().getFixturesAndFittings());
        assertEquals((Long)61L, tangible.getDepreciation().getOnDisposals().getMotorVehicles());
        assertEquals((Long)48L, tangible.getDepreciation().getOnDisposals().getOfficeEquipment());
        assertEquals((Long)22L, tangible.getDepreciation().getOnDisposals().getPlantAndMachinery());
        assertEquals((Long)74L, tangible.getDepreciation().getOnDisposals().getTotal());
    }

    @Test
    @DisplayName("test depreciation other adjustments API values map to iXBRL model")
    public void testDepreciationOtherAdjustmentsAPIToIXBRL() {

        TangibleApi tangibleApi = createTangibleAssets();

        TangibleAssets tangible = new TangibleAssets();
        TangibleAssetsDepreciation depreciation = new TangibleAssetsDepreciation();

        depreciation.setOtherAdjustments(ApiToTangibleAssetsNoteMapper.INSTANCE
                .apiToTangibleAssetsDepreciationOtherAdjustmentsMapper(tangibleApi));
        tangible.setDepreciation(depreciation);

        assertNotNull(tangible);
        assertNotNull(tangible.getDepreciation());
        assertNotNull(tangible.getDepreciation().getOtherAdjustments());
        assertNotNull(tangible.getDepreciation().getOtherAdjustments().getLandAndBuildings());
        assertNotNull(tangible.getDepreciation().getOtherAdjustments().getFixturesAndFittings());
        assertNotNull(tangible.getDepreciation().getOtherAdjustments().getMotorVehicles());
        assertNotNull(tangible.getDepreciation().getOtherAdjustments().getOfficeEquipment());
        assertNotNull(tangible.getDepreciation().getOtherAdjustments().getPlantAndMachinery());
        assertNotNull(tangible.getDepreciation().getOtherAdjustments().getTotal());

        assertEquals((Long)10L, tangible.getDepreciation().getOtherAdjustments().getLandAndBuildings());
        assertEquals((Long)36L, tangible.getDepreciation().getOtherAdjustments().getFixturesAndFittings());
        assertEquals((Long)62L, tangible.getDepreciation().getOtherAdjustments().getMotorVehicles());
        assertEquals((Long)49L, tangible.getDepreciation().getOtherAdjustments().getOfficeEquipment());
        assertEquals((Long)23L, tangible.getDepreciation().getOtherAdjustments().getPlantAndMachinery());
        assertEquals((Long)75L, tangible.getDepreciation().getOtherAdjustments().getTotal());
    }

    @Test
    @DisplayName("test net book value current period API values map to iXBRL model")
    public void testNetBookValueCurrentPeriodAPIToIXBRL() {

        TangibleApi tangibleApi = createTangibleAssets();

        TangibleAssets tangible = new TangibleAssets();
        TangibleAssetsNetBookValue netBookValue = new TangibleAssetsNetBookValue();

        netBookValue.setCurrentPeriod(ApiToTangibleAssetsNoteMapper.INSTANCE
                .apiToTangibleAssetsNetBookValueCurrentPeriodMapper(tangibleApi));
        tangible.setNetBookValue(netBookValue);

        assertNotNull(netBookValue);
        assertNotNull(netBookValue.getCurrentPeriod());
        assertNotNull(netBookValue.getCurrentPeriod());
        assertNotNull(netBookValue.getCurrentPeriod().getLandAndBuildings());
        assertNotNull(netBookValue.getCurrentPeriod().getFixturesAndFittings());
        assertNotNull(netBookValue.getCurrentPeriod().getMotorVehicles());
        assertNotNull(netBookValue.getCurrentPeriod().getOfficeEquipment());
        assertNotNull(netBookValue.getCurrentPeriod().getPlantAndMachinery());
        assertNotNull(netBookValue.getCurrentPeriod().getTotal());

        assertEquals((Long)12L, netBookValue.getCurrentPeriod().getLandAndBuildings());
        assertEquals((Long)38L, netBookValue.getCurrentPeriod().getFixturesAndFittings());
        assertEquals((Long)64L, netBookValue.getCurrentPeriod().getMotorVehicles());
        assertEquals((Long)51L, netBookValue.getCurrentPeriod().getOfficeEquipment());
        assertEquals((Long)25L, netBookValue.getCurrentPeriod().getPlantAndMachinery());
        assertEquals((Long)77L, netBookValue.getCurrentPeriod().getTotal());
    }

    @Test
    @DisplayName("test net book value previous period API values map to iXBRL model")
    public void testNetBookValuePreviousPeriodAPIToIXBRL() {

        TangibleApi tangibleApi = createTangibleAssets();

        TangibleAssets tangible = new TangibleAssets();
        TangibleAssetsNetBookValue netBookValue = new TangibleAssetsNetBookValue();

        netBookValue.setPreviousPeriod(ApiToTangibleAssetsNoteMapper.INSTANCE
                .apiToTangibleAssetsNetBookValuePreviousPeriodMapper(tangibleApi));
        tangible.setNetBookValue(netBookValue);

        assertNotNull(netBookValue);
        assertNotNull(netBookValue.getPreviousPeriod());
        assertNotNull(netBookValue.getPreviousPeriod());
        assertNotNull(netBookValue.getPreviousPeriod().getLandAndBuildings());
        assertNotNull(netBookValue.getPreviousPeriod().getFixturesAndFittings());
        assertNotNull(netBookValue.getPreviousPeriod().getMotorVehicles());
        assertNotNull(netBookValue.getPreviousPeriod().getOfficeEquipment());
        assertNotNull(netBookValue.getPreviousPeriod().getPlantAndMachinery());
        assertNotNull(netBookValue.getPreviousPeriod().getTotal());

        assertEquals((Long)13L, netBookValue.getPreviousPeriod().getLandAndBuildings());
        assertEquals((Long)39L, netBookValue.getPreviousPeriod().getFixturesAndFittings());
        assertEquals((Long)65L, netBookValue.getPreviousPeriod().getMotorVehicles());
        assertEquals((Long)52L, netBookValue.getPreviousPeriod().getOfficeEquipment());
        assertEquals((Long)26L, netBookValue.getPreviousPeriod().getPlantAndMachinery());
        assertEquals((Long)78L, netBookValue.getPreviousPeriod().getTotal());
    }



    @Test
    @DisplayName("test additional information API values map to iXBRL model")
    public void testAdditionalInformationAPIToIXBRL() {

        TangibleApi tangibleApi = createTangibleAssets();
        TangibleAssets tangible = ApiToTangibleAssetsNoteMapper.INSTANCE.apiToTangibleAssetsNoteAdditionalInformation(tangibleApi);

        assertNotNull(tangible);

        assertEquals("Additional information things", tangible.getAdditionalInformation());

    }

    private TangibleApi createTangibleAssets() {

        TangibleApi tangible = new TangibleApi();

        tangible.setAdditionalInformation("Additional information things");
        tangible.setLandAndBuildings(createLandAndBuildingsData());
        tangible.setPlantAndMachinery(createPlantAndMachineryData());
        tangible.setFixturesAndFittings(createFixturesAndFittingsData());
        tangible.setOfficeEquipment(createOfficeEquipmentData());
        tangible.setMotorVehicles(createMotorVehiclesData());
        tangible.setTotal(createTotalData());

        return tangible;
    }

    private TangibleAssetsResource createLandAndBuildingsData() {

        TangibleAssetsResource landAndBuildings = new TangibleAssetsResource();

        Cost cost = new Cost();
        cost.setAtPeriodStart(1L);
        cost.setAdditions(2L);
        cost.setDisposals(3L);
        cost.setRevaluations(4L);
        cost.setTransfers(5L);
        cost.setAtPeriodEnd(6L);

        Depreciation depreciation = new Depreciation();
        depreciation.setAtPeriodStart(7L);
        depreciation.setChargeForYear(8L);
        depreciation.setOnDisposals(9L);
        depreciation.setOtherAdjustments(10L);
        depreciation.setAtPeriodEnd(11L);

        landAndBuildings.setCost(cost);
        landAndBuildings.setDepreciation(depreciation);
        landAndBuildings.setNetBookValueAtEndOfCurrentPeriod(12L);
        landAndBuildings.setNetBookValueAtEndOfPreviousPeriod(13L);

        return landAndBuildings;
    }

    private TangibleAssetsResource createPlantAndMachineryData() {

        TangibleAssetsResource plantAndMachinery = new TangibleAssetsResource();

        Cost cost = new Cost();
        cost.setAtPeriodStart(14L);
        cost.setAdditions(15L);
        cost.setDisposals(16L);
        cost.setRevaluations(17L);
        cost.setTransfers(18L);
        cost.setAtPeriodEnd(19L);

        Depreciation depreciation = new Depreciation();
        depreciation.setAtPeriodStart(20L);
        depreciation.setChargeForYear(21L);
        depreciation.setOnDisposals(22L);
        depreciation.setOtherAdjustments(23L);
        depreciation.setAtPeriodEnd(24L);

        plantAndMachinery.setCost(cost);
        plantAndMachinery.setDepreciation(depreciation);
        plantAndMachinery.setNetBookValueAtEndOfCurrentPeriod(25L);
        plantAndMachinery.setNetBookValueAtEndOfPreviousPeriod(26L);

        return plantAndMachinery;
    }

    private TangibleAssetsResource createFixturesAndFittingsData() {

        TangibleAssetsResource fixturesAndFittings = new TangibleAssetsResource();

        Cost cost = new Cost();
        cost.setAtPeriodStart(27L);
        cost.setAdditions(28L);
        cost.setDisposals(29L);
        cost.setRevaluations(30L);
        cost.setTransfers(31L);
        cost.setAtPeriodEnd(32L);

        Depreciation depreciation = new Depreciation();
        depreciation.setAtPeriodStart(33L);
        depreciation.setChargeForYear(34L);
        depreciation.setOnDisposals(35L);
        depreciation.setOtherAdjustments(36L);
        depreciation.setAtPeriodEnd(37L);

        fixturesAndFittings.setCost(cost);
        fixturesAndFittings.setDepreciation(depreciation);
        fixturesAndFittings.setNetBookValueAtEndOfCurrentPeriod(38L);
        fixturesAndFittings.setNetBookValueAtEndOfPreviousPeriod(39L);

        return fixturesAndFittings;
    }

    private TangibleAssetsResource createOfficeEquipmentData() {

        TangibleAssetsResource officeEquipment = new TangibleAssetsResource();

        Cost cost = new Cost();
        cost.setAtPeriodStart(40L);
        cost.setAdditions(41L);
        cost.setDisposals(42L);
        cost.setRevaluations(43L);
        cost.setTransfers(44L);
        cost.setAtPeriodEnd(45L);

        Depreciation depreciation = new Depreciation();
        depreciation.setAtPeriodStart(46L);
        depreciation.setChargeForYear(47L);
        depreciation.setOnDisposals(48L);
        depreciation.setOtherAdjustments(49L);
        depreciation.setAtPeriodEnd(50L);

        officeEquipment.setCost(cost);
        officeEquipment.setDepreciation(depreciation);
        officeEquipment.setNetBookValueAtEndOfCurrentPeriod(51L);
        officeEquipment.setNetBookValueAtEndOfPreviousPeriod(52L);

        return officeEquipment;
    }

    private TangibleAssetsResource createMotorVehiclesData() {

        TangibleAssetsResource motorVehicles = new TangibleAssetsResource();

        Cost cost = new Cost();
        cost.setAtPeriodStart(53L);
        cost.setAdditions(54L);
        cost.setDisposals(55L);
        cost.setRevaluations(56L);
        cost.setTransfers(57L);
        cost.setAtPeriodEnd(58L);

        Depreciation depreciation = new Depreciation();
        depreciation.setAtPeriodStart(59L);
        depreciation.setChargeForYear(60L);
        depreciation.setOnDisposals(61L);
        depreciation.setOtherAdjustments(62L);
        depreciation.setAtPeriodEnd(63L);

        motorVehicles.setCost(cost);
        motorVehicles.setDepreciation(depreciation);
        motorVehicles.setNetBookValueAtEndOfCurrentPeriod(64L);
        motorVehicles.setNetBookValueAtEndOfPreviousPeriod(65L);

        return motorVehicles;
    }

    private TangibleAssetsResource createTotalData() {

        TangibleAssetsResource total = new TangibleAssetsResource();

        Cost cost = new Cost();
        cost.setAtPeriodStart(66L);
        cost.setAdditions(67L);
        cost.setDisposals(68L);
        cost.setRevaluations(69L);
        cost.setTransfers(70L);
        cost.setAtPeriodEnd(71L);

        Depreciation depreciation = new Depreciation();
        depreciation.setAtPeriodStart(72L);
        depreciation.setChargeForYear(73L);
        depreciation.setOnDisposals(74L);
        depreciation.setOtherAdjustments(75L);
        depreciation.setAtPeriodEnd(76L);

        total.setCost(cost);
        total.setDepreciation(depreciation);
        total.setNetBookValueAtEndOfCurrentPeriod(77L);
        total.setNetBookValueAtEndOfPreviousPeriod(78L);

        return total;
    }
}
