package com.dummy.myerp.consumer.dao.impl;

import com.dummy.myerp.consumer.dao.contrat.ComptabiliteDao;
import com.dummy.myerp.consumer.dao.contrat.DaoProxy;


/**
 * <p>Implémentation du Proxy d'accès à la couche DAO.</p>
 */
public final class DaoProxyImplTest implements DaoProxy {

    // ==================== Attributs ====================
    /** {@link ComptabiliteDao} */
    private ComptabiliteDao comptabiliteDao;


    // ==================== Constructeurs ====================
    /** Instance unique de la classe (design pattern Singleton) */
    private static final DaoProxyImplTest INSTANCE = new DaoProxyImplTest();

    /**
     * Renvoie l'instance unique de la classe (design pattern Singleton).
     *
     * @return {@link DaoProxyImplTest}
     */
    protected static DaoProxyImplTest getInstance() {
        return DaoProxyImplTest.INSTANCE;
    }

    /**
     * Constructeur.
     */
    private DaoProxyImplTest() {
        super();
    }


    // ==================== Getters/Setters ====================
    public ComptabiliteDao getComptabiliteDao() {
        return this.comptabiliteDao;
    }
    public void setComptabiliteDao(ComptabiliteDao pComptabiliteDao) {
        this.comptabiliteDao = pComptabiliteDao;
    }
}
