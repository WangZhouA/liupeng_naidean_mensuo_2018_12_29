package com.saiyi.naideanlock.ui.remote;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.saiyi.naideanlock.R;
import com.saiyi.naideanlock.base.BaseActivity;
import com.saiyi.naideanlock.topbar.TopToolBar;

/**
 * 远距离控制页面
 */

public class RemoteActivity extends BaseActivity implements View.OnClickListener, RemoteContract.RemoteView {

    private TopToolBar remote_top_bar;
    private ProgressBar remote_pgb_battery;//显示电量的进度条
    private TextView remote_power_tv;//电量提示字
    private TextView remote_lock_name_tv;//门锁名字
    private TextView remote_device_tv;//门锁状态 激活或未激活
    private Button remote_open_btn;//开启摄像头按钮
    private EditText remote_pass_ed;//开锁密码输入框
    private Button remote_refuse_btn;//拒绝开锁按钮
    private Button remote_open_lock_btn;//开锁按钮

    private RemoteContract.RemotePresenter mRemotePresenter;

    @Override
    protected int getContentView() {
        return R.layout.activity_remote;
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        new RemotePresenter(this);
    }

    @Override
    protected void initView() {
        remote_top_bar = (TopToolBar) findViewById(R.id.remote_top_bar);
//        remote_pgb_battery = (ProgressBar) findViewById(R.id.remote_pgb_battery);
        remote_power_tv = (TextView) findViewById(R.id.remote_power_tv);
        remote_lock_name_tv = (TextView) findViewById(R.id.remote_lock_name_tv);
        remote_device_tv = (TextView) findViewById(R.id.remote_device_tv);
        remote_open_btn = (Button) findViewById(R.id.remote_open_btn);
        remote_pass_ed = (EditText) findViewById(R.id.remote_pass_ed);
        remote_refuse_btn = (Button) findViewById(R.id.remote_refuse_btn);
        remote_open_lock_btn = (Button) findViewById(R.id.remote_open_lock_btn);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void topBarSet() {
        remote_top_bar.setRightVisible(View.GONE);
        remote_top_bar.setMiddleText(getString(R.string.remote_unlocking));
    }

    @Override
    protected void topBarListener() {
        remote_top_bar.setOnLeftClickListener(new TopToolBar.TitleOnLeftClickListener() {
            @Override
            public void onLeftClick() {
                finish();
            }
        });
    }

    @Override
    protected void setListener() {
        remote_open_btn.setOnClickListener(this);
        remote_refuse_btn.setOnClickListener(this);
        remote_open_lock_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.remote_open_btn://开启摄像头按钮
                break;
            case R.id.remote_refuse_btn://拒绝按钮
                break;
            case R.id.remote_open_lock_btn://开锁按钮
                break;
            default:
                break;
        }
    }

    @Override
    public void setPresenter(RemoteContract.RemotePresenter presenter) {
        this.mRemotePresenter = presenter;
    }
}
