package uk.gov.companieshouse.document.generator.company.report.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.util.UriTemplate;
import uk.gov.companieshouse.api.ApiClient;
import uk.gov.companieshouse.api.error.ApiErrorResponseException;
import uk.gov.companieshouse.api.handler.exception.URIValidationException;
import uk.gov.companieshouse.api.handler.officers.request.OfficersList;
import uk.gov.companieshouse.api.model.officers.OfficersApi;
import uk.gov.companieshouse.document.generator.company.report.exception.ServiceException;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;

@Service
public class OfficerService {

    private CompanyReportApiClientService companyReportApiClientService;
    
    private static final String APPLICATION_NAME_SPACE = "document-generator-api";
    
    private static final Logger LOGGER = LoggerFactory.getLogger(APPLICATION_NAME_SPACE);

    @Autowired
    public OfficerService(CompanyReportApiClientService companyReportApiClientService) {
        this.companyReportApiClientService = companyReportApiClientService;
    }

    private static final UriTemplate GET_OFFICERS_URI =
        new UriTemplate("/company/{companyNumber}/officers");

    public OfficersApi getOfficers(String companyNumber) throws ServiceException {

        OfficersApi officersApi = null;

        ApiClient apiClient = companyReportApiClientService.getApiClient();
        
        Integer startIndex = 0;
        Integer itemsPerPage = 100;

        officersApi = retrieveOfficerAppointments(companyNumber, officersApi, apiClient, startIndex, itemsPerPage);
        
		while (officersApi.getItems().size() < officersApi.getTotalResults()) {
			try {
				startIndex += itemsPerPage;
				OfficersApi moreResults = retrieveOfficerAppointments(companyNumber, officersApi, apiClient, startIndex, itemsPerPage);
				officersApi.getItems().addAll(moreResults.getItems());
			} catch (ServiceException se) {
				if (!CollectionUtils.isEmpty(officersApi.getItems())) {
					LOGGER.error("Possible data discrepancy while retrieving all appointments for " + companyNumber);
					return officersApi;
				} else {
					throw se;
				}
			}
		}
        
        return officersApi;
    }

    private OfficersApi retrieveOfficerAppointments(String companyNumber, OfficersApi officersApi, ApiClient apiClient, Integer startIndex, Integer itemsPerPage)
            throws ServiceException {
        String uri = GET_OFFICERS_URI.expand(companyNumber).toString();

        try {
            OfficersList officersList = apiClient.officers().list(uri);
            officersList.addQueryParams("items_per_page", itemsPerPage.toString());
            officersList.addQueryParams("start_index", startIndex.toString());

            officersApi = officersList.execute().getData();
        } catch (ApiErrorResponseException e) {

            throw new ServiceException("Error retrieving officers", e);
        } catch (URIValidationException e) {

            throw new ServiceException("Invalid URI for officers resource", e);
        }
        return officersApi;
    }
}
