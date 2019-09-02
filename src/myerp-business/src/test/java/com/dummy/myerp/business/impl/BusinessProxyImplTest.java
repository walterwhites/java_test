package com.dummy.myerp.business.impl;

import com.dummy.myerp.consumer.dao.contrat.DaoProxy;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.mockito.Mockito.mock;

public class BusinessProxyImplTest {

    private static DaoProxy daoProxyMock = mock(DaoProxy.class);
    private static TransactionManager transactionManagerMock = mock(TransactionManager.class);
    @Rule
    public ExpectedException thrown= ExpectedException.none();

    /**
     * Test de la méthode getInstance()
     * Cas non passant avec un daoProxy null.
     * retourne UnsatisfiedLinkError.
     */
    @Test(expected = UnsatisfiedLinkError.class)
    public void getInstanceDaoNull() {
        BusinessProxyImpl.getInstance();
    }

    /**
     * Test de la méthode getInstance(DaoProxy pDaoProxy, TransactionManager pTransactionManager)
     * Cas non passant avec un daoProxy null.
     * retourne UnsatisfiedLinkError.
     */
    @Test(expected = UnsatisfiedLinkError.class)
    public void getInstanceWithArgsDaoNull() {
        BusinessProxyImpl.getInstance(null, transactionManagerMock);
    }
}
