package com.saiyi.naideanlock.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.saiyi.naideanlock.R;
import com.saiyi.naideanlock.base.BaseActivity;
import com.saiyi.naideanlock.topbar.TopToolBar;

/**
 * 重置开锁密码页面
 */
public class ResetPasswordActivity extends BaseActivity implements View.OnClickListener {

    private TopToolBar reset_top_bar;
    private EditText reset_new_pass_ed;//输入新密码
    private EditText reset_new_pass_two_ed;//再次输入新密码
    private Button reset_pass_btn;//确定按钮


    @Override
    protected int getContentView() {
        return R.layout.activity_reset_password;
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {

    }

    @Override
    protected void initView() {
        reset_top_bar = (TopToolBar) findViewById(R.id.reset_top_bar);
        reset_new_pass_ed = (EditText) findViewById(R.id.reset_new_pass_ed);
        reset_new_pass_two_ed = (EditText) findViewById(R.id.reset_new_pass_two_ed);
        reset_pass_btn = (Button) findViewById(R.id.reset_pass_btn);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void topBarSet() {
        reset_top_bar.setMiddleText(getString(R.string.open_lock_pass));
        reset_top_bar.setRightVisible(View.GONE);
    }

    @Override
    protected void topBarListener() {
        reset_top_bar.setOnLeftClickListener(new TopToolBar.TitleOnLeftClickListener() {
            @Override
            public void onLeftClick() {
                finish();
            }
        });
    }

    @Override
    protected void setListener() {
        reset_pass_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.reset_pass_btn:
                break;
        }
    }
}
