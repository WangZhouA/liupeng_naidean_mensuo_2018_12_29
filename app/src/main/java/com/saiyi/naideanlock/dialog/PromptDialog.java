package com.saiyi.naideanlock.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.saiyi.naideanlock.R;

/**
 * 描述：提示对话框
 * 创建作者：ask
 * 创建时间：2017/11/15 16:52
 */

public class PromptDialog extends Dialog {

    private TextView dialog_title_tv;
    private TextView input_txt;
    private Button btn_cancel, btn_ok;

    public PromptDialog(Context context) {
        super(context, R.style.ThemeIOSDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prompt_dialog);
        dialog_title_tv = (TextView) findViewById(R.id.dialog_title_tv);
        input_txt = (TextView) findViewById(R.id.input_txt);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        btn_ok = (Button) findViewById(R.id.btn_ok);

        btn_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                cancel();
            }
        });

    }

    /**
     * 设置提示语
     *
     * @param titleText
     */
    public void setTitleText(String titleText) {
        if (!TextUtils.isEmpty(titleText) && dialog_title_tv != null) {
            dialog_title_tv.setText(titleText);
        }
    }

    /**
     * 设置提示信息
     *
     * @param message
     */
    public void setDialogMessage(String message) {
        if (!TextUtils.isEmpty(message) && input_txt != null) {
            input_txt.setText(message);
        }
    }

    /**
     * 设置确定按钮的点击事件
     *
     * @param sureOnClickListener
     */
    public void setSureOnClickListener(View.OnClickListener sureOnClickListener) {
        if (sureOnClickListener != null && btn_ok != null) {
            btn_ok.setOnClickListener(sureOnClickListener);
        }
    }


}
