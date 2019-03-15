package com.saiyi.naideanlock.ui.addAdmin;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.saiyi.naideanlock.R;
import com.saiyi.naideanlock.adapter.ChooseAdminAdapter;
import com.saiyi.naideanlock.adapter.base.AbsBaseAdapter;
import com.saiyi.naideanlock.base.BaseActivity;
import com.saiyi.naideanlock.bean.AddMemberBean;
import com.saiyi.naideanlock.topbar.TopToolBar;
import com.saiyi.naideanlock.utils.PermissionHelpUtil;
import com.saiyi.naideanlock.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 选择要授权的人
 */

public class AddAdminActivity extends BaseActivity implements AddAdminContract.AddAdminView {

    private TopToolBar add_member_bar;
    private RecyclerView add_member_list;//存放获取到的联系人的list

    private AddAdminContract.AddAdminPresenter mAddAdminPresenter;

    private List<AddMemberBean> addAdminList;//读取通讯录中的联系的list
    private ChooseAdminAdapter mChooseAdminAdapter;//显示页面的适配器

    @Override
    protected int getContentView() {
        return R.layout.activity_add_admin;
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        new AddAdminPresenter(this);
    }

    @Override
    protected void initView() {
        add_member_list = (RecyclerView) findViewById(R.id.add_member_list);
        add_member_bar = (TopToolBar) findViewById(R.id.add_member_bar);
    }

    @Override
    protected void initData() {
        addAdminList = new ArrayList<>();

        PermissionHelpUtil.getInstance(this).contacts();//获取权限
        mAddAdminPresenter.mailList(this);

        mChooseAdminAdapter = new ChooseAdminAdapter(this);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        add_member_list.setLayoutManager(mLinearLayoutManager);
        mChooseAdminAdapter.setListData(addAdminList);
        add_member_list.setAdapter(mChooseAdminAdapter);
    }

    @Override
    protected void setListener() {
        //设置item点击事件 进行分享是否选中和分享用户的选择
        mChooseAdminAdapter.setOnItemClickListener(new AbsBaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Object bean, int position) {
                AddMemberBean mAddMemberBean = mChooseAdminAdapter.getItemBean(position);
                if (mAddMemberBean.isShared()) {
                    ToastUtil.toastShort(AddAdminActivity.this, getString(R.string.sorry_already_add));
                    return;
                }

                if (mAddMemberBean.isChoosed()) {  //选中了该用户 再次点击取消分享
                    mAddMemberBean.setChoosed(false);
                    mChooseAdminAdapter.getmSelectedData().remove(mAddMemberBean);
                    mChooseAdminAdapter.notifyDataSetChanged();
                } else {  //点击进行分享
                    mAddMemberBean.setChoosed(true);
                    mChooseAdminAdapter.getmSelectedData().add(mAddMemberBean);
                    mChooseAdminAdapter.notifyDataSetChanged();

                }
            }
        });
    }

    @Override
    protected void topBarSet() {
        add_member_bar.setRightText(getString(R.string.complete));
        add_member_bar.setMiddleText(getString(R.string.add_admin));
        add_member_bar.setRightTextColor(this.getResources().getColor(R.color.color_ffffff));
        add_member_bar.setRightTextSize(14);
    }

    @Override
    protected void topBarListener() {
        add_member_bar.setOnLeftClickListener(new TopToolBar.TitleOnLeftClickListener() {
            @Override
            public void onLeftClick() {
                finish();
            }
        });

        add_member_bar.setOnRightClickListener(new TopToolBar.TitleOnRightClickListener() {
            @Override
            public void onRightClick() {

            }
        });
    }


    @Override
    public void setPresenter(AddAdminContract.AddAdminPresenter presenter) {
        this.mAddAdminPresenter = presenter;
    }

    @Override
    public void GainMailList(List<AddMemberBean> list) {
        this.addAdminList = list;
        mChooseAdminAdapter.setListData(addAdminList);
        mChooseAdminAdapter.notifyDataSetChanged();
    }
}
