package com.saiyi.naideanlock.topbar;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.saiyi.naideanlock.R;

/**
 * 描述：
 * 创建作者：ask
 * 创建时间：2017/9/21 17:06
 */

public class TopToolBar extends RelativeLayout implements View.OnClickListener {

    private TextView left_btn;//左边按钮
    private TextView middle_tv;//中间提示字
    private TextView right_btn;//右边按钮


    private TitleOnLeftClickListener mOnLeftClickListener;
    private TitleOnRightClickListener mOnRightClickListener;


    public TopToolBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.pub_titlebar, this, true);
        left_btn = (TextView) findViewById(R.id.left_btn);
        middle_tv = (TextView) findViewById(R.id.middle_tv);
        right_btn = (TextView) findViewById(R.id.right_btn);

        left_btn.setOnClickListener(this);
        right_btn.setOnClickListener(this);
    }

    /**
     * 设置左边按钮的文字
     *
     * @param text 文字
     */
    public void setLeftText(String text) {
        if (!TextUtils.isEmpty(text)) {
            left_btn.setText(text);
        }
    }

    /**
     * 设置左边按钮文字大小
     *
     * @param size
     */
    public void setLeftTextSize(float size) {
        left_btn.setTextSize(size);
    }

    /**
     * 设置左边按钮的文字颜色
     *
     * @param color
     */
    public void setLeftTextColor(int color) {
        left_btn.setTextColor(color);
    }


    /**
     * 设置左边按钮的背景图
     *
     * @param id 资源索引id
     */
    public void setLeftBackground(int id) {
        left_btn.setBackgroundResource(id);

    }


    /**
     * 设置中间提示信息
     *
     * @param text 文字
     */
    public void setMiddleText(String text) {
        if (!TextUtils.isEmpty(text)) {
            middle_tv.setText(text);
        }
    }

    /**
     * 设置中间文字大小
     *
     * @param size
     */
    public void setMiddleTextSize(float size) {
        middle_tv.setTextSize(size);
    }

    /**
     * 设置中间文字颜色
     *
     * @param color
     */
    public void setMiddleTextColor(int color) {
        middle_tv.setTextColor(color);
    }

    /**
     * 设置右边按钮的文字
     *
     * @param text 文字
     */
    public void setRightText(String text) {
        if (!TextUtils.isEmpty(text)) {
            right_btn.setText(text);
        }
    }

    /**
     * 设置右边按钮文字大小
     *
     * @param size
     */
    public void setRightTextSize(float size) {
        right_btn.setTextSize(size);
    }

    /**
     * 设置右边按钮的文字颜色
     *
     * @param color
     */
    public void setRightTextColor(int color) {
        right_btn.setTextColor(color);
    }


    /**
     * 设置右边按钮的背景图
     *
     * @param id 资源索引id
     */
    public void setRightBackground(int id) {
        right_btn.setBackgroundResource(id);

    }

    /**
     * 设置左键是否可见
     *
     * @param viseble
     */
    public void setLestVisible(int viseble) {
        left_btn.setVisibility(viseble);
    }

    /**
     * 设置右键是否可见
     *
     * @param viseble
     */
    public void setRightVisible(int viseble) {
        right_btn.setVisibility(viseble);
    }

    /**
     * 设置左边按钮的监听
     *
     * @param mOnLeftClickListener
     */
    public void setOnLeftClickListener(TitleOnLeftClickListener mOnLeftClickListener) {
        this.mOnLeftClickListener = mOnLeftClickListener;
    }


    /**
     * 设置右边按钮的监听
     *
     * @param mOnRightClickListener
     */
    public void setOnRightClickListener(TitleOnRightClickListener mOnRightClickListener) {
        this.mOnRightClickListener = mOnRightClickListener;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_btn:
                if (mOnLeftClickListener != null) {
                    mOnLeftClickListener.onLeftClick();
                }
                break;
            case R.id.right_btn:
                if (mOnRightClickListener != null) {
                    mOnRightClickListener.onRightClick();
                }
                break;
            default:
                break;
        }
    }

    public interface TitleOnLeftClickListener {
        void onLeftClick();
    }

    public interface TitleOnRightClickListener {
        void onRightClick();
    }
}
