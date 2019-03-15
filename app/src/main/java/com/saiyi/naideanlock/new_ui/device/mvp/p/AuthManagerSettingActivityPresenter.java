package com.saiyi.naideanlock.new_ui.device.mvp.p;

import com.saiyi.naideanlock.new_ui.device.mvp.m.AuthManagerSettingActivityModel;
import com.saiyi.naideanlock.new_ui.device.mvp.v.AuthManagerSettingActivityView;
import com.sandy.guoguo.babylib.entity.MdlBaseHttpResp;
import com.sandy.guoguo.babylib.ui.mvp.BaseModel;
import com.sandy.guoguo.babylib.ui.mvp.BasePresenter;

import java.util.Map;

/**
 * Created by Administrator on 2018/4/18.
 */

public class AuthManagerSettingActivityPresenter extends BasePresenter<AuthManagerSettingActivityView> {
    private static final int REQ_BIND = 2;
    private static final int REQ_SET_NANNY = 3;

    private AuthManagerSettingActivityModel model;

    public AuthManagerSettingActivityPresenter(AuthManagerSettingActivityView view) {
        this.view = view;
        this.model = new AuthManagerSettingActivityModel();
    }


    public void addNanny(Map param) {
        if (model != null) {
            view.showLoading();
            model.addNanny(REQ_SET_NANNY, param, this);
        }
    }

    public void bindDevice(Map param) {
        if (model != null) {
            view.showLoading();
            model.bindDevice(REQ_BIND, param, this);
        }
    }

    @Override
    protected BaseModel getMode() {
        return model;
    }

    @Override
    protected void responseSuccess(int code, MdlBaseHttpResp resp) {
        switch (code) {
            case REQ_SET_NANNY:
                view.showSetNannyResult(resp);
                break;
            case REQ_BIND:
                view.showSetNoAdminResult(resp);
                break;
        }
    }
}
