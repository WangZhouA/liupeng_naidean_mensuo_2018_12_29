package com.saiyi.naideanlock.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.saiyi.naideanlock.R;
import com.saiyi.naideanlock.adapter.base.AbsBaseAdapter;
import com.saiyi.naideanlock.adapter.base.BaseViewHolder;
import com.saiyi.naideanlock.bean.UnlockingBean;

import java.util.List;

/**
 * 描述：开锁记录适配器
 * 创建作者：ask
 * 创建时间：2017/11/15 17:41
 */

public class UnlockingAdapter extends AbsBaseAdapter<UnlockingBean, UnlockingAdapter.UnlockingViewHolder> {

    public UnlockingAdapter(Context context) {
        super(context, R.layout.item_unlocking);
    }

    @Override
    public UnlockingViewHolder onCreateVH(View itemView, int ViewType) {
        return new UnlockingViewHolder(itemView);
    }

    @Override
    public void onBindDataForItem(UnlockingViewHolder viewHolder, UnlockingBean bean, int position, List<UnlockingBean> mData) {

        viewHolder.record_information_tv.setText(bean.getInformation());

        viewHolder.record_data_tv.setText(bean.getDate());
        viewHolder.record_time_tv.setText(bean.getTime());
    }

    public class UnlockingViewHolder extends BaseViewHolder {

        private RoundedImageView record_head_iv;//头像
        private TextView record_information_tv;//开锁信息
        private ImageView record_type_iv;//开锁类型图标
        private TextView record_data_tv;//开锁日期
        private TextView record_time_tv;//开锁时间

        public UnlockingViewHolder(View itemView) {
            super(itemView);
            record_head_iv = getViewById(R.id.record_head_iv);
            record_information_tv = getViewById(R.id.record_information_tv);
            record_type_iv = getViewById(R.id.record_type_iv);
            record_data_tv = getViewById(R.id.record_data_tv);
            record_time_tv = getViewById(R.id.record_time_tv);
        }
    }
}
