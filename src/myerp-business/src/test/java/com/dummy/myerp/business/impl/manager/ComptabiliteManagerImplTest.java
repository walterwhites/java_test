package com.dummy.myerp.business.impl.manager;

import com.dummy.myerp.business.contrat.BusinessProxy;
import com.dummy.myerp.business.contrat.manager.ComptabiliteManager;
import com.dummy.myerp.business.impl.AbstractBusinessManager;
import com.dummy.myerp.business.impl.TransactionManager;
import com.dummy.myerp.consumer.dao.contrat.ComptabiliteDao;
import com.dummy.myerp.consumer.dao.contrat.DaoProxy;
import com.dummy.myerp.model.bean.comptabilite.*;
import com.dummy.myerp.technical.exception.FunctionalException;
import com.dummy.myerp.technical.exception.NotFoundException;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ComptabiliteManagerImplTest {

    private ComptabiliteManagerImpl manager = new ComptabiliteManagerImpl();
    private static BusinessProxy businessProxyMock=mock(BusinessProxy.class);
    private static DaoProxy daoProxyMock=mock(DaoProxy.class);
    private static TransactionManager transactionManagerMock=mock(TransactionManager.class);
    private static ComptabiliteDao comptabiliteDaoMock=mock(ComptabiliteDao.class);
    private static ComptabiliteManager comptabiliteManagerMock=mock(ComptabiliteManager.class);
    @Rule
    public ExpectedException thrown= ExpectedException.none();

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
    public void addReferenceAlreadyExistRG6() throws Exception {
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

    /**
     * Cas passant checkEcritureComptableUnit et checkEcritureComptableContext passent avec une écriture qui n'existe pas
     */
    @Test
    public void checkEcritureComptable() throws Exception {
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
        when(comptabiliteDaoMock.getEcritureComptableByRef("AC-2019/99999")).thenThrow(new NotFoundException("Ecriture Comptable qui n'existe pas"));
        manager.checkEcritureComptable(vEcritureComptable);
    }

    /**
     * Toutes les règles de gestion passsent
     */
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
                null, null, new BigDecimal(123)));
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitViolation() throws Exception {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }

    /**
     * Pour qu'une écriture comptable soit valide, elle doit être équilibrée : la somme des montants au crédit
     * des lignes d'écriture doit être égale à la somme des montants au débit.
     * Cas passant positif
     */
    @Test(expected = Test.None.class)
    public void checkEcritureComptableUnitRG2() throws Exception {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("OD", "Opérations Diverses"));
        vEcritureComptable.setReference("OD-2019/00001");
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(5555), null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null, new BigDecimal(5555)));
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }

    /**
     * Pour qu'une écriture comptable soit valide, elle doit être équilibrée : la somme des montants au crédit
     * des lignes d'écriture doit être égale à la somme des montants au débit.
     * Cas passant negatif
     */
    @Test(expected = Test.None.class)
    public void checkEcritureComptableUnitRG2WithNegative() throws Exception {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("VE", "Vente"));
        vEcritureComptable.setReference("VE-2019/00001");
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(-66), null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null, new BigDecimal(-66)));
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }

    /**
     * Pour qu'une écriture comptable soit valide, elle doit être équilibrée : la somme des montants au crédit
     * des lignes d'écriture doit être égale à la somme des montants au débit.
     * Cas non passant crédit et débit non égaux
     */
    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG2NotEqual() throws Exception {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setReference("AC-2019/00001");
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
        null, new BigDecimal(123), null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
        null, null, new BigDecimal(1234)));
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }

    /**
     * Pour qu'une écriture comptable soit valide, elle doit être équilibrée : la somme des montants au crédit
     * des lignes d'écriture doit être égale à la somme des montants au débit.
     * Cas non passant crédit et débit inverse
     */
    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG2Inverse() throws Exception {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("BQ", "Banque"));
        vEcritureComptable.setReference("BQ-2019/00001");
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(-2222), null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null, new BigDecimal(2222)));
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

    /**
     *  Vérifier que l'année dans la référence correspond bien à la date de l'écriture, idem pour le code journal...
     *  Cas non passant avec une date dans le passée
     */
    @Test()
    public void checkEcritureComptableUnitRG5BadAnnee() throws Exception {
        thrown.expect(FunctionalException.class);
        thrown.expectMessage("Il y a une erreur dans la référence concernant l'année.");
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setReference("AC-2018/00001");
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123), null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, null, new BigDecimal(123)));
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }

    /**
     *  Vérifier que l'année dans la référence correspond bien à la date de l'écriture, idem pour le code journal...
     *  Cas non passant avec un mauvais code journal
     */
    @Test()
    public void checkEcritureComptableUnitRG5BadCodeJournal() throws Exception {
        thrown.expect(FunctionalException.class);
        thrown.expectMessage("Il y a une erreur dans la référence concernant le code journal.");
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setReference("XX-2018/00001");
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123), null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, null, new BigDecimal(123)));
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }
}
