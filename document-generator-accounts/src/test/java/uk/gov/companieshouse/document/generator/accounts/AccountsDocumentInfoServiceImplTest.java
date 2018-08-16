package uk.gov.companieshouse.document.generator.accounts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.model.transaction.Transaction;
import uk.gov.companieshouse.document.generator.accounts.service.TransactionService;
import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfo;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class AccountsDocumentInfoServiceImplTest {

    @InjectMocks
    private AccountsDocumentInfoServiceImpl accountsDocumentInfoService;

    @Mock
    private TransactionService transactionService;

    @Test
    @DisplayName("Tests the successful retrieval of an accounts document data")
    void testSuccessfulGetDocumentInfo() {
        when(transactionService.getTransaction(anyString())).thenReturn(new Transaction());
        assertEquals(DocumentInfo.class, accountsDocumentInfoService.getDocumentInfo().getClass());
    }

    @Test
    @DisplayName("Tests the unsuccessful retrieval of an accounts document data due to error in transaction retrieval")
    void testUnsuccessfulGetDocumentInfo() {
        when(transactionService.getTransaction(anyString())).thenReturn(null);
        assertNull(accountsDocumentInfoService.getDocumentInfo());
    }

}
