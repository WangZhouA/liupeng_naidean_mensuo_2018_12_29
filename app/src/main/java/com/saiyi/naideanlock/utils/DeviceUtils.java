package com.saiyi.naideanlock.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * 描述：获取手机宽高 做适配用
 * 创建作者：ask
 * 创建时间：2017/9/20 14:47
 */

public class DeviceUtils {
    private static int screenWidth = -1;
    private static int screenHeight = -1;

    /**
     * 获取屏幕分辨率宽度
     */
    public static int getScreenWidth(Activity context) {
        if (screenWidth == -1) {
            DisplayMetrics dm = new DisplayMetrics();
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            wm.getDefaultDisplay().getMetrics(dm);
            screenWidth = dm.widthPixels;
        }
        return screenWidth;
    }

    /**
     * 获取屏幕分辨率宽度
     */
    public static int getScreenHeight(Context context) {
        if (screenHeight == -1) {
            DisplayMetrics dm = new DisplayMetrics();
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            wm.getDefaultDisplay().getMetrics(dm);
            screenHeight = dm.heightPixels;
        }
        return screenHeight;
    }
}
