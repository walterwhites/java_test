package com.dummy.myerp.testbusiness.business;


import com.dummy.myerp.business.impl.manager.ComptabiliteManagerImpl;
import com.dummy.myerp.consumer.dao.contrat.ComptabiliteDao;
import com.dummy.myerp.consumer.dao.contrat.DaoProxy;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import org.junit.After;
import org.junit.Test;
import org.mockito.Spy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.TransactionStatus;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class IntegrationTest extends BusinessTestCase {

    @Spy
    EcritureComptable ecritureComptable = new EcritureComptable();

    @Spy
    ComptabiliteManagerImpl comptabiliteManager = new ComptabiliteManagerImpl();

    @Spy
    DaoProxy daoProxy = new DaoProxy() {
        @Override
        public ComptabiliteDao getComptabiliteDao() {
            return null;
        }
    };

    @After
    public void tearDown() throws Exception {
        TransactionStatus transactionStatus = getTransactionManager().beginTransactionMyERP();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(getDbSource());
    }

    /**
     * Méthode qui test la suppression de {@link EcritureComptable}
     */
    @Test
    public void testDeleteEcritureComptable() {
        int nbLignesEcritureComptableInDatabase = 5;
        getBusinessProxy().getComptabiliteManager().deleteEcritureComptable(-1);
        List<EcritureComptable> vListEcritureComptableBDD = getBusinessProxy().getComptabiliteManager().getListEcritureComptable();
        assertEquals("la suppression n'a pas eu lieu car il y a toujours 5 lignes", nbLignesEcritureComptableInDatabase - 1, vListEcritureComptableBDD.size());
    }
}
/*
    @Test
    public void testInsertEcritureComptable() throws FunctionalException {
        TransactionStatus vTS = getTransactionManager().beginTransactionMyERP();

        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setId(9999);
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setReference("AC-2019/99999");
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(987), null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null, new BigDecimal(987)));


        //JdbcTemplate vJdbcTemplate = new JdbcTemplate(getDataSourceTest());

        Mockito.doReturn(null).when(comptabiliteManager).checkEcritureComptable(ecritureComptable);
        daoProxy.getComptabiliteDao().insertEcritureComptable(ecritureComptable);
    }
}

    TransactionStatus vTS = getTransactionManager().beginTransactionMyERP();
        try {
                getDaoProxy().getComptabiliteDao().insertEcritureComptable(pEcritureComptable);
                getTransactionManager().commitMyERP(vTS);
                vTS = null;
                } finally {
                getTransactionManager().rollbackMyERP(vTS);
                }
         */