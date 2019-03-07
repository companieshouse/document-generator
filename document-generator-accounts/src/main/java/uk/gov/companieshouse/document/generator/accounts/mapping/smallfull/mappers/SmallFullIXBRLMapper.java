package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.mappers;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.web.context.annotation.RequestScope;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.SmallFullApiData;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.SmallFullAccountIxbrl;

@RequestScope
@Mapper(componentModel = "spring")
@DecoratedWith(SmallFullIXBRLMapperDecorator.class)
public interface SmallFullIXBRLMapper {

    @Mappings({
            @Mapping(source = "smallFullApiData.approval.name", target = "approvalName")
    })
    SmallFullAccountIxbrl mapSmallFullIXBRLModel(SmallFullApiData smallFullApiData);

}
