package com.saiyi.naideanlock.ui.control;

/**
 * 描述：
 * 创建作者：Fanjianchang
 * 创建时间：2017/9/30 11:43
 * 注册业务逻辑处理
 */

public class ControlPresenter implements ControlContract.ControlPresenter {

    private ControlContract.ControlView mControlView;

    public ControlPresenter(ControlContract.ControlView mControlView) {
        this.mControlView = mControlView;
        mControlView.setPresenter(this);
    }

}
