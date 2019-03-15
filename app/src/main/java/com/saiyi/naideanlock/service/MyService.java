package com.saiyi.naideanlock.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.saiyi.naideanlock.R;
import com.saiyi.naideanlock.utils.ToastUtil;

/**
 * 描述：创建服务 用于网络环境切换之后的状态变化提示
 * 创建作者：ask
 * 创建时间：2017/11/1 14:35
 */

public class MyService extends Service {

    private ConnectionChangeReceiver mConnectionChangeReceiver;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        mConnectionChangeReceiver = new ConnectionChangeReceiver();
        registerReceiver(mConnectionChangeReceiver, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mConnectionChangeReceiver);
    }

    class ConnectionChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) MyService.this.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mobNetInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            NetworkInfo wifiNetInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (!mobNetInfo.isConnected() && !wifiNetInfo.isConnected()) {
                ToastUtil.toastLong(MyService.this, R.string.no_net);
            }

            if (mobNetInfo.isConnected()) {
                ToastUtil.toastShort(MyService.this, R.string.mobile_net);
            }

            if (wifiNetInfo.isConnected()) {
                ToastUtil.toastShort(MyService.this, R.string.wifi_net);
            }

        }
    }
}
