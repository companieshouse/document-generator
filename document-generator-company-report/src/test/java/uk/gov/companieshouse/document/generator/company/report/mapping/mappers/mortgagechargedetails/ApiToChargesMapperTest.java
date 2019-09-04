package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.mortgagechargedetails;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.model.charges.ChargeApi;
import uk.gov.companieshouse.api.model.charges.ClassificationApi;
import uk.gov.companieshouse.api.model.charges.ParticularsApi;
import uk.gov.companieshouse.document.generator.common.descriptions.RetrieveApiEnumerationDescription;

import java.time.LocalDate;

@ExtendWith({MockitoExtension.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApiToChargesMapperTest {

    @Mock
    private RetrieveApiEnumerationDescription mockRetrieveApiEnumerationDescription;

    @InjectMocks
    private ApiToChargesMapper apiToChargesMapper = new ApiToChargesMapperImpl();

    private static final String DESCRIPTION = "description";
    private static final String STATUS = "status";
    private static final String PARTICULARS = "particulars";
    private static final LocalDate CREATED_ON = LocalDate.of(2016,01,01);
    private static final String CREATED_ON_CONVERTED = "1 January 2016";
    private static final LocalDate DELIVERED_ON = LocalDate.of(2017,02,02);
    private static final String DELIVERED_ON_CONVERTED = "2 February 2017";
    private static final LocalDate SATISFIED_ON = LocalDate.of(2018,03,03);
    private static final String SATISFIED_ON_CONVERTED = "3 March 2018";


    @Test
    @DisplayName("tests Charge data maps to Charge model")
    void testChargeMaps() {



    }


    private ChargeApi createChargeApi() {
        ChargeApi chargeApi = new ChargeApi();

        ClassificationApi classificationApi = new ClassificationApi();
        classificationApi.setDescription(DESCRIPTION);
        chargeApi.setClassification(classificationApi);

        chargeApi.setCreatedOn(CREATED_ON);
        chargeApi.setDeliveredOn(DELIVERED_ON);
        chargeApi.setSatisfiedOn(SATISFIED_ON);
        chargeApi.setStatus(STATUS);

        ParticularsApi particularsApi = new ParticularsApi();
        particularsApi.setDescription(PARTICULARS);
        chargeApi.setParticulars(particularsApi);

        return chargeApi;
    }
}
