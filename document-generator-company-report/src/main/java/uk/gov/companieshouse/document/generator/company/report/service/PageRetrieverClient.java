package uk.gov.companieshouse.document.generator.company.report.service;

import uk.gov.companieshouse.api.ApiClient;
import uk.gov.companieshouse.api.error.ApiErrorResponseException;
import uk.gov.companieshouse.api.handler.exception.URIValidationException;

public interface PageRetrieverClient<T> {
    int getSize(T api);
    long getTotalCount(T api);
    void addPage(T api, T moreRsults);
    T retrievePage(final String uri,
                   final ApiClient apiClient,
                   final int startIndex,
                   final int itemsPerPage) throws ApiErrorResponseException, URIValidationException;
}
