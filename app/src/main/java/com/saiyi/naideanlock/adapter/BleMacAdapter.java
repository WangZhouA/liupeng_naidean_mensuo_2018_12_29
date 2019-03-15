package com.saiyi.naideanlock.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.saiyi.naideanlock.R;
import com.saiyi.naideanlock.adapter.base.AbsBaseAdapter;
import com.saiyi.naideanlock.adapter.base.BaseViewHolder;
import com.saiyi.naideanlock.bean.BleAddressBean;
import com.saiyi.naideanlock.dialog.PromptDialog;

import java.util.List;

/**
 * 描述：扫描蓝牙结果适配器
 * 创建作者：ask
 * 创建时间：2017/11/13 18:51
 */

public class BleMacAdapter extends AbsBaseAdapter<BleAddressBean, BleMacAdapter.MacViewHolder> {
    private Context mContext;

    public static String BLE_MAC_BEAN = "ble_mac_bean";

    public BleMacAdapter(Context context) {
        super(context, R.layout.item_ble_serach);
        this.mContext = context;
    }

    @Override
    public MacViewHolder onCreateVH(View itemView, int ViewType) {
        return new MacViewHolder(itemView);
    }

    @Override
    public void onBindDataForItem(MacViewHolder viewHolder, BleAddressBean bean, int position, List<BleAddressBean> mData) {
        viewHolder.item_ble_name.setText(bean.getBleName());
        viewHolder.item_mac_tv.setText(bean.getBleMac());
    }

    @Override
    protected void setItemListeners(MacViewHolder holder, final BleAddressBean bleAddressBean, int position) {
        super.setItemListeners(holder, bleAddressBean, position);
        holder.item_add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PromptDialog promptDialog = new PromptDialog(mContext);
                promptDialog.show();
                promptDialog.setTitleText(mContext.getString(R.string.warming));
                promptDialog.setDialogMessage(mContext.getString(R.string.add_sure));
                promptDialog.setSureOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //做蓝牙添加操作
                    }
                });
            }
        });
    }

    public class MacViewHolder extends BaseViewHolder {

        private TextView item_ble_name;//显示名字
        private TextView item_mac_tv;//显示蓝牙
        private Button item_add_btn;

        public MacViewHolder(View itemView) {
            super(itemView);
            item_ble_name = getViewById(R.id.item_ble_name);
            item_mac_tv = getViewById(R.id.item_mac_tv);
            item_add_btn = getViewById(R.id.item_add_btn);
        }
    }
}
