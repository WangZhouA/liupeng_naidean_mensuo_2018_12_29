package com.saiyi.naideanlock.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.saiyi.naideanlock.R;

/**
 * 描述：进度提示对话框
 * 创建作者：ask
 * 创建时间：2017/11/15 16:43
 */

public class ProgressDialog extends Dialog {

    private TextView tv_show_message;

    public ProgressDialog(Context context) {
        super(context, R.style.ThemeIOSDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_dialog);
        tv_show_message = (TextView) findViewById(R.id.tv_show_message);
    }
}
