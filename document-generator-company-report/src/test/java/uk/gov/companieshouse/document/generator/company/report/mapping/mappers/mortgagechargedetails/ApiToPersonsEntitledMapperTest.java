package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.mortgagechargedetails;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.model.charges.PersonsEntitledApi;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.mortgagechargedetails.items.PersonsEntitled;

@ExtendWith({MockitoExtension.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApiToPersonsEntitledMapperTest {

    @InjectMocks
    private ApiToPersonsEntitledMapper apiToPersonsEntitledMapper = new ApiToPersonsEntitledMapperImpl();

    private static final String NAME = "person entitled";

    @Test
    @DisplayName("tests SecuredDetailsApi data maps to SecuredDetails model")
    void testSecuredDetailsMaps() {

        List<PersonsEntitled> personsEntitled = apiToPersonsEntitledMapper.apiToPersonsEntitledMapper(createPersonsEntitledApi());

        assertNotNull(personsEntitled);
        assertEquals(NAME, personsEntitled.get(0).getName());
    }

    @Test
    @DisplayName("test Null PersonsEntitled returns Null")
    void TestNull() {

        List<PersonsEntitledApi> personsEntitledApi = null;
        assertNull(apiToPersonsEntitledMapper.apiToPersonsEntitledMapper(personsEntitledApi));
    }

    private List<PersonsEntitledApi> createPersonsEntitledApi() {

        List<PersonsEntitledApi> personsEntitledApi = new ArrayList<>();
        PersonsEntitledApi personEntitledApi = new PersonsEntitledApi();
        personEntitledApi.setName(NAME);
        personsEntitledApi.add(personEntitledApi);

        return personsEntitledApi;
    }
}
