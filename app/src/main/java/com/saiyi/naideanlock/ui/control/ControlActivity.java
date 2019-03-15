package com.saiyi.naideanlock.ui.control;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.saiyi.naideanlock.R;
import com.saiyi.naideanlock.adapter.DeviceAdapter;
import com.saiyi.naideanlock.adapter.base.AbsBaseAdapter;
import com.saiyi.naideanlock.base.BaseActivity;
import com.saiyi.naideanlock.bean.DeviceBean;
import com.saiyi.naideanlock.topbar.TopToolBar;
import com.saiyi.naideanlock.ui.AddDeviceActivity;
import com.saiyi.naideanlock.ui.AuthorizationActivity;
import com.saiyi.naideanlock.ui.ConnectionModelActivity;
import com.saiyi.naideanlock.ui.UnlockingRecordActivity;
import com.saiyi.naideanlock.ui.information.UserInformationActivity;
import com.saiyi.naideanlock.ui.remote.RemoteActivity;
import com.saiyi.naideanlock.ui.setting.SettingActivity;
import com.sandy.guoguo.babylib.utils.Utility;

import java.util.ArrayList;
import java.util.List;

/**
 * 设备控制面板
 */
public class ControlActivity extends BaseActivity implements ControlContract.ControlView, View.OnClickListener {

    private TopToolBar control_top_bar;//顶部导航栏
    private ProgressBar pgb_battery;  //显示电量的进度条
    private TextView control_power_tv;  //数字显示电量
    private ImageView lock;//门锁显示
    private TextView control_lock_name_tv;  //门锁名字
    private TextView control_no_device_tv;  //没有绑定设备提示
    private TextView control_add_device_tv;  //添加设备提示语

    private TextView control_camera_tv;  //摄像头相关
    private TextView control_administration_tv;  //授权管理
    private TextView control_open_lock_tv;  //远程开锁
    private TextView control_set_tv;  //相关设置
    private TextView control_record_tv;  //开锁记录
    private TextView control_information_tv;  //用户信息

    private ControlContract.ControlPresenter mControlPresenter;

    private RecyclerView pop_device_rv;//popwindow中设备列表
    private TextView pop_add_device_tv;//popwindow中点击添加设备按钮
    private PopupWindow mPopupWindow;//设备列表弹窗
    private DeviceAdapter mDeviceAdapter;//设备列表适配器
    private List<DeviceBean> deviceList;//设备list

    @Override
    protected int getContentView() {
        return R.layout.activity_control;
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        new ControlPresenter(this);
    }

    @Override
    protected void initView() {
        control_top_bar = (TopToolBar) findViewById(R.id.control_top_bar);
//        pgb_battery = (ProgressBar) findViewById(R.id.pgb_battery);
        control_power_tv = (TextView) findViewById(R.id.control_power_tv);
        lock = (ImageView) findViewById(R.id.lock);
        control_lock_name_tv = (TextView) findViewById(R.id.control_lock_name_tv);
        control_no_device_tv = (TextView) findViewById(R.id.control_no_device_tv);
        control_add_device_tv = (TextView) findViewById(R.id.control_add_device_tv);


        control_camera_tv = (TextView) findViewById(R.id.control_camera_tv);
        control_administration_tv = (TextView) findViewById(R.id.control_administration_tv);
        control_open_lock_tv = (TextView) findViewById(R.id.control_open_lock_tv);
        control_set_tv = (TextView) findViewById(R.id.control_set_tv);
        control_record_tv = (TextView) findViewById(R.id.control_record_tv);
        control_information_tv = (TextView) findViewById(R.id.control_information_tv);
    }

    @Override
    protected void initData() {
        deviceList = new ArrayList<>();

        mDeviceAdapter = new DeviceAdapter(this);

        for (int i = 0; i < 50; i++) {
            DeviceBean deviceBean = new DeviceBean();
            deviceBean.setDeviceName("设备" + i);
            deviceList.add(deviceBean);
        }
    }

    @Override
    protected void topBarSet() {
//        control_top_bar.setLeftBackground();
        control_top_bar.setMiddleText(getString(R.string.control_panel));
        control_top_bar.setRightVisible(View.GONE);
    }

    @Override
    protected void topBarListener() {
        control_top_bar.setOnLeftClickListener(new TopToolBar.TitleOnLeftClickListener() {
            @Override
            public void onLeftClick() {
                showPopWindow(control_top_bar);
            }
        });
    }

    @Override
    protected void setListener() {
        control_camera_tv.setOnClickListener(this);
        control_administration_tv.setOnClickListener(this);
        control_open_lock_tv.setOnClickListener(this);
        control_set_tv.setOnClickListener(this);
        control_record_tv.setOnClickListener(this);
        control_information_tv.setOnClickListener(this);
        mDeviceAdapter.setOnItemClickListener(new AbsBaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Object bean, int position) {
                startActivity(ConnectionModelActivity.class);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.control_camera_tv://拍照相关
//                startActivity(PhotoActivity.class);
                break;
            case R.id.control_administration_tv://授权管理
                startActivity(AuthorizationActivity.class);
                break;
            case R.id.control_open_lock_tv://远程开锁
                startActivity(RemoteActivity.class);
                break;
            case R.id.control_set_tv://设置
                startActivity(SettingActivity.class);
                break;
            case R.id.control_record_tv://开锁记录
                startActivity(UnlockingRecordActivity.class);
                break;
            case R.id.control_information_tv://用户信息
                startActivity(UserInformationActivity.class);
                break;
            case R.id.pop_add_device_tv://popwindow中添加设备按钮
                startActivity(AddDeviceActivity.class);

                break;
            default:
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mPopupWindow != null) {
            mPopupWindow.dismiss();
        }
    }

    @Override
    public void setPresenter(ControlContract.ControlPresenter presenter) {
        this.mControlPresenter = presenter;
    }

    /**
     * 弹出显示设备列表和添加设备的弹窗
     */
    private void showPopWindow(View v) {
        //获取手机屏幕宽 用来设置弹窗宽度
        DisplayMetrics dm = Utility.getDisplayScreenSize(this);
        //填充弹窗视图
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popup_device_show, null);
        mPopupWindow = new PopupWindow(view, dm.widthPixels - 40, dm.heightPixels / 2);
        //设置获取焦点
        mPopupWindow.setFocusable(true);
        //设置弹窗外部可点击
        mPopupWindow.setOutsideTouchable(true);
        //设置背景
        Drawable drawable = getResources().getDrawable(R.drawable.pop_dlg_bg);
        mPopupWindow.setBackgroundDrawable(drawable);
        //设置弹窗位置
        mPopupWindow.showAsDropDown(v, 20, 0);
        //设置弹窗后背景的亮度
        final WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.6f;
        getWindow().setAttributes(lp);
        //绑定弹窗控件
        pop_device_rv = (RecyclerView) view.findViewById(R.id.pop_device_rv);
        pop_add_device_tv = (TextView) view.findViewById(R.id.pop_add_device_tv);
        pop_add_device_tv.setOnClickListener(this);
        //设置弹窗消失之后恢复背景亮度
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });

        showDevice();
    }

    /**
     * 显示设备列表
     */
    private void showDevice() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        pop_device_rv.setLayoutManager(manager);
        mDeviceAdapter.setListData(deviceList);
        pop_device_rv.setAdapter(mDeviceAdapter);
    }
}
