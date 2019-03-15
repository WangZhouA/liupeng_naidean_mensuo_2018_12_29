package com.saiyi.naideanlock.new_ui.device.mvp.p;


import com.saiyi.naideanlock.new_ui.device.mvp.m.AddAuthManagerActivityModel;
import com.saiyi.naideanlock.new_ui.device.mvp.v.AddAuthManagerActivityView;
import com.sandy.guoguo.babylib.entity.MdlBaseHttpResp;
import com.sandy.guoguo.babylib.ui.mvp.BaseModel;
import com.sandy.guoguo.babylib.ui.mvp.BasePresenter;

import java.util.Map;

/**
 * Created by Administrator on 2018/4/18.
 */

public class AddAuthManagerActivityPresenter extends BasePresenter<AddAuthManagerActivityView> {
    private static final int REQ_GET_USER_INFO = 1;
    private static final int REQ_BIND = 2;

    private AddAuthManagerActivityModel model;

    public AddAuthManagerActivityPresenter(AddAuthManagerActivityView view) {
        this.view = view;
        this.model = new AddAuthManagerActivityModel();
    }

    public void searchUserInfo(Map map) {
        if (model != null) {
            view.showLoading();
            model.searchUserInfo(REQ_GET_USER_INFO, map, this);
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
            case REQ_GET_USER_INFO:
                view.showUserInfoResult(resp);
                break;

            case REQ_BIND:
                view.showAddDeviceResult(resp);
                break;
        }
    }
}
