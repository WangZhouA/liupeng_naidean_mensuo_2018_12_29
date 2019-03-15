package com.saiyi.naideanlock.ui.setting;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.saiyi.naideanlock.R;
import com.saiyi.naideanlock.base.BaseActivity;
import com.saiyi.naideanlock.topbar.TopToolBar;
import com.saiyi.naideanlock.ui.ResetPasswordActivity;

/**
 * 相关设置页面
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, SettingContract.SettingView {

    private TopToolBar setting_top_bar;
    private CheckBox setting_unmanned_mode_cb;//无人模式报警
    private CheckBox setting_tamper_cb;//防撬报警
    private CheckBox setting_no_power_cb;//低电提醒

    private TextView setting_remote_unlocking_tv;//远程开锁密码
    private TextView setting_unlocking_set_tv;//开锁密码设置

    private SettingContract.SettingPresenter mSettingPresenter;

    @Override
    protected int getContentView() {
        return R.layout.activity_setting;
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        new SettingPresenter(this);
    }

    @Override
    protected void initView() {
        setting_top_bar = (TopToolBar)findViewById(R.id.setting_top_bar);
        setting_unmanned_mode_cb = (CheckBox) findViewById(R.id.setting_unmanned_mode_cb);
        setting_tamper_cb = (CheckBox) findViewById(R.id.setting_tamper_cb);
        setting_no_power_cb = (CheckBox) findViewById(R.id.setting_no_power_cb);

        setting_remote_unlocking_tv = (TextView) findViewById(R.id.setting_remote_unlocking_tv);
        setting_unlocking_set_tv = (TextView) findViewById(R.id.setting_unlocking_set_tv);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void topBarSet() {
        setting_top_bar.setMiddleText(getString(R.string.about_setting));
        setting_top_bar.setRightVisible(View.GONE);
    }

    @Override
    protected void topBarListener() {
        setting_top_bar.setOnLeftClickListener(new TopToolBar.TitleOnLeftClickListener() {
            @Override
            public void onLeftClick() {
                finish();
            }
        });
    }

    @Override
    protected void setListener() {
        setting_unmanned_mode_cb.setOnCheckedChangeListener(this);
        setting_tamper_cb.setOnCheckedChangeListener(this);
        setting_no_power_cb.setOnCheckedChangeListener(this);

        setting_remote_unlocking_tv.setOnClickListener(this);
        setting_unlocking_set_tv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting_remote_unlocking_tv://远程开锁密码
                break;
            case R.id.setting_unlocking_set_tv://开锁密码设置
                startActivity(ResetPasswordActivity.class);
                break;
            default:
                break;
        }

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.setting_unmanned_mode_cb://无人模式报警
                break;
            case R.id.setting_tamper_cb://防撬报警
                break;
            case R.id.setting_no_power_cb://低电提醒
                break;
            default:
                break;
        }
    }

    @Override
    public void setPresenter(SettingContract.SettingPresenter presenter) {
        this.mSettingPresenter = presenter;
    }
}
