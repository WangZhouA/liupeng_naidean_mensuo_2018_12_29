package com.saiyi.naideanlock.enums;


import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class EnumSwitch {
    @Retention(RetentionPolicy.SOURCE)
    @StringDef({
            ON, OFF
    })
    public @interface _Status {
    }

    public static final String ON = "1";
    public static final String OFF = "0";

}
