package com.dummy.myerp.consumer.dao.impl.db.dao;

import com.dummy.myerp.consumer.dao.contrat.ComptabiliteDao;
import com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite.*;
import com.dummy.myerp.consumer.db.AbstractDbConsumer;
import com.dummy.myerp.consumer.db.DataSourcesEnum;
import com.dummy.myerp.model.bean.comptabilite.*;
import com.dummy.myerp.technical.exception.NotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.Types;
import java.util.List;


/**
 * Implémentation de l'interface {@link ComptabiliteDao}
 */
public class ComptabiliteDaoImpl extends AbstractDbConsumer implements ComptabiliteDao {

    // ==================== Constructeurs ====================
    /** Instance unique de la classe (design pattern Singleton) */
    private static final ComptabiliteDaoImpl INSTANCE = new ComptabiliteDaoImpl();

    /**
     * Renvoie l'instance unique de la classe (design pattern Singleton).
     *
     * @return {@link ComptabiliteDaoImpl}
     */
    public static ComptabiliteDaoImpl getInstance() {
        return ComptabiliteDaoImpl.INSTANCE;
    }

    /**
     * Constructeur.
     */
    protected ComptabiliteDaoImpl() {
        super();
    }


    // ==================== Méthodes ====================
    /** SQLgetListCompteComptable */
    private static String SQLgetListCompteComptable;
    public void setSQLgetListCompteComptable(String pSQLgetListCompteComptable) {
        SQLgetListCompteComptable = pSQLgetListCompteComptable;
    }
    @Override
    public List<CompteComptable> getListCompteComptable() {
        JdbcTemplate vJdbcTemplate = new JdbcTemplate(this.getDataSource(DataSourcesEnum.MYERP));
        CompteComptableRM vRM = new CompteComptableRM();
        List<CompteComptable> vList = vJdbcTemplate.query(SQLgetListCompteComptable, vRM);
        return vList;
    }


    /** SQLgetListJournalComptable */
    private static String SQLgetListJournalComptable;
    public void setSQLgetListJournalComptable(String pSQLgetListJournalComptable) {
        SQLgetListJournalComptable = pSQLgetListJournalComptable;
    }
    @Override
    public List<JournalComptable> getListJournalComptable() {
        JdbcTemplate vJdbcTemplate = new JdbcTemplate(this.getDataSource(DataSourcesEnum.MYERP));
        JournalComptableRM vRM = new JournalComptableRM();
        List<JournalComptable> vList = vJdbcTemplate.query(SQLgetListJournalComptable, vRM);
        return vList;
    }

    // ==================== EcritureComptable - GET ====================

    /** SQLgetListEcritureComptable */
    private static String SQLgetListEcritureComptable;
    public void setSQLgetListEcritureComptable(String pSQLgetListEcritureComptable) {
        SQLgetListEcritureComptable = pSQLgetListEcritureComptable;
    }
    @Override
    public List<EcritureComptable> getListEcritureComptable() {
        JdbcTemplate vJdbcTemplate = new JdbcTemplate(this.getDataSource(DataSourcesEnum.MYERP));
        EcritureComptableRM vRM = new EcritureComptableRM();
        List<EcritureComptable> vList = vJdbcTemplate.query(SQLgetListEcritureComptable, vRM);
        return vList;
    }


    /** SQLgetEcritureComptable */
    private static String SQLgetEcritureComptable;
    public void setSQLgetEcritureComptable(String pSQLgetEcritureComptable) {
        SQLgetEcritureComptable = pSQLgetEcritureComptable;
    }
    @Override
    public EcritureComptable getEcritureComptable(Integer pId) throws NotFoundException {
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue("id", pId);
        EcritureComptableRM vRM = new EcritureComptableRM();
        EcritureComptable vBean;
        try {
            vBean = vJdbcTemplate.queryForObject(SQLgetEcritureComptable, vSqlParams, vRM);
        } catch (EmptyResultDataAccessException vEx) {
            throw new NotFoundException("EcritureComptable non trouvée : id=" + pId);
        }
        return vBean;
    }


    /** SQLgetEcritureComptableByRef */
    private static String SQLgetEcritureComptableByRef;
    public void setSQLgetEcritureComptableByRef(String pSQLgetEcritureComptableByRef) {
        SQLgetEcritureComptableByRef = pSQLgetEcritureComptableByRef;
    }
    @Override
    public EcritureComptable getEcritureComptableByRef(String pReference) throws NotFoundException {
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue("reference", pReference);
        EcritureComptableRM vRM = new EcritureComptableRM();
        EcritureComptable vBean;
        try {
            vBean = vJdbcTemplate.queryForObject(SQLgetEcritureComptableByRef, vSqlParams, vRM);
        } catch (EmptyResultDataAccessException vEx) {
            throw new NotFoundException("EcritureComptable non trouvée : reference=" + pReference);
        }
        return vBean;
    }


    /** SQLgetLastSequenceEcritureComptable */
    private static String SQLgetLastSequenceEcritureComptable;
    public void setSQLgetLastSequenceEcritureComptable(String pSQLgetLastSequenceEcritureComptable) {
        SQLgetLastSequenceEcritureComptable = pSQLgetLastSequenceEcritureComptable;
    }
    @Override
    public SequenceEcritureComptable getLastSequenceEcritureComptable(String journal_code, Integer pYear) throws NotFoundException {
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue("annee", pYear);
        vSqlParams.addValue("journal_code", journal_code);
        SequenceEcritureComptableRM vRM = new SequenceEcritureComptableRM();
        SequenceEcritureComptable vBean;
        try {
            vBean = vJdbcTemplate.queryForObject(SQLgetLastSequenceEcritureComptable, vSqlParams, vRM);
        } catch (EmptyResultDataAccessException vEx) {
            throw new NotFoundException("La dernière EcritureComptable non trouvée");
        }
        return vBean;
    }

    /** SQLupdateSequenceEcritureComptable */
    private static String SQLupdateSequenceEcritureComptable;
    public static void setSQLupdateSequenceEcritureComptable(String pSQLupdateSequenceEcritureComptable) {
        SQLupdateSequenceEcritureComptable = pSQLupdateSequenceEcritureComptable;
    }
    @Override
    public void updateSequenceEcritureComptable (String pCodeJournal, SequenceEcritureComptable pSequenceEcritureComptable) {
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue("annee", pSequenceEcritureComptable.getAnnee());
        vSqlParams.addValue("journal_code", pCodeJournal);
        vSqlParams.addValue("derniere_valeur", pSequenceEcritureComptable.getDerniereValeur());
        vJdbcTemplate.update(SQLupdateSequenceEcritureComptable, vSqlParams);
    }

    /** SQLloadListLigneEcriture */
    private static String SQLloadListLigneEcriture;
    public void setSQLloadListLigneEcriture(String pSQLloadListLigneEcriture) {
        SQLloadListLigneEcriture = pSQLloadListLigneEcriture;
    }
    @Override
    public void loadListLigneEcriture(EcritureComptable pEcritureComptable) {
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue("ecriture_id", pEcritureComptable.getId());
        LigneEcritureComptableRM vRM = new LigneEcritureComptableRM();
        List<LigneEcritureComptable> vList = vJdbcTemplate.query(SQLloadListLigneEcriture, vSqlParams, vRM);
        pEcritureComptable.getListLigneEcriture().clear();
        pEcritureComptable.getListLigneEcriture().addAll(vList);
    }


    // ==================== EcritureComptable - INSERT ====================

    /** SQLinsertEcritureComptable */
    private static String SQLinsertEcritureComptable;
    public void setSQLinsertEcritureComptable(String pSQLinsertEcritureComptable) {
        SQLinsertEcritureComptable = pSQLinsertEcritureComptable;
    }
    @Override
    public void insertEcritureComptable(EcritureComptable pEcritureComptable) {
        // ===== Ecriture Comptable
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue("journal_code", pEcritureComptable.getJournal().getCode());
        vSqlParams.addValue("reference", pEcritureComptable.getReference());
        vSqlParams.addValue("date", pEcritureComptable.getDate(), Types.DATE);
        vSqlParams.addValue("libelle", pEcritureComptable.getLibelle());

        vJdbcTemplate.update(SQLinsertEcritureComptable, vSqlParams);

        // ----- Récupération de l'id
        Integer vId = this.queryGetSequenceValuePostgreSQL(DataSourcesEnum.MYERP, "myerp.ecriture_comptable_id_seq",
                                                           Integer.class);
        pEcritureComptable.setId(vId);

        // ===== Liste des lignes d'écriture
        this.insertListLigneEcritureComptable(pEcritureComptable);
    }

    /** SQLinsertListLigneEcritureComptable */
    private static String SQLinsertListLigneEcritureComptable;
    public void setSQLinsertListLigneEcritureComptable(String pSQLinsertListLigneEcritureComptable) {
        SQLinsertListLigneEcritureComptable = pSQLinsertListLigneEcritureComptable;
    }
    /**
     * Insert les lignes d'écriture de l'écriture comptable
     * @param pEcritureComptable l'écriture comptable
     */
    protected void insertListLigneEcritureComptable(EcritureComptable pEcritureComptable) {
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue("ecriture_id", pEcritureComptable.getId());

        int vLigneId = 0;
        for (LigneEcritureComptable vLigne : pEcritureComptable.getListLigneEcriture()) {
            vLigneId++;
            vSqlParams.addValue("ligne_id", vLigneId);
            vSqlParams.addValue("compte_comptable_numero", vLigne.getCompteComptable().getNumero());
            vSqlParams.addValue("libelle", vLigne.getLibelle());
            vSqlParams.addValue("debit", vLigne.getDebit());

            vSqlParams.addValue("credit", vLigne.getCredit());

            vJdbcTemplate.update(SQLinsertListLigneEcritureComptable, vSqlParams);
        }
    }


    // ==================== EcritureComptable - UPDATE ====================

    /** SQLupdateEcritureComptable */
    private static String SQLupdateEcritureComptable;
    public void setSQLupdateEcritureComptable(String pSQLupdateEcritureComptable) {
        SQLupdateEcritureComptable = pSQLupdateEcritureComptable;
    }
    @Override
    public void updateEcritureComptable(EcritureComptable pEcritureComptable) throws NotFoundException {
        // ===== Ecriture Comptable
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue("id", pEcritureComptable.getId());
        vSqlParams.addValue("journal_code", pEcritureComptable.getJournal().getCode());
        vSqlParams.addValue("reference", pEcritureComptable.getReference());
        vSqlParams.addValue("date", pEcritureComptable.getDate(), Types.DATE);
        vSqlParams.addValue("libelle", pEcritureComptable.getLibelle());

        vJdbcTemplate.update(SQLupdateEcritureComptable, vSqlParams);
        // ===== Liste des lignes d'écriture
        this.deleteListLigneEcritureComptable(pEcritureComptable.getId());
        this.insertListLigneEcritureComptable(pEcritureComptable);
    }


    // ==================== EcritureComptable - DELETE ====================

    /** SQLdeleteEcritureComptable */
    private static String SQLdeleteEcritureComptable;
    public void setSQLdeleteEcritureComptable(String pSQLdeleteEcritureComptable) {
        SQLdeleteEcritureComptable = pSQLdeleteEcritureComptable;
    }
    @Override
    public void deleteEcritureComptable(Integer pId) throws NotFoundException {
        // ===== Suppression des lignes d'écriture
        this.deleteListLigneEcritureComptable(pId);

        // ===== Suppression de l'écriture
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue("id", pId);
        vJdbcTemplate.update(SQLdeleteEcritureComptable, vSqlParams);
    }

    /** SQLdeleteListLigneEcritureComptable */
    private static String SQLdeleteListLigneEcritureComptable;
    public void setSQLdeleteListLigneEcritureComptable(String pSQLdeleteListLigneEcritureComptable) {
        SQLdeleteListLigneEcritureComptable = pSQLdeleteListLigneEcritureComptable;
    }
    /**
     * Supprime les lignes d'écriture de l'écriture comptable d'id {@code pEcritureId}
     * @param pEcritureId id de l'écriture comptable
     */
    protected void deleteListLigneEcritureComptable(Integer pEcritureId) throws NotFoundException {
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue("ecriture_id", pEcritureId);
        if (vJdbcTemplate.update(SQLdeleteListLigneEcritureComptable, vSqlParams) == 0) {
            throw new NotFoundException();
        }
    }

    /** SQLdeleteLigneEcriture */
    private static String SQLdeleteLigneEcriture;
    public void setSQLdeleteLigneEcriture(String pSQLdeleteLigneEcriture) {
        SQLdeleteLigneEcriture = pSQLdeleteLigneEcriture;
    }
    /** SQLdeleteSequence */
    private static String SQLdeleteSequence;
    public void setSQLdeleteSequence(String pSQLdeleteSequence) {
        SQLdeleteSequence = pSQLdeleteSequence;
    }
    /** SQLdeleteEcriture */
    private static String SQLdeleteEcriture;
    public void setSQLdeleteEcriture(String pSQLdeleteEcriture) {
        SQLdeleteEcriture = pSQLdeleteEcriture;
    }
    /** SQLdeleteJournalComptable */
    private static String SQLdeleteJournalComptable;
    public void setSQLdeleteJournalComptable(String pSQLdeleteJournalComptable) {
        SQLdeleteJournalComptable = pSQLdeleteJournalComptable;
    }
    /** SQLdeleteCompteComptable */
    private static String SQLdeleteCompteComptable;
    public void setSQLdeleteCompteComptable(String pSQLdeleteCompteComptable) {
        SQLdeleteCompteComptable = pSQLdeleteCompteComptable;
    }
    /**
     * Supprime toutes les lignes
     */
    public void deleteAll() {
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vJdbcTemplate.update(SQLdeleteLigneEcriture, vSqlParams);
        vJdbcTemplate.update(SQLdeleteSequence, vSqlParams);
        vJdbcTemplate.update(SQLdeleteEcriture, vSqlParams);
        vJdbcTemplate.update(SQLdeleteJournalComptable, vSqlParams);
        vJdbcTemplate.update(SQLdeleteCompteComptable, vSqlParams);
    }
}
