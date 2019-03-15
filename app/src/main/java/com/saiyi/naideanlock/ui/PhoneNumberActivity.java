package com.saiyi.naideanlock.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.saiyi.naideanlock.R;
import com.saiyi.naideanlock.base.BaseActivity;
import com.saiyi.naideanlock.dialog.PromptDialog;
import com.saiyi.naideanlock.topbar.TopToolBar;
import com.saiyi.naideanlock.utils.CharacterUtil;
import com.saiyi.naideanlock.utils.ToastUtil;

public class PhoneNumberActivity extends BaseActivity implements View.OnClickListener {

    private TopToolBar phone_top_bar;
    private EditText phone_number_ed;//电话号码输入框
    private Button phone_number_sure_btn;//确定按钮

    @Override
    protected int getContentView() {
        return R.layout.activity_phone_number;
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {

    }

    @Override
    protected void initView() {
        phone_top_bar = (TopToolBar) findViewById(R.id.phone_top_bar);
        phone_number_ed = (EditText) findViewById(R.id.phone_number_ed);
        phone_number_sure_btn = (Button) findViewById(R.id.phone_number_sure_btn);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void topBarSet() {
        phone_top_bar.setMiddleText(getString(R.string.contacts));
        phone_top_bar.setRightVisible(View.GONE);
    }

    @Override
    protected void topBarListener() {
        phone_top_bar.setOnLeftClickListener(new TopToolBar.TitleOnLeftClickListener() {
            @Override
            public void onLeftClick() {
                finishWarning();
            }
        });
    }

    @Override
    protected void setListener() {
        phone_number_sure_btn.setOnClickListener(this);
    }

    private void finishWarning() {
        String phoneNumber = phone_number_ed.getText().toString().trim();
        if (TextUtils.isEmpty(phoneNumber)) {
            finish();
        } else {
            PromptDialog promptDialog = new PromptDialog(this);
            promptDialog.show();
            promptDialog.setTitleText(getString(R.string.warming));
            promptDialog.setDialogMessage(getString(R.string.warming_message));
            promptDialog.setSureOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            finishWarning();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    public void onClick(View v) {
        changPhoneNumer();
    }

    /**
     * 点击确定按钮进行电话号码修改
     */
    private void changPhoneNumer() {
        String number = phone_number_ed.getText().toString().trim();
        if (TextUtils.isEmpty(number)) {
            ToastUtil.toastShort(this, getString(R.string.message));
            return;
        }

        if (!TextUtils.isEmpty(number)) {
            if (CharacterUtil.isPhoneNumber(number)) {

            } else {
                ToastUtil.toastShort(this, getString(R.string.error_message));
            }
        }
    }
}
