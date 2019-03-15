package com.saiyi.naideanlock.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.saiyi.naideanlock.R;
import com.saiyi.naideanlock.adapter.base.AbsBaseAdapter;
import com.saiyi.naideanlock.adapter.base.BaseViewHolder;
import com.saiyi.naideanlock.bean.DeviceBean;

import java.util.List;

/**
 * 描述：设备列表视图适配器
 * 创建作者：ask
 * 创建时间：2017/11/16 11:47
 */

public class DeviceAdapter extends AbsBaseAdapter<DeviceBean, DeviceAdapter.DeviceViewHolder> {

    public DeviceAdapter(Context context) {
        super(context, R.layout.item_device);
    }

    @Override
    public DeviceViewHolder onCreateVH(View itemView, int ViewType) {
        return new DeviceViewHolder(itemView);
    }

    @Override
    public void onBindDataForItem(DeviceViewHolder viewHolder, DeviceBean bean, int position, List<DeviceBean> mData) {
        viewHolder.device_name_tv.setText(bean.getDeviceName());
    }

    @Override
    protected void setItemListeners(DeviceViewHolder holder, DeviceBean deviceBean, int position) {
        super.setItemListeners(holder, deviceBean, position);
        holder.device_delete_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //只想删除设备操作
            }
        });

    }

    public class DeviceViewHolder extends BaseViewHolder {

        private TextView device_name_tv;//设备名称
        private ImageView device_delete_iv;//删除设备按钮

        public DeviceViewHolder(View itemView) {
            super(itemView);
            device_name_tv = getViewById(R.id.device_name_tv);
            device_delete_iv = getViewById(R.id.device_delete_iv);
        }
    }
}
