package com.dummy.myerp.model.bean.comptabilite;

import org.apache.commons.lang3.ObjectUtils;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;


public class EcritureComptableTest {

    private LigneEcritureComptable createLigne(Integer pCompteComptableNumero, String pDebit, String pCredit) {
        BigDecimal vDebit = pDebit == null ? null : new BigDecimal(pDebit);
        BigDecimal vCredit = pCredit == null ? null : new BigDecimal(pCredit);
        String vLibelle = ObjectUtils.defaultIfNull(vDebit, BigDecimal.ZERO)
                                     .subtract(ObjectUtils.defaultIfNull(vCredit, BigDecimal.ZERO)).toPlainString();
        LigneEcritureComptable vRetour = new LigneEcritureComptable(new CompteComptable(pCompteComptableNumero),
                                                                    vLibelle,
                                                                    vDebit, vCredit);
        return vRetour;
    }

    @Test
    public void isEquilibree() {
        EcritureComptable vEcriture;
        vEcriture = new EcritureComptable();

        vEcriture.setLibelle("Equilibrée");
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "200.50", null));
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "100.50", "33"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, null, "301"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, "40", "7"));
        Assert.assertTrue(vEcriture.toString(), vEcriture.isEquilibree());

        vEcriture.getListLigneEcriture().clear();
        vEcriture.setLibelle("Non équilibrée");
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "10", null));
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "20", "1"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, null, "30"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, "1", "2"));
        Assert.assertFalse(vEcriture.toString(), vEcriture.isEquilibree());
    }

    @Test
    public void getTotalDebitPositif() {
        EcritureComptable vEcriture;
        vEcriture = new EcritureComptable();
        BigDecimal bigDecimalExpected = new BigDecimal("22.10");

        vEcriture.setLibelle("test total débit positif");
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "10", null));
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "10", null));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, null, null));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, "2.10", null));
        Assert.assertEquals(bigDecimalExpected.compareTo(vEcriture.getTotalDebit()), 0);
    }

    @Test
    public void getTotalCreditPositif() {
        EcritureComptable vEcriture;
        vEcriture = new EcritureComptable();
        BigDecimal bigDecimalExpected = new BigDecimal("4.99");

        vEcriture.setLibelle("test total crédit positif");
        vEcriture.getListLigneEcriture().add(this.createLigne(1, null, "1"));
        vEcriture.getListLigneEcriture().add(this.createLigne(1, null, "1"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, null, "1"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, null, "1.99"));
        Assert.assertEquals(bigDecimalExpected.compareTo(vEcriture.getTotalCredit()), 0);
    }

    @Test
    public void getTotalDebitNegatif() {
        EcritureComptable vEcriture;
        vEcriture = new EcritureComptable();
        BigDecimal bigDecimalExpected = new BigDecimal("-5");

        vEcriture.setLibelle("test total débit négatif");
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "-1", null));
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "-2", null));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, "-1", null));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, "-1", null));
        Assert.assertEquals(bigDecimalExpected.compareTo(vEcriture.getTotalDebit()), 0);
    }

    @Test
    public void getTotalCreditNegatif() {
        EcritureComptable vEcriture;
        vEcriture = new EcritureComptable();
        BigDecimal bigDecimalExpected = new BigDecimal("-3");

        vEcriture.setLibelle("test total crédit négatif");
        vEcriture.getListLigneEcriture().add(this.createLigne(1, null, "-1"));
        vEcriture.getListLigneEcriture().add(this.createLigne(1, null, null));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, null, "-1"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, null, "-1"));
        Assert.assertEquals(bigDecimalExpected.compareTo(vEcriture.getTotalCredit()), 0);
    }

    @Test
    public void getTotalDebitZero() {
        EcritureComptable vEcriture;
        vEcriture = new EcritureComptable();
        BigDecimal bigDecimalExpected = new BigDecimal("0");

        vEcriture.setLibelle("test total débit zéro");
        vEcriture.getListLigneEcriture().add(this.createLigne(1, null, null));
        vEcriture.getListLigneEcriture().add(this.createLigne(1, null, null));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, null, null));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, null, null));
        Assert.assertEquals(bigDecimalExpected.compareTo(vEcriture.getTotalDebit()), 0);
    }

    @Test
    public void getTotalCreditZero() {
        EcritureComptable vEcriture;
        vEcriture = new EcritureComptable();
        BigDecimal bigDecimalExpected = new BigDecimal("0");

        vEcriture.setLibelle("test total crédit zérp");
        vEcriture.getListLigneEcriture().add(this.createLigne(1, null, null));
        vEcriture.getListLigneEcriture().add(this.createLigne(1, null, null));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, null, null));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, null, null));
        Assert.assertEquals(bigDecimalExpected.compareTo(vEcriture.getTotalCredit()), 0);
    }
}
