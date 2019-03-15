package com.saiyi.naideanlock.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.saiyi.naideanlock.R;
import com.saiyi.naideanlock.adapter.base.AbsBaseAdapter;
import com.saiyi.naideanlock.adapter.base.BaseViewHolder;
import com.saiyi.naideanlock.bean.PhotoBean;

import java.util.List;

/**
 * 描述：
 * 创建作者：ask
 * 创建时间：2017/11/15 18:23
 */

public class PhotoAdapter extends AbsBaseAdapter<PhotoBean, PhotoAdapter.PhotoViewHolder> {

    private Context mContext;

    public PhotoAdapter(Context context) {
        super(context, R.layout.item_photo);
        this.mContext = context;
    }

    @Override
    public PhotoViewHolder onCreateVH(View itemView, int ViewType) {
        return new PhotoViewHolder(itemView);
    }

    @Override
    public void onBindDataForItem(PhotoViewHolder viewHolder, PhotoBean bean, int position, List<PhotoBean> mData) {

        Glide.with(mContext).load(bean.getUrl()).error(R.mipmap.about_us).into(viewHolder.photo_iv);
        viewHolder.photo_data_tv.setText(bean.getData());
        viewHolder.photo_time_tv.setText(bean.getTime());


    }

    public class PhotoViewHolder extends BaseViewHolder {

        private ImageView photo_iv;//照片预览
        private TextView photo_data_tv;//拍照日期
        private TextView photo_time_tv;//拍照时间

        public PhotoViewHolder(View itemView) {
            super(itemView);

            photo_iv = getViewById(R.id.photo_time_tv);
            photo_data_tv = getViewById(R.id.photo_data_tv);
            photo_time_tv = getViewById(R.id.photo_time_tv);
        }
    }
}
