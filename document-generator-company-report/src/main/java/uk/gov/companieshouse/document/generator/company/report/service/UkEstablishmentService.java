package uk.gov.companieshouse.document.generator.company.report.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriTemplate;
import uk.gov.companieshouse.api.ApiClient;
import uk.gov.companieshouse.api.error.ApiErrorResponseException;
import uk.gov.companieshouse.api.handler.exception.URIValidationException;
import uk.gov.companieshouse.api.handler.ukestablishments.request.UkEstablishmentsList;
import uk.gov.companieshouse.api.model.ukestablishments.UkEstablishmentsApi;
import uk.gov.companieshouse.document.generator.company.report.exception.ServiceException;

@Service
public class UkEstablishmentService {

    @Autowired
    private CompanyReportApiClientService companyReportApiClientService;

    private static final UriTemplate GET_UK_ESTABLISHMENTS_URI =
        new UriTemplate("/company/{companyNumber}/uk-establishments");

    public UkEstablishmentsApi getUkEstablishments(String companyNumber) throws ServiceException {

        UkEstablishmentsApi ukEstablishmentsApi;

        ApiClient apiClient = companyReportApiClientService.getApiClient();

        String uri = GET_UK_ESTABLISHMENTS_URI.expand(companyNumber).toString();

        try {
            UkEstablishmentsList ukEstablishmentsList = apiClient.ukEstablishments().list(uri);
            ukEstablishmentsApi = ukEstablishmentsList.execute().getData();

        } catch (ApiErrorResponseException e) {

            throw new ServiceException("Error retrieving uk establishment", e);
        } catch (URIValidationException e) {

            throw new ServiceException("Invalid URI for uk establishment resource", e);
        }
        return ukEstablishmentsApi;
    }
}
