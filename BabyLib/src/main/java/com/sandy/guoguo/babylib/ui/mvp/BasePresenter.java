package com.sandy.guoguo.babylib.ui.mvp;




import android.app.Activity;
import android.support.v4.app.Fragment;

import com.sandy.guoguo.babylib.constant.BabyHttpConstant;
import com.sandy.guoguo.babylib.entity.MdlBaseHttpResp;
import com.sandy.guoguo.babylib.listener.OnLoadHttpDataListener;
import com.sandy.guoguo.babylib.utils.LogAndToastUtil;
import com.sandy.guoguo.babylib.utils.Utility;

import java.lang.ref.WeakReference;

import okhttp3.ResponseBody;

public abstract class BasePresenter<MVP_V extends BaseView> implements OnLoadHttpDataListener<MdlBaseHttpResp> {
    protected WeakReference<MVP_V> mViewRef;

    protected abstract BaseModel getMode();

    protected MVP_V view;

    protected MVP_V getView() {
        return mViewRef.get();
    }

    public void attachView(MVP_V view) {
        mViewRef = new WeakReference<>(view);
    }


    public void detachView() {
        if (view != null) {
            view = null;
        }

        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }

        BaseModel baseModel = getMode();
        if (baseModel != null) {
            baseModel.detachModel();
        }
    }

    protected abstract void responseSuccess(int code, MdlBaseHttpResp resp);

    protected void responseSuccess(int code, ResponseBody resp){}

    @Override
    public void onSuccess(int code, ResponseBody data) {
        if (view != null && data != null) {
            view.hideLoading();
            responseSuccess(code, data);
        }
    }

    @Override
    public void onSuccess(int code, MdlBaseHttpResp resp) {
        if (view != null && resp != null) {
            view.hideLoading();

            if(resp.code == BabyHttpConstant.R_HTTP_OK){//成功
                responseSuccess(code, resp);
            }else if (resp.code == BabyHttpConstant.R_TOKEN_EXPIRE) {//token过期,需要重新登录
                LogAndToastUtil.log("needLogin-----token过期,需要重新登录--------------");
                if (view instanceof Fragment) {
                    Utility.needLogin(((Fragment) view).getActivity());
                } else if (view instanceof Activity) {
                    Utility.needLogin((Activity) view);
                }
            } else {
                LogAndToastUtil.toast(resp.message);
                responseSuccess(code, resp);
            }

        }
    }

    @Override
    public void onFailure(int code, Throwable e) {
        if (view != null) {
            view.hideLoading();
            LogAndToastUtil.toast(e.getMessage());
        }
        LogAndToastUtil.log("--出错了--:%s", e.getMessage());
        try {
            throw new Exception(e);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
