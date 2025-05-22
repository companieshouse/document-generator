package uk.gov.companieshouse.document.generator.company.report.service.oracle;

import static uk.gov.companieshouse.document.generator.company.report.CompanyReportDocumentInfoServiceImpl.MODULE_NAME_SPACE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import uk.gov.companieshouse.api.error.ApiErrorResponseException;
import uk.gov.companieshouse.api.handler.exception.URIValidationException;
import uk.gov.companieshouse.api.model.company.CompanyProfileApi;
import uk.gov.companieshouse.document.generator.company.report.exception.OracleQueryApiException;
import uk.gov.companieshouse.document.generator.company.report.service.ReportApiClientService;
import uk.gov.companieshouse.document.generator.company.report.service.oracle.mapper.CompanyProfileMapper;
import uk.gov.companieshouse.document.generator.company.report.service.oracle.mapper.TempMapper;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;
import uk.gov.companieshouse.logging.util.DataMap;

@Service
public class CompanyServiceOracle {

    @Value("${ORACLE_QUERY_API_URL}")
    private String oracleQueryApiUrl;

    public static final String COMPANY = "/company/";

    private static final Logger LOG = LoggerFactory.getLogger(MODULE_NAME_SPACE);

    private final ReportApiClientService apiClientService;
    // private final CompanyProfileMapper companyProfileMapper;
    private final TempMapper companyProfileMapper = new TempMapper();

    @Autowired
    public CompanyServiceOracle(ReportApiClientService apiClientService/*, CompanyProfileMapper companyProfileMapper*/) {
        this.apiClientService = apiClientService;
        //this.companyProfileMapper = companyProfileMapper;
    }

    public CompanyProfileApi getCompanyProfile(String companyNumber) throws OracleQueryApiException {
        DataMap requestDataMap = new DataMap.Builder().
                companyNumber(companyNumber).
                build();
        LOG.info("CompanyServiceOracle: Retrieving Company Profile", requestDataMap.getLogMap());

        var internalApiClient = apiClientService.getInternalApiClient();

        LOG.debug("Base URL used [" + internalApiClient.getBasePath() + "]");

        try {
            var companyProfileData = internalApiClient
                    .privateCompanyResourceHandler()
                    .getCompanyFullProfile(COMPANY + companyNumber)
                    .execute()
                    .getData();

            return companyProfileMapper.toCompanyProfileApi(companyProfileData);

        } catch (URIValidationException | ApiErrorResponseException e) {
            var message = String.format("Error Retrieving Company Profile data for %s", companyNumber);
            LOG.error(message, e, requestDataMap.getLogMap());
            throw new OracleQueryApiException(message, e);
        }
    }

}