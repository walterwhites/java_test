package com.dummy.myerp.business.impl.manager;

import com.dummy.myerp.business.contrat.BusinessProxy;
import com.dummy.myerp.business.contrat.manager.ComptabiliteManager;
import com.dummy.myerp.business.impl.AbstractBusinessManager;
import com.dummy.myerp.business.impl.TransactionManager;
import com.dummy.myerp.consumer.dao.contrat.ComptabiliteDao;
import com.dummy.myerp.consumer.dao.contrat.DaoProxy;
import com.dummy.myerp.model.bean.comptabilite.*;
import com.dummy.myerp.technical.exception.FunctionalException;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ComptabiliteManagerImplTest {

    private ComptabiliteManagerImpl manager = new ComptabiliteManagerImpl();
    private static BusinessProxy businessProxyMock=mock(BusinessProxy.class);
    private static DaoProxy daoProxyMock=mock(DaoProxy.class);
    private static TransactionManager transactionManagerMock=mock(TransactionManager.class);
    private static ComptabiliteDao comptabiliteDaoMock=mock(ComptabiliteDao.class);
    private static ComptabiliteManager comptabiliteManagerMock=mock(ComptabiliteManager.class);

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        AbstractBusinessManager.configure(businessProxyMock, daoProxyMock, transactionManagerMock);
        when(daoProxyMock.getComptabiliteDao()).thenReturn(comptabiliteDaoMock);
    }


    /**
     * Test de la méthode addReference(EcritureComptable pEcritureComptable) dans le cas
     * d'une séquence d'écriture comptable qui existe déjà.
     * @throws Exception
     */
    @Test
    public void addReferenceAlreadyExistTest() throws Exception {
        EcritureComptable vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("test");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1,"test"),
                null, new BigDecimal(111),null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2,"test"),
                null, null,new BigDecimal(111)));

        String referenceExpected="AC-2019/00051";
        when(comptabiliteDaoMock.getLastSequenceEcritureComptable("AC", 2019)).thenReturn(new SequenceEcritureComptable(2019, 50));
        manager.addReference(vEcritureComptable);
        assertEquals("Mis à jour impossible car cette référence d'écriture comptable existe déjà", referenceExpected, vEcritureComptable.getReference());
    }

    @Test
    public void checkEcritureComptableUnit() throws Exception {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setReference("AC-2019/00001");
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(123)));
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitViolation() throws Exception {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG2() throws Exception {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
        null, new BigDecimal(123), null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
        null, null, new BigDecimal(1234)));
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }

    /**
     * Une écriture comptable doit contenir au moins deux lignes d'écriture : une au débit et une au crédit.
     * Cas passant
     */
    @Test(expected = Test.None.class)
    public void checkEcritureComptableUnitRG3() throws Exception {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setReference("AC-2019/00001");
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
        null, new BigDecimal(123), null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
        null, null, new BigDecimal(123)));
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }

    /**
     * Une écriture comptable doit contenir au moins deux lignes d'écriture : une au débit et une au crédit.
     * Cas non passant : 1 crédit et 0 débit
     */
    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG3With1Credit() throws Exception {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setReference("AC-2019/00001");
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, null, new BigDecimal(123)));
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }

    /**
     * Une écriture comptable doit contenir au moins deux lignes d'écriture : une au débit et une au crédit.
     * Cas non passant : 1 débit et 0 crédit
     */
    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG3With1Debit() throws Exception {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setReference("AC-2019/00001");
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123), null));
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }

    /**
    * Une écriture comptable doit contenir au moins deux lignes d'écriture : une au débit et une au crédit.
    * Cas non passant : 2 crédits et 0 débit
    */
    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG3With2Credits() throws Exception {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setReference("AC-2019/00001");
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, null, new BigDecimal(123)));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, null, new BigDecimal(123)));
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }

    /**
     * Une écriture comptable doit contenir au moins deux lignes d'écriture : une au débit et une au crédit.
     * Cas non passant : 2 débits et 0 crédit
     */
    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG3With2Debits() throws Exception {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setReference("AC-2019/00001");
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }
}
