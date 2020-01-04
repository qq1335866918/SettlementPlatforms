package com.tt1000.settlementplatform.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/10/19.
 * by:TaoHui
 */

public class DBUtils {
    private static DBUtils dbUtils;
    private SQLiteDatabase db;

    /**
     * 单例模式
     *
     * @return
     */
    public static DBUtils getInstance() {
        if (dbUtils == null) {
            dbUtils = new DBUtils();
            return dbUtils;
        }
        return dbUtils;
    }

    /**
     * 创建数据表
     *
     * @param contenxt 上下文对象
     */
    public void creads(Context contenxt) {
        String path = contenxt.getCacheDir().getPath() + "/local_db.db";
        db = SQLiteDatabase.openOrCreateDatabase(path, null);
//        String sql = "create table if not exists t_person" +
//                "(id integer primary key autoincrement," +
//                "bsid int(50) ,name text(50))";
//        db.execSQL(sql);//创建表
    }

    /**
     * 查询数据
     * 返回List
     */
    public ArrayList<JSONObject> selectis() {
        ArrayList<JSONObject> list = new ArrayList<JSONObject>();
        Cursor cursor = db.query("tf_mealtimes", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            JSONObject userBean = new JSONObject();
//            String name = cursor.getString(cursor.getColumnIndex("name"));
//            int bsid = cursor.getInt(cursor.getColumnIndex("bsid"));
//            userBean.setName(name);
//            userBean.setBsid(bsid);
            try {
                userBean.put("MI_ID",cursor.getColumnIndex("MI_ID"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            list.add(userBean);
//            Log.e("--Main--", "==============selectis======" + id + "================" + name + "================" + bsid);
        }
        if (cursor != null) {
            cursor.close();
        }

        return list;
    }

    /**
     * 根据ID删除数据
     * id 删除id
     */
    public int delData(int id) {
        int inde = db.delete("t_person", "id = ?", new String[]{String.valueOf(id)});
        Log.e("--Main--", "==============删除了======================" + inde);
        return inde;
    }

    /**
     * 根据ID修改数据
     * id 修改条码的id
     * bsid 修改的ID
     * name 修改的数据库
     */
    public int modifyData(int id, int bsid, String name) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("bsid", id);
        int index = db.update("t_person", contentValues, "id = ?", new String[]{String.valueOf(id)});
        Log.e("--Main--", "==============修改了======================" + index);
        return index;
    }

    /**
     * 添加数据
     * bsid 添加的数据ID
     * name 添加数据名称
     */
    public long insertData(int bsid, String name) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("bsid", bsid);
        long dataSize = db.insert("t_person", null, contentValues);
        Log.e("--Main--", "==============insertData======================" + name + "================" + bsid);
        return dataSize;
    }

    /**
     * 查询名字单个数据
     *
     * @param names
     * @return
     */
    public boolean selectisData(String names) {
        //查询数据库
        Cursor cursor = db.query("t_person", null, "name = ?", new String[]{names}, null, null, null);
        while (cursor.moveToNext()) {
            return true;
        }
        return false;

    }
}