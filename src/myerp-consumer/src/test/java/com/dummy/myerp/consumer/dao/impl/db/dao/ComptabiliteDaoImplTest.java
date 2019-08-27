package com.dummy.myerp.consumer.dao.impl.db.dao;

import com.dummy.myerp.consumer.dao.contrat.ComptabiliteDao;
import com.dummy.myerp.consumer.dao.contrat.DaoProxy;

/**
 * Implémentation de l'interface {@link ComptabiliteDao}
 */
public class ComptabiliteDaoImplTest {

    // ==================== Constructeurs ====================
    /** Instance unique de la classe (design pattern Singleton) */
    private static final ComptabiliteDaoImplTest INSTANCE = new ComptabiliteDaoImplTest();

    /**
     * Renvoie l'instance unique de la classe (design pattern Singleton).
     *
     * @return {@link ComptabiliteDaoImplTest}
     */
    public static ComptabiliteDaoImplTest getInstance() {
        return ComptabiliteDaoImplTest.INSTANCE;
    }

    private static ComptabiliteDao comptabiliteDaoMock = org.mockito.Mockito.mock(ComptabiliteDao.class);

    private static DaoProxy daoProxyMock = org.mockito.Mockito.mock(DaoProxy.class);

    /**
     * Constructeur.
     */
    protected ComptabiliteDaoImplTest() {
        super();
    }
}
