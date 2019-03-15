package com.saiyi.naideanlock.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.saiyi.naideanlock.R;

/**
 * 带有输入框的对话框
 */
public class InputDialog extends Dialog {

    private TextView dialog_title_tv;
    private EditText input_txt;

    private Button btnOk, btnCancel;

    public InputDialog(Context context) {
        super(context, R.style.ThemeIOSDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_dialog);
        dialog_title_tv = (TextView) findViewById(R.id.dialog_title_tv);
        btnOk = (Button) findViewById(R.id.btn_ok);
        btnCancel = (Button) findViewById(R.id.btn_cancel);
        input_txt = (EditText) findViewById(R.id.input_txt);

        input_txt.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (TextUtils.isEmpty(s.toString())) {
                    btnOk.setTextColor(0xffa8a8a8);
                } else {
                    btnOk.setTextColor(0xff037BFF);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                cancel();
            }
        });
    }


    /**
     * 设置标题栏的提示语
     *
     * @param text
     */
    public void setDialogText(String text) {
        if (!TextUtils.isEmpty(text) && dialog_title_tv != null) {
            dialog_title_tv.setText(text);
        }
    }

    /**
     * 获取输入框的输入
     *
     * @return
     */
    public String getInputText() {
        if (input_txt != null) {
            return input_txt.getText().toString().trim();
        }
        return null;
    }

    /**
     * 设置确认按钮的点击事件
     *
     * @param onClickListener
     */
    public void setSureClickListener(View.OnClickListener onClickListener) {
        if (onClickListener != null && btnOk != null) {
            btnOk.setOnClickListener(onClickListener);
        }
    }

}
