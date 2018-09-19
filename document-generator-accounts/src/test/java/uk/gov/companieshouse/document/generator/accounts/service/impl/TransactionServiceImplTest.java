package uk.gov.companieshouse.document.generator.accounts.service.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import uk.gov.companieshouse.document.generator.accounts.data.transaction.TransactionManager;
import uk.gov.companieshouse.document.generator.accounts.exception.ServiceException;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class TransactionServiceImplTest {

    @InjectMocks
    private TransactionServiceImpl transactionServiceImpl;

    @Mock
    private TransactionManager transactionManager;

    @Test
    @DisplayName("Tests unsuccessful retrieval of transaction that throws exception")
    void testGetTransactionThrownException() throws Exception {
        when(transactionManager.getTransaction(anyString())).thenThrow(new Exception());

        assertThrows(ServiceException.class, () -> transactionServiceImpl.getTransaction("10000"));
    }

    @Test
    @DisplayName("Tests successful retrieval of a transaction")
    void testGetTransactionSuccess() throws Exception {
        when(transactionManager.getTransaction(anyString())).thenReturn(new Transaction());

        assertNotNull(transactionServiceImpl.getTransaction("10000"));
    }

}
