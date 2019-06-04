//package uk.gov.companieshouse.document.generator.prosecution.mapping;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestInstance;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.junit.jupiter.MockitoExtension;
//import uk.gov.companieshouse.api.model.prosecution.offence.FilingPeriodTypeApi;
//import uk.gov.companieshouse.api.model.prosecution.offence.OffenceApi;
//import uk.gov.companieshouse.api.model.prosecution.offence.OffenceStatusApi;
//import uk.gov.companieshouse.document.generator.prosecution.mapping.mappers.ApiToOffenceMapper;
//import uk.gov.companieshouse.document.generator.prosecution.mapping.mappers.ApiToOffenceMapperImpl;
//import uk.gov.companieshouse.document.generator.prosecution.mapping.model.offence.FilingPeriodType;
//import uk.gov.companieshouse.document.generator.prosecution.mapping.model.offence.Offence;
//import uk.gov.companieshouse.document.generator.prosecution.mapping.model.offence.OffenceStatus;
//
//import java.time.LocalDate;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//@ExtendWith(MockitoExtension.class)
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//public class ApiToOffenceMapperTest {
//
//    private ApiToOffenceMapper apiToOffenceMapper = new ApiToOffenceMapperImpl();
//
//    private static final FilingPeriodType FILING_PERIOD_TYPE = FilingPeriodType.ALL;
//    private static final OffenceStatus STATUS = OffenceStatus.PROCEED;
//    private static final LocalDate FILING_PERIOD_ENDS_ON = LocalDate.now();
//    private static final LocalDate FILING_DUE_ON = LocalDate.now();
//    private static final String FILING_PERIOD_ID = "filingPeriodId";
//
//    @Test
//    @DisplayName("Tests offence API values map to offence DocGen model")
//    void testApiToOffenceMaps() {
//        Offence offence = apiToOffenceMapper.apiToOffence(createOffence());
//
//        assertNotNull(offence);
//        assertEquals(FILING_PERIOD_TYPE, offence.getFilingPeriodType());
//        assertEquals(STATUS, offence.getStatus());
//        assertEquals(FILING_PERIOD_ENDS_ON, offence.getFilingPeriodEndsOn());
//        assertEquals(FILING_DUE_ON, offence.getFilingDueOn());
//        assertEquals(FILING_PERIOD_ID, offence.getFilingPeriodId());
//    }
//
//    private OffenceApi createOffence() {
//        OffenceApi offence = new OffenceApi();
//
//        offence.setFilingPeriodType(FilingPeriodTypeApi.ALL);
//        offence.setStatus(OffenceStatusApi.PROCEED);
//        offence.setFilingPeriodEndsOn(FILING_PERIOD_ENDS_ON);
//        offence.setFilingDueOn(FILING_DUE_ON);
//        offence.setFilingPeriodId(FILING_PERIOD_ID);
//
//        return offence;
//    }
//}
//
