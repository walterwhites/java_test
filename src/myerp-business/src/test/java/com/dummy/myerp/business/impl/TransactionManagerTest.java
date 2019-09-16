package com.dummy.myerp.business.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.transaction.TransactionStatus;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class TransactionManagerTest {

    @Spy
    private static TransactionManager transactionManager;

    @Spy
    private static TransactionStatus transactionStatus;
    /*private static Transaction transaction;

    @Before
    public void setUp() throws Exception {
        server = new TransactionServerImpl();
        transactionManager = mock(TransactionManager.class);
        transaction = mock(javax.transaction.Transaction.class);
        Mockito.stub(tm.getTransaction()).toReturn(txn);
        Mockito.stub(tm.suspend()).toReturn(txn);
        xaImporter = Mockito.mock(XAImporter.class);
        Mockito.stub(xaImporter.importTransaction(Mockito.any(), Mockito.any(), Mockito.eq(TIMEOUT))).toReturn(txn);
        server.setXaImporter(xaImporter);
        server.setTransactionManager(tm);
    }
*/

    @Test(expected = NullPointerException.class)
    public void commitMyERPWithoutBeginTransaction() throws NullPointerException {
        doReturn(null).when(transactionManager).beginTransactionMyERP();
        TransactionStatus transactionStatus = transactionManager.beginTransactionMyERP();
        transactionManager.commitMyERP(transactionStatus);
    }

    @Test(expected = NullPointerException.class)
    public void rollbackMyERPNULL() throws NullPointerException {
        doReturn(null).when(transactionManager).beginTransactionMyERP();
        TransactionStatus transactionStatus = transactionManager.beginTransactionMyERP();
        transactionManager.rollbackMyERP(null);
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
