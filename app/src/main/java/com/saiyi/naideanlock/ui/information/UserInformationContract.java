package com.saiyi.naideanlock.ui.information;

import com.saiyi.naideanlock.base.BasePresenter;
import com.saiyi.naideanlock.base.BaseView;

/**
 * 描述：
 * 创建作者：Fanjianchang
 * 创建时间：2017/9/30 11:45
 */

public interface UserInformationContract {
    interface UserInformationView extends BaseView<UserInformationPresenter> {
    }


    interface UserInformationPresenter extends BasePresenter {
        /**
         * app检查更新
         */
        void appUpdate();

    }
}
