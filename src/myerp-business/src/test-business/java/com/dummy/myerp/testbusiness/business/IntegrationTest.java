package com.dummy.myerp.testbusiness.business;


import com.dummy.myerp.business.contrat.manager.ComptabiliteManager;
import com.dummy.myerp.business.impl.manager.ComptabiliteManagerImpl;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;
import com.dummy.myerp.technical.exception.NotFoundException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.util.AssertionErrors.assertEquals;


@ContextConfiguration
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class IntegrationTest extends BusinessTestCase {

        public static ComptabiliteManager comptabiliteManager = new ComptabiliteManagerImpl();
        private final Log logger = LogFactory.getLog(getClass());

        // appeler après chaque méthode de test
        /*
        @After
        public void reset() throws Exception {


                TransactionStatus vTS = getTransactionManager().beginTransactionMyERP();
                try {
                        comptabiliteManager.deleteAll();
                        System.out.println("suceed -- 31231");
                } catch (Exception e) {
                        //getTransactionManager().rollbackMyERP(vTS);
                        System.out.println("failed -- DFSFSFS");
                        throw new Exception(e.getMessage());
                }

                ResourceDatabasePopulator dataSet = new ResourceDatabasePopulator();
                try {
                        dataSet.addScripts(new ClassPathResource("data/sql/db.sql"));
                        dataSet.execute(getDbSource());
                } catch (Exception e) {
                        getTransactionManager().rollbackMyERP(vTS);
                        throw new Exception(e.getMessage());
                }

                try {
                        vJdbcTemplate.update("ALTER SEQUENCE myerp.ecriture_comptable_id_seq RESTART 1", sqlParameterSource);
                        getTransactionManager().commitMyERP(vTS);
                } catch (Exception e) {
                        getTransactionManager().rollbackMyERP(vTS);
                        throw new Exception(e.getMessage());
                }
        }*/

        /**
         * Cas passant : Insert de {@link EcritureComptable}
         */
        @Test
        @Order(1)
        public void testInsertEcritureComptable() throws FunctionalException {
                int nbLignesEcritureComptableInDatabase = 5;
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
                assertEquals("l'ajout n'a pas eu lieu car il y a toujours 5 lignes", nbLignesEcritureComptableInDatabase + 1, vListEcritureComptableBDD.size());
        }

        /**
         * Cas non passant : Insert de {@link EcritureComptable}
         */
        @Test()
        @Order(2)
        public void testInsertEcritureComptableNotValid1ligneEcriture() throws FunctionalException {
        assertThrows(FunctionalException.class, () -> {
                EcritureComptable vEcritureComptable;
                vEcritureComptable = new EcritureComptable();
                vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
                vEcritureComptable.setReference("AC-2019/99999");
                vEcritureComptable.setDate(new Date());
                vEcritureComptable.setLibelle("Libelle");
                vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(401),
                        null, new BigDecimal(987), null));
                comptabiliteManager.insertEcritureComptable(vEcritureComptable);
        });
        }

        /**
         * Cas passant : Suppression de {@link EcritureComptable}
         */
        @Test
        @Order(3)
        public void testDeleteEcritureComptable() throws Exception {
                int nbLignesEcritureComptableInDatabase = 6;
                comptabiliteManager.deleteEcritureComptable(-6);
                List<EcritureComptable> vListEcritureComptableBDD = getBusinessProxy().getComptabiliteManager().getListEcritureComptable();
                assertEquals("la suppression n'a pas eu lieu car il y a toujours 5 lignes", nbLignesEcritureComptableInDatabase - 1, vListEcritureComptableBDD.size());
        }

        /**
         * Cas non passant : Suppression de {@link EcritureComptable}
         */
        @Test()
        @Order(4)
        public void testDeleteEcritureComptableEcritureDoesntExist() throws NotFoundException {
                assertThrows(NotFoundException.class, () -> {
                        comptabiliteManager.deleteEcritureComptable(92374234);
                });
        }

        /**
         * Cas passant : Update de {@link EcritureComptable}
         */
        @Test()
        @Order(5)
        public void testUpdateEcritureComptable() throws FunctionalException, NotFoundException {
                EcritureComptable vEcritureComptable = comptabiliteManager.getListEcritureComptable().get(2);
                vEcritureComptable.setLibelle("le libelle a changé");
                comptabiliteManager.updateEcritureComptable(vEcritureComptable);
        }

        /**
         * Cas non passant : Update de {@link EcritureComptable}
         */
        @Test()
        @Order(6)
        public void testUpdateEcritureComptableDoesntExist() throws Exception {
                assertThrows(Exception.class, () -> {
                        EcritureComptable vEcritureComptable = comptabiliteManager.getListEcritureComptable().get(999999);
                        comptabiliteManager.updateEcritureComptable(vEcritureComptable);
                });
        }
}