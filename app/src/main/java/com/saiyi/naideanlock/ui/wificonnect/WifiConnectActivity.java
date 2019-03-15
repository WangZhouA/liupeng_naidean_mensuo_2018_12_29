package com.saiyi.naideanlock.ui.wificonnect;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.makeramen.roundedimageview.RoundedImageView;
import com.saiyi.naideanlock.R;
import com.saiyi.naideanlock.base.BaseActivity;
import com.saiyi.naideanlock.topbar.TopToolBar;
import com.saiyi.naideanlock.ui.wifibinding.WifiBindingActivity;

/**
 * wifi选择连接界面
 */

public class WifiConnectActivity extends BaseActivity implements WifiContract.WifiView, View.OnClickListener {

    private TopToolBar connect_top_bar;
    private RoundedImageView wifi_lock_iv;//门锁头像

    private EditText wifi_name_ed;//wifi名
    private EditText wifi_pass_world_ed;//wifi密码
    private Button wifi_connect_bt;//连接按钮

    private WifiContract.WifiPresenter mWifiPresenter;

    @Override
    protected int getContentView() {
        return R.layout.activity_wifi_connect;
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        new WifiPresenter(this);
    }

    @Override
    protected void initView() {
        connect_top_bar = (TopToolBar) findViewById(R.id.connect_top_bar);
        wifi_lock_iv = (RoundedImageView) findViewById(R.id.wifi_lock_iv);
        wifi_name_ed = (EditText) findViewById(R.id.wifi_name_ed);
        wifi_pass_world_ed = (EditText) findViewById(R.id.wifi_pass_world_ed);
        wifi_connect_bt = (Button) findViewById(R.id.wifi_connect_bt);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void topBarSet() {
        connect_top_bar.setRightVisible(View.GONE);
        connect_top_bar.setMiddleText(getString(R.string.wifi_connect_top));
    }

    @Override
    protected void topBarListener() {
        connect_top_bar.setOnLeftClickListener(new TopToolBar.TitleOnLeftClickListener() {
            @Override
            public void onLeftClick() {
                finish();
            }
        });
    }

    @Override
    protected void setListener() {
        wifi_connect_bt.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {  //点击连接wifi按钮监听
        startActivity(WifiBindingActivity.class);
    }

    @Override
    public void setPresenter(WifiContract.WifiPresenter presenter) {
        this.mWifiPresenter = presenter;
    }
}
