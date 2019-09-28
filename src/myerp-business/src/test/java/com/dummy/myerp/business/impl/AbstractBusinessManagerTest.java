package com.dummy.myerp.business.impl;

import com.dummy.myerp.business.contrat.BusinessProxy;
import com.dummy.myerp.consumer.dao.contrat.ComptabiliteDao;
import com.dummy.myerp.consumer.dao.contrat.DaoProxy;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AbstractBusinessManagerTest {
    @Spy
    private static DaoProxy daoProxy = new DaoProxy() {
        @Override
        public ComptabiliteDao getComptabiliteDao() {
            return null;
        }
    };

    @Spy
    private static TransactionManager transactionManager = new TransactionManager();

    @Spy
    private static BusinessProxy businessProxy = new BusinessProxyImpl();

    @Spy
    private static AbstractBusinessManager abstractBusinessManager = new AbstractBusinessManager() {
        @Override
        protected BusinessProxy getBusinessProxy() {
            return super.getBusinessProxy();
        }
    };

    @Test()
    public void getBusinessProxyWithNullBusinessProxy() {
        assertThrows(NullPointerException.class, () -> {
            abstractBusinessManager.configure(null, daoProxy, transactionManager);
            abstractBusinessManager.getBusinessProxy();
        });
    }

    @Test()
    public void getBusinessProxyWithBusinessProxy() {
        assertDoesNotThrow(() -> {
            abstractBusinessManager.configure(businessProxy, daoProxy, transactionManager);
            abstractBusinessManager.getBusinessProxy();
        });
    }
}