package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.mappers;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uk.gov.companieshouse.api.model.accounts.smallfull.intangible.Amortisation;
import uk.gov.companieshouse.api.model.accounts.smallfull.intangible.Cost;
import uk.gov.companieshouse.api.model.accounts.smallfull.intangible.IntangibleApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.intangible.IntangibleAssetsResource;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.notes.intangible.IntangibleAssets;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.notes.intangible.IntangibleAssetsAmortisation;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.notes.intangible.IntangibleAssetsCost;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.notes.intangible.IntangibleAssetsNetBookValue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ApiToIntangibleAssetsNoteMapperTest {

    private ApiToIntangibleAssetsNoteMapper apiToIntangibleAssetsNoteMapper =
            new ApiToIntangibleAssetsNoteMapperImpl();

    @Test
    @DisplayName("test cost additions to API values map to iXBRL model")
    public void testCostAdditionsAPIToIXBRL() {


        IntangibleApi intangibleApi = createIntangibleAssets();
        IntangibleAssets intangibleAssets = new IntangibleAssets();
        IntangibleAssetsCost intangibleAssetsCost = new IntangibleAssetsCost();
        intangibleAssetsCost.setAdditions(apiToIntangibleAssetsNoteMapper.apiToIntangibleAssetsCostAdditionsMapper(intangibleApi));

        intangibleAssets.setCost(intangibleAssetsCost);

        assertNotNull(intangibleAssets);
        assertNotNull(intangibleAssets.getCost());
        assertNotNull(intangibleAssets.getCost().getAdditions());
        assertNotNull(intangibleAssets.getCost().getAdditions().getGoodwill());
        assertNotNull(intangibleAssets.getCost().getAdditions().getOtherIntangibleAssets());
        assertNotNull(intangibleAssets.getCost().getAdditions().getTotal());

        assertEquals((Long)2L, intangibleAssets.getCost().getAdditions().getGoodwill());
        assertEquals((Long)15L, intangibleAssets.getCost().getAdditions().getOtherIntangibleAssets());
        assertEquals((Long)67L, intangibleAssets.getCost().getAdditions().getTotal());

    }

    @Test
    @DisplayName("test cost at period end API values map to iXBRL model")
    void testCostAtPeriodEndAPIToIXBRL() {
        IntangibleApi intangibleApi = createIntangibleAssets();

        IntangibleAssets intangibleAssets = new IntangibleAssets();
        IntangibleAssetsCost cost = new IntangibleAssetsCost();

        cost.setAtPeriodEnd(apiToIntangibleAssetsNoteMapper.apiToIntangibleAssetsCostAtPeriodEndMapper(intangibleApi));
        intangibleAssets.setCost(cost);

        assertNotNull(intangibleAssets);
        assertNotNull(intangibleAssets.getCost());
        assertNotNull(intangibleAssets.getCost().getAtPeriodEnd());
        assertNotNull(intangibleAssets.getCost().getAtPeriodEnd().getGoodwill());
        assertNotNull(intangibleAssets.getCost().getAtPeriodEnd().getOtherIntangibleAssets());
        assertNotNull(intangibleAssets.getCost().getAtPeriodEnd().getTotal());

        assertEquals((Long)6L, intangibleAssets.getCost().getAtPeriodEnd().getGoodwill());
        assertEquals((Long)19L, intangibleAssets.getCost().getAtPeriodEnd().getOtherIntangibleAssets());
        assertEquals((Long)71L, intangibleAssets.getCost().getAtPeriodEnd().getTotal());

    }

    @Test
    @DisplayName("test cost at period start API values map to iXBRL model")
    void testCostAtPeriodStartAPIToIXBRL() {

        IntangibleApi intangibleApi = createIntangibleAssets();

        IntangibleAssets intangibleAssets = new IntangibleAssets();
        IntangibleAssetsCost cost = new IntangibleAssetsCost();

        cost.setAtPeriodStart(apiToIntangibleAssetsNoteMapper.apiToIntangibleAssetsCostAtPeriodStartMapper(intangibleApi));

        intangibleAssets.setCost(cost);

        assertNotNull(intangibleAssets);
        assertNotNull(intangibleAssets.getCost());
        assertNotNull(intangibleAssets.getCost().getAtPeriodStart());
        assertNotNull(intangibleAssets.getCost().getAtPeriodStart().getGoodwill());
        assertNotNull(intangibleAssets.getCost().getAtPeriodStart().getOtherIntangibleAssets());
        assertNotNull(intangibleAssets.getCost().getAtPeriodStart().getTotal());

        assertEquals((Long)1L, intangibleAssets.getCost().getAtPeriodStart().getGoodwill());
        assertEquals((Long)14L, intangibleAssets.getCost().getAtPeriodStart().getOtherIntangibleAssets());
        assertEquals((Long)66L, intangibleAssets.getCost().getAtPeriodStart().getTotal());
    }

    @Test
    @DisplayName("test cost disposals API values map to iXBRL model")
    void testCostDisposalsAPIToIXBRL() {
        IntangibleApi intangibleApi = createIntangibleAssets();

        IntangibleAssets intangibleAssets = new IntangibleAssets();
        IntangibleAssetsCost cost = new IntangibleAssetsCost();

        cost.setDisposals(apiToIntangibleAssetsNoteMapper.apiToIntangibleAssetsCostDisposalsMapper(intangibleApi));
        intangibleAssets.setCost(cost);

        assertNotNull(intangibleAssets);
        assertNotNull(intangibleAssets.getCost());
        assertNotNull(intangibleAssets.getCost().getDisposals());
        assertNotNull(intangibleAssets.getCost().getDisposals().getGoodwill());
        assertNotNull(intangibleAssets.getCost().getDisposals().getOtherIntangibleAssets());
        assertNotNull(intangibleAssets.getCost().getDisposals().getTotal());

        assertEquals((Long)3L, intangibleAssets.getCost().getDisposals().getGoodwill());
        assertEquals((Long)16L, intangibleAssets.getCost().getDisposals().getOtherIntangibleAssets());
        assertEquals((Long)68L, intangibleAssets.getCost().getDisposals().getTotal());
    }

    @Test
    @DisplayName("test cost revaluations API values map to iXBRL model")
    void testCostRevaluationsAPIToIXBRL() {
        IntangibleApi intangibleApi = createIntangibleAssets();

        IntangibleAssets intangibleAssets = new IntangibleAssets();
        IntangibleAssetsCost cost = new IntangibleAssetsCost();

        cost.setRevaluations(apiToIntangibleAssetsNoteMapper.apiToIntangibleAssetsCostRevaluationsMapper(intangibleApi));

        intangibleAssets.setCost(cost);

        assertNotNull(intangibleAssets);
        assertNotNull(intangibleAssets.getCost());
        assertNotNull(intangibleAssets.getCost().getRevaluations());
        assertNotNull(intangibleAssets.getCost().getRevaluations().getGoodwill());
        assertNotNull(intangibleAssets.getCost().getRevaluations().getOtherIntangibleAssets());
        assertNotNull(intangibleAssets.getCost().getRevaluations().getTotal());

        assertEquals((Long)4L, intangibleAssets.getCost().getRevaluations().getGoodwill());
        assertEquals((Long)17L, intangibleAssets.getCost().getRevaluations().getOtherIntangibleAssets());
        assertEquals((Long)69L, intangibleAssets.getCost().getRevaluations().getTotal());
    }

    @Test
    @DisplayName("test cost transfers API values map to iXBRL model")
    void testCostTransfersAPIToIXBRL() {
        IntangibleApi intangibleApi = createIntangibleAssets();

        IntangibleAssets intangibleAssets = new IntangibleAssets();
        IntangibleAssetsCost cost = new IntangibleAssetsCost();

        cost.setTransfers(apiToIntangibleAssetsNoteMapper.apiToIntangibleAssetsCostTransfersMapper(intangibleApi));

        intangibleAssets.setCost(cost);

        assertNotNull(intangibleAssets);
        assertNotNull(intangibleAssets.getCost());
        assertNotNull(intangibleAssets.getCost().getTransfers());
        assertNotNull(intangibleAssets.getCost().getTransfers().getGoodwill());
        assertNotNull(intangibleAssets.getCost().getTransfers().getOtherIntangibleAssets());
        assertNotNull(intangibleAssets.getCost().getTransfers().getTotal());

        assertEquals((Long)5L, intangibleAssets.getCost().getTransfers().getGoodwill());
        assertEquals((Long)18L, intangibleAssets.getCost().getTransfers().getOtherIntangibleAssets());
        assertEquals((Long)70L, intangibleAssets.getCost().getTransfers().getTotal());
    }

    @Test
    @DisplayName("test amortisation at period end API values map to iXBRL model")
    void testAmortisationAtPeriodEndAPIToIXBRL() {
        IntangibleApi intangibleApi = createIntangibleAssets();

        IntangibleAssets intangibleAssets = new IntangibleAssets();
        IntangibleAssetsAmortisation amortisation = new IntangibleAssetsAmortisation();

        amortisation.setAtPeriodEnd(apiToIntangibleAssetsNoteMapper.apiToIntangibleAssetsAmortisationAtPeriodEndMapper(intangibleApi));

        intangibleAssets.setAmortisation(amortisation);

        assertNotNull(intangibleAssets);
        assertNotNull(intangibleAssets.getAmortisation());
        assertNotNull(intangibleAssets.getAmortisation().getAtPeriodEnd());
        assertNotNull(intangibleAssets.getAmortisation().getAtPeriodEnd().getGoodwill());
        assertNotNull(intangibleAssets.getAmortisation().getAtPeriodEnd().getOtherIntangibleAssets());
        assertNotNull(intangibleAssets.getAmortisation().getAtPeriodEnd().getTotal());

        assertEquals((Long)11L, intangibleAssets.getAmortisation().getAtPeriodEnd().getGoodwill());
        assertEquals((Long)24L, intangibleAssets.getAmortisation().getAtPeriodEnd().getOtherIntangibleAssets());
        assertEquals((Long)76L, intangibleAssets.getAmortisation().getAtPeriodEnd().getTotal());
    }

    @Test
    @DisplayName("test amortisation at period start API values map to iXBRL model")
    void testAmortisationAtPeriodStartAPIToIXBRL() {
        IntangibleApi intangibleApi = createIntangibleAssets();

        IntangibleAssets intangibleAssets = new IntangibleAssets();
        IntangibleAssetsAmortisation amortisation = new IntangibleAssetsAmortisation();

        amortisation.setAtPeriodStart(apiToIntangibleAssetsNoteMapper.apiToIntangibleAssetsAmortisationAtPeriodStartMapper(intangibleApi));

        intangibleAssets.setAmortisation(amortisation);

        assertNotNull(intangibleAssets);
        assertNotNull(intangibleAssets.getAmortisation());
        assertNotNull(intangibleAssets.getAmortisation().getAtPeriodStart());
        assertNotNull(intangibleAssets.getAmortisation().getAtPeriodStart().getGoodwill());
        assertNotNull(intangibleAssets.getAmortisation().getAtPeriodStart().getOtherIntangibleAssets());
        assertNotNull(intangibleAssets.getAmortisation().getAtPeriodStart().getTotal());

        assertEquals((Long)7L, intangibleAssets.getAmortisation().getAtPeriodStart().getGoodwill());
        assertEquals((Long)20L, intangibleAssets.getAmortisation().getAtPeriodStart().getOtherIntangibleAssets());
        assertEquals((Long)72L, intangibleAssets.getAmortisation().getAtPeriodStart().getTotal());
    }

    @Test
    @DisplayName("test amortisation charge for year API values map to iXBRL model")
    void testAmortisationChargeForYearAPIToIXBRL() {
        IntangibleApi intangibleApi = createIntangibleAssets();

        IntangibleAssets intangibleAssets = new IntangibleAssets();
        IntangibleAssetsAmortisation amortisation = new IntangibleAssetsAmortisation();

        amortisation.setChargeForYear(apiToIntangibleAssetsNoteMapper.apiToIntangibleAssetsAmortisationChargeForYearMapper(intangibleApi));

        intangibleAssets.setAmortisation(amortisation);

        assertNotNull(intangibleAssets);
        assertNotNull(intangibleAssets.getAmortisation());
        assertNotNull(intangibleAssets.getAmortisation().getChargeForYear());
        assertNotNull(intangibleAssets.getAmortisation().getChargeForYear().getGoodwill());
        assertNotNull(intangibleAssets.getAmortisation().getChargeForYear().getOtherIntangibleAssets());
        assertNotNull(intangibleAssets.getAmortisation().getChargeForYear().getTotal());

        assertEquals((Long)8L, intangibleAssets.getAmortisation().getChargeForYear().getGoodwill());
        assertEquals((Long)21L, intangibleAssets.getAmortisation().getChargeForYear().getOtherIntangibleAssets());
        assertEquals((Long)73L, intangibleAssets.getAmortisation().getChargeForYear().getTotal());
    }

    @Test
    @DisplayName("test amortisation on disposals API values map to iXBRL model")
    void testAmortisationOnDisposalsAPIToIXBRL() {
        IntangibleApi intangibleApi = createIntangibleAssets();

        IntangibleAssets intangibleAssets = new IntangibleAssets();
        IntangibleAssetsAmortisation amortisation = new IntangibleAssetsAmortisation();

        amortisation.setOnDisposals(apiToIntangibleAssetsNoteMapper.apiToIntangibleAssetsAmortisationOnDisposalsMapper(intangibleApi));

        intangibleAssets.setAmortisation(amortisation);

        assertNotNull(intangibleAssets);
        assertNotNull(intangibleAssets.getAmortisation());
        assertNotNull(intangibleAssets.getAmortisation().getOnDisposals());
        assertNotNull(intangibleAssets.getAmortisation().getOnDisposals().getGoodwill());
        assertNotNull(intangibleAssets.getAmortisation().getOnDisposals().getOtherIntangibleAssets());
        assertNotNull(intangibleAssets.getAmortisation().getOnDisposals().getTotal());

        assertEquals((Long)9L, intangibleAssets.getAmortisation().getOnDisposals().getGoodwill());
        assertEquals((Long)22L, intangibleAssets.getAmortisation().getOnDisposals().getOtherIntangibleAssets());
        assertEquals((Long)74L, intangibleAssets.getAmortisation().getOnDisposals().getTotal());
    }

    @Test
    @DisplayName("test amortisation other adjustments API values map to iXBRL model")
    void testAmortisationOtherAdjustmentsAPIToIXBRL() {
        IntangibleApi intangibleApi = createIntangibleAssets();

        IntangibleAssets intangibleAssets = new IntangibleAssets();
        IntangibleAssetsAmortisation amortisation = new IntangibleAssetsAmortisation();

        amortisation.setOtherAdjustments(apiToIntangibleAssetsNoteMapper.apiToIntangibleAssetsAmortisationOtherAdjustmentsMapper(intangibleApi));

        intangibleAssets.setAmortisation(amortisation);

        assertNotNull(intangibleAssets);
        assertNotNull(intangibleAssets.getAmortisation());
        assertNotNull(intangibleAssets.getAmortisation().getOtherAdjustments());
        assertNotNull(intangibleAssets.getAmortisation().getOtherAdjustments().getGoodwill());
        assertNotNull(intangibleAssets.getAmortisation().getOtherAdjustments().getOtherIntangibleAssets());
        assertNotNull(intangibleAssets.getAmortisation().getOtherAdjustments().getTotal());

        assertEquals((Long)10L, intangibleAssets.getAmortisation().getOtherAdjustments().getGoodwill());
        assertEquals((Long)23L, intangibleAssets.getAmortisation().getOtherAdjustments().getOtherIntangibleAssets());
        assertEquals((Long)75L, intangibleAssets.getAmortisation().getOtherAdjustments().getTotal());
    }

    @Test
    @DisplayName("test net book value current period API values map to iXBRL model")
    void testNetBookValueCurrentPeriodAPIToIXBRL() {
        IntangibleApi intangibleApi = createIntangibleAssets();

        IntangibleAssets intangibleAssets = new IntangibleAssets();
        IntangibleAssetsNetBookValue netBookValue  = new IntangibleAssetsNetBookValue();

        netBookValue.setCurrentPeriod(apiToIntangibleAssetsNoteMapper.apiToIntangibleAssetsNetBookValueCurrentPeriodMapper(intangibleApi));

        intangibleAssets.setNetBookValue(netBookValue);

        assertNotNull(netBookValue);
        assertNotNull(netBookValue.getCurrentPeriod());
        assertNotNull(netBookValue.getCurrentPeriod().getGoodwill());
        assertNotNull(netBookValue.getCurrentPeriod().getOtherIntangibleAssets());
        assertNotNull(netBookValue.getCurrentPeriod().getTotal());

        assertEquals((Long)12L, netBookValue.getCurrentPeriod().getGoodwill());
        assertEquals((Long)25L, netBookValue.getCurrentPeriod().getOtherIntangibleAssets());
        assertEquals((Long)77L, netBookValue.getCurrentPeriod().getTotal());
    }

    @Test
    @DisplayName("test net book value previous period API values map to iXBRL model")
    void testNetBookValuePreviousPeriodAPIToIXBRL() {
        IntangibleApi intangibleApi = createIntangibleAssets();

        IntangibleAssets intangibleAssets = new IntangibleAssets();
        IntangibleAssetsNetBookValue netBookValue  = new IntangibleAssetsNetBookValue();

        netBookValue.setPreviousPeriod(apiToIntangibleAssetsNoteMapper.apiToIntangibleAssetsNetBookValuePreviousPeriodMapper(intangibleApi));

        intangibleAssets.setNetBookValue(netBookValue);

        assertNotNull(netBookValue);
        assertNotNull(netBookValue.getPreviousPeriod());
        assertNotNull(netBookValue.getPreviousPeriod().getGoodwill());
        assertNotNull(netBookValue.getPreviousPeriod().getOtherIntangibleAssets());
        assertNotNull(netBookValue.getPreviousPeriod().getTotal());

        assertEquals((Long)13L, netBookValue.getPreviousPeriod().getGoodwill());
        assertEquals((Long)26L, netBookValue.getPreviousPeriod().getOtherIntangibleAssets());
        assertEquals((Long)78L, netBookValue.getPreviousPeriod().getTotal());
    }

    @Test
    @DisplayName("test additional information API values map to iXBRL model")
    public void testAdditionalInformationAPIToIXBRL() {

        IntangibleApi intangibleApi = createIntangibleAssets();
        IntangibleAssets intangible = apiToIntangibleAssetsNoteMapper.apiToIntangibleAssetsNoteAdditionalInformation(intangibleApi);

        assertNotNull(intangible);

        assertEquals("Additional Information", intangible.getAdditionalInformation());

    }


    private IntangibleApi createIntangibleAssets() {

        IntangibleApi intangibleApi = new IntangibleApi();

        intangibleApi.setAdditionalInformation("Additional Information");
        intangibleApi.setGoodwill(createGoodwillData());
        intangibleApi.setOtherIntangibleAssets(createOtherIntangibleAssetsData());
        intangibleApi.setTotal(createTotalData());

        return intangibleApi;
    }

    private IntangibleAssetsResource createTotalData() {

        IntangibleAssetsResource total = new IntangibleAssetsResource();

        Cost cost = new Cost();
        cost.setAtPeriodStart(66L);
        cost.setAdditions(67L);
        cost.setDisposals(68L);
        cost.setRevaluations(69L);
        cost.setTransfers(70L);
        cost.setAtPeriodEnd(71L);

        Amortisation amortisation = new Amortisation();
        amortisation.setAtPeriodStart(72L);
        amortisation.setChargeForYear(73L);
        amortisation.setOnDisposals(74L);
        amortisation.setOtherAdjustments(75L);
        amortisation.setAtPeriodEnd(76L);

        total.setCost(cost);
        total.setAmortisation(amortisation);
        total.setNetBookValueAtEndOfCurrentPeriod(77L);
        total.setNetBookValueAtEndOfPreviousPeriod(78L);

        return total;
    }

    private IntangibleAssetsResource createOtherIntangibleAssetsData() {

        IntangibleAssetsResource otherIntangibleAssets = new IntangibleAssetsResource();

        Cost cost = new Cost();
        cost.setAtPeriodStart(14L);
        cost.setAdditions(15L);
        cost.setDisposals(16L);
        cost.setRevaluations(17L);
        cost.setTransfers(18L);
        cost.setAtPeriodEnd(19L);

        Amortisation amortisation = new Amortisation();
        amortisation.setAtPeriodStart(20L);
        amortisation.setChargeForYear(21L);
        amortisation.setOnDisposals(22L);
        amortisation.setOtherAdjustments(23L);
        amortisation.setAtPeriodEnd(24L);

        otherIntangibleAssets.setCost(cost);
        otherIntangibleAssets.setAmortisation(amortisation);
        otherIntangibleAssets.setNetBookValueAtEndOfCurrentPeriod(25L);
        otherIntangibleAssets.setNetBookValueAtEndOfPreviousPeriod(26L);

        return otherIntangibleAssets;

    }

    private IntangibleAssetsResource createGoodwillData() {

        IntangibleAssetsResource goodwill = new IntangibleAssetsResource();

        Cost cost = new Cost();
        cost.setAtPeriodStart(1L);
        cost.setAdditions(2L);
        cost.setDisposals(3L);
        cost.setRevaluations(4L);
        cost.setTransfers(5L);
        cost.setAtPeriodEnd(6L);

        Amortisation amortisation = new Amortisation();
        amortisation.setAtPeriodStart(7L);
        amortisation.setChargeForYear(8L);
        amortisation.setOnDisposals(9L);
        amortisation.setOtherAdjustments(10L);
        amortisation.setAtPeriodEnd(11L);

        goodwill.setCost(cost);
        goodwill.setAmortisation(amortisation);
        goodwill.setNetBookValueAtEndOfCurrentPeriod(12L);
        goodwill.setNetBookValueAtEndOfPreviousPeriod(13L);

        return goodwill;
    }
}