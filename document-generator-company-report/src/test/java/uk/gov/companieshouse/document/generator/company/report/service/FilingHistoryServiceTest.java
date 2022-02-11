package uk.gov.companieshouse.document.generator.company.report.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.ApiClient;
import uk.gov.companieshouse.api.model.filinghistory.FilingApi;
import uk.gov.companieshouse.api.model.filinghistory.FilingHistoryApi;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FilingHistoryServiceTest {

    @InjectMocks
    private FilingHistoryService filingHistoryService;

    @Mock
    private ApiClient mockApiClient;

    @Mock
    private CompanyReportApiClientService mockCompanyReportApiClientService;

    @Mock
    private PageRetrieverService<FilingHistoryApi> pageRetrieverService;

    private static final String COMPANY_NUMBER = "00000000";
    private static final String FILING_HISTORY_URI = "/company/00000000/filing-history";

    @BeforeEach
    void init() {
        when(mockCompanyReportApiClientService.getApiClient()).thenReturn(mockApiClient);
    }

    @Test
    @DisplayName("Test get filing history api response is not null")
    void testGetFilingHistorySuccessful() throws Exception {

        when(pageRetrieverService.retrieveAllPages(eq(filingHistoryService),
                eq(FILING_HISTORY_URI), eq(mockApiClient), anyInt()))
                .thenReturn(createFilingHistoryApi());

        FilingHistoryApi filingHistoryApi = filingHistoryService.getFilingHistory(COMPANY_NUMBER);

        assertNotNull(filingHistoryApi);
        assertEquals(1, filingHistoryApi.getItems().size());
    }

    private FilingHistoryApi createFilingHistoryApi() {

        FilingHistoryApi filingHistoryApi = new FilingHistoryApi();
        filingHistoryApi.setEtag("etag");
        filingHistoryApi.setFilingHistoryStatus("status");
        filingHistoryApi.setKind("kind");
        filingHistoryApi.setItemsPerPage(1L);
        filingHistoryApi.setStartIndex(0L);
        filingHistoryApi.setTotalCount(2L);

        List<FilingApi> items = new ArrayList<>();
        FilingApi filingApi = new FilingApi();

        items.add(filingApi);

        filingHistoryApi.setItems(items);

        return filingHistoryApi;
    }
}
