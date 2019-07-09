package uk.gov.companieshouse.document.generator.accounts.data.cic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import uk.gov.companieshouse.api.ApiClient;
import uk.gov.companieshouse.api.error.ApiErrorResponseException;
import uk.gov.companieshouse.api.handler.exception.URIValidationException;
import uk.gov.companieshouse.api.model.accounts.cic.CicReportApi;
import uk.gov.companieshouse.document.generator.accounts.mapping.cic.mappers.CicReportMapper;
import uk.gov.companieshouse.document.generator.accounts.mapping.cic.model.CicReport;
import uk.gov.companieshouse.document.generator.accounts.mapping.cic.model.CicReportApiData;
import uk.gov.companieshouse.document.generator.accounts.service.ApiClientService;

@Component
public class CicReportManager {

    @Autowired
    private ApiClientService apiClientService;

    @Autowired
    private CicReportMapper cicReportMapper;

    public CicReport getCicReport(String cicReportLink) throws URIValidationException, ApiErrorResponseException {

        ApiClient apiClient = apiClientService.getApiClient();

        CicReportApiData cicReportApiData = new CicReportApiData();

        CicReportApi cicReportApi = apiClient.cicReport().get(cicReportLink).execute().getData();

        try {
            if (cicReportApi.getLinks().getStatements() != null) {

                cicReportApiData.setCicStatements(apiClient.cicReport().statements()
                        .get(cicReportApi.getLinks().getStatements()).execute().getData());
            }

            if (cicReportApi.getLinks().getApproval() != null) {

                cicReportApiData.setCicApproval(
                        apiClient.cicReport().approval().get(cicReportApi.getLinks().getApproval())
                                .execute().getData());
            }
        } catch (ApiErrorResponseException e) {

            if (e.getStatusCode() != HttpStatus.NOT_FOUND.value()) {
                throw e;
            }
        }

        return cicReportMapper.mapCicReport(cicReportApiData);
    }
}
