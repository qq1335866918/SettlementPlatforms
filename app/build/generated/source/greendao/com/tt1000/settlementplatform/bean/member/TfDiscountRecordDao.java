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
 * DAO for table "TF_DISCOUNT_RECORD".
*/
public class TfDiscountRecordDao extends AbstractDao<TfDiscountRecord, String> {

    public static final String TABLENAME = "TF_DISCOUNT_RECORD";

    /**
     * Properties of entity TfDiscountRecord.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property SEQNO = new Property(0, String.class, "SEQNO", true, "SEQNO");
        public final static Property DISCOUNT_TYPE = new Property(1, String.class, "DISCOUNT_TYPE", false, "DISCOUNT_TYPE");
        public final static Property DISCOUNT_RATE_C1 = new Property(2, String.class, "DISCOUNT_RATE_C1", false, "DISCOUNT_RATE_C1");
        public final static Property DISCOUNT_RATE_C2 = new Property(3, String.class, "DISCOUNT_RATE_C2", false, "DISCOUNT_RATE_C2");
        public final static Property DISCOUNT_RATE_C3 = new Property(4, String.class, "DISCOUNT_RATE_C3", false, "DISCOUNT_RATE_C3");
        public final static Property DISCOUNT_RATE_C4 = new Property(5, String.class, "DISCOUNT_RATE_C4", false, "DISCOUNT_RATE_C4");
        public final static Property DISCOUNT_RATE_C5 = new Property(6, String.class, "DISCOUNT_RATE_C5", false, "DISCOUNT_RATE_C5");
        public final static Property DISCOUNT_RATE_C6 = new Property(7, String.class, "DISCOUNT_RATE_C6", false, "DISCOUNT_RATE_C6");
        public final static Property DISCOUNT_RATE_C7 = new Property(8, String.class, "DISCOUNT_RATE_C7", false, "DISCOUNT_RATE_C7");
        public final static Property DISCOUNT_RATE_C8 = new Property(9, String.class, "DISCOUNT_RATE_C8", false, "DISCOUNT_RATE_C8");
        public final static Property DISCOUNT_RATE_C9 = new Property(10, String.class, "DISCOUNT_RATE_C9", false, "DISCOUNT_RATE_C9");
        public final static Property DISCOUNT_RATE_C10 = new Property(11, String.class, "DISCOUNT_RATE_C10", false, "DISCOUNT_RATE_C10");
        public final static Property CREATETIME = new Property(12, long.class, "CREATETIME", false, "CREATETIME");
        public final static Property UPDATETIME = new Property(13, long.class, "UPDATETIME", false, "UPDATETIME");
        public final static Property STORE_CODE = new Property(14, String.class, "STORE_CODE", false, "STORE_CODE");
        public final static Property CLIENT_CODE = new Property(15, String.class, "CLIENT_CODE", false, "CLIENT_CODE");
        public final static Property STARTTIME = new Property(16, String.class, "STARTTIME", false, "STARTTIME");
        public final static Property ENDTIME = new Property(17, String.class, "ENDTIME", false, "ENDTIME");
        public final static Property DISCOUNT_STATUS = new Property(18, String.class, "DISCOUNT_STATUS", false, "DISCOUNT_STATUS");
    }


    public TfDiscountRecordDao(DaoConfig config) {
        super(config);
    }
    
    public TfDiscountRecordDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"TF_DISCOUNT_RECORD\" (" + //
                "\"SEQNO\" TEXT PRIMARY KEY NOT NULL ," + // 0: SEQNO
                "\"DISCOUNT_TYPE\" TEXT," + // 1: DISCOUNT_TYPE
                "\"DISCOUNT_RATE_C1\" TEXT," + // 2: DISCOUNT_RATE_C1
                "\"DISCOUNT_RATE_C2\" TEXT," + // 3: DISCOUNT_RATE_C2
                "\"DISCOUNT_RATE_C3\" TEXT," + // 4: DISCOUNT_RATE_C3
                "\"DISCOUNT_RATE_C4\" TEXT," + // 5: DISCOUNT_RATE_C4
                "\"DISCOUNT_RATE_C5\" TEXT," + // 6: DISCOUNT_RATE_C5
                "\"DISCOUNT_RATE_C6\" TEXT," + // 7: DISCOUNT_RATE_C6
                "\"DISCOUNT_RATE_C7\" TEXT," + // 8: DISCOUNT_RATE_C7
                "\"DISCOUNT_RATE_C8\" TEXT," + // 9: DISCOUNT_RATE_C8
                "\"DISCOUNT_RATE_C9\" TEXT," + // 10: DISCOUNT_RATE_C9
                "\"DISCOUNT_RATE_C10\" TEXT," + // 11: DISCOUNT_RATE_C10
                "\"CREATETIME\" INTEGER NOT NULL ," + // 12: CREATETIME
                "\"UPDATETIME\" INTEGER NOT NULL ," + // 13: UPDATETIME
                "\"STORE_CODE\" TEXT," + // 14: STORE_CODE
                "\"CLIENT_CODE\" TEXT," + // 15: CLIENT_CODE
                "\"STARTTIME\" TEXT," + // 16: STARTTIME
                "\"ENDTIME\" TEXT," + // 17: ENDTIME
                "\"DISCOUNT_STATUS\" TEXT);"); // 18: DISCOUNT_STATUS
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"TF_DISCOUNT_RECORD\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, TfDiscountRecord entity) {
        stmt.clearBindings();
 
        String SEQNO = entity.getSEQNO();
        if (SEQNO != null) {
            stmt.bindString(1, SEQNO);
        }
 
        String DISCOUNT_TYPE = entity.getDISCOUNT_TYPE();
        if (DISCOUNT_TYPE != null) {
            stmt.bindString(2, DISCOUNT_TYPE);
        }
 
        String DISCOUNT_RATE_C1 = entity.getDISCOUNT_RATE_C1();
        if (DISCOUNT_RATE_C1 != null) {
            stmt.bindString(3, DISCOUNT_RATE_C1);
        }
 
        String DISCOUNT_RATE_C2 = entity.getDISCOUNT_RATE_C2();
        if (DISCOUNT_RATE_C2 != null) {
            stmt.bindString(4, DISCOUNT_RATE_C2);
        }
 
        String DISCOUNT_RATE_C3 = entity.getDISCOUNT_RATE_C3();
        if (DISCOUNT_RATE_C3 != null) {
            stmt.bindString(5, DISCOUNT_RATE_C3);
        }
 
        String DISCOUNT_RATE_C4 = entity.getDISCOUNT_RATE_C4();
        if (DISCOUNT_RATE_C4 != null) {
            stmt.bindString(6, DISCOUNT_RATE_C4);
        }
 
        String DISCOUNT_RATE_C5 = entity.getDISCOUNT_RATE_C5();
        if (DISCOUNT_RATE_C5 != null) {
            stmt.bindString(7, DISCOUNT_RATE_C5);
        }
 
        String DISCOUNT_RATE_C6 = entity.getDISCOUNT_RATE_C6();
        if (DISCOUNT_RATE_C6 != null) {
            stmt.bindString(8, DISCOUNT_RATE_C6);
        }
 
        String DISCOUNT_RATE_C7 = entity.getDISCOUNT_RATE_C7();
        if (DISCOUNT_RATE_C7 != null) {
            stmt.bindString(9, DISCOUNT_RATE_C7);
        }
 
        String DISCOUNT_RATE_C8 = entity.getDISCOUNT_RATE_C8();
        if (DISCOUNT_RATE_C8 != null) {
            stmt.bindString(10, DISCOUNT_RATE_C8);
        }
 
        String DISCOUNT_RATE_C9 = entity.getDISCOUNT_RATE_C9();
        if (DISCOUNT_RATE_C9 != null) {
            stmt.bindString(11, DISCOUNT_RATE_C9);
        }
 
        String DISCOUNT_RATE_C10 = entity.getDISCOUNT_RATE_C10();
        if (DISCOUNT_RATE_C10 != null) {
            stmt.bindString(12, DISCOUNT_RATE_C10);
        }
        stmt.bindLong(13, entity.getCREATETIME());
        stmt.bindLong(14, entity.getUPDATETIME());
 
        String STORE_CODE = entity.getSTORE_CODE();
        if (STORE_CODE != null) {
            stmt.bindString(15, STORE_CODE);
        }
 
        String CLIENT_CODE = entity.getCLIENT_CODE();
        if (CLIENT_CODE != null) {
            stmt.bindString(16, CLIENT_CODE);
        }
 
        String STARTTIME = entity.getSTARTTIME();
        if (STARTTIME != null) {
            stmt.bindString(17, STARTTIME);
        }
 
        String ENDTIME = entity.getENDTIME();
        if (ENDTIME != null) {
            stmt.bindString(18, ENDTIME);
        }
 
        String DISCOUNT_STATUS = entity.getDISCOUNT_STATUS();
        if (DISCOUNT_STATUS != null) {
            stmt.bindString(19, DISCOUNT_STATUS);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, TfDiscountRecord entity) {
        stmt.clearBindings();
 
        String SEQNO = entity.getSEQNO();
        if (SEQNO != null) {
            stmt.bindString(1, SEQNO);
        }
 
        String DISCOUNT_TYPE = entity.getDISCOUNT_TYPE();
        if (DISCOUNT_TYPE != null) {
            stmt.bindString(2, DISCOUNT_TYPE);
        }
 
        String DISCOUNT_RATE_C1 = entity.getDISCOUNT_RATE_C1();
        if (DISCOUNT_RATE_C1 != null) {
            stmt.bindString(3, DISCOUNT_RATE_C1);
        }
 
        String DISCOUNT_RATE_C2 = entity.getDISCOUNT_RATE_C2();
        if (DISCOUNT_RATE_C2 != null) {
            stmt.bindString(4, DISCOUNT_RATE_C2);
        }
 
        String DISCOUNT_RATE_C3 = entity.getDISCOUNT_RATE_C3();
        if (DISCOUNT_RATE_C3 != null) {
            stmt.bindString(5, DISCOUNT_RATE_C3);
        }
 
        String DISCOUNT_RATE_C4 = entity.getDISCOUNT_RATE_C4();
        if (DISCOUNT_RATE_C4 != null) {
            stmt.bindString(6, DISCOUNT_RATE_C4);
        }
 
        String DISCOUNT_RATE_C5 = entity.getDISCOUNT_RATE_C5();
        if (DISCOUNT_RATE_C5 != null) {
            stmt.bindString(7, DISCOUNT_RATE_C5);
        }
 
        String DISCOUNT_RATE_C6 = entity.getDISCOUNT_RATE_C6();
        if (DISCOUNT_RATE_C6 != null) {
            stmt.bindString(8, DISCOUNT_RATE_C6);
        }
 
        String DISCOUNT_RATE_C7 = entity.getDISCOUNT_RATE_C7();
        if (DISCOUNT_RATE_C7 != null) {
            stmt.bindString(9, DISCOUNT_RATE_C7);
        }
 
        String DISCOUNT_RATE_C8 = entity.getDISCOUNT_RATE_C8();
        if (DISCOUNT_RATE_C8 != null) {
            stmt.bindString(10, DISCOUNT_RATE_C8);
        }
 
        String DISCOUNT_RATE_C9 = entity.getDISCOUNT_RATE_C9();
        if (DISCOUNT_RATE_C9 != null) {
            stmt.bindString(11, DISCOUNT_RATE_C9);
        }
 
        String DISCOUNT_RATE_C10 = entity.getDISCOUNT_RATE_C10();
        if (DISCOUNT_RATE_C10 != null) {
            stmt.bindString(12, DISCOUNT_RATE_C10);
        }
        stmt.bindLong(13, entity.getCREATETIME());
        stmt.bindLong(14, entity.getUPDATETIME());
 
        String STORE_CODE = entity.getSTORE_CODE();
        if (STORE_CODE != null) {
            stmt.bindString(15, STORE_CODE);
        }
 
        String CLIENT_CODE = entity.getCLIENT_CODE();
        if (CLIENT_CODE != null) {
            stmt.bindString(16, CLIENT_CODE);
        }
 
        String STARTTIME = entity.getSTARTTIME();
        if (STARTTIME != null) {
            stmt.bindString(17, STARTTIME);
        }
 
        String ENDTIME = entity.getENDTIME();
        if (ENDTIME != null) {
            stmt.bindString(18, ENDTIME);
        }
 
        String DISCOUNT_STATUS = entity.getDISCOUNT_STATUS();
        if (DISCOUNT_STATUS != null) {
            stmt.bindString(19, DISCOUNT_STATUS);
        }
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public TfDiscountRecord readEntity(Cursor cursor, int offset) {
        TfDiscountRecord entity = new TfDiscountRecord( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // SEQNO
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // DISCOUNT_TYPE
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // DISCOUNT_RATE_C1
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // DISCOUNT_RATE_C2
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // DISCOUNT_RATE_C3
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // DISCOUNT_RATE_C4
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // DISCOUNT_RATE_C5
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // DISCOUNT_RATE_C6
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // DISCOUNT_RATE_C7
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // DISCOUNT_RATE_C8
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // DISCOUNT_RATE_C9
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // DISCOUNT_RATE_C10
            cursor.getLong(offset + 12), // CREATETIME
            cursor.getLong(offset + 13), // UPDATETIME
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // STORE_CODE
            cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15), // CLIENT_CODE
            cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16), // STARTTIME
            cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17), // ENDTIME
            cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18) // DISCOUNT_STATUS
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, TfDiscountRecord entity, int offset) {
        entity.setSEQNO(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setDISCOUNT_TYPE(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setDISCOUNT_RATE_C1(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setDISCOUNT_RATE_C2(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setDISCOUNT_RATE_C3(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setDISCOUNT_RATE_C4(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setDISCOUNT_RATE_C5(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setDISCOUNT_RATE_C6(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setDISCOUNT_RATE_C7(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setDISCOUNT_RATE_C8(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setDISCOUNT_RATE_C9(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setDISCOUNT_RATE_C10(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setCREATETIME(cursor.getLong(offset + 12));
        entity.setUPDATETIME(cursor.getLong(offset + 13));
        entity.setSTORE_CODE(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setCLIENT_CODE(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
        entity.setSTARTTIME(cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16));
        entity.setENDTIME(cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17));
        entity.setDISCOUNT_STATUS(cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18));
     }
    
    @Override
    protected final String updateKeyAfterInsert(TfDiscountRecord entity, long rowId) {
        return entity.getSEQNO();
    }
    
    @Override
    public String getKey(TfDiscountRecord entity) {
        if(entity != null) {
            return entity.getSEQNO();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(TfDiscountRecord entity) {
        return entity.getSEQNO() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}