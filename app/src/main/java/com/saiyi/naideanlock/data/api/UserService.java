package com.saiyi.naideanlock.data.api;

import com.saiyi.naideanlock.bean.MdlHttpRespList;
import com.saiyi.naideanlock.bean.MdlVersion;
import com.sandy.guoguo.babylib.entity.MdlBaseHttpResp;
import com.sandy.guoguo.babylib.entity.MdlUser;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;

public interface UserService {
    @GET("latch-web/latch_app/queryUser")
    Observable<MdlBaseHttpResp<MdlUser>> getUserInfo(@QueryMap Map<String, Object> map);
    @GET("latch-web/latch_app/findedition")
    Observable<MdlBaseHttpResp<MdlHttpRespList<MdlVersion>>> getVersionInfo(@QueryMap Map<String, Object> map);


    @Multipart
    @POST("latch-web/latch_app/addHeadPicture")
    Observable<MdlBaseHttpResp<String>> uploadHeadPic(@Part("number") RequestBody phone, @Part MultipartBody.Part file);

    @GET("latch-web/latch_app/updateUser")
    Observable<MdlBaseHttpResp> updateUserInfo(@QueryMap Map<String, Object> map);

    @POST("latch-web/latch_app/checkNumber")
    Observable<MdlBaseHttpResp> changeBindCheckPhone(@Body RequestBody map);

    @POST("latch-web/latch_app/updateNumber")
    Observable<MdlBaseHttpResp> changeBindUpdatePhone(@Body RequestBody map);
}
