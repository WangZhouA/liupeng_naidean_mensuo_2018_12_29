package com.saiyi.naideanlock.ui;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import com.saiyi.naideanlock.R;
import com.saiyi.naideanlock.base.BaseActivity;
import com.saiyi.naideanlock.config.Config;
import com.saiyi.naideanlock.new_ui.basis.NewLoginActivity;
import com.saiyi.naideanlock.utils.MD5Util;
import com.saiyi.naideanlock.utils.SharedPreferencesUtils;


/**
 * 启动页面
 */
public class LauncherActivity extends BaseActivity {

    private Handler mHandler = new Handler();

    @Override
    protected int getContentView() {
        return R.layout.activity_launcher;
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
//        Intent intent = new Intent(this, MyService.class);
//        startService(intent);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                goToPage();
            }
        }, 2000);
    }

    @Override
    protected void topBarSet() {

    }

    @Override
    protected void topBarListener() {

    }

    @Override
    protected void setListener() {

    }

    private void goToPage() {
        boolean isLogin = SharedPreferencesUtils.getInstance().getSPFBoolean(Config.IS_LOGIN);
        if (isLogin) {//用户登录过
            login();
        } else {//用户没登录
            startActivity(NewLoginActivity.class);
        }
        finish();
    }

    /**
     * 调用一下登录接口 登录成功之后在跳转到主页面
     */
    private void login() {
        String username = SharedPreferencesUtils.getInstance().getSPFString(Config.LOGIN_USER_NAME);
        String password = MD5Util.decrypt(SharedPreferencesUtils.getInstance().getSPFString(Config.LOGIN_PASS_WORLD));
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            return;
        }


    }
}
