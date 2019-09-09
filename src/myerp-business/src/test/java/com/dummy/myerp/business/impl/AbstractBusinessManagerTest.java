package com.dummy.myerp.business.impl;

import com.dummy.myerp.business.contrat.BusinessProxy;
import com.dummy.myerp.consumer.dao.contrat.ComptabiliteDao;
import com.dummy.myerp.consumer.dao.contrat.DaoProxy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
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

    @Test(expected = NullPointerException.class)
    public void getBusinessProxyWithNullBusinessProxy() {
        abstractBusinessManager.configure(null, daoProxy, transactionManager);
        abstractBusinessManager.getBusinessProxy();
    }

    @Test(expected = Test.None.class)
    public void getBusinessProxyWithBusinessProxy() {
        abstractBusinessManager.configure(businessProxy, daoProxy, transactionManager);
        abstractBusinessManager.getBusinessProxy();
    }
}