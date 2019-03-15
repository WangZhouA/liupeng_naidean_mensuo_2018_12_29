package com.saiyi.naideanlock.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.saiyi.naideanlock.R;
import com.saiyi.naideanlock.base.BaseActivity;
import com.saiyi.naideanlock.topbar.TopToolBar;
import com.saiyi.naideanlock.ui.wificonnect.WifiConnectActivity;

/**
 * 设备链接方式 蓝牙链接和wifi链接
 */
public class ConnectionModelActivity extends BaseActivity implements View.OnClickListener {

    private TopToolBar model_top_bar;
    private Button connection_wifi_btn;//wifi连接按钮
    private Button connection_ble_btn;//蓝牙连接按钮

    @Override
    protected int getContentView() {
        return R.layout.activity_connection_model;
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {

    }

    @Override
    protected void initView() {
        model_top_bar = (TopToolBar) findViewById(R.id.model_top_bar);

        connection_wifi_btn = (Button) findViewById(R.id.connection_wifi_btn);
        connection_ble_btn = (Button) findViewById(R.id.connection_ble_btn);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void topBarSet() {
        model_top_bar.setMiddleText(getString(R.string.model_choose));
        model_top_bar.setRightVisible(View.GONE);
    }

    @Override
    protected void topBarListener() {
        model_top_bar.setOnLeftClickListener(new TopToolBar.TitleOnLeftClickListener() {
            @Override
            public void onLeftClick() {
                finish();
            }
        });
    }

    @Override
    protected void setListener() {
        connection_wifi_btn.setOnClickListener(this);
        connection_ble_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.connection_wifi_btn://wifi链接
                startActivity(WifiConnectActivity.class);
                break;
            case R.id.connection_ble_btn://蓝牙链接
                break;
            default:
                break;
        }
    }
}
