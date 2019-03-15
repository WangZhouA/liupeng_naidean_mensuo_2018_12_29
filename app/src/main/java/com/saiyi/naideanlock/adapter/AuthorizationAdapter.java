package com.saiyi.naideanlock.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.makeramen.roundedimageview.RoundedImageView;
import com.saiyi.naideanlock.R;
import com.saiyi.naideanlock.adapter.base.AbsSwipeAdapter;
import com.saiyi.naideanlock.adapter.base.BaseViewHolder;
import com.saiyi.naideanlock.bean.AdminMemberBean;
import com.saiyi.naideanlock.dialog.InputDialog;
import com.saiyi.naideanlock.dialog.PromptDialog;
import com.saiyi.naideanlock.utils.ToastUtil;

import java.util.List;

/**
 * 描述：授权用户适配器
 * 创建作者：ask
 * 创建时间：2017/11/14 15:59
 */

public class AuthorizationAdapter extends AbsSwipeAdapter<AdminMemberBean, AuthorizationAdapter.AuthorizationViewHolder> {

    private Context mContext;

    public AuthorizationAdapter(Context context) {
        super(context, R.layout.item_admin_member);
        this.mContext = context;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe_layout_lt;
    }

    @Override
    public AuthorizationViewHolder onCreateVH(View itemView, int ViewType) {
        return new AuthorizationViewHolder(itemView);
    }

    @Override
    public void onBindDataForItem(AuthorizationViewHolder viewHolder, AdminMemberBean bean, int position, List<AdminMemberBean> mData) {

        viewHolder.item_name_tv.setText(bean.getName());
    }

    @Override
    protected void setItemListeners(AuthorizationViewHolder holder, AdminMemberBean adminMemberBean, int position) {
        setSwipeListener(holder.swipe_layout_lt);
        super.setItemListeners(holder, adminMemberBean, position);
        holder.item_change_name_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final InputDialog mInputDialog = new InputDialog(mContext);
                mInputDialog.show();
                mInputDialog.setDialogText(mContext.getString(R.string.please_enter_name));
                mInputDialog.setSureClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = mInputDialog.getInputText();
                        if (TextUtils.isEmpty(name)) {
                            ToastUtil.toastShort(mContext, mContext.getString(R.string.please_enter_name_toast));
                            return;
                        }

                        //接下来做修改名字的处理
                    }
                });


            }
        });

        holder.item_delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PromptDialog mPromptDialog = new PromptDialog(mContext);
                mPromptDialog.show();
                mPromptDialog.setTitleText(mContext.getString(R.string.warming));
                mPromptDialog.setDialogMessage(mContext.getString(R.string.sure_delete));
                mPromptDialog.setSureOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //做删除操作
                    }
                });
            }
        });

    }

    public class AuthorizationViewHolder extends BaseViewHolder {
        private RoundedImageView item_head_iv;//头像
        private TextView item_name_tv;//名字
        private Button item_change_name_btn;//更名按钮
        private Button item_delete_btn;//删除按钮
        private SwipeLayout swipe_layout_lt;

        public AuthorizationViewHolder(View itemView) {
            super(itemView);
            item_head_iv = (RoundedImageView) itemView.findViewById(R.id.item_head_iv);
            item_name_tv = (TextView) itemView.findViewById(R.id.item_name_tv);
            item_change_name_btn = (Button) itemView.findViewById(R.id.item_change_name_btn);
            item_delete_btn = (Button) itemView.findViewById(R.id.item_delete_btn);
            swipe_layout_lt = (SwipeLayout) itemView.findViewById(R.id.swipe_layout_lt);
        }
    }


}
