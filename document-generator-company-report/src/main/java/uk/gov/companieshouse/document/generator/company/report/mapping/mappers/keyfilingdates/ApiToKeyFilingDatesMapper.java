package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.keyfilingdates;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.web.context.annotation.RequestScope;
import uk.gov.companieshouse.api.model.company.CompanyProfileApi;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.common.DateDayMonthYear;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.keyfilingdates.KeyFilingDates;

@RequestScope
@Mapper(componentModel = "spring")
public abstract class ApiToKeyFilingDatesMapper {

    private static final String D_MMMM_UUUU = "d MMMM uuuu";

    @Mappings({
            @Mapping(source = "companyProfileApi.accounts.accountingReferenceDate.day", target =
                    "accountingReferenceDate.day")
    })

    public abstract KeyFilingDates apiToKeyFilingDates(CompanyProfileApi companyProfileApi);

    @AfterMapping
    protected void convertKeyFilingDates(CompanyProfileApi companyProfileApi,
            @MappingTarget KeyFilingDates keyFilingDates) {

        if (hasAccounts(companyProfileApi)) {

            formatNextMadeUpTo(companyProfileApi, keyFilingDates);

            formatLastAccountsMadeUpTo(companyProfileApi, keyFilingDates);

            formatNextAccountsDueOn(companyProfileApi, keyFilingDates);

            formatAccountingReferenceDate(companyProfileApi, keyFilingDates);
        }

        if (hasConfirmationStatement(companyProfileApi)) {

            formatConfirmationStatementLastMadeUpTo(companyProfileApi, keyFilingDates);

            formatConfirmationStatementNextDue(companyProfileApi, keyFilingDates);
        }

        formatLastMembersList(companyProfileApi, keyFilingDates);
    }

    private void formatLastMembersList(CompanyProfileApi companyProfileApi, @MappingTarget KeyFilingDates keyFilingDates) {

        if (companyProfileApi.getLastFullMembersListDate() != null) {

            LocalDate lastMembersList = companyProfileApi.getLastFullMembersListDate();
            keyFilingDates.setLastMembersList(lastMembersList.format(getFormatter()));
        }
    }

    private void formatConfirmationStatementNextDue(CompanyProfileApi companyProfileApi, @MappingTarget KeyFilingDates keyFilingDates) {

        if (companyProfileApi.getConfirmationStatement().getNextDue() != null) {

            LocalDate nextConfirmationStatement =
                companyProfileApi.getConfirmationStatement().getNextDue();
            keyFilingDates.setNextConfirmationStatement(nextConfirmationStatement.format(getFormatter()));
        }
    }

    private void formatConfirmationStatementLastMadeUpTo(CompanyProfileApi companyProfileApi,
            @MappingTarget KeyFilingDates keyFilingDates) {

        if (companyProfileApi.getConfirmationStatement().getLastMadeUpTo() != null) {

            LocalDate lastConfirmationStatement =
                companyProfileApi.getConfirmationStatement().getLastMadeUpTo();
            keyFilingDates.setLastConfirmationStatement(lastConfirmationStatement.format(getFormatter()));
        }
    }

    private void formatAccountingReferenceDate(CompanyProfileApi companyProfileApi, @MappingTarget KeyFilingDates keyFilingDates) {

        if (companyProfileApi.getAccounts().getAccountingReferenceDate() != null) {

            DateDayMonthYear accountingReferenceDate = new DateDayMonthYear();
            String monthString = getNameOfMonth(companyProfileApi);

            accountingReferenceDate.setDay(companyProfileApi.getAccounts().getAccountingReferenceDate().getDay());
            //Sentence case month string
            accountingReferenceDate.setMonth(monthString.substring(0,1).toUpperCase()
                + monthString.substring(1).toLowerCase());

            keyFilingDates.setAccountingReferenceDate(accountingReferenceDate);
        }
    }

    private void formatNextAccountsDueOn(CompanyProfileApi companyProfileApi, @MappingTarget KeyFilingDates keyFilingDates) {

        if (companyProfileApi.getAccounts().getNextAccounts() != null &&
            companyProfileApi.getAccounts().getNextAccounts().getDueOn() != null) {

            LocalDate nextAccountsDue = companyProfileApi.getAccounts().getNextAccounts().getDueOn();
            keyFilingDates.setNextAccountsDue(nextAccountsDue.format(getFormatter()));
        }
    }

    private void formatNextMadeUpTo(CompanyProfileApi companyProfileApi, @MappingTarget KeyFilingDates keyFilingDates) {

        if (companyProfileApi.getAccounts().getNextMadeUpTo() != null) {
            LocalDate nextMadeUpTo = companyProfileApi.getAccounts().getNextMadeUpTo();
            keyFilingDates.setNextMadeUpTo(nextMadeUpTo.format(getFormatter()));
        }
    }

    private void formatLastAccountsMadeUpTo(CompanyProfileApi companyProfileApi, @MappingTarget KeyFilingDates keyFilingDates) {

        if (companyProfileApi.getAccounts().getLastAccounts() != null &&
            companyProfileApi.getAccounts().getLastAccounts().getMadeUpTo() != null) {

            LocalDate lastAccounts =
                companyProfileApi.getAccounts().getLastAccounts().getMadeUpTo();
            keyFilingDates.setLastAccountsMadeUpTo(lastAccounts.format(getFormatter()));
        }
    }

    private String getNameOfMonth(CompanyProfileApi companyProfileApi) {
        return Month.of(Integer.valueOf(companyProfileApi.getAccounts().getAccountingReferenceDate().getMonth())).name();
    }

    private Boolean hasAccounts(CompanyProfileApi companyProfileApi) {
        return companyProfileApi != null && companyProfileApi.getAccounts() != null;
    }

    private Boolean hasConfirmationStatement(CompanyProfileApi companyProfileApi) {
        return companyProfileApi != null && companyProfileApi.getConfirmationStatement() != null;
    }

    private DateTimeFormatter getFormatter() {
        return DateTimeFormatter.ofPattern(D_MMMM_UUUU);
    }
}