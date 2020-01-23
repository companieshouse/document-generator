package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.web.context.annotation.RequestScope;
import uk.gov.companieshouse.api.model.accounts.directorsreport.StatementsApi;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.directorsreport.DirectorsReport;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.directorsreport.DirectorsReportStatements;

@RequestScope
@Mapper(componentModel= "spring")
public interface ApiToDirectorsReportMapper {

    @Mappings({

            @Mapping(source = "directorsReportStatements.additionalInformation",
            target = "additionalInformation"),
            @Mapping(source = "directorsReportStatements.companyPolicyOnDisabledEmployees",
            target = "companyPolicyOnDisabledEmployees"),
            @Mapping(source = "directorsReportStatements.politicalAndCharitableDonations",
            target = "politicalAndCharitableDonations"),
            @Mapping(source = "directorsReportStatements.principalActivities",
            target = "principalActivities")
    })
    DirectorsReportStatements apiToStatements(StatementsApi directorsReportStatements);
}
