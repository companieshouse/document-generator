package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.mortgagechargedetails;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.web.context.annotation.RequestScope;
import uk.gov.companieshouse.api.model.charges.ChargesApi;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.mortgagechargedetails.MortgageChargeDetails;

import java.util.Optional;

@RequestScope
@Mapper(componentModel = "spring", uses = {ApiToChargesMapper.class})
public abstract class ApiToMortgageChargeDetailsMapper {

    public abstract MortgageChargeDetails apiToMortgageChargeDetails(ChargesApi chargesApi);

    @AfterMapping
    protected void setOutstanding(ChargesApi chargesApi, @MappingTarget MortgageChargeDetails mortgageChargeDetails) {

        if (chargesApi != null) {

            Long totalCount = Optional.ofNullable(chargesApi.getTotalCount()).orElse(0L);
            Long satisfiedCount = Optional.ofNullable(chargesApi.getSatisfiedCount()).orElse(0L);
            Long partSatisfiedCount = Optional.ofNullable(chargesApi.getPartSatisfiedCount()).orElse(0L);

            Long outstanding = totalCount - (satisfiedCount + partSatisfiedCount);

            mortgageChargeDetails.setOutstanding(outstanding);
        }
    }
}
