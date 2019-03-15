package com.saiyi.naideanlock.data.http;


import com.saiyi.naideanlock.data.api.BasisService;
import com.sandy.guoguo.babylib.entity.MdlBaseHttpResp;
import com.sandy.guoguo.babylib.http.BaseHttpData;
import com.sandy.guoguo.babylib.http.HttpManager;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;

/**
 * Created by Administrator on 2018/4/17.
 */

public class HttpDataBasis extends BaseHttpData {
    private static class SingletonHolder {
        private static final HttpDataBasis INSTANCE = new HttpDataBasis();
    }

    public static HttpDataBasis getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private BasisService basisService = HttpManager.getInstance().getRetrofit().create(BasisService.class);


    public void register(Map<String, Object> params, Observer<MdlBaseHttpResp> observer) {
        Observable observable = basisService.register(params);
        setSubscribe(observable, observer);
    }

    public void getCheckCode(Map<String, Object> params, Observer<MdlBaseHttpResp> observer) {
        Observable observable = basisService.getCheckCode(params);
        setSubscribe(observable, observer);
    }

    public void login(Map<String, Object> params, Observer<MdlBaseHttpResp> observer) {
        Observable observable = basisService.login(params);
        setSubscribe(observable, observer);
    }

    public void findPwd(Map<String, Object> params, Observer<MdlBaseHttpResp> observer) {
        Observable observable = basisService.findPwd(params);
        setSubscribe(observable, observer);
    }

}
