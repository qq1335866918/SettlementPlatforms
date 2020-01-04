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
 * DAO for table "TF_CONSUME_CARD_RECORD".
*/
public class TfConsumeCardRecordDao extends AbstractDao<TfConsumeCardRecord, String> {

    public static final String TABLENAME = "TF_CONSUME_CARD_RECORD";

    /**
     * Properties of entity TfConsumeCardRecord.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property CCR_ID = new Property(0, String.class, "CCR_ID", true, "CCR_ID");
        public final static Property IC_ID = new Property(1, String.class, "IC_ID", false, "IC_ID");
        public final static Property CCR_ORIGINALAMOUNT = new Property(2, String.class, "CCR_ORIGINALAMOUNT", false, "CCR_ORIGINALAMOUNT");
        public final static Property CCR_MONEY = new Property(3, float.class, "CCR_MONEY", false, "CCR_MONEY");
        public final static Property CREATETIME = new Property(4, String.class, "CREATETIME", false, "CREATETIME");
        public final static Property U_ID = new Property(5, String.class, "U_ID", false, "U_ID");
        public final static Property COR_ID = new Property(6, String.class, "COR_ID", false, "COR_ID");
        public final static Property MACHINE_NO = new Property(7, String.class, "MACHINE_NO", false, "MACHINE_NO");
        public final static Property CCR_STATUS = new Property(8, String.class, "CCR_STATUS", false, "CCR_STATUS");
        public final static Property CCR_UPLOAD_STATUS = new Property(9, String.class, "CCR_UPLOAD_STATUS", false, "CCR_UPLOAD_STATUS");
        public final static Property CCR_UPLOAD_TIME = new Property(10, String.class, "CCR_UPLOAD_TIME", false, "CCR_UPLOAD_TIME");
        public final static Property CCR_DATABASE_STATUS = new Property(11, String.class, "CCR_DATABASE_STATUS", false, "CCR_DATABASE_STATUS");
        public final static Property IC_SERIAL_NUMBER = new Property(12, String.class, "IC_SERIAL_NUMBER", false, "IC_SERIAL_NUMBER");
        public final static Property MT_ID = new Property(13, String.class, "MT_ID", false, "MT_ID");
        public final static Property CCR_PAY_TYPE = new Property(14, String.class, "CCR_PAY_TYPE", false, "CCR_PAY_TYPE");
        public final static Property MI_ID = new Property(15, String.class, "MI_ID", false, "MI_ID");
        public final static Property ACCOUNT_ID = new Property(16, String.class, "ACCOUNT_ID", false, "ACCOUNT_ID");
        public final static Property ISM_STATUS = new Property(17, String.class, "ISM_STATUS", false, "ISM_STATUS");
        public final static Property CCR_DATABASE_TIME = new Property(18, String.class, "CCR_DATABASE_TIME", false, "CCR_DATABASE_TIME");
        public final static Property STORE_CODE = new Property(19, String.class, "STORE_CODE", false, "STORE_CODE");
        public final static Property CLIENT_CODE = new Property(20, String.class, "CLIENT_CODE", false, "CLIENT_CODE");
        public final static Property DISCOUNT_MONEY = new Property(21, String.class, "DISCOUNT_MONEY", false, "DISCOUNT_MONEY");
        public final static Property DISCOUNT_REMARK = new Property(22, String.class, "DISCOUNT_REMARK", false, "DISCOUNT_REMARK");
        public final static Property PAY_REMARK = new Property(23, String.class, "PAY_REMARK", false, "PAY_REMARK");
        public final static Property ELAPSEDTIME = new Property(24, long.class, "ELAPSEDTIME", false, "ELAPSEDTIME");
    }


    public TfConsumeCardRecordDao(DaoConfig config) {
        super(config);
    }
    
    public TfConsumeCardRecordDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"TF_CONSUME_CARD_RECORD\" (" + //
                "\"CCR_ID\" TEXT PRIMARY KEY NOT NULL ," + // 0: CCR_ID
                "\"IC_ID\" TEXT," + // 1: IC_ID
                "\"CCR_ORIGINALAMOUNT\" TEXT," + // 2: CCR_ORIGINALAMOUNT
                "\"CCR_MONEY\" REAL NOT NULL ," + // 3: CCR_MONEY
                "\"CREATETIME\" TEXT," + // 4: CREATETIME
                "\"U_ID\" TEXT," + // 5: U_ID
                "\"COR_ID\" TEXT," + // 6: COR_ID
                "\"MACHINE_NO\" TEXT," + // 7: MACHINE_NO
                "\"CCR_STATUS\" TEXT," + // 8: CCR_STATUS
                "\"CCR_UPLOAD_STATUS\" TEXT," + // 9: CCR_UPLOAD_STATUS
                "\"CCR_UPLOAD_TIME\" TEXT," + // 10: CCR_UPLOAD_TIME
                "\"CCR_DATABASE_STATUS\" TEXT," + // 11: CCR_DATABASE_STATUS
                "\"IC_SERIAL_NUMBER\" TEXT," + // 12: IC_SERIAL_NUMBER
                "\"MT_ID\" TEXT," + // 13: MT_ID
                "\"CCR_PAY_TYPE\" TEXT," + // 14: CCR_PAY_TYPE
                "\"MI_ID\" TEXT," + // 15: MI_ID
                "\"ACCOUNT_ID\" TEXT," + // 16: ACCOUNT_ID
                "\"ISM_STATUS\" TEXT," + // 17: ISM_STATUS
                "\"CCR_DATABASE_TIME\" TEXT," + // 18: CCR_DATABASE_TIME
                "\"STORE_CODE\" TEXT," + // 19: STORE_CODE
                "\"CLIENT_CODE\" TEXT," + // 20: CLIENT_CODE
                "\"DISCOUNT_MONEY\" TEXT," + // 21: DISCOUNT_MONEY
                "\"DISCOUNT_REMARK\" TEXT," + // 22: DISCOUNT_REMARK
                "\"PAY_REMARK\" TEXT," + // 23: PAY_REMARK
                "\"ELAPSEDTIME\" INTEGER NOT NULL );"); // 24: ELAPSEDTIME
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"TF_CONSUME_CARD_RECORD\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, TfConsumeCardRecord entity) {
        stmt.clearBindings();
 
        String CCR_ID = entity.getCCR_ID();
        if (CCR_ID != null) {
            stmt.bindString(1, CCR_ID);
        }
 
        String IC_ID = entity.getIC_ID();
        if (IC_ID != null) {
            stmt.bindString(2, IC_ID);
        }
 
        String CCR_ORIGINALAMOUNT = entity.getCCR_ORIGINALAMOUNT();
        if (CCR_ORIGINALAMOUNT != null) {
            stmt.bindString(3, CCR_ORIGINALAMOUNT);
        }
        stmt.bindDouble(4, entity.getCCR_MONEY());
 
        String CREATETIME = entity.getCREATETIME();
        if (CREATETIME != null) {
            stmt.bindString(5, CREATETIME);
        }
 
        String U_ID = entity.getU_ID();
        if (U_ID != null) {
            stmt.bindString(6, U_ID);
        }
 
        String COR_ID = entity.getCOR_ID();
        if (COR_ID != null) {
            stmt.bindString(7, COR_ID);
        }
 
        String MACHINE_NO = entity.getMACHINE_NO();
        if (MACHINE_NO != null) {
            stmt.bindString(8, MACHINE_NO);
        }
 
        String CCR_STATUS = entity.getCCR_STATUS();
        if (CCR_STATUS != null) {
            stmt.bindString(9, CCR_STATUS);
        }
 
        String CCR_UPLOAD_STATUS = entity.getCCR_UPLOAD_STATUS();
        if (CCR_UPLOAD_STATUS != null) {
            stmt.bindString(10, CCR_UPLOAD_STATUS);
        }
 
        String CCR_UPLOAD_TIME = entity.getCCR_UPLOAD_TIME();
        if (CCR_UPLOAD_TIME != null) {
            stmt.bindString(11, CCR_UPLOAD_TIME);
        }
 
        String CCR_DATABASE_STATUS = entity.getCCR_DATABASE_STATUS();
        if (CCR_DATABASE_STATUS != null) {
            stmt.bindString(12, CCR_DATABASE_STATUS);
        }
 
        String IC_SERIAL_NUMBER = entity.getIC_SERIAL_NUMBER();
        if (IC_SERIAL_NUMBER != null) {
            stmt.bindString(13, IC_SERIAL_NUMBER);
        }
 
        String MT_ID = entity.getMT_ID();
        if (MT_ID != null) {
            stmt.bindString(14, MT_ID);
        }
 
        String CCR_PAY_TYPE = entity.getCCR_PAY_TYPE();
        if (CCR_PAY_TYPE != null) {
            stmt.bindString(15, CCR_PAY_TYPE);
        }
 
        String MI_ID = entity.getMI_ID();
        if (MI_ID != null) {
            stmt.bindString(16, MI_ID);
        }
 
        String ACCOUNT_ID = entity.getACCOUNT_ID();
        if (ACCOUNT_ID != null) {
            stmt.bindString(17, ACCOUNT_ID);
        }
 
        String ISM_STATUS = entity.getISM_STATUS();
        if (ISM_STATUS != null) {
            stmt.bindString(18, ISM_STATUS);
        }
 
        String CCR_DATABASE_TIME = entity.getCCR_DATABASE_TIME();
        if (CCR_DATABASE_TIME != null) {
            stmt.bindString(19, CCR_DATABASE_TIME);
        }
 
        String STORE_CODE = entity.getSTORE_CODE();
        if (STORE_CODE != null) {
            stmt.bindString(20, STORE_CODE);
        }
 
        String CLIENT_CODE = entity.getCLIENT_CODE();
        if (CLIENT_CODE != null) {
            stmt.bindString(21, CLIENT_CODE);
        }
 
        String DISCOUNT_MONEY = entity.getDISCOUNT_MONEY();
        if (DISCOUNT_MONEY != null) {
            stmt.bindString(22, DISCOUNT_MONEY);
        }
 
        String DISCOUNT_REMARK = entity.getDISCOUNT_REMARK();
        if (DISCOUNT_REMARK != null) {
            stmt.bindString(23, DISCOUNT_REMARK);
        }
 
        String PAY_REMARK = entity.getPAY_REMARK();
        if (PAY_REMARK != null) {
            stmt.bindString(24, PAY_REMARK);
        }
        stmt.bindLong(25, entity.getELAPSEDTIME());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, TfConsumeCardRecord entity) {
        stmt.clearBindings();
 
        String CCR_ID = entity.getCCR_ID();
        if (CCR_ID != null) {
            stmt.bindString(1, CCR_ID);
        }
 
        String IC_ID = entity.getIC_ID();
        if (IC_ID != null) {
            stmt.bindString(2, IC_ID);
        }
 
        String CCR_ORIGINALAMOUNT = entity.getCCR_ORIGINALAMOUNT();
        if (CCR_ORIGINALAMOUNT != null) {
            stmt.bindString(3, CCR_ORIGINALAMOUNT);
        }
        stmt.bindDouble(4, entity.getCCR_MONEY());
 
        String CREATETIME = entity.getCREATETIME();
        if (CREATETIME != null) {
            stmt.bindString(5, CREATETIME);
        }
 
        String U_ID = entity.getU_ID();
        if (U_ID != null) {
            stmt.bindString(6, U_ID);
        }
 
        String COR_ID = entity.getCOR_ID();
        if (COR_ID != null) {
            stmt.bindString(7, COR_ID);
        }
 
        String MACHINE_NO = entity.getMACHINE_NO();
        if (MACHINE_NO != null) {
            stmt.bindString(8, MACHINE_NO);
        }
 
        String CCR_STATUS = entity.getCCR_STATUS();
        if (CCR_STATUS != null) {
            stmt.bindString(9, CCR_STATUS);
        }
 
        String CCR_UPLOAD_STATUS = entity.getCCR_UPLOAD_STATUS();
        if (CCR_UPLOAD_STATUS != null) {
            stmt.bindString(10, CCR_UPLOAD_STATUS);
        }
 
        String CCR_UPLOAD_TIME = entity.getCCR_UPLOAD_TIME();
        if (CCR_UPLOAD_TIME != null) {
            stmt.bindString(11, CCR_UPLOAD_TIME);
        }
 
        String CCR_DATABASE_STATUS = entity.getCCR_DATABASE_STATUS();
        if (CCR_DATABASE_STATUS != null) {
            stmt.bindString(12, CCR_DATABASE_STATUS);
        }
 
        String IC_SERIAL_NUMBER = entity.getIC_SERIAL_NUMBER();
        if (IC_SERIAL_NUMBER != null) {
            stmt.bindString(13, IC_SERIAL_NUMBER);
        }
 
        String MT_ID = entity.getMT_ID();
        if (MT_ID != null) {
            stmt.bindString(14, MT_ID);
        }
 
        String CCR_PAY_TYPE = entity.getCCR_PAY_TYPE();
        if (CCR_PAY_TYPE != null) {
            stmt.bindString(15, CCR_PAY_TYPE);
        }
 
        String MI_ID = entity.getMI_ID();
        if (MI_ID != null) {
            stmt.bindString(16, MI_ID);
        }
 
        String ACCOUNT_ID = entity.getACCOUNT_ID();
        if (ACCOUNT_ID != null) {
            stmt.bindString(17, ACCOUNT_ID);
        }
 
        String ISM_STATUS = entity.getISM_STATUS();
        if (ISM_STATUS != null) {
            stmt.bindString(18, ISM_STATUS);
        }
 
        String CCR_DATABASE_TIME = entity.getCCR_DATABASE_TIME();
        if (CCR_DATABASE_TIME != null) {
            stmt.bindString(19, CCR_DATABASE_TIME);
        }
 
        String STORE_CODE = entity.getSTORE_CODE();
        if (STORE_CODE != null) {
            stmt.bindString(20, STORE_CODE);
        }
 
        String CLIENT_CODE = entity.getCLIENT_CODE();
        if (CLIENT_CODE != null) {
            stmt.bindString(21, CLIENT_CODE);
        }
 
        String DISCOUNT_MONEY = entity.getDISCOUNT_MONEY();
        if (DISCOUNT_MONEY != null) {
            stmt.bindString(22, DISCOUNT_MONEY);
        }
 
        String DISCOUNT_REMARK = entity.getDISCOUNT_REMARK();
        if (DISCOUNT_REMARK != null) {
            stmt.bindString(23, DISCOUNT_REMARK);
        }
 
        String PAY_REMARK = entity.getPAY_REMARK();
        if (PAY_REMARK != null) {
            stmt.bindString(24, PAY_REMARK);
        }
        stmt.bindLong(25, entity.getELAPSEDTIME());
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public TfConsumeCardRecord readEntity(Cursor cursor, int offset) {
        TfConsumeCardRecord entity = new TfConsumeCardRecord( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // CCR_ID
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // IC_ID
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // CCR_ORIGINALAMOUNT
            cursor.getFloat(offset + 3), // CCR_MONEY
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // CREATETIME
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // U_ID
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // COR_ID
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // MACHINE_NO
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // CCR_STATUS
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // CCR_UPLOAD_STATUS
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // CCR_UPLOAD_TIME
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // CCR_DATABASE_STATUS
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // IC_SERIAL_NUMBER
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // MT_ID
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // CCR_PAY_TYPE
            cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15), // MI_ID
            cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16), // ACCOUNT_ID
            cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17), // ISM_STATUS
            cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18), // CCR_DATABASE_TIME
            cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19), // STORE_CODE
            cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20), // CLIENT_CODE
            cursor.isNull(offset + 21) ? null : cursor.getString(offset + 21), // DISCOUNT_MONEY
            cursor.isNull(offset + 22) ? null : cursor.getString(offset + 22), // DISCOUNT_REMARK
            cursor.isNull(offset + 23) ? null : cursor.getString(offset + 23), // PAY_REMARK
            cursor.getLong(offset + 24) // ELAPSEDTIME
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, TfConsumeCardRecord entity, int offset) {
        entity.setCCR_ID(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setIC_ID(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setCCR_ORIGINALAMOUNT(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setCCR_MONEY(cursor.getFloat(offset + 3));
        entity.setCREATETIME(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setU_ID(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setCOR_ID(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setMACHINE_NO(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setCCR_STATUS(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setCCR_UPLOAD_STATUS(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setCCR_UPLOAD_TIME(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setCCR_DATABASE_STATUS(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setIC_SERIAL_NUMBER(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setMT_ID(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setCCR_PAY_TYPE(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setMI_ID(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
        entity.setACCOUNT_ID(cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16));
        entity.setISM_STATUS(cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17));
        entity.setCCR_DATABASE_TIME(cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18));
        entity.setSTORE_CODE(cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19));
        entity.setCLIENT_CODE(cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20));
        entity.setDISCOUNT_MONEY(cursor.isNull(offset + 21) ? null : cursor.getString(offset + 21));
        entity.setDISCOUNT_REMARK(cursor.isNull(offset + 22) ? null : cursor.getString(offset + 22));
        entity.setPAY_REMARK(cursor.isNull(offset + 23) ? null : cursor.getString(offset + 23));
        entity.setELAPSEDTIME(cursor.getLong(offset + 24));
     }
    
    @Override
    protected final String updateKeyAfterInsert(TfConsumeCardRecord entity, long rowId) {
        return entity.getCCR_ID();
    }
    
    @Override
    public String getKey(TfConsumeCardRecord entity) {
        if(entity != null) {
            return entity.getCCR_ID();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(TfConsumeCardRecord entity) {
        return entity.getCCR_ID() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
