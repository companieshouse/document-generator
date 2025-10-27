package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.companyregisters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.model.registers.RegisterApi;
import uk.gov.companieshouse.api.model.registers.RegistersApi;
import uk.gov.companieshouse.document.generator.company.report.mapping.mappers.registers.ApiToRegister;
import uk.gov.companieshouse.document.generator.company.report.mapping.mappers.registers.ApiToRegistersMapper;
import uk.gov.companieshouse.document.generator.company.report.mapping.mappers.registers.ApiToRegistersMapperImpl;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.registers.CompanyRegisters;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.registers.items.Register;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApiToRegistersMapperTest {

    @InjectMocks
    private ApiToRegistersMapper apiToRegistersMapper = new ApiToRegistersMapperImpl();

    @Mock
    private ApiToRegister mockApiToRegister;

    @Test
    @DisplayName("tests company registers data maps to model")
    void testCompanyRegistersMaps() {

        when(mockApiToRegister.apiToRegister(any(RegisterApi.class))).thenReturn(new Register());

        CompanyRegisters companyRegisters = apiToRegistersMapper.apiToRegistersMapper(createRegistersApi());

        assertNotNull(companyRegisters);
    }

    @Test
    @DisplayName("tests company registers data with null value maps to model")
    void testCompanyRegistersNullValueMaps() {

        CompanyRegisters companyRegisters = apiToRegistersMapper.apiToRegistersMapper(createRegistersNullValueApi());

        assertEquals(null, companyRegisters);
    }

    private RegistersApi createRegistersApi() {

        RegistersApi registersApi = new RegistersApi();

        registersApi.setPscRegister(new RegisterApi());
        registersApi.setMembersRegister(new RegisterApi());
        registersApi.setLlpUsualResidentialAddressRegister(new RegisterApi());
        registersApi.setLlpMembersRegister(new RegisterApi());
        registersApi.setDirectorsRegister(new RegisterApi());
        registersApi.setSecretariesRegister(new RegisterApi());
        registersApi.setUsualResidentialAddressRegister(new RegisterApi());

        return registersApi;
    }

    private  RegistersApi createRegistersNullValueApi() {

        RegistersApi registersNullApi = null;

        return registersNullApi;
    }
}


