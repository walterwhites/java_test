package com.dummy.myerp.business.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.TransactionStatus;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class TransactionManagerTest {

    @Spy
    private static TransactionManager transactionManager;

    @Spy
    private static TransactionStatus transactionStatus;

    @Test()
    public void commitMyERPWithoutBeginTransaction() throws NullPointerException {
        assertThrows(NullPointerException.class, () -> {
            doReturn(null).when(transactionManager).beginTransactionMyERP();
            TransactionStatus transactionStatus = transactionManager.beginTransactionMyERP();
            transactionManager.commitMyERP(transactionStatus);
        });
    }

    @Test()
    public void rollbackMyERPNULL() throws NullPointerException {
        assertThrows(NullPointerException.class, () -> {
            doReturn(null).when(transactionManager).beginTransactionMyERP();
            TransactionStatus transactionStatus = transactionManager.beginTransactionMyERP();
            transactionManager.rollbackMyERP(null);
        });
    }


/*
    @Test(expected = Test.None.class)
    public void commitMyERPWithBeginTransaction() throws NullPointerException {
        transactionStatus = transactionManager.beginTransactionMyERP();
        transactionManager.commitMyERP(transactionStatus);
    }

    @Test(expected = Test.None.class)
    public void rollbackMyERP() {
        transactionStatus = transactionManager.beginTransactionMyERP();
        transactionManager.rollbackMyERP(transactionStatus);
    } */
}
