package com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.dummy.myerp.model.bean.comptabilite.SequenceEcritureComptable;
import org.springframework.jdbc.core.RowMapper;
import com.dummy.myerp.consumer.ConsumerHelper;
import com.dummy.myerp.consumer.dao.impl.cache.JournalComptableDaoCache;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;


/**
 * {@link RowMapper} de {@link EcritureComptable}
 */
public class SequenceEcritureComptableRMTest implements RowMapper<SequenceEcritureComptable> {

    @Override
    public SequenceEcritureComptable mapRow(ResultSet pRS, int pRowNum) throws SQLException {
        SequenceEcritureComptable vBean = new SequenceEcritureComptable();
        vBean.setAnnee(pRS.getInt("id"));
        vBean.setDerniereValeur(pRS.getInt("derniere_valeur"));

        return vBean;
    }
}
