package com.dummy.myerp.business.impl;

import com.dummy.myerp.consumer.dao.contrat.DaoProxy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class BusinessProxyImplTest {

    private static DaoProxy daoProxyMock = mock(DaoProxy.class);
    private static TransactionManager transactionManagerMock = mock(TransactionManager.class);

    /**
     * Test de la méthode getInstance()
     * Cas non passant avec un daoProxy null.
     * retourne UnsatisfiedLinkError.
     */
    @Test()
    public void getInstanceDaoNull() {
        assertThrows(UnsatisfiedLinkError.class, () -> {
            BusinessProxyImpl.getInstance();
        });
    }

    /**
     * Test de la méthode getInstance(DaoProxy pDaoProxy, TransactionManager pTransactionManager)
     * Cas non passant avec un daoProxy null.
     * retourne UnsatisfiedLinkError.
     */
    @Test()
    public void getInstanceWithArgsDaoNull() {
        assertThrows(UnsatisfiedLinkError.class, () -> {
            BusinessProxyImpl.getInstance(null, transactionManagerMock);
        });
    }
}
