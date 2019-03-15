package com.saiyi.naideanlock.new_ui.device;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.support.annotation.StringRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.saiyi.naideanlock.R;
import com.saiyi.naideanlock.application.MyApplication;
import com.saiyi.naideanlock.bean.MdlControlItem;
import com.saiyi.naideanlock.bean.MdlDevice;
import com.saiyi.naideanlock.bean.MdlHttpRespList;
import com.saiyi.naideanlock.bean.MdlLockPeriodListBean;
import com.saiyi.naideanlock.bean.MdlLockTimeListBean;
import com.saiyi.naideanlock.bean.MdlScanNewDevice;
import com.saiyi.naideanlock.constant.PublicConstant;
import com.saiyi.naideanlock.enums.BLEDeviceStatus;
import com.saiyi.naideanlock.enums.EnumBLECmd;
import com.saiyi.naideanlock.enums.EnumControlItemId;
import com.saiyi.naideanlock.enums.EnumDeviceAdmin;
import com.saiyi.naideanlock.enums.EnumDeviceLink;
import com.saiyi.naideanlock.enums.EnumSwitch;
import com.saiyi.naideanlock.new_ui.base.MVPBaseHandleBLEActivity;
import com.saiyi.naideanlock.new_ui.device.mvp.p.ControlActivityPresenter;
import com.saiyi.naideanlock.new_ui.device.mvp.v.ControlActivityView;
import com.saiyi.naideanlock.new_ui.user.NewUserInfoActivity;
import com.saiyi.naideanlock.service.HomeService;
import com.saiyi.naideanlock.ui.AddDeviceActivity;
import com.saiyi.naideanlock.utils.BLEDeviceCmd;
import com.saiyi.naideanlock.utils.MyUtility;
import com.sandy.guoguo.babylib.adapter.MyViewAdapter;
import com.sandy.guoguo.babylib.adapter.recycler.BaseAdapterHelper;
import com.sandy.guoguo.babylib.adapter.recycler.MyRecyclerAdapter;
import com.sandy.guoguo.babylib.adapter.recycler.RecycleViewDivider;
import com.sandy.guoguo.babylib.adapter.recycler.WrapContentLinearLayoutManager;
import com.sandy.guoguo.babylib.constant.BabyExtraConstant;
import com.sandy.guoguo.babylib.constant.BabyHttpConstant;
import com.sandy.guoguo.babylib.dialogs.CommonDialog;
import com.sandy.guoguo.babylib.dialogs.CommonInputDialog;
import com.sandy.guoguo.babylib.entity.MdlBaseHttpResp;
import com.sandy.guoguo.babylib.listener.OnMultiClickListener;
import com.sandy.guoguo.babylib.utils.DelayHandler;
import com.sandy.guoguo.babylib.utils.LogAndToastUtil;
import com.sandy.guoguo.babylib.utils.ResourceUtil;
import com.sandy.guoguo.babylib.utils.Utility;
import com.sandy.guoguo.babylib.utils.eventbus.MdlEventBus;
import com.sandy.guoguo.babylib.widgets.MyGridView;
import com.sandy.guoguo.babylib.widgets.MyRecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewControlActivity extends MVPBaseHandleBLEActivity<ControlActivityView, ControlActivityPresenter> implements ControlActivityView {
    private PopupWindow mPopupWindow;//设备列表弹窗

    private MyRecyclerView<MdlDevice> mXRecyclerView;
    private MyRecyclerAdapter<MdlDevice> adapter;
    private List<MdlDevice> dataList = new ArrayList<>();

    private int actionIndex = -1;
    private TextView tvDeviceName, tvDevicePower, tvDeviceStatus;

    private MdlDevice currentDevice;
    private TextView tvLeft;
    private MyGridView gridViewBottom;
    private ImageView ivLockStatus;
    private ArrayList<MdlControlItem> dataListBottom;
    private MyViewAdapter<MdlControlItem> bottomAdapter;

    private static int backCount = 0;
    private static final int BACK_COUNT_RESET_0_WHAT = 0X1225;

    @Override
    public void onDestroy() {
        super.onDestroy();
        DelayHandler.getInstance().removeMessages(BACK_COUNT_RESET_0_WHAT);
        if (mXRecyclerView != null) {
            mXRecyclerView.destroy();
            mXRecyclerView = null;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_new_control;
    }

    @Override
    protected int getTitleResId() {
        return R.string.control_panel;
    }

    private void initNav() {
        tvLeft = findView(R.id.toolbarLeft);
        tvLeft.setVisibility(View.VISIBLE);
        ResourceUtil.setCompoundDrawable(tvLeft, R.drawable.ic_device, 0, 0, 0);
        tvLeft.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void OnMultiClick(View view) {
                clickShowPopup();
            }
        });
    }

    private void clickShowPopup() {
        if (mPopupWindow == null) {
            initPopWindow();
        }
        if (!mPopupWindow.isShowing()) {
            getAllDevice();

            //设置弹窗位置
            mPopupWindow.showAsDropDown(tvLeft, 0, 40);
        } else {
            mPopupWindow.dismiss();
        }
    }

    private void hidePopupWindow() {
        if (mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }

    private void getAllDevice() {
        Map<String, Object> params = new HashMap<>();
        params.put("linkType", MyApplication.getInstance().deviceLinkType);
        params.put("number", MyApplication.getInstance().mdlUserInApp.phone);
        presenter.getAllDeviceByType(params);
    }


    @Override
    protected void initViewAndControl() {
        initNav();

        ivLockStatus = findView(R.id.ivLockStatus);
        if (MyApplication.getInstance().deviceLinkType == EnumDeviceLink.BLE) {
            ivLockStatus.setImageResource(R.drawable.ic_ble_lock_switch_off);
        } else {
            ivLockStatus.setImageResource(R.drawable.ic_wifi_lock_switch_off);
        }

        tvDeviceStatus = findView(R.id.tvDeviceStatus);
        showDeviceStatusUI(false);

        tvDeviceName = findView(R.id.tvDeviceName);
        tvDeviceName.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void OnMultiClick(View view) {
                updateDeviceNameDialog();
            }
        });
        tvDevicePower = findView(R.id.tvPower);


        initBottomView();
    }

    private void initBottomView() {
        dataListBottom = new ArrayList<>();
        gridViewBottom = findView(R.id.gridView);
        resetBottomData();

        bottomAdapter = new MyViewAdapter<>(dataListBottom);
        bottomAdapter.setOnBindingListener(onBindingBottomView);
        gridViewBottom.setAdapter(bottomAdapter);

    }

    private void resetBottomData() {
        dataListBottom.clear();
        MdlControlItem item;
        if (currentDevice == null) {
            gridViewBottom.setNumColumns(3);
            item = new MdlControlItem(false, getString(R.string.camera), R.drawable.dr_ic_set_camera);
            dataListBottom.add(item);
            item = new MdlControlItem(false, getString(R.string.administration), R.drawable.dr_ic_set_authorization);
            dataListBottom.add(item);
            item = new MdlControlItem(false, getString(R.string.open_lock), R.drawable.dr_ic_set_lock);
            dataListBottom.add(item);
            item = new MdlControlItem(false, getString(R.string.set), R.drawable.dr_ic_set_setting);
            dataListBottom.add(item);
            item = new MdlControlItem(false, getString(R.string.record), R.drawable.dr_ic_set_record);
            dataListBottom.add(item);
        } else if (currentDevice.isAdmin == EnumDeviceAdmin.NANNY) {
            gridViewBottom.setNumColumns(2);
            item = new MdlControlItem(EnumControlItemId.UNLOCK, getString(R.string.open_lock), R.drawable.dr_ic_set_lock);
            dataListBottom.add(item);
        } else {
            gridViewBottom.setNumColumns(3);
            item = new MdlControlItem(EnumControlItemId.CAMERA, getString(R.string.camera), R.drawable.dr_ic_set_camera);
            dataListBottom.add(item);
//            item = new MdlControlItem(EnumControlItemId.AUTHORIZATION, currentDevice.isAdmin == EnumDeviceAdmin.IS_ADMIN, getString(R.string.administration), R.drawable.dr_ic_set_authorization);
            item = new MdlControlItem(EnumControlItemId.AUTHORIZATION, true, getString(R.string.administration), R.drawable.dr_ic_set_authorization);
            dataListBottom.add(item);

            @StringRes int lockStrId = currentDevice.linkType == EnumDeviceLink.WIFI ? R.string.open_remote_lock : R.string.open_lock;
            item = new MdlControlItem(EnumControlItemId.UNLOCK, getString(lockStrId), R.drawable.dr_ic_set_lock);
            dataListBottom.add(item);

            item = new MdlControlItem(EnumControlItemId.ABOUT_SETTING, getString(R.string.set), R.drawable.dr_ic_set_setting);
            dataListBottom.add(item);
            item = new MdlControlItem(EnumControlItemId.RECORD, getString(R.string.record), R.drawable.dr_ic_set_record);
            dataListBottom.add(item);
        }
        item = new MdlControlItem(EnumControlItemId.USER, true, getString(R.string.information), R.drawable.dr_ic_set_user);
        dataListBottom.add(item);
    }

    private MyViewAdapter.OnBindingListener<MdlControlItem> onBindingBottomView = new MyViewAdapter.OnBindingListener<MdlControlItem>() {

        @Override
        public View OnBinding(int index, MdlControlItem item, View convertView) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout._item_activity_new_control_grid_view, null);
            }
            TextView tvItemName = convertView.findViewById(R.id.stvName);
            tvItemName.setText(item.name);
            tvItemName.setEnabled(item.enable);
            ResourceUtil.setCompoundDrawable(tvItemName, 0, item.iconRes, 0, 0);
            tvItemName.setOnClickListener(new ClickBottomListener(item));
            return convertView;
        }
    };


    private class ClickBottomListener extends OnMultiClickListener {
        private MdlControlItem item;

        public ClickBottomListener(MdlControlItem item) {
            this.item = item;
        }

        @Override
        public void OnMultiClick(View view) {
            Class<?> targetCls = null;
            switch (item.targetId) {
                case EnumControlItemId.CAMERA://拍照相关
                    targetCls = NewPhotoActivity.class;
                    break;
                case EnumControlItemId.AUTHORIZATION://授权管理
                    targetCls = NewAuthManagerActivity.class;
                    break;
                case EnumControlItemId.UNLOCK://远程开锁
//                        targetCls = NewRemoteUnlockActivity.class;
                    if (currentDevice.isAdmin == EnumDeviceAdmin.NANNY) {
                        if (!nannyCheckUnlockTimeAndWeekPeriod()) {
                            LogAndToastUtil.toast("非授权时段不能开锁");
                            return;
                        }
                    }
                    if (currentDevice.linkType == EnumDeviceLink.BLE) {
                        clickOpenBLELock();
                    } else {
                        targetCls = NewRemoteUnlockActivity.class;
                    }

                    break;
                case EnumControlItemId.ABOUT_SETTING://设置
                    targetCls = NewSettingActivity.class;
                    break;
                case EnumControlItemId.RECORD://开锁记录
                    targetCls = NewUnlockRecordActivity.class;
                    break;
                case EnumControlItemId.USER://用户信息
                    targetCls = NewUserInfoActivity.class;
                    break;
                default:
                    break;
            }

            if (targetCls == null) {
                return;
            }
            Intent intent = new Intent(NewControlActivity.this, targetCls);
            intent.putExtra(BabyExtraConstant.EXTRA_ITEM, currentDevice);
            startActivityForResult(intent, PublicConstant.REQ_ITEM);
        }
    }

    /**
     * 当前用户身份是“保姆”时，对允许开锁时段和星期的校验
     */
    private boolean nannyCheckUnlockTimeAndWeekPeriod() {
        Calendar calendar = Calendar.getInstance();
        boolean flag = false;
        int today = calendar.get(Calendar.DAY_OF_WEEK);


        for (MdlLockTimeListBean bean : currentDevice.lockTimeList) {
            if (String.valueOf(today).equals(bean.lockTime) || "8".equals(bean.lockTime)) {
                flag = true;
                break;
            }
        }
        if (!flag) {
            return false;
        }

        List<MdlLockPeriodListBean> lockPeriodListBeans = currentDevice.lockPeriodList;

        int nowMinuteAndSecond = calendar.get(Calendar.HOUR_OF_DAY) * 60 + calendar.get(Calendar.MINUTE);


        flag = false;

        int[] period;
        for (MdlLockPeriodListBean bean : lockPeriodListBeans) {
            period = new int[4];
            if (TextUtils.isEmpty(bean.lockPeriod)) {
                continue;
            }
            String[] periodArr = bean.lockPeriod.split("[:-]");
            int len = periodArr.length;
            len = len > 4 ? 4 : len;
            for (int i = 0; i < len; i++) {
                period[i] = Integer.parseInt(periodArr[i]);
            }
            if (period[0] * 60 + period[1] <= nowMinuteAndSecond && nowMinuteAndSecond <= period[2] * 60 + period[3]) {
                flag = true;
                break;
            }
        }

        return flag;
    }

    private void clickOpenBLELock() {
        if (isOneDeviceConnected) {
            CommonInputDialog dialog = new CommonInputDialog(this, getString(R.string.input_device_pwd), getString(R.string.input_device_pwd), new CommonInputDialog.ClickListener() {
                @Override
                public void clickConfirm(String content) {
                    Utility.toggleSoftKeyboard(NewControlActivity.this);

                    if (MyUtility.checkBLEServiceIsNull()) {
                        return;
                    }
                    if (content.equals(currentDevice.pwd)) {
                        HomeService.ME.writeData(BLEDeviceCmd.unlock());
                    } else {
                        LogAndToastUtil.toast("密码输入错误!!!");
                    }
                }

                @Override
                public void clickCancel() {
                }
            }, true);

            dialog.show();

        } else {
            LogAndToastUtil.toast("设备未连接!!!");
            startScan();
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case PublicConstant.REQ_ITEM:
                currentDevice = data.getParcelableExtra(BabyExtraConstant.EXTRA_ITEM);
                break;
        }

    }


    private void showDeviceStatusUI(boolean isLocked) {
        String str = isLocked ? "打开" : "关闭";
        Spannable spannable = Utility.getCommon2LinesSpan(str + "\n", getString(R.string.add_device), R.dimen.font_14, R.dimen.font_14, R.color.brown_1, R.color.gray3);
        tvDeviceStatus.setText(spannable);
    }

    private void updateDeviceNameDialog() {
        String oldName = Utility.getEditTextStr(tvDeviceName);
        if (TextUtils.isEmpty(oldName)) {
            return;
        }

        CommonInputDialog dialog = new CommonInputDialog(this, getString(R.string.rename_device), getString(R.string.input_device_name), new CommonInputDialog.ClickListener() {
            @Override
            public void clickConfirm(String content) {
                handleUpdateDeviceName(content);
                Utility.toggleSoftKeyboard(NewControlActivity.this);
            }

            @Override
            public void clickCancel() {
            }
        });

        dialog.show();
    }

    private void handleUpdateDeviceName(String name) {
        Map<String, Object> params = new HashMap<>();
        params.put("lockName", name);
        params.put("mac", currentDevice.mac);
        params.put("linkType", MyApplication.getInstance().deviceLinkType);
        presenter.updateDeviceName(params);

        tvDeviceName.setText(name);
    }

    /**
     * 弹出显示设备列表和添加设备的弹窗
     */
    private void initPopWindow() {
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

        view.findViewById(R.id.ivAddDevice).setOnClickListener(new OnMultiClickListener() {
            @Override
            public void OnMultiClick(View view) {
                go2AddDevice();
                hidePopupWindow();
            }
        });


        initRecyclerView(view);
    }

    private void go2AddDevice() {
        if (MyApplication.getInstance().deviceLinkType == EnumDeviceLink.BLE) {
            if (isOneDeviceConnected && currentDevice != null) {
                HomeService.ME.disConnect(currentDevice.mac);
            }
            startActivity(new Intent(NewControlActivity.this, NewAddBleDeviceActivity.class));
        } else {
            startActivity(new Intent(NewControlActivity.this, AddDeviceActivity.class));
        }
    }

    private void initRecyclerView(View view) {
        mXRecyclerView = view.findViewById(R.id.recyclerView);
        mXRecyclerView.fillData(dataList);

        LinearLayoutManager layoutManager = new WrapContentLinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mXRecyclerView.setLayoutManager(layoutManager);
        mXRecyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL));

        initAdapter();
//        mXRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
//            @Override
//            public void onRefresh() {
//                loadRefresh();
//            }
//
//            @Override
//            public void onLoadMore() {
//                loadMore();
//            }
//        });
        mXRecyclerView.setAdapter(adapter);
        mXRecyclerView.setLoadingMoreEnabled(false);
        mXRecyclerView.setPullRefreshEnabled(false);
    }

    private void initAdapter() {
        adapter = new MyRecyclerAdapter<MdlDevice>(this, R.layout._item_popup_device_show, dataList) {
            @Override
            public void onUpdate(BaseAdapterHelper helper, final MdlDevice item, final int position) {
                if (item == null) {
                    return;
                }

                helper.setText(R.id.tvName, item.lockName);
                helper.setOnClickListener(R.id.ivDel, new OnMultiClickListener() {
                    @Override
                    public void OnMultiClick(View view) {
                        clickDelDeviceNotice(position);
                    }
                });

            }
        };
        adapter.setOnItemClickListener(new MyRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder, View view, int position) {
                position--;

                MdlDevice device = dataList.get(position);
                if (device.linkType == EnumDeviceLink.BLE) {
                    boolean flag = false;
                    if (!MyUtility.checkBLEServiceIsNull()) {
                        flag = HomeService.ME.getProfileState(device.mac);
                    }
                    if (flag) {
                        showCurrentDevice(position);
                        return;
                    }
                    if (currentDevice == null) {
                        showCurrentDevice(position);
                        startScan();
                        return;
                    }
                    if (device.mac.equals(currentDevice.mac)) {
                        if (isOneDeviceConnected) {
                            LogAndToastUtil.log("--------相同的设备，且已经连接上了------");
                            showCurrentDevice(position);
                        } else {
                            startScan();
                        }
                    } else {
                        if (MyUtility.checkBLEServiceIsNull()) {
                            return;
                        }
                        if (isOneDeviceConnected) {
                            HomeService.ME.disConnect(currentDevice.mac);
                        }
                        showCurrentDevice(position);
                        startScan();
                    }
                } else {
                    showCurrentDevice(position);
                }

            }
        });
    }

    private void showCurrentDevice(int position) {
        currentDevice = dataList.get(position);
        tvDeviceName.setText(currentDevice.lockName);
        //todo 要处理显示电量
        tvDevicePower.setText("电量多少呢??????");

        resetBottomData();
        bottomAdapter.notifyDataSetChanged();

        hidePopupWindow();
    }

    private void clickDelDeviceNotice(int position) {
        CommonDialog dialog = new CommonDialog(this, getString(R.string.delete), getString(R.string.delete_device_notice), new CommonDialog.ClickListener() {
            @Override
            public void clickConfirm() {
                delDevice(position);
            }

            @Override
            public void clickCancel() {

            }
        });
        dialog.show();
    }

    private void delDevice(int position) {
        actionIndex = position;

        MdlDevice md = dataList.get(position);

        if (currentDevice != null && currentDevice.mac.equals(md.mac)) {
            currentDevice = null;
            resetBottomData();
        }

        Map<String, Object> params = new HashMap<>();
        params.put("mac", md.mac);
        params.put("isAdmin", md.isAdmin);
        params.put("linkType", MyApplication.getInstance().deviceLinkType);
        params.put("number", MyApplication.getInstance().mdlUserInApp.phone);
        presenter.delDeviceBinding(params);
    }

    private void addUnlockRecord2Remote() {
        if (currentDevice == null) {
            return;
        }

        Map<String, Object> params = new HashMap<>();
        params.put("mac", currentDevice.mac);
        params.put("status", EnumSwitch.ON);
        params.put("linkType", MyApplication.getInstance().deviceLinkType);
        params.put("number", MyApplication.getInstance().mdlUserInApp.phone);
        presenter.addUnlockRecord(params);
    }

    @Override
    protected ControlActivityPresenter createPresenter() {
        return new ControlActivityPresenter(this);
    }

    @Override
    public void showDeviceListResult(MdlBaseHttpResp<MdlHttpRespList<MdlDevice>> resp) {
        mXRecyclerView.loadMoreComplete();
        mXRecyclerView.refreshComplete();

        if (resp.code == BabyHttpConstant.R_HTTP_OK) {
            if (currentPage == 1) {
                dataList.clear();
            }


            boolean haveData = resp.data != null && resp.data.list != null && !resp.data.list.isEmpty();

            if (haveData) {
                dataList.addAll(resp.data.list);
            }
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showDelDeviceResult(MdlBaseHttpResp resp) {
        if (resp.code == BabyHttpConstant.R_HTTP_OK) {
            if (actionIndex >= 0) {
                dataList.remove(actionIndex);
                mXRecyclerView.notifyItemRemoved(dataList, actionIndex);
            }
        }
    }

    @Override
    public void showUpdateDeviceNameResult(MdlBaseHttpResp resp) {
        if (resp.code == BabyHttpConstant.R_HTTP_OK) {
            currentDevice.lockName = Utility.getEditTextStr(tvDeviceName);
        } else {
            tvDeviceName.setText(currentDevice.lockName);
        }
    }

    @Override
    public void showAddUnlockRecordResult(MdlBaseHttpResp resp) {

    }

    @Override
    protected void timeoutStopScan() {
        LogAndToastUtil.cancelWait(this);
    }

    @Override
    protected void bleSwitchOff() {

    }

    @Override
    protected void scanNewDevice(MdlScanNewDevice mdlScanNewDevice) {
        if (currentDevice != null && mdlScanNewDevice.device.getAddress().equals(currentDevice.mac)) {
            LogAndToastUtil.cancelWait(this);

            Utility.ThreadSleep(150);
            LogAndToastUtil.showWait(this, R.string.connecting);
            HomeService.ME.connect(mdlScanNewDevice.device.getAddress());
        }
    }

    @Override
    protected void prepareScan() {
        LogAndToastUtil.showWait(this, R.string.scanning);
    }

    @Override
    public synchronized void onEventBusMessage(MdlEventBus event) {
        super.onEventBusMessage(event);
        switch (event.eventType) {
            case BLEDeviceStatus.CONNECTED: {
                LogAndToastUtil.cancelWait(this);
                isOneDeviceConnected = true;
                LogAndToastUtil.toast(R.string.connected);

                hidePopupWindow();
                break;
            }

            case BLEDeviceStatus.DISCONNECTED:
            case BLEDeviceStatus.CUSTOM_CONNECT_TIMEOUT:
                LogAndToastUtil.cancelWait(this);
                isOneDeviceConnected = false;
                LogAndToastUtil.toast(R.string.disconnected);

                if (MyApplication.getInstance().deviceLinkType == EnumDeviceLink.BLE) {
                    ivLockStatus.setImageResource(R.drawable.ic_ble_lock_switch_off);
                } else {
                    ivLockStatus.setImageResource(R.drawable.ic_wifi_lock_switch_off);
                }
                showDeviceStatusUI(false);

                break;

            case EnumBLECmd.UNLOCK: {
                byte[] data = (byte[]) event.data;
                if (data[0] == Byte.parseByte(EnumSwitch.ON)) {

                }
                if (MyApplication.getInstance().deviceLinkType == EnumDeviceLink.BLE) {
                    ivLockStatus.setImageResource(R.drawable.ic_ble_lock_switch_on);
                } else {
                    ivLockStatus.setImageResource(R.drawable.ic_wifi_lock_switch_on);
                }
                showDeviceStatusUI(true);

                addUnlockRecord2Remote();
                break;
            }
            case EnumBLECmd.GET_POWER: {
                byte[] data = (byte[]) event.data;
                int powerV = Utility.byte2Uint(data[0]);
                tvDevicePower.setText(Utility.myFormat("设备电量 %.1fV", powerV * 0.1f));
                break;
            }
            case EnumBLECmd.BONDED: {
                if (!MyUtility.checkBLEServiceIsNull()) {
                    HomeService.ME.writeData(BLEDeviceCmd.getPower());
                }
                break;
            }

            /*
             * 根据这个得到的貌似是错误的状态
             * 回调通知onCharacteristicChanged()->uuid:0000ff01-0000-1000-8000-00805f9b34fb;value: 0x5A 0xA5 0x33 0x01 0x01 0xCC
             * 回调通知onCharacteristicChanged()->uuid:0000ff01-0000-1000-8000-00805f9b34fb;value: 0x5A 0xA5 0x36 0x01 0x00 0xC8
             */
            case EnumBLECmd.DEVICE_ACTIVE_REPORT_LOCK_STATUS: {

//                byte[] data = (byte[]) event.data;
//                if (data[0] == Byte.parseByte(EnumSwitch.OFF)) {
//                    if (MyApplication.getInstance().deviceLinkType == EnumDeviceLink.BLE) {
//                        ivLockStatus.setImageResource(R.drawable.ic_ble_lock_switch_off);
//                    } else {
//                        ivLockStatus.setImageResource(R.drawable.ic_wifi_lock_switch_off);
//                    }
//                    showDeviceStatusUI(false);
//                } else {
//                    if (MyApplication.getInstance().deviceLinkType == EnumDeviceLink.BLE) {
//                        ivLockStatus.setImageResource(R.drawable.ic_ble_lock_switch_on);
//                    } else {
//                        ivLockStatus.setImageResource(R.drawable.ic_wifi_lock_switch_on);
//                    }
//                    showDeviceStatusUI(true);
//                }
                break;
            }
        }
    }

    //重写方法，为了是在当前activity不扫描ble设备
    @Override
    protected void permissionOK() {

    }

    @Override
    protected void handleBackKey() {
        if (backCount == 0) {
            LogAndToastUtil.toast(R.string.click_again_go_desktop);
            backCount++;

            Message msg = DelayHandler.getInstance().obtainMessage();
            msg.what = BACK_COUNT_RESET_0_WHAT;
            msg.obj = new Runnable() {
                @Override
                public void run() {
                    backCount = 0;
                }
            };
            DelayHandler.getInstance().sendMessageDelayed(msg, 2 * 1000);
        } else if (backCount >= 1) {
            DelayHandler.getInstance().removeMessages(BACK_COUNT_RESET_0_WHAT);

            // 回到桌面
            Utility.goToDesktop(this);
            backCount = 0;
        }

    }


}
