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
 * DAO for table "TF_MEMBER_ACCOUNT_RECORD".
*/
public class TfMemberAccountRecordDao extends AbstractDao<TfMemberAccountRecord, String> {

    public static final String TABLENAME = "TF_MEMBER_ACCOUNT_RECORD";

    /**
     * Properties of entity TfMemberAccountRecord.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property ACCOUNT_ID = new Property(0, String.class, "ACCOUNT_ID", true, "ACCOUNT_ID");
        public final static Property BALANCE = new Property(1, String.class, "BALANCE", false, "BALANCE");
        public final static Property TOTAL_RECHARGE_MONEY = new Property(2, String.class, "TOTAL_RECHARGE_MONEY", false, "TOTAL_RECHARGE_MONEY");
        public final static Property ACCOUNT_STATUS = new Property(3, String.class, "ACCOUNT_STATUS", false, "ACCOUNT_STATUS");
        public final static Property REFUND_STATUS = new Property(4, String.class, "REFUND_STATUS", false, "REFUND_STATUS");
        public final static Property CREATETIME = new Property(5, long.class, "CREATETIME", false, "CREATETIME");
        public final static Property UPDATETIME = new Property(6, long.class, "UPDATETIME", false, "UPDATETIME");
        public final static Property U_ID = new Property(7, String.class, "U_ID", false, "U_ID");
        public final static Property MI_ID = new Property(8, String.class, "MI_ID", false, "MI_ID");
        public final static Property ACCOUNT_TYPE = new Property(9, String.class, "ACCOUNT_TYPE", false, "ACCOUNT_TYPE");
        public final static Property STORE_CODE = new Property(10, String.class, "STORE_CODE", false, "STORE_CODE");
        public final static Property CLIENT_CODE = new Property(11, String.class, "CLIENT_CODE", false, "CLIENT_CODE");
        public final static Property ISM_STATUS = new Property(12, String.class, "ISM_STATUS", false, "ISM_STATUS");
    }


    public TfMemberAccountRecordDao(DaoConfig config) {
        super(config);
    }
    
    public TfMemberAccountRecordDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"TF_MEMBER_ACCOUNT_RECORD\" (" + //
                "\"ACCOUNT_ID\" TEXT PRIMARY KEY NOT NULL ," + // 0: ACCOUNT_ID
                "\"BALANCE\" TEXT," + // 1: BALANCE
                "\"TOTAL_RECHARGE_MONEY\" TEXT," + // 2: TOTAL_RECHARGE_MONEY
                "\"ACCOUNT_STATUS\" TEXT," + // 3: ACCOUNT_STATUS
                "\"REFUND_STATUS\" TEXT," + // 4: REFUND_STATUS
                "\"CREATETIME\" INTEGER NOT NULL ," + // 5: CREATETIME
                "\"UPDATETIME\" INTEGER NOT NULL ," + // 6: UPDATETIME
                "\"U_ID\" TEXT," + // 7: U_ID
                "\"MI_ID\" TEXT," + // 8: MI_ID
                "\"ACCOUNT_TYPE\" TEXT," + // 9: ACCOUNT_TYPE
                "\"STORE_CODE\" TEXT," + // 10: STORE_CODE
                "\"CLIENT_CODE\" TEXT," + // 11: CLIENT_CODE
                "\"ISM_STATUS\" TEXT);"); // 12: ISM_STATUS
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"TF_MEMBER_ACCOUNT_RECORD\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, TfMemberAccountRecord entity) {
        stmt.clearBindings();
 
        String ACCOUNT_ID = entity.getACCOUNT_ID();
        if (ACCOUNT_ID != null) {
            stmt.bindString(1, ACCOUNT_ID);
        }
 
        String BALANCE = entity.getBALANCE();
        if (BALANCE != null) {
            stmt.bindString(2, BALANCE);
        }
 
        String TOTAL_RECHARGE_MONEY = entity.getTOTAL_RECHARGE_MONEY();
        if (TOTAL_RECHARGE_MONEY != null) {
            stmt.bindString(3, TOTAL_RECHARGE_MONEY);
        }
 
        String ACCOUNT_STATUS = entity.getACCOUNT_STATUS();
        if (ACCOUNT_STATUS != null) {
            stmt.bindString(4, ACCOUNT_STATUS);
        }
 
        String REFUND_STATUS = entity.getREFUND_STATUS();
        if (REFUND_STATUS != null) {
            stmt.bindString(5, REFUND_STATUS);
        }
        stmt.bindLong(6, entity.getCREATETIME());
        stmt.bindLong(7, entity.getUPDATETIME());
 
        String U_ID = entity.getU_ID();
        if (U_ID != null) {
            stmt.bindString(8, U_ID);
        }
 
        String MI_ID = entity.getMI_ID();
        if (MI_ID != null) {
            stmt.bindString(9, MI_ID);
        }
 
        String ACCOUNT_TYPE = entity.getACCOUNT_TYPE();
        if (ACCOUNT_TYPE != null) {
            stmt.bindString(10, ACCOUNT_TYPE);
        }
 
        String STORE_CODE = entity.getSTORE_CODE();
        if (STORE_CODE != null) {
            stmt.bindString(11, STORE_CODE);
        }
 
        String CLIENT_CODE = entity.getCLIENT_CODE();
        if (CLIENT_CODE != null) {
            stmt.bindString(12, CLIENT_CODE);
        }
 
        String ISM_STATUS = entity.getISM_STATUS();
        if (ISM_STATUS != null) {
            stmt.bindString(13, ISM_STATUS);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, TfMemberAccountRecord entity) {
        stmt.clearBindings();
 
        String ACCOUNT_ID = entity.getACCOUNT_ID();
        if (ACCOUNT_ID != null) {
            stmt.bindString(1, ACCOUNT_ID);
        }
 
        String BALANCE = entity.getBALANCE();
        if (BALANCE != null) {
            stmt.bindString(2, BALANCE);
        }
 
        String TOTAL_RECHARGE_MONEY = entity.getTOTAL_RECHARGE_MONEY();
        if (TOTAL_RECHARGE_MONEY != null) {
            stmt.bindString(3, TOTAL_RECHARGE_MONEY);
        }
 
        String ACCOUNT_STATUS = entity.getACCOUNT_STATUS();
        if (ACCOUNT_STATUS != null) {
            stmt.bindString(4, ACCOUNT_STATUS);
        }
 
        String REFUND_STATUS = entity.getREFUND_STATUS();
        if (REFUND_STATUS != null) {
            stmt.bindString(5, REFUND_STATUS);
        }
        stmt.bindLong(6, entity.getCREATETIME());
        stmt.bindLong(7, entity.getUPDATETIME());
 
        String U_ID = entity.getU_ID();
        if (U_ID != null) {
            stmt.bindString(8, U_ID);
        }
 
        String MI_ID = entity.getMI_ID();
        if (MI_ID != null) {
            stmt.bindString(9, MI_ID);
        }
 
        String ACCOUNT_TYPE = entity.getACCOUNT_TYPE();
        if (ACCOUNT_TYPE != null) {
            stmt.bindString(10, ACCOUNT_TYPE);
        }
 
        String STORE_CODE = entity.getSTORE_CODE();
        if (STORE_CODE != null) {
            stmt.bindString(11, STORE_CODE);
        }
 
        String CLIENT_CODE = entity.getCLIENT_CODE();
        if (CLIENT_CODE != null) {
            stmt.bindString(12, CLIENT_CODE);
        }
 
        String ISM_STATUS = entity.getISM_STATUS();
        if (ISM_STATUS != null) {
            stmt.bindString(13, ISM_STATUS);
        }
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public TfMemberAccountRecord readEntity(Cursor cursor, int offset) {
        TfMemberAccountRecord entity = new TfMemberAccountRecord( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // ACCOUNT_ID
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // BALANCE
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // TOTAL_RECHARGE_MONEY
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // ACCOUNT_STATUS
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // REFUND_STATUS
            cursor.getLong(offset + 5), // CREATETIME
            cursor.getLong(offset + 6), // UPDATETIME
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // U_ID
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // MI_ID
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // ACCOUNT_TYPE
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // STORE_CODE
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // CLIENT_CODE
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12) // ISM_STATUS
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, TfMemberAccountRecord entity, int offset) {
        entity.setACCOUNT_ID(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setBALANCE(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setTOTAL_RECHARGE_MONEY(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setACCOUNT_STATUS(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setREFUND_STATUS(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setCREATETIME(cursor.getLong(offset + 5));
        entity.setUPDATETIME(cursor.getLong(offset + 6));
        entity.setU_ID(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setMI_ID(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setACCOUNT_TYPE(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setSTORE_CODE(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setCLIENT_CODE(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setISM_STATUS(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
     }
    
    @Override
    protected final String updateKeyAfterInsert(TfMemberAccountRecord entity, long rowId) {
        return entity.getACCOUNT_ID();
    }
    
    @Override
    public String getKey(TfMemberAccountRecord entity) {
        if(entity != null) {
            return entity.getACCOUNT_ID();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(TfMemberAccountRecord entity) {
        return entity.getACCOUNT_ID() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
