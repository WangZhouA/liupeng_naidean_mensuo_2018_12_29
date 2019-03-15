package com.saiyi.naideanlock.ui.wifibinding;

/**
 * 描述：
 * 创建作者：Fanjianchang
 * 创建时间：2017/9/30 11:43
 * 注册业务逻辑处理
 */

public class BindingPresenter implements BindingContract.BindingPresenter {
    private BindingContract.BindingView mBindingView;

    public BindingPresenter(BindingContract.BindingView mBindingView) {
        this.mBindingView = mBindingView;
        mBindingView.setPresenter(this);
    }
}
