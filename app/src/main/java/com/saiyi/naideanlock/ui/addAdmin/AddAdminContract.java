package com.saiyi.naideanlock.ui.addAdmin;

import android.content.Context;

import com.saiyi.naideanlock.base.BasePresenter;
import com.saiyi.naideanlock.base.BaseView;
import com.saiyi.naideanlock.bean.AddMemberBean;

import java.util.List;

/**
 * 描述：
 * 创建作者：Fanjianchang
 * 创建时间：2017/9/30 11:45
 */

public interface AddAdminContract {
    interface AddAdminView extends BaseView<AddAdminPresenter> {
        void GainMailList(List<AddMemberBean> list);
    }


    interface AddAdminPresenter extends BasePresenter {
        void mailList(Context context);
    }
}
