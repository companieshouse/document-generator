//package uk.gov.companieshouse.document.generator.prosecution;
//
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.Mockito.when;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestInstance;
//import org.junit.jupiter.api.TestInstance.Lifecycle;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import uk.gov.companieshouse.document.generator.interfaces.exception.DocumentInfoException;
//import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfoRequest;
//import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfoResponse;
//import uk.gov.companieshouse.document.generator.prosecution.UltimatumDocumentInfoBuilderProvider.UltimatumDocumentInfoBuilder;
//import uk.gov.companieshouse.document.generator.prosecution.mapping.model.prosecutioncase.ProsecutionCase;
//import uk.gov.companieshouse.document.generator.prosecution.tmpclient.ProsecutionClient;
//import uk.gov.companieshouse.document.generator.prosecution.tmpclient.SdkException;
//
//@ExtendWith(MockitoExtension.class)
//@TestInstance(Lifecycle.PER_CLASS)
//class ProsecutionDocumentInfoServiceTest {
//    private static final String REQUEST_ID = "id1";
//    private static final String PROSECUTION_CASE_URI = "/company/006/prosecution-cases/dd908";
//    private static final String ULTIMATUM_RESOURCE_URI =
//                    "/prosecution/ultimatum" + PROSECUTION_CASE_URI;
//    private static final String SJP_RESOURCE_URI = "/prosecution/sjp" + PROSECUTION_CASE_URI;
//
//    @Mock
//    private UltimatumDocumentInfoBuilderProvider docInfoBuilderProvider;
//
//    @Mock
//    private UltimatumDocumentInfoBuilder docInfoBuilder;
//
//    @Mock
//    ProsecutionClient prosecutionClient;
//
//    @Mock
//    ProsecutionCase prosecutionCase;
//
//    @DisplayName("Given a request to produce an ultimatum for a given prosecution case, "
//                    + "when the UltimatumDocumentInfoService successfully produces a DocumentInfoResponse,"
//                    + "then that response is returned.")
//    @Test
//    void testGetDocumentInfoForUltimatum()
//                    throws DocumentInfoException, SdkException, DocumentInfoCreationException {
//        ProsecutionDocumentInfoService prosDocInfoService = new ProsecutionDocumentInfoService(
//                        docInfoBuilderProvider, prosecutionClient);
//        when(prosecutionClient.getProsecutionCase(PROSECUTION_CASE_URI, REQUEST_ID))
//                        .thenReturn(prosecutionCase);
//        when(docInfoBuilderProvider.builder()).thenReturn(docInfoBuilder);
//        DocumentInfoResponse responseToReturn = new DocumentInfoResponse();
//        when(docInfoBuilder.build()).thenReturn(responseToReturn);
//
//        DocumentInfoRequest docInfoReq = new DocumentInfoRequest();
//        docInfoReq.setResourceUri(ULTIMATUM_RESOURCE_URI);
//        docInfoReq.setRequestId(REQUEST_ID);
//        DocumentInfoResponse result = prosDocInfoService.getDocumentInfo(docInfoReq);
//        Assertions.assertSame(responseToReturn, result);
//    }
//
//    @DisplayName("Given a request to produce an ultimatum for a given prosecution case, "
//                    + "when the UltimatumDocumentInfoService successfully produces a DocumentInfoResponse,"
//                    + "then that response is returned.")
//    @Test
//    void testGetDocumentInfoForUltimatumNoProsecutionCaseFound()
//                    throws SdkException {
//        ProsecutionDocumentInfoService prosDocInfoService = new ProsecutionDocumentInfoService(
//                        docInfoBuilderProvider, prosecutionClient);
//        when(prosecutionClient.getProsecutionCase(PROSECUTION_CASE_URI, REQUEST_ID))
//                        .thenThrow(SdkException.class);
//
//        DocumentInfoRequest docInfoReq = new DocumentInfoRequest();
//        docInfoReq.setResourceUri(ULTIMATUM_RESOURCE_URI);
//        docInfoReq.setRequestId(REQUEST_ID);
//        assertThrows(DocumentInfoException.class,
//                        () -> prosDocInfoService.getDocumentInfo(docInfoReq));
//    }
//
//    @DisplayName("Given a request to produce an ultimatum for a given prosecution case, "
//                    + "when the UltimatumDocumentInfoService successfully produces a DocumentInfoResponse,"
//                    + "then that response is returned.")
//    @Test
//    void testGetDocumentInfoForUltimatumErrorBuildingDocInfo()
//                    throws SdkException, DocumentInfoCreationException {
//        ProsecutionDocumentInfoService prosDocInfoService = new ProsecutionDocumentInfoService(
//                        docInfoBuilderProvider, prosecutionClient);
//        when(prosecutionClient.getProsecutionCase(PROSECUTION_CASE_URI, REQUEST_ID))
//        .thenReturn(prosecutionCase);
//        when(docInfoBuilderProvider.builder()).thenReturn(docInfoBuilder);
//        when(docInfoBuilder.build())
//        .thenThrow(DocumentInfoCreationException.class);
//
//        DocumentInfoRequest docInfoReq = new DocumentInfoRequest();
//        docInfoReq.setResourceUri(ULTIMATUM_RESOURCE_URI);
//        docInfoReq.setRequestId(REQUEST_ID);
//        assertThrows(DocumentInfoException.class,
//                        () -> prosDocInfoService.getDocumentInfo(docInfoReq));
//    }
//}
