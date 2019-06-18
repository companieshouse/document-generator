package uk.gov.companieshouse.document.generator.accounts.mapping.cic.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.springframework.web.context.annotation.RequestScope;
import uk.gov.companieshouse.api.model.accounts.cic.approval.CicApprovalApi;
import uk.gov.companieshouse.document.generator.accounts.mapping.cic.model.Approval;

@RequestScope
@Mapper(componentModel = "spring")
public interface ApiToApprovalMapper {

    ApiToApprovalMapper INSTANCE = Mappers.getMapper(ApiToApprovalMapper.class);

    @Mappings({
            @Mapping(source = "name",
                    target = "name"),
            @Mapping(source = "date",
                    target = "date"),
    })
    Approval apiToApproval(CicApprovalApi cicApproval);
}
