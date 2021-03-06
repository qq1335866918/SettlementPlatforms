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
 * DAO for table "SYNC_TABLE_RECORD".
*/
public class SyncTableRecordDao extends AbstractDao<SyncTableRecord, String> {

    public static final String TABLENAME = "SYNC_TABLE_RECORD";

    /**
     * Properties of entity SyncTableRecord.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property SEQNO = new Property(0, String.class, "SEQNO", true, "SEQNO");
        public final static Property TABLENAME = new Property(1, String.class, "TABLENAME", false, "TABLENAME");
        public final static Property PRIMARYKEY = new Property(2, String.class, "PRIMARYKEY", false, "PRIMARYKEY");
        public final static Property QUESTTIME = new Property(3, int.class, "QUESTTIME", false, "QUESTTIME");
        public final static Property ISM_STATUS = new Property(4, String.class, "ISM_STATUS", false, "ISM_STATUS");
        public final static Property INIT_STATUS = new Property(5, String.class, "INIT_STATUS", false, "INIT_STATUS");
        public final static Property CLIENT_CODE = new Property(6, String.class, "CLIENT_CODE", false, "CLIENT_CODE");
        public final static Property STORE_CODE = new Property(7, String.class, "STORE_CODE", false, "STORE_CODE");
        public final static Property CREATETIME = new Property(8, String.class, "CREATETIME", false, "CREATETIME");
        public final static Property UPDATETIME = new Property(9, String.class, "UPDATETIME", false, "UPDATETIME");
        public final static Property ANOTHER_NAME = new Property(10, String.class, "ANOTHER_NAME", false, "ANOTHER_NAME");
    }


    public SyncTableRecordDao(DaoConfig config) {
        super(config);
    }
    
    public SyncTableRecordDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"SYNC_TABLE_RECORD\" (" + //
                "\"SEQNO\" TEXT PRIMARY KEY NOT NULL ," + // 0: SEQNO
                "\"TABLENAME\" TEXT," + // 1: TABLENAME
                "\"PRIMARYKEY\" TEXT," + // 2: PRIMARYKEY
                "\"QUESTTIME\" INTEGER NOT NULL ," + // 3: QUESTTIME
                "\"ISM_STATUS\" TEXT," + // 4: ISM_STATUS
                "\"INIT_STATUS\" TEXT," + // 5: INIT_STATUS
                "\"CLIENT_CODE\" TEXT," + // 6: CLIENT_CODE
                "\"STORE_CODE\" TEXT," + // 7: STORE_CODE
                "\"CREATETIME\" TEXT," + // 8: CREATETIME
                "\"UPDATETIME\" TEXT," + // 9: UPDATETIME
                "\"ANOTHER_NAME\" TEXT);"); // 10: ANOTHER_NAME
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"SYNC_TABLE_RECORD\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, SyncTableRecord entity) {
        stmt.clearBindings();
 
        String SEQNO = entity.getSEQNO();
        if (SEQNO != null) {
            stmt.bindString(1, SEQNO);
        }
 
        String TABLENAME = entity.getTABLENAME();
        if (TABLENAME != null) {
            stmt.bindString(2, TABLENAME);
        }
 
        String PRIMARYKEY = entity.getPRIMARYKEY();
        if (PRIMARYKEY != null) {
            stmt.bindString(3, PRIMARYKEY);
        }
        stmt.bindLong(4, entity.getQUESTTIME());
 
        String ISM_STATUS = entity.getISM_STATUS();
        if (ISM_STATUS != null) {
            stmt.bindString(5, ISM_STATUS);
        }
 
        String INIT_STATUS = entity.getINIT_STATUS();
        if (INIT_STATUS != null) {
            stmt.bindString(6, INIT_STATUS);
        }
 
        String CLIENT_CODE = entity.getCLIENT_CODE();
        if (CLIENT_CODE != null) {
            stmt.bindString(7, CLIENT_CODE);
        }
 
        String STORE_CODE = entity.getSTORE_CODE();
        if (STORE_CODE != null) {
            stmt.bindString(8, STORE_CODE);
        }
 
        String CREATETIME = entity.getCREATETIME();
        if (CREATETIME != null) {
            stmt.bindString(9, CREATETIME);
        }
 
        String UPDATETIME = entity.getUPDATETIME();
        if (UPDATETIME != null) {
            stmt.bindString(10, UPDATETIME);
        }
 
        String ANOTHER_NAME = entity.getANOTHER_NAME();
        if (ANOTHER_NAME != null) {
            stmt.bindString(11, ANOTHER_NAME);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, SyncTableRecord entity) {
        stmt.clearBindings();
 
        String SEQNO = entity.getSEQNO();
        if (SEQNO != null) {
            stmt.bindString(1, SEQNO);
        }
 
        String TABLENAME = entity.getTABLENAME();
        if (TABLENAME != null) {
            stmt.bindString(2, TABLENAME);
        }
 
        String PRIMARYKEY = entity.getPRIMARYKEY();
        if (PRIMARYKEY != null) {
            stmt.bindString(3, PRIMARYKEY);
        }
        stmt.bindLong(4, entity.getQUESTTIME());
 
        String ISM_STATUS = entity.getISM_STATUS();
        if (ISM_STATUS != null) {
            stmt.bindString(5, ISM_STATUS);
        }
 
        String INIT_STATUS = entity.getINIT_STATUS();
        if (INIT_STATUS != null) {
            stmt.bindString(6, INIT_STATUS);
        }
 
        String CLIENT_CODE = entity.getCLIENT_CODE();
        if (CLIENT_CODE != null) {
            stmt.bindString(7, CLIENT_CODE);
        }
 
        String STORE_CODE = entity.getSTORE_CODE();
        if (STORE_CODE != null) {
            stmt.bindString(8, STORE_CODE);
        }
 
        String CREATETIME = entity.getCREATETIME();
        if (CREATETIME != null) {
            stmt.bindString(9, CREATETIME);
        }
 
        String UPDATETIME = entity.getUPDATETIME();
        if (UPDATETIME != null) {
            stmt.bindString(10, UPDATETIME);
        }
 
        String ANOTHER_NAME = entity.getANOTHER_NAME();
        if (ANOTHER_NAME != null) {
            stmt.bindString(11, ANOTHER_NAME);
        }
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public SyncTableRecord readEntity(Cursor cursor, int offset) {
        SyncTableRecord entity = new SyncTableRecord( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // SEQNO
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // TABLENAME
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // PRIMARYKEY
            cursor.getInt(offset + 3), // QUESTTIME
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // ISM_STATUS
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // INIT_STATUS
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // CLIENT_CODE
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // STORE_CODE
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // CREATETIME
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // UPDATETIME
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10) // ANOTHER_NAME
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, SyncTableRecord entity, int offset) {
        entity.setSEQNO(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setTABLENAME(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setPRIMARYKEY(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setQUESTTIME(cursor.getInt(offset + 3));
        entity.setISM_STATUS(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setINIT_STATUS(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setCLIENT_CODE(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setSTORE_CODE(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setCREATETIME(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setUPDATETIME(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setANOTHER_NAME(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
     }
    
    @Override
    protected final String updateKeyAfterInsert(SyncTableRecord entity, long rowId) {
        return entity.getSEQNO();
    }
    
    @Override
    public String getKey(SyncTableRecord entity) {
        if(entity != null) {
            return entity.getSEQNO();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(SyncTableRecord entity) {
        return entity.getSEQNO() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
