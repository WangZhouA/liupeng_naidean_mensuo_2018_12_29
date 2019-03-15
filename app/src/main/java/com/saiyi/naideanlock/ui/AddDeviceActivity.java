package com.saiyi.naideanlock.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.saiyi.naideanlock.R;
import com.saiyi.naideanlock.adapter.BleMacAdapter;
import com.saiyi.naideanlock.base.BaseActivity;
import com.saiyi.naideanlock.bean.BleAddressBean;
import com.saiyi.naideanlock.topbar.TopToolBar;
import com.saiyi.naideanlock.utils.BluetoothUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索蓝牙 并添加设备页面
 */
public class AddDeviceActivity extends BaseActivity implements View.OnClickListener {

    private TopToolBar add_device_top_bar;
    private ImageView add_ble_device_iv;//添加动画
    private TextView add_ble_device_cancel_tv;//取消添加按钮
    private RecyclerView add_ble_device_list;//扫描到的蓝牙设备
    private List<BleAddressBean> bleMacList;//蓝牙扫描结果

    private BleMacAdapter mBleMacAdapter;//适配器

    @Override
    protected int getContentView() {
        return R.layout.activity_add_device;
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {

    }

    @Override
    protected void initView() {
        add_device_top_bar = (TopToolBar) findViewById(R.id.add_device_top_bar);
        add_ble_device_iv = (ImageView) findViewById(R.id.add_ble_device_iv);
        add_ble_device_cancel_tv = (TextView) findViewById(R.id.add_ble_device_cancel_tv);
        add_ble_device_list = (RecyclerView) findViewById(R.id.add_ble_device_list);
    }

    @Override
    protected void initData() {
        bleMacList = new ArrayList<>();
        startAnim();
        BluetoothUtil.getInstance(this).setBleListener();
        bleMacList = BluetoothUtil.getInstance(this).getSearchResult();

        mBleMacAdapter = new BleMacAdapter(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        add_ble_device_list.setLayoutManager(manager);
        mBleMacAdapter.setListData(bleMacList);
        add_ble_device_list.setAdapter(mBleMacAdapter);
    }

    @Override
    protected void topBarSet() {
        add_device_top_bar.setRightVisible(View.GONE);
        add_device_top_bar.setMiddleText(getString(R.string.device_add));
    }

    @Override
    protected void topBarListener() {
        add_device_top_bar.setOnLeftClickListener(new TopToolBar.TitleOnLeftClickListener() {
            @Override
            public void onLeftClick() {
                finish();
            }
        });
    }

    @Override
    protected void setListener() {
        add_ble_device_cancel_tv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        stopAnim();
        BluetoothUtil.getInstance(this).stopSearch();
    }

    /**
     * 旋转动画
     */
    private void startAnim() {
        Animation circle_anim = AnimationUtils.loadAnimation(this, R.anim.anim_round_rotate);
        LinearInterpolator interpolator = new LinearInterpolator();  //设置匀速旋转，在xml文件中设置会出现卡顿
        circle_anim.setInterpolator(interpolator);
        if (circle_anim != null) {
            add_ble_device_iv.startAnimation(circle_anim);  //开始动画
        }
    }

    /**
     * 停止动画
     */
    private void stopAnim() {
        add_ble_device_iv.clearAnimation();
    }
}
