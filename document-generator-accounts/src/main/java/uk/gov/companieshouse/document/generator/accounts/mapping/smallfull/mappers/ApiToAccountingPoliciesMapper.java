package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.web.context.annotation.RequestScope;
import uk.gov.companieshouse.api.model.accounts.smallfull.AccountingPoliciesApi;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.accountingpolicies.AccountingPolicies;

@RequestScope
@Mapper(componentModel = "spring")
public interface ApiToAccountingPoliciesMapper {

    @Mappings({
            @Mapping(source = "accountingPolicies.basisOfMeasurementAndPreparation", target = "basisOfMeasurementAndPreparation"),
            @Mapping(source = "accountingPolicies.turnoverPolicy", target = "turnoverPolicy"),
            @Mapping(source = "accountingPolicies.tangibleFixedAssetsDepreciationPolicy", target = "tangibleFixedAssetsDepreciationPolicy"),
            @Mapping(source = "accountingPolicies.intangibleFixedAssetsAmortisationPolicy", target = "intangibleFixedAssetsAmortisationPolicy"),
            @Mapping(source = "accountingPolicies.valuationInformationAndPolicy", target = "valuationInformationPolicy"),
            @Mapping(source = "accountingPolicies.otherAccountingPolicy", target = "otherAccountingPolicy")
    })
    AccountingPolicies apiToAccountingPolicies(AccountingPoliciesApi accountingPolicies);
}
