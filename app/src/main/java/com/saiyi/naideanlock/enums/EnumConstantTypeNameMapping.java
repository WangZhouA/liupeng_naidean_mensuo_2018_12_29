package com.saiyi.naideanlock.enums;

/**
 * Created by Administrator on 2018/6/8.
 */

public class EnumConstantTypeNameMapping {


    public static String getDeviceTypeStr(@EnumDeviceLink._Type int type) {
        String s = "";
        switch (type){
            case EnumDeviceLink.BLE:
                s = "蓝牙";
                break;
            case EnumDeviceLink.WIFI:
                s = "wifi";
                break;
        }
        return s;
    }

//    public static String getAuthName(@EnumDeviceAdmin._Type int type) {
//        String s = "";
//        switch (type){
//            case EnumDeviceAdmin.NOT_ADMIN:
//                s = "蓝牙";
//                break;
//            case EnumDeviceAdmin.IS_ADMIN:
//                s = "蓝牙";
//                break;
//            case EnumDeviceAdmin.NANNY:
//                s = "wifi";
//                break;
//        }
//        return s;
//    }




}
