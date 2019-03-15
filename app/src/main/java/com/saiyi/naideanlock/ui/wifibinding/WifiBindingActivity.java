package com.saiyi.naideanlock.ui.wifibinding;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.czp.library.ArcProgress;
import com.saiyi.naideanlock.R;
import com.saiyi.naideanlock.base.BaseActivity;
import com.saiyi.naideanlock.topbar.TopToolBar;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 链接wifi进度显示页面
 */
public class WifiBindingActivity extends BaseActivity implements View.OnClickListener, BindingContract.BindingView {

    private TopToolBar binding_top_bar;
    private ArcProgress binding_arc_progress_bar;//圆形进度条
    private Button binding_cancel_btn;//取消按钮

    private BindingContract.BindingPresenter mBindingPresenter;

    //用于进度
    private int sumProgress;
    //进度定时器
    private Timer mProgressTimer;

    //用于更新界面
    private Handler mViewHandler = new Handler();

    //进度任务
    private TimerTask mProgressTask = new TimerTask() {

        public void run() {
            if (sumProgress < 40) {
                sumProgress += new Random().nextInt(10);
            } else if (sumProgress >= 40 && sumProgress < 80) {
                sumProgress += new Random().nextInt(5);
            } else if (sumProgress >= 80 && sumProgress < 99) {
                sumProgress += new Random().nextInt(2);
            } else {
            }
            mViewHandler.post(new Runnable() {
                @Override
                public void run() {
                    binding_arc_progress_bar.setProgress(sumProgress);
                }
            });
        }
    };


    @Override
    protected int getContentView() {
        return R.layout.activity_wifi_binding;
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        new BindingPresenter(this);
    }

    @Override
    protected void initView() {
        binding_top_bar = (TopToolBar) findViewById(R.id.binding_top_bar);
        binding_arc_progress_bar = (ArcProgress) findViewById(R.id.binding_arc_progress_bar);
        binding_cancel_btn = (Button) findViewById(R.id.binding_cancel_btn);
    }

    @Override
    protected void initData() {
        mProgressTimer = new Timer("ProgressTimer");

        mProgressTimer.schedule(mProgressTask, 0, 1000);

        binding_arc_progress_bar.setOnCenterDraw(new ArcProgress.OnCenterDraw() {
            @Override
            public void draw(Canvas canvas, RectF rectF, float x, float y, float storkeWidth, int progress) {
                Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

                Paint bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                bgPaint.setColor(getResources().getColor(R.color.color_3f424e));
                textPaint.setStrokeWidth(35);
                textPaint.setTextSize(50);
                textPaint.setColor(getResources().getColor(R.color.color_FFFFFFFF));
                String progressStr = String.valueOf(progress + "%");
                float textX = x - (textPaint.measureText(progressStr) / 2);
                float textY = y - ((textPaint.descent() + textPaint.ascent()) / 2);

                float circleY = binding_arc_progress_bar.getHeight() / 2;
                float circleX = binding_arc_progress_bar.getMeasuredWidth() / 2;

                float width = ((binding_arc_progress_bar.getMeasuredWidth()) - 10) / 2;
                canvas.drawCircle(circleX, circleY, width, bgPaint);

                canvas.drawText(progressStr, textX, textY, textPaint);


            }
        });
    }

    @Override
    protected void topBarSet() {
        binding_top_bar.setRightVisible(View.GONE);
        binding_top_bar.setMiddleText(getString(R.string.device_connect));
    }

    @Override
    protected void topBarListener() {
        binding_top_bar.setOnLeftClickListener(new TopToolBar.TitleOnLeftClickListener() {
            @Override
            public void onLeftClick() {
                finish();
            }
        });
    }

    @Override
    protected void setListener() {

    }

    //停值
    private void stopTimer() {
        if (mProgressTimer != null) {
            mProgressTask.cancel();
            mProgressTimer.cancel();
            mProgressTimer = null;
        }
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopTimer();
    }

    @Override
    public void setPresenter(BindingContract.BindingPresenter presenter) {
        this.mBindingPresenter = presenter;
    }
}
