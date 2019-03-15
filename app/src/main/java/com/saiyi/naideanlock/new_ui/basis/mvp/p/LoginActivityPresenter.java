package com.saiyi.naideanlock.new_ui.basis.mvp.p;


import com.saiyi.naideanlock.new_ui.basis.mvp.m.LoginActivityModel;
import com.saiyi.naideanlock.new_ui.basis.mvp.v.LoginActivityView;
import com.sandy.guoguo.babylib.entity.MdlBaseHttpResp;
import com.sandy.guoguo.babylib.ui.mvp.BaseModel;
import com.sandy.guoguo.babylib.ui.mvp.BasePresenter;

import java.util.Map;

/**
 * Created by Administrator on 2018/4/18.
 */

public class LoginActivityPresenter extends BasePresenter<LoginActivityView> {
    private LoginActivityModel model;

    public LoginActivityPresenter(LoginActivityView view) {
        this.view = view;
        this.model = new LoginActivityModel();
    }

    public void login(Map param){
        if(model != null){
            view.showLoading();
            model.login(0, param, this);
        }
    }


    @Override
    protected BaseModel getMode() {
        return model;
    }

    @Override
    protected void responseSuccess(int code, MdlBaseHttpResp resp) {
        view.showLoginResult(resp);
    }
}
