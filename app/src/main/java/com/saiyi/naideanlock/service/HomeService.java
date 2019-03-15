package com.saiyi.naideanlock.service;

import android.app.Service;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.IBinder;

import java.util.HashSet;
import java.util.List;

public abstract class HomeService extends Service {
    public static HomeService ME = null;
    public HashSet<String> scanDeviceMacAddress;

    public static final int AUTO_STOP_SCAN_MSG_WHAT = 0X15011501;
    protected static final int CUSTOM_CONNECT_TIMEOUT_MSG_WHAT = 0X031801;

    public boolean isScanning = false;


    public void clearScanCache(){
        scanDeviceMacAddress.clear();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        scanDeviceMacAddress = new HashSet<>();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


/*	protected void setForeground() {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
		mBuilder.setSmallIcon(R.drawable.ic_launcher);
		mBuilder.setContentTitle(getString(R.string.notify_title));
		mBuilder.setContentText(getString(R.string.notify_text));
		Intent resultIntent = new Intent(this, DeviceShowListActivity.class);
		resultIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		stackBuilder.addParentStack(DeviceShowListActivity.class);
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
		mBuilder.setContentIntent(resultPendingIntent);
		startForeground(0, mBuilder.build());
	}*/

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        scanDeviceMacAddress.clear();
        return super.onStartCommand(intent, flags, startId);
    }

    public abstract IBinder getBinder();

    /**
     * 得到连接状态
     */
    public abstract boolean getProfileState(String address);


    /**
     * 连接设备
     */
    public abstract boolean connect(String address);

    /**
     * 断开设备
     */
    public abstract void disConnect(String address);

    /**
     * 得到已连接的蓝牙设备
     */
    public abstract List<BluetoothDevice> getConnectDevices();

    /**
     * 扫描设备
     */
    public abstract void scan(boolean flag);

    /**
     * 写入数据
     */
    public abstract void writeData(byte[] data);


    @Override
    public IBinder onBind(Intent intent) {
        return getBinder();
    }

}
