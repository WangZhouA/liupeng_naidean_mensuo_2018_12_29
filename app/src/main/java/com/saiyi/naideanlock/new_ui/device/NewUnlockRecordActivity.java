package com.saiyi.naideanlock.new_ui.device;

import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.saiyi.naideanlock.R;
import com.saiyi.naideanlock.bean.MdlDevice;
import com.saiyi.naideanlock.bean.MdlHttpRespList;
import com.saiyi.naideanlock.bean.MdlUnlockRecord;
import com.saiyi.naideanlock.enums.EnumConstantTypeNameMapping;
import com.saiyi.naideanlock.new_ui.device.mvp.p.UnlockRecordActivityPresenter;
import com.saiyi.naideanlock.new_ui.device.mvp.v.UnlockRecordActivityView;
import com.sandy.guoguo.babylib.adapter.recycler.BaseAdapterHelper;
import com.sandy.guoguo.babylib.adapter.recycler.MyRecyclerAdapter;
import com.sandy.guoguo.babylib.adapter.recycler.RecycleViewDivider;
import com.sandy.guoguo.babylib.adapter.recycler.WrapContentLinearLayoutManager;
import com.sandy.guoguo.babylib.constant.BabyExtraConstant;
import com.sandy.guoguo.babylib.constant.BabyHttpConstant;
import com.sandy.guoguo.babylib.dialogs.CommonDialog;
import com.sandy.guoguo.babylib.entity.MdlBaseHttpResp;
import com.sandy.guoguo.babylib.listener.OnMultiClickListener;
import com.sandy.guoguo.babylib.ui.MVPBaseActivity;
import com.sandy.guoguo.babylib.utils.ResourceUtil;
import com.sandy.guoguo.babylib.utils.Utility;
import com.sandy.guoguo.babylib.widgets.MyRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewUnlockRecordActivity extends MVPBaseActivity<UnlockRecordActivityView, UnlockRecordActivityPresenter> implements UnlockRecordActivityView {
    private MyRecyclerView<MdlUnlockRecord> mXRecyclerView;
    private MyRecyclerAdapter<MdlUnlockRecord> adapter;
    private List<MdlUnlockRecord> dataList = new ArrayList<>();

    private TextView tvEmpty;

    private MdlDevice mdlDevice;

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mXRecyclerView != null) {
            mXRecyclerView.destroy();
            mXRecyclerView = null;
        }
    }


    @Override
    protected int getTitleResId() {
        return R.string.unlocking_record;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_new_unlock_record;
    }

    @Override
    protected void initViewAndControl() {
        initNav();

        mdlDevice = getIntent().getParcelableExtra(BabyExtraConstant.EXTRA_ITEM);

        mXRecyclerView = findView(R.id.recyclerView);
        mXRecyclerView.fillData(dataList);


        LinearLayoutManager layoutManager = new WrapContentLinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mXRecyclerView.setLayoutManager(layoutManager);

        mXRecyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL));

        initAdapter();

        mXRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                loadRefresh();
            }

            @Override
            public void onLoadMore() {
                loadMore();
            }
        });
        mXRecyclerView.setAdapter(adapter);

        tvEmpty = findView(R.id.tvEmpty);

        resetRecyclerView(false);

        loadRefresh();
    }

    private void resetRecyclerView(boolean haveData) {
        if (haveData) {
            mXRecyclerView.setVisibility(View.VISIBLE);
            tvEmpty.setVisibility(View.GONE);
        } else {
            mXRecyclerView.setVisibility(View.GONE);
            tvEmpty.setVisibility(View.VISIBLE);
        }
    }

    private void initNav() {
        TextView tvLeft = findView(R.id.toolbarLeft);
        tvLeft.setVisibility(View.VISIBLE);
        ResourceUtil.setCompoundDrawable(tvLeft, R.drawable.dr_ic_back, 0, 0, 0);

        TextView tvRight = findView(R.id.toolbarRight);
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText(R.string.empty);
        tvRight.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void OnMultiClick(View view) {
                clickRight();
            }
        });
    }

    private void clickRight() {
        CommonDialog dialog = new CommonDialog(this, getString(R.string.delete), getString(R.string.sure_empty), new CommonDialog.ClickListener() {
            @Override
            public void clickConfirm() {
                clearAllRecord();
            }

            @Override
            public void clickCancel() {

            }
        });
        dialog.show();
    }


    private void initAdapter() {
        adapter = new MyRecyclerAdapter<MdlUnlockRecord>(this, R.layout._item_activity_new_unlock_record, dataList) {


            @Override
            public void onUpdate(BaseAdapterHelper helper, final MdlUnlockRecord item, final int position) {
                if (item == null) {
                    return;
                }
                ImageView ivPic = helper.getView(R.id.ivPic);
                showRemoteUserHeadImage(item.headPicurl, ivPic);

                String people = TextUtils.isEmpty(item.userName) ? item.number : item.userName;
                String type = EnumConstantTypeNameMapping.getDeviceTypeStr(item.linkType);
                helper.setText(R.id.tvName, Utility.myFormat("%s使用%s开锁", people, type));

                String[] dateTime = item.time.split(" ");
                helper.setText(R.id.tvTime, dateTime[0] + "\n" + dateTime[1]);
            }
        };

    }


    private void loadRefresh() {
        currentPage = 1;
        getUnlockRecordList();
    }

    private void loadMore() {
        if (currentPage < totalPage) {
            currentPage++;
            getUnlockRecordList();
        } else {
            mXRecyclerView.loadMoreComplete();
            mXRecyclerView.refreshComplete();
        }
    }


    private void getUnlockRecordList() {
        Map<String, Object> params = new HashMap<>();
        params.put("mac", mdlDevice.mac);
        params.put("page", currentPage);
        params.put("count", BabyHttpConstant.PER_PAGE_SIZE);
        presenter.getUnlockRecord(params);
    }

    private void clearAllRecord() {
        Map<String, Object> params = new HashMap<>();
        params.put("mac", mdlDevice.mac);
        presenter.deleteAllUnlockRecord(params);
    }

    @Override
    protected UnlockRecordActivityPresenter createPresenter() {
        return new UnlockRecordActivityPresenter(this);
    }


    @Override
    public void showRecordListResult(MdlBaseHttpResp<MdlHttpRespList<MdlUnlockRecord>> resp) {
        mXRecyclerView.loadMoreComplete();
        mXRecyclerView.refreshComplete();
        if (resp.code == BabyHttpConstant.R_HTTP_OK || resp.code == BabyHttpConstant.R_RESPONSE_NO_DATA) {
            if (currentPage == 1) {
                dataList.clear();
            }


            boolean haveData = resp.data != null && resp.data.list != null && !resp.data.list.isEmpty();
            resetRecyclerView(haveData);

            if (haveData) {
                totalPage = resp.totalItems;
                dataList.addAll(resp.data.list);
            }
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showDelAllRecordResult(MdlBaseHttpResp resp) {
        if(resp.code == BabyHttpConstant.R_HTTP_OK){
            dataList.clear();
            adapter.notifyDataSetChanged();
            resetRecyclerView(false);
        }
    }
}
