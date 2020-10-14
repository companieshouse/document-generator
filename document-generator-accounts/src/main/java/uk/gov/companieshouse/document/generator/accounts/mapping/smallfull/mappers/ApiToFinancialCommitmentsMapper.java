package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.web.context.annotation.RequestScope;
import uk.gov.companieshouse.api.handler.smallfull.financialcommitments.FinancialCommitmentsApi;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.FinancialCommitments;

@RequestScope
@Mapper(componentModel = "spring")
public interface ApiToFinancialCommitmentsMapper {

    @Mappings({
            @Mapping(source = "financialCommitmentsApi.details", target = "details")
    })
    FinancialCommitments apiToFinancialCommitments(FinancialCommitmentsApi financialCommitmentsApi);
}
