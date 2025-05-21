package uk.gov.companieshouse.document.generator.company.report.service.oracle;

import static uk.gov.companieshouse.document.generator.company.report.CompanyReportDocumentInfoServiceImpl.MODULE_NAME_SPACE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.companieshouse.api.error.ApiErrorResponseException;
import uk.gov.companieshouse.api.handler.exception.URIValidationException;
import uk.gov.companieshouse.api.model.company.CompanyProfileApi;
import uk.gov.companieshouse.document.generator.company.report.exception.OracleQueryApiException;
import uk.gov.companieshouse.document.generator.company.report.service.ReportApiClientService;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;
import uk.gov.companieshouse.logging.util.DataMap;

@Service
public class CompanyServiceOracle {

    public static final String COMPANY = "/company/";

    private static final Logger LOG = LoggerFactory.getLogger(MODULE_NAME_SPACE);

    private final ReportApiClientService apiClientService;

    @Autowired
    public CompanyServiceOracle(ReportApiClientService apiClientService) {
        this.apiClientService = apiClientService;
    }

    public CompanyProfileApi getCompanyProfile(String companyNumber) throws OracleQueryApiException {
        DataMap requestDataMap = new DataMap.Builder().
                companyNumber(companyNumber).
                build();
        LOG.info("CompanyServiceOracle: Retrieving Company Profile", requestDataMap.getLogMap());

        try {
            return apiClientService
                    .getInternalApiClient()
                    .company()
                    .get(COMPANY + companyNumber)
                    .execute()
                    .getData();
        } catch (URIValidationException | ApiErrorResponseException e) {
            var message = String.format("Error Retrieving Company Profile data for %s", companyNumber);
            LOG.error(message, e, requestDataMap.getLogMap());
            throw new OracleQueryApiException(message, e);
        }
    }

}