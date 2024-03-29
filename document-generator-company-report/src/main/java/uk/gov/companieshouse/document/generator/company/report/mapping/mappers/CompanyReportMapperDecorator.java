package uk.gov.companieshouse.document.generator.company.report.mapping.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import uk.gov.companieshouse.api.model.company.CompanyProfileApi;
import uk.gov.companieshouse.api.model.company.CorporateAnnotationApi;
import uk.gov.companieshouse.api.model.company.PreviousCompanyNamesApi;
import uk.gov.companieshouse.api.model.company.foreigncompany.ForeignCompanyDetailsApi;
import uk.gov.companieshouse.api.model.exemptions.ExemptionsApi;
import uk.gov.companieshouse.api.model.filinghistory.FilingApi;
import uk.gov.companieshouse.api.model.insolvency.InsolvencyApi;
import uk.gov.companieshouse.api.model.officers.OfficersApi;
import uk.gov.companieshouse.api.model.psc.PscsApi;
import uk.gov.companieshouse.api.model.registers.RegistersApi;
import uk.gov.companieshouse.api.model.statements.StatementsApi;
import uk.gov.companieshouse.api.model.ukestablishments.UkEstablishmentsItemsApi;
import uk.gov.companieshouse.document.generator.company.report.mapping.mappers.corporateannotation.ApiToCorporateAnnotationMapper;
import uk.gov.companieshouse.document.generator.company.report.mapping.mappers.currentappointments.ApiToCurrentAppointmentsMapper;
import uk.gov.companieshouse.document.generator.company.report.mapping.mappers.exemptions.ApiToExemptionsMapper;
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
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.corporateannotation.CorporateAnnotation;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.currentappointments.CurrentAppointments;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.exemptions.Exemptions;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.foreigncompanydetails.ForeignCompanyDetails;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.insolvency.Insolvency;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.keyfilingdates.KeyFilingDates;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.mortgagechargedetails.MortgageChargeDetails;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.previousnames.PreviousNames;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.pscs.Pscs;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.recentfilinghistory.RecentFilingHistory;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.registers.CompanyRegisters;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.registrationinformation.RegistrationInformation;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.statements.Statements;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.ukestablishment.UkEstablishment;
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
    private ApiToCorporateAnnotationMapper apiToCorporateAnnotationMapper;

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
    private ApiToUkEstablishmentMapper apiToUkEstablishmentMapper;

    @Autowired
    private ApiToRecentFilingHistoryMapper apiToRecentFilingHistoryMapper;

    @Autowired
    private ApiToPscStatementsMapper apiToPscStatementsMapper;

    @Autowired
    private ApiToMortgageChargeDetailsMapper apiToMortgageChargeDetailsMapper;

    @Autowired
    private ApiToRegistersMapper apiToRegistersMapper;

    @Autowired
    private ApiToInsolvencyMapper apiToInsolvencyMapper;

    @Autowired
    private ApiToExemptionsMapper apiToExemptionsMapper;


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

            if (companyReportApiData.getCompanyProfileApi().getCorporateAnnotation() != null) {
                LOG.infoContext(requestId, "Map Data for Corporate Annotation", getDebugMap(companyNumber));
                companyReport.setCorporateAnnotation(setCorporateAnnotation(companyReportApiData.getCompanyProfileApi().getCorporateAnnotation()));
            }

            if (companyReportApiData.getOfficersApi() != null && companyReportApiData.getOfficersApi().getItems().size() > 0) {
                LOG.infoContext(requestId, "Map data for Current Appointments", getDebugMap(companyNumber));
                companyReport.setCurrentAppointments(setCurrentAppointments(companyReportApiData.getOfficersApi()));
            }

            if (companyReportApiData.getCompanyProfileApi().getAccounts() != null || companyReportApiData.getCompanyProfileApi().getConfirmationStatement() != null) {
                LOG.infoContext(requestId, "Map data for Key Filing Dates", getDebugMap(companyNumber));
                companyReport.setKeyFilingDates(setKeyFilingDates(companyReportApiData.getCompanyProfileApi()));
            }

            if (companyReportApiData.getFilingHistoryApi() != null && companyReportApiData.getFilingHistoryApi().getItems().size() > 0) {
                LOG.infoContext(requestId, "Map data for Recent Filing History", getDebugMap(companyNumber));
                companyReport.setRecentFilingHistory(setRecentFilingHistory(companyReportApiData.getFilingHistoryApi().getItems()));
            }

            if (companyReportApiData.getPscsApi() != null) {
                LOG.infoContext(requestId, "Map Data for PSCS", getDebugMap(companyNumber));
                companyReport.setPscs(setPscs(companyReportApiData.getPscsApi()));
            }

            if(companyReportApiData.getStatementsApi() != null) {
                LOG.infoContext(requestId, "Map data for Statements", getDebugMap(companyNumber));
            }

            if (companyReportApiData.getStatementsApi() != null) {
                LOG.infoContext(requestId, "Map Data for Statements", getDebugMap(companyNumber));
                companyReport.setStatements(setStatements(companyReportApiData.getStatementsApi()));
            }

            if (companyReportApiData.getCompanyProfileApi().getForeignCompanyDetails() != null) {
                LOG.infoContext(requestId, "Map data for Foreign Company Details", getDebugMap(companyNumber));
                companyReport.setForeignCompanyDetails(setForeignCompanyDetails(companyReportApiData
                    .getCompanyProfileApi().getForeignCompanyDetails()));
            }

            if (companyReportApiData.getChargesApi() != null && companyReportApiData.getChargesApi().getItems() != null) {
                LOG.infoContext(requestId, "Map data for Mortgage Charge", getDebugMap(companyNumber));
                companyReport.setMortgageChargeDetails(setMortgageChargeDetails(companyReportApiData));
            }

            if (companyReportApiData.getInsolvencyApi() != null) {
                LOG.infoContext(requestId, "Map data for insolvency", getDebugMap(companyNumber));
                companyReport.setInsolvency(setInsolvency(companyReportApiData.getInsolvencyApi()));
            }

            if (companyReportApiData.getUkEstablishmentsApi() != null && companyReportApiData.getUkEstablishmentsApi().getItems() != null) {
                LOG.infoContext(requestId, "Map data for UK Establishments", getDebugMap(companyNumber));
                companyReport.setUkEstablishment(setUkEstablishments(companyReportApiData.getUkEstablishmentsApi().getItems()));
            }

            if (companyReportApiData.getCompanyRegistersApi() != null) {
                LOG.infoContext(requestId, "Map Data for Company Registers", getDebugMap(companyNumber));
                companyReport.setCompanyRegisters(setRegister(companyReportApiData.getCompanyRegistersApi().getRegisters()));
            }

            if (companyReportApiData.getCompanyExemptionsApi() !=null) {
                LOG.infoContext(requestId, "Map Data for Company Exemptions", getDebugMap(companyNumber));
                companyReport.setExemptions(setExemptions(companyReportApiData.getCompanyExemptionsApi().getExemptions()));
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

    private List<CorporateAnnotation> setCorporateAnnotation(List<CorporateAnnotationApi> corporateAnnotation) {
        return apiToCorporateAnnotationMapper.apiToCorporateAnnotations(corporateAnnotation);
    }

    private CurrentAppointments setCurrentAppointments(OfficersApi officersApi) {
        return apiToCurrentAppointmentsMapper.apiToCurrentAppointmentsMapper(officersApi);
    }

    private KeyFilingDates setKeyFilingDates(CompanyProfileApi companyProfileApi) {
        return apiToKeyFilingDatesMapper.apiToKeyFilingDates(companyProfileApi);
    }

    private List<RecentFilingHistory> setRecentFilingHistory(List<FilingApi> filingHistory) {
        return apiToRecentFilingHistoryMapper.apiToRecentFilingHistoryMapper(filingHistory);
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

    private MortgageChargeDetails setMortgageChargeDetails(CompanyReportApiData companyReportApiData) {
        return apiToMortgageChargeDetailsMapper.apiToMortgageChargeDetails(companyReportApiData.getChargesApi());
    }

    private List<UkEstablishment> setUkEstablishments(List<UkEstablishmentsItemsApi> ukEstablishmentsItemsApi) {
        return apiToUkEstablishmentMapper.apiToUkEstablishmentMapper(ukEstablishmentsItemsApi);
    }

    private CompanyRegisters setRegister(RegistersApi registersApi) {
        return apiToRegistersMapper.apiToRegistersMapper(registersApi);
    }

    private Insolvency setInsolvency (InsolvencyApi insolvencyApi){
        return apiToInsolvencyMapper.apiToInsolvencyMapper(insolvencyApi);
    }

    private Exemptions setExemptions (ExemptionsApi exemptionsApi) {
        return apiToExemptionsMapper.apiToExemptionsMapper(exemptionsApi);
    }

    private Map<String, Object> getDebugMap (String companyNumber){
        Map<String, Object> logMap = new HashMap<>();
        logMap.put("COMPANY_NUMBER", companyNumber);

        return logMap;
    }
}


