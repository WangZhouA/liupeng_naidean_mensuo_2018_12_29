package com.saiyi.naideanlock.new_ui.device;

import android.bluetooth.BluetoothDevice;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.saiyi.naideanlock.R;
import com.saiyi.naideanlock.application.MyApplication;
import com.saiyi.naideanlock.bean.MdlScanNewDevice;
import com.saiyi.naideanlock.enums.BLEDeviceStatus;
import com.saiyi.naideanlock.enums.EnumDeviceAdmin;
import com.saiyi.naideanlock.enums.EnumDeviceLink;
import com.saiyi.naideanlock.new_ui.base.MVPBaseHandleBLEActivity;
import com.saiyi.naideanlock.new_ui.device.mvp.p.AddDeviceActivityPresenter;
import com.saiyi.naideanlock.new_ui.device.mvp.v.AddDeviceActivityView;
import com.sandy.guoguo.babylib.adapter.recycler.BaseAdapterHelper;
import com.sandy.guoguo.babylib.adapter.recycler.MyRecyclerAdapter;
import com.sandy.guoguo.babylib.adapter.recycler.RecycleViewDivider;
import com.sandy.guoguo.babylib.adapter.recycler.WrapContentLinearLayoutManager;
import com.sandy.guoguo.babylib.constant.BabyHttpConstant;
import com.sandy.guoguo.babylib.entity.MdlBaseHttpResp;
import com.sandy.guoguo.babylib.listener.OnMultiClickListener;
import com.sandy.guoguo.babylib.utils.LogAndToastUtil;
import com.sandy.guoguo.babylib.utils.ResourceUtil;
import com.sandy.guoguo.babylib.utils.eventbus.MdlEventBus;
import com.sandy.guoguo.babylib.widgets.MyRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewAddBleDeviceActivity extends MVPBaseHandleBLEActivity<AddDeviceActivityView, AddDeviceActivityPresenter> implements AddDeviceActivityView {
    private MyRecyclerView<MdlScanNewDevice> mXRecyclerView;
    private MyRecyclerAdapter<MdlScanNewDevice> adapter;
    private List<MdlScanNewDevice> dataList = new ArrayList<>();

    private ImageView ivBgBle;

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopAnim();
        if (mXRecyclerView != null) {
            mXRecyclerView.destroy();
            mXRecyclerView = null;
        }
    }

    /**
     * 旋转动画
     */
    private void startAnim() {
        Animation circle_anim = AnimationUtils.loadAnimation(this, R.anim.anim_round_rotate);
        LinearInterpolator interpolator = new LinearInterpolator();  //设置匀速旋转，在xml文件中设置会出现卡顿
        circle_anim.setInterpolator(interpolator);
        ivBgBle.startAnimation(circle_anim);  //开始动画
        ivBgBle.setEnabled(false);
    }

    /**
     * 停止动画
     */
    private void stopAnim() {
        ivBgBle.setEnabled(true);
        ivBgBle.clearAnimation();
    }

    @Override
    protected void timeoutStopScan() {
        stopAnim();
    }

    @Override
    protected void bleSwitchOff() {

    }

    @Override
    protected void scanNewDevice(MdlScanNewDevice mdlScanNewDevice) {
        if (!dataList.contains(mdlScanNewDevice)) {
            dataList.add(mdlScanNewDevice);
            mXRecyclerView.notifyItemInserted(dataList, dataList.size());
        }
    }

    @Override
    protected void prepareScan() {

    }

    @Override
    public synchronized void onEventBusMessage(MdlEventBus event) {
        super.onEventBusMessage(event);
        switch (event.eventType) {
            case BLEDeviceStatus.STOP_SCAN:
                LogAndToastUtil.cancelWait(this);
                timeoutStopScan();
                break;

        }
    }

    @Override
    protected int getTitleResId() {
        return R.string.device_add;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_new_add_ble_device;
    }

    @Override
    protected void initViewAndControl() {
        initNav();

        ivBgBle = findView(R.id.ivBgBle);
        ivBgBle.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void OnMultiClick(View view) {
                startAnim();
                startScan();

            }
        });

        mXRecyclerView = findView(R.id.recyclerView);
        mXRecyclerView.fillData(dataList);


        LinearLayoutManager layoutManager = new WrapContentLinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mXRecyclerView.setLayoutManager(layoutManager);

        mXRecyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL));

        initAdapter();


        mXRecyclerView.setAdapter(adapter);
        mXRecyclerView.setLoadingMoreEnabled(false);
        mXRecyclerView.setPullRefreshEnabled(false);
    }


    private void initNav() {
        TextView tvLeft = findView(R.id.toolbarLeft);
        tvLeft.setVisibility(View.VISIBLE);
        ResourceUtil.setCompoundDrawable(tvLeft, R.drawable.dr_ic_back, 0, 0, 0);
    }


    private void initAdapter() {
        adapter = new MyRecyclerAdapter<MdlScanNewDevice>(this, R.layout._item_activity_new_add_ble_device, dataList) {


            @Override
            public void onUpdate(BaseAdapterHelper helper, final MdlScanNewDevice item, final int position) {
                if (item == null) {
                    return;
                }

                helper.setText(R.id.tvName, item.device.getName());
            }
        };

        adapter.setOnItemClickListener(new MyRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder, View view, int position) {
                position--;

                BluetoothDevice bleDevice = dataList.get(position).device;
                addDevice(bleDevice);
            }
        });
    }


    private void addDevice(BluetoothDevice bleDevice) {
        Map<String, Object> params = new HashMap<>();
        params.put("number", MyApplication.getInstance().mdlUserInApp.phone);
        params.put("mac", bleDevice.getAddress());
        params.put("lockName", bleDevice.getName());
        params.put("isAdmin", EnumDeviceAdmin.NOT_ADMIN);
        params.put("linkType", EnumDeviceLink.BLE);
        presenter.bindDevice(params);
    }

    @Override
    protected AddDeviceActivityPresenter createPresenter() {
        return new AddDeviceActivityPresenter(this);
    }

    @Override
    public void showAddDeviceResult(MdlBaseHttpResp resp) {
        if (resp.code == BabyHttpConstant.R_HTTP_OK) {
            finish();
        }
    }
}
