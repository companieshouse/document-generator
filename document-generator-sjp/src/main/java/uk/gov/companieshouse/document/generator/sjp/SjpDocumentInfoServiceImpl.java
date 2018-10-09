package uk.gov.companieshouse.document.generator.sjp;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

//import uk.gov.companieshouse.document.generator.accounts.exception.HandlerException;
//import uk.gov.companieshouse.document.generator.accounts.exception.ServiceException;
//import uk.gov.companieshouse.document.generator.accounts.handler.accounts.AccountsHandler;
//import uk.gov.companieshouse.document.generator.accounts.service.TransactionService;
import uk.gov.companieshouse.document.generator.interfaces.DocumentInfoService;
import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfoRequest;
import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfoResponse;
import uk.gov.companieshouse.environment.EnvironmentReader;
import uk.gov.companieshouse.environment.impl.EnvironmentReaderImpl;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;


@Service
public class SjpDocumentInfoServiceImpl implements DocumentInfoService {

	private static final String PROSECTION_SERVICE = "http://chs-dev:41001";
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final EnvironmentReader READER = new EnvironmentReaderImpl();
	
//    @Autowired
//    private TransactionService transactionService;
//
//    @Autowired
//    private AccountsHandler accountsHandler;

    public static final String MODULE_NAME_SPACE = "document-generator-sjp";

    private static final Logger LOG = LoggerFactory.getLogger(MODULE_NAME_SPACE);

    @Override
    public DocumentInfoResponse getDocumentInfo(DocumentInfoRequest documentInfoRequest) {
        LOG.info("Started getting document");

        String resourceId = documentInfoRequest.getResourceId();
        String resourceUri = documentInfoRequest.getResourceUri();

//        Transaction transaction;
//        try {
//            transaction = transactionService.getTransaction(resourceId);
//        } catch (ServiceException e) {
//            LOG.error(e);
//            return null;
//        }

//        String resourceLink =  Optional.of(transaction)
//                .map(Transaction::getResources)
//                .map(resources -> resources.get(resourceId))
//                .map(Resource::getLinks)
//                .map(links -> links.get(LinkType.RESOURCE.getLink()))
//                .orElseGet(() -> {
//                    LOG.info("Unable to find resource: " + resourceId + " in transaction: " + resourceUri);
//                    return "";
//                });

//        JSONObject abridgedAccountJSON = new JSONObject(abridgedAccountData);
//        JSONObject abridgedAccount = new JSONObject();
//        abridgedAccount.put("abridged_account", abridgedAccountJSON);
//        abridgedAccount.put("company_name", "company house");
//        abridgedAccount.put("company_number", "1234");
//        //return abridgedAccount.toString();

		HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set(AUTHORIZATION_HEADER, READER.getMandatoryString("CHS_API_KEY"));
		
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
	    RestTemplate restTemplate = new RestTemplate(requestFactory);

        HttpEntity<ProsecutionCase> requestEntity = new HttpEntity<>(requestHeaders);

        ResponseEntity<ProsecutionCase> prosecutionCaseResponse = restTemplate.exchange(PROSECTION_SERVICE + resourceUri, HttpMethod.GET, requestEntity, ProsecutionCase.class);

        ProsecutionCase prosCase = prosecutionCaseResponse.getBody();
        
        System.out.println("Prosecution Case Company Name: " + prosCase.getCompanyName());

        DocumentInfoResponse documentInfoResponse = new DocumentInfoResponse();
        documentInfoResponse.setData(getSjpData().toString());
        documentInfoResponse.setAssetId("accounts");
        documentInfoResponse.setTemplateName("sjp.html");
        documentInfoResponse.setPath(String.format("/%s/%s", "sjp", "SJP-1234.pdf"));
        return documentInfoResponse;

        // when the Accounts migration has been completed to Company Accounts, this code can be removed
//        if (isAccounts(resourceLink)) {
//            try {
//                return accountsHandler.getAbridgedAccountsData(transaction, resourceLink);
//            } catch (HandlerException e) {
//                LOG.error(e);
//            }
//        }
//
//        return null;
    }

	private ProsecutionCase callProsecutionService() {
		
		HttpHeaders requestHeaders = new HttpHeaders();
		
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
	    RestTemplate restTemplate = new RestTemplate(requestFactory);

        HttpEntity requestEntity = new HttpEntity(requestHeaders);

        ResponseEntity<ProsecutionCase> prosecutionCaseResponse = restTemplate.exchange(PROSECTION_SERVICE, HttpMethod.GET, requestEntity, ProsecutionCase.class);

        return prosecutionCaseResponse.getBody();
	}

    /**
     * Determines if is an accounts specific link as "/transactions/{transactionId}/accounts/{accountsId}"
     * only exists within the accounts (abridged) implementation
     * @param resourceLink - resource link
     * @return true if accounts, false if not
     */
    private boolean isAccounts(String resourceLink) {
        return resourceLink.matches("/transactions\\/[0-9-]+/accsjounts\\/.*");
    }
    
    private JSONObject getSjpData() {
		JSONObject pdfData = new JSONObject();
		
		pdfData.put("DefendantName", "Mr D Fraud");
		pdfData.put("CompanyName", "companies house");
		pdfData.put("CompanyNumber", "1234");
		pdfData.put("CompanyAddress", "");
		pdfData.put("URN", "URN123");
		pdfData.put("DefendantDOB", "");
		pdfData.put("template_registry_addr", "chs-dev:5002");

		return pdfData;
    }
}