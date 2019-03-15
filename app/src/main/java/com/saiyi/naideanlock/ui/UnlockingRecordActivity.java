package com.saiyi.naideanlock.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.saiyi.naideanlock.R;
import com.saiyi.naideanlock.adapter.UnlockingAdapter;
import com.saiyi.naideanlock.adapter.base.AbsBaseAdapter;
import com.saiyi.naideanlock.base.BaseActivity;
import com.saiyi.naideanlock.bean.UnlockingBean;
import com.saiyi.naideanlock.dialog.PromptDialog;
import com.saiyi.naideanlock.topbar.TopToolBar;

import java.util.ArrayList;
import java.util.List;

/**
 * 开锁记录页面
 */
public class UnlockingRecordActivity extends BaseActivity {

    private TopToolBar record_top_bar;
    private RecyclerView record_list;
    private List<UnlockingBean> unlockingList;//开锁记录
    private UnlockingAdapter mUnlockingAdapter;//开锁记录适配器
    private PromptDialog mPromptDialog;//提示对话框

    @Override
    protected int getContentView() {
        return R.layout.activity_unlocking_record;
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {

    }

    @Override
    protected void initView() {
        record_top_bar = (TopToolBar) findViewById(R.id.record_top_bar);
        record_list = (RecyclerView) findViewById(R.id.record_list);
        mPromptDialog = new PromptDialog(this);
    }

    @Override
    protected void initData() {
        unlockingList = new ArrayList<>();

        mUnlockingAdapter = new UnlockingAdapter(this);
        mUnlockingAdapter.setListData(unlockingList);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        record_list.setLayoutManager(manager);

    }

    @Override
    protected void topBarSet() {
        record_top_bar.setMiddleText(getString(R.string.unlocking_record));
        record_top_bar.setRightText(getString(R.string.empty));
        record_top_bar.setRightTextSize(14);
    }

    @Override
    protected void topBarListener() {
        record_top_bar.setOnLeftClickListener(new TopToolBar.TitleOnLeftClickListener() {
            @Override
            public void onLeftClick() {
                finish();
            }
        });
        record_top_bar.setOnRightClickListener(new TopToolBar.TitleOnRightClickListener() {
            @Override
            public void onRightClick() {
                mPromptDialog.show();
                mPromptDialog.setTitleText(getString(R.string.warming));
                mPromptDialog.setDialogMessage(getString(R.string.sure_empty));
                mPromptDialog.setSureOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //做清空开锁记录处理
                    }
                });
            }
        });
    }

    @Override
    protected void setListener() {
        mUnlockingAdapter.setOnItemLongListener(new AbsBaseAdapter.OnItemLongListener() {
            @Override
            public boolean onItemLong(View view, Object bean, int position) {
                mPromptDialog.show();
                mPromptDialog.setTitleText(getString(R.string.warming));
                mPromptDialog.setDialogMessage(getString(R.string.sure_delete_unlocking));
                mPromptDialog.setSureOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //做删除开锁记录处理
                    }
                });
                return false;
            }
        });
    }
}
