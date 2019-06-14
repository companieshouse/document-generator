package uk.gov.companieshouse.document.generator.company.report.mapping.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.model.company.PreviousCompanyNamesApi;
import uk.gov.companieshouse.document.generator.company.report.mapping.mappers.previousnames.ApiToPreviousNamesMapper;
import uk.gov.companieshouse.document.generator.company.report.mapping.mappers.previousnames.ApiToPreviousNamesMapperImpl;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.previousnames.PreviousNames;

@ExtendWith({MockitoExtension.class})
public class ApiToPreviousNamesMapperTest {

    private static final String PREVIOUS_NAME = "previous name 1";
    private static final LocalDate DATE_OF_CHANGE = LocalDate.of(2019, 06, 06);
    private static final String PREVIOUS_NAME2 = "previous name 2";
    private static final LocalDate DATE_OF_CHANGE2 = LocalDate.of(2018, 05, 05);
    private static final String PREVIOUS_NAME3 = "previous name 3";
    private static final LocalDate DATE_OF_CHANGE3 = LocalDate.of(2017, 05, 05);

    @InjectMocks
    private ApiToPreviousNamesMapper apiToPreviousNamesMapper = new ApiToPreviousNamesMapperImpl();


    @Test
    @DisplayName("tests previous names data maps to previous names model")
    void testApiToPreviousNamesMaps() {

        PreviousCompanyNamesApi previousCompanyNamesApi = createPreviousNamesApiData();

        PreviousNames previousNames =
                apiToPreviousNamesMapper.apiToPreviousNameMapper(previousCompanyNamesApi);

        assertNotNull(previousNames);
        assertEquals(PREVIOUS_NAME, previousNames.getPreviousName());
        assertEquals(DATE_OF_CHANGE, previousNames.getDateOfChange());

    }

    @Test
    @DisplayName("tests multiple previous names data maps to previous names model")
    void testMultipleApiToPreviousNamesMaps() {

        List<PreviousCompanyNamesApi> previousCompanyNamesApiList = new ArrayList<>();

        previousCompanyNamesApiList.add(createPreviousNamesApiData());
        previousCompanyNamesApiList.add(createSecondPreviousName());
        previousCompanyNamesApiList.add(createThirdPreviousName());

        List<PreviousNames> previousNamesList =
                apiToPreviousNamesMapper.apiToPreviousNamesMapper(previousCompanyNamesApiList);

        assertNotNull(previousNamesList);
        assertEquals(3, previousNamesList.size());

        assertEquals(previousNamesList.get(0).getPreviousName(), PREVIOUS_NAME);
        assertEquals(previousNamesList.get(0).getDateOfChange(), DATE_OF_CHANGE);

        assertEquals(previousNamesList.get(1).getPreviousName(), PREVIOUS_NAME2);
        assertEquals(previousNamesList.get(1).getDateOfChange(), DATE_OF_CHANGE2);

        assertEquals(previousNamesList.get(2).getPreviousName(), PREVIOUS_NAME3);
        assertEquals(previousNamesList.get(2).getDateOfChange(), DATE_OF_CHANGE3);

    }

    private PreviousCompanyNamesApi createThirdPreviousName() {
        PreviousCompanyNamesApi previousCompanyNamesApi = new PreviousCompanyNamesApi();

        previousCompanyNamesApi.setName(PREVIOUS_NAME3);
        previousCompanyNamesApi.setCeasedOn(DATE_OF_CHANGE3);

        return previousCompanyNamesApi;
    }

    private PreviousCompanyNamesApi createSecondPreviousName() {
        PreviousCompanyNamesApi previousCompanyNamesApi = new PreviousCompanyNamesApi();

        previousCompanyNamesApi.setName(PREVIOUS_NAME2);
        previousCompanyNamesApi.setCeasedOn(DATE_OF_CHANGE2);

        return previousCompanyNamesApi;
    }

    private PreviousCompanyNamesApi createPreviousNamesApiData() {

        PreviousCompanyNamesApi previousCompanyNamesApi = new PreviousCompanyNamesApi();

        previousCompanyNamesApi.setName(PREVIOUS_NAME);
        previousCompanyNamesApi.setCeasedOn(DATE_OF_CHANGE);

        return previousCompanyNamesApi;
    }
}
