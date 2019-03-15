package com.saiyi.naideanlock.adapter;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.saiyi.naideanlock.R;
import com.saiyi.naideanlock.adapter.base.AbsBaseAdapter;
import com.saiyi.naideanlock.adapter.base.BaseViewHolder;
import com.saiyi.naideanlock.bean.AddMemberBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：选择授权对象适配器
 * 创建作者：ask
 * 创建时间：2017/11/1 15:13
 */

public class ChooseAdminAdapter extends AbsBaseAdapter<AddMemberBean, ChooseAdminAdapter.AdminViewHolder> {

    private List<AddMemberBean> mSelectedData;//用于存放需要分享的联系人
    private Context mContext;

    public ChooseAdminAdapter(Context context) {
        super(context, R.layout.item_contacts);
        mSelectedData = new ArrayList<>();
        this.mContext = context;
    }

    @Override
    public AdminViewHolder onCreateVH(View itemView, int ViewType) {
        return new AdminViewHolder(itemView);
    }

    @Override
    public void onBindDataForItem(AdminViewHolder viewHolder, AddMemberBean bean, int position, List<AddMemberBean> mData) {
//        viewHolder.contacts_head_iv.setImageDrawable(bean.getUrl());
        viewHolder.contacts_name_tv.setText(bean.getName());
        viewHolder.contacts_check_box.setChecked(bean.isShared());
        if (bean.isChoosed()) {
            viewHolder.contacts_check_box.setChecked(bean.isChoosed());
        }

    }

    @Override
    protected void setItemListeners(AdminViewHolder holder, AddMemberBean addMemberBean, int position) {
        super.setItemListeners(holder, addMemberBean, position);
    }

    public List<AddMemberBean> getmSelectedData() {
        if (mSelectedData != null) {
            return mSelectedData;
        }
        return null;
    }


    public class AdminViewHolder extends BaseViewHolder {
        private RoundedImageView contacts_head_iv;//联系人头像
        private TextView contacts_name_tv;//联系人姓名
        private CheckBox contacts_check_box;//是否被选中


        public AdminViewHolder(View itemView) {
            super(itemView);
            contacts_head_iv = getViewById(R.id.contacts_head_iv);
            contacts_name_tv = getViewById(R.id.contacts_name_tv);
            contacts_check_box = getViewById(R.id.contacts_check_box);

        }
    }
}
