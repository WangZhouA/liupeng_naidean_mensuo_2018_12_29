package com.saiyi.naideanlock.new_ui.device;

import android.content.Intent;
import android.support.constraint.Group;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import com.saiyi.naideanlock.R;
import com.saiyi.naideanlock.bean.MdlDevice;
import com.saiyi.naideanlock.bean.MdlLockPeriodListBean;
import com.saiyi.naideanlock.bean.MdlLockTimeListBean;
import com.saiyi.naideanlock.enums.EnumDeviceAdmin;
import com.saiyi.naideanlock.new_ui.device.mvp.p.AuthManagerSettingActivityPresenter;
import com.saiyi.naideanlock.new_ui.device.mvp.v.AuthManagerSettingActivityView;
import com.sandy.guoguo.babylib.constant.BabyExtraConstant;
import com.sandy.guoguo.babylib.constant.BabyHttpConstant;
import com.sandy.guoguo.babylib.entity.MdlBaseHttpResp;
import com.sandy.guoguo.babylib.listener.OnMultiClickListener;
import com.sandy.guoguo.babylib.ui.MVPBaseActivity;
import com.sandy.guoguo.babylib.utils.LogAndToastUtil;
import com.sandy.guoguo.babylib.utils.ResourceUtil;
import com.sandy.guoguo.babylib.utils.Utility;
import com.sandy.guoguo.babylib.widgets.wheel.ArrayWheelAdapter;
import com.sandy.guoguo.babylib.widgets.wheel.WheelView;
import com.sandy.guoguo.babylib.widgets.wheel.listens.OnWheelChangedListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/6/11.
 */

public class NewAuthManagerSettingActivity extends MVPBaseActivity<AuthManagerSettingActivityView, AuthManagerSettingActivityPresenter> implements AuthManagerSettingActivityView {
    private static final int INTENT_CHANGE_WEEK_REQ = 0XA009;

    private MdlDevice mdlAuthUser;

    private List<TextView> timeFrameList = new ArrayList<>();
    private WheelView wheelViewStartHour, wheelViewStartMinute, wheelViewEndHour, wheelViewEndMinute;
    private int timeFrameIndex = -1;

    private ArrayList<Integer> weekCheckList = new ArrayList<>();
    private ArrayList<int[]> timeSelectList = new ArrayList<>();
    private TextView tvGeneralAdmin;
    private TextView tvNanny;
    private TextView tvUnlockWeekPeriodWeek;

    private Group groupCenter, groupBottom;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_new_auth_manager_setting;
    }

    @Override
    protected int getTitleResId() {
        return R.string.set;
    }

    @Override
    protected void initViewAndControl() {
        initNav();

        mdlAuthUser = getIntent().getParcelableExtra(BabyExtraConstant.EXTRA_ITEM);

        groupBottom = findView(R.id.groupBottom);
        groupCenter = findView(R.id.groupCenter);

        tvGeneralAdmin = findView(R.id.tvGeneralAdmin);
        tvGeneralAdmin.setSelected(mdlAuthUser.isAdmin == EnumDeviceAdmin.NOT_ADMIN);
        tvGeneralAdmin.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void OnMultiClick(View view) {
                clickAuthGeneralAdmin();
            }
        });
        tvNanny = findView(R.id.tvNanny);
        tvNanny.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void OnMultiClick(View view) {
                clickAuthNanny();
            }
        });

        TextView tvTime1 = findView(R.id.tvTime1);
        TextView tvTime2 = findView(R.id.tvTime2);
        TextView tvTime3 = findView(R.id.tvTime3);
        tvTime1.setOnClickListener(new TimeClickListener(0));
        tvTime2.setOnClickListener(new TimeClickListener(1));
        tvTime3.setOnClickListener(new TimeClickListener(2));
        timeFrameList.add(tvTime1);
        timeFrameList.add(tvTime2);
        timeFrameList.add(tvTime3);

        if (mdlAuthUser.isAdmin == EnumDeviceAdmin.NANNY) {
            setLockPeriodAndTimeData();
            tvNanny.setSelected(true);
            groupCenter.setVisibility(View.VISIBLE);
        } else {
            initWeekCheck();
            initTimeSelect();
        }

        tvUnlockWeekPeriodWeek = findView(R.id.tvUnlockWeekPeriod);
        tvUnlockWeekPeriodWeek.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void OnMultiClick(View view) {
                clickChangeWeek();
            }
        });


        wheelViewStartHour = findView(R.id.wheelViewStartHour);
        wheelViewStartMinute = findView(R.id.wheelViewStartMinute);
        wheelViewEndHour = findView(R.id.wheelViewEndHour);
        wheelViewEndMinute = findView(R.id.wheelViewEndMinute);

        initWheelView();


        findView(R.id.tvCancel).setOnClickListener(new OnMultiClickListener() {
            @Override
            public void OnMultiClick(View view) {
                groupBottom.setVisibility(View.GONE);
            }
        });
        findView(R.id.tvComplete).setOnClickListener(new OnMultiClickListener() {
            @Override
            public void OnMultiClick(View view) {
                clickComplete();
            }
        });


        showPeriodWeekView();
        showLockTimeView();
    }

    private void showLockTimeView() {
        int len = timeSelectList.size();
        len = len > 3 ? 3 : len;
        int[] _arr;
        for (int i = 0; i < len; i++) {
            _arr = timeSelectList.get(i);
            timeFrameList.get(i).setText(Utility.myFormat("%d:%02d-%d:%02d", _arr[0], _arr[1], _arr[2], _arr[3]));
        }
    }

    private void setLockPeriodAndTimeData() {
        List<MdlLockTimeListBean> timeListBeans = mdlAuthUser.lockTimeList;
        for (MdlLockTimeListBean bean : timeListBeans) {
            if (bean.lockTime.equals("8")) {//8就是每天
                weekCheckList.clear();
                for (int i = 1; i < 8; i++) {
                    weekCheckList.add(i);
                }
                break;
            } else if (!TextUtils.isEmpty(bean.lockTime)) {
                weekCheckList.add(Integer.parseInt(bean.lockTime));
            }
        }


        List<MdlLockPeriodListBean> lockPeriodListBeans = mdlAuthUser.lockPeriodList;

        int[] period;
        for (MdlLockPeriodListBean bean : lockPeriodListBeans) {
            period = new int[4];
            if (TextUtils.isEmpty(bean.lockPeriod)) {
                continue;
            }
            String[] periodArr = bean.lockPeriod.split("[:-]");
            int len = periodArr.length;
            len = len > 4 ? 4 : len;
            for (int i = 0; i < len; i++) {
                period[i] = Integer.parseInt(periodArr[i]);
            }
            timeSelectList.add(period);
        }
    }

    private void initTimeSelect() {
        timeSelectList.add(new int[]{8, 0, 10, 0});
        timeSelectList.add(new int[]{12, 0, 14, 0});
        timeSelectList.add(new int[]{17, 0, 19, 0});
    }


    private void clickChangeWeek() {
        Intent intent = new Intent(this, NewAuthManagerSettingWeekActivity.class);
        intent.putIntegerArrayListExtra(BabyExtraConstant.EXTRA_ITEM, weekCheckList);
        startActivityForResult(intent, INTENT_CHANGE_WEEK_REQ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case INTENT_CHANGE_WEEK_REQ:
                ArrayList<Integer> list = data.getIntegerArrayListExtra(BabyExtraConstant.EXTRA_ITEM);
                weekCheckList.clear();
                weekCheckList.addAll(list);
                showPeriodWeekView();
                break;
        }
    }

    private void showPeriodWeekView() {
        if (weekCheckList.size() == 7) {
            tvUnlockWeekPeriodWeek.setText(R.string.everyday);
        } else {
            StringBuilder sb = new StringBuilder();
            for (int i : weekCheckList) {
                switch (i) {
                    case 1:
                        sb.append("每周日、");
                        break;
                    case 2:
                        sb.append("每周一、");
                        break;
                    case 3:
                        sb.append("每周二、");
                        break;
                    case 4:
                        sb.append("每周三、");
                        break;
                    case 5:
                        sb.append("每周四、");
                        break;
                    case 6:
                        sb.append("每周五、");
                        break;
                    case 7:
                        sb.append("每周六、");
                        break;
                }
            }

            if (sb.length() > 0) {
                sb.deleteCharAt(sb.length() - 1);
            }

            tvUnlockWeekPeriodWeek.setText(sb.toString());
        }
    }

    private void clickAuthNanny() {
        groupCenter.setVisibility(View.VISIBLE);
        tvGeneralAdmin.setSelected(false);
        tvNanny.setSelected(true);
    }

    private void clickAuthGeneralAdmin() {
        groupCenter.setVisibility(View.GONE);
        tvGeneralAdmin.setSelected(true);
        tvNanny.setSelected(false);
    }

    private void initWeekCheck() {
        weekCheckList.add(1);
        weekCheckList.add(2);
        weekCheckList.add(3);
        weekCheckList.add(4);
        weekCheckList.add(5);
        weekCheckList.add(6);
        weekCheckList.add(7);
    }

    private void clickComplete() {
        int startHourIndex = wheelViewStartHour.getCurrentItem();
        int startMinuteIndex = wheelViewStartMinute.getCurrentItem();
        int endHourIndex = wheelViewEndHour.getCurrentItem();
        int endMinuteIndex = wheelViewEndMinute.getCurrentItem();

        if (endHourIndex * 60 + endMinuteIndex > startHourIndex * 60 + startMinuteIndex) {
            timeFrameList.get(timeFrameIndex).setText(Utility.myFormat("%d:%02d-%d:%02d", startHourIndex, startMinuteIndex, endHourIndex, endMinuteIndex));
            groupBottom.setVisibility(View.GONE);

            int[] data = {startHourIndex, startMinuteIndex, endHourIndex, endMinuteIndex};
            timeSelectList.set(timeFrameIndex, data);
        } else {
            LogAndToastUtil.toast("结束时间必须大于开始时间");
        }
    }

    private class TimeClickListener extends OnMultiClickListener {
        private int index;

        public TimeClickListener(int index) {
            this.index = index;
        }

        @Override
        public void OnMultiClick(View view) {
            if (timeFrameIndex >= 0) {
                if (timeFrameIndex == index) {
                    return;
                }
                TextView tv = timeFrameList.get(timeFrameIndex);
                tv.setSelected(false);
            }
            timeFrameList.get(index).setSelected(true);
            timeFrameIndex = index;
            groupBottom.setVisibility(View.VISIBLE);
            resetCheckTimeView();
        }
    }


    private void initNav() {
        TextView tvLeft = findView(R.id.toolbarLeft);
        tvLeft.setVisibility(View.VISIBLE);
        ResourceUtil.setCompoundDrawable(tvLeft, R.drawable.dr_ic_back, 0, 0, 0);

        TextView tvRight = findView(R.id.toolbarRight);
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText(R.string.complete);
        tvRight.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void OnMultiClick(View view) {
                clickRight();
            }
        });
    }

    private void clickRight() {
        if (tvGeneralAdmin.isSelected() && mdlAuthUser.isAdmin == EnumDeviceAdmin.NOT_ADMIN) {
            finish();
        } else {
            addRole2Remote();
        }
    }

    private void addRole2Remote() {
        Map<String, Object> params = new HashMap<>();
        params.put("number", mdlAuthUser.number);
        params.put("mac", mdlAuthUser.mac);
        params.put("linkType", mdlAuthUser.linkType);
        if (tvGeneralAdmin.isSelected()) {
            params.put("lockName", mdlAuthUser.lockName);
            params.put("isAdmin", EnumDeviceAdmin.IS_ADMIN);
            presenter.bindDevice(params);
        } else {

            List<Object> list = new ArrayList<>();
            Map<String, Object> tmpMap;
            if (weekCheckList.size() == 7) {
                tmpMap = new HashMap<>();
                tmpMap.put("lockTime", 8);
                list.add(tmpMap);
            } else {
                for (int i : weekCheckList) {
                    tmpMap = new HashMap<>();
                    tmpMap.put("lockTime", i);
                    list.add(tmpMap);
                }
            }

            params.put("lockTimeList", list);

            for (TextView tv : timeFrameList) {
                tmpMap = new HashMap<>();
                tmpMap.put("lockPeriod", Utility.getEditTextStr(tv));
                list.add(tmpMap);
            }

            params.put("lockPeriodList", list);
            presenter.addNanny(params);
        }

    }

    @Override
    protected AuthManagerSettingActivityPresenter createPresenter() {
        return new AuthManagerSettingActivityPresenter(this);
    }


    @Override
    public void showSetNannyResult(MdlBaseHttpResp resp) {
        if (resp.code == BabyHttpConstant.R_HTTP_OK) {
            setResult(RESULT_OK);
            finish();
        }
    }

    @Override
    public void showSetNoAdminResult(MdlBaseHttpResp resp) {
        if (resp.code == BabyHttpConstant.R_HTTP_OK) {
            setResult(RESULT_OK);
            finish();
        }
    }

    private void initWheelView() {
        DisplayMetrics dm = Utility.getDisplayScreenSize(this);
        int height = (int) (dm.heightPixels * 0.25f);
        wheelViewStartHour.setHeight(height);
        wheelViewStartMinute.setHeight(height);
        wheelViewEndHour.setHeight(height);
        wheelViewEndMinute.setHeight(height);

        final int hourLen = 24;
        final String[] hourData = new String[hourLen];
        for (int i = 0; i < hourLen; i++) {
            hourData[i] = Utility.myFormat("%02d", i);
        }

        ArrayWheelAdapter<String> hourAdapter = new ArrayWheelAdapter<>(this, hourData);
        hourAdapter.setItemResource(R.layout.wheel_text_item);//设置圈里面View的视图
        wheelViewStartHour.setViewAdapter(hourAdapter);
        wheelViewEndHour.setViewAdapter(hourAdapter);

        final int minuteLen = 60;
        final String[] minuteData = new String[minuteLen];
        for (int i = 0; i < minuteLen; i++) {
            minuteData[i] = Utility.myFormat("%02d", i);
        }

        ArrayWheelAdapter<String> minuteAdapter = new ArrayWheelAdapter<>(this, minuteData);
        minuteAdapter.setItemResource(R.layout.wheel_text_item);//设置圈里面View的视图
        wheelViewStartMinute.setViewAdapter(minuteAdapter);
        wheelViewEndMinute.setViewAdapter(minuteAdapter);

        wheelViewStartHour.addChangingListener(changedListener);
        wheelViewStartMinute.addChangingListener(changedListener);
        wheelViewEndHour.addChangingListener(changedListener);
        wheelViewEndMinute.addChangingListener(changedListener);

//        wheelViewStartHour.addScrollingListener(scrollListener);
//        wheelViewStartMinute.addScrollingListener(scrollListener);
//        wheelViewEndHour.addScrollingListener(scrollListener);
//        wheelViewEndMinute.addScrollingListener(scrollListener);

    }

    private void resetCheckTimeView() {
        int[] data = timeSelectList.get(timeFrameIndex);
        wheelViewStartHour.setCurrentItem(data[0], true);
        wheelViewStartMinute.setCurrentItem(data[1], true);
        wheelViewEndHour.setCurrentItem(data[2], true);
        wheelViewEndMinute.setCurrentItem(data[3], true);
    }

    private OnWheelChangedListener changedListener = new OnWheelChangedListener() {
        @Override
        public void onChanged(WheelView wheel, int oldValue, int newValue) {
            wheel.invalidateWheel(true);
        }
    };

}
