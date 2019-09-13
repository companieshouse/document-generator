package uk.gov.companieshouse.document.generator.company.report.mapping;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.model.charges.ChargesApi;
import uk.gov.companieshouse.api.model.company.CompanyProfileApi;
import uk.gov.companieshouse.api.model.company.foreigncompany.ForeignCompanyDetailsApi;
import uk.gov.companieshouse.api.model.filinghistory.FilingApi;
import uk.gov.companieshouse.api.model.filinghistory.FilingHistoryApi;
import uk.gov.companieshouse.api.model.insolvency.InsolvencyApi;
import uk.gov.companieshouse.api.model.officers.OfficersApi;
import uk.gov.companieshouse.api.model.psc.PscsApi;
import uk.gov.companieshouse.api.model.registers.CompanyRegistersApi;
import uk.gov.companieshouse.api.model.registers.RegistersApi;
import uk.gov.companieshouse.api.model.statements.StatementsApi;
import uk.gov.companieshouse.api.model.ukestablishments.UkEstablishmentsApi;
import uk.gov.companieshouse.api.model.ukestablishments.UkEstablishmentsItemsApi;
import uk.gov.companieshouse.document.generator.company.report.handler.RequestParameters;
import uk.gov.companieshouse.document.generator.company.report.mapping.mappers.CompanyReportMapper;
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
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.currentappointments.CurrentAppointments;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.foreigncompanydetails.ForeignCompanyDetails;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.insolvency.Insolvency;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.keyfilingdates.KeyFilingDates;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.mortgagechargedetails.MortgageChargeDetails;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.pscs.Pscs;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.registers.CompanyRegisters;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.registrationinformation.RegistrationInformation;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.statements.Statements;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.ukestablishment.UkEstablishment;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CompanyReportMapperTest {

    @Mock
    private ApiToRegistrationInformationMapper apiToRegistrationInformationMapper;

    @Mock
    private ApiToPreviousNamesMapper apiToPreviousNamesMapper;

    @Mock
    private ApiToKeyFilingDatesMapper apiToKeyFilingDatesMapper;

    @Mock
    private ApiToPscsMapper apiToPscsMapper;

    @Mock
    private ApiToForeignCompanyDetailsMapper apiToForeignCompanyDetailsMapper;

    @Mock
    private ApiToCurrentAppointmentsMapper apiToCurrentAppointmentsMapper;

    @Mock
    private ApiToUkEstablishmentMapper apiToUkEstablishmentMapper;

    @Mock
    private ApiToRecentFilingHistoryMapper apiToRecentFilingHistoryMapper;

    @Mock
    private ApiToPscStatementsMapper apiToPscStatementsMapper;

    @Mock
    private ApiToMortgageChargeDetailsMapper apiToMortgageChargeDetailsMapper;

    @Mock
    private ApiToRegistersMapper apiToRegistersMapper;

    @Mock
    private ApiToInsolvencyMapper apiToInsolvencyMapper;

    @InjectMocks
    private CompanyReportMapper companyReportMapper;

    private static final String COMPANY_NUMBER = "CN001122";

    @Test
    @DisplayName("Test company report mapper is successful")
    void testCompanyReportMapperSuccessful() {

        when(apiToRegistrationInformationMapper.apiToRegistrationInformation(any(CompanyProfileApi.class))).thenReturn(new RegistrationInformation());
        when(apiToPreviousNamesMapper.apiToPreviousNamesMapper(anyList())).thenReturn(new ArrayList<>());
        when(apiToKeyFilingDatesMapper.apiToKeyFilingDates(any(CompanyProfileApi.class))).thenReturn(new KeyFilingDates());
        when(apiToPscsMapper.apiToPscsMapper(any(PscsApi.class))).thenReturn(new Pscs());
        when(apiToForeignCompanyDetailsMapper.apiToForeignCompanyDetails(any(ForeignCompanyDetailsApi.class))).thenReturn(new ForeignCompanyDetails());
        when(apiToCurrentAppointmentsMapper.apiToCurrentAppointmentsMapper(any(OfficersApi.class))).thenReturn(new CurrentAppointments());
        when(apiToUkEstablishmentMapper.apiToUkEstablishmentMapper(anyList())).thenReturn(new ArrayList<>());
        when(apiToRecentFilingHistoryMapper.apiToRecentFilingHistoryMapper(anyList())).thenReturn(new ArrayList<>());
        when(apiToPscStatementsMapper.apiToStatementsMapper(any(StatementsApi.class))).thenReturn(new Statements());
        when(apiToMortgageChargeDetailsMapper.apiToMortgageChargeDetails(any(ChargesApi.class))).thenReturn(new MortgageChargeDetails());
        when(apiToRegistersMapper.apiToRegistersMapper(any(RegistersApi.class))).thenReturn(new CompanyRegisters());
        when(apiToInsolvencyMapper.apiToInsolvencyMapper(any(InsolvencyApi.class))).thenReturn(new Insolvency());

        CompanyReport companyReport =
            companyReportMapper.mapCompanyReport(createCompanyReportApiData(), createRequestParameters());

        assertNotNull(companyReport);

    }

    private CompanyReportApiData createCompanyReportApiData() {

        CompanyReportApiData companyReportApiData = new CompanyReportApiData();

        companyReportApiData.setChargesApi(new ChargesApi());
        companyReportApiData.setInsolvencyApi(new InsolvencyApi());
        companyReportApiData.setCompanyRegistersApi(createCompanyRegistersApi());
        companyReportApiData.setStatementsApi(new StatementsApi());
        companyReportApiData.setFilingHistoryApi(createFilingHistoryApi());
        companyReportApiData.setUkEstablishmentsApi(createUkEstablishmentsApi());
        companyReportApiData.setOfficersApi(new OfficersApi());
        companyReportApiData.setPscsApi(new PscsApi());
        companyReportApiData.setCompanyProfileApi(createCompanyProfileApi());

        return companyReportApiData;
    }

    private CompanyRegistersApi createCompanyRegistersApi() {

        CompanyRegistersApi companyRegistersApi = new CompanyRegistersApi();
        companyRegistersApi.setRegisters(new RegistersApi());

        return companyRegistersApi;
    }

    private UkEstablishmentsApi createUkEstablishmentsApi() {

        UkEstablishmentsApi ukEstablishmentsApi = new UkEstablishmentsApi();
        ukEstablishmentsApi.setItems(createUkEstablishmentsItems());

        return ukEstablishmentsApi;
    }

    private List<UkEstablishmentsItemsApi> createUkEstablishmentsItems() {

        List ukEstablishmentsItems = new ArrayList();
        UkEstablishmentsItemsApi ukEstablishmentsItemsApi = new UkEstablishmentsItemsApi();
        ukEstablishmentsItems.add(ukEstablishmentsItemsApi);

        return ukEstablishmentsItems;
    }

    private FilingHistoryApi createFilingHistoryApi() {

        FilingHistoryApi filingHistoryApi = new FilingHistoryApi();
        filingHistoryApi.setItems(createFilinghistoryItems());

        return filingHistoryApi;
    }

    private List<FilingApi> createFilinghistoryItems() {

        List filingItems = new ArrayList();
        FilingApi filingApi = new FilingApi();
        filingItems.add(filingApi);

        return filingItems;

    }

    private CompanyProfileApi createCompanyProfileApi() {

        CompanyProfileApi companyProfileApi = new CompanyProfileApi();
        companyProfileApi.setForeignCompanyDetails(new ForeignCompanyDetailsApi());
        companyProfileApi.setPreviousCompanyNames(new ArrayList<>());

        return companyProfileApi;
    }

    private RequestParameters createRequestParameters() {

        RequestParameters requestParameters = new RequestParameters();
        requestParameters.setResourceUri("/resource/test");
        requestParameters.setRequestId("request_id");
        requestParameters.setCompanyNumber(COMPANY_NUMBER);

        return requestParameters;
    }
}
