package uk.gov.companieshouse.document.generator.company.report.mapping.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import uk.gov.companieshouse.api.model.company.CompanyProfileApi;
import uk.gov.companieshouse.api.model.company.PreviousCompanyNamesApi;
import uk.gov.companieshouse.api.model.company.foreigncompany.ForeignCompanyDetailsApi;
import uk.gov.companieshouse.api.model.officers.OfficersApi;
import uk.gov.companieshouse.api.model.psc.PscsApi;
import uk.gov.companieshouse.api.model.statements.StatementsApi;
import uk.gov.companieshouse.document.generator.company.report.mapping.mappers.currentappointments.ApiToCurrentAppointmentsMapper;
import uk.gov.companieshouse.document.generator.company.report.mapping.mappers.foreigncompanydetails.ApiToForeignCompanyDetailsMapper;
import uk.gov.companieshouse.document.generator.company.report.mapping.mappers.keyfilingdates.ApiToKeyFilingDatesMapper;
import uk.gov.companieshouse.document.generator.company.report.mapping.mappers.previousnames.ApiToPreviousNamesMapper;
import uk.gov.companieshouse.document.generator.company.report.mapping.mappers.pscs.ApiToPscsMapper;
import uk.gov.companieshouse.document.generator.company.report.mapping.mappers.registrationinformation.ApiToRegistrationInformationMapper;
import uk.gov.companieshouse.document.generator.company.report.mapping.mappers.statements.ApiToPscStatementsMapper;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.CompanyReportApiData;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.CompanyReport;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.currentappointments.CurrentAppointments;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.foreigncompanydetails.ForeignCompanyDetails;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.keyfilingdates.KeyFilingDates;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.previousnames.PreviousNames;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.pscs.Pscs;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.registrationinformation.RegistrationInformation;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.statements.Statements;

import java.util.List;

public class CompanyReportMapperDecorator implements CompanyReportMapper {

    @Autowired
    @Qualifier("delegate")
    private CompanyReportMapper companyReportMapper;

    @Autowired
    private ApiToRegistrationInformationMapper apiToRegistrationInformationMapper;

    @Autowired
    private ApiToPreviousNamesMapper apiToPreviousNamesMapper;

    @Autowired
    private ApiToKeyFilingDatesMapper apiToKeyFilingDatesMapper;

    @Autowired
    private ApiToPscsMapper apiToPscsMapper;

    @Autowired
    private ApiToForeignCompanyDetailsMapper apiToForeignCompanyDetailsMapper;

    @Autowired
    private ApiToCurrentAppointmentsMapper apiToCurrentAppointmentsMapper;

    @Autowired
    private ApiToPscStatementsMapper apiToPscStatementsMapper;


    @Override
    public CompanyReport mapCompanyReport(CompanyReportApiData companyReportApiData) {

        CompanyReport companyReport = companyReportMapper.mapCompanyReport(companyReportApiData);

        if (companyReportApiData.getCompanyProfileApi() != null) {
            companyReport.setRegistrationInformation(setRegistrationInformation(companyReportApiData.getCompanyProfileApi()));

            if (companyReportApiData.getCompanyProfileApi().getPreviousCompanyNames() != null) {
                companyReport.setPreviousNames(setPreviousNames(companyReportApiData.getCompanyProfileApi().getPreviousCompanyNames()));
            }

            if(companyReportApiData.getOfficersApi() != null && companyReportApiData.getOfficersApi().getItems().size() > 0) {
                    companyReport.setCurrentAppointments(setCurrentAppointments(companyReportApiData.getOfficersApi()));
            }

            if (companyReportApiData.getCompanyProfileApi().getAccounts() != null) {
                companyReport.setKeyFilingDates(setKeyFilingDates(companyReportApiData.getCompanyProfileApi()));
            }

            if(companyReportApiData.getPscsApi() != null) {
                companyReport.setPscs(setPscs(companyReportApiData.getPscsApi()));
            }

            if(companyReportApiData.getStatementsApi() != null) {
                companyReport.setStatements(setStatements(companyReportApiData.getStatementsApi()));
            }

            if (companyReportApiData.getCompanyProfileApi().getForeignCompanyDetails() != null) {
                companyReport.setForeignCompanyDetails(setForeignCompanyDetails(companyReportApiData
                    .getCompanyProfileApi().getForeignCompanyDetails()));
            }
        }

        return companyReport;
    }

    private RegistrationInformation setRegistrationInformation(CompanyProfileApi companyProfileApi) {
        return apiToRegistrationInformationMapper.apiToRegistrationInformation(companyProfileApi);
    }

    private List<PreviousNames> setPreviousNames(List<PreviousCompanyNamesApi> previousCompanyNames) {
        return apiToPreviousNamesMapper.apiToPreviousNamesMapper(previousCompanyNames);
    }

    private CurrentAppointments setCurrentAppointments(OfficersApi officersApi) {
            return apiToCurrentAppointmentsMapper.apiToCurrentAppointmentsMapper(officersApi);
    }

    private KeyFilingDates setKeyFilingDates(CompanyProfileApi companyProfileApi) {
        return apiToKeyFilingDatesMapper.apiToKeyFilingDates(companyProfileApi);
    }

    private Pscs setPscs(PscsApi pscsApi) {
        return apiToPscsMapper.apiToPscsMapper(pscsApi);
    }

    private Statements setStatements(StatementsApi statementsApi) {
        return apiToPscStatementsMapper.ApiToStatementsMapper(statementsApi);
    }

    private ForeignCompanyDetails setForeignCompanyDetails(ForeignCompanyDetailsApi foreignCompanyDetailsApi) {
        return apiToForeignCompanyDetailsMapper.apiToForeignCompanyDetails(foreignCompanyDetailsApi);
    }
}


