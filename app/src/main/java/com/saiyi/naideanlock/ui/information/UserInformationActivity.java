package com.saiyi.naideanlock.ui.information;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.saiyi.naideanlock.R;
import com.saiyi.naideanlock.base.BaseActivity;
import com.saiyi.naideanlock.topbar.TopToolBar;
import com.saiyi.naideanlock.ui.PhoneNumberActivity;

/**
 * 用户信息页面
 */
public class UserInformationActivity extends BaseActivity implements View.OnClickListener, UserInformationContract.UserInformationView {

    private TopToolBar user_top_bar;
    private RoundedImageView user_head_portrait_iv;//圆形头像

    private TextView user_account_number_tv;//用户账号
    private TextView user_change_avatar_tv;//更换头像
    private TextView user_change_phone_number_tv;//更改绑定号码
    private TextView user_update_tv;//检查更新
    private TextView user_about_us_tv;//关于我们
    private TextView user_help_tv;//使用帮助
    private Button user_quit_bt;//退出登录
    private UserInformationContract.UserInformationPresenter mPresenter;

    private int requestCode;

    public static String PHONE_NUMBER = "phone_number";

    @Override
    protected int getContentView() {
        return R.layout.activity_user_information;
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        new UserInformationPresenter(this);
    }

    @Override
    protected void initView() {
        user_top_bar = (TopToolBar) findViewById(R.id.user_top_bar);
        user_head_portrait_iv = (RoundedImageView) findViewById(R.id.user_head_portrait_iv);
        user_account_number_tv = (TextView) findViewById(R.id.user_account_number_tv);
        user_change_avatar_tv = (TextView) findViewById(R.id.user_change_avatar_tv);
        user_change_phone_number_tv = (TextView) findViewById(R.id.user_change_phone_number_tv);
        user_update_tv = (TextView) findViewById(R.id.user_update_tv);
        user_about_us_tv = (TextView) findViewById(R.id.user_about_us_tv);
        user_help_tv = (TextView) findViewById(R.id.user_help_tv);
        user_quit_bt = (Button) findViewById(R.id.user_quit_bt);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void topBarSet() {
        user_top_bar.setRightVisible(View.GONE);
        user_top_bar.setMiddleText(getString(R.string.user_information));
    }

    @Override
    protected void topBarListener() {
        user_top_bar.setOnLeftClickListener(new TopToolBar.TitleOnLeftClickListener() {
            @Override
            public void onLeftClick() {
                finish();
            }
        });
    }

    @Override
    protected void setListener() {
        user_head_portrait_iv.setOnClickListener(this);
        user_account_number_tv.setOnClickListener(this);
        user_change_avatar_tv.setOnClickListener(this);
        user_change_phone_number_tv.setOnClickListener(this);
        user_update_tv.setOnClickListener(this);
        user_about_us_tv.setOnClickListener(this);
        user_help_tv.setOnClickListener(this);
        user_quit_bt.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_head_portrait_iv://更改头像
            case R.id.user_change_avatar_tv://更换头像

                break;

            case R.id.user_change_phone_number_tv://更改绑定号码
                changPhoneNumber();
                break;

            case R.id.user_update_tv://检查更新
                mPresenter.appUpdate();
                break;

            case R.id.user_about_us_tv://关于我们

                break;

            case R.id.user_help_tv://使用帮助
                break;

            case R.id.user_quit_bt://退出登录


                break;
            default:
                break;
        }

    }

    /**
     * 修改绑定电话号码
     */
    private void changPhoneNumber() {
        requestCode = 1;
        Intent phoneIntent = new Intent(this, PhoneNumberActivity.class);
        startActivityForResult(phoneIntent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (data != null) {
                    String number = data.getStringExtra(PHONE_NUMBER);
                    user_account_number_tv.setText(getString(R.string.account_number) + "\t" + number);
                }

                break;
        }
    }

    @Override
    public void setPresenter(UserInformationContract.UserInformationPresenter presenter) {
        mPresenter = presenter;
    }
}
