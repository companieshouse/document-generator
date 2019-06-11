package uk.gov.companieshouse.document.generator.prosecution.service;


import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.document.generator.prosecution.mapping.mappers.ApiToDefendantMapper;
import uk.gov.companieshouse.document.generator.prosecution.mapping.mappers.ApiToOffenceMapper;
import uk.gov.companieshouse.document.generator.prosecution.mapping.mappers.ApiToProsecutionCaseMapper;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProsecutionServiceTest {

    @Mock
    private ApiClientService apiClientService;

    @Mock
    private ApiToDefendantMapper apiToDefendantMapper;

    @Mock
    private ApiToProsecutionCaseMapper apiToProsecutionCaseMapper;

    @Mock
    private ApiToOffenceMapper apiToOffenceMapper;
}
