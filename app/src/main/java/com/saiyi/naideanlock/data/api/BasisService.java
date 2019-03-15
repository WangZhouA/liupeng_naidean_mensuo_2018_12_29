package com.saiyi.naideanlock.data.api;


import com.sandy.guoguo.babylib.entity.MdlBaseHttpResp;
import com.sandy.guoguo.babylib.entity.MdlUser;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by Administrator on 2018/6/11.
 */

public interface BasisService {

    @GET("latch-web/latch_app/register")
    Observable<MdlBaseHttpResp> register(@QueryMap Map<String, Object> map);

    @GET("latch-web/latch_app/getIdentify")
    Observable<MdlBaseHttpResp> getCheckCode(@QueryMap Map<String, Object> map);

    @GET("latch-web/latch_app/login")
    Observable<MdlBaseHttpResp<MdlUser>> login(@QueryMap Map<String, Object> map);

    @GET("latch-web/latch_app/findBack")
    Observable<MdlBaseHttpResp> findPwd(@QueryMap Map<String, Object> map);


}
