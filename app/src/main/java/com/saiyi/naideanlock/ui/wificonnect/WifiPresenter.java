package com.saiyi.naideanlock.ui.wificonnect;

/**
 * 描述：
 * 创建作者：Fanjianchang
 * 创建时间：2017/9/30 11:43
 * 登录业务逻辑处理
 */

public class WifiPresenter implements WifiContract.WifiPresenter {

    private WifiContract.WifiView mWifiView;

    public WifiPresenter(WifiContract.WifiView mWifiView) {
        this.mWifiView = mWifiView;
        mWifiView.setPresenter(this);
    }
}
