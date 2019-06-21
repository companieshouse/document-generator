package uk.gov.companieshouse.document.generator.company.report.mapping.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.model.company.CompanyProfileApi;
import uk.gov.companieshouse.api.model.company.ConfirmationStatementApi;
import uk.gov.companieshouse.api.model.company.account.AccountingReferenceDateApi;
import uk.gov.companieshouse.api.model.company.account.CompanyAccountApi;
import uk.gov.companieshouse.api.model.company.account.LastAccountsApi;
import uk.gov.companieshouse.api.model.company.account.NextAccountsApi;
import uk.gov.companieshouse.document.generator.company.report.mapping.mappers.keyfilingdates.ApiToKeyFilingDatesMapper;
import uk.gov.companieshouse.document.generator.company.report.mapping.mappers.keyfilingdates.ApiToKeyFilingDatesMapperImpl;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.keyfilingdates.KeyFilingDates;

@ExtendWith({MockitoExtension.class})
@TestInstance(Lifecycle.PER_CLASS)
public class ApiToKeyFilingDatesMapperTest {

    private static final LocalDate LAST_MADE_UP_TO = LocalDate.of(2019, 06, 06);
    private static final String LAST_MADE_UP_TO_FORMATTED = "6 June 2019";
    private static final LocalDate NEXT_DUE_ON = LocalDate.of(2018, 01, 01);
    private static final String NEXT_DUE_ON_FORMATTED = "1 January 2018";
    private static final LocalDate CONF_STATEMENT_LAST_MADE_UP_TO = LocalDate.of(2019, 02, 02);
    private static final String CONF_STATEMENT_LAST_MADE_UP_TO_FORMATTED = "2 February 2019";
    private static final LocalDate CONF_STATEMENT_NEXT_DUE_ON = LocalDate.of(2018, 03, 03);
    private static final String CONF_STATEMENT_NEXT_DUE_ON_FORMATTED = "3 March 2018";
    private static final LocalDate LAST_FULL_MEMBERS_LIST = LocalDate.of(2015, 04, 04);
    private static final String LAST_FULL_MEMBERS_LIST_FORMATTED = "4 April 2015";

    @InjectMocks
    private ApiToKeyFilingDatesMapper apiToKeyFilingDatesMapper =
            new ApiToKeyFilingDatesMapperImpl();

    @Test
    void testApiToKeyFilingDatesMapCorrectly() {

        CompanyProfileApi companyProfileApi = new CompanyProfileApi();

        CompanyAccountApi companyAccountApi = new CompanyAccountApi();

        AccountingReferenceDateApi accountingReferenceDateApi = new AccountingReferenceDateApi();
        accountingReferenceDateApi.setDay(String.valueOf(5));
        accountingReferenceDateApi.setMonth(String.valueOf(5));
        companyAccountApi.setAccountingReferenceDate(accountingReferenceDateApi);

        NextAccountsApi nextAccountsApi = new NextAccountsApi();
        nextAccountsApi.setDueOn(NEXT_DUE_ON);
        companyAccountApi.setNextAccounts(nextAccountsApi);

        LastAccountsApi lastAccountsApi = new LastAccountsApi();
        lastAccountsApi.setMadeUpTo(LAST_MADE_UP_TO);
        companyAccountApi.setLastAccounts(lastAccountsApi);

        ConfirmationStatementApi confirmationStatementApi = new ConfirmationStatementApi();
        confirmationStatementApi.setLastMadeUpTo(CONF_STATEMENT_LAST_MADE_UP_TO);
        confirmationStatementApi.setNextDue(CONF_STATEMENT_NEXT_DUE_ON);

        companyProfileApi.setLastFullMembersListDate(LAST_FULL_MEMBERS_LIST);
        companyProfileApi.setConfirmationStatement(confirmationStatementApi);
        companyProfileApi.setAccounts(companyAccountApi);


        KeyFilingDates keyFilingDates = apiToKeyFilingDatesMapper.apiToKeyFilingDates(companyProfileApi);

        assertNotNull(keyFilingDates);
        assertEquals(LAST_MADE_UP_TO_FORMATTED, keyFilingDates.getLastAccountsMadeUpTo());
        assertEquals(NEXT_DUE_ON_FORMATTED, keyFilingDates.getNextAccountsDue());
        assertEquals("5", keyFilingDates.getAccountingReferenceDate().getDay());
        assertEquals("5", keyFilingDates.getAccountingReferenceDate().getMonth());

        assertEquals(CONF_STATEMENT_LAST_MADE_UP_TO_FORMATTED, keyFilingDates.getLastConfirmationStatement());
        assertEquals(CONF_STATEMENT_NEXT_DUE_ON_FORMATTED, keyFilingDates.getNextConfirmationStatement());

        assertEquals(LAST_FULL_MEMBERS_LIST_FORMATTED, keyFilingDates.getLastMembersList());

    }

}
