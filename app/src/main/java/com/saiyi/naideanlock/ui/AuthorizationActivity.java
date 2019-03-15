package com.saiyi.naideanlock.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.saiyi.naideanlock.R;
import com.saiyi.naideanlock.adapter.AuthorizationAdapter;
import com.saiyi.naideanlock.base.BaseActivity;
import com.saiyi.naideanlock.bean.AdminMemberBean;
import com.saiyi.naideanlock.topbar.TopToolBar;
import com.saiyi.naideanlock.ui.addAdmin.AddAdminActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限管理界面
 */
public class AuthorizationActivity extends BaseActivity {

    private TopToolBar authorization_top_bar;
    private RoundedImageView authorization_head_iv;//管理头像
    private TextView authorization_admin_tv;//管理员
    private TextView authorization_admin_name_tv;//管理员名称

    private RecyclerView authorization_admin_member_list;//管理员成员列表

    private List<AdminMemberBean> memberList;//存放授权成员的额列表

    private AuthorizationAdapter mAuthorizationAdapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_authorization;
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {

    }

    @Override
    protected void initView() {
        authorization_top_bar = (TopToolBar) findViewById(R.id.authorization_top_bar);
        authorization_head_iv = (RoundedImageView) findViewById(R.id.authorization_head_iv);
        authorization_admin_tv = (TextView) findViewById(R.id.authorization_admin_tv);
        authorization_admin_name_tv = (TextView) findViewById(R.id.authorization_admin_name_tv);
        authorization_admin_member_list = (RecyclerView) findViewById(R.id.authorization_admin_member_list);
    }

    @Override
    protected void initData() {
        memberList = new ArrayList<>();

        mAuthorizationAdapter = new AuthorizationAdapter(this);
        mAuthorizationAdapter.setListData(memberList);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        authorization_admin_member_list.setLayoutManager(manager);
        authorization_admin_member_list.setAdapter(mAuthorizationAdapter);
    }

    @Override
    protected void topBarSet() {
        authorization_top_bar.setMiddleText(getString(R.string.permission_management));
        authorization_top_bar.setRightText(getString(R.string.add_to));
        authorization_top_bar.setRightTextSize(14);
        authorization_top_bar.setRightTextColor(getResources().getColor(R.color.color_FFFFFFFF));
    }

    @Override
    protected void topBarListener() {
        authorization_top_bar.setOnLeftClickListener(new TopToolBar.TitleOnLeftClickListener() {
            @Override
            public void onLeftClick() {
                finish();
            }
        });

        authorization_top_bar.setOnRightClickListener(new TopToolBar.TitleOnRightClickListener() {
            @Override
            public void onRightClick() {
                startActivity(AddAdminActivity.class);
            }
        });
    }

    @Override
    protected void setListener() {

    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

}
