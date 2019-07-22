package uk.gov.companieshouse.document.generator.company.report.mapping.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import uk.gov.companieshouse.api.model.company.CompanyProfileApi;
import uk.gov.companieshouse.api.model.company.PreviousCompanyNamesApi;
import uk.gov.companieshouse.api.model.officers.OfficersApi;
import uk.gov.companieshouse.document.generator.company.report.exception.MapperException;
import uk.gov.companieshouse.document.generator.company.report.mapping.mappers.currentappointments.ApiToCurrentAppointmentsMapper;
import uk.gov.companieshouse.document.generator.company.report.mapping.mappers.keyfilingdates.ApiToKeyFilingDatesMapper;
import uk.gov.companieshouse.document.generator.company.report.mapping.mappers.previousnames.ApiToPreviousNamesMapper;
import uk.gov.companieshouse.document.generator.company.report.mapping.mappers.recentfilinghistory.ApiToRecentFilingHistoryMapper;
import uk.gov.companieshouse.document.generator.company.report.mapping.mappers.registrationinformation.ApiToRegistrationInformationMapper;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.CompanyReportApiData;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.CompanyReport;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.currentappointments.CurrentAppointments;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.keyfilingdates.KeyFilingDates;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.previousnames.PreviousNames;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.registrationinformation.RegistrationInformation;

import java.io.IOException;
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
    private ApiToCurrentAppointmentsMapper apiToCurrentAppointmentsMapper;

    @Autowired
    private ApiToRecentFilingHistoryMapper apiToRecentFilingHistoryMapper;

    @Override
    public CompanyReport mapCompanyReport(CompanyReportApiData companyReportApiData) throws MapperException {

        CompanyReport companyReport = companyReportMapper.mapCompanyReport(companyReportApiData);

        if (companyReportApiData.getCompanyProfileApi() != null) {
            companyReport.setRegistrationInformation(setRegistrationInformation(companyReportApiData.getCompanyProfileApi()));

            if (companyReportApiData.getCompanyProfileApi().getPreviousCompanyNames() != null) {
                companyReport.setPreviousNames(setPreviousNames(companyReportApiData.getCompanyProfileApi().getPreviousCompanyNames()));
            }

            companyReport.setCurrentAppointments(setCurrentAppointments(companyReportApiData.getOfficersApi()));

            if (companyReportApiData.getCompanyProfileApi().getAccounts() != null) {
                companyReport.setKeyFilingDates(setKeyFilingDates(companyReportApiData.getCompanyProfileApi()));
            }

            if (companyReportApiData.getFilingHistoryApi() !=null) {
                companyReport.setRecentFilingHistory(apiToRecentFilingHistoryMapper.apiToRecentFilingHistoryMapperList(companyReportApiData.getFilingHistoryApi().getItems()));
            }
        }

        return companyReport;
    }

    private RegistrationInformation setRegistrationInformation(CompanyProfileApi companyProfileApi) throws MapperException {
        try {
            return apiToRegistrationInformationMapper.apiToRegistrationInformation(companyProfileApi);
        } catch (IOException e) {
            throw new MapperException("An error occurred when mapping to registration " +
                    "information", e);
        }
    }

    private KeyFilingDates setKeyFilingDates(CompanyProfileApi companyProfileApi) {
        return apiToKeyFilingDatesMapper.apiToKeyFilingDates(companyProfileApi);
    }

    private List<PreviousNames> setPreviousNames(List<PreviousCompanyNamesApi> previousCompanyNames) {
        return apiToPreviousNamesMapper.apiToPreviousNamesMapper(previousCompanyNames);
    }

    private CurrentAppointments setCurrentAppointments(OfficersApi officersApi) throws MapperException {
        try {
            return apiToCurrentAppointmentsMapper.apiToCurrentAppointmentsMapper(officersApi);
        } catch (MapperException e) {
            throw new MapperException("An error occurred when mapping to current appointments", e);
        }
    }
}
