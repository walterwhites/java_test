package com.dummy.myerp.testbusiness.business;


import com.dummy.myerp.business.contrat.manager.ComptabiliteManager;
import com.dummy.myerp.business.impl.manager.ComptabiliteManagerImpl;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;
import com.dummy.myerp.technical.exception.NotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class})
public class IntegrationTest extends BusinessTestCase {

        public static ComptabiliteManager comptabiliteManager = new ComptabiliteManagerImpl();

        /**
         * Méthode qui test la suppression de {@link EcritureComptable}
         */
        @DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
        @Test
        public void testDeleteEcritureComptable() throws NotFoundException {
                int nbLignesEcritureComptableInDatabase = 5;
                comptabiliteManager.deleteEcritureComptable(-1);
                List<EcritureComptable> vListEcritureComptableBDD = getBusinessProxy().getComptabiliteManager().getListEcritureComptable();
                assertEquals("la suppression n'a pas eu lieu car il y a toujours 5 lignes", nbLignesEcritureComptableInDatabase - 1, vListEcritureComptableBDD.size());
        }

        /**
         * Méthode qui test la suppression de {@link EcritureComptable}
         */
        @DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
        @Test(expected = NotFoundException.class)
        public void testDeleteEcritureComptableEcritureDoesntExist() throws NotFoundException {
                comptabiliteManager.deleteEcritureComptable(92374234);
        }

        /**
         * Méthode qui test la suppression de {@link EcritureComptable}
         */
        @DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
        @Test
        public void testInsertEcritureComptable() throws NotFoundException, FunctionalException {
                int nbLignesEcritureComptableInDatabase = 4;
                EcritureComptable vEcritureComptable;
                vEcritureComptable = new EcritureComptable();
                vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
                vEcritureComptable.setReference("AC-2019/99999");
                vEcritureComptable.setDate(new Date());
                vEcritureComptable.setLibelle("Libelle");
                vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(401),
                        null, new BigDecimal(987), null));
                vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(401),
                        null, null, new BigDecimal(987)));

                comptabiliteManager.insertEcritureComptable(vEcritureComptable);
                List<EcritureComptable> vListEcritureComptableBDD = comptabiliteManager.getListEcritureComptable();
                assertEquals("l'ajout n'a pas eu lieu car il y a toujours 4 lignes", nbLignesEcritureComptableInDatabase + 1, vListEcritureComptableBDD.size());
        }

        /**
         * Méthode qui test la suppression de {@link EcritureComptable}
         */
        @DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
        @Test(expected = FunctionalException.class)
        public void testInsertEcritureComptableNotValid1ligneEcriture() throws FunctionalException {
                EcritureComptable vEcritureComptable;
                vEcritureComptable = new EcritureComptable();
                vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
                vEcritureComptable.setReference("AC-2019/99999");
                vEcritureComptable.setDate(new Date());
                vEcritureComptable.setLibelle("Libelle");
                vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(401),
                        null, new BigDecimal(987), null));
                comptabiliteManager.insertEcritureComptable(vEcritureComptable);
        }

        /**
         * Méthode qui test la suppression de {@link EcritureComptable}
         */
        @DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
        @Test()
        public void testUpdateEcritureComptable() throws FunctionalException, NotFoundException {
                EcritureComptable vEcritureComptable = comptabiliteManager.getListEcritureComptable().get(3);
                vEcritureComptable.setLibelle("le libelle a changé");
                comptabiliteManager.updateEcritureComptable(vEcritureComptable);
        }

        /**
         * Méthode qui test la suppression de {@link EcritureComptable}
         */
        @DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
        @Test(expected = Exception.class)
        public void testUpdateEcritureComptableDoesntExist() throws FunctionalException, NotFoundException {
                EcritureComptable vEcritureComptable = comptabiliteManager.getListEcritureComptable().get(999999);
                comptabiliteManager.updateEcritureComptable(vEcritureComptable);
        }
}