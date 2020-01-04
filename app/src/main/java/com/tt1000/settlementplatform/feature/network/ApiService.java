package com.tt1000.settlementplatform.feature.network;

import com.tt1000.settlementplatform.bean.BaseBean;
import com.tt1000.settlementplatform.bean.TotalNum;
import com.tt1000.settlementplatform.bean.member.SyncTableRecord;
import com.tt1000.settlementplatform.bean.member.TfStoreRecord;
import com.tt1000.settlementplatform.bean.pay.PayResultInfo;
import com.tt1000.settlementplatform.bean.result_info.RegMachineResultInfo;
import com.tt1000.settlementplatform.bean.member.SyncResultInfo;
import com.tt1000.settlementplatform.bean.result_info.UpdateResultInfo;
import com.tt1000.settlementplatform.bean.member.TfCardInfo;
import com.tt1000.settlementplatform.bean.member.CommodityRecord;
import com.tt1000.settlementplatform.bean.member.CommodityTypeRecord;
import com.tt1000.settlementplatform.bean.member.TfDiscountRecord;
import com.tt1000.settlementplatform.bean.member.TfMealTimes;
import com.tt1000.settlementplatform.bean.member.TfMemberAccountRecord;
import com.tt1000.settlementplatform.bean.member.TfMemberInfo;
import com.tt1000.settlementplatform.bean.member.MemberTypeRecord;
import com.tt1000.settlementplatform.bean.member.TfPrintTask;
import com.tt1000.settlementplatform.bean.member.StoreConfig;
import com.tt1000.settlementplatform.bean.member.TfUserInfo;
import com.tt1000.settlementplatform.bean.result_info.QueryCardResultInfo;
import com.tt1000.settlementplatform.bean.result_info.VerifyMachineResultInfo;

import java.util.Map;

import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

public interface ApiService {
    /**
     *  timestamp   时间戳(必须)
     *  client_code 客户代码(必须)
     *  store_code  店铺代码(必须)
     *  api         是否需要最大更新时间(必须)(为1的话需要传最大更新时间)
     *  tableName   需要更新的表的名称(必须)
     *  pageNo      要同步数据的条数
     *  updateTime  最大更新时间(当api为1时是必须的)
     * @return
     */
    @FormUrlEncoded()
    @Headers("token:123")
    @POST("k-occ/sync/table?tableName=commodity_record")
    Observable<SyncResultInfo<CommodityRecord>> syncCommodityRecordData(@FieldMap Map<String, Object> map);

    @FormUrlEncoded()
    @Headers("token:123")
    @POST("k-occ/sync/table?tableName=commodity_type_record")
    Observable<SyncResultInfo<CommodityTypeRecord>> syncCommodityTypeRecordData(@FieldMap Map<String, Object> map);

    @FormUrlEncoded()
    @Headers("token:123")
    @POST("k-occ/sync/table?tableName=member_type_record")
    Observable<SyncResultInfo<MemberTypeRecord >> syncMemberTypeRecordData(@FieldMap Map<String, Object> map);

    @FormUrlEncoded()
    @Headers("token:123")
    @POST("k-occ/sync/table?tableName=store_config")
    Observable<SyncResultInfo<StoreConfig>> syncStoreConfigData(@FieldMap Map<String, Object> map);

    @FormUrlEncoded()
    @Headers("token:123")
    @POST("k-occ/sync/table?tableName=tf_cardinfo")
    Observable<SyncResultInfo<TfCardInfo>> syncTfCardInfoData(@FieldMap Map<String, Object> map);

    @FormUrlEncoded()
    @Headers("token:123")
    @POST("k-occ/sync/table?tableName=tf_discount_record")
    Observable<SyncResultInfo<TfDiscountRecord>> syncTfDiscountRecordData(@FieldMap Map<String, Object> map);

    @FormUrlEncoded()
    @Headers("token:123")
    @POST("k-occ/sync/table?tableName=tf_mealtimes")
    Observable<SyncResultInfo<TfMealTimes>> syncTfMealTimesData(@FieldMap Map<String, Object> map);

    @FormUrlEncoded()
    @Headers("token:123")
    @POST("k-occ/sync/table?tableName=tf_member_account_record")
    Observable<SyncResultInfo<TfMemberAccountRecord>> syncTfMemberAccountRecordData(@FieldMap Map<String, Object> map);

    @FormUrlEncoded()
    @Headers("token:123")
    @POST("k-occ/sync/table?tableName=tf_memberinfo")
    Observable<SyncResultInfo<TfMemberInfo>> syncTfMemberInfoData(@FieldMap Map<String, Object> map);

    @FormUrlEncoded()
    @Headers("token:123")
    @POST("k-occ/sync/table?tableName=tf_print_task")
    Observable<SyncResultInfo<TfPrintTask>> syncTfPrintTaskData(@FieldMap Map<String, Object> map);

    @FormUrlEncoded()
    @Headers("token:123")
    @POST("k-occ/sync/table?tableName=tf_userinfo")
    Observable<SyncResultInfo<TfUserInfo>> syncTfUserInfoData(@FieldMap Map<String, Object> map);

    @FormUrlEncoded()
    @Headers("token:123")
    @POST("k-occ/sync/table?tableName=tf_store_record")
    Observable<SyncResultInfo<TfStoreRecord>> syncTfStoreRecordData(@FieldMap Map<String, Object> map);

    @FormUrlEncoded()
    @Headers("token:123")
    @POST("k-occ/sync/table?tableName=sync_table_record")
    Observable<SyncResultInfo<SyncTableRecord>> syncTableRecord(@FieldMap Map<String, Object> map);

    @FormUrlEncoded()
    @Headers("token:123")
    @POST("/k-occ/sync/check")
    Observable<TotalNum> getTableTotalNum(@FieldMap Map<String, Object> map);

    /**
     * 支付接口
     * @param auth_code 判断支付类型，支付宝（28开头）还是微信（13开头）
     * @param transamt 支付金额
     * @return
     */
    @FormUrlEncoded()
    @POST("k-occ/pay/reverse/scan")
    Observable<PayResultInfo> postPayInfo(@Field("data") String data);

    /**
     * 离线状态下的更新
     */
    @FormUrlEncoded()
    @POST("k-occ/consume/inline")
    Observable<BaseBean<Object>> postUpdateInline(@Field("data") String data);

    /**
     * 线上更新
     */
    @FormUrlEncoded()
    @POST("k-occ/consume/online")
    Observable<UpdateResultInfo> postUpdateOnline(@Field("data") String data);

    /**
     * 注册机器号
     * @param machineCode
     * @param mac
     * @return
     */
    @POST("k-crm/machine/bind")
    Observable<RegMachineResultInfo> registerMachine(@Query("machineCode") String machineCode,
                                                     @Query("mac") String mac);

    /**
     * 指定链接的注册机器号方法
     * @param crm_addr
     * @param machineCode
     * @param mac
     * @return
     */
    @POST()
    Observable<RegMachineResultInfo> registerMachine(@Url String crm_addr,
                                                     @Query("machineCode") String machineCode,
                                                     @Query("mac") String mac);

    @GET()
    Observable<QueryCardResultInfo> queryCardInfo(@Url String url);

    /**
     * 验证机器日期
     * @param machineNo 机器号
     * @return
     */
    @GET("k-occ//machineManager/details/{machineNo}")
    Observable<BaseBean<VerifyMachineResultInfo>> verifyMachineDate(@Path("machineNo")String machineNo);

    /**
     * 心跳接口
     * @return
     */
    @POST("k-occ/sync/heart")
    Observable<Object> obtainHeart();
}
