package uk.gov.companieshouse.document.generator.accounts.mapping.cic.mappers;

import java.time.LocalDate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.web.context.annotation.RequestScope;
import uk.gov.companieshouse.accountsdates.AccountsDatesHelper;
import uk.gov.companieshouse.accountsdates.impl.AccountsDatesHelperImpl;
import uk.gov.companieshouse.api.model.accounts.cic.approval.CicApprovalApi;
import uk.gov.companieshouse.document.generator.accounts.mapping.cic.model.Approval;

@RequestScope
@Mapper(componentModel = "spring")
public interface ApiToApprovalMapper {

    AccountsDatesHelper accountsDatesHelper = new AccountsDatesHelperImpl();

    @Mappings({
            @Mapping(source = "name",
                    target = "name"),
            @Mapping(source = "date",
                    target = "date",
                    qualifiedByName = "localDateToDisplayDateMapper"),
    })
    Approval apiToApproval(CicApprovalApi cicApproval);

    @Named("localDateToDisplayDateMapper")
    default String localDateToDisplayDate(LocalDate date) {
        return accountsDatesHelper.convertLocalDateToDisplayDate(date);
    }
}
