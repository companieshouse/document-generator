package uk.gov.companieshouse.document.generator.company.report.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriTemplate;
import uk.gov.companieshouse.api.ApiClient;
import uk.gov.companieshouse.api.error.ApiErrorResponseException;
import uk.gov.companieshouse.api.handler.exception.URIValidationException;
import uk.gov.companieshouse.api.handler.statements.request.StatementsList;
import uk.gov.companieshouse.api.model.statements.StatementsApi;
import uk.gov.companieshouse.document.generator.company.report.exception.ServiceException;

@Service
public class StatementsService {

    private CompanyReportApiClientService companyReportApiClientService;

    @Autowired
    public StatementsService(CompanyReportApiClientService companyReportApiClientService) {
        this.companyReportApiClientService = companyReportApiClientService;
    }

    private static final UriTemplate GET_STATEMENTS_URI =
            new UriTemplate("/company/{companyNumber}/persons-with-significant-control-statements");

    public StatementsApi getStatements(String companyNumber) throws ServiceException {

        StatementsApi statementsApi;

        ApiClient apiClient = companyReportApiClientService.getApiClient();

        String uri = GET_STATEMENTS_URI.expand(companyNumber).toString();

        try {
            StatementsList statementsList = apiClient.statements().list(uri);
            statementsList.addQueryParams("items_per_page", "100");

            statementsApi = statementsList.execute().getData();
        } catch (ApiErrorResponseException e) {

            throw new ServiceException("Error retrieving psc statements", e);
        } catch (URIValidationException e) {

            throw new ServiceException("Invalid URI for psc statements resource", e);
        }
        return statementsApi;
    }
}
