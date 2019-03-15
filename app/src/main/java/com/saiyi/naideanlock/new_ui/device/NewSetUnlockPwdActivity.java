package com.saiyi.naideanlock.new_ui.device;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.saiyi.naideanlock.R;
import com.saiyi.naideanlock.bean.MdlDevice;
import com.saiyi.naideanlock.new_ui.device.mvp.p.SetUnlockPwdActivityPresenter;
import com.saiyi.naideanlock.new_ui.device.mvp.v.SetUnlockPwdActivityView;
import com.sandy.guoguo.babylib.constant.BabyExtraConstant;
import com.sandy.guoguo.babylib.constant.BabyHttpConstant;
import com.sandy.guoguo.babylib.entity.MdlBaseHttpResp;
import com.sandy.guoguo.babylib.listener.OnMultiClickListener;
import com.sandy.guoguo.babylib.ui.MVPBaseActivity;
import com.sandy.guoguo.babylib.utils.LogAndToastUtil;
import com.sandy.guoguo.babylib.utils.RegexUtil;
import com.sandy.guoguo.babylib.utils.ResourceUtil;
import com.sandy.guoguo.babylib.utils.Utility;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/6/11.
 */

public class NewSetUnlockPwdActivity extends MVPBaseActivity<SetUnlockPwdActivityView, SetUnlockPwdActivityPresenter> implements SetUnlockPwdActivityView {
    private EditText etOldPwd, etPwd, etConfirmPwd;
    private MdlDevice mdlDevice;


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_new_set_unlock_pwd;
    }

    @Override
    protected int getTitleResId() {
        return R.string.unlocking_set;
    }

    @Override
    protected void initViewAndControl() {
        initNav();
        etOldPwd = findView(R.id.etOldPwd);

        etPwd = findView(R.id.etPwd);

        etConfirmPwd = findView(R.id.etConfirmPwd);

        mdlDevice = getIntent().getParcelableExtra(BabyExtraConstant.EXTRA_ITEM);

        findView(R.id.btnConfirm).setOnClickListener(new OnMultiClickListener() {
            @Override
            public void OnMultiClick(View view) {
                clickConfirm();
            }
        });

    }

    private void clickConfirm() {
        sendNewPwd2Remote();
    }


    private void initNav() {
        TextView tvLeft = findView(R.id.toolbarLeft);
        tvLeft.setVisibility(View.VISIBLE);
        ResourceUtil.setCompoundDrawable(tvLeft, R.drawable.dr_ic_back, 0, 0, 0);

    }

    @Override
    protected SetUnlockPwdActivityPresenter createPresenter() {
        return new SetUnlockPwdActivityPresenter(this);
    }


    private void sendNewPwd2Remote() {

        String oldPwd = Utility.getEditTextStr(etOldPwd);
        String pwd = Utility.getEditTextStr(etPwd);
        String confirmPwd = Utility.getEditTextStr(etConfirmPwd);
        if (!RegexUtil.isPwd(oldPwd) || !RegexUtil.isPwd(pwd) || !RegexUtil.isPwd(confirmPwd)) {
            LogAndToastUtil.toast("密码要不能低于6位");
            return;
        }
//        if (!TextUtils.equals(oldPwd, mdlDevice.pwd)) {
//            LogAndToastUtil.toast("旧密码输入错误");
//            return;
//        }
        if (!TextUtils.equals(pwd, confirmPwd)) {
            LogAndToastUtil.toast("两次输入密码不一致");
            return;
        }


        Map<String, Object> params = new HashMap<>();
        params.put("bindingID", mdlDevice.bindingID);
        params.put("oldPwd", mdlDevice.pwd);
        params.put("pwd", pwd);
        params.put("pwds", confirmPwd);
        presenter.setUnlockPwd(params);
    }


    @Override
    public void showSetUnlockPwdResult(MdlBaseHttpResp resp) {
        if (resp.code == BabyHttpConstant.R_HTTP_OK) {


            String pwd = Utility.getEditTextStr(etPwd);
            mdlDevice.pwd = pwd;
            mdlDevice.lockPwd = pwd;

            Intent intent = new Intent();
            intent.putExtra(BabyExtraConstant.EXTRA_ITEM, mdlDevice);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

}
