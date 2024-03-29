package com.saiyi.naideanlock.new_ui.user.mvp.m;


import com.saiyi.naideanlock.data.http.HttpDataUser;
import com.sandy.guoguo.babylib.entity.MdlBaseHttpResp;
import com.sandy.guoguo.babylib.listener.OnLoadHttpDataListener;
import com.sandy.guoguo.babylib.ui.mvp.BaseModel;

import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2018/6/11.
 */

public class UserInfoActivityModel extends BaseModel {
    public void getUserInfo(final int code, Map map, final OnLoadHttpDataListener listener) {
        HttpDataUser.getInstance().getUserInfo(map, new Observer<MdlBaseHttpResp>() {
            @Override
            public void onSubscribe(Disposable d) {
                UserInfoActivityModel.this.disposable = d;
            }

            @Override
            public void onNext(MdlBaseHttpResp mdlBaseHttpResp) {
                listener.onSuccess(code, mdlBaseHttpResp);
            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(code, e);
            }

            @Override
            public void onComplete() {

            }
        });
    }
    public void getVersionInfo(final int code, Map map, final OnLoadHttpDataListener listener) {
        HttpDataUser.getInstance().getVersionInfo(map, new Observer<MdlBaseHttpResp>() {
            @Override
            public void onSubscribe(Disposable d) {
                UserInfoActivityModel.this.disposable = d;
            }

            @Override
            public void onNext(MdlBaseHttpResp mdlBaseHttpResp) {
                listener.onSuccess(code, mdlBaseHttpResp);
            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(code, e);
            }

            @Override
            public void onComplete() {

            }
        });
    }

}
