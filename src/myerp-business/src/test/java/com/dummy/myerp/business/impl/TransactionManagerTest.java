package com.dummy.myerp.business.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.SimpleTransactionStatus;

import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class TransactionManagerTest {

    @Spy
    private static TransactionManager transactionManager = new TransactionManager();

    @Spy
    private static TransactionStatus transactionStatus = new SimpleTransactionStatus();

    @Test(expected = NullPointerException.class)
    public void commitMyERPWithoutBeginTransaction() throws NullPointerException {
        doReturn(null).when(transactionManager).beginTransactionMyERP();
        TransactionStatus transactionStatus = transactionManager.beginTransactionMyERP();
        transactionManager.commitMyERP(transactionStatus);
    }

    @Test(expected = NullPointerException.class)
    public void rollbackMyERPNULL() {
        doReturn(null).when(transactionManager).beginTransactionMyERP();
        TransactionStatus transactionStatus = transactionManager.beginTransactionMyERP();
        transactionManager.rollbackMyERP(null);
    }
}
