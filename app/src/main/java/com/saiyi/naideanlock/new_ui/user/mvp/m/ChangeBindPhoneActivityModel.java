package com.saiyi.naideanlock.new_ui.user.mvp.m;


import com.saiyi.naideanlock.data.http.HttpDataBasis;
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

public class ChangeBindPhoneActivityModel extends BaseModel {
    public void checkPhone(final int code, Map map, final OnLoadHttpDataListener listener) {
        HttpDataUser.getInstance().changeBindCheckPhone(map, new Observer<MdlBaseHttpResp>() {
            @Override
            public void onSubscribe(Disposable d) {
                ChangeBindPhoneActivityModel.this.disposable = d;
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
    public void updatePhone(final int code, Map map, final OnLoadHttpDataListener listener) {
        HttpDataUser.getInstance().changeBindUpdatePhone(map, new Observer<MdlBaseHttpResp>() {
            @Override
            public void onSubscribe(Disposable d) {
                ChangeBindPhoneActivityModel.this.disposable = d;
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

    public void getCheckCode(final int code, Map param, final OnLoadHttpDataListener listener){
        HttpDataBasis.getInstance().getCheckCode(param, new Observer<MdlBaseHttpResp>() {
            @Override
            public void onSubscribe(Disposable d){ ChangeBindPhoneActivityModel.this.disposable = d;
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
