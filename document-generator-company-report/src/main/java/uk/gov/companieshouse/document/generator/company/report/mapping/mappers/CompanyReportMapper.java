package uk.gov.companieshouse.document.generator.company.report.mapping.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.companieshouse.api.model.charges.ChargesApi;
import uk.gov.companieshouse.api.model.company.CompanyProfileApi;
import uk.gov.companieshouse.api.model.filinghistory.FilingHistoryApi;
import uk.gov.companieshouse.api.model.insolvency.InsolvencyApi;
import uk.gov.companieshouse.api.model.officers.OfficersApi;
import uk.gov.companieshouse.api.model.psc.PscsApi;
import uk.gov.companieshouse.api.model.registers.CompanyRegistersApi;
import uk.gov.companieshouse.api.model.statements.StatementsApi;
import uk.gov.companieshouse.api.model.ukestablishments.UkEstablishmentsApi;
import uk.gov.companieshouse.document.generator.company.report.handler.RequestParameters;
import uk.gov.companieshouse.document.generator.company.report.mapping.mappers.currentappointments.ApiToCurrentAppointmentsMapper;
import uk.gov.companieshouse.document.generator.company.report.mapping.mappers.foreigncompanydetails.ApiToForeignCompanyDetailsMapper;
import uk.gov.companieshouse.document.generator.company.report.mapping.mappers.insolvency.ApiToInsolvencyMapper;
import uk.gov.companieshouse.document.generator.company.report.mapping.mappers.keyfilingdates.ApiToKeyFilingDatesMapper;
import uk.gov.companieshouse.document.generator.company.report.mapping.mappers.mortgagechargedetails.ApiToMortgageChargeDetailsMapper;
import uk.gov.companieshouse.document.generator.company.report.mapping.mappers.previousnames.ApiToPreviousNamesMapper;
import uk.gov.companieshouse.document.generator.company.report.mapping.mappers.pscs.ApiToPscsMapper;
import uk.gov.companieshouse.document.generator.company.report.mapping.mappers.recentfilinghistory.ApiToRecentFilingHistoryMapper;
import uk.gov.companieshouse.document.generator.company.report.mapping.mappers.registers.ApiToRegistersMapper;
import uk.gov.companieshouse.document.generator.company.report.mapping.mappers.registrationinformation.ApiToRegistrationInformationMapper;
import uk.gov.companieshouse.document.generator.company.report.mapping.mappers.statements.ApiToPscStatementsMapper;
import uk.gov.companieshouse.document.generator.company.report.mapping.mappers.ukestablishment.ApiToUkEstablishmentMapper;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.CompanyReportApiData;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.CompanyReport;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static uk.gov.companieshouse.document.generator.company.report.CompanyReportDocumentInfoServiceImpl.MODULE_NAME_SPACE;

@Component
public class CompanyReportMapper {

    private ApiToRegistrationInformationMapper apiToRegistrationInformationMapper;
    private ApiToPreviousNamesMapper apiToPreviousNamesMapper;
    private ApiToKeyFilingDatesMapper apiToKeyFilingDatesMapper;
    private ApiToPscsMapper apiToPscsMapper;
    private ApiToForeignCompanyDetailsMapper apiToForeignCompanyDetailsMapper;
    private ApiToCurrentAppointmentsMapper apiToCurrentAppointmentsMapper;
    private ApiToUkEstablishmentMapper apiToUkEstablishmentMapper;
    private ApiToRecentFilingHistoryMapper apiToRecentFilingHistoryMapper;
    private ApiToPscStatementsMapper apiToPscStatementsMapper;
    private ApiToMortgageChargeDetailsMapper apiToMortgageChargeDetailsMapper;
    private ApiToRegistersMapper apiToRegistersMapper;
    private ApiToInsolvencyMapper apiToInsolvencyMapper;

    @Autowired
    public CompanyReportMapper(ApiToRegistrationInformationMapper apiToRegistrationInformationMapper,
                           ApiToPreviousNamesMapper apiToPreviousNamesMapper,
                           ApiToKeyFilingDatesMapper apiToKeyFilingDatesMapper,
                           ApiToPscsMapper apiToPscsMapper,
                           ApiToForeignCompanyDetailsMapper apiToForeignCompanyDetailsMapper,
                           ApiToCurrentAppointmentsMapper apiToCurrentAppointmentsMapper,
                           ApiToUkEstablishmentMapper apiToUkEstablishmentMapper,
                           ApiToRecentFilingHistoryMapper apiToRecentFilingHistoryMapper,
                           ApiToPscStatementsMapper apiToPscStatementsMapper,
                           ApiToMortgageChargeDetailsMapper apiToMortgageChargeDetailsMapper,
                           ApiToRegistersMapper apiToRegistersMapper,
                           ApiToInsolvencyMapper apiToInsolvencyMapper) {

        this.apiToRegistrationInformationMapper = apiToRegistrationInformationMapper;
        this.apiToPreviousNamesMapper = apiToPreviousNamesMapper;
        this.apiToKeyFilingDatesMapper = apiToKeyFilingDatesMapper;
        this.apiToPscsMapper = apiToPscsMapper;
        this.apiToForeignCompanyDetailsMapper = apiToForeignCompanyDetailsMapper;
        this.apiToCurrentAppointmentsMapper = apiToCurrentAppointmentsMapper;
        this.apiToUkEstablishmentMapper = apiToUkEstablishmentMapper;
        this.apiToRecentFilingHistoryMapper = apiToRecentFilingHistoryMapper;
        this.apiToPscStatementsMapper = apiToPscStatementsMapper;
        this.apiToMortgageChargeDetailsMapper = apiToMortgageChargeDetailsMapper;
        this.apiToRegistersMapper = apiToRegistersMapper;
        this.apiToInsolvencyMapper = apiToInsolvencyMapper;
    }

    private static final Logger LOG = LoggerFactory.getLogger(MODULE_NAME_SPACE);

    public CompanyReport mapCompanyReport(CompanyReportApiData companyReportApiData, RequestParameters requestParameters) {

        CompanyReport companyReport = new CompanyReport();

        if(companyReportApiData != null) {
            mapCompanyProfileData(companyReport, companyReportApiData.getCompanyProfileApi(), requestParameters);
            mapCurrentAppointments(companyReport, companyReportApiData.getOfficersApi(), requestParameters);
            mapRecentFilingHistory(companyReport, companyReportApiData.getFilingHistoryApi(), requestParameters);
            mapPscs(companyReport, companyReportApiData.getPscsApi(), requestParameters);
            mapStatements(companyReport, companyReportApiData.getStatementsApi(), requestParameters);
            mapMortgageChargeDetails(companyReport, companyReportApiData.getChargesApi(), requestParameters);
            mapInsolvency(companyReport, companyReportApiData.getInsolvencyApi(), requestParameters);
            mapUkEstablishments(companyReport, companyReportApiData.getUkEstablishmentsApi(), requestParameters);
            mapRegister(companyReport, companyReportApiData.getCompanyRegistersApi(), requestParameters);
        }

        return companyReport;
    }

    private void mapCompanyProfileData(CompanyReport companyReport,
        CompanyProfileApi companyProfileApi, RequestParameters requestParameters) {

        if(companyProfileApi != null) {

            LOG.infoContext(requestParameters.getRequestId(), "mapping data registration information for company: "
                + requestParameters.getCompanyNumber(), getDebugMap(requestParameters.getResourceUri()));

            companyReport.setRegistrationInformation(apiToRegistrationInformationMapper
                .apiToRegistrationInformation(companyProfileApi));

            LOG.infoContext(requestParameters.getRequestId(), "mapping data filing dates for company: "
                + requestParameters.getCompanyNumber(), getDebugMap(requestParameters.getResourceUri()));

            companyReport.setKeyFilingDates(apiToKeyFilingDatesMapper.apiToKeyFilingDates(companyProfileApi));

            if(companyProfileApi.getForeignCompanyDetails() != null) {

                LOG.infoContext(requestParameters.getRequestId(), "mapping data foreign company details for company: "
                    + requestParameters.getCompanyNumber(), getDebugMap(requestParameters.getResourceUri()));

                companyReport.setForeignCompanyDetails(apiToForeignCompanyDetailsMapper
                    .apiToForeignCompanyDetails(companyProfileApi.getForeignCompanyDetails()));
            }

            if (companyProfileApi.getPreviousCompanyNames() != null) {

                LOG.infoContext(requestParameters.getRequestId(), "mapping data previous company names for company: "
                    + requestParameters.getCompanyNumber(), getDebugMap(requestParameters.getResourceUri()));

                companyReport.setPreviousNames(apiToPreviousNamesMapper
                    .apiToPreviousNamesMapper(companyProfileApi.getPreviousCompanyNames()));
            }
        }
    }

    private void mapCurrentAppointments(CompanyReport companyReport, OfficersApi officersApi,
        RequestParameters requestParameters) {

        if(officersApi != null) {

            LOG.infoContext(requestParameters.getRequestId(), "mapping data current appointments for company: "
                + requestParameters.getCompanyNumber(), getDebugMap(requestParameters.getResourceUri()));

            companyReport.setCurrentAppointments( apiToCurrentAppointmentsMapper.apiToCurrentAppointmentsMapper(officersApi));
        }
    }

    private void mapRecentFilingHistory(CompanyReport companyReport, FilingHistoryApi filingHistory,
        RequestParameters requestParameters) {

        if(filingHistory != null && filingHistory.getItems() != null && !filingHistory.getItems().isEmpty()) {

            LOG.infoContext(requestParameters.getRequestId(), "mapping data filing history for company: "
                + requestParameters.getCompanyNumber(), getDebugMap(requestParameters.getResourceUri()));

            companyReport.setRecentFilingHistory(apiToRecentFilingHistoryMapper.apiToRecentFilingHistoryMapper(filingHistory.getItems()));
        }
    }

    private void mapPscs(CompanyReport companyReport, PscsApi pscsApi, RequestParameters requestParameters) {

        if(pscsApi != null) {

            LOG.infoContext(requestParameters.getRequestId(), "mapping data PSCS for company: "
                + requestParameters.getCompanyNumber(), getDebugMap(requestParameters.getResourceUri()));

            companyReport.setPscs(apiToPscsMapper.apiToPscsMapper(pscsApi));
        }
    }

    private void mapStatements(CompanyReport companyReport, StatementsApi statementsApi,
        RequestParameters requestParameters) {

        if(statementsApi != null) {

            LOG.infoContext(requestParameters.getRequestId(), "mapping data statements for company: "
                + requestParameters.getCompanyNumber(), getDebugMap(requestParameters.getResourceUri()));

            companyReport.setStatements(apiToPscStatementsMapper.apiToStatementsMapper(statementsApi));
        }
    }

    private void mapMortgageChargeDetails(CompanyReport companyReport,
        ChargesApi chargesApi, RequestParameters requestParameters) {

        if(chargesApi != null) {

            LOG.infoContext(requestParameters.getRequestId(), "mapping data mortgage charges for company: "
                + requestParameters.getCompanyNumber(), getDebugMap(requestParameters.getResourceUri()));

            companyReport.setMortgageChargeDetails(apiToMortgageChargeDetailsMapper
                .apiToMortgageChargeDetails(chargesApi));
        }
    }

    private void mapInsolvency(CompanyReport companyReport, InsolvencyApi insolvencyApi,
        RequestParameters requestParameters) {

        if (insolvencyApi != null) {

            LOG.infoContext(requestParameters.getRequestId(), "mapping data insolvency for company: "
                + requestParameters.getCompanyNumber(), getDebugMap(requestParameters.getResourceUri()));

            companyReport.setInsolvency(apiToInsolvencyMapper.apiToInsolvencyMapper(insolvencyApi));
        }
    }

    private void mapUkEstablishments(CompanyReport companyReport, UkEstablishmentsApi ukEstablishmentsApi,
        RequestParameters requestParameters) {

        if(ukEstablishmentsApi != null && ukEstablishmentsApi.getItems() != null
            && !ukEstablishmentsApi.getItems().isEmpty()) {

            LOG.infoContext(requestParameters.getRequestId(), "mapping data UKEstablishments for company: "
                + requestParameters.getCompanyNumber(), getDebugMap(requestParameters.getResourceUri()));

            companyReport.setUkEstablishment(apiToUkEstablishmentMapper
                .apiToUkEstablishmentMapper(ukEstablishmentsApi.getItems()));
        }
    }

    private void mapRegister(CompanyReport companyReport, CompanyRegistersApi companyRegistersApi,
        RequestParameters requestParameters) {

        if(companyRegistersApi != null && companyRegistersApi.getRegisters() != null) {

            LOG.infoContext(requestParameters.getRequestId(), "mapping data company registers for company: "
                + requestParameters.getCompanyNumber(), getDebugMap(requestParameters.getResourceUri()));

            companyReport.setCompanyRegisters(apiToRegistersMapper
                .apiToRegistersMapper(companyRegistersApi.getRegisters()));
        }
    }

    private Map<String, Object> getDebugMap (String resourceUri){
        Map<String, Object> logMap = new HashMap<>();
        logMap.put("resource", resourceUri);

        return logMap;
    }
}


