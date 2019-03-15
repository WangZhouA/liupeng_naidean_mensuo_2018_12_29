package com.saiyi.naideanlock.ui.remote;

/**
 * 描述：
 * 创建作者：Fanjianchang
 * 创建时间：2017/9/30 11:43
 * 注册业务逻辑处理
 */

public class RemotePresenter implements RemoteContract.RemotePresenter {

    private RemoteContract.RemoteView mRemoteView;

    public RemotePresenter(RemoteContract.RemoteView mRemoteView) {
        this.mRemoteView = mRemoteView;
        mRemoteView.setPresenter(this);
    }
}
