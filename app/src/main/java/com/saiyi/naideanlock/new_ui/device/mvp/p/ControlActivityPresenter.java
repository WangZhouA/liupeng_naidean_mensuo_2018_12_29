package com.saiyi.naideanlock.new_ui.device.mvp.p;


import com.saiyi.naideanlock.new_ui.device.mvp.m.ControlActivityModel;
import com.saiyi.naideanlock.new_ui.device.mvp.v.ControlActivityView;
import com.sandy.guoguo.babylib.entity.MdlBaseHttpResp;
import com.sandy.guoguo.babylib.ui.mvp.BaseModel;
import com.sandy.guoguo.babylib.ui.mvp.BasePresenter;

import java.util.Map;

/**
 * Created by Administrator on 2018/4/18.
 */

public class ControlActivityPresenter extends BasePresenter<ControlActivityView> {
    private static final int REQ_GET_LIST = 1;
    private static final int REQ_DEL = 2;
    private static final int REQ_UPDATE_NAME = 3;
    private static final int REQ_ADD_UNLOCK_RECORD = 4;

    private ControlActivityModel model;

    public ControlActivityPresenter(ControlActivityView view) {
        this.view = view;
        this.model = new ControlActivityModel();
    }

    public void getAllDeviceByType(Map map) {
        if (model != null) {
            view.showLoading();
            model.getAllDeviceByType(REQ_GET_LIST, map, this);
        }
    }

    public void delDeviceBinding(Map map) {
        if (model != null) {
            view.showLoading();
            model.delDeviceBinding(REQ_DEL, map, this);
        }
    }
    public void updateDeviceName(Map map) {
        if (model != null) {
            view.showLoading();
            model.updateDeviceName(REQ_UPDATE_NAME, map, this);
        }
    }
    public void addUnlockRecord(Map map) {
        if (model != null) {
            view.showLoading();
            model.addUnlockRecord(REQ_ADD_UNLOCK_RECORD, map, this);
        }
    }


    @Override
    protected BaseModel getMode() {
        return model;
    }

    @Override
    protected void responseSuccess(int code, MdlBaseHttpResp resp) {
        switch (code) {
            case REQ_GET_LIST:
                view.showDeviceListResult(resp);
                break;
            case REQ_DEL:
                view.showDelDeviceResult(resp);
                break;
            case REQ_UPDATE_NAME:
                view.showUpdateDeviceNameResult(resp);
                break;
            case REQ_ADD_UNLOCK_RECORD:
                view.showAddUnlockRecordResult(resp);
                break;
        }
    }
}
