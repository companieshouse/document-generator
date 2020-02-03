package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.web.context.annotation.RequestScope;
import uk.gov.companieshouse.api.model.accounts.directorsreport.ApprovalApi;
import uk.gov.companieshouse.api.model.accounts.directorsreport.DirectorApi;
import uk.gov.companieshouse.api.model.accounts.directorsreport.DirectorsReportApi;
import uk.gov.companieshouse.api.model.accounts.directorsreport.SecretaryApi;
import uk.gov.companieshouse.api.model.accounts.directorsreport.StatementsApi;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.directorsreport.Approval;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.directorsreport.Directors;

import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.directorsreport.DirectorsReportStatements;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.directorsreport.Secretary;

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

    @Mappings({

            @Mapping(source = "secretary.name",
                    target = "name"),
    })
    Secretary apiToSecretary(SecretaryApi secretary);

    @Mappings({

            @Mapping(source = "approval.name",
                    target = "name"),
            @Mapping(source = "approval.date",
                    target = "date"),
    })
    Approval apiToApproval(ApprovalApi approval);


    Directors[] apiToDirector(DirectorApi[] directors);




}
