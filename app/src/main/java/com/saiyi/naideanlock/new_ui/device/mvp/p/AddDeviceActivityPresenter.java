package com.saiyi.naideanlock.new_ui.device.mvp.p;

import com.saiyi.naideanlock.new_ui.device.mvp.m.AddDeviceActivityModel;
import com.saiyi.naideanlock.new_ui.device.mvp.v.AddDeviceActivityView;
import com.sandy.guoguo.babylib.entity.MdlBaseHttpResp;
import com.sandy.guoguo.babylib.ui.mvp.BaseModel;
import com.sandy.guoguo.babylib.ui.mvp.BasePresenter;

import java.util.Map;

/**
 * Created by Administrator on 2018/4/18.
 */

public class AddDeviceActivityPresenter extends BasePresenter<AddDeviceActivityView> {
    private static final int REQ_BIND = 1;

    private AddDeviceActivityModel model;

    public AddDeviceActivityPresenter(AddDeviceActivityView view) {
        this.view = view;
        this.model = new AddDeviceActivityModel();
    }

    public void bindDevice(Map param){
        if(model != null){
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
        switch (code){
            case REQ_BIND:
                view.showAddDeviceResult(resp);
                break;
        }
    }
}
