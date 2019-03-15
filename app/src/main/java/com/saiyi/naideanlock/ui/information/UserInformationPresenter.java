package com.saiyi.naideanlock.ui.information;

/**
 * 描述：
 * 创建作者：ask
 * 创建时间：2017/9/30 11:43
 * 登录业务逻辑处理
 */

public class UserInformationPresenter implements UserInformationContract.UserInformationPresenter {

    private UserInformationContract.UserInformationView mUserInformationView;

    public UserInformationPresenter(UserInformationContract.UserInformationView mUserInformationView) {
        this.mUserInformationView = mUserInformationView;
        mUserInformationView.setPresenter(this);
    }


    @Override
    public void appUpdate() {

    }
}
