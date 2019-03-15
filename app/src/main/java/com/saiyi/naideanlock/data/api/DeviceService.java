package com.saiyi.naideanlock.data.api;


import com.saiyi.naideanlock.bean.MdlDevice;
import com.saiyi.naideanlock.bean.MdlHttpRespList;
import com.saiyi.naideanlock.bean.MdlPhoto;
import com.saiyi.naideanlock.bean.MdlUnlockRecord;
import com.sandy.guoguo.babylib.entity.MdlBaseHttpResp;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by Administrator on 2018/6/11.
 */

public interface DeviceService {

    @GET("latch-web/latch_app/getDeviceAll")
    Observable<MdlBaseHttpResp<MdlHttpRespList<MdlDevice>>> getAllDeviceByType(@QueryMap Map<String, Object> map);

    @GET("latch-web/latch_app/updateLockName")
    Observable<MdlBaseHttpResp> updateDeviceName(@QueryMap Map<String, Object> map);

    @GET("latch-web/latch_app/cancelBinding")
    Observable<MdlBaseHttpResp> delDeviceBinding(@QueryMap Map<String, Object> map);

    @GET("latch-web/latch_app/insertRecord")
    Observable<MdlBaseHttpResp> addUnlockRecord(@QueryMap Map<String, Object> map);

    @GET("latch-web/latch_app/findPic")
    Observable<MdlBaseHttpResp<MdlHttpRespList<MdlPhoto>>> getPhotoList(@QueryMap Map<String, Object> map);

    /**无人模式报警*/
    @GET("latch-web/latch_app/setNoMode")
    Observable<MdlBaseHttpResp> setUnmannedMode(@QueryMap Map<String, Object> map);
    /**防撬报警*/
    @GET("latch-web/latch_app/setPryMode")
    Observable<MdlBaseHttpResp> setTamperAlert(@QueryMap Map<String, Object> map);
    /**低电提醒*/
    @GET("latch-web/latch_app/setLowMode")
    Observable<MdlBaseHttpResp> setLowPower(@QueryMap Map<String, Object> map);

    /**设置开锁密码*/
    @GET("latch-web/latch_app/setPwd")
    Observable<MdlBaseHttpResp> setUnlockPwd(@QueryMap Map<String, Object> map);

    @GET("latch-web/latch_app/bindingDevice")
    Observable<MdlBaseHttpResp> bindDevice(@QueryMap Map<String, Object> map);

    /**获取开锁记录*/
    @GET("latch-web/latch_app/getRecord")
    Observable<MdlBaseHttpResp<MdlHttpRespList<MdlUnlockRecord>>> getUnlockRecord(@QueryMap Map<String, Object> map);

    /**删除开锁记录*/
    @GET("latch-web/latch_app/deleteRecordAll")
    Observable<MdlBaseHttpResp> deleteAllUnlockRecord(@QueryMap Map<String, Object> map);

    /**权限管理-列表*/
    @GET("latch-web/latch_app/getAuthUsers")
    Observable<MdlBaseHttpResp<MdlHttpRespList<MdlDevice>>> getAuthManagerList(@QueryMap Map<String, Object> map);
    /**权限管理-重命名*/
    @GET("latch-web/latch_app/updateRemark")
    Observable<MdlBaseHttpResp> renameAuthUser(@QueryMap Map<String, Object> map);
    /**权限管理-删除保姆*/
    @POST("latch-web/latch_app/deleteNurseRole")
    Observable<MdlBaseHttpResp> deleteNanny(@Body RequestBody map);
    /**权限管理-添加保姆*/
    @POST("latch-web/latch_app/addNurseRole")
    Observable<MdlBaseHttpResp> addNanny(@Body RequestBody map);
}
