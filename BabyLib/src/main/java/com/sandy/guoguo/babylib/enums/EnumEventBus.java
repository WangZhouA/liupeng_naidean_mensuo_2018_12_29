package com.sandy.guoguo.babylib.enums;


import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class EnumEventBus {
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({
            LOGIN_OK, LOGOUT_OK, LOGIN_PHONE, LOGIN_PWD, LOCATION_SUCCESS, PAY_SUCCESS
    })
    public @interface EventBusCmd {
    }

    public static final int LOGIN_OK = 1;
    public static final int LOGOUT_OK = 1 << 1;
    public static final int LOGIN_PHONE = 1 << 2;
    public static final int LOGIN_PWD = 1 << 3;
    public static final int LOCATION_SUCCESS = 1 << 4;
    public static final int PAY_SUCCESS = 1 << 5;

}
