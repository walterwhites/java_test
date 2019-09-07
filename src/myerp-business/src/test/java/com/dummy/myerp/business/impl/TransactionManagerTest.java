package com.dummy.myerp.business.impl;

import org.junit.Test;
import org.mockito.Spy;
import org.springframework.transaction.TransactionStatus;

public class TransactionManagerTest {

    @Spy
    private static TransactionManager transactionManager = new TransactionManager();

    @Test(expected = Test.None.class)
    public void commitMyERPWithBeginTransaction() {
        TransactionStatus transactionStatus = transactionManager.beginTransactionMyERP();
        transactionManager.commitMyERP(transactionStatus);
    }

    @Test(expected = NullPointerException.class)
    public void commitMyERPWithoutBeginTransaction() throws NullPointerException {
        //when(transactionManagerSpy.beginTransactionMyERP()).thenReturn(null);
        TransactionStatus transactionStatus = transactionManager.beginTransactionMyERP();
        transactionManager.commitMyERP(transactionStatus);
    }

/*
    @Test(expected = Test.None.class)
    public void rollbackMyERPNULL() {
        when(transactionManagerMock.beginTransactionMyERP()).thenReturn(null);
        transactionManagerMock.beginTransactionMyERP();
        transactionManagerMock.rollbackMyERP(null);
    }

    @Test(expected = Test.None.class)
    public void commitMyERP() {
        transactionManagerMock.commitMyERP(transactionManagerSpy.beginTransactionMyERP());
        TransactionStatus transactionStatus = transactionManagerMock.beginTransactionMyERP();
        transactionManagerMock.commitMyERP(transactionStatus);
    }

    @Test(expected = Test.None.class)
    public void rollbackMyERP() {
        transactionManagerMock.rollbackMyERP(transactionManagerSpy.beginTransactionMyERP());
        TransactionStatus transactionStatus = transactionManagerMock.beginTransactionMyERP();
        transactionManagerMock.rollbackMyERP(transactionStatus);
    }*/
}
