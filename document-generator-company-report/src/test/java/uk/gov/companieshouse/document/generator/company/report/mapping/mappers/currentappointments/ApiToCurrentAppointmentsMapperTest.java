package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.currentappointments;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.model.officers.CompanyOfficerApi;
import uk.gov.companieshouse.api.model.officers.OfficersApi;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.currentappointments.CurrentAppointments;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.currentappointments.items.CurrentOfficer;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApiToCurrentAppointmentsMapperTest {

    @Mock
    private ApiToCurrentOfficer mockApiToCurrentOfficer;

    @InjectMocks
    private ApiToCurrentAppointmentsMapper apiToCurrentAppointmentsMapper = new ApiToCurrentAppointmentsMapperImpl();

    @Test
    @DisplayName("tests previous names data maps to previous names model")
    void testApiToPreviousNamesMaps() throws Exception{

        OfficersApi officersApi = createOfficersApi();

        List<CurrentOfficer> appointments = new ArrayList<>();

        when(mockApiToCurrentOfficer.apiToCurrentOfficer(officersApi.getItems())).thenReturn(appointments);
        CurrentAppointments currentAppointments =
            apiToCurrentAppointmentsMapper.apiToCurrentAppointmentsMapper(officersApi);

        assertNotNull(currentAppointments);
        assertEquals(1L, currentAppointments.getNumberOfCurrentAppointments().longValue());
        assertEquals(appointments, currentAppointments.getItems());
    }


    private OfficersApi createOfficersApi() {

        List<CompanyOfficerApi> officers = new ArrayList<>();

        CompanyOfficerApi companyOfficerApi1 = new CompanyOfficerApi();
        companyOfficerApi1.setName("test1");

        CompanyOfficerApi companyOfficerApi2 = new CompanyOfficerApi();
        companyOfficerApi2.setName("test2");

        officers.add(companyOfficerApi1);
        officers.add(companyOfficerApi2);

        OfficersApi officersApi = new OfficersApi();
        officersApi.setActiveCount(1L);
        officersApi.setItems(officers);

        return officersApi;
    }
}
