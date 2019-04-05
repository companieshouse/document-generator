package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.model.accounts.smallfull.employees.CurrentPeriod;
import uk.gov.companieshouse.api.model.accounts.smallfull.employees.PreviousPeriod;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.employees.Employees;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApiToEmployeesMapperTest {

    private static final String DETAILS = "Details";
    private static final Long NUMBER_OF_EMPLOYEES_CURRENT = 1L;
    private static final Long NUMBER_OF_EMPLOYEES_PREVIOUS = 10L;


    @Test
    @DisplayName("tests employees API values map to employees IXBRL model")
    void testApiToCompanyMaps() {

        Employees employees = ApiToEmployeesMapper.INSTANCE.apiToEmployees(createCurrentPeriodEmployees(), createPreviousPeriodEmployees());

        assertNotNull(employees);
        assertEquals(NUMBER_OF_EMPLOYEES_CURRENT, employees.getAverageNumberOfEmployees().getCurrentAmount());
        assertEquals(DETAILS, employees.getDetails());
        assertEquals(NUMBER_OF_EMPLOYEES_PREVIOUS, employees.getAverageNumberOfEmployees().getPreviousAmount());

    }

    private CurrentPeriod createCurrentPeriodEmployees() {
        CurrentPeriod employeesCurrentPeriod = new CurrentPeriod();
        employeesCurrentPeriod.setDetails(DETAILS);
        employeesCurrentPeriod.setAverageNumberOfEmployees(NUMBER_OF_EMPLOYEES_CURRENT);

        return employeesCurrentPeriod;
    }

    private PreviousPeriod createPreviousPeriodEmployees() {
        PreviousPeriod employeesPreviousPeriod = new PreviousPeriod();
        employeesPreviousPeriod.setAverageNumberOfEmployees(NUMBER_OF_EMPLOYEES_PREVIOUS);

        return employeesPreviousPeriod;
    }
}
