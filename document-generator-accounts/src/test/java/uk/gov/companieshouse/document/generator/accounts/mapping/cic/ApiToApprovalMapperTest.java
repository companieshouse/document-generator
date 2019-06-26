package uk.gov.companieshouse.document.generator.accounts.mapping.cic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uk.gov.companieshouse.api.model.accounts.cic.approval.CicApprovalApi;
import uk.gov.companieshouse.document.generator.accounts.mapping.cic.mappers.ApiToApprovalMapper;
import uk.gov.companieshouse.document.generator.accounts.mapping.cic.mappers.ApiToApprovalMapperImpl;
import uk.gov.companieshouse.document.generator.accounts.mapping.cic.model.Approval;

public class ApiToApprovalMapperTest {

    private ApiToApprovalMapper apiToApprovalMapper = new ApiToApprovalMapperImpl();

    private static final String NAME = "name";

    private static final LocalDate DATE = LocalDate.of(2019, 6, 1);

    private static final String DATE_FORMATTED = "1 June 2019";

    @Test
    @DisplayName("API to approval")
    void apiToApproval() {

        CicApprovalApi cicApprovalApi = new CicApprovalApi();
        cicApprovalApi.setName(NAME);
        cicApprovalApi.setDate(DATE);

        Approval approval = apiToApprovalMapper.apiToApproval(cicApprovalApi);

        assertNotNull(approval);
        assertEquals(NAME, approval.getName());
        assertEquals(DATE_FORMATTED, approval.getDate());
    }
}
