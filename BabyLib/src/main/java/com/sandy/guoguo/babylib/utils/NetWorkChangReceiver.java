package com.sandy.guoguo.babylib.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;

public class NetWorkChangReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        LogAndToastUtil.log("网络变化:%s","--网络状态发生变化");
        //检测API是不是小于23，因为到了API23之后getNetworkInfo(int networkType)方法被弃用
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {

            //获得ConnectivityManager对象
            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            //获取ConnectivityManager对象对应的NetworkInfo对象
            //获取WIFI连接的信息
            NetworkInfo wifiNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            //获取移动数据连接的信息
            NetworkInfo dataNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (wifiNetworkInfo.isConnected() && dataNetworkInfo.isConnected()) {
                LogAndToastUtil.log("API level < 23 网络情况:%s","WIFI已连接,移动数据已连接");
            } else if (wifiNetworkInfo.isConnected() && !dataNetworkInfo.isConnected()) {
                LogAndToastUtil.log("API level < 23 网络情况:%s","WIFI已连接,移动数据已断开");
            } else if (!wifiNetworkInfo.isConnected() && dataNetworkInfo.isConnected()) {
                LogAndToastUtil.log("API level < 23 网络情况:%s","WIFI已断开,移动数据已连接");
            } else {
                LogAndToastUtil.log("API level < 23 网络情况:%s","WIFI已断开,移动数据已断开");
            }
        //API大于23时使用下面的方式进行网络监听
        } else {

            //获得ConnectivityManager对象
            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            //获取所有网络连接的信息
            Network[] networks = connMgr.getAllNetworks();
            //用于存放网络连接信息
            StringBuilder sb = new StringBuilder();
            //通过循环将网络信息逐个取出来
            for (int i = 0; i < networks.length; i++) {
                //获取ConnectivityManager对象对应的NetworkInfo对象
                NetworkInfo networkInfo = connMgr.getNetworkInfo(networks[i]);
                sb.append(networkInfo.getTypeName() + " connect is " + networkInfo.isConnected());
            }
            LogAndToastUtil.log("API level >= 23 网络情况:%s", sb.toString());
        }

    }
}
