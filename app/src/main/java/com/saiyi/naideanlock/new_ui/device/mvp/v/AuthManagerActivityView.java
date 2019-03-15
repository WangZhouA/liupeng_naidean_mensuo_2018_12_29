package com.saiyi.naideanlock.new_ui.device.mvp.v;


import com.saiyi.naideanlock.bean.MdlDevice;
import com.saiyi.naideanlock.bean.MdlHttpRespList;
import com.sandy.guoguo.babylib.entity.MdlBaseHttpResp;
import com.sandy.guoguo.babylib.ui.mvp.BaseView;

/**
 * Created by Administrator on 2018/4/18.
 */

public interface AuthManagerActivityView extends BaseView {
    void showAuthUserListResult(MdlBaseHttpResp<MdlHttpRespList<MdlDevice>> resp);
    void showRenameAuthUserResult(MdlBaseHttpResp resp);
    void showDelNannyResult(MdlBaseHttpResp resp);
    void showDelNoAdminResult(MdlBaseHttpResp resp);
}
