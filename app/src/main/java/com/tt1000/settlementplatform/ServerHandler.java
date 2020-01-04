package com.tt1000.settlementplatform;

import android.database.Cursor;
import android.util.Log;

import com.google.gson.Gson;
import com.tt1000.settlementplatform.app.MyApplication;
import com.tt1000.settlementplatform.bean.TcpSocketBean;
import com.tt1000.settlementplatform.bean.member.DaoSession;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ServerHandler extends IoHandlerAdapter {
    DaoSession sessions = MyApplication.getInstance();

    // 从端口接受消息，会响应此方法来对消息进行处理
    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        super.messageReceived(session, message);
        String data = "";
        String msg = message.toString();
        if (!msg.isEmpty() || msg != null) {
            try {
                Gson gson = new Gson();
                TcpSocketBean tcpSocketBean = gson.fromJson(msg, TcpSocketBean.class);
                String method = tcpSocketBean.getMethod();
                String sqls = tcpSocketBean.getSqls();
                Cursor cursor = sessions.getDatabase().rawQuery(sqls, null);
//                data = cursor.getCount()+"";
                if (method.equals("query")) {
                    String types = tcpSocketBean.getTypes();
                    if (types.equals("array")) {
                        List<JSONObject> list2 = new ArrayList<JSONObject>(); //数据数组
                        JSONObject jsonObject = null; //数据对象
                        while (cursor.moveToNext()) { //移到下一行
                            String[] str = cursor.getColumnNames();
                            jsonObject = new JSONObject();
                            for (int i = 0; i < str.length; i++) {
                                String col = str[i];
                                String val = cursor.getString(i);
                                jsonObject.put(col, val);
                            }
                            list2.add(jsonObject);
                        }
                        data = list2.toString();
                    }
                    if (types.equals("count")) {
                        if (cursor.moveToNext()) { //移到下一行
                            data = cursor.getString(0);
                        }
                    }
                    if (types.equals("single")) {
                        if (cursor.getCount() > 0) {
                            List<JSONObject> list2 = new ArrayList<JSONObject>(); //数据数组
                            JSONObject jsonObject = null; //数据对象
                            while (cursor.moveToNext()) { //移到下一行
                                String[] str = cursor.getColumnNames();
                                jsonObject = new JSONObject();
                                for (int i = 0; i < str.length; i++) {
                                    String col = str[i];
                                    String val = cursor.getString(i);
                                    jsonObject.put(col, val);
                                }
                                list2.add(jsonObject);
                            }
                            System.out.print(list2.toString());
                            data = list2.toString();
                        }
                    }
                }
                if (method.equals("delete")) {
                    if (!cursor.moveToFirst()) {
                        data = "true";
                    } else {
                        data = "false";
                    }

                }
            } catch (Exception e) {
                Log.e("frost", "socket:::" + e.getMessage());
            }
        }
//         向客户端发送消息
        session.write(data);
        System.out.println("服务器接受消息成功..." + data);
    }

    // 向客服端发送消息后会调用此方法
    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        super.messageSent(session, message);
//      session.close(true);//加上这句话实现短连接的效果，向客户端成功发送数据后断开连接
        System.out.println("服务器发送消息成功...");
        sessions.clear();
    }

    // 关闭与客户端的连接时会调用此方法
    @Override
    public void sessionClosed(IoSession session) throws Exception {
        super.sessionClosed(session);
        System.out.println("服务器与客户端断开连接...");
        sessions.clear();
    }

    // 服务器与客户端创建连接
    @Override
    public void sessionCreated(IoSession session) throws Exception {
        super.sessionCreated(session);
        System.out.println("服务器与客户端创建连接...");
    }

    // 服务器与客户端连接打开
    @Override
    public void sessionOpened(IoSession session) throws Exception {
        System.out.println("服务器与客户端连接打开...");
        super.sessionOpened(session);
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        super.sessionIdle(session, status);
        System.out.println("服务器进入空闲状态...");
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        super.exceptionCaught(session, cause);
        System.out.println("服务器发送异常...");
        session.close();
        sessions.clear();
    }

}