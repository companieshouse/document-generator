package uk.gov.companieshouse.document.generator.company.report.mapping.mappers;


import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.springframework.web.context.annotation.RequestScope;
import uk.gov.companieshouse.document.generator.company.report.exception.MapperException;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.CompanyReportApiData;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.CompanyReport;

@RequestScope
@Mapper(componentModel = "spring")
@DecoratedWith(CompanyReportMapperDecorator.class)
public interface CompanyReportMapper {

    CompanyReport mapCompanyReport(CompanyReportApiData companyReportApiData) throws MapperException;
}
