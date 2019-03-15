package com.saiyi.naideanlock.new_ui.device.mvp.v;


import com.sandy.guoguo.babylib.entity.MdlBaseHttpResp;
import com.sandy.guoguo.babylib.entity.MdlUser;
import com.sandy.guoguo.babylib.ui.mvp.BaseView;

/**
 * Created by Administrator on 2018/4/18.
 */

public interface AddAuthManagerActivityView extends BaseView {
    void showUserInfoResult(MdlBaseHttpResp<MdlUser> resp);
    void showAddDeviceResult(MdlBaseHttpResp resp);
}
