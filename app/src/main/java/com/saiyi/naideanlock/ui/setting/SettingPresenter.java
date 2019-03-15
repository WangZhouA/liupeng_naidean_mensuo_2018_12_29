package com.saiyi.naideanlock.ui.setting;

/**
 * 描述：
 * 创建作者：Fanjianchang
 * 创建时间：2017/9/30 11:43
 * 注册业务逻辑处理
 */

public class SettingPresenter implements SettingContract.SettingPresenter {

    private SettingContract.SettingView mSettingView;

    public SettingPresenter(SettingContract.SettingView mSettingView) {
        this.mSettingView = mSettingView;
        mSettingView.setPresenter(this);
    }
}
