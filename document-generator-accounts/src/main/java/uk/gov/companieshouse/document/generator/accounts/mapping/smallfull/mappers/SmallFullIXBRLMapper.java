package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.mappers;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.SmallFullApiData;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.SmallFullAccountIxbrl;

@Mapper
@DecoratedWith(SmallFullIXBRLMapperDecorator.class)
public interface SmallFullIXBRLMapper {

    SmallFullIXBRLMapper INSTANCE = Mappers.getMapper(SmallFullIXBRLMapper.class);


    @Mappings({
            @Mapping(source = "smallFullApiData.approval.date", target = "approvalDate"),
            @Mapping(source = "smallFullApiData.approval.name", target = "approvalName")
    })
    SmallFullAccountIxbrl mapSmallFullIXBRLModel(SmallFullApiData smallFullApiData);

}
