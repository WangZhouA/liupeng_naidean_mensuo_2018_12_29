package com.saiyi.naideanlock.data.http;


import com.saiyi.naideanlock.data.api.UserService;
import com.sandy.guoguo.babylib.entity.MdlBaseHttpResp;
import com.sandy.guoguo.babylib.http.BaseHttpData;
import com.sandy.guoguo.babylib.http.HttpManager;

import java.io.File;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2018/4/17.
 */

public class HttpDataUser extends BaseHttpData {
    private static class SingletonHolder {
        private static final HttpDataUser INSTANCE = new HttpDataUser();
    }

    public static HttpDataUser getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private UserService userService = HttpManager.getInstance().getRetrofit().create(UserService.class);


    public void getUserInfo(Map params, Observer<MdlBaseHttpResp> observer) {
        Observable observable = userService.getUserInfo(params);
        setSubscribe(observable, observer);
    }
    public void getVersionInfo(Map params, Observer<MdlBaseHttpResp> observer) {
        Observable observable = userService.getVersionInfo(params);
        setSubscribe(observable, observer);
    }
    public void updateUserInfo(Map params, Observer<MdlBaseHttpResp> observer) {
        Observable observable = userService.updateUserInfo(params);
        setSubscribe(observable, observer);
    }

    public void uploadHeadPic(String number, String picPath, Observer<MdlBaseHttpResp> observer){
        File file = new File(picPath);
        RequestBody reqFile = RequestBody.create(MediaType.parse("image/png"), file);
        MultipartBody.Part photo = MultipartBody.Part.createFormData("file", file.getName(), reqFile);//pic为key
        RequestBody reqType = RequestBody.create(MediaType.parse("text/plain"), number);


        Observable observable = userService.uploadHeadPic(reqType, photo);
        setSubscribe(observable, observer);
    }

    public void changeBindCheckPhone(Map params, Observer<MdlBaseHttpResp> observer) {
        RequestBody body = GenJsonParamRequestBody(params);
        Observable observable = userService.changeBindCheckPhone(body);
        setSubscribe(observable, observer);
    }
    public void changeBindUpdatePhone(Map params, Observer<MdlBaseHttpResp> observer) {
        RequestBody body = GenJsonParamRequestBody(params);
        Observable observable = userService.changeBindUpdatePhone(body);
        setSubscribe(observable, observer);
    }
}
