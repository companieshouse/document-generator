package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import uk.gov.companieshouse.api.model.company.CompanyProfileApi;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.company.Company;

@Mapper
public interface ApiToCompanyMapper {

    ApiToCompanyMapper INSTANCE = Mappers.getMapper(ApiToCompanyMapper.class);

    @Mappings({
            @Mapping(source = "companyProfile.companyNumber", target = "companyNumber"),
            @Mapping(source = "companyProfile.companyName", target = "companyName"),
            @Mapping(source = "companyProfile.jurisdiction", target = "jurisdiction")
    })
    Company apiToCompany(CompanyProfileApi companyProfile);
}
