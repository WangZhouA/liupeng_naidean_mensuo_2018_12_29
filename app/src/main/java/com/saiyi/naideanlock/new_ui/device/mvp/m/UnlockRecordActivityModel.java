package com.saiyi.naideanlock.new_ui.device.mvp.m;


import com.saiyi.naideanlock.data.http.HttpDataDevice;
import com.sandy.guoguo.babylib.entity.MdlBaseHttpResp;
import com.sandy.guoguo.babylib.listener.OnLoadHttpDataListener;
import com.sandy.guoguo.babylib.ui.mvp.BaseModel;

import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2018/6/11.
 */

public class UnlockRecordActivityModel extends BaseModel {
    public void getUnlockRecord(final int code, Map map, final OnLoadHttpDataListener listener) {
        HttpDataDevice.getInstance().getUnlockRecord(map, new Observer<MdlBaseHttpResp>() {
            @Override
            public void onSubscribe(Disposable d) {
                UnlockRecordActivityModel.this.disposable = d;
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
    public void deleteAllUnlockRecord(final int code, Map map, final OnLoadHttpDataListener listener) {
        HttpDataDevice.getInstance().deleteAllUnlockRecord(map, new Observer<MdlBaseHttpResp>() {
            @Override
            public void onSubscribe(Disposable d) {
                UnlockRecordActivityModel.this.disposable = d;
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
