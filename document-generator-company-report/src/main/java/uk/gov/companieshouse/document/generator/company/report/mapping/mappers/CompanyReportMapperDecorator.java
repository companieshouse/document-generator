package uk.gov.companieshouse.document.generator.company.report.mapping.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import uk.gov.companieshouse.api.model.company.CompanyProfileApi;
import uk.gov.companieshouse.api.model.company.PreviousCompanyNamesApi;
import uk.gov.companieshouse.api.model.company.foreigncompany.ForeignCompanyDetailsApi;
import uk.gov.companieshouse.api.model.filinghistory.FilingApi;
import uk.gov.companieshouse.api.model.officers.OfficersApi;
import uk.gov.companieshouse.api.model.psc.PscsApi;
import uk.gov.companieshouse.document.generator.company.report.mapping.mappers.currentappointments.ApiToCurrentAppointmentsMapper;
import uk.gov.companieshouse.document.generator.company.report.mapping.mappers.foreigncompanydetails.ApiToForeignCompanyDetailsMapper;
import uk.gov.companieshouse.document.generator.company.report.mapping.mappers.keyfilingdates.ApiToKeyFilingDatesMapper;
import uk.gov.companieshouse.document.generator.company.report.mapping.mappers.previousnames.ApiToPreviousNamesMapper;
import uk.gov.companieshouse.document.generator.company.report.mapping.mappers.pscs.ApiToPscsMapper;
import uk.gov.companieshouse.document.generator.company.report.mapping.mappers.recentfilinghistory.ApiToRecentFilingHistoryMapper;
import uk.gov.companieshouse.document.generator.company.report.mapping.mappers.registrationinformation.ApiToRegistrationInformationMapper;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.CompanyReportApiData;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.CompanyReport;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.currentappointments.CurrentAppointments;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.foreigncompanydetails.ForeignCompanyDetails;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.keyfilingdates.KeyFilingDates;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.previousnames.PreviousNames;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.pscs.Pscs;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.recentfilinghistory.RecentFilingHistory;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.registrationinformation.RegistrationInformation;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static uk.gov.companieshouse.document.generator.company.report.CompanyReportDocumentInfoServiceImpl.MODULE_NAME_SPACE;

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
    private ApiToRecentFilingHistoryMapper apiToRecentFilingHistoryMapper;

    private static final Logger LOG = LoggerFactory.getLogger(MODULE_NAME_SPACE);

    @Override
    public CompanyReport mapCompanyReport(CompanyReportApiData companyReportApiData,
        String requestId, String companyNumber) {

        CompanyReport companyReport = companyReportMapper.mapCompanyReport(companyReportApiData, requestId, companyNumber);

        if (companyReportApiData.getCompanyProfileApi() != null) {
            companyReport.setRegistrationInformation(setRegistrationInformation(companyReportApiData.getCompanyProfileApi()));

            if (companyReportApiData.getCompanyProfileApi().getPreviousCompanyNames() != null) {
                LOG.infoContext(requestId, "Map Data for Previous Names", getDebugMap(companyNumber));
                companyReport.setPreviousNames(setPreviousNames(companyReportApiData.getCompanyProfileApi().getPreviousCompanyNames()));
            }

            if(companyReportApiData.getOfficersApi() != null && companyReportApiData.getOfficersApi().getItems().size() > 0) {
                LOG.infoContext(requestId, "Map data for Current Appointments", getDebugMap(companyNumber));
                companyReport.setCurrentAppointments(setCurrentAppointments(companyReportApiData.getOfficersApi()));
            }

            if (companyReportApiData.getCompanyProfileApi().getAccounts() != null) {
                LOG.infoContext(requestId, "Map data for Key Filing Dates", getDebugMap(companyNumber));
                companyReport.setKeyFilingDates(setKeyFilingDates(companyReportApiData.getCompanyProfileApi()));
            }

            if (companyReportApiData.getFilingHistoryApi() !=null) {
                LOG.infoContext(requestId, "Map data for Recent Filing History", getDebugMap(companyNumber));
                companyReport.setRecentFilingHistory(setRecentFilingHistory(companyReportApiData.getFilingHistoryApi().getItems()));
            }

            if(companyReportApiData.getPscsApi() != null) {
                LOG.infoContext(requestId, "Map Data for PSCS", getDebugMap(companyNumber));
                companyReport.setPscs(setPscs(companyReportApiData.getPscsApi()));
            }

            if (companyReportApiData.getCompanyProfileApi().getForeignCompanyDetails() != null) {
                LOG.infoContext(requestId, "Map data for Foreign Company Details", getDebugMap(companyNumber));
                companyReport.setForeignCompanyDetails(setForeignCompanyDetails(companyReportApiData
                    .getCompanyProfileApi().getForeignCompanyDetails()));
            }
        }

        return companyReport;
    }

    private List<RecentFilingHistory> setRecentFilingHistory(List<FilingApi> filingHistory) {
        return apiToRecentFilingHistoryMapper.apiToRecentFilingHistoryMapper(filingHistory);
    }

    private Pscs setPscs(PscsApi pscsApi) {
        return apiToPscsMapper.apiToPscsMapper(pscsApi);
    }

    private RegistrationInformation setRegistrationInformation(CompanyProfileApi companyProfileApi) {
        return apiToRegistrationInformationMapper.apiToRegistrationInformation(companyProfileApi);
    }

    private KeyFilingDates setKeyFilingDates(CompanyProfileApi companyProfileApi) {
        return apiToKeyFilingDatesMapper.apiToKeyFilingDates(companyProfileApi);
    }

    private List<PreviousNames> setPreviousNames(List<PreviousCompanyNamesApi> previousCompanyNames) {
        return apiToPreviousNamesMapper.apiToPreviousNamesMapper(previousCompanyNames);
    }

    private ForeignCompanyDetails setForeignCompanyDetails(ForeignCompanyDetailsApi foreignCompanyDetailsApi) {
        return apiToForeignCompanyDetailsMapper.apiToForeignCompanyDetails(foreignCompanyDetailsApi);
    }

    private CurrentAppointments setCurrentAppointments(OfficersApi officersApi) {
        return apiToCurrentAppointmentsMapper.apiToCurrentAppointmentsMapper(officersApi);
    }

    private Map<String, Object> getDebugMap(String companyNumber) {
        Map<String, Object> logMap = new HashMap<>();
        logMap.put("COMPANY_NUMBER", companyNumber);

        return logMap;
    }
}


