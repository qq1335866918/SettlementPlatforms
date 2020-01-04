package com.tt1000.settlementplatform.bean.member;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "TF_PRINT_TASK".
*/
public class TfPrintTaskDao extends AbstractDao<TfPrintTask, String> {

    public static final String TABLENAME = "TF_PRINT_TASK";

    /**
     * Properties of entity TfPrintTask.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property SEQNO = new Property(0, String.class, "SEQNO", true, "SEQNO");
        public final static Property COR_ID = new Property(1, String.class, "COR_ID", false, "COR_ID");
        public final static Property PRINT_TYPE = new Property(2, String.class, "PRINT_TYPE", false, "PRINT_TYPE");
        public final static Property CREATETIME = new Property(3, long.class, "CREATETIME", false, "CREATETIME");
        public final static Property CLIENT_CODE = new Property(4, String.class, "CLIENT_CODE", false, "CLIENT_CODE");
        public final static Property STORE_CODE = new Property(5, String.class, "STORE_CODE", false, "STORE_CODE");
        public final static Property PRINTER_NAME = new Property(6, String.class, "PRINTER_NAME", false, "PRINTER_NAME");
        public final static Property PRINT_STATUS = new Property(7, String.class, "PRINT_STATUS", false, "PRINT_STATUS");
        public final static Property UPDATETIME = new Property(8, long.class, "UPDATETIME", false, "UPDATETIME");
    }


    public TfPrintTaskDao(DaoConfig config) {
        super(config);
    }
    
    public TfPrintTaskDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"TF_PRINT_TASK\" (" + //
                "\"SEQNO\" TEXT PRIMARY KEY NOT NULL ," + // 0: SEQNO
                "\"COR_ID\" TEXT," + // 1: COR_ID
                "\"PRINT_TYPE\" TEXT," + // 2: PRINT_TYPE
                "\"CREATETIME\" INTEGER NOT NULL ," + // 3: CREATETIME
                "\"CLIENT_CODE\" TEXT," + // 4: CLIENT_CODE
                "\"STORE_CODE\" TEXT," + // 5: STORE_CODE
                "\"PRINTER_NAME\" TEXT," + // 6: PRINTER_NAME
                "\"PRINT_STATUS\" TEXT," + // 7: PRINT_STATUS
                "\"UPDATETIME\" INTEGER NOT NULL );"); // 8: UPDATETIME
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"TF_PRINT_TASK\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, TfPrintTask entity) {
        stmt.clearBindings();
 
        String SEQNO = entity.getSEQNO();
        if (SEQNO != null) {
            stmt.bindString(1, SEQNO);
        }
 
        String COR_ID = entity.getCOR_ID();
        if (COR_ID != null) {
            stmt.bindString(2, COR_ID);
        }
 
        String PRINT_TYPE = entity.getPRINT_TYPE();
        if (PRINT_TYPE != null) {
            stmt.bindString(3, PRINT_TYPE);
        }
        stmt.bindLong(4, entity.getCREATETIME());
 
        String CLIENT_CODE = entity.getCLIENT_CODE();
        if (CLIENT_CODE != null) {
            stmt.bindString(5, CLIENT_CODE);
        }
 
        String STORE_CODE = entity.getSTORE_CODE();
        if (STORE_CODE != null) {
            stmt.bindString(6, STORE_CODE);
        }
 
        String PRINTER_NAME = entity.getPRINTER_NAME();
        if (PRINTER_NAME != null) {
            stmt.bindString(7, PRINTER_NAME);
        }
 
        String PRINT_STATUS = entity.getPRINT_STATUS();
        if (PRINT_STATUS != null) {
            stmt.bindString(8, PRINT_STATUS);
        }
        stmt.bindLong(9, entity.getUPDATETIME());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, TfPrintTask entity) {
        stmt.clearBindings();
 
        String SEQNO = entity.getSEQNO();
        if (SEQNO != null) {
            stmt.bindString(1, SEQNO);
        }
 
        String COR_ID = entity.getCOR_ID();
        if (COR_ID != null) {
            stmt.bindString(2, COR_ID);
        }
 
        String PRINT_TYPE = entity.getPRINT_TYPE();
        if (PRINT_TYPE != null) {
            stmt.bindString(3, PRINT_TYPE);
        }
        stmt.bindLong(4, entity.getCREATETIME());
 
        String CLIENT_CODE = entity.getCLIENT_CODE();
        if (CLIENT_CODE != null) {
            stmt.bindString(5, CLIENT_CODE);
        }
 
        String STORE_CODE = entity.getSTORE_CODE();
        if (STORE_CODE != null) {
            stmt.bindString(6, STORE_CODE);
        }
 
        String PRINTER_NAME = entity.getPRINTER_NAME();
        if (PRINTER_NAME != null) {
            stmt.bindString(7, PRINTER_NAME);
        }
 
        String PRINT_STATUS = entity.getPRINT_STATUS();
        if (PRINT_STATUS != null) {
            stmt.bindString(8, PRINT_STATUS);
        }
        stmt.bindLong(9, entity.getUPDATETIME());
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public TfPrintTask readEntity(Cursor cursor, int offset) {
        TfPrintTask entity = new TfPrintTask( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // SEQNO
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // COR_ID
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // PRINT_TYPE
            cursor.getLong(offset + 3), // CREATETIME
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // CLIENT_CODE
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // STORE_CODE
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // PRINTER_NAME
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // PRINT_STATUS
            cursor.getLong(offset + 8) // UPDATETIME
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, TfPrintTask entity, int offset) {
        entity.setSEQNO(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setCOR_ID(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setPRINT_TYPE(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setCREATETIME(cursor.getLong(offset + 3));
        entity.setCLIENT_CODE(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setSTORE_CODE(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setPRINTER_NAME(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setPRINT_STATUS(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setUPDATETIME(cursor.getLong(offset + 8));
     }
    
    @Override
    protected final String updateKeyAfterInsert(TfPrintTask entity, long rowId) {
        return entity.getSEQNO();
    }
    
    @Override
    public String getKey(TfPrintTask entity) {
        if(entity != null) {
            return entity.getSEQNO();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(TfPrintTask entity) {
        return entity.getSEQNO() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
