package uk.gov.companieshouse.document.generator.company.report.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriTemplate;
import uk.gov.companieshouse.api.ApiClient;
import uk.gov.companieshouse.api.error.ApiErrorResponseException;
import uk.gov.companieshouse.api.handler.exception.URIValidationException;
import uk.gov.companieshouse.api.handler.registers.request.RegistersList;
import uk.gov.companieshouse.api.model.registers.CompanyRegistersApi;
import uk.gov.companieshouse.document.generator.company.report.exception.ServiceException;

@Service
public class RegistersService {

    @Autowired
    CompanyReportApiClientService companyReportApiClientService;

    private static final UriTemplate GET_REGISTERS_URI =
        new UriTemplate("/company/{companyNumber}/registers");

    public CompanyRegistersApi getCompanyRegisters(String companyNumber) throws ServiceException {

        CompanyRegistersApi companyRegistersApi;

        ApiClient apiClient = companyReportApiClientService.getApiClient();

        String uri = GET_REGISTERS_URI.expand(companyNumber).toString();

        try {

            RegistersList registersList = apiClient.registers().list(uri);

            companyRegistersApi = registersList.execute().getData();
        } catch (ApiErrorResponseException e) {

            throw new ServiceException("Error retrieving company registers", e);
        } catch (URIValidationException e) {

            throw new ServiceException("Invalid URI for company registers resource", e);
        }
        return companyRegistersApi;
    }
}
