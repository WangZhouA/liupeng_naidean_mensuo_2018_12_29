package com.saiyi.naideanlock.new_ui.device.mvp.p;

import com.saiyi.naideanlock.new_ui.device.mvp.m.AuthManagerActivityModel;
import com.saiyi.naideanlock.new_ui.device.mvp.v.AuthManagerActivityView;
import com.sandy.guoguo.babylib.entity.MdlBaseHttpResp;
import com.sandy.guoguo.babylib.ui.mvp.BaseModel;
import com.sandy.guoguo.babylib.ui.mvp.BasePresenter;

import java.util.Map;

/**
 * Created by Administrator on 2018/4/18.
 */

public class AuthManagerActivityPresenter extends BasePresenter<AuthManagerActivityView> {
    private static final int REQ_GET_LIST = 1;
    private static final int REQ_RENAME = 2;
    private static final int REQ_DEL_NANNY = 3;
    private static final int REQ_DEL_NO_ADMIN = 4;

    private AuthManagerActivityModel model;

    public AuthManagerActivityPresenter(AuthManagerActivityView view) {
        this.view = view;
        this.model = new AuthManagerActivityModel();
    }

    public void getAuthManagerList(Map param) {
        if (model != null) {
            view.showLoading();
            model.getAuthManagerList(REQ_GET_LIST, param, this);
        }
    }

    public void renameAuthUser(Map param) {
        if (model != null) {
            view.showLoading();
            model.renameAuthUser(REQ_RENAME, param, this);
        }
    }

    public void deleteNanny(Map param) {
        if (model != null) {
            view.showLoading();
            model.deleteNanny(REQ_DEL_NANNY, param, this);
        }
    }

    public void delDeviceBinding(Map param) {
        if (model != null) {
            view.showLoading();
            model.delDeviceBinding(REQ_DEL_NO_ADMIN, param, this);
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
                view.showAuthUserListResult(resp);
                break;

            case REQ_RENAME:
                view.showRenameAuthUserResult(resp);
                break;

            case REQ_DEL_NANNY:
                view.showDelNannyResult(resp);
                break;

            case REQ_DEL_NO_ADMIN:
                view.showDelNoAdminResult(resp);
                break;
        }
    }
}
