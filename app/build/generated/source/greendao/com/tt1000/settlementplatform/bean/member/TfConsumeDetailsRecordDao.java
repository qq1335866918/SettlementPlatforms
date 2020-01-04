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
 * DAO for table "TF_CONSUME_DETAILS_RECORD".
*/
public class TfConsumeDetailsRecordDao extends AbstractDao<TfConsumeDetailsRecord, String> {

    public static final String TABLENAME = "TF_CONSUME_DETAILS_RECORD";

    /**
     * Properties of entity TfConsumeDetailsRecord.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property CDR_ID = new Property(0, String.class, "CDR_ID", true, "CDR_ID");
        public final static Property COR_ID = new Property(1, String.class, "COR_ID", false, "COR_ID");
        public final static Property CREATETIME = new Property(2, String.class, "CREATETIME", false, "CREATETIME");
        public final static Property CDR_UNIT_PRICE = new Property(3, String.class, "CDR_UNIT_PRICE", false, "CDR_UNIT_PRICE");
        public final static Property CDR_NUMBER = new Property(4, String.class, "CDR_NUMBER", false, "CDR_NUMBER");
        public final static Property CDR_MONEY = new Property(5, String.class, "CDR_MONEY", false, "CDR_MONEY");
        public final static Property CDR_NO = new Property(6, String.class, "CDR_NO", false, "CDR_NO");
        public final static Property CDR_TYPE = new Property(7, String.class, "CDR_TYPE", false, "CDR_TYPE");
        public final static Property ISM_STATUS = new Property(8, String.class, "ISM_STATUS", false, "ISM_STATUS");
        public final static Property STORE_CODE = new Property(9, String.class, "STORE_CODE", false, "STORE_CODE");
        public final static Property CLIENT_CODE = new Property(10, String.class, "CLIENT_CODE", false, "CLIENT_CODE");
        public final static Property REMARKS = new Property(11, String.class, "REMARKS", false, "REMARKS");
        public final static Property DROP_STATUS = new Property(12, String.class, "DROP_STATUS", false, "DROP_STATUS");
        public final static Property CHIP_ID = new Property(13, String.class, "CHIP_ID", false, "CHIP_ID");
    }


    public TfConsumeDetailsRecordDao(DaoConfig config) {
        super(config);
    }
    
    public TfConsumeDetailsRecordDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"TF_CONSUME_DETAILS_RECORD\" (" + //
                "\"CDR_ID\" TEXT PRIMARY KEY NOT NULL UNIQUE ," + // 0: CDR_ID
                "\"COR_ID\" TEXT," + // 1: COR_ID
                "\"CREATETIME\" TEXT," + // 2: CREATETIME
                "\"CDR_UNIT_PRICE\" TEXT," + // 3: CDR_UNIT_PRICE
                "\"CDR_NUMBER\" TEXT," + // 4: CDR_NUMBER
                "\"CDR_MONEY\" TEXT," + // 5: CDR_MONEY
                "\"CDR_NO\" TEXT," + // 6: CDR_NO
                "\"CDR_TYPE\" TEXT," + // 7: CDR_TYPE
                "\"ISM_STATUS\" TEXT," + // 8: ISM_STATUS
                "\"STORE_CODE\" TEXT," + // 9: STORE_CODE
                "\"CLIENT_CODE\" TEXT," + // 10: CLIENT_CODE
                "\"REMARKS\" TEXT," + // 11: REMARKS
                "\"DROP_STATUS\" TEXT," + // 12: DROP_STATUS
                "\"CHIP_ID\" TEXT);"); // 13: CHIP_ID
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"TF_CONSUME_DETAILS_RECORD\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, TfConsumeDetailsRecord entity) {
        stmt.clearBindings();
 
        String CDR_ID = entity.getCDR_ID();
        if (CDR_ID != null) {
            stmt.bindString(1, CDR_ID);
        }
 
        String COR_ID = entity.getCOR_ID();
        if (COR_ID != null) {
            stmt.bindString(2, COR_ID);
        }
 
        String CREATETIME = entity.getCREATETIME();
        if (CREATETIME != null) {
            stmt.bindString(3, CREATETIME);
        }
 
        String CDR_UNIT_PRICE = entity.getCDR_UNIT_PRICE();
        if (CDR_UNIT_PRICE != null) {
            stmt.bindString(4, CDR_UNIT_PRICE);
        }
 
        String CDR_NUMBER = entity.getCDR_NUMBER();
        if (CDR_NUMBER != null) {
            stmt.bindString(5, CDR_NUMBER);
        }
 
        String CDR_MONEY = entity.getCDR_MONEY();
        if (CDR_MONEY != null) {
            stmt.bindString(6, CDR_MONEY);
        }
 
        String CDR_NO = entity.getCDR_NO();
        if (CDR_NO != null) {
            stmt.bindString(7, CDR_NO);
        }
 
        String CDR_TYPE = entity.getCDR_TYPE();
        if (CDR_TYPE != null) {
            stmt.bindString(8, CDR_TYPE);
        }
 
        String ISM_STATUS = entity.getISM_STATUS();
        if (ISM_STATUS != null) {
            stmt.bindString(9, ISM_STATUS);
        }
 
        String STORE_CODE = entity.getSTORE_CODE();
        if (STORE_CODE != null) {
            stmt.bindString(10, STORE_CODE);
        }
 
        String CLIENT_CODE = entity.getCLIENT_CODE();
        if (CLIENT_CODE != null) {
            stmt.bindString(11, CLIENT_CODE);
        }
 
        String REMARKS = entity.getREMARKS();
        if (REMARKS != null) {
            stmt.bindString(12, REMARKS);
        }
 
        String DROP_STATUS = entity.getDROP_STATUS();
        if (DROP_STATUS != null) {
            stmt.bindString(13, DROP_STATUS);
        }
 
        String CHIP_ID = entity.getCHIP_ID();
        if (CHIP_ID != null) {
            stmt.bindString(14, CHIP_ID);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, TfConsumeDetailsRecord entity) {
        stmt.clearBindings();
 
        String CDR_ID = entity.getCDR_ID();
        if (CDR_ID != null) {
            stmt.bindString(1, CDR_ID);
        }
 
        String COR_ID = entity.getCOR_ID();
        if (COR_ID != null) {
            stmt.bindString(2, COR_ID);
        }
 
        String CREATETIME = entity.getCREATETIME();
        if (CREATETIME != null) {
            stmt.bindString(3, CREATETIME);
        }
 
        String CDR_UNIT_PRICE = entity.getCDR_UNIT_PRICE();
        if (CDR_UNIT_PRICE != null) {
            stmt.bindString(4, CDR_UNIT_PRICE);
        }
 
        String CDR_NUMBER = entity.getCDR_NUMBER();
        if (CDR_NUMBER != null) {
            stmt.bindString(5, CDR_NUMBER);
        }
 
        String CDR_MONEY = entity.getCDR_MONEY();
        if (CDR_MONEY != null) {
            stmt.bindString(6, CDR_MONEY);
        }
 
        String CDR_NO = entity.getCDR_NO();
        if (CDR_NO != null) {
            stmt.bindString(7, CDR_NO);
        }
 
        String CDR_TYPE = entity.getCDR_TYPE();
        if (CDR_TYPE != null) {
            stmt.bindString(8, CDR_TYPE);
        }
 
        String ISM_STATUS = entity.getISM_STATUS();
        if (ISM_STATUS != null) {
            stmt.bindString(9, ISM_STATUS);
        }
 
        String STORE_CODE = entity.getSTORE_CODE();
        if (STORE_CODE != null) {
            stmt.bindString(10, STORE_CODE);
        }
 
        String CLIENT_CODE = entity.getCLIENT_CODE();
        if (CLIENT_CODE != null) {
            stmt.bindString(11, CLIENT_CODE);
        }
 
        String REMARKS = entity.getREMARKS();
        if (REMARKS != null) {
            stmt.bindString(12, REMARKS);
        }
 
        String DROP_STATUS = entity.getDROP_STATUS();
        if (DROP_STATUS != null) {
            stmt.bindString(13, DROP_STATUS);
        }
 
        String CHIP_ID = entity.getCHIP_ID();
        if (CHIP_ID != null) {
            stmt.bindString(14, CHIP_ID);
        }
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public TfConsumeDetailsRecord readEntity(Cursor cursor, int offset) {
        TfConsumeDetailsRecord entity = new TfConsumeDetailsRecord( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // CDR_ID
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // COR_ID
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // CREATETIME
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // CDR_UNIT_PRICE
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // CDR_NUMBER
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // CDR_MONEY
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // CDR_NO
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // CDR_TYPE
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // ISM_STATUS
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // STORE_CODE
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // CLIENT_CODE
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // REMARKS
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // DROP_STATUS
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13) // CHIP_ID
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, TfConsumeDetailsRecord entity, int offset) {
        entity.setCDR_ID(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setCOR_ID(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setCREATETIME(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setCDR_UNIT_PRICE(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setCDR_NUMBER(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setCDR_MONEY(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setCDR_NO(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setCDR_TYPE(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setISM_STATUS(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setSTORE_CODE(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setCLIENT_CODE(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setREMARKS(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setDROP_STATUS(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setCHIP_ID(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
     }
    
    @Override
    protected final String updateKeyAfterInsert(TfConsumeDetailsRecord entity, long rowId) {
        return entity.getCDR_ID();
    }
    
    @Override
    public String getKey(TfConsumeDetailsRecord entity) {
        if(entity != null) {
            return entity.getCDR_ID();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(TfConsumeDetailsRecord entity) {
        return entity.getCDR_ID() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
